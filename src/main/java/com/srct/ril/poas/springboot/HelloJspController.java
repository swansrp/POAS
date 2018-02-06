package com.srct.ril.poas.springboot;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srct.ril.poas.dbconfig.DS;
import com.srct.ril.poas.dbconfig.DataSourceEnum;

@Controller
public class HelloJspController {

    @RequestMapping("/hellojsp")
    @DS(DataSourceEnum.CONFIG)
    public String hello(Model m) {
    	m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "hello";
    }
}
