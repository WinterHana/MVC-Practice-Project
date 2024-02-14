package com.model2.mvc.service.product;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.vo.ProductVO;

public interface ProductService {
	// findProduct
	public ProductVO findProduct(int productId);
	
	// getProductList
	
	// insertProduct
	public HashMap<String, Object> getProductList(SearchVO searchVO);
	
	// updateProduct
	
}
