package com.spring.MySpring.mybatis.executor;

import com.spring.MySpring.mybatis.configuration.Configuration;
import com.spring.MySpring.mybatis.configuration.MappedStatement;
import com.spring.MySpring.mybatis.util.ReflectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyExecutor implements Executor{

    private final Configuration configuration;

    public MyExecutor(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Object parameter) {
        System.out.println(mappedStatement == null);
        List<T> result = new ArrayList<>();
        try {
            Class.forName(configuration.getJdbcDriver());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DriverManager.getConnection(configuration.getJdbcUrl(),configuration.getJdbcUsername(),configuration.getJdbcPassword());
            String regex = "#\\{([^}])*\\}";
            //sql预编译  把参数替换为？
            String realSql = mappedStatement.getSql().replaceAll(regex,"?");
            System.out.println(realSql);
            preparedStatement = con.prepareStatement(realSql);
            //处理占位符
            parameterSize(preparedStatement,parameter);
            resultSet = preparedStatement.executeQuery();
            //处理结果集
            handlerResultSet(resultSet,result,mappedStatement.getResultType());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                resultSet.close();
                preparedStatement.close();
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    private <T> void handlerResultSet(ResultSet resultSet,List<T> list,String resultType){
        //获取返回的class对象
        try{
            Class<T> clazz = (Class<T>)Class.forName(resultType);
            while(resultSet.next()){
                Object o = clazz.newInstance();
                System.out.println(o.getClass().getName());
                //把结果集的字段数据设置到o中
                ReflectionUtil.setBeanFromResultSet(o,resultSet);
                list.add((T)o);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parameterSize(PreparedStatement preparedStatement,Object parameter) throws SQLException{
        System.out.println(1);
        if(parameter instanceof Integer){
            //int类型
            preparedStatement.setInt(1,(Integer) parameter);
        }else if(parameter instanceof Long){
            preparedStatement.setLong(1,(Long) parameter);
        }else if(parameter instanceof String){
            preparedStatement.setString(1,(String) parameter);
        }
    }

}