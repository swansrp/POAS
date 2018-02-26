package com.srct.ril.poas.utils.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;

@Aspect
@Component //Spring 中立logic 注释
public class LogAspect {
	
	@Pointcut("execution(public * com.srct.ril.poas.controller..*.*(..))"
			+ " || "
			+ "execution(public * com.srct.ril.poas.service..*.*(..))"
			+ " || "
			//注解类
			+ "@within(com.srct.ril.poas.utils.log.MySlf4j)"
			+ " || "
			//注解方法
			+ "@annotation(com.srct.ril.poas.utils.log.MySlf4j)")
    public void recordLog() {}
	
    @Before("recordLog()")
    public void before(JoinPoint point) {
    }

    @Around("recordLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
    	Object res;
    	//获得当前访问的class
    	Class clazz = pjp.getTarget().getClass();
    	//获得访问的方法名
        String methodName = pjp.getSignature().getName();
    	this.printLog(clazz, "Enter " + methodName);
        res = pjp.proceed();
        //获得当前访问的class
        this.printLog(clazz, "Exit " + methodName);
        return res;
    }

    @After("recordLog()")
    public void after() {
    }
    
    @AfterThrowing("recordLog()")
    public void afterThorw(JoinPoint point) {
    	Class clazz = point.getTarget().getClass();
    	String methodName = point.getSignature().getName();
    	this.printLog(clazz, "Exception is occured on "+ methodName);
    }
    
    private void printLog(Class<?> clazz, String str){
        if(clazz==null) 
        	clazz=this.getClass();
        LoggerFactory.getLogger(clazz).debug(str);
    }
	
}
