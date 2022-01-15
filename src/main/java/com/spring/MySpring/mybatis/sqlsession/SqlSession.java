package com.spring.MySpring.mybatis.sqlsession;

//封装数据库操作
public interface SqlSession<T> {
    //根据传入参数查询结果
    T selectOne(String statement,Object parameter);
    //获得Mapper
    T getMapper(Class clazz);
}
