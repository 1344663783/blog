package utils;

import com.alibaba.fastjson.JSON;
import com.sun.jersey.core.header.InBoundHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 获取当前请求上下文Request对象相关信息
 */
public class CurrentHttpServletRequestHelper {
    public static HttpServletRequest getRequest() throws Exception {
        try {
            ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            if (servletRequestAttributes != null) {
                return servletRequestAttributes.getRequest();
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static String getHeader(String name) throws Exception {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getHeader(name);
        } else {
            return null;
        }
    }

    private static InBoundHeaders getHeaders(HttpServletRequest request) {
        InBoundHeaders rh = new InBoundHeaders();

        for (Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements(); ) {
            String name = names.nextElement();
            List<String> valueList = new LinkedList<String>();
            for (Enumeration<String> values = request.getHeaders(name); values.hasMoreElements(); ) {
                valueList.add(values.nextElement());
            }
            rh.put(name, valueList);
        }

        return rh;
    }

    /**
     * 打印请求信息
     *
     * @return 请求信息
     * @throws Exception
     */
    public static String dumpRequest() {
        HttpServletRequest request = null;
        try {
            request = CurrentHttpServletRequestHelper.getRequest();

            Map<String, Object> requestMap = new LinkedHashMap<String, Object>();
            requestMap.put("method", request.getMethod());
            requestMap.put("url", request.getRequestURL());
            requestMap.put("query", request.getQueryString());
            requestMap.put("headers", getHeaders(request));
//            if ((request.getContentLength() > 0)) {
//                requestMap.put("body", IOUtils.toString(request.getReader()));
//            }

            return JSON.toJSONString(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
