package com.srct.ril.poas.service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.category.Category;
import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.dao.mapper.KeywordMapper;
import com.srct.ril.poas.dao.pojo.Keyword;
import com.srct.ril.poas.dao.pojo.KeywordExample;
import com.srct.ril.poas.utils.log.Log;

@Service
@DS(DataSourceEnum.CONFIG)
public class KeywordsService {
	
	@Autowired
	private KeywordMapper keywordDao;
	
	@Autowired
	private Category catBean;
	
	public void initKeywords() {
		KeywordExample ex = new KeywordExample();
		ex.setDistinct(false);
		List<Keyword> keywordList = keywordDao.selectByExample(ex);
		for(Keyword key : keywordList) {
			catBean.addCategory(key);
		}
		Log.dd(catBean);
	}
	
}
