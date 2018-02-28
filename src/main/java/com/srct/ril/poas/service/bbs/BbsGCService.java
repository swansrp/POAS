package com.srct.ril.poas.service.bbs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.BbsGCMapper;
import com.srct.ril.poas.dao.pojo.BbsGC;
import com.srct.ril.poas.dao.pojo.BbsGCExample;
import com.srct.ril.poas.dao.pojo.BbsGCExample.Criteria;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class BbsGCService {

	@Autowired
	private BbsGCMapper bbsGCDao;
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	public List<BbsGC> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
    	BbsGCExample ex = new BbsGCExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(startTime.equals("") || endTime.equals("")) {
    		
    	} else {
	    	criteria.andDateGreaterThan(startTime);
	    	criteria.andDateLessThan(endTime);
    	};
    	
    	List<BbsGC> BbsGC = bbsGCDao.selectByExample(ex);
        if (BbsGC == null) {
            throw new ServiceException("["+modelName+"] BBS GC from " + startTime + "to" + endTime + " not found" );
        }
        nlpAnalysisService.saveExcel(modelName, "GC", BbsGC);
        return BbsGC;
    }
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category) {
		BbsGC record = (BbsGC)obj;
		record.setCategory(category);
    	record.setSentiment(sentiment);
    	bbsGCDao.updateByPrimaryKey(record);
	}
	
	public void updateSentiment(String modelName, Object obj, Integer sentiment) {
		updateAnalysis(modelName,obj,sentiment,null);
    }
    
	public void updateCategory(String modelName, Object obj, Integer category) {
		updateAnalysis(modelName,obj,null,category);
	}
}
