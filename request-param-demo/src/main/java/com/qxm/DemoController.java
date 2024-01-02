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

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: {@link DemoController}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/24 9:34
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/list")
    public ResponseEntity<List<CustomDemo>> getList(@RequestParam(required = false) Map map) {
        log.info("请求map：{}", map);
        List<CustomDemo> list = new ArrayList<>();
        CustomDemo demo = new CustomDemo(1L, "HELLO", 18);
        list.add(demo);
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<CustomDemo> get(@RequestParam("id") Long id) {
        log.info("请求id:{}", id);
        CustomDemo demo = new CustomDemo(1L, "HELLO", 18);
        return ResponseEntity.ok(demo);
    }

    @PostMapping
    public ResponseEntity<Long> post(@RequestBody CustomDemo demo) {
        log.info("请求demo：{}", demo);
        return ResponseEntity.ok(1L);
    }

    @PutMapping
    public ResponseEntity<String> put(@RequestBody CustomDemo demo) {
        log.info("请求demo：{}", demo);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        log.info("请求id:{}", id);
        return ResponseEntity.ok("success");
    }

}
