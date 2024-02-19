package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdatePurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[UpdatePurchaseViewAction.execute] start");
		
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
		
		// System.out.println("[UpdatePurchaseViewAction.execute] purchaseVO " + purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("[UpdatePurchaseViewAction.execute] end");
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}
