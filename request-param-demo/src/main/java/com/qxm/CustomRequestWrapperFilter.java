package com.qxm;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: {@link CustomRequestWrapperFilter}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/24 9:12
 * @Description
 */
@Component
public class CustomRequestWrapperFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.getParameterMap();
        request = new CustomRequestWrapper(request);
        filterChain.doFilter(request, response);
    }
}
