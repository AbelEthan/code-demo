package com.zmqx.dahua.config;

import com.dahuatech.icc.exception.ClientException;
import com.dahuatech.icc.oauth.http.DefaultClient;
import com.dahuatech.icc.oauth.http.IClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: {@link DahuaConfig}
 * @Author: AbelEthan
 * @Email AbelEthan@vip.qq.com
 * @Date 2023/12/29 17:01
 * @Describes
 */
@Configuration
public class DahuaConfig {

    @Bean
    public IClient defaultClient() throws ClientException {
        return new DefaultClient();
    }
}
