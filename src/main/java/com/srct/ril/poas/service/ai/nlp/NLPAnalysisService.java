package com.srct.ril.poas.service.ai.nlp;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.srct.ril.poas.ai.nlp.NLPAnalysis;
import com.srct.ril.poas.ai.nlp.NLPItem;
import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.utils.ServiceException;

public interface NLPAnalysisService {

	public NLPAnalysis nlp(String content) 
			throws ServiceException;

	public List<NLPAnalysis> nlpList(List<String> contentList) 
			throws ServiceException;

	public void analyse() 
			throws ServiceException;

	public NLPItem NLPitemFactory(String modelName, String origin, StoreBbsPojoBase obj) 
			throws ServiceException;

	public NLPItem NLPitemFactory(String modelName, String origin, StoreBbsPojoBase obj, boolean needAnalysis)
			throws ServiceException;

	public HSSFWorkbook saveExcel(String modelName, String origin, List<StoreBbsPojoBase> dataList) 
			throws ServiceException;

	public HSSFWorkbook saveExcel(String modelName, List<NLPItem> nlpItemList) 
			throws ServiceException;

}
