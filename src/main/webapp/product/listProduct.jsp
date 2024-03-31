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
        		<h1>��ǰ ���</h1>
        	</div>
        	 <div class = "col-sm-2">
		       	<c:if test = "${sessionScope.user.role eq 'admin'}">
		       		<form action = "/product/addProductView/" method = "post">
		       			<button class="btn btn-primary btn-lg">��ǰ �߰��ϱ�</button>
		       		</form>
		       	</c:if>
        	</div>
        </div>
        <!-- menu -->
        <div class = "row">
        	<div class = "col-sm-3">
				<div class="input-group mb-3">
				        <span class="input-group-text">����</span>
					        <select class="form-select"  name="sortCondition">
								<option value="prodName"  ${not empty search.sortCondition && search.sortCondition == "prodName" ? "selected" : '' }>��ǰ �̸�</option>
								<option value="price"  ${not empty search.sortCondition && search.sortCondition == "price" ? "selected" : '' }>��ǰ ����</option>
				      	</select>
				</div>
			</div>
			<div class = "col-sm-3">
				<div class="input-group mb-3">
				    <span class="input-group-text">�˻�</span>
					    <select class="form-select"  name="searchCondition">
							<option value="prodName"  ${not empty search.searchCondition && search.searchCondition == "prodName" ? "selected" : '' }>��ǰ �̸�</option>
							<option value="price"  ${not empty search.searchCondition && search.searchCondition == "price" ? "selected" : '' }>��ǰ ����</option>
				    </select>
				</div> 
			</div>
			<div class = "col-sm-6" name = "priceContent">    
				<div class="input-group mb-3"> 
						���� : 
						<input type="text" class="form-control"  name="searchKeywordSub"  value="${search.searchKeywordSub}">
						�� : 
			    		<input type="text" class="form-control"  name="searchKeywordThird"  value="${search.searchKeywordThird}">
			    		<button class="btn btn-outline-secondary" type="button">�˻�</button>				
				</div>
			</div>
			<div class = "col-sm-6"name = "prodNameContent">  
				<div class="input-group mb-3" > 
						<input type="text" class="form-control"  name="searchKeyword"  value="${search.searchKeyword}">
				    	<button class="btn btn-outline-secondary" type="button">�˻�</button>					
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
			���� ����
			<select name="sortCondition"  id = "sortCondition" class="ct_input_g" style="width:80px" onchange = "submitDetailForm()">
				<option value="prodName"  ${not empty search.sortCondition && search.sortCondition == "prodName" ? "selected" : '' }>��ǰ �̸�</option>
				<option value="price"  ${not empty search.sortCondition && search.sortCondition == "price" ? "selected" : '' }>��ǰ ����</option>
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
				<option value="prodName"  ${not empty search.searchCondition && search.searchCondition == "prodName" ? "selected" : '' }>��ǰ �̸�</option>
				<option value="price"  ${not empty search.searchCondition && search.searchCondition == "price" ? "selected" : '' }>��ǰ ����</option>
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
						�˻�
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
		<td colspan="11" >��ü ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="50">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="100">����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b" width>��ǰ ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="100">���� ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width ="150">�Ǹ� ����</td>		
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
			�Ǹ���
		</c:if>
		<c:if test = "${product.count <= 0}">
			����
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
<!--  ������ Navigator �� -->
</form>
</div> --%>
</body>
</html>
