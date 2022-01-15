package com.spring.MySpring.test;

import com.spring.MySpring.spring.Autowired;
import com.spring.MySpring.spring.BeanNameAware;
import com.spring.MySpring.spring.Component;
import com.spring.MySpring.spring.InitializingBean;

@Component("userServiceImpl")
public class UserServiceImpl implements UserService, BeanNameAware, InitializingBean {

    @Autowired
    private HelloService helloService;

    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //用户自定义的操作xxx
        //System.out.println("鼠鼠的操作");
    }

    public void test(){
        System.out.println("我是鼠鼠");
    }
}
