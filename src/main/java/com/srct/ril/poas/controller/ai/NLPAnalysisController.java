package com.srct.ril.poas.controller.ai;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.srct.ril.poas.ai.nlp.NLPItem;
import com.srct.ril.poas.dao.utils.category.Category.Sentiment;
import com.srct.ril.poas.http.CommonResponse;
import com.srct.ril.poas.http.Response;
import com.srct.ril.poas.service.ai.nlp.NLPAnalysisServiceImpl;
import com.srct.ril.poas.service.storebbs.StoreBbsService;
import com.srct.ril.poas.utils.ServiceException;

@RestController

public class NLPAnalysisController {
	@Autowired
	private NLPAnalysisServiceImpl nlpService;
	@Autowired
	private StoreBbsService storeBbsService;
	
	@RequestMapping("/nlp")
	public CommonResponse nlp(
			@RequestParam(value="text") String content) throws ServiceException {
		return Response.generateResponse(nlpService.nlp(content).getSentiment());
	}
	
	@RequestMapping("/nlp/model")
	public CommonResponse nlpList() throws ServiceException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String startTime = df.format(new Date(new Date().getTime()-24*60*60*1000));
		String endTime = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		List<NLPItem> nlpItemList = storeBbsService.select("G9500", startTime, endTime);
		List<String> contentList = new ArrayList<>();
		for(NLPItem it : nlpItemList) {
			contentList.add(it.getFirstcomment());
		}
		return Response.generateResponse(nlpService.nlpList(contentList));
	}
	
	@RequestMapping("/nlp/upload")
	public void nlpUpload(MultipartFile file) throws ServiceException {
		if(file.isEmpty()){  
            return;  
        }
		nlpService.nlpUpload(file);
	}
}