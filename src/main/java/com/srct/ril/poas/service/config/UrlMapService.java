package com.srct.ril.poas.service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.UrlJoinMapper;
import com.srct.ril.poas.dao.mapper.UrlMapMapper;
import com.srct.ril.poas.dao.pojo.UrlJoinMap;
import com.srct.ril.poas.dao.pojo.UrlMap;
import com.srct.ril.poas.dao.pojo.UrlMapExample;
import com.srct.ril.poas.dao.pojo.UrlMapExample.Criteria;
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
	
	@Autowired
	private UrlJoinMapper urlJoinDao;
	public int addUrl(String url, String modelName, String srcCN) throws ServiceException {
		UrlMap urlMap = new UrlMap();
		urlMap.setUrl(url);
		urlMap.setModelId(modelMapService.getId(modelName));
		urlMap.setSourceId(sourceMapService.getId(srcCN));
		urlMapDao.insertSelective(urlMap);
		return urlMap.getId();
	}
	
	public List<UrlMap> getUrl(String modelName, String srcCN) throws ServiceException {
		UrlMapExample ex = new UrlMapExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	if(modelName != null && !modelName.equals("")) {
    		criteria.andModelIdEqualTo(modelMapService.getId(modelName));
    	}
    	if(srcCN != null && !srcCN.equals("")) {
    		criteria.andSourceIdEqualTo(sourceMapService.getId(srcCN));
    	}
    	List<UrlMap> urlMapList = urlMapDao.selectByExample(ex);
    	return urlMapList;
	}
	
	public void delUrl(String webUrl) {
		UrlMapExample ex = new UrlMapExample();
    	ex.setDistinct(false);
    	Criteria criteria = ex.createCriteria();
    	criteria.andUrlEqualTo(webUrl);
    	urlMapDao.deleteByExample(ex);
	}
	
	public List<UrlJoinMap> getUrlJoinList() throws ServiceException{
		List<UrlJoinMap> urlJoinMaps= urlJoinDao.selectByJoin();
		return urlJoinMaps;
	}
}
