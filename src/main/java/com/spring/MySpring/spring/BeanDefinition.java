package com.spring.MySpring.spring;

public class BeanDefinition {

    private Class<?> className;

    private String scope;

    public BeanDefinition(){};
    public BeanDefinition(Class<?> className, String scope) {
        this.className = className;
        this.scope = scope;
    }

    public Class<?> getClassName() {
        return className;
    }

    public String getScope() {
        return scope;
    }

    public void setClassName(Class<?> className) {
        this.className = className;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
