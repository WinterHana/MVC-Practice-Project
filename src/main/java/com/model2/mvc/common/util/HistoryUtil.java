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
	public static Deque<String> cookieDeque;
	
	private HistoryUtil() {
		// blank	
	}
	
	private static void getQueue()  {
		if(cookieDeque == null) {
			cookieDeque = new LinkedList<String>();
		}
	}
	
	public static void saveHistory(int prodNo) {
		getQueue();
		
		String strProdNo = String.valueOf(prodNo);
		cookieDeque.addFirst(strProdNo);
		
		if(cookieDeque.size() > DEQUE_SIZE) {
			System.out.println("삭제된 Queue : " + cookieDeque.removeLast());
		}
	}
}
