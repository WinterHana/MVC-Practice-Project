<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<html>
<head>
<title>판매 관리</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	function fncGetPurchaseList(currentPage) {
		let url = '/purchase/listPurchase/' + currentPage;
		$("form").attr("method", "POST").attr("action", url).submit();
	}

	function updateTranCode(tranNo, selectId) {
    	let selectedValue = $("#" + selectId).val();
    	let url = "/purchase/updateTranCode?tranNo=" + tranNo + "&updateTranCode=" + selectedValue;
   	 	$(window.location).attr("href" ,url); 
	}
	
	$(function() {
		$("span.tranCode:contains('변경하기')").on("click", function() {
			let tranNo = $(this).data("a");
			let selectId = $(this).data("b");
			
			console.log("tranNo : " + tranNo);
			console.log("selectId : " + selectId);
			
			updateTranCode(tranNo, selectId);
		})
		
		$("span.getPurchase").on("click", function() {
			let url = "/purchase/getPurchase/"+ $(this).data("no");
			$(window.location).attr("href" ,url);
		})
		
		$("span.getUser").on("click", function() {
			let url = "/user/getUser/"+ $(this).data("id");
			$(window.location).attr("href" ,url);
		})
		
		$("span.pageNavigator").on("click", function() {
			fncGetPurchaseList($(this).data("page"));
		})
	})
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
			<span class = "getPurchase" data-no ="${purchase.tranNo}">
			<%-- <a href="/purchase/getPurchase/${purchase.tranNo}">${purchase.tranNo}</a> --%>
			${purchase.tranNo}
			</span>
		</td>
		<td></td>
		<td align="left">
			<span class = "getUser" data-id ="${purchase.buyer.userId}">
			<%-- <a href="/user/getUser/{purchase.buyer.userId}">${purchase.buyer.userId}</a> --%>
			${purchase.buyer.userId}
			</span>
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
			${not empty purchase.tranCode && purchase.tranCode eq "001" ? "selected" : "" }>
			판매 완료</option>
			<option value = "002" 
			${not empty purchase.tranCode && purchase.tranCode eq "002" ? "selected" : "" }>
			배송 중</option>
			<option value = "003" 
			${not empty purchase.tranCode && purchase.tranCode eq "003" ? "selected" : "" }>
			배송 완료</option>
		</select>
		<%-- <a href="#" onclick="updateTranCode(${purchase.tranNo}, 'updateTranCode${purchase.tranNo}')">변경하기</a> --%>
		<span class = "tranCode" 
					data-a = "${purchase.tranNo}" 
					data-b ="updateTranCode${purchase.tranNo}">변경하기</span>
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