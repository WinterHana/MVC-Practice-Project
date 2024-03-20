package com.model2.mvc.common.web;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.util.HistoryUtil;

public class CookieInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String historyInfo = null;
		String path = "/layout/left.jsp";
		if((historyInfo = (String)modelAndView.getModel().get("historyInfo")) != null) {
			Cookie cookie = new Cookie("historyInfo", historyInfo);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}
}
