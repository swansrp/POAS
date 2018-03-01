package com.srct.ril.poas.service.store;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.StoreTBMapper;
import com.srct.ril.poas.dao.pojo.StoreTB;
import com.srct.ril.poas.dao.pojo.StoreTBExample;
import com.srct.ril.poas.dao.pojo.StoreTBExample.Criteria;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreTBService {

	@Autowired
	private StoreTBMapper storeTBDao;
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	public List<StoreTB> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
    	StoreTBExample ex = new StoreTBExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(startTime.equals("") || endTime.equals("")) {
    		
    	} else {
	    	criteria.andDateGreaterThan(startTime);
	    	criteria.andDateLessThan(endTime);
    	}
    	
    	List<StoreTB> StoreTB = storeTBDao.selectByExample(ex);
        if (StoreTB == null) {
            throw new ServiceException("["+modelName+"] store TB from " + startTime + "to" + endTime + " not found" );
        }

        return StoreTB;
    }

    public List<StoreTB> select(String modelName, String startTime, String endTime, HttpServletResponse response) throws ServiceException, IOException {
    	List<StoreTB> storeTBList = select(modelName, startTime, endTime);
		nlpAnalysisService.saveExcel(modelName, "TB", storeTBList).write(response.getOutputStream());
		return storeTBList;
    }
	
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category) {
		StoreTB record = (StoreTB)obj;
		record.setCategory(category);
    	record.setSentiment(sentiment);
    	storeTBDao.updateByPrimaryKey(record);
	}
	
	public void updateSentiment(String modelName, Object obj, Integer sentiment) {
		updateAnalysis(modelName,obj,sentiment,null);
    }
    
	public void updateCategory(String modelName, Object obj, Integer category) {
		updateAnalysis(modelName,obj,null,category);
	}
}
