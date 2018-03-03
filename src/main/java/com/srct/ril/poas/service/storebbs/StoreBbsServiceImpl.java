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

import com.srct.ril.poas.ai.NLPItem;
import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.dao.utils.origin.Origin;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreBbsServiceImpl implements StoreBbsService {
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	@Autowired
	private Origin originBean;
	
	@Override
	public List<NLPItem> select(String modelName, String startTime, String endTime) throws ServiceException {
		List<NLPItem> nlpItemList = new ArrayList<>();
		List<String> originList = originBean.getOriginList();
		for(String origin : originList) {
			List<StoreBbsPojoBase> storeBbsPojoList = select(modelName, origin, startTime, endTime);
			if(storeBbsPojoList!=null) {
				for(StoreBbsPojoBase pojo : storeBbsPojoList) {
					nlpItemList.add(nlpAnalysisService.NLPitemFactory(modelName, origin, pojo));
				}
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
		Class<?> pojoClass = originBean.getPojoClassFromSource(origin);
		Class<?> pojoExampleClass = originBean.getPojoExampleClassFromSource(origin);
		Class<?> pojoCriteriaClass = originBean.getpojoCriteriaFromSource(origin);
		Method method;
		try {
			Object pojoExamplseClassObj = pojoExampleClass.newInstance();
			method = pojoExampleClass.getMethod("setDistinct", new Class[] { boolean.class });
			method.invoke(pojoExamplseClassObj,false);
			method = pojoExampleClass.getMethod("createCriteria");
			Object pojoCriteriaClassObj = method.invoke(pojoExamplseClassObj);
			if(startTime.equals("") || endTime.equals("")) {
	    		
	    	} else {
	    		method = pojoCriteriaClass.getMethod("andDateGreaterThan", new Class[] { String.class });
	    		method.invoke(pojoCriteriaClassObj,startTime);
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
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSentiment(String modelName, Object obj, Integer sentiment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCategory(String modelName, Object obj, Integer category) {
		// TODO Auto-generated method stub
		
	}

    
    
    
    
}
