package com.srct.ril.poas.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srct.ril.poas.dao.dbconfig.DynamicDataSource;

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
	
}
