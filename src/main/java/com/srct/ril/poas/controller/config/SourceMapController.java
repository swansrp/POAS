package com.srct.ril.poas.controller.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.dao.dbconfig.DS;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
import com.srct.ril.poas.service.config.SourceMapService;
import com.srct.ril.poas.utils.ServiceException;

@RestController
@RequestMapping("/config/source")
public class SourceMapController {
	
	@Autowired
	SourceMapService sourceMapService;
	
	@RequestMapping("/name")
	@DS(DataSourceEnum.CONFIG)
	public List<String> getProduct() throws ServiceException {
        return sourceMapService.getNameList();
    }
}
