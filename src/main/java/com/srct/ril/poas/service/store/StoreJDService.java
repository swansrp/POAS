package com.srct.ril.poas.service.store;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.StoreJDMapper;
import com.srct.ril.poas.dao.pojo.StoreJD;
import com.srct.ril.poas.dao.pojo.StoreJDExample;
import com.srct.ril.poas.dao.pojo.StoreJDExample.Criteria;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreJDService {
	@Autowired
	private StoreJDMapper storeJDDao;
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	 /**
     * Get product by id
     * If not found product will throw ServiceException
     *
     * @param modelId
     * @return
     * @throws ServiceException
     */
    public List<StoreJD> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
    	StoreJDExample ex = new StoreJDExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(startTime.equals("") || endTime.equals("")) {
    		
    	} else {
	    	criteria.andDateGreaterThan(startTime);
	    	criteria.andDateLessThan(endTime);
    	}
    	
    	List<StoreJD> storeJD = storeJDDao.selectByExample(ex);
        if (storeJD == null) {
            throw new ServiceException("["+modelName+"] store JD from " + startTime + "to" + endTime + " not found" );
        }
        return storeJD;
    }
    
    public List<StoreJD> select(String modelName, String startTime, String endTime, HttpServletResponse response) throws ServiceException, IOException {
    	List<StoreJD> storeJDList = select(modelName, startTime, endTime);
		nlpAnalysisService.saveExcel(modelName, "JD", storeJDList).write(response.getOutputStream());
		return storeJDList;
    }
    
	public void updateAnalysis(String modelName, Object obj, Integer sentiment, Integer category) {
		StoreJD record = (StoreJD)obj;
		record.setCategory(category);
    	record.setSentiment(sentiment);
    	storeJDDao.updateByPrimaryKey(record);
	}
	
	public void updateSentiment(String modelName, Object obj, Integer sentiment) {
		updateAnalysis(modelName,obj,sentiment,null);
    }
    
	public void updateCategory(String modelName, Object obj, Integer category) {
		updateAnalysis(modelName,obj,null,category);
	}
}
