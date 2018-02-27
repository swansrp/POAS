package com.srct.ril.poas.service.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.StoreTMMapper;
import com.srct.ril.poas.dao.pojo.StoreTM;
import com.srct.ril.poas.dao.pojo.StoreTMExample;
import com.srct.ril.poas.dao.pojo.StoreTMExample.Criteria;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreTMService {

	@Autowired
	private StoreTMMapper storeTMDao;
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	public List<StoreTM> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
    	StoreTMExample ex = new StoreTMExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(startTime.equals("") || endTime.equals("")) {
    		
    	} else {
	    	criteria.andDateGreaterThan(startTime);
	    	criteria.andDateLessThan(endTime);
    	}
    	
    	List<StoreTM> StoreTM = storeTMDao.selectByExample(ex);
        if (StoreTM == null) {
            throw new ServiceException("["+modelName+"] store TM from " + startTime + "to" + endTime + " not found" );
        }
        nlpAnalysisService.saveExcel(modelName, "TM", StoreTM);
        return StoreTM;
    }
}
