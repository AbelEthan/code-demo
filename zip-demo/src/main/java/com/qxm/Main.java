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

/**
 * @ClassName: {@link ${NAME}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date ${DATE} ${TIME}
 * @Describes
 */
public class Main {
    public static void main(String[] args) {
        PasswordCrackService service = new ThreadProcessPasswordCrackService();
        String source = "C:\\Users\\Administrator\\Downloads\\破解\\XMind 2021 v11.0.2.rar";
        String dest = "C:\\Users\\Administrator\\Downloads\\破解";
        service.run(source, dest);
    }
}