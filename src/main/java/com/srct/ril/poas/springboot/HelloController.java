package com.srct.ril.poas.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.dbconfig.DS;
import com.srct.ril.poas.dbconfig.DataSourceEnum;
 
@RestController
public class HelloController {
	
	
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);
 
	@RequestMapping("/hello")
    public String hello(Model m) {	
    	log.info("==============hello==========");
        return "Hello Spring Boot! " + DataSourceEnum.CONFIG.toString();
    }
 
}
