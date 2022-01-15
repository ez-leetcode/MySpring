package com.spring.MySpring.springmvc;

import com.spring.MySpring.spring.BeanPostProcessor;

public interface HandlerMapping extends BeanPostProcessor {

    Object getHandler(String url);

}
