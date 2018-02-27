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
	
	public List<StoreAMZ> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
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
        
        nlpAnalysisService.saveExcel(modelName, "AMZ", StoreAMZ);
        return StoreAMZ;
    }
}
