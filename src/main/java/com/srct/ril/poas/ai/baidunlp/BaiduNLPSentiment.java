package com.srct.ril.poas.ai.baidunlp;

public class BaiduNLPSentiment {
	public String text;
	public item[] items;
	public static class item {
		public Number sentiment;
		public Number confidence;
		public Number positive_prob;
		public Number negative_prob;
	}
}
