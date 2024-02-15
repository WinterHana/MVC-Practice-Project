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
		
		// 번호나 정수에 대해서 한글 입력에 대한 유효성 처리
		if(searchVO != null) {
			String searchCondition = searchVO.getSearchCondition();
			String serachKeyword = searchVO.getSearchKeyword();
			
			if(searchCondition != null && serachKeyword != null &&
					(searchCondition.equals("prodNo") || searchCondition.equals("price"))) {
				
				// 숫자가 아닐 경우, 임의로 숫자로 변경 (여기서는 -1)
				if(serachKeyword.matches("^[\\D]*$")) {
					serachKeyword = "-1";
					searchVO.setSearchKeyword(serachKeyword);
				}
			}
		}

		try {
			result = productDAO.getProductList(searchVO);
		} catch (Exception e) {
			System.out.println("ProductServiceImpl.getUserList Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void insertProduct(ProductVO productVO) {
		try {
			productDAO.insertProduct(productVO);
		} catch (Exception e) {
			System.out.println("ProductServiceImpl.insertProduct Exception");
			e.printStackTrace();
		}
	}
}
