package com.srct.ril.poas.springboot;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.aip.nlp.AipNlp;
import com.srct.ril.poas.ai.BaiduClient;
import com.srct.ril.poas.dao.dbconfig.DataSourceEnum;
 
@RestController
public class HelloController {
	
	
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);
	
	@RequestMapping("/hello")
    public String hello(Model m) {	
    	log.info("==============hello==========");
        return "Hello Spring Boot! " + DataSourceEnum.CONFIG.toString();
    }
}
