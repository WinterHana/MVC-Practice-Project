<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
<script defer type="text/javascript" src ="/javascript/common.js"></script>
<script defer type="text/javascript" src ="/javascript/product/listProduct.js"></script>
<jsp:include page="../toolbar.jsp" flush="true"/>
<title>Product List</title>
</head>

<body bgcolor="#ffffff" text="#000000">

    <div class="container">
        <br/>
        <div class = "row">
        	<div class = "col-sm-2">
        		<h1>상품 목록</h1>
        	</div>
        	 <div class = "col-sm-2">
		       	<c:if test = "${sessionScope.user.role eq 'admin'}">
		       		<form action = "/product/addProductView/" method = "post">
		       			<button class="btn btn-primary btn-lg">상품 추가하기</button>
		       		</form>
		       	</c:if>
        	</div>
        </div>
        <!-- menu -->
        <div class = "row">
        	<div class = "col-sm-3">
				<div class="input-group mb-3">
				        <span class="input-group-text">정렬</span>
					        <select class="form-select"  name="sortCondition">
								<option value="prodName"  ${not empty search.sortCondition && search.sortCondition == "prodName" ? "selected" : '' }>상품 이름</option>
								<option value="price"  ${not empty search.sortCondition && search.sortCondition == "price" ? "selected" : '' }>상품 가격</option>
				      	</select>
				</div>
			</div>
			<div class = "col-sm-3">
				<div class="input-group mb-3">
				    <span class="input-group-text">검색</span>
					    <select class="form-select"  name="searchCondition">
							<option value="prodName"  ${not empty search.searchCondition && search.searchCondition == "prodName" ? "selected" : '' }>상품 이름</option>
							<option value="price"  ${not empty search.searchCondition && search.searchCondition == "price" ? "selected" : '' }>상품 가격</option>
				    </select>
				</div> 
			</div>
			<div class = "col-sm-6" name = "priceContent">    
				<div class="input-group mb-3"> 
						시작 : 
						<input type="text" class="form-control"  name="searchKeywordSub"  value="${search.searchKeywordSub}">
						끝 : 
			    		<input type="text" class="form-control"  name="searchKeywordThird"  value="${search.searchKeywordThird}">
			    		<button class="btn btn-outline-secondary" type="button">검색</button>				
				</div>
			</div>
			<div class = "col-sm-6"name = "prodNameContent">  
				<div class="input-group mb-3" > 
						<input type="text" class="form-control"  name="searchKeyword"  value="${search.searchKeyword}">
				    	<button class="btn btn-outline-secondary" type="button">검색</button>					
				</div>
			</div>
		</div>
		<!-- product -->
        <div class="row" name = "productList">
        </div>
    </div>
    
<%--
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>			
		<td align = "left" width = "1200" height = 20>
			정렬 기준
			<select name="sortCondition"  id = "sortCondition" class="ct_input_g" style="width:80px" onchange = "submitDetailForm()">
				<option value="prodName"  ${not empty search.sortCondition && search.sortCondition == "prodName" ? "selected" : '' }>상품 이름</option>
				<option value="price"  ${not empty search.sortCondition && search.sortCondition == "price" ? "selected" : '' }>상품 가격</option>
			</select>
		</td>
		<td align="right" width = "400" height = 20>
			<div id = "prodNameContent">
				<input type="text" id = "searchKeyword" name="searchKeyword" class = "searchText"   value="${search.searchKeyword}"
					class=ct_input_g" style="width:150px; height:19px">
			</div>
			<div id = "priceContent">
				<input type="text" name="searchKeywordSub" class = "searchText"  value="${search.searchKeywordSub}"
					class=ct_input_g" style="width:100px; height:19px">
				~
				<input type="text" name="searchKeywordThird" class = "searchText"  value="${search.searchKeywordThird}"
					class=ct_input_g" style="width:100px; height:19px">
			</div>
		</td>
		<td align = "right" width = "100"  height = 20>	
			<select name="searchCondition"  id = "searchCondition" class="ct_input_g" style="width:80px" onchange = "showContentBySelectBox()">
				<option value="prodName"  ${not empty search.searchCondition && search.searchCondition == "prodName" ? "selected" : '' }>상품 이름</option>
				<option value="price"  ${not empty search.searchCondition && search.searchCondition == "price" ? "selected" : '' }>상품 가격</option>
			</select>
		</td>
		<td width = "30" height = 20>
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="50">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="150">사진</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="100">가격</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b" width>상품 설명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="100">남은 개수</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="150">판매 유무</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:set var = "no" value = "0"/>
	<c:forEach var = "product" items = "${list}">
		<c:set var = "no" value = "${no + 1}"/>
		<tr class="ct_list_pop">
		<td align="center">${no}</td>
		<td></td>
		<td align="center">
		<a href="/product/getProduct/${product.prodNo}">
		<span class = "getProduct" data-no ="${product.prodNo}">
		<img src = "/images/uploadFiles/${product.fileName[0]}" width = "120" height = "90"/>
		</span>
		</a>
		</td>
		<td></td>
		<td align = "center">
		<span class = "getProduct" data-no ="${product.prodNo}">
		<a href="/product/getProduct/${product.prodNo}">${product.prodName}</a>
		${product.prodName}
		</span>
		</td>
		<td></td>
		<td align="center">${product.price}</td> 
		<td></td>
		<td align="center">${product.prodDetail}</td>		
		<td></td>		
		<td align="center">${product.count}</td>		
		<td></td>
		<td align="center">
		<c:if test="${product.count > 0}">
			판매중
		</c:if>
		<c:if test = "${product.count <= 0}">
			매진
		</c:if>
		</td>		
		<td></td>
		</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
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
