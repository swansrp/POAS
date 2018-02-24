package com.srct.ril.poas.utils.log;

import org.apache.tomcat.util.digester.ObjectCreateRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srct.ril.poas.dao.dbconfig.DynamicDataSource;
import com.srct.ril.poas.utils.JSONUtil;

public class Log {
	private static Logger log = null;
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
