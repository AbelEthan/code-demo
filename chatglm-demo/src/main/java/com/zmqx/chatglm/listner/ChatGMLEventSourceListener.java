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
        emitter.send("[DONE]");
        emitter.complete();
    }
}
