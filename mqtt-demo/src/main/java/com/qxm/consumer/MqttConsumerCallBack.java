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

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @ClassName: {@link MqttConsumerCallBack}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/28 13:40
 * @Describes
 */
@Slf4j
public class MqttConsumerCallBack implements MqttCallback {
    /**
     * 客户端断开链接的回调
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.error("Consumer与服务器断开连接。{}", throwable.getMessage());
    }

    /**
     * 消息到达的回调
     *
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("Consumer接收消息主题：{}", topic);
        log.info("Consumer接收消息Qos：{}", message.getQos());
        log.info("Consumer接收消息内容：{}", new String(message.getPayload()));
        log.info("Consumer接收消息retained：{}", message.isRetained());
    }

    /**
     * 消息发布成功的回调
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.debug("Consumer接收消息成功");
    }
}
