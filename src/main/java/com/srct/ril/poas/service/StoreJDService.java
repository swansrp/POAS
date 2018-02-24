package com.srct.ril.poas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.StoreJDMapper;
import com.srct.ril.poas.dao.pojo.ModelMapExample;
import com.srct.ril.poas.dao.pojo.StoreJD;
import com.srct.ril.poas.dao.pojo.StoreJDExample;
import com.srct.ril.poas.dao.pojo.StoreJDExample.Criteria;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.MODEL)
public class StoreJDService {
	@Autowired
	private StoreJDMapper StoreJDDao;
	
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
    	criteria.andDateGreaterThan(startTime);
    	criteria.andDateLessThan(endTime);
    	
    	
    	List<StoreJD> storeJD = StoreJDDao.selectByExample(ex);
        if (storeJD == null) {
            throw new ServiceException("["+modelName+"] store JD from " + startTime + "to" + endTime + " not found" );
        }
        return storeJD;
    }
}
