package com.spring.MySpring.mybatis.sqlsession;

import com.spring.MySpring.mybatis.configuration.Configuration;
import com.spring.MySpring.mybatis.configuration.MappedStatement;
import com.spring.MySpring.mybatis.executor.Executor;
import com.spring.MySpring.mybatis.executor.MyExecutor;
import com.spring.MySpring.mybatis.proxy.MapperProxy;

import java.lang.reflect.Proxy;
import java.util.List;

public class MySqlSession implements SqlSession{

    private final Configuration configuration;

    private Executor executor;

    public MySqlSession(Configuration configuration){
        this.configuration = configuration;
        this.executor = new MyExecutor(configuration);
    }

    @Override
    public Object getMapper(Class clazz){
        MapperProxy mapperProxy = new MapperProxy(this);
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz},mapperProxy);
    }

    @Override
    public Object selectOne(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statement);
        for(String s:configuration.getMappedStatementMap().keySet()){
            System.out.println(s);
        }
        List result = executor.query(mappedStatement,parameter);
        if(result != null && result.size() > 1){
            throw new RuntimeException("to many result");
        }
        return result.get(0);
    }
}
