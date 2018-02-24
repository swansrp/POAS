package com.srct.ril.poas.ai.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.dao.dbconfig.DataSourceConfig;
import com.srct.ril.poas.service.config.KeywordsService;
import com.srct.ril.poas.utils.log.Log;

@Component
public class CategoryLifecycle implements SmartLifecycle {
	
	private boolean isRunning = false;
	
	//必须先注入dsc促使Mybatis sessionfactoryBean先被建立
	@Autowired
	DataSourceConfig dsc;
	@Autowired
	private KeywordsService keywordService;
	
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		keywordService.updateKeywords();
		isRunning = true;
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return isRunning;
	}
	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
        callback.run();

        isRunning = false;
		
	}

}
