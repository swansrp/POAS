package com.srct.ril.poas.service.ai.nlp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.baidunlp.BaiduNLPCommentTag;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPDepParser;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPLexer;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPSentiment;
import com.srct.ril.poas.ai.nlp.MyAnsj;
import com.srct.ril.poas.ai.nlp.NLPAnalysis;
import com.srct.ril.poas.ai.nlp.NLPItem;
import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.dao.utils.category.Category;
import com.srct.ril.poas.dao.utils.category.Category.Sentiment;
import com.srct.ril.poas.dao.utils.origin.Origin;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.service.storebbs.StoreBbsService;
import com.srct.ril.poas.utils.ExcelUtils;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
public class NLPAnalysisServiceImpl implements NLPAnalysisService {
	@Autowired
	private BaiduNLPService baiduService;
	
	@Autowired
	private Category cat;
	@Autowired
	private Origin ori;
	@Autowired
	private StoreBbsService storeBbsService;
	
	private double confidence = 0.7;
	private double prob = 0.65;
	private Sentiment sentiment = Sentiment.ALL;
	private boolean debugMode = true;
	private String fileName = "DEMO";
	
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
	
	private Sentiment setimentClassify(String subContent) throws ServiceException {
		Sentiment sentimental = Sentiment.UNKNOWN;
		BaiduNLPSentiment sc = null;
		try {
			sc = baiduService.sentimentClassify(subContent);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("提取情感异常 ", e);
		}
		
		if(sc == null) {
			Log.i("提取情感失败");
		} else if(sc.items == null){
			Log.i("提取情感失败");
		} else {
			Log.ii(sc);
			if(sc.items[0].confidence.doubleValue() > confidence) {
				if(sc.items[0].sentiment.intValue() == sentiment.getValue())
					sentimental = sentiment;
				if(sentiment == Sentiment.ALL){
					sentimental = Sentiment.getSetiment(sc.items[0].sentiment.intValue());
				}
			}
			if(sentimental == Sentiment.UNKNOWN) {
				switch(sentiment) {
					case NEGATIVE: 
						if(sc.items[0].negative_prob.doubleValue()>prob) 
							sentimental = sentiment;; 
						break;
					case NEUTRAL:
						break;
					case POSITIVE: 
						if(sc.items[0].positive_prob.doubleValue()>prob) 
							sentimental = sentiment; 
						break;
					case ALL:
						if(sc.items[0].negative_prob.doubleValue()>prob) 
							sentimental=Sentiment.NEGATIVE;
						else if(sc.items[0].positive_prob.doubleValue()>prob)
							sentimental=Sentiment.POSITIVE;
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
	
	private NLPAnalysis.Item parseCategory(Sentiment sentimental, String subContent, List<String> keyWords) throws ServiceException {
		NLPAnalysis.Item it = new NLPAnalysis.Item();
		it.setSubContent(subContent);
		it.setSentiment(sentimental);
		if(sentimental!=Sentiment.UNKNOWN) {
			Log.i("==明确情感分析({})开始获取观点==", sentiment);
			BaiduNLPCommentTag ct = commentTag(subContent);
			if(ct == null) {
				Log.i("获取观点失败");
				String key=cat.getCategory(keyWords);
				it.setCategory(key);
			} else {
				it.setAdj(ct.items[0].adj);
				it.setProp(ct.items[0].prop);
				it.setSentiment(Sentiment.getSetiment(ct.items[0].sentiment));
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
		if(content == null ||content.equals("")) {
			return null;
		}
		NLPAnalysis res = new NLPAnalysis();
		res.setContent(content);
		//Step 1. 分句
		Log.i("======{}======",content);
		List<String> subContentList = null;
		boolean successful = false;
		boolean needLexer = true;
		BaiduNLPDepParser dp = defParser(content);
		if(dp != null) {
			Log.i("error code : {}",dp.error_code);
			if(dp.error_code == 282131) {
				subContentList = MyAnsj.deperParse(content);
			} else {
				subContentList = dp.simpleText;
			}
		}
		for(String subContent : subContentList) {
			if(subContent.equals(""))continue;
			Log.i(">>>>{}<<<<",subContent);
			//Step 2. 各分句情感分析
			Sentiment sentimental = setimentClassify(subContent);
			//Step 3. 根据情感分析结果分类
			List<String> keyWords = null;
			keyWords = dp.getKeyWords(subContent);
			if(keyWords == null) {
				BaiduNLPLexer lexer = lexer(subContent);
				keyWords = lexer.getKeywords();
				needLexer = false;
			}
			NLPAnalysis.Item it = parseCategory(sentimental, subContent, keyWords);
			if(it.getCategory() != null) {
				needLexer = false;
				successful = true;
				Log.i("<{}>情感关键词获取<{}>", subContent, it.getCategory());
			}
			res.addItem(it);			
		}
		if(needLexer) {
			Log.i("全句逐词提取");
			BaiduNLPLexer lexer = lexer(content);
			Sentiment sentimental = setimentClassify(content);
			NLPAnalysis.Item it = parseCategory(sentimental, content, lexer.getKeywords());
			if(it.getCategory() != null) {
				successful = true;
				Log.i("<{}>情感关键词获取<{}>", content, it.getCategory());
			}
			res.addItem(it);	
		}
		if(successful == false) {
			Log.w("分类失败");
		}
		res.parse();
		return res;
	}
	
	@Override
	public NLPAnalysis nlp(String content) throws ServiceException {
		List<NLPAnalysis> NLPAnalysisList = new ArrayList<>();
		NLPAnalysis nlpAnalysis = _nlp(content);
		NLPAnalysisList.add(nlpAnalysis);
		ExcelUtils.NLP_WriteToExcel(NLPAnalysisList,debugMode,fileName);
		return nlpAnalysis;
	}
	
	@Override
	public List<NLPAnalysis> nlpList(List<String> contentList) throws ServiceException {
		List<NLPAnalysis> res = new ArrayList<>();
		for(String content : contentList) {
			res.add(_nlp(content));
		}
		ExcelUtils.NLP_WriteToExcel(res,debugMode,fileName);
		return res;
	}
	
	@Override
	public List<NLPItem> analyse(String modelName, String startTime, String endTime) throws ServiceException {
		return storeBbsService.select(modelName, startTime, endTime, true);
	}
	
	@Override
	public NLPItem NLPitemFactory(String modelName, String origin, StoreBbsPojoBase obj) throws ServiceException {
		return NLPitemFactory(modelName, origin, obj, false);
	}
	
	@Override
	public NLPItem NLPitemFactory(String modelName, String origin, StoreBbsPojoBase obj, boolean needAnalysis) throws ServiceException {
		Class<?> clazz = ori.getPojoClassFromSource(origin);
		NLPItem nlpIt = new NLPItem(modelName, origin, obj, clazz);
		if(needAnalysis) {
			boolean bUpdated = updateNLPItemAnalysis(nlpIt);
			if(bUpdated) {
				syncNLPItem2DB(nlpIt);
			}
		}
		return nlpIt;
	}
	
	private boolean updateNLPItemAnalysis(NLPItem it) throws ServiceException {
		String title = it.getTitle();
		String comment = it.getFirstcomment();
		if(it.needAnalysis()) {
			NLPAnalysis titleAnalysis = null;
			NLPAnalysis commentAnalysis = null;
			if(title!=null) titleAnalysis = _nlp(title);
			if(comment!=null) commentAnalysis = _nlp(comment);
			it.setAnalysis(titleAnalysis, commentAnalysis);
			return true;
		} else {
			return false;
		}
	}
	
	private void syncNLPItem2DB(NLPItem it) throws ServiceException {
		try {
			storeBbsService.updateAnalysis(it.getModelName(), it.getOrigin(), it.getId(), 
					it.getSentiment().getValue(), cat.getId(it.getCategory()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public HSSFWorkbook saveExcel(String modelName, String origin, List<StoreBbsPojoBase> dataList) throws ServiceException {
		if(dataList==null) return null;
		List<NLPItem> nlpItemList = new ArrayList<>();
		for(StoreBbsPojoBase obj : (List<StoreBbsPojoBase>)dataList) {
			NLPItem nlpIt = NLPitemFactory(modelName, origin, obj);
			nlpItemList.add(nlpIt);
			Log.i("[{}] from {} {}/{}", modelName, origin, nlpItemList.size(),dataList.size());
		}
		return saveExcel(modelName,nlpItemList);
	}
	
	@Override
	public HSSFWorkbook saveExcel(String modelName, List<NLPItem> nlpItemList) throws ServiceException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HHmm");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		for(NLPItem nlpIt : nlpItemList) {
			List<NLPAnalysis>NLPAnalysisList = new ArrayList<>();
			NLPAnalysis titleAnalysis = nlpIt.getTitleAnalysis();
			if(titleAnalysis!=null) {
				NLPAnalysisList.add(titleAnalysis);
			}
			NLPAnalysis commentAnalysis = nlpIt.getCommentAnalysis();
			if(commentAnalysis!=null) {
				NLPAnalysisList.add(commentAnalysis);
			}
			if(NLPAnalysisList.size()!=0) {
				ExcelUtils.NLP_WriteToExcel(NLPAnalysisList, debugMode, fileName+date);
			}
		}
		return ExcelUtils.NLPItem_WriteToExcel(nlpItemList);
	}
	
	
}