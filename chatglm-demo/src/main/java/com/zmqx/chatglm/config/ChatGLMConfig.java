package com.zmqx.chatglm.config;

import cn.bugstack.chatglm.session.OpenAiSession;
import cn.bugstack.chatglm.session.OpenAiSessionFactory;
import cn.bugstack.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import com.zmqx.chatglm.properties.ChatGLMProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: {@link ChatGLMConfig}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/12/15 13:31
 * @Describes
 */
@Configuration
@EnableConfigurationProperties(ChatGLMProperties.class)
public class ChatGLMConfig {

    @Bean
    @ConditionalOnProperty(value = "chatglm.config.enabled", havingValue = "true", matchIfMissing = false)
    public OpenAiSession openAiSession(ChatGLMProperties properties) {
        // 1. 配置文件
        cn.bugstack.chatglm.session.Configuration configuration = new cn.bugstack.chatglm.session.Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("2487bbcb59ed914e4ac5984c107c46f0.sVyIfxAyyj0O0cki");
        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        // 3. 开启会话
        return factory.openSession();
    }

}
