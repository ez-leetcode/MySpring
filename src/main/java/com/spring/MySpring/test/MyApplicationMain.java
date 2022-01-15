package com.spring.MySpring.test;

import com.spring.MySpring.mybatis.builder.MySqlSessionFactoryBuilder;
import com.spring.MySpring.mybatis.factory.MySqlSessionFactory;
import com.spring.MySpring.mybatis.mapper.UserMapper;
import com.spring.MySpring.mybatis.pojo.User;
import com.spring.MySpring.mybatis.sqlsession.SqlSession;

public class MyApplicationMain {
    public static void main(String[] args) {
        /*
        MyApplicationContext myApplicationContext = new MyApplicationContext(MyApplicationContext.class);
        //Object o1 = myApplicationContext.getBean("helloServiceImpl");
        //System.out.println(o1);

         */
        MySqlSessionFactory mySqlSessionFactory = new MySqlSessionFactoryBuilder().build();
        SqlSession sqlSession = mySqlSessionFactory.openSession();
        UserMapper userMapper = (UserMapper) sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectUser("ck");
        System.out.println(user.getEmail());
    }
}
