package com.srct.ril.poas.service.ai.baidu;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.nlp.ESimnetType;
import com.srct.ril.poas.ai.baidunlp.BaiduClient;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPCommentTag;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPDepParser;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPLexer;
import com.srct.ril.poas.ai.baidunlp.BaiduNLPSentiment;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@Service
public class BaiduNLPService {

	@Autowired
	private BaiduClient baiduClent;
	
	public BaiduNLPLexer lexer(String content) throws ServiceException {
		// 传入可选参数调用接口
	    HashMap<String, Object> options = new HashMap<String, Object>();
		AipNlp client = baiduClent.getClient();
		Log.d(getClass(), "<BaiduNLP>:词法分析");
		JSONObject resJson = client.lexer(content, options);
		//Log.d(resJson.toString(2));
		BaiduNLPLexer res;
		try {
			res = (BaiduNLPLexer)JSONUtil.readJson(resJson.toString(), BaiduNLPLexer.class);
			res.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("BaiduNLPLexer" + content + " cant parse", e);
		}
		return res;
	}
	
	public BaiduNLPDepParser depParser(String content, int mode) throws ServiceException {
		AipNlp client = baiduClent.getClient();
		HashMap<String, Object> options = new HashMap<String, Object>();
	    options.put("mode", mode);
	    Log.d(getClass(), "<BaiduNLP>:句法分析");
		JSONObject resJson = client.depParser(content, options);
		//Log.d(getClass(), resJson.toString(2));
		BaiduNLPDepParser res;
		try {
			res = (BaiduNLPDepParser)JSONUtil.readJson(resJson.toString(), BaiduNLPDepParser.class);
			res.parse();
			Log.d(getClass(), JSONUtil.toJSONString(res));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("BaiduNLPDepParser " + content + " cant parse", e);
		}
		return res;
		
	}
	
	public BaiduNLPDepParser depParser(String content) throws ServiceException {
		return depParser(content, 1);
	}
	
	public BaiduNLPCommentTag commentTag(String content, int mode) throws ServiceException {
		AipNlp client = baiduClent.getClient();
		HashMap<String, Object> options = new HashMap<String, Object>();
		ESimnetType type = BaiduNLPCommentTag.getESimnetType(mode);
		Log.d(getClass(), "<BaiduNLP>:评论提取");
		JSONObject resJson = client.commentTag(content, type, options);
		//Log.i(getClass(), resJson.toString(2));
		BaiduNLPCommentTag res;
		try {
			res = (BaiduNLPCommentTag)JSONUtil.readJson(resJson.toString(), BaiduNLPCommentTag.class);
			//Log.i(getClass(), JSONUtil.toJSONString(res));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("BaiduNLPCommentTag " + content + " cant parse", e);
		}
		return res;	
	}
	
	public BaiduNLPCommentTag commentTag(String content) throws ServiceException {
		return commentTag(content, 13);
	}
	
	public BaiduNLPSentiment sentimentClassify(String content) throws ServiceException {
		AipNlp client = baiduClent.getClient();
		// 传入可选参数调用接口
	    HashMap<String, Object> options = new HashMap<String, Object>();
	    Log.d(getClass(), "<BaiduNLP>:情感分析");
	    // 情感倾向分析
	    JSONObject resJson = client.sentimentClassify(content, options);
	    //System.out.println(resJson.toString(2));
	    BaiduNLPSentiment res;
		try {
			res = (BaiduNLPSentiment)JSONUtil.readJson(resJson.toString(), BaiduNLPSentiment.class);
			//Log.i(getClass(), JSONUtil.toJSONString(res));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("BaiduNLPSentiment " + content + " cant parse", e);
		}
		return res;	
	    
	}
}
