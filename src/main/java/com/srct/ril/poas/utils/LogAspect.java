package com.srct.ril.poas.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;

@Aspect
@Component //Spring 中立logic 注释
public class LogAspect {
	
	Class<?> clazz;
	String methodName;
	
	@Pointcut("execution(public * com.srct.ril.poas.controller..*.*(..))"
			+ " || "
			+ "execution(public * com.srct.ril.poas.service..*.*(..))")
    public void recordLog() {}
	
    @Before("recordLog()")
    public void before(JoinPoint point) {
    }

    @Around("recordLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
    	Object res;
    	//获得当前访问的class
        this.clazz = pjp.getTarget().getClass();
    	//获得访问的方法名
        String methodName = pjp.getSignature().getName();
    	this.printLog("Enter " + methodName);
        res = pjp.proceed();
        this.printLog("Exit " + methodName);
        return res;
    }

    @After("recordLog()")
    public void after() {
    }
    
    @AfterThrowing("recordLog()")
    public void afterThorw() {
    	this.printLog("Exception is occured on "+ methodName);
    }
    
    private void printLog(String str){
        if(this.clazz==null) return;
    	Log.i(this.clazz, str);
    }
	
}
