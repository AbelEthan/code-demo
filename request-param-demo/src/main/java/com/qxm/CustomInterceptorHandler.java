package com.qxm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: {@link CustomInterceptorHandler}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/24 9:15
 * @Description
 */
@Slf4j
@Component
public class CustomInterceptorHandler extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("请求地址：{}", requestURI);
        request.setAttribute("requestTime", new Date());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Date responseTime = new Date();
        Object requestTime = request.getAttribute("requestTime");
        long useTime = responseTime.getTime() - ((Date) requestTime).getTime();
        int status = response.getStatus();
        String method = request.getMethod();
        Map<String, String> parameterMap = RequestUtil.getParameterMap(request);
        log.info("请求方法：{},请求参数:{},请求时间：{},响应时间{},请求耗时：{},响应状态:{}", method, parameterMap, requestTime, responseTime, useTime, status);
    }
}
