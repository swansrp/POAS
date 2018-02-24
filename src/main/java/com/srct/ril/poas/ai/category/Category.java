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
	
	private List<String> categoryList = new ArrayList<String>();
	private Map<String, Integer> cat2IdMap = new HashMap<>();
	private Map<String, String> cat2aliasMap = new HashMap<>();
	private Map<String, List<String>> alias2catMap = new HashMap<>();
	private Map<String, List<String>> keywordMap = new HashMap<>();

	
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
			Log.w(getClass(),"categoryList is null");
			return res;
		}
		for(String cat : categoryList) {
			for(String key : keywordMap.get(cat)) {
				if(content.contains(key)) {
					Log.d(getClass(),"{}-->{}/{}", content,cat,key);
					return cat;
				}
			}
		}
		return res;
	}
	public String getCategory(List<String> words) {
		String res = null;
		if(categoryList==null) {
			Log.d(getClass(),"categoryList is null");
			return res;
		}
		for(String cat : categoryList) {
			for(String key : keywordMap.get(cat)) {
				for(String word : words) {
					if(key.equals(word)) {
						return cat;
					}
				}
			}
		}
		return res;
	}
	/**
	 * @return the categoryList
	 */
	public List<String> getCategoryList() {
		return categoryList;
	}
	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	/**
	 * @return the cat2IdMap
	 */
	public Map<String, Integer> getCat2IdMap() {
		return cat2IdMap;
	}
	/**
	 * @param cat2IdMap the cat2IdMap to set
	 */
	public void setCat2IdMap(Map<String, Integer> cat2IdMap) {
		this.cat2IdMap = cat2IdMap;
	}
	/**
	 * @return the cat2aliasMap
	 */
	public Map<String, String> getCat2aliasMap() {
		return cat2aliasMap;
	}
	/**
	 * @param cat2aliasMap the cat2aliasMap to set
	 */
	public void setCat2aliasMap(Map<String, String> cat2aliasMap) {
		this.cat2aliasMap = cat2aliasMap;
	}
	/**
	 * @return the alias2catMap
	 */
	public Map<String, List<String>> getAlias2catMap() {
		return alias2catMap;
	}
	/**
	 * @param alias2catMap the alias2catMap to set
	 */
	public void setAlias2catMap(Map<String, List<String>> alias2catMap) {
		this.alias2catMap = alias2catMap;
	}
	/**
	 * @return the keywordMap
	 */
	public Map<String, List<String>> getKeywordMap() {
		return keywordMap;
	}
	/**
	 * @param keywordMap the keywordMap to set
	 */
	public void setKeywordMap(Map<String, List<String>> keywordMap) {
		this.keywordMap = keywordMap;
	}
}
