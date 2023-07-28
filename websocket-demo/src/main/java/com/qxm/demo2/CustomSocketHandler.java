package com.qxm.demo2;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: {@link CustomSocketHandler}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/10 15:53
 * @Describes
 */
@Slf4j
public class CustomSocketHandler extends TextWebSocketHandler {

    public static ConcurrentHashMap<String, WebSocketSession> sessionPools = new ConcurrentHashMap<>();

    @SneakyThrows
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info("获取客户端消息：{}", message.getPayload());
        session.sendMessage(new TextMessage(String.format("收到业务：[%s]，发来的：[%s]",session.getAttributes().get("bid"), message.getPayload())));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String bid = session.getAttributes().get("bid").toString();
        sessionPools.put(bid, session);
        session.sendMessage(new TextMessage("欢迎链接到ws服务！"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("断开链接！");
        String bid = session.getAttributes().get("bid").toString();
        sessionPools.remove(bid);
    }

}
