package com.qxm.model;

import lombok.Data;

/**
 * @ClassName: {@link MqttProperties}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/7/28 11:51
 * @Describes
 */
@Data
public class MqttProperties {
    private String username;
    private String password;
    private String url;
    private String clientId;
    private String topic;
}
