package com.srct.ril.poas.dbconfig;

public enum DataSourceEnum {
	CONFIG("Configuration"),
	GREAT("GREAT"),
	DREAM("DREAM");
	
	private String name;
	
	DataSourceEnum(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;		
	}
}
