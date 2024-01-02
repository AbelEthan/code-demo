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
