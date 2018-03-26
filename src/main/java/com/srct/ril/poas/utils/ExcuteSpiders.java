package com.srct.ril.poas.utils;

import org.quartz.SchedulerException;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.srct.ril.poas.python.job.PythonJob;

public class ExcuteSpiders {

	private Scheduler scheduler;
	
	public void Excute(){
		
		JobDetail job = JobBuilder.newJob(PythonJob.class).withIdentity(PythonJob.class.getSimpleName(), "Python").usingJobData("job", "fetch") .build();
        
		SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2)
              .withRepeatCount(0);
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(PythonJob.class.getSimpleName(), "Python").startNow()
              .withSchedule(builder).build();
		
	      try {
	      	scheduler.scheduleJob(job, trigger);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	}
	
}
