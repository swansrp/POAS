package com.srct.ril.poas.dbconfig;

import javax.sql.DataSource;  
  
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;  
import org.springframework.boot.context.properties.ConfigurationProperties;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.Primary;  
  
@Configuration  
public class DataSourceConfig {  
    @Bean(name = "configMasterDS")  
    @Primary //主数据库  
    @ConfigurationProperties(prefix = "spring.datasource.config") // application.properteis中对应属性的前缀  
    public DataSource userDataSource() {  
        return DataSourceBuilder.create().build();  
    }
      
    @Bean(name = "mboxMasterDS")  
    @ConfigurationProperties(prefix = "spring.datasource.great") // application.properteis中对应属性的前缀  
    public DataSource greatDataSource() {  
        return DataSourceBuilder.create().build();  
    }  
      
    @Bean(name = "riskMasterDS")  
    @ConfigurationProperties(prefix = "spring.datasource.dream") // application.properteis中对应属性的前缀  
    public DataSource dreamDataSource() {  
        return DataSourceBuilder.create().build();  
    }  
      
}  
