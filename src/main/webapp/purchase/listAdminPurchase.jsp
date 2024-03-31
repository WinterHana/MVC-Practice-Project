<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html lang="en">

<head>
<script defer type="text/javascript" src ="/javascript/common.js"></script>
<script defer type="text/javascript" src ="/javascript/purchase/listAdminPurchase.js"></script>
<jsp:include page="../toolbar.jsp" flush="true"/>
<title>Purchase List</title>
</head>

<body bgcolor="#ffffff" text="#000000">
    <div class="container">
    	<br/>
    	<h1>구매 관리</h1>
    	<h3>전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</h3>
        <table class="table table-bordered">
            <thead class="table-light ">
                <tr>
                    <td width = "10%" align = "center">No</td>
                    <td width = "10%" align = "center">회원 ID</td>
                    <td width = "20%" align = "center">제품 이름</td>
                    <td width = "10%" align = "center">개수</td>
                    <td width = "20%" align = "center">배송 현황</td>
                    <td width = "40%" align = "center">메뉴</td>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var = "purchase" items = "${list}">
	               <tr>
	                   <td width = "10%" align = "center">${purchase.tranNo}</td>
	                   <td width = "10%" align = "center">
	                   <form name = "getUser" action = "/user/getUser/${purchase.buyer.userId}" method = "POST">
		                  <span class = "getUser text-primary" data-id ="${purchase.buyer.userId}">
		                   		<%-- <input type = "hidden" value = "${purchase.buyer.userId}"/> --%>
		                   		${purchase.buyer.userId}
		                  </span>
		               </form>
	                   </td>
	                   <td width = "20%" align = "center">${purchase.purchaseProd.prodName}</td>
	                   <td width = "10%" align = "center">${purchase.prodCount}</td>
	                   <td width = "20%" align = "center">
	                   <c:forEach var = "entry" items = "${messageMap}">
	                   		<c:if test = "${entry.key == purchase.tranNo}">
								${entry.value}
							</c:if>
						</c:forEach>
	                   </td>
	                   <td width = "40%" align = "center">
		                   <span class = "getPurchase text-primary" data-no ="${purchase.tranNo}">
		                   자세히 보기
		                   </span>
		                   	<select name = "updateTranCode" id = "updateTranCode${purchase.tranNo}" >
								<option value = "001"  ${not empty purchase.tranCode && purchase.tranCode eq "001" ? "selected" : "" }>
								판매 완료</option>
								<option value = "002"  ${not empty purchase.tranCode && purchase.tranCode eq "002" ? "selected" : "" }>
								배송 중</option>
								<option value = "003"  ${not empty purchase.tranCode && purchase.tranCode eq "003" ? "selected" : "" }>
								배송 완료</option>
							</select>
							<span class = "tranCode" 
								data-a = "${purchase.tranNo}" 
								data-b ="updateTranCode${purchase.tranNo}">변경하기</span>
						</td>
                </c:forEach>
            </tbody>
          </table>
          <div class = "row">
          <jsp:include page = "../common/pageNavigator.jsp"/>
          </div>
       </div>
<%-- <div style="width: 98%; margin-left: 10px;">

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
		<td class="ct_list_b" width = "150">제품 이름</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width = "150">개수</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송 현황</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:forEach var = "purchase" items = "${list}">
		<tr class="ct_list_pop">
		<td align="center">
			<!-- 구매 내역 상세 보기 -->
			<span class = "getPurchase" data-no ="${purchase.tranNo}">
			<a href="/purchase/getPurchase/${purchase.tranNo}">${purchase.tranNo}</a>
			${purchase.tranNo}
			</span>
		</td>
		<td></td>
		<td align="left">
			<span class = "getUser" data-id ="${purchase.buyer.userId}">
			<a href="/user/getUser/{purchase.buyer.userId}">${purchase.buyer.userId}</a>
			${purchase.buyer.userId}
			</span>
		</td>
		<td></td>
			<td align="left">${purchase.receiverPhone}</td>
		<td></td>
				<td align="left">${purchase.purchaseProd.prodName}</td>
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
</div> --%>
</body>
</html>