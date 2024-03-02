package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodId = 0;
		
		try {
			prodId = Integer.parseInt(request.getParameter("prodNo"));
		} catch (Exception e) {
			System.out.println("Integer.parseInt Exceptions");
			e.printStackTrace();
		}
		
		ProductService service = new ProductServiceImpl();
		ProductVO prodVO = service.findProduct(prodId);
		
		request.setAttribute("prodVO", prodVO);
		
		System.out.println("UpdateProductView.execute prodVO : " + prodVO);
		
		return "forward:/product/updateProductView.jsp";
	}

}
