package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService {
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}
	
	@Override
	public ProductVO findProduct(int productId) {
		ProductVO prodVO = null;
		
		try {
			prodVO = productDAO.findProduct(productId);
		} catch (Exception e) {
			System.out.println("ProductServiceImpl.findProduct Exception");
			e.printStackTrace();
		}
		
		System.out.println("ProductServiceImpl.findProduct findProduct : " + prodVO);
		
		return prodVO;
	}
	
	@Override
	public HashMap<String, Object> getProductList(SearchVO searchVO) {
		
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
