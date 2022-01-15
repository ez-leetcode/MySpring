package com.spring.MySpring.spring;

//所有bean都要统一经过这个beanPostProcessor
//可以在里面自定义所有bean的增强逻辑  可针对单个bean等等
@Component
public class MyBeanPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("bean初始化前");
        System.out.println("当前beanName：" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("AOP部分");
        return bean;
    }
}
