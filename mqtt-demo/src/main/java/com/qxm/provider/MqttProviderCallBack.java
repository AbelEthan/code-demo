package com.qxm.provider;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @ClassName: {@link MqttProviderCallBack}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/28 13:40
 * @Describes
 */
@Slf4j
public class MqttProviderCallBack implements MqttCallback {
    /**
     * 客户端断开链接的回调
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.error("Provider与服务器断开连接。{}", throwable.getMessage());
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
        log.info("Provider接收消息主题：{}", topic);
        log.info("Provider接收消息Qos：{}", message.getQos());
        log.info("Provider接收消息内容：{}", new String(message.getPayload()));
        log.info("Provider接收消息retained：{}", message.isRetained());
    }

    /**
     * 消息发布成功的回调
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.debug("Provider接收消息成功");
    }
}
