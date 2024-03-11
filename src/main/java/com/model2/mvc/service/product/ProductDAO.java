package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.ProductVO;

public interface ProductDAO {
	public int addProduct(ProductVO productVO);
	
	public ProductVO getProduct(int prodNo);
	
	public int updateProduct(ProductVO productVO);
	
	public int deleteProduct(String prodName);
	
	public List<ProductVO> getProductList(SearchVO searchVO);
	
	public int getProductCount(SearchVO searchVO);
	
	// product의 개수 조절
	public int updateProductCount(Map<String, Integer> map);
}
