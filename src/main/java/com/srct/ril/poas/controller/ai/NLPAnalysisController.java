package com.srct.ril.poas.controller.ai;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.ai.NLPItem;
import com.srct.ril.poas.dao.utils.category.Category.Sentiment;
import com.srct.ril.poas.http.CommonResponse;
import com.srct.ril.poas.http.Response;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.service.storebbs.StoreBbsService;
import com.srct.ril.poas.utils.ServiceException;

@RestController

public class NLPAnalysisController {
	@Autowired
	private NLPAnalysisService nlpService;
	@Autowired
	private StoreBbsService storeBbsService;
	
	@RequestMapping("/nlp")
	public CommonResponse nlp(
			@RequestParam(value="text") String content,
			@RequestParam(value="Sentiment") Integer sentiment) throws ServiceException {
		return Response.generateResponse(nlpService.nlp(content).getCategory(Sentiment.getSetiment(sentiment)));
	}
	
	@RequestMapping("/nlp/JD")
	public CommonResponse nlpList() throws ServiceException {
		
		List<NLPItem> nlpItemList = storeBbsService.select("G9500", "2018-02-23 00:00:00", "2018-02-24 00:00:00");
		List<String> contentList = new ArrayList<>();
		for(NLPItem it : nlpItemList) {
			contentList.add(it.getFirstcomment());
		}
		return Response.generateResponse(nlpService.nlpList(contentList));
	}
}