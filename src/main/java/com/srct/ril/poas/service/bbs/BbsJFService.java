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
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class BbsJFService {

	@Autowired
	private BbsJFMapper bbsJFDao;
	
	public List<BbsJF> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
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
        return BbsJF;
    }
}
