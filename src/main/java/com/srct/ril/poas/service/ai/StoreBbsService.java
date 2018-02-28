package com.srct.ril.poas.service.ai;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.ril.poas.ai.NLPItem;
import com.srct.ril.poas.service.bbs.BbsBDService;
import com.srct.ril.poas.service.bbs.BbsGCService;
import com.srct.ril.poas.service.bbs.BbsJFService;
import com.srct.ril.poas.service.store.StoreAMZService;
import com.srct.ril.poas.service.store.StoreJDService;
import com.srct.ril.poas.service.store.StoreTBService;
import com.srct.ril.poas.service.store.StoreTMService;
import com.srct.ril.poas.utils.ServiceException;

@Service
public class StoreBbsService {
	@Autowired
	private StoreJDService storeJDService;
	@Autowired
	private StoreTBService storeTBService;
	@Autowired
	private StoreTMService storeTMService;
	@Autowired
	private StoreAMZService storeAMZService;
	@Autowired
	private BbsBDService bbsBDService;
	@Autowired
	private BbsGCService bbsGCService;
	@Autowired
	private BbsJFService bbsJFService;
	@Autowired
	private NLPAnalysisService nlpAnalysisService;
	
	public List<NLPItem> select(String modelName, String startTime, String endTime) throws ServiceException {
    	
		List<NLPItem> NLPItemList = new ArrayList<>();
		NLPItemList.add(nlpAnalysisService.NLPitemFactory(modelName, "JD", storeJDService.select(modelName,startTime,endTime)));
		NLPItemList.add(nlpAnalysisService.NLPitemFactory(modelName, "AMZ", storeAMZService.select(modelName,startTime,endTime)));
		NLPItemList.add(nlpAnalysisService.NLPitemFactory(modelName, "TB", storeTBService.select(modelName,startTime,endTime)));
		NLPItemList.add(nlpAnalysisService.NLPitemFactory(modelName, "TM", storeTMService.select(modelName,startTime,endTime)));
		NLPItemList.add(nlpAnalysisService.NLPitemFactory(modelName, "BD", bbsBDService.select(modelName,startTime,endTime)));
		NLPItemList.add(nlpAnalysisService.NLPitemFactory(modelName, "GC", bbsGCService.select(modelName,startTime,endTime)));
		NLPItemList.add(nlpAnalysisService.NLPitemFactory(modelName, "JF", bbsJFService.select(modelName,startTime,endTime)));
		nlpAnalysisService.saveExcel(modelName, NLPItemList);
        return NLPItemList;
    }
}
