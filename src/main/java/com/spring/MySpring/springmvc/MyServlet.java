package com.spring.MySpring.springmvc;

import com.spring.MySpring.spring.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class MyServlet extends HttpServlet {

    static List<HandlerMapping> handlerMappingList;

    static List<HandlerAdapter> handlerAdapterList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("我是鼠");
        Object handler = getHandlerMapping(req);
        HandlerAdapter handlerAdapter = (HandlerAdapter) getHandlerAdapter(handler);
        Object result = null;
        try{
            result = handlerAdapter.handle(req,resp,handler);
        }catch (Exception e){
            e.printStackTrace();
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(result);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //Spring容器
        MyApplicationContext context = new MyApplicationContext(MyApplicationContext.class);
        List<Object> l = context.getBeanByInterface(HandlerMapping.class);
        for(Object s:l){
            handlerMappingList.add((HandlerMapping) s);
        }
        //把url读到map里
    }

    private Object getHandlerMapping(HttpServletRequest req){
        for(HandlerMapping handlerMapping:handlerMappingList){
            Object o = handlerMapping.getHandler(req.getRequestURI());
            return o;
        }
        return null;
    }

    private Object getHandlerAdapter(Object o){
        for(HandlerAdapter handlerAdapter:handlerAdapterList){
            if(handlerAdapter.supportAdapter(o)){
                return o;
            }
        }
        return null;
    }
}
