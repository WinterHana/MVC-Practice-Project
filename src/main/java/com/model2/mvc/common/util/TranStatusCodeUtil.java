package com.model2.mvc.common.util;

public class TranStatusCodeUtil {
	
	private TranStatusCodeUtil() {
		// blank
	}
	
	// 제품 선택, 관리자의 배송 관리에 사용
	public static String getMessage(String tranCode, boolean isAdmin) {
		System.out.println("[TranStatusCodeUtil.getMessage(String, boolean)] start");
		
		for(TranStatusCode tsc : TranStatusCode.values()) {
			if(tsc.getNumber().trim().equals(tranCode.trim())) {
				System.out.println("[TranStatusCodeUtil.getMessage] Success end");
				if(isAdmin) {
					return  tsc.getAdminMessage();
				} else {
					return tsc.getUserMessage();
				}
			}
		}
		
		System.out.println("[TranStatusCodeUtil.getMessage(String, boolean)] Exception end");
		
		// failed
		return null;
	}
	
	// 구매 관리에 사용
//	public static String getMessage(String tranCode) {
//		System.out.println("[TranStatusCodeUtil.getMessage(String)] start");
//		
//		for(TranStatusCode tsc : TranStatusCode.values()) {
//			if(tsc.getNumber().trim().equals(tranCode.trim())) {
//				System.out.println("[TranStatusCodeUtil.getMessage] Success end");
//					return tsc.getSaleMessage();
//			}
//		}
//		
//		System.out.println("[TranStatusCodeUtil.getMessage(String)] Exception end");
//		return null;
//	}
	
	// 다음 배송 상태를 받을 때 사용 - UserPurchase 전용
	public static String getNextCode(String tranCode) {
		if (tranCode.trim().equals("001")) {
			return "002";
		} else if (tranCode.trim().equals("002")) {
			return "003";
		}
		return null;
	}
}
