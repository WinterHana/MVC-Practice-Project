package com.model2.mvc.service.product;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.domain.ProductVO;

public interface ProductService {
	// findProduct
	public ProductVO findProduct(int productId);
	
	// getProductList
	public Map<String, Object> getProductList(SearchVO searchVO);
	
	// insertProduct
	public void insertProduct(ProductVO productVO);
	
	// updateProduct
	public void updateProduct(ProductVO productVO);
}
