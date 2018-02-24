package com.srct.ril.poas.controller.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.http.CommonResponse;
import com.srct.ril.poas.http.Response;
import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.ServiceException;

@RestController
public class NLPAnalysisController {
	@Autowired
	private NLPAnalysisService service;
	
	@RequestMapping("/nlp")
	public CommonResponse nlp(
			@RequestParam(value="text", required = true) String content) throws ServiceException {
		return Response.generateResponse(service.nlp(content).getCategory());
	}
}