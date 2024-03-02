package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.TranStatusCode;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseVO purchaseVO = new PurchaseVO();
		
		// System.out.println("Integer.parseInt(request.getParameter()) " + Integer.parseInt(request.getParameter("prodNo")));
		
		// Input information
		purchaseVO.setPaymentOption((String)request.getParameter("paymentOption"));
		purchaseVO.setReceiverName((String)request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone((String)request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr((String)request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest((String)request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate((String)request.getParameter("receiverDate"));
		purchaseVO.setTranCode(TranStatusCode.PURCHASED.getNumber());
		
		// User information
		UserService us = new UserServiceImpl();
		UserVO userVO = us.getUser(request.getParameter("buyerId"));
		purchaseVO.setBuyer(userVO);
		
		// Product information
		ProductService productService = new ProductServiceImpl();
		ProductVO productVO = productService.findProduct(Integer.parseInt(request.getParameter("prodNo")));
		purchaseVO.setPurchaseProd(productVO);
		
		// Debugging
		System.out.println("AddPurchaseAction.execute purchaseVO : " + purchaseVO);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
