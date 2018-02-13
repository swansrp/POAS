package com.srct.ril.poas.python.crontask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ResourceUtils;

import com.srct.ril.poas.python.callback.PythonJobCallBack;
import com.srct.ril.poas.utils.Log;

@Configuration
@EnableScheduling
public class ScarCronTask {

	@SuppressWarnings("unused")
	private PythonJobCallBack mPythonJobCallBack = null;
	private InputStream normalIS = null;
	private InputStream errorIS = null;
	@Scheduled(cron = "*/20 * * * * ?")
	public void executeScarScript() throws IOException, InterruptedException {
		Log.i(getClass(), " 我来了");
	    File file = ResourceUtils.getFile("classpath:pythonScript/JingDongSpider.py");
		Process fetchScarProcess = Runtime.getRuntime().exec("python " + file.toString());
		normalIS = fetchScarProcess.getInputStream();
		errorIS = fetchScarProcess.getErrorStream();
		startCollectScriptNormalResultThread();
		startCollectScriptErrorResultThread();
		Log.i(getClass(), " 我走了");
		//mPythonJobCallBack.loadDataComplete("success");
	}
	
	private void startCollectScriptNormalResultThread() {
		new Thread() {
			public void run() {
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(normalIS));
				String line = null;
				try {
					while ((line = bufferReader.readLine()) != null) {
						if (line != null ) {
							Log.i(getClass(), " normal input = " + line);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						normalIS.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	private void startCollectScriptErrorResultThread() {
		new Thread() {
			public void run() {
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(errorIS));
				String line = null;
				try {
					while ((line = bufferReader.readLine()) != null) {
						if (line != null ) {
							Log.i(getClass(), " error = " + line);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						normalIS.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	
	public void setCallBack(PythonJobCallBack pythonJobCallBack) {
		mPythonJobCallBack = pythonJobCallBack;
	}
	
}
