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

package com.qxm.demo1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName: {@link WebSocketServer}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/9 11:46
 * @Description
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int ONLINE_COUNT = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> WEB_SOCKET_SET = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private String id = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        WEB_SOCKET_SET.add(this);
        addOnlineCount();
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        this.id = id;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    @OnClose
    public void onClose() {
        WEB_SOCKET_SET.remove(this);
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        for (WebSocketServer server : WEB_SOCKET_SET) {
            try {
                server.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("发生错误");
        throwable.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     *
     * @param message
     * @param id
     */
    public static void sendInfo(String message, @PathParam("id") String id) {
        log.info("推送消息到窗口" + id + "，推送内容:" + message);
        for (WebSocketServer server : WEB_SOCKET_SET) {
            try {
                if (id == null) {
                    server.sendMessage(message);
                } else if (server.id.equals(id)) {
                    server.sendMessage(message);
                }
            }catch (IOException e){
                continue;
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return ONLINE_COUNT;
    }

    private static synchronized void addOnlineCount() {
        ONLINE_COUNT++;
    }

    private static synchronized void subOnlineCount() {
        ONLINE_COUNT--;
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WebSocketServer that = (WebSocketServer) obj;
        return Objects.equals(session, that.session) && Objects.equals(id, that.id);
    }
}
