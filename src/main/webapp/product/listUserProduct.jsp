<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>${title}</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	window.onload = showContentBySelectBox;

	function fncGetProductList(currentPage) {
		let url = '/product/listUserProduct/' + currentPage;	
			$("form").attr("method", "POST").attr("action", url).submit();
	}

	function submitDetailForm() {
		$("form").attr("method", "POST").submit();
	}

	function showContentBySelectBox() {
		let selectOption = $("#searchCondition").val();

		if(selectOption === "price") {
			$("#prodNameContent").css("display", "none");
			$("#priceContent").css("display", "block");
		} 
	
		else {
			$("#prodNameContent").css("display", "block");
			$("#priceContent").css("display", "none");
		}
	}

	$(function() {
		$("#sortCondition").on("change", function() {
			submitDetailForm();
		})
	
		$("#searchCondition").on("change", function() {
			showContentBySelectBox();
		})
		
		$("span.getProduct").on("click", function() {
			let url = "/product/getProduct/"+ $(this).data("no");
			$(window.location).attr("href" ,url);
		})
		
		$("td.ct_btn01:contains('검색')").on("click", function() {
			fncGetProductList(1);
		})
		
		$("span.pageNavigator").on("click", function() {
			fncGetProductList($(this).data("page"));
		})
	})
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listUserProduct/1" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품 목록 조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


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
				<input type="text" name="searchKeyword"  value="${search.searchKeyword}"
					class=ct_input_g" style="width:150px; height:19px">
			</div>
			<div id = "priceContent">
				<input type="text" name="searchKeywordSub"  value="${search.searchKeywordSub}"
					class=ct_input_g" style="width:100px; height:19px">
				~
				<input type="text" name="searchKeywordThird"  value="${search.searchKeywordThird}"
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
						<!-- <a href="javascript:fncGetProductList(1);">검색</a> -->
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
		<%-- <a href="/product/getProduct/${product.prodNo}"> --%>
		<span class = "getProduct" data-no ="${product.prodNo}">
		<img src = "/images/uploadFiles/${product.fileName[0]}" width = "120" height = "90"/>
		</span>
		</a>
		</td>
		<td></td>
		<td align = "center">
		<span class = "getProduct" data-no ="${product.prodNo}">
		<%-- <a href="/product/getProduct/${product.prodNo}">${product.prodName}</a> --%>
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
</div>
</body>
</html>
