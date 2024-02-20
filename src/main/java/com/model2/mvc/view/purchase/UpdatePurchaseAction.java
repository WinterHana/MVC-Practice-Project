package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.domain.PurchaseVO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[UpdatePurchaseAction.execute] start");
		
		PurchaseVO purchaseVO = new PurchaseVO();
		try {
			purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		} catch (Exception e) {
			System.out.println("Integer.parseInt Exception");
			e.printStackTrace();
		}
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		
		PurchaseService ps = new PurchaseServiceImpl();
		ps.updatePurchase(purchaseVO);
		
		System.out.println("[UpdatePurchaseAction.execute] end");
		
		return "forward:/purchase/completeUpdateView.jsp";
	}

}
