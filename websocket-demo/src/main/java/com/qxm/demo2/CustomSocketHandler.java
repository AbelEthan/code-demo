/*
 * Copyright (c) 2023 AbelEthan Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
