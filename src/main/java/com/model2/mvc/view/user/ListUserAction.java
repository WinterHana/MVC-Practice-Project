package com.model2.mvc.view.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class ListUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println("[ListUserAction.execute] start");
		
		SearchVO searchVO=new SearchVO();
		
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
		
		// service
		UserService service=new UserServiceImpl();
		Map<String,Object> map=service.getUserList(searchVO);

		// resultPage
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		// request
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("getList", "fncGetUserList");		// 이전이나 다음 리스트로 이동할 URL 제공
		
		System.out.println("[ListUserAction.execute] end");
		
		return "forward:/user/listUser.jsp";
	}
}