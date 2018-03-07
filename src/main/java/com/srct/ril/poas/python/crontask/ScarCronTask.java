package com.srct.ril.poas.python.crontask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.srct.ril.poas.python.callback.PythonJobCallBack;
import com.srct.ril.poas.python.model.PythonResponseModel;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.log.Log;

@Configuration
@EnableScheduling
public class ScarCronTask {

	private PythonJobCallBack mPythonJobCallBack = null;
	private InputStream normalIS = null;
	private InputStream errorIS = null;
	private PythonResponseModel mPythonResponseModel = null;
	@Scheduled(cron = "0 6 * * * ?")
	public void executeScarScript() throws IOException, InterruptedException {
//	    File file = ResourceUtils.getFile("classpath:pythonScript/JingDongSpider.py");
//		Process fetchScarProcess = Runtime.getRuntime().exec("python " + file.toString());
//		normalIS = fetchScarProcess.getInputStream();
//		errorIS = fetchScarProcess.getErrorStream();
//		startCollectScriptNormalResultThread();
//		startCollectScriptErrorResultThread();
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
							mPythonResponseModel = (PythonResponseModel)JSONUtil.readJson(line, PythonResponseModel.class);
							mPythonJobCallBack.loadDataComplete(mPythonResponseModel);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
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
