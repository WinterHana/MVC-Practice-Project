package com.model2.mvc.service.purchase.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;

public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO=new PurchaseDAO();
	}
	
	@Override
	public void addPurchase(PurchaseVO purchaseVO) {
		System.out.println("[purchaseServiceImpl.addPurchase] start");
		
		try {
			purchaseDAO.addPurchase(purchaseVO);
		} catch (SQLException e) {
			System.out.println("PurchaseServiceImpl.addPurchase Exception");
			e.printStackTrace();
		}
		
		System.out.println("[purchaseServiceImpl.addPurchase] end");
	}

	@Override
	public Map<String, Object> getPurchaseList(SearchVO searchVO, String userId) {
		System.out.println("purchaseServiceImpl.getPurchaseList start");
		
		Map<String, Object> result = null;
		
		try {
			result = purchaseDAO.getPurchaseList(searchVO, userId);
		} catch (Exception e) {
			System.out.println("[PurchaseServiceImpl.getPurchaseList] Exception");
			e.printStackTrace();
		}
		
		System.out.println("[PurchaseServiceImpl.getPurchaseList] end");
		
		return result;
	}
}
