package com.srct.ril.poas.service.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPCommentTag;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPDepParser;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPSentiment;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.Log;
import com.srct.ril.poas.utils.ServiceException;

@Service
public class NLPAnalysisService {
	@Autowired
	private BaiduNLPService baiduService;
	
	//0表示消极，1表示中性，2表示积极
	public NLPAnalysis nlp(String content, int mode, double confidence, double prob) throws ServiceException {
		NLPAnalysis res = new NLPAnalysis(mode);
		res.setContent(content);
		//Step 1. 分句
		BaiduNLPDepParser dp = baiduService.depParser(content);
		for(String str :dp.simpleText) {
			Log.i(getClass(), ">>>>"+str);
			//Step 2. 各分句情感分析
			BaiduNLPSentiment sc = baiduService.sentimentClassify(str);
			Log.i(getClass(), JSONUtil.toJSONString(sc));
			boolean sentimental = false;
			if(sc.items[0].confidence.doubleValue() > confidence) {
				Log.i(getClass(), "sentiment:" + sc.items[0].sentiment.intValue());
				if(sc.items[0].sentiment.intValue()==mode)
					sentimental=true; 
			}
			if(sentimental==false) {
				switch(mode) {
					case 0: 
						if(sc.items[0].negative_prob.doubleValue()>prob) 
							sentimental=true; 
						break;
					case 1: 
						break;
					case 2: 
						if(sc.items[0].positive_prob.doubleValue()>prob) 
							sentimental=true; 
						break;
					default: 
						break;
				}
			}
			// 若分句有强烈情感色彩 做观点提取
			if(sentimental) {
				NLPAnalysis.Item it = new NLPAnalysis.Item();
				BaiduNLPCommentTag ct = baiduService.commentTag(str);
				Log.i(getClass(), JSONUtil.toJSONString(ct));
				it.setSubContent(str);
				it.setSentiment(mode);
				if(ct.items!=null) {
					it.setAdj(ct.items[0].adj);
					it.setProp(ct.items[0].prop);
					it.setSentiment(ct.items[0].sentiment);
				}
				res.addItem(it);
			}
		}
		Log.i(getClass(), JSONUtil.toJSONString(res));
		return res;
	}
	
	public NLPAnalysis nlp(String content) throws ServiceException {
		return nlp(content, 0, 0.6, 0.7);
	}
}