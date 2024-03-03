package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.UserVO;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		System.out.println(path);
		
		try{
			Action action = mapper.getAction(path);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			System.out.println("ActionServlet.service : resultPage = " + resultPage);
			System.out.println("ActionServlet.service : result = " + result);
			
			UserVO vo=(UserVO)request.getAttribute("vo");
			System.out.println("ActionServlet.service : vo = " + vo);
			
			ProductVO prodVO = (ProductVO) request.getAttribute("prodVO");
			System.out.println("ActionServlet.service : prodVO = " + vo);
		
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}