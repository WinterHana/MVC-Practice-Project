package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[ListPurchaseAction.execute] start");
		
		SearchVO searchVO = new SearchVO();
		
		// Page information
		int page = 1;
		if(request.getParameter("page") != null) {
			page =  Integer.parseInt(request.getParameter("page"));
		}
		
		searchVO.setPage(page);
		
		String pageUnit = request.getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		// User information
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)(session.getAttribute("user"));
		String userId = userVO.getUserId();
		String userName = userVO.getUserName();
		
		// DAO control
		PurchaseService service = new PurchaseServiceImpl();
		Map<String, Object> map = service.getPurchaseList(searchVO, userId);
		
		// Request control
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
		
		System.out.println("[ListPurchaseAction.execute] end");
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
