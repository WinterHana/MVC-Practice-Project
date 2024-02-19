package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class GetPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[GetPurchaseAction.execute] start");
		int tranNo = 0;
		
		try {
			tranNo = Integer.parseInt(request.getParameter("tranNo"));
		} catch (Exception e) {
			System.out.println("Integer.parseInt Exceptions");
			e.printStackTrace();
		}
		
		PurchaseService service = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = service.getPurchase(tranNo);
		
		System.out.println("[GetPurchaseAction.execute] purchaseVO " + purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("[GetPurchaseAction.execute] end");
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
