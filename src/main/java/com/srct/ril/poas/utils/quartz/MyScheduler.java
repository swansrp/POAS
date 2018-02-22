package com.srct.ril.poas.utils.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler  {
    @Autowired
    private  Scheduler scheduler;


    public void scheduleJobs() throws SchedulerException {
//        startJob();
    }

    public void startJob(String time,String group,String jobId,String job){

        try {
            Class jobClass = Class.forName(job);
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail jobDetail = JobBuilder.newJob(jobClass/*MyJob1.class*/) .withIdentity(jobId, group).build();//设置Job的名字和组
//          JobDetail jobDetail = JobBuilder.newJob(jobClass/*MyJob1.class*/) .withIdentity(jobId, group).usingJobData("name","我的名字").build();//设置Job的名字和组
            jobDetail.getJobDataMap().put("name","MyName");//动态添加数据

        //  corn表达式  每2秒执行一次
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time/*"0/2 * * * * ?"*/);

        //设置定时任务的时间触发规则
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobId,group) .withSchedule(scheduleBuilder).build();


        // 把作业和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail,cronTrigger);
/*
        // 启动调度
        scheduler.start();

        Thread.sleep(30000);

        // 停止调度
        scheduler.shutdown();*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 修改定时任务时间
     * @param triggerName
     * @param triggerGroupName
     * @param time
     */
    public  void  modifyJobTime(String triggerName,String triggerGroupName, String time) {
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            System.out.println(scheduler.getTriggerState(triggerKey));
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {  // Trigger已存在，那么更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);//设置一个新的定时时间

                // 按新的cronExpression表达式重新构建trigger
                CronTrigger cronTrigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /****
     * 暂停一个任务
     * @param triggerName
     * @param triggerGroupName
     */
    public void pauseJob(String triggerName,String triggerGroupName){
        try {
            JobKey jobKey = new JobKey(triggerName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail==null){
                return;
            }
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    /****
     * 删除一个任务
     * @param triggerName
     * @param triggerGroupName
     */
    public void deleteJob(String triggerName,String triggerGroupName){
        try {
            JobKey jobKey = new JobKey(triggerName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail==null){
                return;
            }
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    /****
     * 恢复一个任务
     * @param triggerName
     * @param triggerGroupName
     */
    public void resumeJob(String triggerName,String triggerGroupName){
        try {
            JobKey jobKey = new JobKey(triggerName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail==null){
                return;
            }
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /***
     * 开始定时任务
     */
    public void startAllJob(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    /***
     * 立即执行定时任务
     */
    public void doJob(String triggerName,String triggerGroupName){
        try {
            JobKey jobKey = JobKey.jobKey(triggerName, triggerGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    public void shutdown(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
