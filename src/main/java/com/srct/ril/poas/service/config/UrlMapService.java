package com.srct.ril.poas.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.UrlMapMapper;
import com.srct.ril.poas.dao.pojo.UrlMap;
import com.srct.ril.poas.utils.ServiceException;

@Service
@DS(DataSourceEnum.CONFIG)
public class UrlMapService {
	@Autowired
	private UrlMapMapper urlMapDao;
	
	@Autowired
	private SourceMapService sourceMapService;
	
	@Autowired
	private ModelMapService modelMapService;
	
	public int addUrl(String url, String modelName, String srcCN) throws ServiceException {
		UrlMap urlMap = new UrlMap();
		urlMap.setUrl(url);
		urlMap.setModelId(modelMapService.getId(modelName));
		urlMap.setSourceId(sourceMapService.getId(srcCN));
		urlMapDao.insertSelective(urlMap);
		return urlMap.getId();
	}
}
