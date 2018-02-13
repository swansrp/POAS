package com.srct.ril.poas.service.ai.baidu;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.baidu.aip.nlp.AipNlp;
import com.srct.ril.poas.ai.BaiduClient;
import com.srct.ril.poas.ai.baidunlp.pojo.BaiduNLPDepParser;
import com.srct.ril.poas.ai.baidunlp.pojo.BaiduNLPLexer;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.Log;
import com.srct.ril.poas.utils.ServiceException;
@Service
public class BaiduNLPService {

	@Autowired
	private BaiduClient baiduClent;
	
	public BaiduNLPLexer lexer(String content) throws ServiceException {
		// 传入可选参数调用接口
	    HashMap<String, Object> options = new HashMap<String, Object>();
		AipNlp client = baiduClent.getClient();
		JSONObject resJson = client.lexer(content, options);
		Log.i(this.getClass(), resJson.toString(2));
		BaiduNLPLexer res;
		try {
			res = (BaiduNLPLexer)JSONUtil.readJson(resJson.toString(), BaiduNLPLexer.class);
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
		JSONObject resJson = client.depParser(content, options);
		
		BaiduNLPDepParser res;
		try {
			res = (BaiduNLPDepParser)JSONUtil.readJson(resJson.toString(), BaiduNLPDepParser.class);
			res.parse();
			Log.i(getClass(), JSONUtil.toJSONString(res));
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
}
