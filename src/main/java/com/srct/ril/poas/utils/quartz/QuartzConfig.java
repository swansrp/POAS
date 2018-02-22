package com.srct.ril.poas.utils.quartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class QuartzConfig {

    @Autowired
    private JobFactory jobFactory;



    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);

        schedulerFactoryBean.setStartupDelay(20);
        //用于quartz集群,加载quartz数据源配置
//          factory.setQuartzProperties(quartzProperties());
        //注册触发器

        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}