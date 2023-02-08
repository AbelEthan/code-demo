package com.qxm;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @ClassName: {@link IDemoWebService}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/8 15:26
 * @Description webservice接口
 */
@WebService
public interface IDemoWebService {
    /**
     * 非自定义参数方式
     *
     * @param param1
     * @param param2
     * @param param3
     * @return
     */
    @WebMethod
    String notCustomParam(
            @WebParam(targetNamespace = "http://qxm.com/") String param1,
            @WebParam(targetNamespace = "http://qxm.com/") String param2,
            @WebParam(targetNamespace = "http://qxm.com/") String param3
    );


    /**
     * 自定义参数方式
     *
     * @param param1
     * @param param2
     * @param param3
     * @return
     */
    @WebMethod
    String customParam(
            @WebParam(name = "param1", targetNamespace = "http://qxm.com/") String param1,
            @WebParam(name = "param2", targetNamespace = "http://qxm.com/") String param2,
            @WebParam(name = "param3", targetNamespace = "http://qxm.com/") String param3
    );
}
