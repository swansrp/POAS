package com.srct.ril.poas.dbconfig;


import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.springboot.HelloController; 


@Aspect
@Component //Spring 中立logic 注释
public class DynamicDataSourceAspect {
	
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class); 

    @Before("@annotation(DS)")
    public void beforeSwitchDS(JoinPoint point){
    	
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        
        DataSourceEnum dataSource = DataSourceContextHolder.DEFAULT_DS;
        
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
                log.info("注解中的数据源 is {}", dataSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);

    }


    @After("@annotation(DS)")
    public void afterSwitchDS(JoinPoint point){

        DataSourceContextHolder.clearDB();

    }
    
    
    /** 
     * 得到参数的值 
     * @param obj 
     */  
    public static DataSourceEnum getFieldsValue(Object obj) {  
        Field[] fields = obj.getClass().getDeclaredFields();  
        String typeName = obj.getClass().getTypeName();   
        log.info("typeName is {}", typeName);
        for (Field f : fields) {
        	log.info("Fileds typeName is {}", f.getType().getName());
        	if(f.getType().getName().equals(DataSourceEnum.class.getName())) {
        		f.setAccessible(true);
        		try {
					return (DataSourceEnum)(f.get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        return null;
    } 
    
    
    
}