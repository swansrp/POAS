package com.srct.ril.poas.controller.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.dao.pojo.Keyword;
import com.srct.ril.poas.service.config.KeywordsService;
import com.srct.ril.poas.utils.ServiceException;

@RestController
@RequestMapping("/config/keyword")
public class KeywordsController {
	
	@Autowired
	KeywordsService keywordsService;
	
	
	@RequestMapping("/get")
	public List<Keyword> getKeywords() 
			throws ServiceException {
		return keywordsService.getKeywords();
	}
	
	@RequestMapping("/nlp")
	public void nlpKeywords() 
			throws ServiceException {
		keywordsService.nlpKeywords();
	}
	
	@RequestMapping("/getmap")
	public Map<String, List<String>> getKeywordsMap() 
			throws ServiceException {
		return keywordsService.getKeywordsMap();
	}
}
