package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.HistoryUtil;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.domain.FileVO;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.view.common.CommonController;

@RestController
@RequestMapping("/rest/product/*")
public class ProductRestController extends CommonController  {
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
		
	// 제품 리스트를 가져옴
	@PostMapping(value = "/listProduct/{page}")
	public Map<String, Object> listUserProduct(
			@RequestBody SearchVO search,
			@PathVariable("page") int page) {
		System.out.println("[ProductController.listUserProduct()] start");
		
		// 1. Page setting Default page = 1;
		search.setPage(page);
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		search.setPageUnit(PAGE_UNIT); 
		search.setPageSize(8);
		
		// 2. Get ProductList
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage	= new Page(
				search.getPage(), 
				((Integer)map.get("totalCount")).intValue(), 
				PAGE_UNIT,
				PAGE_SIZE
		);
		
		// 3. Set ModelAndView
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", map.get("list"));
		result.put("resultPage", resultPage);
		result.put("search", search);
		result.put("getList", "fncGetProductList");
		
		System.out.println("[ProductController.listUserProduct()] end");
		
		return result;
	}
	
	// Test
	@RequestMapping(value = "/getProduct/{prodNo}")
	public Map<String, Object> getProduct(
			@PathVariable("prodNo") int prodNo) {
		System.out.println("[ProductController.getProduct()] start");
		
		ProductVO product = productService.getProduct(prodNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", "forward:/product/getProduct.jsp");
		map.put("product", product);
		
		System.out.println("[ProductController.getProduct()] end");
		
		return map;
	}
	
	// Test : form-data
	// https://stackoverflow.com/questions/48051177/content-type-multipart-form-databoundary-charset-utf-8-not-supported
	@RequestMapping(value = "/addProduct")
	public Map<String, Object> addProduct(
			@ModelAttribute ProductVO product,
			@ModelAttribute FileVO file) {
		System.out.println("[ProductController.addProduct()] start");
		
//		productService.addProduct(product);
//		
//		// Upload할 파일 설정s
//		String fileName = null;
//		MultipartFile fileResult = file.getMultipartFile();
//		if(!fileResult.isEmpty()) {
//			String originalFileName = fileResult.getOriginalFilename(); // 파일의 실제 이름
//			fileName = originalFileName;
//			
//			// new File 객체를 통해 file 객체를 만들고, 파일 새로 만들기
//			try {
//				fileResult.transferTo(new File("/Project_Eclipse/01.Model2MVCShop(stu)/src/main/webapp/images/uploadFiles/" + fileName));
//			} catch (IllegalStateException | IOException e) {
//				System.out.println("[addProduct] file Upload Exception");
//				e.printStackTrace();
//			}
//		}
//		
//		file.setFileName(fileName);
//		
//		if(!fileResult.isEmpty()) productService.addProductImage(file);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("path", "redirect:/product/completeAddView.jsp");
//		map.put("product", product);
		
		System.out.println("[ProductController.addProduct()] end");
		
//		return map;
		return null;
	}
	
	// Test
	 @RequestMapping(value = "/updateProductView")
	public Map<String, Object> updateProductView(@RequestBody ProductVO product) {
		System.out.println("[ProductController.updaeProductView()] start");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", "forward:/product/updateProductView.jsp");
		map.put("product",  productService.getProduct(product.getProdNo()));
		
		System.out.println("[ProductController.updaeProductView()] end");
		
		return map;
	}
	
	// Test : form-data
	@RequestMapping(value = "/updateProduct")
	public Map<String, Object> updateProduct(			
			@ModelAttribute ProductVO product,
			@ModelAttribute FileVO file) {
		System.out.println("[ProductController.updateProduct()] start");
		
//		String fileName = null;
//		MultipartFile fileResult = file.getMultipartFile();
//		if(!fileResult.isEmpty()) {
//			String originalFileName = fileResult.getOriginalFilename();
//			fileName = originalFileName;
//			
//			// new File 객체를 통해 file 객체를 만들고, 파일 새로 만들기
//			try {
//				fileResult.transferTo(new File("/Project_Eclipse/01.Model2MVCShop(stu)/src/main/webapp/images/uploadFiles/" + fileName));
//			} catch (IllegalStateException | IOException e) {
//				System.out.println("[addProduct] file Upload Exception");
//				e.printStackTrace();
//			}
//		}
//		
//		file.setFileName(fileName);
//		
//		productService.updateProduct(product);
//		if(!fileResult.isEmpty()) productService.updateProductImage(file);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("path", "redirect:/product/completeUpdateView.jsp");
//		map.put("product", product);
		
		System.out.println("[ProductController.updateProduct()] end");
		
//		return map;
		return null;
	}
	
	// AutoComplete method
	@PostMapping(value = "getProdNames")
	public List<String> getProdNames() {
		System.out.println("[UserController.getProdNames()] start");
		
		List<String> result = productService.getProductInfo("prodName");
		
		System.out.println("[UserController.getProdNames()] end");
		
		return result;
	}
	
	@PostMapping(value = "getProdNos")
	public List<String> getProdNos() {
		System.out.println("[UserController.getProdNos()] start");
		
		List<String> result = productService.getProductInfo("prodNo");
		
		System.out.println("[UserController.getProdNos()] end");
		
		return result;
	}
}
