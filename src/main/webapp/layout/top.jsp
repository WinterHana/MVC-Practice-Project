<%@page import="com.model2.mvc.service.domain.UserVO"%>
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%-- <%
	UserVO vo=(UserVO)session.getAttribute("user");
%>
 --%>
<html>
<head>
<title>Model2 MVC Shop</title>

<link href="/css/left.css" rel="stylesheet" type="text/css">

</head>

<body topmargin="0" leftmargin="0">
 
<table width="100%" height="10" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td width="500" height="15"><h2>&nbsp;&nbsp;Model2 MVC Shop</h2></td> 
</tr>
<tr>
 	<td width = "500" height="15" align="left" >
 	<c:if test = "${empty user}">
 		<h2>
	       <a href="/user/loginView.jsp" target="rightFrame">
	       	&nbsp;&nbsp;login
	       </a>   
	    </h2>
	</c:if>     
	<c:if test = "${not empty user}">
		<h2>
	      	<a href="/user/logout" target="_parent">
	      	&nbsp;&nbsp;logout
	      	</a>  
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
