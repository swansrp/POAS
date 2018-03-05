package com.srct.ril.poas.controller.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.pojo.UrlMap;
import com.srct.ril.poas.service.config.UrlMapService;
import com.srct.ril.poas.utils.ServiceException;

@RestController
@RequestMapping("/config/url")
public class UrlMapController {
	
	@Autowired
	UrlMapService urlMapService;
	
	@RequestMapping("/add")
	public int addUrl(
			@RequestParam(value = "weburl", required = true) 
			String url,
			@RequestParam(value = "modelname", required = true)
			String modelName,
			@RequestParam(value = "webname", required = true)
			String srcCN) throws ServiceException {
		return urlMapService.addUrl(url, modelName, srcCN);
	}
	
	@RequestMapping("/get")
	public List<UrlMap> getUrl(
			@RequestParam(value = "modelname", required = true)
			String modelName,
			@RequestParam(value = "webname", required = true)
			String srcCN) throws ServiceException {
		return urlMapService.getUrl(modelName, srcCN);
	}
	
	@RequestMapping("/del")
	public void getUrl(
			@RequestParam(value = "weburl", required = true) 
			String url) throws ServiceException {
		urlMapService.delUrl(url);
	}
	
}
