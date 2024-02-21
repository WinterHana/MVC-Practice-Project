<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%--
<%@page import="com.model2.mvc.common.util.PaymentOption"%>
<%@page import="com.model2.mvc.service.purchase.domain.PurchaseVO"%>
 <%
   	PurchaseVO purchaseVO = (PurchaseVO)request.getAttribute("purchaseVO");
 %>
--%>
<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${purchaseVO.purchaseProd.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${purchaseVO.buyer.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		<c:if test="${purchaseVO.paymentOption == 1}">
			현금 결제
		</c:if> 
				<c:if test="${purchaseVO.paymentOption == 2}">
			카드 결제
		</c:if> 
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${purchaseVO.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${purchaseVO.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${purchaseVO.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${purchaseVO.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${purchaseVO.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>
</body>
</html>