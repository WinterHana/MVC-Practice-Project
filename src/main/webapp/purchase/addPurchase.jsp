<%@page import="com.model2.mvc.common.util.PaymentOption"%>
<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%
   	PurchaseVO purchaseVO = (PurchaseVO)request.getAttribute("purchaseVO");
 %>
 
<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td><%=purchaseVO.getPurchaseProd().getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td><%=purchaseVO.getBuyer().getUserId() %></td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
		<% if(purchaseVO.getPaymentOption().equals(PaymentOption.CASH.getNumber())) {%>
				���ݰ���
		<% } else { %>
				ī�����
		<% } %>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%=purchaseVO.getReceiverName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%=purchaseVO.getReceiverPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%=purchaseVO.getDivyAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%=purchaseVO.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%=purchaseVO.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>
</body>
</html>