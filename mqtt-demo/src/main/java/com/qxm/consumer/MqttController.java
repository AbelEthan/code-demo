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

package com.qxm.consumer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: {@link MqttController}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/28 14:42
 * @Describes
 */
@RestController
public class MqttController {

    final private MqttConsumerConfig consumerConfig;
    final private MqttConsumerProperties properties;

    public MqttController(MqttConsumerConfig consumerConfig, MqttConsumerProperties properties) {
        this.consumerConfig = consumerConfig;
        this.properties = properties;
    }

    @PostMapping("/connect")
    public String connect(){
        consumerConfig.connect();
        return properties.getClientId() + "连接到服务器";
    }

    @PostMapping("/disConnect")
    public String disConnect(){
        consumerConfig.disConnect();
        return properties.getClientId() + "与服务器断开链接";
    }
}
