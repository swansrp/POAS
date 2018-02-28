package com.srct.ril.poas.ai;

import java.util.ArrayList;
import java.util.List;

public class MyAnsj {

	public static List<String> deperParse(String content) {
		String regEx="：|。|！|；"; 
		String[] strArray = content.split(regEx);
		List<String> strList = new ArrayList<>();
		for(String str : strArray) {
			strList.add(str);
		}
		return strList;
	}

}
