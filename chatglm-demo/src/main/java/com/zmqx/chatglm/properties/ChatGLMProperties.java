package com.zmqx.chatglm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName: {@link ChatGLMProperties}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/12/15 13:32
 * @Describes
 */
@Data
@ConfigurationProperties("chatglm.config")
public class ChatGLMProperties {
    private Boolean enabled;
    private String apiHost;
    private String apiSecretKey;
}
