package com.srct.ril.poas.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.srct.ril.poas.dao.mapper.StoreJDMapper;
import com.srct.ril.poas.dao.pojo.StoreJD;
import com.srct.ril.poas.utils.ServiceException;

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
    public StoreJD select(int modelId) throws ServiceException {
    	StoreJD storeJD = StoreJDDao.selectByPrimaryKey(modelId);
        if (storeJD == null) {
            throw new ServiceException("store JD:" + modelId + " not found");
        }
        return storeJD;
    }
}
