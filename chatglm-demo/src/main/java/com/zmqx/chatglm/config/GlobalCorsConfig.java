package com.zmqx.chatglm.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: {@link GlobalCorsConfig}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/12/15 16:32
 * @Describes
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public FilterRegistrationBean corsFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configurationSource()));
        return bean;
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = corsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private static CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> allowedHeaders = Arrays.asList("x-auth-token", "content-type", "X-Requested-With", "XMLHttpRequest", "Authorization");
        List<String> exposedHeaders = Arrays.asList("x-auth-token", "content-type", "X-Requested-With", "XMLHttpRequest", "Authorization");
        List<String> allowedMethods = Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS");
        List<String> allowedOrigins = Arrays.asList("*");
        corsConfiguration.setAllowedHeaders(allowedHeaders);
        corsConfiguration.setExposedHeaders(exposedHeaders);
        corsConfiguration.setAllowedMethods(allowedMethods);
        corsConfiguration.setAllowedOrigins(allowedOrigins);
        corsConfiguration.setMaxAge(36000L);
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
}
