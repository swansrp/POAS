package com.srct.ril.poas.service.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPCommentTag;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPDepParser;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPSentiment;
import com.srct.ril.poas.ai.category.Category;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
public class NLPAnalysisService {
	@Autowired
	private BaiduNLPService baiduService;
	
	@Autowired
	private Category cat;
	
	//0表示消极，1表示中性，2表示积极
	public NLPAnalysis nlp(String content, int mode, double confidence, double prob) throws ServiceException {
		NLPAnalysis res = new NLPAnalysis(mode);
		res.setContent(content);
		//Step 1. 分句
		BaiduNLPDepParser dp = baiduService.depParser(content);
		for(String str :dp.simpleText) {
			Log.i(getClass(), "\n >>>>{}<<<<",str);
			//Step 2. 各分句情感分析
			BaiduNLPSentiment sc = baiduService.sentimentClassify(str);
			Log.d(getClass(), JSONUtil.toJSONString(sc));
			boolean sentimental = false;
			if(sc.items[0].confidence.doubleValue() > confidence) {
				
				if(sc.items[0].sentiment.intValue()==mode)
					sentimental=true; 
				else if(sc.items[0].sentiment.intValue() == 1){
					
				} else {
					Log.i(getClass(), "sentiment:" + sc.items[0].sentiment.intValue() + " ignore!!");
					continue;
				}
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
			NLPAnalysis.Item it = new NLPAnalysis.Item();
			// 若分句有强烈情感色彩 做观点提取
			if(sentimental) {
				Log.i(getClass(), "==明确情感分析({})开始获取观点==",mode);
				BaiduNLPCommentTag ct = baiduService.commentTag(str);
				Log.d(getClass(), JSONUtil.toJSONString(ct));
				it.setSubContent(str);
				it.setSentiment(mode);
				if(ct.items!=null) {
					it.setAdj(ct.items[0].adj);
					it.setProp(ct.items[0].prop);
					it.setSentiment(ct.items[0].sentiment);
					String key=cat.getCategory(dp.getKeyWords(str));
					if(key==null) {
						Log.i(getClass(), "收集情感关键字");
						it.setUnKnownKeyWords(ct.items[0].prop);
					} else {
						it.setCategory(key);
					}
				} else {
					Log.i(getClass(), "获取观点失败");
					String key=cat.getCategory(dp.getKeyWords(str));
					it.setCategory(key);
				}
			} else {
				String key=cat.getCategory(dp.getKeyWords(str));
				it.setCategory(key);
				it.setSentiment(-1);
			}
			if(it.getCategory() == null) {
				Log.i(getClass(), "分句匹配");
				String key = cat.getCategory(str);
				it.setCategory(key);
			}
			Log.i(getClass(), "<{}>情感关键词获取<{}>", str, it.getCategory());
			res.addItem(it);			
		}
		Log.d(getClass(), JSONUtil.toJSONString(res));
		return res;
	}
	
	public NLPAnalysis nlp(String content) throws ServiceException {
		return nlp(content, 0, 0.6, 0.7);
	}
}