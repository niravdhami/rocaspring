package com.eny.roca.blenum;

public enum AllowedPaths {
	VALIDATE_EMAIL("verifiedEmailId"),
	REGISTRATION("register"),
	REGISTERROLES("registerroles"),
	COUNTRY("country"),
	CITY("city"),
	INDUSTRY("industry"),
	SEND_OTP("sendOtp"),
	VERIFY_OTP("verifyOtp"),
	CA_LAYER_SEND_OTP("calayerSendOtp");
	 
	private final String value;

	public String getValue() {
		return value;
	}
	
	AllowedPaths(final String value){
		this.value=value;
	}
	
}
