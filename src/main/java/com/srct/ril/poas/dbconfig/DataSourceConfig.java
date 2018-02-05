package com.srct.ril.poas.dbconfig;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
  
  
@Configuration  
public class DataSourceConfig {  
	
	public static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
	
    @Bean(name = "configMasterDS")  
    @Primary //主数据库  
    @ConfigurationProperties(prefix = "config.datasource") // application.properteis中对应属性的前缀  
    public DataSource configDataSource() {  
        return DataSourceBuilder.create().build();  
    }
      
    @Bean(name = "greatSlaveDS")  
    @ConfigurationProperties(prefix = "great.datasource") // application.properteis中对应属性的前缀  
    public DataSource greatDataSource() {  
        return DataSourceBuilder.create().build();  
    }  
      
    @Bean(name = "dreamSlaveDS")  
    @ConfigurationProperties(prefix = "dream.datasource") // application.properteis中对应属性的前缀  
    public DataSource dreamDataSource() {  
        return DataSourceBuilder.create().build();  
    }
    
    @Bean(name = "dynamicConfigDS")
    public DataSource initDataSource(@Value("${my.db.config.dbcount}") int dbCount) {
    	
    	log.info("============切换到{}数据源=================", dbCount);
    	
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(configDataSource());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap<>(dbCount);
        dsMap.put(DataSourceEnum.CONFIG, configDataSource());
        dsMap.put(DataSourceEnum.GREAT, greatDataSource());
        dsMap.put(DataSourceEnum.DREAM, dreamDataSource());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }
    //http://blog.csdn.net/neosmith/article/details/61202084
      
}  
