package com.srct.ril.poas.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DataSourceConfig;
import com.srct.ril.poas.dao.mapper.ModelMapMapper;
import com.srct.ril.poas.dao.pojo.ModelMap;
import com.srct.ril.poas.dao.pojo.ModelMapExample;
import com.srct.ril.poas.dao.pojo.ModelMapExample.Criteria;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
public class ModelMapService {
	@Autowired
	private ModelMapMapper modelMapDao;
	
	@Autowired
	DataSourceConfig dsc;
	
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

	public int addModel(String modelName) {
		// TODO Auto-generated method stub
		ModelMap model = new ModelMap();
		model.setModelName(modelName);
		modelMapDao.insertSelective(model);
		dsc.updateDynamicDataSource();
		return model.getId();
	}
	
	public int getId(String modelName) throws ServiceException {
		ModelMapExample ex = new ModelMapExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	criteria.andModelNameEqualTo(modelName);
    	List<ModelMap> modelMapList = modelMapDao.selectByExample(ex);
    	if (modelMapList == null) {
            throw new ServiceException(modelName + " in modelmap_ not found");
        }
        return modelMapList.get(0).getId();
	}
	
	
	
}
