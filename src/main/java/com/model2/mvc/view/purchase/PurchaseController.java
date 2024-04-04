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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/purchase/*")
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
	
	// 개인 목록 확인 : role을 admin을 user로 바꿔서 검색한다.
	@RequestMapping(value = "/listPurchase/{page}")
	public ModelAndView listPurchase(
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
		
		// 잠시 Role을 user로 바꾼 다음, 나중에 다시 admin으로 바꾼다.
		Map<String, Object> map = null;
		if (user.getRole().equals("admin")) {
			user.setRole("user");
			map = purchaseService.getPurchaseList(search, user);
			user.setRole("admin");
		} else {
			map = purchaseService.getPurchaseList(search, user);
		}
		
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
		
		String url = "forward:/purchase/listPurchase.jsp";
		
//		String url = null;
//		if(isAdmin) {
//			url = "forward:/purchase/listAdminPurchase.jsp";
//		} else {
//			url = "forward:/purchase/listUserPurchase.jsp";
//		}
		
		ModelAndView modelAndView = new ModelAndView(url);
		modelAndView.addObject("list", list);
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetPurchaseList");
		modelAndView.addObject("messageMap", messageMap);
		
		System.out.println("[PurchaseController.listPurchase()] end");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/listAdminPurchase/{page}")
	public ModelAndView listAdminPurchase(
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
		List<PurchaseVO> list = (List<PurchaseVO>) map.get("list");
		Map<Integer, String> messageMap = new HashMap<Integer, String>();
		
		Iterator<PurchaseVO> iterator = list.iterator();
		while (iterator.hasNext()) {
			PurchaseVO purchaseResult = iterator.next();
			String tranCode = purchaseResult.getTranCode();
			String message = TranStatusCodeUtil.getMessage(tranCode, isAdmin);
			messageMap.put(purchaseResult.getTranNo(), message);
		}
		
		String url = "forward:/purchase/listAdminPurchase.jsp";
		
		ModelAndView modelAndView = new ModelAndView(url);
		modelAndView.addObject("list", list);
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetPurchaseList");
		modelAndView.addObject("messageMap", messageMap);
		
		System.out.println("[PurchaseController.listPurchase()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getPurchase/{tranNo}")
	public ModelAndView getPurchase(@PathVariable("tranNo") int tranNo) {
		System.out.println("[PurchaseController.getPurchase()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/getPurchase.jsp");
		
		PurchaseVO result = purchaseService.getPurchase(tranNo);
		
		modelAndView.addObject("purchase", result);
		for(PaymentOption po : PaymentOption.values()) {
			if(result.getPaymentOption().trim().equals(po.getNumber())) {
				modelAndView.addObject("paymentOption", po.getOption());
			}
		}	
		
		System.out.println("[PurchaseController.getPurchase()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addPurchaseView/{prodNo}")
	public ModelAndView addPurchaseView(
			@PathVariable("prodNo") int prodNo,
			HttpSession session) {
		System.out.println("[PurchaseController.addPurchaseView()] start");
		
		UserVO user =  (UserVO)session.getAttribute("user");
		ProductVO result = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", result);
		modelAndView.addObject("userId", user.getUserId());
		
		System.out.println("[PurchaseController.addPurchaseView()] end");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/addPurchase")
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
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/listPurchase/1");
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("[PurchaseController.addPurchase()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateTranCode/{page}", method = RequestMethod.GET)
	public ModelAndView updateTranCode(
			@RequestParam("tranNo") String tranNo,
			@RequestParam("updateTranCode") String tranCode,
			@PathVariable("page") int page) {
		System.out.println("[PurchaseController.updateTranCode()] start");
		
		// PurchaseVO
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranCode(tranCode);
		purchaseVO.setTranNo(Integer.parseInt(tranNo));
		
		purchaseService.updateTranCode(purchaseVO);
		
		System.out.println("[PurchaseController.updateTranCode()] end");
		
		return new ModelAndView("redirect:/purchase/listAdminPurchase/" + page);
	}
	
//	@RequestMapping(value = "/updateUserTranCode", method = RequestMethod.GET)
//	public ModelAndView updateUserTranCode(
//			@RequestParam("tranNo") String tranNo,
//			@RequestParam("updateTranCode") String tranCode) {
//		System.out.println("[PurchaseController.updateTranCode()] start");
//		
//		// PurchaseVO
//		PurchaseVO purchaseVO = new PurchaseVO();
//		purchaseVO.setTranCode(tranCode);
//		purchaseVO.setTranNo(Integer.parseInt(tranNo));
//		
//		purchaseService.updateTranCode(purchaseVO);
//		
//		System.out.println("[PurchaseController.updateTranCode()] end");
//		
//		return new ModelAndView("redirect:/purchase/listPurchase/1");
//	}
	
	@RequestMapping(value = "/updatePurchaseView/{tranNo}")
	public ModelAndView updatePurchaseView(@PathVariable("tranNo") int tranNo) {
		System.out.println("[PurchaseController.updatePurchaseView()] start");
		
		PurchaseVO purchaseResult = purchaseService.getPurchase(tranNo);
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase", purchaseResult);
		
		System.out.println("[PurchaseController.updatePurchaseView()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updatePurchase")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") PurchaseVO purchase) {
		System.out.println("[PurchaseController.updatePurchase()] start");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/purchase/getPurchase/" + purchase.getTranNo());
		purchaseService.updatePurchase(purchase);
		
		System.out.println("[PurchaseController.updatePurchase()] end");
		
		return modelAndView;
	}
	
	@PostMapping(value = "/deletePurchase") 
	public ModelAndView deletePurchase(
			@ModelAttribute("purchase") PurchaseVO purchase,
			@ModelAttribute("product") ProductVO product) {
		System.out.println("[PurchaseController.deletePurchase()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/listPurchase/1");
		purchase.setPurchaseProd(product);
		purchaseService.deletePurchase(purchase);
		
		System.out.println("[PurchaseController.deletePurchase()] end");
		
		return modelAndView;
	}
}
