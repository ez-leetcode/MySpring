package com.spring.MySpring.mybatis.configuration;

//xml配置信息加载到这里
public class MappedStatement {

    private String namespace;
    private String id;
    private String resultType;
    private String sql;

    public MappedStatement(){};

    public MappedStatement(String namespace, String id, String resultType, String sql) {
        this.namespace = namespace;
        this.id = id;
        this.resultType = resultType;
        this.sql = sql;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getId() {
        return id;
    }

    public String getResultType() {
        return resultType;
    }

    public String getSql() {
        return sql;
    }
}
