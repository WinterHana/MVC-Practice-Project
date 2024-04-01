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
							<span class = "tranCode text-primary" 
									data-no = "${purchase.tranNo}" 
									data-code ="updateTranCode${purchase.tranNo}"
									data-page = "${resultPage.currentPage}">변경하기</span>
							</form>
						</td>
                </c:forEach>
            </tbody>
          </table>
          <div class = "row">
          <jsp:include page = "../common/pageNavigator.jsp"/>
          </div>
       </div>
</body>
</html>