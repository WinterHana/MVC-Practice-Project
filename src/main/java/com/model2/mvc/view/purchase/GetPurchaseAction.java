package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.PaymentOption;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.domain.PurchaseVO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[GetPurchaseAction.execute] start");
		
		// Get tranNo
		int tranNo = 0;
		try {
			tranNo = Integer.parseInt(request.getParameter("tranNo"));
		} catch (Exception e) {
			System.out.println("Integer.parseInt Exceptions");
			e.printStackTrace();
		}
		
		// Set Service and setAttribute
		PurchaseService service = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = service.getPurchase(tranNo);
		
		for(PaymentOption po : PaymentOption.values()) {
			if(purchaseVO.getPaymentOption().trim().equals(po.getNumber())) {
				request.setAttribute("paymentOption", po.getOption());
			}
		}	
	
		// System.out.println("[GetPurchaseAction.execute] purchaseVO " + purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("[GetPurchaseAction.execute] end");
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
