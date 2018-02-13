package com.srct.ril.poas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srct.ril.poas.dao.dbconfig.DynamicDataSource;

public class Log {
	private static Logger log = null;
	public static void i(Class<?> clazz,String msg) {
		 log = LoggerFactory.getLogger(clazz);
		 log.info(msg);
	}
	
}
