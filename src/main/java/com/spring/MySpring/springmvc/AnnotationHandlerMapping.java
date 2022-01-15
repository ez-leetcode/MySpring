package com.spring.MySpring.springmvc;

import com.spring.MySpring.spring.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Component
public class AnnotationHandlerMapping implements HandlerMapping{

    //url  维护到map中
    static Map<String,Object> map = new HashMap<>();

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Method[] methods = bean.getClass().getMethods();
        for(Method method:methods){
            RequestMappingInfo info = getRequestMappingInfo(method,bean);
            map.put(info.getUrl(),info);
        }
        return bean;
    }

    //生成注解的   请求映射信息保存
    private RequestMappingInfo getRequestMappingInfo(Method method, Object object){
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
        if(method.isAnnotationPresent(RequestMapping.class)){
            requestMappingInfo.setObject(object);
            requestMappingInfo.setMethod(method);
            requestMappingInfo.setUrl(method.getDeclaredAnnotation(RequestMapping.class).value());
        }
        return requestMappingInfo;
    }
}
