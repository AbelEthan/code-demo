package com.zmqx.chatglm.listner;

import cn.bugstack.chatglm.model.ChatCompletionResponse;
import cn.bugstack.chatglm.model.EventType;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @ClassName: {@link ChatGMLEventSourceListener}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/12/15 15:55
 * @Describes
 */
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
