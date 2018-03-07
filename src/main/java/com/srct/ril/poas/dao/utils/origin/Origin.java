package com.srct.ril.poas.dao.utils.origin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.srct.ril.poas.dao.pojo.SourceMap;
import com.srct.ril.poas.utils.BeanUtil;


@Component
public class Origin {
	
	private static Map<String, Integer> originIdMap = new HashMap<>();
	private static Map<String, Integer> originDisplayIdMap = new HashMap<>();
	private static Map<Integer, String> originMap = new HashMap<>();
	private static Map<Integer, String> originDisplayMap = new HashMap<>();
	private static List<String> originList = new ArrayList<>();
	
	final private static String pojoPackageName = "com.srct.ril.poas.dao.pojo.Dao";
	final private static String mapperPackageName = "com.srct.ril.poas.dao.mapper.Dao";
	
	private Class<?> getClassFromName(String className) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clazz;
	}
	
	public Class<?> getPojoClassFromSource(String origin) {
		String className = pojoPackageName + origin;
		return getClassFromName(className);
		
	}
	
	public Class<?> getDaoClassFromSource(String origin) {
		String className = mapperPackageName + origin + "Mapper";
		return getClassFromName(className);
	}
	
	public Class<?> getPojoExampleClassFromSource(String origin) {
		String className = pojoPackageName + origin + "Example";
		return getClassFromName(className);
	}
	
	public Class<?> getpojoCriteriaFromSource(String origin) {
		String className = pojoPackageName + origin + "Example$Criteria";
		return getClassFromName(className);
	}
	
	
	public Object getDaoFromSource(String origin) {
		return BeanUtil.getBean("dao"+origin+"Mapper");
	}
	

	public void addOrigin(SourceMap source) {
		originIdMap.put(source.getSourceEn(), source.getId());
		originDisplayIdMap.put(source.getSourceCn(), source.getId());
		originMap.put(source.getId(),source.getSourceEn());
		originList.add(source.getSourceEn());
		originDisplayMap.put(source.getId(), source.getSourceCn());
	}
	public Integer getId(String src) {
		return originIdMap.get(src);
	}
	public static String getOrigin(String srcCn) {
		return originMap.get(originDisplayIdMap.get(srcCn));
	}
	public String getOrigin(Integer id) {
		return originMap.get(id);
	}
	public static String displayOrigin(String ori) {
		return originDisplayMap.get(originIdMap.get(ori));
	}
	public Map<String, Integer> getOriginIdMap() {
		return originIdMap;
	}
	public void setOriginIdMap(Map<String, Integer> originIdMap) {
		Origin.originIdMap = originIdMap;
	}
	public Map<String, Integer> getOriginDisplayIdMap() {
		return originDisplayIdMap;
	}
	public void setOriginDisplayIdMap(Map<String, Integer> originDisplayIdMap) {
		Origin.originDisplayIdMap = originDisplayIdMap;
	}
	public Map<Integer, String> getOriginMap() {
		return originMap;
	}
	public void setOriginMap(Map<Integer, String> originMap) {
		Origin.originMap = originMap;
	}

	public List<String> getOriginList() {
		return originList;
	}

	public void setOriginList(List<String> originList) {
		Origin.originList = originList;
	}
}
