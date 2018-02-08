package com.srct.ril.poas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.mapper.ModelMapMapper;
import com.srct.ril.poas.dao.pojo.ModelMap;
import com.srct.ril.poas.dao.pojo.ModelMapExample;
import com.srct.ril.poas.utils.ServiceException;

@Service
public class ModelMapService {
	@Autowired
	private ModelMapMapper modelMapDao;
	
	 /**
     * Get product by id
     * If not found product will throw ServiceException
     *
     * @param modelId
     * @return
     * @throws ServiceException
     */
    public ModelMap select(int modelId) throws ServiceException {
    	ModelMap modelMap = modelMapDao.selectByPrimaryKey(modelId);
        if (modelMap == null) {
            throw new ServiceException("Product:" + modelId + " not found");
        }
        return modelMap;
    }
    
    public List<String> getNameList() {
    	ModelMapExample ex = new ModelMapExample();
    	ex.setDistinct(false);
    	List<ModelMap> modelMapList = modelMapDao.selectByExample(ex);
    	List<String> modelMapNameList = new ArrayList<String>();
    	for(ModelMap item : modelMapList) {
    		modelMapNameList.add(item.getModelName());
    	}
    	return modelMapNameList;
    }
	
	
	
}
