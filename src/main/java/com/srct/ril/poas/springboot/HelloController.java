package com.srct.ril.poas.springboot;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srct.ril.poas.dbconfig.DataSourceEnum;
 
@RestController
public class HelloController {
 
    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!" + DataSourceEnum.CONFIG.toString();
    }
    
    @RequestMapping("/hellojsp")
    public String hello(Model m) {
    	m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "Hello";
    }
 
}
