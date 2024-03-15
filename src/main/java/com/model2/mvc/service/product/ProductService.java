package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.FileVO;
import com.model2.mvc.service.domain.ProductVO;

public interface ProductService {
	public ProductVO getProduct(int productId);
	
	public Map<String, Object> getProductList(SearchVO searchVO);
	
	public int addProduct(ProductVO productVO);
	
	public int updateProduct(ProductVO productVO);
	
	public int deleteProduct(String prodName);
	
	public int addProductImage(FileVO file);
	
	public int updateProductImage(FileVO file);
	
	public FileVO selectProductImage(int prodNo);
}
