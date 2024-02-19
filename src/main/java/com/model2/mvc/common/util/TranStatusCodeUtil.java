package com.model2.mvc.common.util;

public class TranStatusCodeUtil {
	
	private TranStatusCodeUtil() {
		// blank
	}
	
	// 제품 선택, 관리자의 배송 관리에 사용
	public static String getMessage(String tranCode, boolean isManager) {
		System.out.println("[TranStatusCodeUtil.getMessage(String, boolean)] start");
		
		for(TranStatusCode tsc : TranStatusCode.values()) {
			// System.out.println("tsc.getNumber() : " + tsc.getNumber());
			// System.out.println("tranCode : " + tranCode);
			if(tsc.getNumber().trim().equals(tranCode.trim())) {
				System.out.println("[TranStatusCodeUtil.getMessage] Success end");
				if(isManager) {
					return tsc.getSaleMessage();
				} else {
					return tsc.getPurchaseMessage();
				}
			}
		}
		
		System.out.println("[TranStatusCodeUtil.getMessage(String, boolean)] Exception end");
		return null;
	}
	
	// 구매 관리에 사용
	public static String getMessage(String tranCode) {
		System.out.println("[TranStatusCodeUtil.getMessage(String)] start");
		
		for(TranStatusCode tsc : TranStatusCode.values()) {
			// System.out.println("tsc.getNumber() : " + tsc.getNumber());
			// System.out.println("number : " + number);
			if(tsc.getNumber().trim().equals(tranCode.trim())) {
				System.out.println("[TranStatusCodeUtil.getMessage] Success end");
					return tsc.getSaleMessage();
			}
		}
		
		System.out.println("[TranStatusCodeUtil.getMessage(String)] Exception end");
		return null;
	}
	
	// 다음 배송 상태를 받을 때 사용
	public static String getNextCode(String tranCode) {
		if(tranCode.trim().equals("2")) {
			return "3";
		} else if (tranCode.trim().equals("3")) {
			return "4";
		} else {
			return null;
		}
	}
}
