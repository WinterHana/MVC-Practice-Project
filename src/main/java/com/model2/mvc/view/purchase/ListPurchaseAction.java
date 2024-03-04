package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[ListPurchaseAction.execute] start");
		
		SearchVO searchVO = new SearchVO();
		
		// currentPage
		int currentPage = 1;
		String getCurrentPage = request.getParameter("currentPage");
		System.out.println("getCurrentPage : " + getCurrentPage);
		
		if(getCurrentPage != null) {
			if(getCurrentPage.equals("undefined") == false) {
				currentPage=Integer.parseInt(getCurrentPage);
			}
		}
		
		// searchVO
		searchVO.setPage(currentPage);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		searchVO.setPageSize(pageSize);
		searchVO.setPageUnit(pageUnit);
		
		// User information
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)(session.getAttribute("user"));
		String userId = userVO.getUserId();
		String userName = userVO.getUserName();
		
		// DAO control
		// PurchaseService service = new PurchaseServiceImpl();
		Map<String, Object> map = purchaseService.getPurchaseList(searchVO, userId);
		
		// PurchaseService, statue check
		// PurchaseService pservice = new PurchaseServiceImpl();
		Map<Integer, Object> pmap = purchaseService.getSalaList();
		
		// resultPage
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		// Enum Message
		Map<Integer, String> messageMap = new HashMap<Integer, String>();
		Iterator<Integer> keys = pmap.keySet().iterator();
		while (keys.hasNext()) {
			int key = keys.next();
			PurchaseVO purchaseVO = (PurchaseVO)pmap.get(key);
			String message = TranStatusCodeUtil.getMessage(purchaseVO.getTranCode());
			messageMap.put(key, message);
		}
		
		// Request control
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
		request.setAttribute("pmap", pmap);
		request.setAttribute("messageMap", messageMap);
		request.setAttribute("getList", " fncGetPurchaseList");		// 이전이나 다음 리스트로 이동할 URL 제공
		
		System.out.println("[ListPurchaseAction.execute] end");
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
