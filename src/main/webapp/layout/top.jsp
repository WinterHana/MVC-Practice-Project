<%@page import="com.model2.mvc.service.domain.UserVO"%>
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>집에 가고 싶어</title>

<link href="/css/left.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("span.loginAndOut:contains('login')").on("click", function() {
			$(window.parent.frames["rightFrame"].document.location)
			.attr("href" ,"/user/loginView.jsp"); 
		})
		
		$("span.loginAndOut:contains('logout')").on("click", function() {
			$(window.parent.frames["rightFrame"].document.location)
			.attr("href" ,"/user/logout"); 
			window.parent.location.reload();
		})
	})
	
</script>
</head>

<body topmargin="0" leftmargin="0">
 
<table width="100%" height="10" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td width="500" height="15"><h2>&nbsp;&nbsp;집에 가고 싶어</h2></td> 
</tr>
<tr>
 	<td width = "500" height="15" align="left" >
 	<c:if test = "${empty user}">
 		<h2>
	      	&nbsp;&nbsp;<span class = "loginAndOut">login</span>
	    </h2>
	</c:if>     
	<c:if test = "${not empty user}">
		<h2>
	      	&nbsp;&nbsp;<span class = "loginAndOut">logout</span>
	    </h2>
	</c:if>     
	</td>
</tr>
<tr>
	<td height = "10"><hr width = "100%" color = "black" size = 2></td>
</tr>
</table>
</body>
</html>
