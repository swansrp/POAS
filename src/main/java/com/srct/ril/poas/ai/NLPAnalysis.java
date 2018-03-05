package com.srct.ril.poas.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.srct.ril.poas.dao.utils.category.Category.Sentiment;

public class NLPAnalysis {
	private String content;
	private List<Item> items = new ArrayList<>();
	private Map<Sentiment, List<String>> summary = new HashMap<Sentiment, List<String>>();
	private Sentiment sentiment; 
	public static class Item {
		private int id;
		private String subContent; // 有意义的分句
		private String prop; // 匹配上的属性词
		private String adj; // 匹配上的描述词
		private Sentiment sentiment; // 该情感搭配的极性（0表示消极，1表示中性，2表示积极）
		private String category; // 按要求分类
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getSubContent() {
			return subContent;
		}
		public void setSubContent(String subContent) {
			this.subContent = subContent;
		}
		public String getProp() {
			return prop;
		}
		public void setProp(String prop) {
			this.prop = prop;
		}
		public String getAdj() {
			return adj;
		}
		public void setAdj(String adj) {
			this.adj = adj;
		}
		public Sentiment getSentiment() {
			return sentiment;
		}
		public void setSentiment(Sentiment sentiment) {
			this.sentiment = sentiment;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
	}
	
	public NLPAnalysis() {
		summary.put(Sentiment.NEGATIVE, new ArrayList<String>());
		summary.put(Sentiment.POSITIVE, new ArrayList<String>());
		summary.put(sentiment.NEUTRAL, new ArrayList<String>());
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public int addItem(Item it) {
		it.setId(items.size());
		items.add(it);
		return it.id;
	}
	public Item getItem(int id) {
		return items.get(id);
	}
	
	public void parse() {
		if(items==null) {
			return;
		}
		for(Item it : items) {
			if(it.getSentiment() != Sentiment.UNKNOWN) {	
				summary.get(it.getSentiment()).add(it.getCategory());
			}
		}
		if(summary.get(Sentiment.NEGATIVE).size() != 0) {
			this.sentiment = Sentiment.NEGATIVE;
		} else if(summary.get(Sentiment.POSITIVE).size() != 0) {
			this.sentiment = Sentiment.POSITIVE;
		} else if(summary.get(Sentiment.NEUTRAL).size() != 0) {
			this.sentiment = Sentiment.NEUTRAL;
		} else {
			this.sentiment = Sentiment.ALL;
		}
	}
	
	public String getCategory(Sentiment sentiment) {
		for(String cat : summary.get(sentiment)) {
			if(cat!=null) {
				return cat;
			}
		}
		return null;
	}
	
	public Sentiment getSentiment() {		
		return this.sentiment;
	}
}
