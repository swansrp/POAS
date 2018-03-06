package com.srct.ril.poas.controller.storebbs.store;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.http.CommonResponse;
import com.srct.ril.poas.http.Response;
import com.srct.ril.poas.service.storebbs.StoreBbsService;
import com.srct.ril.poas.utils.ServiceException;
import com.srct.ril.poas.utils.log.Log;

@RestController
@RequestMapping("/JD")
public class StoreJDController {
	
	@Autowired
	private StoreBbsService storeBbsService;
	
	//http://127.0.0.1:8004/JD/modelinfo?modelname=N9500&start=2018-02-09%2000:00:09&end=2018-02-09%2009:44:00
	@RequestMapping("/modelinfo")
	public CommonResponse getProduct(
			@RequestParam(value="modelname", required = true) String modelName,
			@RequestParam(value="start", required = true) String startTime,
			@RequestParam(value="end", required = true) String endTime) throws ServiceException {
		String origin = getClass().getAnnotation(RequestMapping.class).value()[0].substring(1);
        return Response.generateResponse(storeBbsService.select(modelName,origin,startTime,endTime));
    }
	
	@RequestMapping("/modelinfo/download")
	public void getProduct(
			@RequestParam(value="modelname", required = true) String modelName,
			@RequestParam(value="start", required = true) String startTime,
			@RequestParam(value="end", required = true) String endTime,
			HttpServletRequest request, 
			HttpServletResponse response) throws ServiceException {
		String origin = getClass().getAnnotation(RequestMapping.class).value()[0].substring(1);
		String fileName = modelName+"_"+"JD"+"_"+startTime+"_"+endTime+".xls";
		response.setContentType("application/octet-stream");  
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);//默认Excel名称  
		try {
			response.flushBuffer();
			Response.generateResponse(storeBbsService.select(modelName,origin,startTime,endTime,response));
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@RequestMapping("/modelinfo/update/analysis")
    public void updateAnalysis(
    		@RequestParam(value="modelname", required = true) String modelName,
			@RequestParam(value="id", required = true) Integer id,
			@RequestParam(value="sentiment", required = true) Integer sentiment,
			@RequestParam(value="category", required = true) String cetegory) throws ServiceException {
    	String origin = getClass().getAnnotation(RequestMapping.class).value()[0].substring(1);
    	try {
			storeBbsService.updateAnalysis(modelName, origin, id, sentiment, cetegory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
}
