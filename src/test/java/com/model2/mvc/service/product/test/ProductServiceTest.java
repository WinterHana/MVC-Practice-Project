package com.model2.mvc.service.product.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.FileVO;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)

// @ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })

@ContextConfiguration(locations = {
		"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml"
})
public class ProductServiceTest {

	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Test
	public void productDAOTest() {
		// 1. getProduct
		ProductVO getProductVO = productDAO.getProduct(10000);
		Assert.assertNotNull(getProductVO);
		
		// 2. addProduct
		ProductVO addProductVO = new ProductVO();
		addProductVO.setProdName("testProduct");
		addProductVO.setProdDetail("For testing");
		addProductVO.setManuDate("2004-10-25");
		addProductVO.setPrice(10000);
		addProductVO.setFileName("nullString");
		
		int addResult = productDAO.addProduct(addProductVO);
		Assert.assertEquals(1, addResult);
		
		// 3. deleteProduct
		int deleteResult = productDAO.deleteProduct(addProductVO.getProdName());
		Assert.assertEquals(1, deleteResult);
		
		// 4. productImage 추가
//		FileVO file = new FileVO();
//		file.setFileName("테스트중입니다.");
//		file.setProdNo(10000);
//		int addProductImageResult = productDAO.addProductImage(file);
//		Assert.assertEquals(1, addProductImageResult);
		
		FileVO selectProductImageResult = productDAO.selectProductImage(10161);
		System.out.println(selectProductImageResult);
		Assert.assertEquals(10161, selectProductImageResult.getProdNo());
		
//		file.setFileName("테스트중입니다. : update");
//		int updateProductImageResult = productDAO.updateProductImage(file);
//		Assert.assertEquals(1, updateProductImageResult);
	}
	
	// @Test
	public void getProductTest() {
		ProductVO getProductVO = productService.getProduct(10000);
		Assert.assertNotNull(getProductVO);
		Assert.assertEquals("vaio vgn FS70B", getProductVO.getProdName());
	}
	
	// @Test
	public void addProductTest() {
		ProductVO addProductVO = new ProductVO();
		addProductVO.setProdName("testProduct");
		addProductVO.setProdDetail("For testing");
		addProductVO.setManuDate("2004-10-25");
		addProductVO.setPrice(10000);
		addProductVO.setFileName("nullString");
		
		FileVO file = new FileVO();
		file.setFileName("테스트중입니다.");
		file.setProdNo(10000);
		
		int addResult = productService.addProduct(addProductVO);
		Assert.assertEquals(1, addResult);
	}
	
	// @Test
	public void deleteProductTest() {
		int deleteResult = productService.deleteProduct("testProduct");
		Assert.assertEquals(1, deleteResult);
	}
	
	// @Test
	public void getProductListTest() {
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(2);
		searchVO.setPageSize(5);
		
		Map<String, Object> getListResult = productService.getProductList(searchVO);
		List<ProductVO> list = (List<ProductVO>) getListResult.get("list");
		int totalCount = (int) getListResult.get("totalCount");
		
		System.out.println("[getProductListTest] totalCount : " + totalCount);
		list.stream().forEach(System.out::println);
		
		Assert.assertEquals(5, list.size());
	}
	
	// @Test
	public void updateProductTest() {
		ProductVO updateProductVO = new ProductVO();
		updateProductVO.setProdNo(10009);
		updateProductVO.setProdName("제품1");
		updateProductVO.setProdDetail("For testing");
		updateProductVO.setManuDate("2004-10-25");
		updateProductVO.setPrice(10000);
		updateProductVO.setFileName("nullString");
		
		int updateResult = productService.updateProduct(updateProductVO);
		
		Assert.assertEquals(1, updateResult);
	}
}