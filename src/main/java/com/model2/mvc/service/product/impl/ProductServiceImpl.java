package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		System.out.println("[" + getClass().getName() + " Default Constructor] Call");
	}
	
	public ProductServiceImpl(ProductDAO productDAO) {
		System.out.println("[" + getClass().getName() + " ProductDAO set Constructor] Call");
		this.productDAO = productDAO;
	}
	
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	@Override
	public ProductVO getProduct(int productId) {
		ProductVO prodVO = null;
		
		try {
			prodVO = productDAO.getProduct(productId);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".findProduct Exception");
			e.printStackTrace();
		}
		
		return prodVO;
	}
	
	@Override
	public Map<String, Object> getProductList(SearchVO searchVO) {	
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProductVO> list = null;
		int totalCount = 0;
		
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
			list = productDAO.getProductList(searchVO);
			totalCount = productDAO.getProductCount(searchVO);
			
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".getProduct Exception");
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}

	@Override
	public int addProduct(ProductVO productVO) {
		int result = 0;
		
		try {
			result = productDAO.addProduct(productVO);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".addProduct Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int updateProduct(ProductVO productVO) {
		int result = 0;
		try {
			result = productDAO.updateProduct(productVO);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".updateProduct Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deleteProduct(String prodName) {
		int result = 0;
		try {
			result = productDAO.deleteProduct(prodName);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".deleteProduct Exception");
			e.printStackTrace();
		}
		
		return result;
	}
}
