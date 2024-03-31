<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<!DOCTYPE html>
<html lang="en">

<head>
<script defer type="text/javascript" src ="/javascript/common.js"></script>
<script defer type="text/javascript" src ="/javascript/product/updateProduct.js"></script>
<jsp:include page="../toolbar.jsp" flush="true"/>
<title>Product Update</title>
</head>

<body bgcolor="#ffffff" text="#000000">
	<div class="container">
		<br/>
	 	<h1>제품 수정</h1>
        <form action = "/product/updateProduct" method="post" enctype="multipart/form-data">
        	<input type="hidden" name="prodNo" value="${product.prodNo}"/>
            <div class="row">
                <div class="col-md-7">
                    <div class="input-group input-group-lg flex-nowrap mb-3">
                        <span class="input-group-text" id="addon-wrapping">상품 이름</span>
                        <input class="form-control" type="text" name="prodName" value="${product.prodName}">
                    </div>

                    <div class="input-group input-group-lg flex-nowrap mb-3">
                        <span class="input-group-text" id="addon-wrapping">상품 상세 정보</span>
                        <input class="form-control" type="text" name="prodDetail" value="${product.prodDetail}">
                    </div>

                    <div class="input-group input-group-lg flex-nowrap mb-3">
                        <span class="input-group-text" id="addon-wrapping">제조 일자</span>
                        <input class="form-control" type="date" name="manuDate" value="${product.manuDate}">
                    </div>

                    <div class="input-group input-group-lg flex-nowrap mb-3">
                        <span class="input-group-text" id="addon-wrapping">가격</span>
                        <input class="form-control" type="text" name="price" value="${product.price}">
                    </div>

                    <div class="input-group input-group-lg flex-nowrap mb-3">
                        <span class="input-group-text" id="addon-wrapping">상품 개수</span>
                        <input class="form-control" type="text" name="count" value="${product.count}">
                    </div>
                    
                    <div class="input-group input-group-lg flex-nowrap mb-3">
                        <span class="input-group-text" id="addon-wrapping">상품 이미지</span>
                        <input class="form-control" type="file" name="multipartFile" id="formFileMultiple" multiple/>
                    </div>

                    <button type="button" name="update" class="btn btn-warning">수정하기</button>

                    <button type="button" name="back" class="btn btn-success">뒤로가기</button>
                </div>
            </div>
        </form>
      </div> 
</body>
</html>