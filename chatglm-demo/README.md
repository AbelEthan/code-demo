# 智谱Ai大模型开放SDK - By 小傅哥版本

为了让研发伙伴更快，更方便的接入使用智谱Ai大模型。从而开发的 chatglm-sdk-java 也欢迎👏🏻大家基于智谱API接口补充需要的功能。

此SDK设计，以 Session 会话模型，提供工厂🏭创建服务。代码非常清晰，易于扩展、易于维护。你的PR/ISSUE贡献💐会让AI更加璀璨，[感谢智谱AI团队](https://www.zhipuai.cn/)。

---

## 👣目录

1. 组件配置
2. 功能测试
    1. 代码执行 - `使用：代码的方式主要用于程序接入`
    2. 脚本测试 - `测试：生成Token，直接通过HTTP访问Ai服务`
3. 程序接入

## 1. 组件配置

- 申请ApiKey：[https://open.bigmodel.cn/usercenter/apikeys](https://open.bigmodel.cn/usercenter/apikeys) - 注册申请开通，即可获得 ApiKey
- 运行环境：JDK 1.8+
- maven pom - `已发布到Maven仓库`

```pom
<dependency>
    <groupId>cn.bugstack</groupId>
    <artifactId>chatglm-sdk-java</artifactId>
    <version>1.1</version>
</dependency>
```

## 2. 功能测试

### 2.1 代码执行

```java
package com.zmqx.chatglm.web;

import cn.bugstack.chatglm.model.ChatCompletionRequest;
import cn.bugstack.chatglm.model.ChatCompletionSyncResponse;
import cn.bugstack.chatglm.model.Model;
import cn.bugstack.chatglm.model.Role;
import cn.bugstack.chatglm.session.OpenAiSession;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zmqx.chatglm.listner.ChatGMLEventSourceListener;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName: {@link ApiController}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/12/15 13:38
 * @Describes
 */

@Slf4j
@RestController
public class ApiController {

    private final OpenAiSession openAiSession;

    public ApiController(OpenAiSession openAiSession) {
        this.openAiSession = openAiSession;
    }

    @GetMapping("/completions")
    public SseEmitter completions(String content, HttpServletResponse response) throws JsonProcessingException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        SseEmitter emitter = new SseEmitter();
        ChatCompletionRequest request = getRequest(content);
        // 请求
        openAiSession.completions(request, new ChatGMLEventSourceListener(emitter));
        return emitter;
    }

    @PostMapping("/completions/future")
    public String completionsFuture() throws InterruptedException, ExecutionException {
        // 入参；模型、请求信息
        ChatCompletionRequest request = getRequest("写个java冒泡排序");

        CompletableFuture<String> future = openAiSession.completions(request);
        return future.get();
    }

    @PostMapping("/completions/sync")
    public String completionsSync() throws IOException {
        // 入参；模型、请求信息
        ChatCompletionRequest request = getRequest("写个java冒泡排序");

        ChatCompletionSyncResponse response = openAiSession.completionsSync(request);
        return JSON.toJSONString(response);
    }

    @NotNull
    private ChatCompletionRequest getRequest(String content) {
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(Model.CHATGLM_TURBO);
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Role.user.getCode())
                        .content(content)
                        .build());
            }

        });
        return request;
    }
}

@Slf4j
public class ChatGMLEventSourceListener extends EventSourceListener {

    private final SseEmitter emitter;

    public ChatGMLEventSourceListener(SseEmitter emitter) {
        this.emitter = emitter;
    }

    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        ChatCompletionResponse response = JSON.parseObject(data, ChatCompletionResponse.class);
//                httpServerResponse.send
        System.out.println(response.getData());
        emitter.send(response.getData());
        if (EventType.finish.getCode().equals(type)) {
            ChatCompletionResponse.Meta meta = JSON.parseObject(response.getMeta(), ChatCompletionResponse.Meta.class);
            log.info("[输出结束] Tokens {}", JSON.toJSONString(meta));
        }
    }

    @SneakyThrows
    @Override
    public void onClosed(EventSource eventSource) {
        log.info("对话完成");
        emitter.send("对话完成");
        emitter.complete();
    }
}
```

- 这是一个单元测试类，也是最常使用的流式对话模式。

### 2.2 页面测试

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Receive Server Sent Events</title>
</head>
<body>
<ul id="message">
</ul>

<input id="content" type="text">
<button type="submit" onclick="submitContent()">提交</button>

<script>
    // 获取 ul 元素
    let ulElement = document.querySelector('ul');

    function submitContent() {
        let content = document.getElementById("content").value;

        // 创建 li 元素
        let liElement = document.createElement('li');

        // 设置 li 的文本内容
        liElement.textContent = content;

        // 将 li 元素追加到 ul 中
        ulElement.appendChild(liElement);

        // 创建一个 EventSource 对象
        let eventSource = new EventSource('http://127.0.0.1:8080/api/completions?content=' + content); // 指定服务器端的 SSE URL

        eventSource.onopen = function () {
            console.log('Connection opened');
        };

        eventSource.onerror = function (e) {
            eventSource.close();
            console.log('Error occurred', e);
        };

        eventSource.onmessage = function (e) {
            // console.log(e);
            // 创建 li 元素
            let liElement = document.createElement('li');

            // 设置 li 的文本内容
            liElement.textContent = e.data;

            // 将 li 元素追加到 ul 中
            ulElement.appendChild(liElement);
            if (e.data == "对话完成") {
                eventSource.close();
            }
        }
    }
</script>
</body>
</html>
```

- 运行后你会获得一个 Token 信息，之后在 curl.sh 中替换  Authorization: Bearer 后面的值。就可以执行测试了。

## 3. 程序接入

SpringBoot 配置类

```java
@Configuration
@EnableConfigurationProperties(ChatGLMSDKConfigProperties.class)
public class ChatGLMSDKConfig {

    @Bean
    @ConditionalOnProperty(value = "chatglm.config.enabled", havingValue = "true", matchIfMissing = false)
    public OpenAiSession openAiSession(ChatGLMSDKConfigProperties properties) {
        // 1. 配置文件
        cn.bugstack.chatglm.session.Configuration configuration = new cn.bugstack.chatglm.session.Configuration();
        configuration.setApiHost(properties.getApiHost());
        configuration.setApiSecretKey(properties.getApiSecretKey());

        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);

        // 3. 开启会话
        return factory.openSession();
    }

}

@Data
@ConfigurationProperties(prefix = "chatglm.config", ignoreInvalidFields = true)
public class ChatGLMSDKConfigProperties {

    /** 状态；open = 开启、close 关闭 */
    private boolean enable;
    /** 转发地址 */
    private String apiHost;
    /** 可以申请 sk-*** */
    private String apiSecretKey;

}
```

```java
@Autowired(required = false)
private OpenAiSession openAiSession;
```

- 注意：如果你在服务中配置了关闭启动 ChatGLM SDK 那么注入 openAiSession 为 null

yml 配置

```pom
# ChatGLM SDK Config
chatglm:
    config:
      # 状态；true = 开启、false 关闭
      enabled: false
      # 官网地址 
      api-host: https://open.bigmodel.cn/
      # 官网申请 https://open.bigmodel.cn/usercenter/apikeys
      api-secret-key: 4e087e4135306ef4a676f0cce3cee560.sgP2DUs*****
```