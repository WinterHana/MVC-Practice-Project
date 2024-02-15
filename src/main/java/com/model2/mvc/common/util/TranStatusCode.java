package com.model2.mvc.common.util;

/**
 * 배송 상태에 대한 정의
 */
public enum TranStatusCode {
	
	ON_SALE("1", "판매 중"),
	PURCHASED("2", "판매 완료"),
	ON_DELIVERY("3", "배송 중"),
	COMPLETION("4", "배송 완료");
	
	private String number;
	private String message;
	
	TranStatusCode(String number, String message) {
		this.number = number;
		this.message = message;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getMessage() {
		return message;
	}
}
