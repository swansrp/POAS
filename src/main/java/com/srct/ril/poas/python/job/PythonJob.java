package com.srct.ril.poas.python.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.srct.ril.poas.dao.pojo.UrlJoinMap;
import com.srct.ril.poas.dao.pojo.UrlMap;
import com.srct.ril.poas.python.callback.PythonJobCallBack;
import com.srct.ril.poas.python.model.PythonResponseModel;
import com.srct.ril.poas.service.config.UrlMapService;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Configuration
public class PythonJob implements Job{

	private PythonJobCallBack mFetchJobCallBack = null;
	private InputStream normalIS = null;
	private InputStream errorIS = null;
	private PythonResponseModel mPythonResponseModel = null;
	private Process fetchScarProcess = null;
	private int mSourceCount;
	private int mSourceAlreadyFetchIndex;
	@Autowired
	UrlMapService urlMapService;
	private PythonJobCallBack mInternalPythonJobCallBack = new PythonJobCallBack() {
		
		@Override
		public void loadDataComplete(PythonResponseModel pythonResponseModel) {
			// TODO Auto-generated method stub
			mSourceAlreadyFetchIndex++;
			if (mSourceAlreadyFetchIndex == mSourceCount) {
				PythonResponseModel result = new PythonResponseModel();
				result.message = "finish";
				mFetchJobCallBack.loadDataComplete(result);
			} else {
				fetchByCurrentIndex();
			}
		}
	};
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail detail = context.getJobDetail();
        String name = detail.getJobDataMap().getString("job");
        if (name != null && "fetch".equals(name)) {
        	mSourceAlreadyFetchIndex = 0;
    		mSourceCount = 0;
    		try {
    			//mSourceCount = urlMapService.getAllUrl().size();
    			Log.i("count = " + urlMapService.getUrlJoinList().size());
    			fetchByCurrentIndex();
    			Log.i("current Source count is " + mSourceCount);
    		} catch (ServiceException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		}
	}
	
	private void fetchByCurrentIndex() {
		File file;
		try {
			List<UrlJoinMap> urlJoinMaps = urlMapService.getUrlJoinList();
			Log.i("fetchByCurrentIndex url = "+ urlJoinMaps.get(mSourceAlreadyFetchIndex).getUrl() + " sourceen = " + urlJoinMaps.get(mSourceAlreadyFetchIndex).getSourceMap().getSourceEn());
			file = ResourceUtils.getFile("classpath:pythonScript/" + urlJoinMaps.get(mSourceAlreadyFetchIndex).getSourceMap().getSourceEn() + "Spider.py");
			fetchScarProcess = Runtime.getRuntime().exec("python " + file.toString() + " " + urlJoinMaps.get(mSourceAlreadyFetchIndex).getUrl());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		normalIS = fetchScarProcess.getInputStream();
		errorIS = fetchScarProcess.getErrorStream();
		startCollectScriptNormalResultThread();
		startCollectScriptErrorResultThread();
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
							mInternalPythonJobCallBack.loadDataComplete(mPythonResponseModel);
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
						errorIS.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	
	public void setCallBack(PythonJobCallBack pythonJobCallBack) {
		mFetchJobCallBack = pythonJobCallBack;
	}

}
