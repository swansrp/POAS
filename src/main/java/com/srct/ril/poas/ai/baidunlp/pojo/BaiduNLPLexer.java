package com.srct.ril.poas.ai.baidunlp.pojo;

public class BaiduNLPLexer extends BaiduNLPBase {
	public long log_id;
	public String text; // 原始单条请求文本
	public Items[] items; // 词汇数组，每个元素对应结果中的一个词
	
	public static class Items {
		public String item; // 词汇的字符串
		public String ne;   // 命名实体类型，命名实体识别算法使用。词性标注算法中，此项为空串
		public String pos;  // 词性，词性标注算法使用。命名实体识别算法中，此项为空串
		public int byte_offset; // 在text中的字节级offset（使用GBK编码）
		public int byte_length; // 字节级length（使用GBK编码）
		public String uri;  // 链指到知识库的URI，只对命名实体有效。对于非命名实体和链接不到知识库的命名实体，此项为空串
		public String formal; // 词汇的标准化表达，主要针对时间、数字单位，没有归一化表达的，此项为空串
		public String[] basic_words; // 基本词成分
		public Details[] loc_details; // 地址成分，非必需，仅对地址型命名实体有效，没有地址成分的，此项为空数组。
		public static class Details {
			public String type; // 成分类型，如省、市、区、县
			public int byte_offset; // 在item中的字节级offset（使用GBK编码） 
			public int byte_lenth; // 字节级length（使用GBK编码）
		}
	}
	
	public String toString() {
		return "Text:" +  text;
	}
}
