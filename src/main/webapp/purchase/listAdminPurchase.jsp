<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<html>
<head>
<title>�Ǹ� ����</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetPurchaseList(currentPage) {
	// document.getElementById("currentPage").value = currentPage;
	let url = '/purchase/listPurchase/' + currentPage;
	document.detailForm.action = url;
   	document.detailForm.submit();		
}

function updateTranCode(tranNo, updateTranCode) {
    var selectedValue = document.getElementById(updateTranCode).value;
    var url = "/purchase/updateTranCode?tranNo=" + tranNo + "&updateTranCode=" + selectedValue;
    window.location.href = url; 
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/purchase/listPurchase/1" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">�Ǹ� ����</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">��ü ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ��ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width = "150">��ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width = "150">��ǰ ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width = "150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:forEach var = "purchase" items = "${list}">
		<tr class="ct_list_pop">
		<td align="center">
			<!-- ���� ���� �� ���� -->
			<a href="/purchase/getPurchase/${purchase.tranNo}">${purchase.tranNo}</a>
		</td>
		<td></td>
		<td align="left">
			<a href="/user/getUser/{purchase.buyer.userId}">${purchase.buyer.userId}</a>
		</td>
		<td></td>
			<td align="left">${purchase.receiverPhone}</td>
		<td></td>
				<td align="left">${purchase.purchaseProd.prodNo}</td>
		<td></td>
				<td align="left">${purchase.prodCount }</td>
		<td></td>
		<td align="left">	
		<c:forEach var = "entry" items = "${messageMap}">
			<c:if test = "${entry.key == purchase.tranNo}">
				${entry.value}
			</c:if>
		</c:forEach>
		<select name = "updateTranCode" id = "updateTranCode${purchase.tranNo}" >
			<option value = "001" 
			${not empty purchase.tranNo && purchase.tranNo == "001" ? "selected" : '' }>
			�Ǹ� �Ϸ�</option>
			<option value = "002" 
			${not empty purchase.tranNo && purchase.tranNo == "002" ? "selected" : '' }>
			��� ��</option>
			<option value = "003" 
			${not empty purchase.tranNo && purchase.tranNo == "003" ? "selected" : '' }>
			��� �Ϸ�</option>
		</select>
		<a href="#" onclick="updateTranCode(${purchase.tranNo}, 'updateTranCode${purchase.tranNo}')">�����ϱ�</a>
		</td>
		<td></td>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
			<jsp:include page = "../common/pageNavigator.jsp"/>
		</td>
	</tr>
</table>

<!--  ������ Navigator �� -->
</form>
</div>
</body>
</html>