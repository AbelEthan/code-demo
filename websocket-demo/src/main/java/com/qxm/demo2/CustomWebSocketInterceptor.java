package com.qxm.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @ClassName: {@link CustomWebSocketInterceptor}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/10 15:53
 * @Describes
 */
@Slf4j
@Component
public class CustomWebSocketInterceptor implements HandshakeInterceptor {
    /**
     * 握手前，返回false，则不建立连接
     *
     * @param request    the current request
     * @param response   the current response
     * @param wsHandler  the target WebSocket handler
     * @param attributes attributes from the HTTP handshake to associate with the WebSocket
     *                   session; the provided attributes are copied, the original map is not used.
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        String bid = serverHttpRequest.getServletRequest().getParameter("bid");
        attributes.put("bid", bid);
        if (!"bid".equals(bid)) {
            log.info("握手失败!");
            return false;
        }
        log.info("开始握手-");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("握手成功-");
    }
}
