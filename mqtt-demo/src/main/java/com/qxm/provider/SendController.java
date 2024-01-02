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

package com.qxm.provider;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: {@link SendController}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/28 14:14
 * @Describes
 */
@RestController
public class SendController {

    final private MqttProviderConfig providerConfig;

    public SendController(MqttProviderConfig providerConfig) {
        this.providerConfig = providerConfig;
    }

    @PostMapping("/send/message")
    public String sendMessage(int qos, boolean retained, String topic, String message) {
        try {
            providerConfig.publish(qos, retained, topic, message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }
    }
}
