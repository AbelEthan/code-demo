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

import cn.hutool.core.util.StrUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: {@link ThreadProcessPasswordCrackService
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/3/15 12:21
 * @Describes
 */
public class ThreadProcessPasswordCrackService implements PasswordCrackService{
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadProcessPasswordCrackService() {
        this.threadPoolExecutor = new ThreadPoolExecutor(6, 20, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public String run(String source, String dest) {
        List<String> numberStr = StringUtil.getNumberStr(4);
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        numberStr.forEach(num -> queue.offer(num));
        long startTime = System.currentTimeMillis();
        while (!queue.isEmpty()) {
            threadPoolExecutor.execute(() -> {
                String name = Thread.currentThread().getName();
                String key = queue.poll();
                if (StrUtil.isNotBlank(key)) {
                    boolean result = UnZipUtil.unZip(source, dest, key);
                    if (result) {
                        try (FileWriter fileWriter = new FileWriter(dest + "\\password.txt")) {
                            fileWriter.write(key);
                            System.out.println("线程：" + name + ",密码是：" + key);
                            queue.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("线程：" + name + "," + key + "密码错误");
                    }
                }
            });
        }

        final long endTime = System.currentTimeMillis();
        System.out.println("共花费：" + (endTime - startTime) / 1000 + "秒");
        return null;
    }
}
