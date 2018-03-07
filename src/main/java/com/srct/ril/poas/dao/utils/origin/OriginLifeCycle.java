package com.srct.ril.poas.dao.utils.origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.service.config.SourceMapService;

@Component
public class OriginLifeCycle implements SmartLifecycle {

private boolean isRunning = false;
	
	@Autowired
	private SourceMapService sourceMapService;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		sourceMapService.initOrigin();
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
		return 2;
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
