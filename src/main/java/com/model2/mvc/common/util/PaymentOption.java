package com.model2.mvc.common.util;

/**
 * 결제 방법에 대한 정의
 */
public enum PaymentOption {

	CASH("1"),
	CREDIT_CARD("2");
	
	private String number;
	
	PaymentOption(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}
}
