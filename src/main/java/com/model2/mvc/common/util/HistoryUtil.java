package com.model2.mvc.common.util;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Queue를 이용해서 Cookie를 이용한 열람 내용을 관리한다.
 */
public class HistoryUtil {
	
	private static final int DEQUE_SIZE = 5;
	private static Deque<String> cookieDeque;
	
	
	private HistoryUtil() {
		// blank	
	}
	
	public static Deque<String> getCookieDeque()  {
		if(cookieDeque == null) {
			cookieDeque = new LinkedList<String>();
		}
		
		return cookieDeque;
	}
	
	public static void saveHistory(int prodNo) {
		getCookieDeque();
		
		String strProdNo = String.valueOf(prodNo);
		cookieDeque.addFirst(strProdNo);
		
		if(cookieDeque.size() > DEQUE_SIZE) {
			System.out.println("삭제된 Queue : " + cookieDeque.removeLast());
		}
	}
	
	public static String getHistory() {
		StringBuffer sb = new StringBuffer();
		if(cookieDeque != null || !cookieDeque.isEmpty()) {
			for(String s : cookieDeque)
			sb.append(s + "/");
		}
		
		return sb.toString();
	}
}
