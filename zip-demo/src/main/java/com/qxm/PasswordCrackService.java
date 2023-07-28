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
