package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

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
	public Map<String, Object> getProductList(SearchVO searchVO) {
		System.out.println("[ProductServiceImpl.getProductList] start");
		
		Map<String, Object> result = null;
		
		// 번호나 정수에 대해서 한글 입력에 대한 유효성 처리
		if(searchVO != null) {
			String searchCondition = searchVO.getSearchCondition();
			String serachKeyword = searchVO.getSearchKeyword();
			
			if(searchCondition != null && serachKeyword != null) {
				if (searchCondition.equals("prodNo") || searchCondition.equals("price")) {
					serachKeyword = CommonUtil.notNumToNum(serachKeyword);
				} else if (searchCondition.equals("prodName")) {
					serachKeyword = CommonUtil.null2str(serachKeyword);
				}
				System.out.println("searchKeyword : " + serachKeyword);
				
				searchVO.setSearchKeyword(serachKeyword);
			}
		}

		try {
			result = productDAO.getProductList(searchVO);
		} catch (Exception e) {
			System.out.println("ProductServiceImpl.getUserList Exception");
			e.printStackTrace();
		}
		
		System.out.println("[ProductServiceImpl.getProductList] end");
		
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

	@Override
	public void updateProduct(ProductVO productVO) {
		try {
			productDAO.updateProduct(productVO);
		} catch (Exception e) {
			System.out.println("ProductServiceImpl.updateProduct Exception");
			e.printStackTrace();
		}
		
	}
}
