package com.qxm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName: {@link CustomWebConfiguration}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/24 9:29
 * @Description
 */
@Configuration
public class CustomWebConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private CustomInterceptorHandler customInterceptorHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(customInterceptorHandler);
        super.addInterceptors(registry);
    }
}
