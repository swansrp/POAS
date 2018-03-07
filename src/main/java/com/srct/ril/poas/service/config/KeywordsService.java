package com.srct.ril.poas.service.config;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.baidunlp.BaiduNLPLexer;
import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.KeywordMapper;
import com.srct.ril.poas.dao.pojo.Keyword;
import com.srct.ril.poas.dao.pojo.KeywordExample;
import com.srct.ril.poas.dao.utils.category.Category;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
@DS(DataSourceEnum.CONFIG)
public class KeywordsService {
	
	@Autowired
	private KeywordMapper keywordDao;
	
	@Autowired
	private Category catBean;
	
	@Autowired
	private BaiduNLPService baiduNLPService;
	
	public void initKeywords() {
		KeywordExample ex = new KeywordExample();
		ex.setDistinct(false);
		List<Keyword> keywordList = keywordDao.selectByExample(ex);
		for(Keyword key : keywordList) {
			catBean.addCategory(key);
		}
		Log.dd(catBean);
	}
	
	public List<Keyword> getKeywords() {
		KeywordExample ex = new KeywordExample();
		ex.setDistinct(false);
		List<Keyword> keywordList = keywordDao.selectByExample(ex);
		return keywordList;
	}
	
	public void nlpKeywords() throws ServiceException {
		Map<String, List<String>> keywordMap = catBean.getKeywordMap();
		for(Entry<String, List<String>> entry : keywordMap.entrySet()) {
			for(String str : entry.getValue()) {
				BaiduNLPLexer lexer = baiduNLPService.lexer(str);
				Log.ii(lexer);
			}
		}		
	}
	
	public Map<String, List<String>> getKeywordsMap() {
		return catBean.getAlias2catMap();
	}
	
}
