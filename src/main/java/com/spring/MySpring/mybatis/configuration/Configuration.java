package com.spring.MySpring.mybatis.configuration;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcPassword;
    private String jdbcUsername;

    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }
}
