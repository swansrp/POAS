package com.srct.ril.poas.service.storebbs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.nlp.NLPItem;
import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.dao.utils.origin.Origin;
import com.srct.ril.poas.service.ai.nlp.NLPAnalysisServiceImpl;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreBbsServiceImpl implements StoreBbsService {
	@Autowired
	private NLPAnalysisServiceImpl nlpAnalysisService;
	@Autowired
	private Origin originBean;
	@Autowired
	private StoreBbsServiceImpl self;
	
	@Override
	public List<NLPItem> select(String modelName, String startTime, String endTime) throws ServiceException {
		return select(modelName, startTime, endTime, false);
    }
	
	@Override
	public List<NLPItem> select(String modelName, String startTime, String endTime, boolean bAnalysis) throws ServiceException {
		List<NLPItem> nlpItemList = new ArrayList<>();
		List<String> originList = originBean.getOriginList();
		for(String origin : originList) {			
			List<StoreBbsPojoBase> storeBbsPojoList = self.select(modelName, origin, startTime, endTime);
			if(storeBbsPojoList!=null) {
				for(StoreBbsPojoBase pojo : storeBbsPojoList) {
					nlpItemList.add(nlpAnalysisService.NLPitemFactory(modelName, origin, pojo, bAnalysis));
				}
			}
		}
        return nlpItemList;
    }
	
	@Override
	public List<NLPItem> select(String modelName, String origin, String startTime, String endTime, boolean bAnalysis) throws ServiceException {
		List<NLPItem> nlpItemList = new ArrayList<>();		
		List<StoreBbsPojoBase> storeBbsPojoList = self.select(modelName, origin, startTime, endTime);
		if(storeBbsPojoList!=null) {
			for(StoreBbsPojoBase pojo : storeBbsPojoList) {
				nlpItemList.add(nlpAnalysisService.NLPitemFactory(modelName, origin, pojo, bAnalysis));
			}
		}
        return nlpItemList;
    }

	@Override
	public List<StoreBbsPojoBase> select(String modelName, String origin, String startTime, String endTime)
			throws ServiceException {
		// TODO Auto-generated method stub
		Log.i("[{}, select from {}  {}--{}]",modelName,origin,startTime,endTime);
		List<StoreBbsPojoBase> StoreBbsPojoList = null;
		Object dao = null;
		try {
			dao = originBean.getDaoFromSource(origin);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
		Class<?> daoClass = originBean.getDaoClassFromSource(origin);
		Class<?> pojoExampleClass = originBean.getPojoExampleClassFromSource(origin);
		Class<?> pojoCriteriaClass = originBean.getpojoCriteriaFromSource(origin);
		Method method;
		try {
			Object pojoExamplseClassObj = pojoExampleClass.newInstance();
			method = pojoExampleClass.getMethod("setDistinct", new Class[] { boolean.class });
			method.invoke(pojoExamplseClassObj,false);
			method = pojoExampleClass.getMethod("createCriteria");
			Object pojoCriteriaClassObj = method.invoke(pojoExamplseClassObj);
			if(!startTime.equals("")) {
				method = pojoCriteriaClass.getMethod("andDateGreaterThan", new Class[] { String.class });
	    		method.invoke(pojoCriteriaClassObj,startTime);
	    	} 
			if(!endTime.equals("")) {
	    		method = pojoCriteriaClass.getMethod("andDateLessThan", new Class[] { String.class });
	    		method.invoke(pojoCriteriaClassObj,endTime);
	    	}
			method = daoClass.getMethod("selectByExample", new Class[] {pojoExampleClass});
			StoreBbsPojoList = (List<StoreBbsPojoBase>)method.invoke(dao, pojoExamplseClassObj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException | 
				InstantiationException e) {
			throw new ServiceException("["+modelName+"] from "  + origin + " start " + startTime + " to " + endTime + " occur exception " + e);
		}
		if (StoreBbsPojoList == null) {
            throw new ServiceException("["+modelName+"] from "  + origin + " start " + startTime + " to " + endTime + " not found");
        } 
		return StoreBbsPojoList;
	}
		
	@Override
	public List<StoreBbsPojoBase> select(String modelName, String origin, String startTime, String endTime,
			HttpServletResponse response) throws ServiceException, IOException {
		// TODO Auto-generated method stub
		List<StoreBbsPojoBase> storeBbsPojoList = select(modelName, origin, startTime, endTime);
		nlpAnalysisService.saveExcel(modelName, origin, storeBbsPojoList).write(response.getOutputStream());
		return storeBbsPojoList;
	}
	
	@Override
	public List<NLPItem> select(String modelName, String startTime, String endTime,
			HttpServletResponse response) throws ServiceException, IOException {
		// TODO Auto-generated method stub
		List<NLPItem> nlpItemList = select(modelName, startTime, endTime);
		nlpAnalysisService.saveExcel(modelName, nlpItemList).write(response.getOutputStream());
		return nlpItemList;
	}

	@Override
	public void updateAnalysis(String modelName, String origin, StoreBbsPojoBase pojo, Integer sentiment, Integer category)
			throws ServiceException {
		// TODO Auto-generated method stub
		Class<?> daoClass = originBean.getDaoClassFromSource(origin);
		Method method = null;
		Integer id = null;
		try {
			method = daoClass.getMethod("getId");
			id = (Integer) method.invoke(pojo);
		} catch (NoSuchMethodException | 
				SecurityException |
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("["+modelName+"] from "  + origin + " get Id occur exception " + e);
		}
		if(id == null) {
			throw new ServiceException("["+modelName+"] from "  + origin + " get Id failed ");
			
		}
		self.updateAnalysis(modelName,origin,id,sentiment,category);
	}

	@Override
	public void updateSentiment(String modelName, String origin, StoreBbsPojoBase pojo, Integer sentiment)
			throws ServiceException {
		// TODO Auto-generated method stub
		updateAnalysis(modelName, origin, pojo, sentiment, null);
	}

	@Override
	public void updateCategory(String modelName, String origin, StoreBbsPojoBase pojo, Integer category)
			throws ServiceException {
		// TODO Auto-generated method stub
		updateAnalysis(modelName, origin, pojo, null, category);
	}
	
	@Override
	public void updateAnalysis(String modelName, String origin, NLPItem nlp, Integer sentiment, Integer category)
			throws ServiceException {
		// TODO Auto-generated method stub
		self.updateAnalysis(modelName,origin,nlp.getId(),sentiment,category);
	}

	@Override
	public void updateSentiment(String modelName, String origin, NLPItem nlp, Integer sentiment) 
			throws ServiceException {
		// TODO Auto-generated method stub
		updateAnalysis(modelName, origin, nlp, sentiment, null);
	}
	
	@Override
	public void updateCategory(String modelName, String origin, NLPItem nlp, Integer category)
			throws ServiceException {
		// TODO Auto-generated method stub
		updateAnalysis(modelName, origin, nlp, null, category);
	}

	@Override
	public void updateAnalysis(String modelName, String origin, Integer id, Integer sentiment, Integer category) 
			throws ServiceException {
		// TODO Auto-generated method stub
		Log.i("[{}, updateAnalysis from {} id[{}] {}--{}]",modelName,origin,id,sentiment,category);
		if(sentiment==null && category==null) {
    		return;
    	}
		Object dao = null;
		try {
			dao = originBean.getDaoFromSource(origin);
		} catch (NoSuchBeanDefinitionException e) {
			return;
		}
		Class<?> daoClass = originBean.getDaoClassFromSource(origin);
		Class<?> pojoClass = originBean.getPojoClassFromSource(origin);
		Method method;
		try {
			Object pojoClassObj = pojoClass.newInstance();
			method = pojoClass.getMethod("setId", new Class[] {Integer.class});
			method.invoke(pojoClassObj, id);
			
			method = pojoClass.getMethod("setSentiment", new Class[] {Integer.class});
			method.invoke(pojoClassObj, sentiment);
					
			method = pojoClass.getMethod("setCategory", new Class[] {Integer.class});
			method.invoke(pojoClassObj, category);
			
			method = daoClass.getMethod("updateByPrimaryKeySelective", new Class[] {pojoClass});
			method.invoke(dao, pojoClassObj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException | 
				InstantiationException e) {
			throw new ServiceException("["+modelName+"] from "  + origin + " set analysis " + id + " occur exception " + e);
		}
	}

    
    
    
    
}
