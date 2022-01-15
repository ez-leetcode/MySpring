package com.spring.MySpring.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    //判断是否支持处理器
    boolean supportAdapter(Object handler);

    Object handle(HttpServletRequest req, HttpServletResponse resp,Object handler) throws Exception;
}
