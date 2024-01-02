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

package com.zmqx.chatglm.web;

import cn.bugstack.chatglm.model.ChatCompletionRequest;
import cn.bugstack.chatglm.model.ChatCompletionSyncResponse;
import cn.bugstack.chatglm.model.Model;
import cn.bugstack.chatglm.model.Role;
import cn.bugstack.chatglm.session.OpenAiSession;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zmqx.chatglm.dto.RequestDTO;
import com.zmqx.chatglm.listner.ChatGMLEventSourceListener;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName: {@link ApiController}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/12/15 13:38
 * @Describes
 */

@Slf4j
@RestController
public class ApiController {

    private final OpenAiSession openAiSession;

    public ApiController(OpenAiSession openAiSession) {
        this.openAiSession = openAiSession;
    }

    @PostMapping("/completions")
    public SseEmitter completions(@RequestBody RequestDTO dto, HttpServletResponse response) throws JsonProcessingException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        SseEmitter emitter = new SseEmitter();
        ChatCompletionRequest request = getRequest(dto.getContent());
        // 请求
        openAiSession.completions(request, new ChatGMLEventSourceListener(emitter));
        return emitter;
    }

    @PostMapping("/completions/future")
    public String completionsFuture() throws InterruptedException, ExecutionException {
        // 入参；模型、请求信息
        ChatCompletionRequest request = getRequest("写个java冒泡排序");

        CompletableFuture<String> future = openAiSession.completions(request);
        return future.get();
    }

    @PostMapping("/completions/sync")
    public String completionsSync() throws IOException {
        // 入参；模型、请求信息
        ChatCompletionRequest request = getRequest("写个java冒泡排序");

        ChatCompletionSyncResponse response = openAiSession.completionsSync(request);
        return JSON.toJSONString(response);
    }

    @NotNull
    private ChatCompletionRequest getRequest(String content) {
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(Model.CHATGLM_TURBO);
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Role.user.getCode())
                        .content(content)
                        .build());
            }

        });
        return request;
    }
}
