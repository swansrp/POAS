package com.srct.ril.poas.dao.dbconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.srct.ril.poas.service.config.ModelMapService;
  
  
@Configuration
public class DataSourceConfig {  
	
	private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Autowired
    private ModelMapService modelMapService;
	
	@Value("${my.db.config.url}")  
    private String dbIP;
	
	@Value("${my.db.config.port}")
	private String dbPort;
	
	@Value("${my.db.config.property}")
	private String dbProp;
	     
    @Value("${my.db.config.username}")  
    private String username;  
      
    @Value("${my.db.config.password}")  
    private String password;  
      
    @Value("${my.db.config.driver}")  
    private String driverClassName;  
	
    @Bean(name = "configMasterDS")  
    @Primary //主数据库  
    @ConfigurationProperties(prefix = "config.datasource") // application.properteis中对应属性的前缀  
    public DataSource configDataSource() {     
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
    	    	
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(configDataSource());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap<>(0);
        dsMap.put(DataSourceEnum.CONFIG, configDataSource());
        
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
        log.info("sqlSessionFactoryBean");
        return sqlSessionFactoryBean;
    }
    
    /**
     * Transaction manager platform transaction manager.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
    	log.info("transactionManager");
        return new DataSourceTransactionManager(dynamicDataSource());
    }
    
    public void updateDynamicDataSource() {

		//DataSourceContextHolder.setDB(DataSourceEnum.CONFIG);
    	List<String> modelDBNameList = modelMapService.getNameList();

		DynamicDataSource dds = (DynamicDataSource)dynamicDataSource();
		
        Map<Object, Object> dsMap = new HashMap<>(modelDBNameList.size()+1);
        dsMap.put(DataSourceEnum.CONFIG, configDataSource());
        for(String name : modelDBNameList) {
        	DruidDataSource datasource = new DruidDataSource();  
        	//jdbc:mysql://${my.db.config.url}:${my.db.config.port}/Configuration?${my.db.config.property}
            String dbUrl = "jdbc:mysql://" + dbIP + ":" + dbPort + "/" + name + "?" + dbProp;
            datasource.setUrl(dbUrl);  
            datasource.setUsername(username);  
            datasource.setPassword(password);  
            datasource.setDriverClassName(driverClassName);
        	dsMap.put(name, datasource);
        }
        dds.setTargetDataSources(dsMap);
        dds.afterPropertiesSet();

    }
    
    
    //http://blog.csdn.net/neosmith/article/details/61202084
    //https://github.com/helloworlde/SpringBoot-DynamicDataSource.git
    //http://blog.csdn.net/catoop/article/details/50564513
      
}  
