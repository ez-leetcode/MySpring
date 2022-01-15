package com.spring.MySpring.mybatis.factory;


import com.spring.MySpring.mybatis.configuration.Configuration;
import com.spring.MySpring.mybatis.configuration.MappedStatement;
import com.spring.MySpring.mybatis.sqlsession.MySqlSession;
import com.spring.MySpring.mybatis.sqlsession.SqlSession;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySqlSessionFactory implements SqlSessionFactory {

    //配置类信息  单例
    private final Configuration configuration = new Configuration();

    //xml 文件存放位置
    private static final String MAPPER_LOCATION = "mappers";

    //数据库信息存放位置
    private static final String CONFIG_FILE = "mybatis.properties";

    public MySqlSessionFactory(){
        loadDbInfo();
        loadMapperInfo();
    }

    public SqlSession openSession(){
        return new MySqlSession(configuration);
    }

    //加载从db信息
    private void loadDbInfo(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
        Properties properties = new Properties();
        try{
            properties.load(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        //配置信息写入
        configuration.setJdbcDriver(properties.getProperty("jdbc_driver"));
        configuration.setJdbcUrl(properties.getProperty("jdbc_url"));
        configuration.setJdbcUsername(properties.getProperty("jdbc_username"));
        configuration.setJdbcPassword(properties.getProperty("jdbc_password"));
    }

    @SuppressWarnings("all")
    public void loadMapperInfo(){
        URL resource = this.getClass().getClassLoader().getResource(MAPPER_LOCATION);
        File file = new File(resource.getFile());
        //读取mapper文件夹下的信息
        //是目录
        if(file.isDirectory()){
            File [] files = file.listFiles();
            for(File f:files){
                loadMapperFileInfo(f);
            }
        }
    }

    //根据文件加载单个Mapper信息
    @SuppressWarnings("all")
    public void loadMapperFileInfo(File file){
        SAXReader reader = new SAXReader();
        Document document = null;
        try{
            document = reader.read(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        //获取根节点元素对象<mapper>
        Element element = document.getRootElement();
        //获取命名空间namespace
        String namespace = element.attributeValue("namespace");
        //获取操作列表
        List<Element> selectList = element.elements("select");
        List<Element> updateList = element.elements("update");
        List<Element> insertList = element.elements("insert");
        List<Element> deleteList = element.elements("delete");
        List<Element> allSqlList = new ArrayList<>();
        allSqlList.addAll(selectList);
        allSqlList.addAll(updateList);
        allSqlList.addAll(insertList);
        allSqlList.addAll(deleteList);
        //遍历list把信息封装
        for(Element e:allSqlList){
            MappedStatement mappedStatement = new MappedStatement();
            String id = e.attribute("id").getData().toString();
            String resultType = e.attribute("resultType").getData().toString();
            String sql = e.getData().toString();
            mappedStatement.setId(namespace + "." + id);
            mappedStatement.setNamespace(namespace);
            mappedStatement.setSql(sql);
            mappedStatement.setResultType(resultType);
            configuration.getMappedStatementMap().put(namespace + "." + id,mappedStatement);
        }
    }

}
