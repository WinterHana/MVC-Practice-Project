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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

@Controller
@RequestMapping("/product/*")
public class ProductController extends CommonController  {
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@RequestMapping(value = "/listAdminProduct/{page}")
	public ModelAndView listAdminProduct(
			@ModelAttribute("search") SearchVO search, 
			@PathVariable("page") int page) {
		System.out.println("[ProductController.listAdminProduct()] start");
		
		// 1. Page setting Default page = 1;
		search.setPage(page);
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		search.setPageUnit(PAGE_UNIT); 
		search.setPageSize(PAGE_SIZE);
		
		// 2. Get ProductList
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage	= new Page(
				search.getPage(), 
				((Integer)map.get("totalCount")).intValue(), 
				PAGE_UNIT,
				PAGE_SIZE
		);
		
		// System.out.println("resultPage : " + resultPage);
		
		// 3. Set ModelAndView
		ModelAndView modelAndView = new ModelAndView("forward:/product/listAdminProduct.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetProductList");
		
		System.out.println("[ProductController.listAdminProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/listUserProduct/{page}")
	public ModelAndView listUserProduct(
			@ModelAttribute("search") SearchVO search, 
			@PathVariable("page") int page) {
		System.out.println("[ProductController.listUserProduct()] start");
		
		// 1. Page setting Default page = 1;
		search.setPage(page);
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		search.setPageUnit(PAGE_UNIT); 
		search.setPageSize(PAGE_SIZE);
		
		// 2. Get ProductList
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage	= new Page(
				search.getPage(), 
				((Integer)map.get("totalCount")).intValue(), 
				PAGE_UNIT,
				PAGE_SIZE
		);
		
		// 3. Set ModelAndView
		ModelAndView modelAndView = new ModelAndView("forward:/product/listUserProduct.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetProductList");
		
		System.out.println("[ProductController.listUserProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getProduct/{prodNo}")
	public ModelAndView getProduct(
			@PathVariable("prodNo") int prodNo) {
		System.out.println("[ProductController.getProduct()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/product/getProduct.jsp");
		ProductVO product = productService.getProduct(prodNo);
		modelAndView.addObject("product", product);
		
		HistoryUtil.saveHistory(product.getProdNo());
		modelAndView.addObject("historyInfo", HistoryUtil.getHistory());
		
		System.out.println("[ProductController.getProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addProduct")
	public ModelAndView addProduct(
			@ModelAttribute("product") ProductVO product,
			@RequestParam("multipartFile") List<MultipartFile> multiFileLists) {
		System.out.println("[ProductController.addProduct()] start");
		
		// 제품 추가
		productService.addProduct(product, multiFileLists);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/product/completeAddView.jsp");
		
		System.out.println("[ProductController.addProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateProductView")
	public ModelAndView updateProductView(@ModelAttribute("product") ProductVO product) {
		System.out.println("[ProductController.updaeProductView()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/product/updateProductView.jsp");
		modelAndView.addObject("product", productService.getProduct(product.getProdNo()));
		
		System.out.println("[ProductController.updaeProductView()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateProduct")
	public ModelAndView updateProduct(
			@ModelAttribute("product") ProductVO product,
			@RequestParam("multipartFile") List<MultipartFile> multiFileLists) {
		System.out.println("[ProductController.updateProduct()] start");
		
		// 제품 수정
		productService.updateProduct(product, multiFileLists);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/product/completeUpdateView.jsp");
		
		System.out.println("[ProductController.updateProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/deleteProduct")
	public ModelAndView deleteProduct(@ModelAttribute("product") ProductVO product) {
		System.out.println("[ProductController.deleteProduct()] start");
		
		productService.deleteProduct(product);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/product/listAdminProduct/1");
		
		System.out.println("[ProductController.deleteProduct()] end");
		
		return modelAndView;
	}
}
