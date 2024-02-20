package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.domain.ProductVO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductVO productVO = new ProductVO();

		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		try {
			productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		} catch(Exception e) {
			System.out.println("Integer.parseInt Exceptions");
			e.printStackTrace();
		} 
		productVO.setFileName(request.getParameter("fileName"));
	
		Cookie[] Cookies = request.getCookies();
		for(Cookie c : Cookies) {
			if(c.getName().equals("prodNo")) {
				productVO.setProdNo(Integer.parseInt(c.getValue()));
			}
		}
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(productVO);
		
		return "redirect:/product/completeUpdateView.jsp";
	}

}
