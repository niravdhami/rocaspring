package com.eny.roca.blenum;

public enum AllowedPaths {
	VALIDATE_EMAIL("verifiedEmailId"),
	REGISTRATION("register"),
	REGISTERROLES("registerroles"),
	COUNTRY("country"),
	CITY("city"),
	INDUSTRY("industry");
	 
	private final String value;

	public String getValue() {
		return value;
	}
	
	AllowedPaths(final String value){
		this.value=value;
	}
	
}
