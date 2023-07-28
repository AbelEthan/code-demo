package com.qxm.consumer;

import com.qxm.model.MqttProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: {@link MqttConsumerProperties}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/28 11:51
 * @Describes
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mqtt.consumer")
public class MqttConsumerProperties extends MqttProperties {
}
