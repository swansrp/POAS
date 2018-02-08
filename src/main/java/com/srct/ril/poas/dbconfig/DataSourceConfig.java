package com.srct.ril.poas.dbconfig;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.srct.ril.poas.dao.mapper.ModelMapMapper;
  
  
@Configuration
@PropertySource(value = { "application.properties" })
public class DataSourceConfig {  
	
	public static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Value("${my.db.config.dbcount}")
	private int dbCount;
	
	@Autowired
	private ModelMapMapper modelMapDao;
	
    @Bean(name = "configMasterDS")  
    @Primary //主数据库  
    @ConfigurationProperties(prefix = "config.datasource") // application.properteis中对应属性的前缀  
    public DataSource configDataSource() {     
        return DruidDataSourceBuilder.create().build();
    }
      
    @Bean(name = "greatSlaveDS")  
    @ConfigurationProperties(prefix = "great.datasource") // application.properteis中对应属性的前缀  
    public DataSource greatDataSource() {  
        return DruidDataSourceBuilder.create().build();  
    }  
      
    @Bean(name = "dreamSlaveDS")  
    @ConfigurationProperties(prefix = "dream.datasource") // application.properteis中对应属性的前缀  
    public DataSource dreamDataSource() {  
        return DruidDataSourceBuilder.create().build();  
    }
    
    @Bean(name = "dynamicConfigDS")
    
    public DataSource dynamicDataSource() {
    	
    	log.info("============本地数据源一共{}个=================", dbCount);
    	
//    	ModelMapExample example = new ModelMapExample();
//    	dbCount = modelMapDao.countByExample(example);
    	
    	log.info("============远程数据源一共{}个=================", dbCount);
    	    	
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(configDataSource());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap<>(0);
        dsMap.put(DataSourceEnum.CONFIG, configDataSource());
        dsMap.put(DataSourceEnum.GREAT, greatDataSource());
        dsMap.put(DataSourceEnum.DREAM, dreamDataSource());      

        if(dsMap.size() != dbCount) {
        	log.error("Need check data source Map!!!!");
        }
        
        log.info("{}",((DataSource)dsMap.get(DataSourceEnum.CONFIG)).toString());
        
        
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }
    
    /**
     * Sql session factory bean.
     * Here to config datasource for SqlSessionFactory
     * <p>
     * You need to add @{@code @ConfigurationProperties(prefix = "mybatis")}, if you are using *.xml file,
     * the {@code 'mybatis.type-aliases-package'} and {@code 'mybatis.mapper-locations'} should be set in
     * {@code 'application.properties'} file, or there will appear invalid bond statement exception
     *
     * @return the sql session factory bean
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // Here is very important, if don't config this, will can't switch datasource
        // put all datasource into SqlSessionFactoryBean, then will autoconfig SqlSessionFactory
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        
        return sqlSessionFactoryBean;
    }
    
    /**
     * Transaction manager platform transaction manager.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
    
    
    //http://blog.csdn.net/neosmith/article/details/61202084
    //https://github.com/helloworlde/SpringBoot-DynamicDataSource.git
    //http://blog.csdn.net/catoop/article/details/50564513
      
}  
