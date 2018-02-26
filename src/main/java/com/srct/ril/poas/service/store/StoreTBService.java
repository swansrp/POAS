package com.srct.ril.poas.service.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.StoreTBMapper;
import com.srct.ril.poas.dao.pojo.StoreTB;
import com.srct.ril.poas.dao.pojo.StoreTBExample;
import com.srct.ril.poas.dao.pojo.StoreTBExample.Criteria;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreTBService {

	@Autowired
	private StoreTBMapper storeTBDao;
	
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
}
