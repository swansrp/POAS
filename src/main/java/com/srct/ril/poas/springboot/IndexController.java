package com.srct.ril.poas.springboot;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping("")
	public String index() {
        return "main";
    }
	
	@RequestMapping("/add")
	public String addpage() {
        return "addpage";
        
    }
	@RequestMapping("/main")
	public String mainpage() {
        return "main";
        
    }
	@RequestMapping("/test")
	public String testpage(HttpSession session) {
        return "testpage";
        
    }
	@RequestMapping("/category")
	public String catepage() {
        return "catepage";
        
    }
	@RequestMapping("/login")
	public String loginpage() {
        return "loginpage";
        
    }

}
