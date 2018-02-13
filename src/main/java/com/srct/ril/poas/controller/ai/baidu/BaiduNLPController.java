package com.srct.ril.poas.controller.ai.baidu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.http.CommonResponse;
import com.srct.ril.poas.http.Response;
import com.srct.ril.poas.service.ai.baidu.BaiduNLPService;
import com.srct.ril.poas.utils.JSONUtil;
import com.srct.ril.poas.utils.ServiceException;

@RestController
@RequestMapping("/baidu")
public class BaiduNLPController {
	
	@Autowired
	private BaiduNLPService service;
	
	@RequestMapping("/lexer")
	public CommonResponse lexer(
			@RequestParam(value="text") String content) throws ServiceException {
		return Response.generateResponse(service.lexer(content));
	}
	
	@RequestMapping("/depparser")
	public String depParser(
			@RequestParam(value="text") String content) throws ServiceException {
		return JSONUtil.toJSONString(service.depParser(content));
	}
}
