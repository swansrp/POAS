package com.srct.ril.poas.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 工具类-spring bean*/
public class BeanUtil {
    private static WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

    /**
     * 根据bean名称获取
     * 
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return ctx.getBean(name);
    }
}