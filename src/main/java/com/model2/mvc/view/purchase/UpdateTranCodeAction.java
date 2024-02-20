package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.domain.PurchaseVO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[UpdateTranCodeAction] start");
		
		// getParameter
		int tranNo = 0;
		try {
			tranNo = Integer.parseInt(request.getParameter("tranNo"));
		} catch (Exception e) {
			System.out.println("Integer.parseInt Exception");
			e.printStackTrace();
		}
		String tranCode = request.getParameter("tranCode");
		String url = request.getParameter("url");
		
		// PurchaseVO
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranCode(tranCode);
		purchaseVO.setTranNo(tranNo);
		
		PurchaseService ps = new PurchaseServiceImpl();
		ps.updateTranCode(purchaseVO);
		
		System.out.println("[UpdateTranCodeAction] start");
		
		return "forward:/" + url;
	}
	
}
