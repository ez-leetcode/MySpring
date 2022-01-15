package com.spring.MySpring.mybatis.executor;

import com.spring.MySpring.mybatis.configuration.MappedStatement;

import java.util.List;

public interface Executor {

    //封装sql语句的mappedStatement对象，查询并返回结果集
    <T> List<T> query(MappedStatement mappedStatement,Object parameter);
}
