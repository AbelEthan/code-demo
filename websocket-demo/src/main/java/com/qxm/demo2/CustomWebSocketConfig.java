package com.qxm.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @ClassName: {@link CustomWebSocketConfig}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/10 15:52
 * @Describes
 */
@Configuration
@EnableWebSocket
public class CustomWebSocketConfig implements WebSocketConfigurer {

    private final CustomWebSocketInterceptor customWebSocketInterceptor;

    public CustomWebSocketConfig(CustomWebSocketInterceptor customWebSocketInterceptor) {
        this.customWebSocketInterceptor = customWebSocketInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketHandler(), "/websocket2")
                .setAllowedOrigins("*")
                .addInterceptors(customWebSocketInterceptor);

    }

    @Bean
    public CustomSocketHandler webSocketHandler() {
        return new CustomSocketHandler();
    }
}
