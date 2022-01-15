package com.spring.MySpring.mybatis.util;

import com.spring.MySpring.mybatis.pojo.User;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class ReflectionUtil {

    public static void setBeanFromResultSet(Object o, ResultSet resultSet) throws Exception{
        //System.out.println(resultSet.getString(""));
        Field[] fields = o.getClass().getDeclaredFields();
        //  field???
        //把对象的所有字段遍历逐个设置属性
        for(Field field:fields){
            switch (field.getType().getSimpleName()) {
                case "String":
                    setProToBean(o, field.getName(), resultSet.getString(field.getName()));
                    break;
                case "Integer":
                case "int":
                    setProToBean(o, field.getName(), resultSet.getInt(field.getName()));
                    break;
                case "Long":
                    setProToBean(o, field.getName(), resultSet.getLong(field.getName()));
                    break;
            }
        }
    }

    private static void setProToBean(Object o,String name,Object value){
        Field field;
        try {
            field = o.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(o,value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
