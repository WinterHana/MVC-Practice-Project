package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.FileVO;
import com.model2.mvc.service.domain.ProductVO;

public interface ProductService {
	public ProductVO getProduct(int productId);
	
	public Map<String, Object> getProductList(SearchVO searchVO);
	
	public int addProduct(ProductVO productVO, List<MultipartFile> multipartFiles);
	
	public int updateProduct(ProductVO productVO, List<MultipartFile> multipartFiles);
	
	public int deleteProduct(ProductVO product);
	
	public int addProductImage(FileVO file);
	
	public List<FileVO> getProductImage(int prodNo);
	
	public List<String> getProductInfo(String key);
}
