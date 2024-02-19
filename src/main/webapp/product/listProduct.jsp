<%@page import="com.model2.mvc.service.product.vo.ProductVO"%>
<%@page import="com.model2.mvc.service.user.vo.UserVO"%>
<%@page import="com.model2.mvc.common.SearchVO"%>
<%@ page import="java.util.*"  %>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>



<%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map");
	SearchVO searchVO=(SearchVO)request.getAttribute("searchVO");

	int total = 0;
	ArrayList<ProductVO> list = null;
	
	if(map != null) {
		total = ((Integer)map.get("count")).intValue();
		list = (ArrayList<ProductVO>) map.get("list");
	}
	
	int currentPage = searchVO.getPage();
	
	int totalPage = 0;
	if(total > 0) {
		totalPage = total / searchVO.getPageUnit();
		if(total % searchVO.getPageUnit() > 0) {
			totalPage += 1;
		}
	}
	
	
	String menu = request.getParameter("menu");
	String title = null;
	String pageTarget = null;
	if(menu != null) {
		if(menu.equals("manage")) {
			title = "상품 관리";
			pageTarget = "updateProductView";
		} else if (menu.equals("search")) {
			title = "상품 목록 조회";
			pageTarget = "getProduct";
		}
	}

%>
<html>
<head>
<title><%=title %></title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(){
	document.detailForm.submit();
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=<%=menu %>" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01"><%=title %></td>
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
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="prodNo">상품번호</option>
				<option value="prodName">상품명</option>
				<option value="price">상품가격</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" />
		</td>
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">검색</a>
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
		<td colspan="11" >전체 <%=total %>건수, 현재 <%= currentPage %> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">가격</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">후기</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">상태</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	
		int no = 1;
		for(int i=0; i<list.size(); i++) {
			ProductVO vo = (ProductVO) list.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center"><%=no++%></td>
		<td></td>
		<%-- 나중에 수정 --%>
		<td align="left"><a href="/<%=pageTarget %>.do?prodNo=<%=vo.getProdNo()%>"><%=vo.getProdNo()%></a></td>
		<td></td>
		<td align="left"><%= vo.getProdName() %></td>
		<td></td>
		<td align="left"><%= vo.getPrice() %></td> 
		<td></td>
		<td align="left"><%= vo.getRegDate().toString() %></td>
		<td></td>
		<td align="left"><%= vo.getProdDetail() %></td>		
		<td></td>
		<td align="left">판매 중</td>		
	</tr>
	<%} %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<%
			for(int i  = 1; i <= totalPage; i++) {
		%>
			<a href="/listProduct.do?menu=<%=menu %>&page=<%=i %>"><%=i %></a>
		<%
			}
		%>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>
</body>
</html>
