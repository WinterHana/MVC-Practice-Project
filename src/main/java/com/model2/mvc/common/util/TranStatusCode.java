package com.model2.mvc.common.util;

/**
 * 배송 상태에 대한 정의
 */
public enum TranStatusCode {
	
	ON_SALE("1"),
	PURCHASED("2"),
	ON_DELIVERY("3"),
	COMPLETION("4");
	
	private String number;
	
	TranStatusCode(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}
}
