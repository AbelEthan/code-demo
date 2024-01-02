/*
 * Copyright (c) 2023 AbelEthan Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
