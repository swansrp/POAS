package com.srct.ril.poas.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.mapper.SourceMapMapper;
import com.srct.ril.poas.dao.pojo.SourceMap;
import com.srct.ril.poas.dao.pojo.SourceMapExample;
import com.srct.ril.poas.dao.pojo.SourceMapExample.Criteria;
import com.srct.ril.poas.utils.ServiceException;

@Service
public class SourceMapService {
	@Autowired
	private SourceMapMapper sourceMapDao;
	
	public int getId(String srcCn) throws ServiceException {
		SourceMapExample ex = new SourceMapExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	criteria.andSourceCnEqualTo(srcCn);
    	List<SourceMap> sourceMapList = sourceMapDao.selectByExample(ex);
        if (sourceMapList == null) {
            throw new ServiceException("Product:" + srcCn + " not found");
        }
        return sourceMapList.get(0).getId();
    }
	
	public List<String> getNameList() {
		SourceMapExample ex = new SourceMapExample();
    	ex.setDistinct(false);
    	List<SourceMap> sourceMapList = sourceMapDao.selectByExample(ex);
    	List<String> sourceMapNameList = new ArrayList<String>();
    	for(SourceMap item : sourceMapList) {
    		sourceMapNameList.add(item.getSourceCn());
    	}
    	return sourceMapNameList;
	}
}
