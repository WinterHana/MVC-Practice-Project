package com.model2.mvc.common.util;

/**
 * 배송 상태에 대한 정의
 */
public enum TranStatusCode {
	
	PURCHASED("001", "구입 완료", "판매 완료"),
	ON_DELIVERY("002", "배송 중", "배송 중"),
	COMPLETION("003", "배송 완료", "배송 완료");
	
	private String number;
	private String userMessage;
	private String adminMessage;
	
	TranStatusCode(String number, String userMessage, String adminMessage) {
		this.number = number;
		this.userMessage = userMessage;
		this.adminMessage = adminMessage;
	}
	
	public String getNumber()  {
		return number;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public String getAdminMessage() {
		return adminMessage;
	}
}
