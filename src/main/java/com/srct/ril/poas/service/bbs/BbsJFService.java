package com.srct.ril.poas.service.bbs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.BbsJFMapper;
import com.srct.ril.poas.dao.pojo.BbsJF;
import com.srct.ril.poas.dao.pojo.BbsJFExample;
import com.srct.ril.poas.dao.pojo.BbsJFExample.Criteria;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class BbsJFService {

	@Autowired
	private BbsJFMapper bbsJFDao;
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	public List<BbsJF> select(String modelName, String startTime, String endTime, boolean saveExcel) throws ServiceException {
    	
    	BbsJFExample ex = new BbsJFExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(startTime.equals("") || endTime.equals("")) {
    		
    	} else {
	    	criteria.andDateGreaterThan(startTime);
	    	criteria.andDateLessThan(endTime);
    	}
    	
    	List<BbsJF> BbsJF = bbsJFDao.selectByExample(ex);
        if (BbsJF == null) {
            throw new ServiceException("["+modelName+"] BBS JF from " + startTime + "to" + endTime + " not found" );
        }
        if(saveExcel) {
        	nlpAnalysisService.saveExcel(modelName, "JF", BbsJF);
        }
        return BbsJF;
    }
	
	public List<BbsJF> select(String modelName, String startTime, String endTime) throws ServiceException {
		return select(modelName, startTime, endTime, true);
	}
	
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category) {
		BbsJF record = (BbsJF)obj;
		record.setCategory(category);
    	record.setSentiment(sentiment);
    	bbsJFDao.updateByPrimaryKey(record);
	}
	
	public void updateSentiment(String modelName, Object obj, Integer sentiment) {
		updateAnalysis(modelName,obj,sentiment,null);
    }
    
	public void updateCategory(String modelName, Object obj, Integer category) {
		updateAnalysis(modelName,obj,null,category);
	}
}
