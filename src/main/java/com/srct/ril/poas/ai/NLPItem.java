package com.srct.ril.poas.ai;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;

import com.srct.ril.poas.service.ai.NLPAnalysisService;
import com.srct.ril.poas.utils.log.Log;

public class NLPItem {
	
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	private Integer id;
	private String modelName;
	private String origin;
	private String timestamp;
	private String title;
	private String comment;
	private NLPAnalysis titleAnalysis;
	private NLPAnalysis commentAnalysis;
	private String url;
	
	
	public NLPItem(Class<?> clazz, Object obj, String origin, String modelName) {
		//Log.i("NLPItem:  origin {} modelName {}", origin,modelName);
		this.origin = origin;
		this.modelName = modelName;
		Method method;
		try {
			method = clazz.getMethod("getId");
			id = (Integer)method.invoke(obj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
		}
		try {
			method = clazz.getMethod("getDate");
			timestamp = (String)method.invoke(obj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
		}
		try {
			method = clazz.getMethod("getLink");
			url = (String)method.invoke(obj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
		}
		try {
			method = clazz.getMethod("getFirstcomment");
			comment = (String)method.invoke(obj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
		}
		try {
			method = clazz.getMethod("getTitle"); 
			title = (String)method.invoke(obj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
		}
	}

	public NLPAnalysisService getNlpAnalysisService() {
		return nlpAnalysisService;
	}

	public void setNlpAnalysisService(NLPAnalysisService nlpAnalysisService) {
		this.nlpAnalysisService = nlpAnalysisService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public NLPAnalysis getTitleAnalysis() {
		return titleAnalysis;
	}

	public void setTitleAnalysis(NLPAnalysis titleAnalysis) {
		this.titleAnalysis = titleAnalysis;
	}

	public NLPAnalysis getCommentAnalysis() {
		return commentAnalysis;
	}

	public void setCommentAnalysis(NLPAnalysis commentAnalysis) {
		this.commentAnalysis = commentAnalysis;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
