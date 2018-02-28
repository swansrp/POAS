package com.srct.ril.poas.ai.origin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.srct.ril.poas.dao.pojo.BbsBD;
import com.srct.ril.poas.dao.pojo.BbsGC;
import com.srct.ril.poas.dao.pojo.BbsJF;
import com.srct.ril.poas.dao.pojo.SourceMap;
import com.srct.ril.poas.dao.pojo.StoreAMZ;
import com.srct.ril.poas.dao.pojo.StoreJD;
import com.srct.ril.poas.dao.pojo.StoreTB;
import com.srct.ril.poas.dao.pojo.StoreTM;
import com.srct.ril.poas.service.bbs.BbsBDService;
import com.srct.ril.poas.service.bbs.BbsGCService;
import com.srct.ril.poas.service.bbs.BbsJFService;
import com.srct.ril.poas.service.store.StoreAMZService;
import com.srct.ril.poas.service.store.StoreJDService;
import com.srct.ril.poas.service.store.StoreTBService;
import com.srct.ril.poas.service.store.StoreTMService;

@Component
public class Origin {
	
	@Autowired
	private StoreJDService storeJDDao;
	@Autowired
	private StoreTBService storeTBDao;
	@Autowired
	private StoreTMService storeTMDao;
	@Autowired
	private StoreAMZService storeAMZDao;
	@Autowired
	private BbsBDService bbsBDDao;
	@Autowired
	private BbsGCService bbsGCDao;
	@Autowired
	private BbsJFService bbsJFDao;
	
	private Map<String, Integer> originIdMap = new HashMap<>();
	private Map<Integer, String> originMap = new HashMap<>();
	
	public Class<?> getPojoClassFromSource(String source) {
		switch(getId(source)) {
		case 1: return StoreJD.class;
		case 2: return StoreTB.class;
		case 3: return null;
		case 4: return StoreTM.class;
		case 5: return StoreAMZ.class;
		case 6: return BbsBD.class;
		case 7: return BbsGC.class;
		case 8: return BbsJF.class;
		case 9: return null;
		default: return null;
		}
	}
	
	public Class<?> getServiceClassFromSource(String source) {
		switch(getId(source)) {
		case 1: return StoreJDService.class;
		case 2: return StoreTBService.class;
		case 3: return null;
		case 4: return StoreTMService.class;
		case 5: return StoreAMZService.class;
		case 6: return BbsBDService.class;
		case 7: return BbsGCService.class;
		case 8: return BbsJFService.class;
		case 9: return null;
		default: return null;
		}
	}
	
	public Object getServiceFromSource(String source) {
		switch(getId(source)) {
		case 1: return storeJDDao;
		case 2: return storeTBDao;
		case 3: return null;
		case 4: return storeTMDao;
		case 5: return storeAMZDao;
		case 6: return bbsBDDao;
		case 7: return bbsGCDao;
		case 8: return bbsJFDao;
		case 9: return null;
		default: return null;
		}
	}

	public void addOrigin(SourceMap source) {
		originIdMap.put(source.getSourceEn(), source.getId());
		originMap.put(source.getId(),source.getSourceEn());
	}
	public Integer getId(String src) {
		return originIdMap.get(src);
	}
	public String getOrigin(Integer id) {
		return originMap.get(id);
	}
	public Map<String, Integer> getOriginIdMap() {
		return originIdMap;
	}
	public void setOriginIdMap(Map<String, Integer> originIdMap) {
		this.originIdMap = originIdMap;
	}
	public Map<Integer, String> getOriginMap() {
		return originMap;
	}
	public void setOriginMap(Map<Integer, String> originMap) {
		this.originMap = originMap;
	}
}
