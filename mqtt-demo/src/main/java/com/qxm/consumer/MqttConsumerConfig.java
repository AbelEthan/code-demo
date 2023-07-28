package com.qxm.consumer;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * @ClassName: {@link MqttConsumerConfig}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/28 11:47
 * @Describes
 */
@Order(1)
@Slf4j
@Configuration
public class MqttConsumerConfig {

    final private MqttConsumerProperties mqttProperties;

    private MqttClient client;

    public MqttConsumerConfig(MqttConsumerProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    /**
     * 客户端链接服务端
     */
    @PostConstruct
    public void connect() {
        try {
            //创建MQTT客户端对象
            client = new MqttClient(mqttProperties.getUrl(), mqttProperties.getClientId(), new MemoryPersistence());
            //链接设置
            MqttConnectOptions options = new MqttConnectOptions();
            //是否清空session，设置false表示服务器会保留客户端的连接记录（订阅主题，qos）,客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
            //设置为true表示每次连接服务器都是以新的身份
            options.setCleanSession(true);
            //设置连接用户名
            options.setUserName(mqttProperties.getUsername());
            //设置连接密码
            options.setPassword(mqttProperties.getPassword().toCharArray());
            //设置超时时间，单位为秒
            options.setConnectionTimeout(100);
            //设置心跳时间 单位为秒，表示服务器每隔 1.5*20秒的时间向客户端发送心跳判断客户端是否在线
            options.setKeepAliveInterval(20);
            //设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
            options.setWill("willTopic", (mqttProperties.getClientId() + "与服务器断开链接").getBytes(), 0, false);
            //设置回调
            client.setCallback(new MqttConsumerCallBack());
            client.connect(options);
            //订阅主题
            //消息等级，和主题数组一一对应，服务端将按照指定等级给订阅了主题的客户端推送消息
            int[] qos = {1, 1};
            //主题
            String[] topics = {"topic1", "topic2"};
            //订阅主题
            client.subscribe(topics, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开链接
     */
    public void disConnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
