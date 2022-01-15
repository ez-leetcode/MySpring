package com.spring.MySpring.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class AnnotationHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supportAdapter(Object handler) {
        return handler instanceof RequestMappingInfo;
    }

    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Map<String,String[]> paramMap = req.getParameterMap();
        //匹配参数列表
        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) handler;
        //获取方法
        Method method = requestMappingInfo.getMethod();
        //获取真实参数
        Parameter[] params = method.getParameters();
        Object[] value = new Object[params.length];
        for(int i = 0; i < params.length; i++){
            for(String s:paramMap.keySet()){
                if(s.equals(params[i].getAnnotation(RequestMapping.class).value())){
                    value[i] = paramMap.get(s)[0];
                }
            }
        }
        //????
        return method.invoke(requestMappingInfo.getObject(),value);
    }
}
