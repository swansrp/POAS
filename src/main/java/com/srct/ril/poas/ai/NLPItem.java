package com.srct.ril.poas.ai;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.dao.utils.category.Category;
import com.srct.ril.poas.dao.utils.category.Category.Sentiment;
import com.srct.ril.poas.utils.log.Log;

public class NLPItem {
	
	private Object daoPojoObject;
	
	private Integer id;
	private String modelName;
	private String origin;
	private String timestamp;
	private String title;
	private String firstcomment;
	private NLPAnalysis titleAnalysis;
	private NLPAnalysis commentAnalysis;
	private String link;
	

	private Sentiment sentiment;
	private String category;
	public NLPItem (Integer id, String timestamp, String origin, String title, String link ) {
		this.id = id;
		this.timestamp = timestamp;
		this.origin = origin;
		this.title = title;
		this.link = link;
	}

	public NLPItem(String modelName, String origin, StoreBbsPojoBase obj, Class<?> clazz) {
		//Log.i("NLPItem:  origin {} modelName {}", origin,modelName);
		this.daoPojoObject = obj;
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
			link = (String)method.invoke(obj);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
		}
		try {
			method = clazz.getMethod("getFirstcomment");
			firstcomment = (String)method.invoke(obj);
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
		try {
			method = clazz.getMethod("getCategory");
			int categoryId = (Integer)method.invoke(obj);
			category = Category.getCategorybyId(categoryId);
			//Log.i("category {}", category);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException |
				NullPointerException e) {
			category = Category.getCategorybyId(1);
		}
		try {
			method = clazz.getMethod("getSentiment");
			int sentimentValue = (Integer)method.invoke(obj);
			//Log.i("sentimentValue{}",sentimentValue);
			sentiment = Category.Sentiment.getSetiment(sentimentValue);
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
		}
	}
	
	public void setAnalysis(NLPAnalysis titleAnalysis, NLPAnalysis commentAnalysis) {
		this.titleAnalysis = titleAnalysis;
		this.commentAnalysis = commentAnalysis;
		if(this.sentiment == Sentiment.UNKNOWN) {
			if(titleAnalysis!=null) {
				this.sentiment = titleAnalysis.getSentiment();
			}
			if(this.sentiment == Sentiment.NEGATIVE) {
				this.category = titleAnalysis.getCategory(Sentiment.NEGATIVE);
			} else {
				if(commentAnalysis!=null) {
					this.sentiment = commentAnalysis.getSentiment();
				}
				if(this.sentiment == Sentiment.NEGATIVE) {
					this.category = commentAnalysis.getCategory(Sentiment.NEGATIVE);
				}
			}
		}
		if(this.sentiment == Sentiment.UNKNOWN) {
			boolean bPostive = true;
			if(titleAnalysis!=null) {
				if(titleAnalysis.getSentiment() != Sentiment.POSITIVE) {
					bPostive = false;
				}
			}
			if(commentAnalysis!=null) {
				if(commentAnalysis.getSentiment() != Sentiment.POSITIVE) {
					bPostive = false;
				}
			}
			if(bPostive) {
				this.sentiment = Sentiment.POSITIVE;
			}
		}
	}
	
	public boolean needAnalysis() {
		Log.d("sentiment: {} category: {}", sentiment, category);
		return !(
				(sentiment!=Sentiment.UNKNOWN)
				|| 
				(category!=null)
			   );
	}

	public Object getDaoPojoObject() {
		return daoPojoObject;
	}

	public void setDaoPojoObject(Object daoPojoObject) {
		this.daoPojoObject = daoPojoObject;
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

	public String getFirstcomment() {
		return firstcomment;
	}

	public void setFirstcomment(String comment) {
		this.firstcomment = comment;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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
