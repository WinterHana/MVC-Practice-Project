package com.model2.mvc.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.model2.mvc.service.domain.UserVO;

public class LogonCheckInterceptor extends HandlerInterceptorAdapter {
	
	public LogonCheckInterceptor() {
		System.out.println("LogonCheckInterceptor() Default constructor call");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("[LogonCheckInterceptor start]");
		
//		// 0. 로그인 유무 확인
//		HttpSession session = request.getSession(true);
//		UserVO sessionUser = null;
//		if ((sessionUser=(UserVO)session.getAttribute("sessionUser")) == null) {
//			sessionUser = new UserVO();
//		}
//		
//		// 1. 로그인한 회원이라면...
//		if(sessionUser.isActive()) {
//			String uri = request.getRequestURI();
//			if(uri.indexOf("logonAction") != -1 || uri.indexOf("logon") != -1) {
//				request.getRequestDispatcher("/user/home.jsp").forward(request, response);
//				System.out.println("[로그인을 한 상태입니다. 로그인 후에는 불필요한 요청입니다.]");
//				System.out.println("[LogonCheckInterceptor end]");
//				return false;
//			}
//			
//			System.out.println("[로그인을 한 상태입니다.]");
//			System.out.println("[LogonCheckInterceptor end]");
//			return true;
//			
//		// 2. 미로그인한 회원이라면...
//		} else {
//			String uri = request.getRequestURI();
//			if(uri.indexOf("logonAction") != -1 || uri.indexOf("logon") != -1) {
//				System.out.println("[로그인 시도 상태...]");
//				System.out.println("[LogonCheckInterceptor end]");
//				return true;
//			}
//			
//			request.getRequestDispatcher("/user/logon.jsp").forward(request, response);
//			System.out.println("[로그인 이전...]");
//			System.out.println("[LogonCheckInterceptor end]");
//			return false;
//		}
		
		return true;
	}
}
