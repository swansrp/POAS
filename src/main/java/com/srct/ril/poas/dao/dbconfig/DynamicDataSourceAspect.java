package com.srct.ril.poas.dao.dbconfig;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo; 


@Aspect
@Component //Spring 中立logic 注释
public class DynamicDataSourceAspect {
	
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class); 

    @Before("@annotation(DS)||@within(DS)")
    public void beforeSwitchDS(JoinPoint point){
    	
        //获得当前访问的class
        Class<?> clazz = point.getTarget().getClass();
    	//class name
    	String classType = point.getTarget().getClass().getName();  
    	
        String clazzName = clazz.getName();  
        String clazzSimpleName = clazz.getSimpleName();
        //获得访问的方法名
        String methodName = point.getSignature().getName();  
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        
        try {
            // 得到访问的方法对象
            Method method = clazz.getMethod(methodName, argClass);

            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
                log.debug("Method注解中的数据源 is {}", dataSource);
            } else if (clazz.isAnnotationPresent(DS.class)) {
            	DS annotation = clazz.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
                log.debug("Class注解中的数据源 is {}", dataSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        if(dataSource.equals(DataSourceEnum.CONFIG)) {
            DataSourceContextHolder.setDB(dataSource);
        } else {

            String modelDBName = null; 
        	try {
            	//获取参数名称
    			String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);
    			Object[] args = point.getArgs();
    			for(int index=0;index<paramNames.length;index++) {
    				log.debug("paramNames {} args {} ", paramNames[index],args[index]);
    				if(paramNames[index].equals("modelName")) {
    					modelDBName = (String)args[index];
    					break;
    				}
    			}
            	DataSourceContextHolder.setDB(modelDBName);
    		} catch (NotFoundException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} 

        }

    }


    @After("@annotation(DS)||@within(DS)")
    public void afterSwitchDS(JoinPoint point){

        DataSourceContextHolder.clearDB();

    }
    
    /** 
     * 得到方法参数的名称 
     * @param cls 
     * @param clazzName 
     * @param methodName 
     * @return 
     * @throws NotFoundException 
     */  
    private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException{  
        ClassPool pool = ClassPool.getDefault();  
        //ClassClassPath classPath = new ClassClassPath(this.getClass());  
        ClassClassPath classPath = new ClassClassPath(cls);  
        pool.insertClassPath(classPath);  
          
        CtClass cc = pool.get(clazzName);  
        CtMethod cm = cc.getDeclaredMethod(methodName);  
        MethodInfo methodInfo = cm.getMethodInfo();  
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
        if (attr == null) {  
            // exception  
        }  
        String[] paramNames = new String[cm.getParameterTypes().length];  
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
        for (int i = 0; i < paramNames.length; i++){  
            paramNames[i] = attr.variableName(i + pos); //paramNames即参数名  
        }  
        return paramNames;  
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