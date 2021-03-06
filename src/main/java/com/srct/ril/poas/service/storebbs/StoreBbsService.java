package com.srct.ril.poas.service.storebbs;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.srct.ril.poas.ai.nlp.NLPItem;
import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.utils.ServiceException;

public interface StoreBbsService {
	public List<StoreBbsPojoBase> select(String modelName, String origin, String startTime, String endTime) 
			throws ServiceException;
	
	public List<NLPItem> select(String modelName, String startTime, String endTime) 
			throws ServiceException;
	
	public List<StoreBbsPojoBase> select(String modelName, String origin, String startTime, String endTime, HttpServletResponse response) 
			throws ServiceException, IOException;
	
	public List<NLPItem> select(String modelName, String startTime, String endTime, HttpServletResponse response)
			throws ServiceException, IOException;
	
	public List<NLPItem> select(String modelName, String startTime, String endTime, boolean bAnalysis) 
			throws ServiceException;
	
	public List<NLPItem> select(String modelName, String origin, String startTime, String endTime, boolean bAnalysis) 
			throws ServiceException;
	
	public void updateAnalysis(String modelName, String origin, StoreBbsPojoBase pojo, Integer sentiment, Integer category)
			throws ServiceException, IOException;
	
	public void updateSentiment(String modelName, String origin, StoreBbsPojoBase pojo, Integer sentiment)
			throws ServiceException, IOException;
	
	public void updateCategory(String modelName, String origin, StoreBbsPojoBase pojo, Integer category)
			throws ServiceException, IOException;
	
	public void updateAnalysis(String modelName, String origin, NLPItem nlp, Integer sentiment, Integer category)
			throws ServiceException, IOException;
	
	public void updateSentiment(String modelName, String origin, NLPItem nlp, Integer sentiment)
			throws ServiceException, IOException;
	
	public void updateCategory(String modelName, String origin, NLPItem nlp, Integer category)
			throws ServiceException, IOException;
	
	public void updateAnalysis(String modelName, String origin, Integer id, Integer sentiment, Integer category)
			throws ServiceException, IOException;
	
	public void updateAnalysis(String modelName, String origin, Integer id, Integer sentiment, String category)
			throws ServiceException, IOException;
	
}
