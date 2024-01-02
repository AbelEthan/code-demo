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

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: {@link ${NAME}}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date ${DATE} ${TIME}
 * @Describes
 */
public class Main {
    public static void main(String[] args) {
        //读取学生数据
        JSONArray studentArray = JSONUtil
                .readJSONArray(new File("D:\\Users\\Administrator\\Desktop\\manage_student_baseinfo.json"), StandardCharsets.UTF_8);
        List<StudentBO> boList = studentArray.toList(StudentBO.class);

        //读取行政区划码
        JSONArray codeArray = JSONUtil
                .readJSONArray(new File("D:\\Users\\Administrator\\Desktop\\name_varchar.json"), StandardCharsets.UTF_8);
        List<NameBO> codeList = codeArray.toList(NameBO.class);

        List<String> codeNameList = codeList.stream().map(NameBO::getName).collect(Collectors.toList());

        List<StudentBO> errorList = new ArrayList<>();
        boList.stream().forEach(bo -> {
            if (codeNameList.contains(bo.getHomeAddress())) {
                errorList.add(bo);
            }
        });
        EasyExcel.write("test.xlsx").sheet().doWrite(errorList);
    }
}