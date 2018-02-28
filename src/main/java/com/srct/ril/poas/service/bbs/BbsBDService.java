package com.srct.ril.poas.service.bbs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.BbsBDMapper;
import com.srct.ril.poas.dao.pojo.BbsBD;
import com.srct.ril.poas.dao.pojo.BbsBDExample;
import com.srct.ril.poas.dao.pojo.BbsBDExample.Criteria;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class BbsBDService {

	@Autowired
	private BbsBDMapper bbsBDDao;
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	public List<BbsBD> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
    	BbsBDExample ex = new BbsBDExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(startTime.equals("") || endTime.equals("")) {
    		
    	} else {
	    	criteria.andDateGreaterThan(startTime);
	    	criteria.andDateLessThan(endTime);
    	}
    	
    	List<BbsBD> BbsBD = bbsBDDao.selectByExample(ex);
        if (BbsBD == null) {
            throw new ServiceException("["+modelName+"] BBS BD from " + startTime + "to" + endTime + " not found" );
        }
        nlpAnalysisService.saveExcel(modelName, "BD", BbsBD);
        return BbsBD;
    }
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category) {
		BbsBD record = (BbsBD)obj;
		record.setCategory(category);
    	record.setSentiment(sentiment);
    	bbsBDDao.updateByPrimaryKey(record);
	}
	
	public void updateSentiment(String modelName, Object obj, Integer sentiment) {
		updateAnalysis(modelName,obj,sentiment,null);
    }
    
	public void updateCategory(String modelName, Object obj, Integer category) {
		updateAnalysis(modelName,obj,null,category);
	}
	
	
}
