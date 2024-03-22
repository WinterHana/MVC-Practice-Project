package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;

public interface PurchaseDAO {
	public PurchaseVO getPurchase(int tranNo);
	
	public List<PurchaseVO> getPurchaseList(Map<String,Object> map);
	
	public int getPurchaseCount(Map<String,Object> map);
	
	public List<Map<String, Object>> getSaleList();
	
	public int addPurchase(PurchaseVO purchaseVO);
	
	public int deletePurchase(int tranNo);
	
	public int deletePurchaseProdNo(int prodNo);
	
	public int deletePurchaseBuyerId(String buyerId);
	
	public int updatePurchase(PurchaseVO purchaseVO);
	
	public int updateTranCode(Map<String,Object> map);
}
