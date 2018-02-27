package com.srct.ril.poas.controller.ai;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.dao.pojo.StoreJD;
import com.srct.ril.poas.http.CommonResponse;
import com.srct.ril.poas.http.Response;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.service.store.StoreJDService;
import com.srct.ril.poas.utils.ServiceException;

@RestController

public class NLPAnalysisController {
	@Autowired
	private NLPAnalysisService nlpService;
	
	@Autowired
	private StoreJDService jdService;
	
	@RequestMapping("/nlp")
	public CommonResponse nlp(
			@RequestParam(value="text") String content) throws ServiceException {
		return Response.generateResponse(nlpService.nlp(content).getCategory());
	}
	
	@RequestMapping("/nlp/JD")
	public CommonResponse nlpList() throws ServiceException {
		
		List<StoreJD> jdList = jdService.select("G9500", "2018-02-23 00:00:00", "2018-02-24 00:00:00");
		List<String> contentList = new ArrayList<>();
		for(StoreJD jd : jdList) {
			contentList.add(jd.getFirstcomment());
		}
		return Response.generateResponse(nlpService.nlpList(contentList));
	}
}