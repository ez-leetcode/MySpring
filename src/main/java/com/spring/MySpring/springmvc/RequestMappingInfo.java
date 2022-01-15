package com.spring.MySpring.springmvc;

import java.lang.reflect.Method;

public class RequestMappingInfo {

    private Method method;

    private Object object;

    private String url;

    public RequestMappingInfo(){

    }
    public RequestMappingInfo(Method method, Object object, String url) {
        this.method = method;
        this.object = object;
        this.url = url;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    public String getUrl() {
        return url;
    }
}
