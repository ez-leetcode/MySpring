package com.spring.MySpring.mybatis.proxy;

import com.spring.MySpring.mybatis.sqlsession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(args.length);
        return sqlSession.selectOne(method.getDeclaringClass().getName() + "." + method.getName(),args[0]);
    }

}
