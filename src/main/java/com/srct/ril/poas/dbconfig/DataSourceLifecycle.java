package com.srct.ril.poas.dbconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.srct.ril.poas.service.ModelMapService;

@Component
public class DataSourceLifecycle implements SmartLifecycle {
	
	public static final Logger log = LoggerFactory.getLogger(SmartLifecycle.class);

	private boolean isRunning = false;
	@Autowired
    private ModelMapService modelMapService;
	@Autowired
	private DataSourceConfig dsc;
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
	
	
	/**
     * 1. 我们主要在该方法中启动任务或者其他异步服务，比如开启MQ接收消息<br/>
     * 2. 当上下文被刷新（所有对象已被实例化和初始化之后）时，将调用该方法，默认生命周期处理器将检查每个SmartLifecycle对象的isAutoStartup()方法返回的布尔值。
     * 如果为“true”，则该方法会被调用，而不是等待显式调用自己的start()方法。
     */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
		DataSourceContextHolder.setDB(DataSourceEnum.CONFIG);
		
		List<String> modelDBNameList = modelMapService.getNameList();
		log.info("========={}===========", modelDBNameList);

		DynamicDataSource dds = (DynamicDataSource)dsc.dynamicDataSource();
		
        Map<Object, Object> dsMap = new HashMap<>(modelDBNameList.size()+1);
        dsMap.put(DataSourceEnum.CONFIG, dsc.configDataSource());
        for(String name : modelDBNameList) {
        	DruidDataSource datasource = new DruidDataSource();  
        	//jdbc:mysql://${my.db.config.url}:${my.db.config.port}/Configuration?${my.db.config.property}
            String dbUrl = "jdbc:mysql://" + this.dbIP + ":" + this.dbPort + "/" + name + "?" + this.dbProp;
            datasource.setUrl(dbUrl);  
            datasource.setUsername(username);  
            datasource.setPassword(password);  
            datasource.setDriverClassName(driverClassName);
        	dsMap.put(name, datasource);
        }
        dds.setTargetDataSources(dsMap);
        dds.afterPropertiesSet();
        
		modelDBNameList = modelMapService.getNameList();
		log.info("========={}===========", modelDBNameList);
		isRunning = true;
	}

	/**
     * 接口Lifecycle的子类的方法，只有非SmartLifecycle的子类才会执行该方法。<br/>
     * 1. 该方法只对直接实现接口Lifecycle的类才起作用，对实现SmartLifecycle接口的类无效。<br/>
     * 2. 方法stop()和方法stop(Runnable callback)的区别只在于，后者是SmartLifecycle子类的专属。
     */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * 1. 只有该方法返回false时，start方法才会被执行。<br/>
     * 2. 只有该方法返回true时，stop(Runnable callback)或stop()方法才会被执行。
     */

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return isRunning;
	}

	/**
     * 如果工程中有多个实现接口SmartLifecycle的类，则这些类的start的执行顺序按getPhase方法返回值从小到大执行。<br/>
     * 例如：1比2先执行，-1比0先执行。 stop方法的执行顺序则相反，getPhase返回值较大类的stop方法先被调用，小的后被调用。
     */
	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
     * 根据该方法的返回值决定是否执行start方法。<br/> 
     * 返回true时start方法会被自动执行，返回false则不会。
     */
	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
     * SmartLifecycle子类的才有的方法，当isRunning方法返回true时，该方法才会被调用。
     */
	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
		// 如果你让isRunning返回true，需要执行stop这个方法，那么就不要忘记调用callback.run()。
        // 否则在你程序退出时，Spring的DefaultLifecycleProcessor会认为你这个TestSmartLifecycle没有stop完成，程序会一直卡着结束不了，等待一定时间（默认超时时间30秒）后才会自动结束。
        // PS：如果你想修改这个默认超时时间，可以按下面思路做，当然下面代码是springmvc配置文件形式的参考，在SpringBoot中自然不是配置xml来完成，这里只是提供一种思路。
        // <bean id="lifecycleProcessor" class="org.springframework.context.support.DefaultLifecycleProcessor">
        //      <!-- timeout value in milliseconds -->
        //      <property name="timeoutPerShutdownPhase" value="10000"/>
        // </bean>
        callback.run();

        isRunning = false;

	}

}
