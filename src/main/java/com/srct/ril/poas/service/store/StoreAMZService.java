package com.srct.ril.poas.service.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.StoreAMZMapper;
import com.srct.ril.poas.dao.pojo.StoreAMZ;
import com.srct.ril.poas.dao.pojo.StoreAMZExample;
import com.srct.ril.poas.dao.pojo.StoreAMZExample.Criteria;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreAMZService {
	@Autowired
	private StoreAMZMapper storeAMZDao;
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	public List<StoreAMZ> select(String modelName, String startTime, String endTime, boolean saveExcel) throws ServiceException {
    	
    	StoreAMZExample ex = new StoreAMZExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(startTime.equals("") || endTime.equals("")) {
    		
    	} else {
	    	criteria.andDateGreaterThan(startTime);
	    	criteria.andDateLessThan(endTime);
    	}
    	
    	List<StoreAMZ> StoreAMZ = storeAMZDao.selectByExample(ex);
        if (StoreAMZ == null) {
            throw new ServiceException("["+modelName+"] store AMZ from " + startTime + "to" + endTime + " not found" );
        }
        if(saveExcel) {
        	nlpAnalysisService.saveExcel(modelName, "AMZ", StoreAMZ);
        }
        return StoreAMZ;
    }
	
	public List<StoreAMZ> select(String modelName, String startTime, String endTime) throws ServiceException {
		return select(modelName, startTime, endTime, true);
	}
	
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category) {
		StoreAMZ record = (StoreAMZ)obj;
		record.setCategory(category);
    	record.setSentiment(sentiment);
    	storeAMZDao.updateByPrimaryKey(record);
	}
	
	public void updateSentiment(String modelName, Object obj, Integer sentiment) {
		updateAnalysis(modelName,obj,sentiment,null);
    }
    
	public void updateCategory(String modelName, Object obj, Integer category) {
		updateAnalysis(modelName,obj,null,category);
	}
}
