<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<html>
<head>
<title>판매 관리</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetPurchaseList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">판매 관리</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width = "150">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width = "150">제품 번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width = "150">개수</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:forEach var = "purchase" items = "${list}">
		<tr class="ct_list_pop">
		<td align="center">
			<!-- 구매 내역 상세 보기 -->
			<a href="/getPurchase.do?tranNo=${purchase.tranNo}">${purchase.tranNo}</a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a>
		</td>
		<td></td>
			<td align="left">${purchase.receiverPhone}</td>
		<td></td>
				<td align="left">${purchase.purchaseProd.prodNo}</td>
		<td></td>
				<td align="left">${purchase.prodCount }</td>
		<td></td>
		<td align="left">	
		<c:set var = "isContain" value = "false"/>
		<c:forEach var = "entry"  items = "${pmap}">
			<c:if test="${entry.key == purchase.purchaseProd.prodNo}">
				<c:set var = "tranCode"  value = "${pmap[purchase.purchaseProd.prodNo].tranCode}"/>
				<c:set var = "tranNo" value = "${pmap[purchase.purchaseProd.prodNo].tranNo}"/>
				${messageMap[purchase.purchaseProd.prodNo]}
				<c:if test="${tranCode == 003}">
					<a href="/updateTranCode.do?tranNo= ${tranNo}&tranCode=${tranCode}&url=listPurchase.do?menu=manage">배송 받기</a>
				</c:if>
				<c:set var = "isContain" value = "true"/>
			</c:if>
		</c:forEach>
		<c:if test="${not isContain}">
			판매중
		</c:if>
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

<!--  페이지 Navigator 끝 -->
</form>
</div>
</body>
</html>