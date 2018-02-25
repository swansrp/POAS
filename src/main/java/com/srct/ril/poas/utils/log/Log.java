package com.srct.ril.poas.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srct.ril.poas.utils.JSONUtil;

public class Log {
	
	private static Logger log = null;
	private static Log sInstance = new Log();
	private static String getCallerClassName() {  
        return new SecurityManager() {  
            public String getClassName() {  
                return getClassContext()[4].getSimpleName();  
            }  
        }.getClassName();  
    }
	
	private static Class<?> getCallerClass() {
		return new SecurityManager() {  
            public Class<?> getClassName() {  
                try {
					return Class.forName(getClassContext()[4].getName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}  
            }  
        }.getClassName();
    }
	
	private static int getCallerLine() {
		return new Throwable().getStackTrace()[3].getLineNumber();  
	}
	
	private static String loc() {
		return "("+getCallerClassName()+".java:"+getCallerLine()+")";
	}
	
	public static void i(String msg, Object...strings) {
		LoggerFactory.getLogger(getCallerClass()).info(loc()+msg, strings);
	}
	
	public static void d(String msg, Object...strings) {
		LoggerFactory.getLogger(getCallerClass()).debug(loc()+msg, strings);
	}
	
	public static void e(String msg, Object...strings) {
		LoggerFactory.getLogger(getCallerClass()).error(loc()+msg, strings);
	}
	
	public static void w(String msg, Object...strings) {
		LoggerFactory.getLogger(getCallerClass()).warn(loc()+msg, strings);
	}
	
	public static void v(String msg, Object...strings) {
		LoggerFactory.getLogger(getCallerClass()).trace(loc()+msg, strings);
	}
	public static void ii(Object o) {
		LoggerFactory.getLogger(sInstance.getClass()).info(loc()+JSONUtil.toJSONString(o));
	}
	public static void ee(Object o) {
		LoggerFactory.getLogger(sInstance.getClass()).error(loc()+JSONUtil.toJSONString(o));
	}
	public static void ww(Object o) {
		LoggerFactory.getLogger(sInstance.getClass()).warn(loc()+JSONUtil.toJSONString(o));
	}
	public static void dd(Object o) {
		LoggerFactory.getLogger(sInstance.getClass()).debug(loc()+JSONUtil.toJSONString(o));
	}
	public static void vv(Object o) {
		LoggerFactory.getLogger(sInstance.getClass()).trace(loc()+JSONUtil.toJSONString(o));
	}
		
	public static void i(Class<?> clazz,String msg, Object...strings) {
		log = LoggerFactory.getLogger(clazz);
		log.info(msg, strings);
	}
	public static void e(Class<?> clazz,String msg, Object...strings) {
		log = LoggerFactory.getLogger(clazz);
		log.error(msg, strings);
	}
	public static void w(Class<?> clazz,String msg, Object...strings) {
		log = LoggerFactory.getLogger(clazz);
		log.warn(msg, strings);
	}
	public static void d(Class<?> clazz,String msg, Object...strings) {
		log = LoggerFactory.getLogger(clazz);
		log.debug(msg, strings);
	}
	public static void v(Class<?> clazz,String msg, Object...strings) {
		log = LoggerFactory.getLogger(clazz);
		log.trace(msg, strings);
	}
	public static void ii(Class<?> clazz, Object o) {
		i(clazz, JSONUtil.toJSONString(o));
	}
	public static void ee(Class<?> clazz, Object o) {
		e(clazz, JSONUtil.toJSONString(o));
	}
	public static void ww(Class<?> clazz, Object o) {
		w(clazz, JSONUtil.toJSONString(o));
	}
	public static void dd(Class<?> clazz, Object o) {
		d(clazz, JSONUtil.toJSONString(o));
	}
	public static void vv(Class<?> clazz, Object o) {
		v(clazz, JSONUtil.toJSONString(o));
	}
	
	
}
