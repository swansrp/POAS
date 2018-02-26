package com.srct.ril.poas.dao.pojo;

public class UrlJoinMap {

    private Integer id;
    private String url;
    private Integer modelId;
    private Integer sourceId;
    private String lastFetchTime;
    private ModelMap modelMap;
    private SourceMap sourceMap;
    public ModelMap getModelMap() {
		return modelMap;
	}

	public void setModelMap(ModelMap modelMap) {
		this.modelMap = modelMap;
	}

    public SourceMap getSourceMap() {
		return sourceMap;
	}

	public void setSourceMap(SourceMap sourceMap) {
		this.sourceMap = sourceMap;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getLastFetchTime() {
        return lastFetchTime;
    }

    public void setLastFetchTime(String lastFetchTime) {
        this.lastFetchTime = lastFetchTime == null ? null : lastFetchTime.trim();
    }
}
