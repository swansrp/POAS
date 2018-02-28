package com.srct.ril.poas.ai;

import java.util.ArrayList;
import java.util.List;

import com.srct.ril.poas.ai.category.Category.Sentiment;

public class NLPAnalysis {
	private String content;
	private List<Item> items = new ArrayList<>();
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Item> getItems() {
		if(this.sentiment != Sentiment.NEGATIVE 
		&& this.sentiment != Sentiment.POSITIVE) 
			return items;
		List<Item> res = new ArrayList<>();
		for(Item it :items) {
			if(it.getSentiment() == sentiment) {
				res.add(it);
			}
		}
		return res;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public NLPAnalysis(Sentiment sentiment) {
		this.sentiment = sentiment;
	}
	public int addItem(Item it) {
		it.setId(items.size());
		items.add(it);
		return it.id;
	}
	public Item getItem(int id) {
		return items.get(id);
	}
	
	public String getCategory() {
		for(Item it : items) {
			if(it.getCategory()!=null) {
				return it.getCategory();
			}
		}
		return null;
	}
}
