package com.srct.ril.poas.crontask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ResourceUtils;

import com.srct.ril.poas.utils.Log;

@Configuration
@EnableScheduling
public class ScarCronTask {
	
	@Scheduled(cron = "*/20 * * * * ?")
	public void executeScarScript() throws IOException, InterruptedException {
		//File file = ResourceUtils.getFile("classpath:pythonScript/JingDongSpider.py");
		//Process fetchScarProcess = Runtime.getRuntime().exec("python " + file.toString());
	}

}
