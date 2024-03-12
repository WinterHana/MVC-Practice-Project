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
	private static Deque<String> deque;
	
	private HistoryUtil() {
		// blank	
	}
	
	private static void getQueue()  {
		if(deque == null) {
			deque = new LinkedList<String>();
		}
	}
	
	public static void saveHistory(HttpServletResponse response, int prodNo) {
		getQueue();
		
		String strProdNo = String.valueOf(prodNo);
		deque.addFirst(strProdNo);
		
		if(deque.size() > DEQUE_SIZE) {
			System.out.println("삭제된 Queue : " + deque.removeLast());
		}
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> iterator = deque.iterator();
		while(iterator.hasNext()) {
			String s = iterator.next();
			// Cookie csv로 , 사용 불가능
			sb.append(s + "/");
		}
		
		Cookie historyList = new Cookie("history", sb.toString());
		historyList.setMaxAge(600);
		
		response.addCookie(historyList);
	}
}
