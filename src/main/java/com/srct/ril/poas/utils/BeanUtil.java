package com.srct.ril.poas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.web.context.WebApplicationContext;

/**
 * 工具类-spring bean*/
@Configuration
public class BeanUtil implements ApplicationContextAware,ApplicationListener {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private static ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private static BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    private static WebApplicationContext ctx = null;
    
    /**
     * 根据bean名称获取
     * 
     * @param name
     * @return
     */
    public static Object getBean(String name) {
    	if(ctx == null) {
    		logger.info("Call BeanUtils too early");
    		return null;
    	}
        return ctx.getBean(name);
    }
    
    public static void registerBean(BeanDefinitionRegistry registry, String name, Class<?> beanClass) {
    	AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
    	ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(abd);
    	abd.setScope(scopeMetadata.getScopeName());
        // 可以自动生成name
        String beanName = (name != null ? name : beanNameGenerator.generateBeanName(abd, registry));

        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }


	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		logger.info("onApplicationEvent {}", event);  
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		logger.info("setApplicationContext");  
		ctx = (WebApplicationContext)applicationContext;
		
	}
}