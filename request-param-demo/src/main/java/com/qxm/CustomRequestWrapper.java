package com.qxm;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName: {@link CustomRequestWrapper}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/24 9:07
 * @Description
 */
public class CustomRequestWrapper extends HttpServletRequestWrapper {

    private final String body;


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public CustomRequestWrapper(HttpServletRequest request) {
        super(request);
        this.body = RequestUtil.getBodyString(request);
    }

    @Override
    public ServletInputStream getInputStream(){
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());

        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return body;
    }
}
