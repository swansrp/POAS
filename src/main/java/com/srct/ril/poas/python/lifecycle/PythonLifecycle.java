package com.srct.ril.poas.python.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.python.callback.PythonJobCallBack;
import com.srct.ril.poas.python.crontask.ScarCronTask;
import com.srct.ril.poas.python.model.PythonResponseModel;
import com.srct.ril.poas.utils.Log;

@Component
public class PythonLifecycle implements SmartLifecycle  {
	
	@Autowired
	ScarCronTask mScarCronTask;
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
		return false;
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		mScarCronTask.setCallBack(mPythonJobCallBack);
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void stop(Runnable arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
