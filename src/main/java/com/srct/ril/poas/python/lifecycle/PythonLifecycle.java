package com.srct.ril.poas.python.lifecycle;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.python.callback.PythonJobCallBack;
import com.srct.ril.poas.python.crontask.ScarCronTask;
import com.srct.ril.poas.python.job.PythonJob;
import com.srct.ril.poas.python.model.PythonResponseModel;
import com.srct.ril.poas.utils.log.Log;

@Component
public class PythonLifecycle implements SmartLifecycle  {
	
	@Autowired
	ScarCronTask mScarCronTask;
	private boolean isRunning = false;
	@Autowired
    private  Scheduler scheduler;
	private PythonJobCallBack mPythonJobCallBack = new PythonJobCallBack() {
		
		@Override
		public void loadDataComplete(PythonResponseModel pythonResponseModel) {
			// TODO Auto-generated method stub
			Log.i("loadDataComplete = " + pythonResponseModel.message);
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
		JobDetail job = JobBuilder.newJob(PythonJob.class).withIdentity(PythonJob.class.getSimpleName(), "Python").usingJobData("job", "fetch") .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/20 * * * * ?");
        //CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(PythonJob.class.getSimpleName(),"Python") .withSchedule(scheduleBuilder).build();
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40)
                .withRepeatCount(2);
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(PythonJob.class.getSimpleName(), "Python").startNow()
                .withSchedule(builder).build();
//        try {
//			scheduler.scheduleJob(job, trigger);
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		isRunning = true;
		mScarCronTask.setCallBack(mPythonJobCallBack);
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		mScarCronTask.setCallBack(null);
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
		mScarCronTask.setCallBack(null);
        isRunning = false;
	}
	
}
