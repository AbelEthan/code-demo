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

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: {@link PasswordCrackService
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/3/15 12:20
 * @Describes
 */
public interface PasswordCrackService {
    Integer THREAD_NUM = 5;

    String run(String source, String dest);

    default public List<List<String>> getShardingList(List<String> numberStr) {
        int num = numberStr.size() / THREAD_NUM;
        List<List<String>> dataList = new ArrayList<>();
        int index = 0;
        int temp = 0;
        while (temp < numberStr.size()) {
            temp = (index + 1) * num;
            dataList.add(numberStr.subList(index * num, temp > numberStr.size() ? numberStr.size() : temp));
            index++;
        }
        return dataList;
    }
}
