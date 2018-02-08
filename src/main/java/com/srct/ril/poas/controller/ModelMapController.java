package com.srct.ril.poas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.dbconfig.DS;
import com.srct.ril.poas.dbconfig.DataSourceEnum;
import com.srct.ril.poas.http.CommonResponse;
import com.srct.ril.poas.http.Response;
import com.srct.ril.poas.service.ModelMapService;
import com.srct.ril.poas.utils.ServiceException;

@RestController
@RequestMapping("/model")
public class ModelMapController {

	@Autowired
    private ModelMapService modelMapService;
	
	@RequestMapping("/{id}")
	@DS(DataSourceEnum.CONFIG)
	public CommonResponse getProduct(@PathVariable("id") int modelId) throws ServiceException {
        return Response.generateResponse(modelMapService.select(modelId));
    }
	
	@RequestMapping("/name")
	@DS(DataSourceEnum.CONFIG)
	public List<String> getProduct() throws ServiceException {
        return modelMapService.getNameList();
    }
}
