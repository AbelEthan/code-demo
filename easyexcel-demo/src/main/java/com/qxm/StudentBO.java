package com.qxm;

import lombok.Data;

/**
 * @ClassName: {@link StudentBO}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/10/24 17:20
 * @Describes
 */
@Data
public class StudentBO {
    private Long id;
    private String studentName;
    private String studentNo;
    private String homeAddress;
}
