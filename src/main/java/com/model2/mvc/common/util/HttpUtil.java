package com.model2.mvc.common.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.service.domain.UserVO;



public class HttpUtil {
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path){
		try{
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			
			UserVO vo=(UserVO)request.getAttribute("vo");
			System.out.println("HttpUtil.foward : vo = " + vo);
			
			dispatcher.forward(request, response);
			
		}catch(Exception ex) {
			System.out.println("forward error : " + ex);
			throw new RuntimeException("forward error : " + ex);
		}
	}
	
	public static void redirect(HttpServletResponse response, String path){
		try{			
			response.sendRedirect(path);
		}catch(Exception ex){
			System.out.println("redirect error : " + ex);
			throw new RuntimeException("redirect error : " + ex);
		}
	}
}