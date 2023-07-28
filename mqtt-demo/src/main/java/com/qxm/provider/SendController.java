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
