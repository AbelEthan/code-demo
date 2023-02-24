package com.qxm;

import lombok.Data;

/**
 * @ClassName: {@link CustomDemo}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/24 10:06
 * @Description
 */
@Data
public class CustomDemo {
    private Long id;
    private String name;
    private Integer age;


    public CustomDemo() {
    }

    public CustomDemo(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
