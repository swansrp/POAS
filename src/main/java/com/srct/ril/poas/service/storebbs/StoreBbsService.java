package com.srct.ril.poas.service.storebbs;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.srct.ril.poas.ai.NLPItem;
import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.utils.ServiceException;

public interface StoreBbsService {
	public List<StoreBbsPojoBase> select(String modelName, String origin, String startTime, String endTime) 
			throws ServiceException;
	public List<NLPItem> select(String modelName, String startTime, String endTime) throws ServiceException;
	public List<StoreBbsPojoBase> select(String modelName, String origin, String startTime, String endTime, HttpServletResponse response) 
			throws ServiceException, IOException;
	public List<NLPItem> select(String modelName, String startTime, String endTime, HttpServletResponse response)
			throws ServiceException, IOException;
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category);
	public void updateSentiment(String modelName, Object obj, Integer sentiment);
	public void updateCategory(String modelName, Object obj, Integer category);
	

}
