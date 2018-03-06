package com.srct.ril.poas.python.lifecycle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.python.callback.PythonJobCallBack;
import com.srct.ril.poas.python.crontask.ScarCronTask;
import com.srct.ril.poas.python.job.PythonJob;
import com.srct.ril.poas.python.model.PythonResponseModel;
import com.srct.ril.poas.service.ai.nlp.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Component
public class PythonLifecycle implements SmartLifecycle  {
	
	@Autowired
	ScarCronTask mScarCronTask;
	
	@Autowired
    private Scheduler scheduler;
	
	@Autowired
	private PythonJob mPythonJob;
	
	@Autowired
	private NLPAnalysisService nlpService;
	
	private boolean isRunning = false;
	
	private PythonJobCallBack mPythonJobCallBack = new PythonJobCallBack() {
		
		@Override
		public void loadDataComplete(PythonResponseModel pythonResponseModel) {
			// TODO Auto-generated method stub
			try {
				Log.i("analyse begin");
				nlpService.analyse();
				Log.i("analyse complete");
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		isRunning = true;
		mPythonJob.setCallBack(mPythonJobCallBack);
		JobDetail job = JobBuilder.newJob(PythonJob.class).withIdentity(PythonJob.class.getSimpleName(), "Python").usingJobData("job", "fetch") .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 * * ?");
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(PythonJob.class.getSimpleName(),"Python") .withSchedule(scheduleBuilder).build();
//        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2)
//                .withRepeatCount(0);
//        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(PythonJob.class.getSimpleName(), "Python").startNow()
//                .withSchedule(builder).build();
        try {
        	scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
    private void copyPythonScript2LocalSystem() {
	    Resource fileResource = new ClassPathResource("/pythonScript/");
	    try {
			File dir = new File(fileResource.getFile().toString());
			copyFile(dir, new File("~/Resources/pythonScript/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    }
    
    public void copyFile(File file, File file2) {
    
        if (file.isDirectory()) {
            file2.mkdir();
            File[] files = file.listFiles();
            for (File file3 : files) {
            	copyFile(file3, new File(file2, file3.getName()));
            }
        } else if (file.isFile()) {
            File file3 = new File(file2.getAbsolutePath());
            try {
                file3.createNewFile();
                copyDatas(file.getAbsolutePath(), file3.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void copyDatas(String filePath, String filePath1) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(filePath);
            fos = new FileOutputStream(filePath1);
            byte[] buffer = new byte[1024];
            while (true) {
                int temp = fis.read(buffer, 0, buffer.length);
                if (temp == -1) {
                    break;
                } else {
                    fos.write(buffer, 0, temp);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
