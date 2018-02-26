package com.srct.ril.poas.service.ai;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.NLPAnalysis;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPCommentTag;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPDepParser;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPSentiment;
import com.srct.ril.poas.ai.category.Category;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.utils.ExcelUtils;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
public class NLPAnalysisService {
	@Autowired
	private BaiduNLPService baiduService;
	
	@Autowired
	private Category cat;
	
	private double confidence = 0.7;
	private double prob = 0.7;
	private int mode = 0;
	
	private BaiduNLPDepParser defParser(String content) throws ServiceException {
		BaiduNLPDepParser dp = null;
		try {
			dp = baiduService.depParser(content);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("分句异常 ", e);
		}
		Log.dd(getClass(), dp.simpleText);
		return dp;
	}
	
	private boolean setimentClassify(String subContent) throws ServiceException {
		boolean sentimental = false;
		BaiduNLPSentiment sc = null;
		try {
			sc = baiduService.sentimentClassify(subContent);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("提取情感异常 ", e);
		}
		
		if(sc == null) {
			Log.i(getClass(), "提取情感失败");
		} else {
			Log.ii(getClass(), sc);
			if(sc.items[0].confidence.doubleValue() > confidence) {
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
		}
		Log.i(getClass(), ">>{}-->判定情感{}", subContent, sentimental?"成功":"失败");
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
		Log.ii(getClass(), ct);
		if(ct.items==null) {
			return null;
		} else {
			return ct;
		}
	}
	
	private NLPAnalysis.Item parseCategory(boolean sentimental, String subContent, BaiduNLPDepParser dp) throws ServiceException {
		NLPAnalysis.Item it = new NLPAnalysis.Item();
		it.setSubContent(subContent);
		if(sentimental) {
			it.setSentiment(mode);
			Log.i(getClass(), "==明确情感分析({})开始获取观点==", mode);
			BaiduNLPCommentTag ct = commentTag(subContent);
			if(ct == null) {
				Log.i(getClass(), "获取观点失败");
				String key=cat.getCategory(dp.getKeyWords(subContent));
				it.setCategory(key);
			} else {
				it.setAdj(ct.items[0].adj);
				it.setProp(ct.items[0].prop);
				it.setSentiment(ct.items[0].sentiment);
				String key=cat.getCategory(dp.getKeyWords(subContent));
				if(key==null) {
					Log.i(getClass(), "收集情感关键字");
					cat.addUnknownKeywordList(ct.items[0].prop);
				} else {
					it.setCategory(key);
				}
			}
		} else {
			String key=cat.getCategory(dp.getKeyWords(subContent));
			it.setCategory(key);
			it.setSentiment(-1);
		}
		if(it.getCategory() == null) {
			Log.i(getClass(), "分句匹配");
			String key = cat.getCategory(subContent);
			it.setCategory(key);
		}
		return it;
	}
	
	//0表示消极，1表示中性，2表示积极
	private NLPAnalysis _nlp(String content) throws ServiceException {
		
		NLPAnalysis res = new NLPAnalysis(mode);
		res.setContent(content);
		//Step 1. 分句
		Log.i("\n======{}======",content);
		BaiduNLPDepParser dp = defParser(content);
		boolean getCategorySubcontent = false;
		if(dp != null) {
			for(String subContent :dp.simpleText) {
				Log.i(getClass(), "\n >>>>{}<<<<",subContent);
				//Step 2. 各分句情感分析
				boolean sentimental = setimentClassify(subContent);
				//Step 3. 根据情感分析结果分类
				NLPAnalysis.Item it = parseCategory(sentimental, subContent, dp);
				if(it.getCategory() != null) {
					getCategorySubcontent = true;
					Log.i(getClass(), "<{}>情感关键词获取<{}>", subContent, it.getCategory());
				}
				res.addItem(it);			
			}
		}
		if(getCategorySubcontent == false) {
			Log.i(getClass(), "分句提取失败");
			boolean sentimental = setimentClassify(content);
			BaiduNLPCommentTag ct = commentTag(content);
			Log.ii(getClass(), ct);
		}
		Log.d(getClass(), JSONUtil.toJSONString(res));
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
			res.add(nlp(content));
		}
		ExcelUtils.NLP_WriteToExcel(res);
		return res;
	}
}