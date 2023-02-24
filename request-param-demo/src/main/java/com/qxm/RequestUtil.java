package com.qxm;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: {@link RequestUtil}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/23 17:35
 * @Description
 */
public class RequestUtil {

    /**
     * 获取body请求字符串
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        InputStream is = null;
        try {
            is = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 复制请求流
     *
     * @param inputStream
     * @return
     */
    public static InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        Map<String, String> map = new HashMap<>();
        if (MediaType.APPLICATION_JSON_VALUE.equals(contentType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType)) {
            String body = getBodyString(request);
            if (!StringUtils.isEmpty(body)) {
                map = JSONObject.parseObject(body, Map.class);
            }
        } else {
            Map properties = request.getParameterMap();
            Iterator entries = properties.entrySet().iterator();
            Map.Entry entry;
            String name = "";
            String value = "";
            while (entries.hasNext()) {
                entry = (Map.Entry) entries.next();
                name = (String) entry.getKey();
                Object entryValue = entry.getValue();
                if (ObjectUtils.isEmpty(entryValue)){
                    value = "";
                }else if (entryValue instanceof String[]){
                    String[] values = (String[]) entryValue;
                    for (int i = 0; i < values.length; i++) {
                        value = values[i] + ",";
                    }
                    value = value.substring(0, value.length() - 1);
                }else {
                    value = entryValue.toString();
                }
                map.put(name, value);
            }
        }
        return map;
    }
}
