package com.srct.ril.poas.ai.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.srct.ril.poas.dao.pojo.Keyword;
import com.srct.ril.poas.utils.log.Log;

@Component
public class Category {
	
	public static enum Sentiment{
		UNKNOWN(-1),
		POSITIVE(2),
		NEGATIVE(0),
		NEUTRAL(1),
		ALL(3);
		
		private int value;
		private Sentiment(int v) {
			this.value = v;
		}
		public int getValue() {
			return this.value;
		}
		public static Sentiment getSetiment(int value) {
			switch (value) {
			case -1: return UNKNOWN;
			case 0:  return NEGATIVE;		
			case 1:  return NEUTRAL;	
			case 2:	 return POSITIVE;
			case 3:  return ALL;
			default: return UNKNOWN;
			}
		}
	}
	
	private List<String> categoryList = new ArrayList<String>();
	private Map<String, Integer> cat2IdMap = new HashMap<>();
	private Map<String, String> cat2aliasMap = new HashMap<>();
	private Map<String, List<String>> alias2catMap = new HashMap<>();
	private Map<String, List<String>> keywordMap = new HashMap<>();
	private List<String> unknownKeywordList = new ArrayList<String>();

	public void addUnknownKeywordList (String keyword) {
		if(!unknownKeywordList.contains(keyword))
			unknownKeywordList.add(keyword);
	}
	public List<String> getUnknownKeywordList (String keyword) {
		return unknownKeywordList;
	}
	public void addCategory(Keyword key) {
		if(categoryList.contains(key.getCategory())) {
			return;
		}
		categoryList.add(key.getCategory());
		cat2aliasMap.put(key.getCategory(), key.getAlias());
		cat2IdMap.put(key.getCategory(), key.getId());
		keywordMap.put(key.getCategory(), new ArrayList<String>());
		String[] strArray = key.getKeywords().split(",");
		for(String str : strArray) {
			keywordMap.get(key.getCategory()).add(str);
		}
		if(!alias2catMap.containsKey(key.getAlias())) {
			alias2catMap.put(key.getAlias(), new ArrayList<String>());
		}
		alias2catMap.get(key.getAlias()).add(key.getCategory());
	}
	public String getAlias(String category) {
		String alias = null;
		if(cat2aliasMap.containsKey(category)) {
			alias=cat2aliasMap.get(category);
		}
		return alias;
	}
	public int getId(String category) {
		int id = -1;
		if(cat2IdMap.containsKey(category)) {
			id = cat2IdMap.get(category);
		}
		return id;
	}
	public List<String> getCategoryList(String alias) {
		List<String> categoryList = null;
		if(alias2catMap.containsKey(alias)) {
			categoryList=alias2catMap.get(alias);
		}
		return categoryList;
	}
	public String getCategory(String content) {
		String res = null;
		if(categoryList==null) {
			Log.w("categoryList is null");
			return res;
		}
		for(String cat : categoryList) {
			for(String key : keywordMap.get(cat)) {
				if(content.contains(key)) {
					Log.d("{}-->{}/{}", content,cat,key);
					return cat;
				}
			}
		}
		return res;
	}
	public String getCategory(List<String> words) {
		String res = null;
		if(categoryList==null) {
			Log.d("categoryList is null");
			return res;
		}
		for(String cat : categoryList) {
			for(String key : keywordMap.get(cat)) {
				for(String word : words) {
					if(key.equals(word)) {
						Log.i("key:{}, word:{}", key, word);
						return cat;
					}
				}
			}
		}
		return res;
	}
	public List<String> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	public Map<String, Integer> getCat2IdMap() {
		return cat2IdMap;
	}
	public void setCat2IdMap(Map<String, Integer> cat2IdMap) {
		cat2IdMap = cat2IdMap;
	}
	public Map<String, String> getCat2aliasMap() {
		return cat2aliasMap;
	}
	public void setCat2aliasMap(Map<String, String> cat2aliasMap) {
		cat2aliasMap = cat2aliasMap;
	}
	public Map<String, List<String>> getAlias2catMap() {
		return alias2catMap;
	}
	public void setAlias2catMap(Map<String, List<String>> alias2catMap) {
		alias2catMap = alias2catMap;
	}
	public Map<String, List<String>> getKeywordMap() {
		return keywordMap;
	}
	public void setKeywordMap(Map<String, List<String>> keywordMap) {
		keywordMap = keywordMap;
	}
	public List<String> getUnknownKeywordList() {
		return unknownKeywordList;
	}
	public void setUnknownKeywordList(List<String> unknownKeywordList) {
		unknownKeywordList = unknownKeywordList;
	}
}
