package com.model2.mvc.common.util;

/**
 * 배송 상태에 대한 정의
 */
public enum TranStatusCode {
	
	ON_SALE("1", "판매 중", "판매 중"),
	PURCHASED("2", "매진", "판매 완료"),
	ON_DELIVERY("3", "매진", "배송 중"),
	COMPLETION("4", "매진", "배송 완료");
	
	private String number;
	private String purchaseMessage;
	private String saleMessage;
	
	TranStatusCode(String number, String purchaseMessage, String saleMessage) {
		this.number = number;
		this.purchaseMessage = purchaseMessage;
		this.saleMessage = saleMessage;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getPurchaseMessage() {
		return purchaseMessage;
	}
	
	public String getSaleMessage() {
		return saleMessage;
	}
}
