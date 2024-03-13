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
		
		if(HistoryUtil.cookieDeque != null) {
			StringBuffer sb = new StringBuffer();
			Iterator<String> iterator = HistoryUtil.cookieDeque.iterator();
			int index = 0;
			while(iterator.hasNext()) {
				String prodNo = iterator.next();
				response.addCookie(new Cookie("prodNo"+ index, prodNo));
			}
		}
		
		return true;
	}
}
