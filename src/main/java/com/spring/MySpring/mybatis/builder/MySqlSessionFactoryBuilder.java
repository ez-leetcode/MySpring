package com.spring.MySpring.mybatis.builder;

import com.spring.MySpring.mybatis.factory.MySqlSessionFactory;

public class MySqlSessionFactoryBuilder {

    public MySqlSessionFactory build(){
        return new MySqlSessionFactory();
    }
}
