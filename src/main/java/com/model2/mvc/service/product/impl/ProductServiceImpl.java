package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService {
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}
	
	@Override
	public HashMap<String, Object> getUserList(SearchVO searchVO) {
		
		HashMap<String, Object> result = null;
		
		try {
			result = productDAO.getProductList(searchVO);
		} catch (Exception e) {
			System.out.println("ProductServiceImpl.getUserList Exception");
			e.printStackTrace();
		}
		
		return result;
	}
	
}
