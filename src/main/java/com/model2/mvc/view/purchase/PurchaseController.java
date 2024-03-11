package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.PaymentOption;
import com.model2.mvc.common.util.TranStatusCode;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.view.common.CommonController;

@Controller
public class PurchaseController extends CommonController {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@RequestMapping(value = "/listPurchase.do")
	public ModelAndView listPurchase(
			@ModelAttribute("search") SearchVO search, 
			HttpServletRequest request,
			HttpSession session) {
		System.out.println("[PurchaseController.listPurchase()] start");
		
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
		
		// 2. Get purchaseList
		boolean isAdmin = false;
		session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		if (user.getRole().equals("admin")) {
			isAdmin = true;
		}
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, user);
		
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
		
		String url = null;
		if(isAdmin) {
			url = "forward:/purchase/listAdminPurchase.jsp";
		} else {
			url = "forward:/purchase/listUserPurchase.jsp";
		}
		
		ModelAndView modelAndView = new ModelAndView(url);
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetPurchaseList");
		modelAndView.addObject("pmap", pmap);
		modelAndView.addObject("messageMap", messageMap);
		
		System.out.println("[PurchaseController.listPurchase()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getPurchase.do")
	public ModelAndView getPurchase(@ModelAttribute("purchase") PurchaseVO purchase) {
		System.out.println("[PurchaseController.getPurchase()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/getPurchase.jsp");
		
		PurchaseVO result = purchaseService.getPurchase(purchase.getTranNo());
		
		modelAndView.addObject("purchase", result);
		for(PaymentOption po : PaymentOption.values()) {
			if(result.getPaymentOption().trim().equals(po.getNumber())) {
				modelAndView.addObject("paymentOption", po.getOption());
			}
		}	
		
		System.out.println("[PurchaseController.getPurchase()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addPurchaseView.do")
	public ModelAndView addPurchaseView(
			@ModelAttribute("product") ProductVO product,
			HttpSession session) {
		System.out.println("[PurchaseController.addPurchaseView()] start");
		UserVO user =  (UserVO)session.getAttribute("user");
		ProductVO result = productService.getProduct(product.getProdNo());
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", result);
		modelAndView.addObject("userId", user.getUserId());
		
		System.out.println("[PurchaseController.addPurchaseView()] end");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/addPurchase.do")
	public ModelAndView addPurchase(
			@ModelAttribute("user") UserVO user,
			@ModelAttribute("product") ProductVO product,
			@ModelAttribute("purchase") PurchaseVO purchase) {
		System.out.println("[PurchaseController.addPurchase()] start");
		
		purchase.setTranCode(TranStatusCode.PURCHASED.getNumber());
		
		UserVO userResult = userService.getUser(user.getUserId());
		purchase.setBuyer(userResult);
		
		ProductVO productResult = productService.getProduct(product.getProdNo());
		purchase.setPurchaseProd(productResult);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("[PurchaseController.addPurchase()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateTranCode.do")
	public ModelAndView updateTranCode(
			@RequestParam("tranNo") int tranNo,
			@RequestParam("tranCode") String tranCode,
			@RequestParam("url")String url) {
		System.out.println("[PurchaseController.updateTranCode()] start");
		
		// PurchaseVO
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranCode(tranCode);
		purchaseVO.setTranNo(tranNo);
		
		purchaseService.updateTranCode(purchaseVO);
		
		System.out.println("[PurchaseController.updateTranCode()] end");
		
		return new ModelAndView("forward:/" + url);
	}
	
	@RequestMapping(value = "/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@ModelAttribute("purchase") PurchaseVO purchase) {
		System.out.println("[PurchaseController.updatePurchaseView()] start");
		
		PurchaseVO purchaseResult = purchaseService.getPurchase(purchase.getTranNo());
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase", purchaseResult);
		
		System.out.println("[PurchaseController.updatePurchaseView()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") PurchaseVO purchase) {
		System.out.println("[PurchaseController.updatePurchase()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/completeUpdateView.jsp");
		purchaseService.updatePurchase(purchase);
		
		System.out.println("[PurchaseController.updatePurchase()] end");
		
		return modelAndView;
	}
}
