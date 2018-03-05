package com.srct.ril.poas.dao.utils.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.dao.dbconfig.DataSourceConfig;
import com.srct.ril.poas.service.config.KeywordsService;

@Component
public class CategoryLifecycle implements SmartLifecycle {
	
	private boolean isRunning = false;
	
	@Autowired
	private KeywordsService keywordService;
	
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		keywordService.initKeywords();
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
