package com.model2.mvc.service.purchase.impl;

import java.sql.SQLException;
import java.util.HashMap;

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
		try {
			purchaseDAO.addPurchase(purchaseVO);
		} catch (SQLException e) {
			System.out.println("PurchaseServiceImpl.addPurchase Exception");
			e.printStackTrace();
		}
	}
}
