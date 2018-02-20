package com.srct.ril.poas.ai.baidunlp;

import com.baidu.aip.nlp.ESimnetType;

public class BaiduNLPCommentTag {
	public long log_id; // 匹配上的属性词请求唯一标识码
	public String error_msg;
	public int error_code;
	public Item[] items;
	public static class Item {
		public String prop; // 匹配上的属性词
		public String adj; // 匹配上的描述词
		public int sentiment; // 该情感搭配的极性（0表示消极，1表示中性，2表示积极）
		public int begin_pos; // 该情感搭配在句子中的开始位置
		public int end_pos; // 该情感搭配在句子中的结束位置
		//public String Abstract; // 对应于该情感搭配的短句摘要
	}
	public static ESimnetType getESimnetType(int mode) {
		switch(mode) {
		case 1: return ESimnetType.HOTEL;
		case 2: return ESimnetType.KTV;
		case 3: return ESimnetType.BEAUTY;
		case 4: return ESimnetType.FOOD;
		case 5: return ESimnetType.TRAVEL;
		case 6: return ESimnetType.HEALTH;
		case 7: return ESimnetType.EDU;
		case 8: return ESimnetType.BUSINESS;
		case 9: return ESimnetType.HOUSE;
		case 10: return ESimnetType.CAR;
		case 11: return ESimnetType.LIFE;
		case 12: return ESimnetType.SHOPPING;
		case 13: return ESimnetType._3C;
		default: return ESimnetType.UNSUPPORT_TYPE;
		
		}
	}
}
