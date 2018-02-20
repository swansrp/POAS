package com.srct.ril.poas.service.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPCommentTag;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPDepParser;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.Log;
import com.srct.ril.poas.utils.ServiceException;

@Service
public class NLPAnalysisService {
	@Autowired
	private BaiduNLPService baiduService;
	
	//0表示消极，1表示中性，2表示积极
	public NLPAnalysis nlp(String content, int mode) throws ServiceException {
		NLPAnalysis res = new NLPAnalysis(mode);
		res.setContent(content);
		BaiduNLPDepParser dp = baiduService.depParser(content);
		for(String str :dp.simpleText) {
			NLPAnalysis.Item it = new NLPAnalysis.Item();
			Log.i(getClass(), ">>>>"+str);
			BaiduNLPCommentTag ct = baiduService.commentTag(str);
			Log.i(getClass(), JSONUtil.toJSONString(ct));
			if(ct.items!=null) {
				it.setSubContent(str);
				it.setAdj(ct.items[0].adj);
				it.setProp(ct.items[0].prop);
				it.setSentiment(ct.items[0].sentiment);
				res.addItem(it);
			}
		}
		Log.i(getClass(), JSONUtil.toJSONString(res));
		return res;
	}
	
	public NLPAnalysis nlp(String content) throws ServiceException {
		return nlp(content, 1);
	}
}