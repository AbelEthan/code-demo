package com.qxm;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @ClassName: {@link WebServiceConfig}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/8 15:34
 * @Description
 */
@Configuration
public class WebServiceConfig {
    @Autowired
    private IDemoWebService demoWebService;

    @Autowired
    private Bus bus;

    @Bean
    public ServletRegistrationBean cxfServlet(){
        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
    }

    @Bean
    public Endpoint demoWebserviceEndPoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, demoWebService);
        endpoint.publish("/demo");
        return endpoint;
    }
}
