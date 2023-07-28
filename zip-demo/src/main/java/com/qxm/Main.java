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