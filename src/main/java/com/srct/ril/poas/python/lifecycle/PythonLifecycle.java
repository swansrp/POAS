package com.srct.ril.poas.python.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.python.callback.PythonJobCallBack;
import com.srct.ril.poas.python.crontask.ScarCronTask;
import com.srct.ril.poas.python.model.PythonResponseModel;
import com.srct.ril.poas.utils.log.Log;

@Component
public class PythonLifecycle implements SmartLifecycle  {
	
	@Autowired
	ScarCronTask mScarCronTask;
	private boolean isRunning = false;
	private PythonJobCallBack mPythonJobCallBack = new PythonJobCallBack() {
		
		@Override
		public void loadDataComplete(PythonResponseModel pythonResponseModel) {
			// TODO Auto-generated method stub
			Log.i(getClass(), "loadDataComplete = " + pythonResponseModel.message);
		}
	};
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return isRunning;
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		isRunning = true;
		mScarCronTask.setCallBack(mPythonJobCallBack);
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		isRunning = false;
	}
	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 1223;
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
