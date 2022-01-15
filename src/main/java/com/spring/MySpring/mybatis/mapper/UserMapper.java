package com.spring.MySpring.mybatis.mapper;

import com.spring.MySpring.mybatis.pojo.User;

public interface UserMapper {
    User selectUser(String username);
}
