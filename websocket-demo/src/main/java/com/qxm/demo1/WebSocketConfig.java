package com.qxm.demo1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName: {@link WebSocketConfig}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/9 11:45
 * @Description
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
