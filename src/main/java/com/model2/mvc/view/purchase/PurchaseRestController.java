package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.PaymentOption;
import com.model2.mvc.common.util.TranStatusCode;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.RestApiCommonVO;
import com.model2.mvc.service.domain.UpdateTranCodeVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.view.common.CommonController;

@RestController
@RequestMapping("/rest/purchase/*")
public class PurchaseRestController extends CommonController {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	// Test
	@RequestMapping(value = "/listPurchase/{page}")
	public Map<String, Object> listPurchase(
			@ModelAttribute("search") SearchVO search, 
			@PathVariable("page") int page,
			HttpSession session) {
		System.out.println("[PurchaseController.listPurchase()] start");
		
		// Default page = 1;
		search.setPage(page);
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		search.setPageUnit(PAGE_UNIT); 
		search.setPageSize(PAGE_SIZE);
		
		// 2. Get purchaseList
		boolean isAdmin = false;
		UserVO user = (UserVO) session.getAttribute("user");
		if (user != null && user.getRole().equals("admin")) {
			isAdmin = true;
		} else {
			user = new UserVO();
			user.setUserId("test");
		}
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, user);
		
		Page resultPage	= new Page(
				search.getPage(), 
				((Integer)map.get("totalCount")).intValue(), 
				PAGE_UNIT,
				PAGE_SIZE
		);
		
		// 3. Get Enum Message
		List<PurchaseVO> list = (List<PurchaseVO>) map.get("list");
		Map<Integer, String> messageMap = new HashMap<Integer, String>();
		
		Iterator<PurchaseVO> iterator = list.iterator();
		while (iterator.hasNext()) {
			PurchaseVO purchaseResult = iterator.next();
			String tranCode = purchaseResult.getTranCode();
			String message = TranStatusCodeUtil.getMessage(tranCode, isAdmin);
			messageMap.put(purchaseResult.getTranNo(), message);
		}
		
		String url = null;
		if(isAdmin) {
			url = "forward:/purchase/listAdminPurchase.jsp";
		} else {
			url = "forward:/purchase/listUserPurchase.jsp";
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("path", url);
		resultMap.put("list", list);
		resultMap.put("resultPage",	resultPage);
		resultMap.put("search", search);
		resultMap.put("getList", "fncGetPurchaseList");
		resultMap.put("messageMap", messageMap);
		
		System.out.println("[PurchaseController.listPurchase()] end");
		
		return resultMap;
	}
	
	// Test
	@RequestMapping(value = "/getPurchase/{tranNo}")
	public Map<String, Object> getPurchase(@PathVariable("tranNo") int tranNo) {
		System.out.println("[PurchaseController.getPurchase()] start");
		
		PurchaseVO result = purchaseService.getPurchase(tranNo);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("path", "forward:/purchase/getPurchase.jsp");
		resultMap.put("purchase", result);
		
		for(PaymentOption po : PaymentOption.values()) {
			if(result.getPaymentOption().trim().equals(po.getNumber())) {
				resultMap.put("paymentOption",  po.getOption());
			}
		}	
		
		System.out.println("[PurchaseController.getPurchase()] end");
		
		return resultMap;
	}
	
	// Test
	@RequestMapping(value = "/addPurchaseView/{prodNo}")
	public Map<String, Object> addPurchaseView(
			@PathVariable("prodNo") int prodNo,
			HttpSession session) {
		System.out.println("[PurchaseController.addPurchaseView()] start");
		
		UserVO user =  (UserVO)session.getAttribute("user");
		ProductVO result = productService.getProduct(prodNo);
		if(user == null) {
			user = new UserVO();
			user.setUserId("test");
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("path", "forward:/purchase/addPurchaseView.jsp");
		resultMap.put("product", result);
		resultMap.put("userId", user.getUserId());
		
		System.out.println("[PurchaseController.addPurchaseView()] end");
		
		return resultMap;
		
	}
	
	// Test
	@RequestMapping(value = "/addPurchase")
	public Map<String, Object> addPurchase(
			@RequestBody RestApiCommonVO restApiCommon) {
		System.out.println("[PurchaseController.addPurchase()] start");
		
		UserVO user = restApiCommon.getUser();
		ProductVO product = restApiCommon.getProduct();
		PurchaseVO purchase = restApiCommon.getPurchase();
		
		purchase.setTranCode(TranStatusCode.PURCHASED.getNumber());
		
		UserVO userResult = userService.getUser(user.getUserId());
		purchase.setBuyer(userResult);
		
		ProductVO productResult = productService.getProduct(product.getProdNo());
		purchase.setPurchaseProd(productResult);
		
		purchaseService.addPurchase(purchase);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("path", "forward:/purchase/addPurchase.jsp");
		result.put("purchase", purchase);
		
		System.out.println("[PurchaseController.addPurchase()] end");
		
		return result;
	}
	
	// Test
	@PostMapping(value = "/updateTranCode/{page}")
	public Map<String, Object> updateTranCode(
			@RequestBody UpdateTranCodeVO updateTranCode,
			@PathVariable("page") int page) {
		System.out.println("[PurchaseController.updateTranCode()] start");
		
		// PurchaseVO
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranCode(updateTranCode.getTranCode());
		purchaseVO.setTranNo(updateTranCode.getTranNo());
		
		purchaseService.updateTranCode(purchaseVO);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("path", "redirect:/purchase/listPurchase/" + page);
		
		System.out.println("[PurchaseController.updateTranCode()] end");
		
		return result;
	}
	
	// Test
	@RequestMapping(value = "/updatePurchaseView/{tranNo}")
	public Map<String, Object> updatePurchaseView(@PathVariable("tranNo") int tranNo) {
		System.out.println("[PurchaseController.updatePurchaseView()] start");
		
		PurchaseVO purchaseResult = purchaseService.getPurchase(tranNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(purchaseResult != null) {
			map.put("path", "forward:/purchase/updatePurchaseView.jsp");
			map.put("purchase", purchaseResult);
		}

		System.out.println("[PurchaseController.updatePurchaseView()] end");
		
		return map;
	}
	
	// Test
	@RequestMapping(value = "/updatePurchase")
	public Map<String, Object> updatePurchase(@RequestBody PurchaseVO purchase) {
		System.out.println("[PurchaseController.updatePurchase()] start");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = purchaseService.updatePurchase(purchase);
		
		if(result == 1) {
			map.put("path", "redirect:/purchase/getPurchase/" + purchase.getTranNo());
		}
		
		System.out.println("[PurchaseController.updatePurchase()] end");
		
		return map;
	}
}
