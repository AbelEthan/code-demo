package com.qxm;

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @ClassName: {@link DemoWebService}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/8 15:32
 * @Description
 */
@Service
@WebService(name = "DemoWebService", targetNamespace = "http://qxm.com/", endpointInterface = "com.qxm.IDemoWebService")
public class DemoWebService implements IDemoWebService{
    @Override
    public String notCustomParam(String param1, String param2, String param3) {
        return "notCustomParam:" + param1 + param2 + param3;
    }

    @Override
    public String customParam(String param1, String param2, String param3) {
        return "customParam:" + param1 + param2 + param3;
    }
}
