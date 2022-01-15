package com.spring.MySpring.springmvc;

import com.spring.MySpring.spring.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

//处理器映射器   维护url和处理器之间的映射关系
@Component
public class BeanHandlerMapping implements HandlerMapping{

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
        return null;
    }
}
