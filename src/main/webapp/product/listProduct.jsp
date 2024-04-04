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
</body>
</html>
