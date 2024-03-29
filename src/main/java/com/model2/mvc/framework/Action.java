package com.model2.mvc.framework;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public abstract class Action {
	// request 내의 메서드로 커버 가능
	private ServletContext servletContext;
	
	protected UserService userService;
	protected ProductService productService;
	protected PurchaseService purchaseService;
	
	public Action(){
		// Proxy problem 때문에 변경 보류
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
						new String[] {			
								"classpath:config/commonservice.xml" }
				);
//		ApplicationContext context =
//				new ClassPathXmlApplicationContext(
//						new String[] {			
//								"classpath:config/context-common.xml",
//								"classpath:config/context-aspect.xml",
//								"classpath:config/context-mybatis.xml",
//								"classpath:config/context-transaction.xml" }
//				);
//		userService = (UserService)context.getBean("userServiceImpl");
		productService = (ProductServiceImpl)context.getBean("productServiceImpl");
		purchaseService = (PurchaseServiceImpl)context.getBean("purchaseServiceImpl");
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	
	public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws Exception ;
}