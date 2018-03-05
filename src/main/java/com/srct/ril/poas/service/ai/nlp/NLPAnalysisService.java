package com.srct.ril.poas.service.ai.nlp;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.srct.ril.poas.ai.nlp.NLPAnalysis;
import com.srct.ril.poas.ai.nlp.NLPItem;
import com.srct.ril.poas.dao.pojo.StoreBbsPojoBase;
import com.srct.ril.poas.utils.ServiceException;

public interface NLPAnalysisService {

	NLPAnalysis nlp(String content) 
			throws ServiceException;

	List<NLPAnalysis> nlpList(List<String> contentList) 
			throws ServiceException;

	List<NLPItem> analyse(String modelName, String startTime, String endTime) 
			throws ServiceException;

	NLPItem NLPitemFactory(String modelName, String origin, StoreBbsPojoBase obj) 
			throws ServiceException;

	NLPItem NLPitemFactory(String modelName, String origin, StoreBbsPojoBase obj, boolean needAnalysis)
			throws ServiceException;

	HSSFWorkbook saveExcel(String modelName, String origin, List<StoreBbsPojoBase> dataList) 
			throws ServiceException;

	HSSFWorkbook saveExcel(String modelName, List<NLPItem> nlpItemList) 
			throws ServiceException;

}
