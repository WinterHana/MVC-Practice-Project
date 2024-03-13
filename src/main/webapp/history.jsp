<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>

<title>열어본 상품 보기</title>

</head>
<body>
	<p>최근 본 상품 목록</p>
	<p>최대 5개까지 저장됩니다.</p>
<br>
<%
	String history = null;
	Cookie[] cookies = request.getCookies();
	
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			}
		}
		
		if (history != null) {
			String[] h = history.split("/");
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) {
%>
	<a href="/product/getProduct/<%=h[i]%>" target="rightFrame"><%=h[i]%></a>
	<br>
<%
				}
			}
		}
	}
%>

<%= history %>
<%= cookies.length %>
</body>
</html>