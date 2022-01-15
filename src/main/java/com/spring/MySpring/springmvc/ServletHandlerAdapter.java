package com.spring.MySpring.springmvc;

import com.spring.MySpring.spring.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ServletHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supportAdapter(Object handler) {
        return handler instanceof MyServlet;
    }

    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        ((MyServlet) handler).service(req, resp);
        return null;
    }
}
