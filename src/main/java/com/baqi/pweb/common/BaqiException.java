package com.baqi.pweb.common;

public class BaqiException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public BaqiException(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
