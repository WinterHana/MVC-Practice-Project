package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public interface PurchaseService {
	
	// getPurchase
	public PurchaseVO getPurchase(int tranNo);
	// getPurchaseList
	public Map<String, Object> getPurchaseList(SearchVO searchVO, String userId);
	
	// getSaleList
	
	// addPurchase
	public void addPurchase(PurchaseVO purchaseVO);
	
	// updatePurchase
	
	// updateTranCode
}
