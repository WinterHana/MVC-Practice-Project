<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html lang="en">

<head>
<script defer type="text/javascript" src ="/javascript/common.js"></script>
<script defer type="text/javascript" src ="/javascript/purchase/listPurchase.js"></script>
<jsp:include page="../toolbar.jsp" flush="true"/>
<title>Purchase List</title>
</head>

<body bgcolor="#ffffff" text="#000000">
    <div class="container">
    	<br/>
    	<h1>구매 목록</h1>
    	<h3>전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</h3>
        <table class="table table-bordered">
            <thead class="table-light ">
                <tr>
                    <td width = "10%" align = "center">No</td>
                    <td width = "30%" align = "center">제품 이름</td>
                    <td width = "10%" align = "center">개수</td>
                    <td width = "20%" align = "center">배송 현황</td>
                    <td width = "40%" align = "center">메뉴</td>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var = "purchase" items = "${list}">
	               <tr>
	                   <td width = "10%" align = "center">${purchase.tranNo}</td>
	                   <td width = "30%" align = "center">${purchase.purchaseProd.prodName}</td>
	                   <td width = "10%" align = "center">${purchase.prodCount}</td>
	                   <td width = "20%" align = "center">
	                   <c:forEach var = "entry" items = "${messageMap}">
	                   		<c:if test = "${entry.key == purchase.tranNo}">
								${entry.value}
							</c:if>
						</c:forEach>
						<c:if test = "${purchase.tranCode == '002'}">
							<span class = "updateTranCode text-primary" 
							data-no = "${purchase.tranNo}" 
							data-page ="${resultPage.currentPage}">
								배송 받기
							</span>
						</c:if>
	                   </td>
	                   <td width = "40%" align = "center">
		                   <span class = "getPurchase text-primary" 
		                   data-no ="${purchase.tranNo}" >
		                   자세히 보기
		                   </span>
	                   </td>
	               </tr>
                </c:forEach>
            </tbody>
          </table>
          <div class = "row">
          <jsp:include page = "../common/pageNavigator.jsp"/>
          </div>
       </div>
</body>
</html>