package com.srct.ril.poas.ai.origin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.srct.ril.poas.dao.pojo.BbsBD;
import com.srct.ril.poas.dao.pojo.BbsGC;
import com.srct.ril.poas.dao.pojo.BbsJF;
import com.srct.ril.poas.dao.pojo.SourceMap;
import com.srct.ril.poas.dao.pojo.StoreAMZ;
import com.srct.ril.poas.dao.pojo.StoreJD;
import com.srct.ril.poas.dao.pojo.StoreTB;
import com.srct.ril.poas.dao.pojo.StoreTM;

@Component
public class Origin {
	private Map<String, Integer> originIdMap = new HashMap<>();
	private Map<Integer, String> originMap = new HashMap<>();
	
	public Class<?> getClassFromSource(String source) {
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
