package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		// ProductService
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(searchVO);
		
		// PurchaseService, status check
		PurchaseService pservice = new PurchaseServiceImpl();
		Map<Integer, Object> pmap = pservice.getSalaList();
		
		// resultPage
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		// request
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("pmap", pmap);
		request.setAttribute("searchVO", searchVO);
		
		return "forward:/product/listProduct.jsp";
	}

}
