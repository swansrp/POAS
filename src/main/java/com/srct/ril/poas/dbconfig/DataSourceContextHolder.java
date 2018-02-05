package com.srct.ril.poas.dbconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DataSourceContextHolder {
	public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    /**
     * 默认数据源
     */
    public static final DataSourceEnum DEFAULT_DS = DataSourceEnum.CONFIG;

    private static final ThreadLocal<DataSourceEnum> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(DataSourceEnum dbType) {
        log.debug("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static DataSourceEnum getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}

