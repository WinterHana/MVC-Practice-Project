package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Get prodNo, userId
		int prodNo = Integer.parseInt((String)(request.getParameter("prodNo")));
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)(session.getAttribute("user"));
		String userId = userVO.getUserId();
		
		// Get product information
		ProductService ps = new ProductServiceImpl();
		ProductVO productVO = ps.findProduct(prodNo);
		
		// Set attribute in request
		request.setAttribute("productVO", productVO);
		request.setAttribute("userId", userId);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
