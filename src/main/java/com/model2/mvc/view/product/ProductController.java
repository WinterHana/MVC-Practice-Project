package com.model2.mvc.view.product;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.HistoryUtil;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.view.common.CommonController;

@Controller
public class ProductController extends CommonController  {
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@RequestMapping(value = "/listAdminProduct.do")
	public ModelAndView listAdminProduct(
			@ModelAttribute("search") SearchVO search, 
			HttpServletRequest request) {
		System.out.println("[ProductController.listAdminProduct()] start");
		
		// 1. Page setting
		String pageStr = request.getParameter("currentPage");
		
		int page = 0;
		if(pageStr != null && !pageStr.equals("undefined")) {
			page = Integer.parseInt(pageStr);
		}
		
		// Default page = 1;
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
		
		// 3. Get Enum Message
		Map<Integer, Object> pmap = purchaseService.getSalaList();
		
		Map<Integer, String> messageMap = new HashMap<Integer, String>();
		Iterator<Integer> keys = pmap.keySet().iterator();
		while (keys.hasNext()) {
			int key = keys.next();
			PurchaseVO purchaseVO = (PurchaseVO)pmap.get(key);
			String message = TranStatusCodeUtil.getMessage(purchaseVO.getTranCode(), true);
			messageMap.put(key, message);
		}
		
		// 4. Set ModelAndView
		ModelAndView modelAndView = new ModelAndView("forward:/product/listAdminProduct.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetProductList");
		modelAndView.addObject("pmap", pmap);
		modelAndView.addObject("messageMap", messageMap);
		
		System.out.println("[ProductController.listAdminProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/listUserProduct.do")
	public ModelAndView listUserProduct(
			@ModelAttribute("search") SearchVO search, 
			HttpServletRequest request) {
		System.out.println("[ProductController.listUserProduct()] start");
		
		// 1. Page setting
		String pageStr = request.getParameter("currentPage");
		int page = 0;
		if(pageStr != null && !pageStr.equals("undefined")) {
			page = Integer.parseInt(pageStr);
		}
		
		// Default page = 1;
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
		
		// 3. Get Enum Message
		Map<Integer, Object> pmap = purchaseService.getSalaList();
		
		Map<Integer, String> messageMap = new HashMap<Integer, String>();
		Iterator<Integer> keys = pmap.keySet().iterator();
		while (keys.hasNext()) {
			int key = keys.next();
			PurchaseVO purchaseVO = (PurchaseVO)pmap.get(key);
			String message = TranStatusCodeUtil.getMessage(purchaseVO.getTranCode(), false);
			messageMap.put(key, message);
		}
		
		// 4. Set ModelAndView
		ModelAndView modelAndView = new ModelAndView("forward:/product/listUserProduct.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetProductList");
		modelAndView.addObject("pmap", pmap);
		modelAndView.addObject("messageMap", messageMap);
		
		System.out.println("[ProductController.listUserProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getProduct.do")
	public ModelAndView getProduct(
			@ModelAttribute("product") ProductVO product,
			 HttpServletResponse response) {
		System.out.println("[ProductController.getProduct()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/product/getProduct.jsp");
		modelAndView.addObject("product", productService.getProduct(product.getProdNo()));
		HistoryUtil.saveHistory(response, product.getProdNo());
		
		System.out.println("[ProductController.getProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addProduct.do")
	public ModelAndView addProduct(@ModelAttribute("product") ProductVO product) {
		System.out.println("[ProductController.addProduct()] start");
		
		productService.addProduct(product);
		ModelAndView modelAndView = new ModelAndView("redirect:/product/completeAddView.jsp");
		
		System.out.println("[ProductController.addProduct()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateProductView.do")
	public ModelAndView updateProductView(@ModelAttribute("product") ProductVO product) {
		System.out.println("[ProductController.updaeProductView()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/product/updateProductView.jsp");
		modelAndView.addObject("product", productService.getProduct(product.getProdNo()));
		
		System.out.println("[ProductController.updaeProductView()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateProduct.do")
	public ModelAndView updateProduct(@ModelAttribute("product") ProductVO product) {
		System.out.println("[ProductController.updateProduct()] start");
		
		productService.updateProduct(product);
		ModelAndView modelAndView = new ModelAndView("redirect:/product/completeUpdateView.jsp");
		
		System.out.println("[ProductController.updateProduct()] end");
		
		return modelAndView;
	}
}
