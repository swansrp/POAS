package com.srct.ril.poas.service.ai;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.NLPItem;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPCommentTag;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPDepParser;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPLexer;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPSentiment;
import com.srct.ril.poas.ai.category.Category;
import com.srct.ril.poas.ai.origin.Origin;
import com.srct.ril.poas.dao.pojo.StoreJD;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.utils.ExcelUtils;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
public class NLPAnalysisService {
	@Autowired
	private BaiduNLPService baiduService;
	
	@Autowired
	private Category cat;
	@Autowired
	private Origin ori;
	
	private static enum Setiment{
		UNKNOWN(-1),
		POSITIVE(2),
		NEGATIVE(0),
		NEUTRAL(1),
		ALL(3);
		
		private int value;
		private Setiment(int v) {
			this.value = v;
		}
		public int getValue() {
			return this.value;
		}
		public static Setiment getSetiment(int value) {
			switch (value) {
			case -1: return UNKNOWN;
			case 0:  return NEGATIVE;		
			case 1:  return NEUTRAL;	
			case 2:	 return POSITIVE;
			case 3:  return ALL;
			default: return UNKNOWN;
			}
		}
	}
	
	private double confidence = 0.7;
	private double prob = 0.65;
	private Setiment mode = Setiment.ALL;
	
	private BaiduNLPDepParser defParser(String content) throws ServiceException {
		BaiduNLPDepParser dp = null;
		try {
			dp = baiduService.depParser(content);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("分句异常 ", e);
		}
		Log.dd(dp.simpleText);
		return dp;
	}
	
	private Setiment setimentClassify(String subContent) throws ServiceException {
		Setiment sentimental = Setiment.UNKNOWN;
		BaiduNLPSentiment sc = null;
		try {
			sc = baiduService.sentimentClassify(subContent);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("提取情感异常 ", e);
		}
		
		if(sc == null) {
			Log.i("提取情感失败");
		} else {
			Log.ii(sc);
			if(sc.items[0].confidence.doubleValue() > confidence) {
				if(sc.items[0].sentiment.intValue() == mode.getValue())
					sentimental = mode;
				if(mode == Setiment.ALL){
					sentimental = Setiment.getSetiment(sc.items[0].sentiment.intValue());
				}
			}
			if(sentimental == Setiment.UNKNOWN) {
				switch(mode) {
					case NEGATIVE: 
						if(sc.items[0].negative_prob.doubleValue()>prob) 
							sentimental = mode;; 
						break;
					case NEUTRAL:
						break;
					case POSITIVE: 
						if(sc.items[0].positive_prob.doubleValue()>prob) 
							sentimental = mode; 
						break;
					case ALL:
						if(sc.items[0].negative_prob.doubleValue()>prob) 
							sentimental=Setiment.NEGATIVE;
						else if(sc.items[0].positive_prob.doubleValue()>prob)
							sentimental=Setiment.POSITIVE;
						break;					
					default: 
						break;
				}
			}
		}
		Log.i(">>{}-->判定情感{}", subContent, sentimental.getValue());
		return sentimental;
	}
	
	private BaiduNLPCommentTag commentTag(String subContent) throws ServiceException {
		BaiduNLPCommentTag ct = null;
		try {
			ct = baiduService.commentTag(subContent);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("提取观点异常 ", e);
		}
		Log.ii(ct);
		if(ct.items==null) {
			return null;
		} else {
			return ct;
		}
	}
	
	private BaiduNLPLexer lexer(String content) throws ServiceException {
		BaiduNLPLexer lexer = null;
		try {
			lexer = baiduService.lexer(content);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("提取单词异常 ", e);
		}
		return lexer;
	}
	
	private NLPAnalysis.Item parseCategory(Setiment sentimental, String subContent, List<String> keyWords) throws ServiceException {
		NLPAnalysis.Item it = new NLPAnalysis.Item();
		it.setSubContent(subContent);
		it.setSentiment(sentimental.getValue());
		if(sentimental!=Setiment.UNKNOWN) {
			Log.i("==明确情感分析({})开始获取观点==", mode);
			BaiduNLPCommentTag ct = commentTag(subContent);
			if(ct == null) {
				Log.i("获取观点失败");
				String key=cat.getCategory(keyWords);
				it.setCategory(key);
			} else {
				it.setAdj(ct.items[0].adj);
				it.setProp(ct.items[0].prop);
				it.setSentiment(ct.items[0].sentiment);
				String key=cat.getCategory(keyWords);
				if(key==null) {
					Log.i("收集情感关键字");
					cat.addUnknownKeywordList(ct.items[0].prop);
				} else {
					it.setCategory(key);
				}
			}
		} else {
			String key=cat.getCategory(keyWords);
			it.setCategory(key);
		}
		if(it.getCategory() == null) {
			Log.i("分句匹配");
			String key = cat.getCategory(subContent);
			it.setCategory(key);
		}
		return it;
	}
	
	//0表示消极，1表示中性，2表示积极
	private NLPAnalysis _nlp(String content) throws ServiceException {
		
		NLPAnalysis res = new NLPAnalysis(mode.getValue());
		res.setContent(content);
		//Step 1. 分句
		Log.i("\n======{}======",content);
		BaiduNLPDepParser dp = defParser(content);
		boolean getCategorySubcontent = false;
		if(dp != null) {
			for(String subContent :dp.simpleText) {
				Log.i("\n >>>>{}<<<<",subContent);
				//Step 2. 各分句情感分析
				Setiment sentimental = setimentClassify(subContent);
				//Step 3. 根据情感分析结果分类
				NLPAnalysis.Item it = parseCategory(sentimental, subContent, dp.getKeyWords(subContent));
				if(it.getCategory() != null) {
					getCategorySubcontent = true;
					Log.i("<{}>情感关键词获取<{}>", subContent, it.getCategory());
				}
				res.addItem(it);			
			}
		}
		if(getCategorySubcontent == false) {
			Log.i("分句提取失败,全句提取");
			BaiduNLPLexer lexer = lexer(content);
			Setiment sentimental = setimentClassify(content);
			NLPAnalysis.Item it = parseCategory(sentimental, content, lexer.getKeywords());
			if(it.getCategory() != null) {
				getCategorySubcontent = true;
				Log.i("<{}>情感关键词获取<{}>", content, it.getCategory());
			}
			res.addItem(it);	
		}
		if(getCategorySubcontent == false) {
			Log.w("分类失败");
		}
		//Log.d(JSONUtil.toJSONString(res));
		return res;
	}
	
	public NLPAnalysis nlp(String content) throws ServiceException {
		List<NLPAnalysis> NLPAnalysisList = new ArrayList<>();
		NLPAnalysis nlpAnalysis = _nlp(content);
		NLPAnalysisList.add(nlpAnalysis);
		ExcelUtils.NLP_WriteToExcel(NLPAnalysisList);
		return nlpAnalysis;
	}
	
	public List<NLPAnalysis> nlpList(List<String> contentList) throws ServiceException {
		List<NLPAnalysis> res = new ArrayList<>();
		for(String content : contentList) {
			res.add(_nlp(content));
		}
		ExcelUtils.NLP_WriteToExcel(res);
		return res;
	}
	
	public void saveExcel(String modelName, String origin, Object dataList) {
		List<NLPItem> res = new ArrayList<>();
		Class<?> clazz = ori.getClassFromSource(origin);
		for(Object obj : (List<Object>)dataList) {
			res.add(new NLPItem(clazz, obj, origin, modelName));
		}
		ExcelUtils.NLPItem_WriteToExcel(res);
	}
	
	
}