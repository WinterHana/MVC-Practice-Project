package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;

public interface PurchaseService {
	
	// getPurchase
	public PurchaseVO getPurchase(int tranNo);
	
	// getPurchaseList
	public Map<String, Object> getPurchaseList(SearchVO searchVO, UserVO userVO);
	
	// getSaleList
	public Map<Integer, String> getSalaList();
	
	// addPurchase
	public int addPurchase(PurchaseVO purchaseVO);
	
	// updatePurchase
	public int updatePurchase(PurchaseVO purchaseVO);
	
	// updateTranCode
	public int updateTranCode(PurchaseVO purchaseVO);
	
	// deletePurchase
	public int deletePurchase(PurchaseVO purchaseVO);
}
