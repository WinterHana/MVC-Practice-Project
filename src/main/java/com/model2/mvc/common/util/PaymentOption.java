package com.model2.mvc.common.util;

/**
 * 결제 방법에 대한 정의
 */
public enum PaymentOption {

	CASH("1", "현금결제"),
	CREDIT_CARD("2", "카드결제");
	
	private String number;
	private String option;
	
	PaymentOption(String number, String option) {
		this.number = number;
		this.option = option; 
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getOption()  {
		return option;
	}
}
