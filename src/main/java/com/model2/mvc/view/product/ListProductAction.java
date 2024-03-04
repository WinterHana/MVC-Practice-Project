package com.model2.mvc.view.product;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("[ListProductAction.execute] start");
		
		// Information
		String menu = request.getParameter("menu");
		String title = null;
		String pageTarget = null;
		boolean isManager = false;
		if(menu != null) {
			if(menu.equals("manage")) {
				title = "상품 관리";
				pageTarget = "updateProductView";
				isManager = true;
			} else if (menu.equals("search")) {
				title = "상품 목록 조회";
				pageTarget = "getProduct";
			}
		}
		
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
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(currentPage);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		searchVO.setPageSize(pageSize);
		searchVO.setPageUnit(pageUnit);
		
		// ProductService
		// ProductService service=new ProductServiceImpl();
		Map<String,Object> map = productService.getProductList(searchVO);
		
		// PurchaseService, status check
		// PurchaseService pservice = new PurchaseServiceImpl();
		Map<Integer, Object> pmap = purchaseService.getSalaList();
		
		// resultPage
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		System.out.println(map.get("list"));
		
		// Enum Message
		Map<Integer, String> messageMap = new HashMap<Integer, String>();
		Iterator<Integer> keys = pmap.keySet().iterator();
		while (keys.hasNext()) {
			int key = keys.next();
			PurchaseVO purchaseVO = (PurchaseVO)pmap.get(key);
			String message = TranStatusCodeUtil.getMessage(purchaseVO.getTranCode(), isManager);
			messageMap.put(key, message);
		}
		
		// request
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("pmap", pmap);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("getList", " fncGetProductList");		// 이전이나 다음 리스트로 이동할 URL 제공
		request.setAttribute("menu", menu);
		request.setAttribute("title", title);
		request.setAttribute("pageTarget", pageTarget);
		request.setAttribute("isManager", isManager);
		request.setAttribute("messageMap", messageMap);
		
		System.out.println("[ListProductAction.execute] end... isManager = " + isManager);
		
		if(isManager) {
			return "forward:/product/listManagerProduct.jsp";
		} else {
			return "forward:/product/listUserProduct.jsp";
		}
	}
}
