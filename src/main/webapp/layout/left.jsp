<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Model2 MVC Shop</title>
<!-- 새로고침 : 10초마다 -->
<META HTTP-EQUIV="refresh" CONTENT="5">

<link href="/css/left.css" rel="stylesheet" type="text/css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
/* function history(){
	popWin = window.open("/history.jsp","popWin","left=300, top=200, width=300, height=200, marginwidth=0, marginheight=0, scrollbars=no, scrolling=no, menubar=no, resizable=no");
} */
	$(function() {
		$("span.menu:contains('개인정보 조회')").on("click", function() {
			url = "/user/getUser/" + $(this).data("userId");
			$(window.parent.frames["rightFrame"].document.location).attr("href", url);
		})
		
		$("td.Depth03:contains('회원목록 조회')").on("click", function() {
			url = "/user/listUser/1";
			$(window.parent.frames["rightFrame"].document.location).attr("href", url);
		})
		
		$("td.Depth03:contains('상품 등록')").on("click", function() {
			url = "../product/addProductView.jsp";
			$(window.parent.frames["rightFrame"].document.location).attr("href", url);
		})
		
		$("td.Depth03:contains('상품 관리')").on("click", function() {
			url = "/product/listAdminProduct/1";
			$(window.parent.frames["rightFrame"].document.location).attr("href", url);
		})
		
		$("td.Depth03:contains('판매 관리')").on("click", function() {
			url = "/purchase/listPurchase/1";
			$(window.parent.frames["rightFrame"].document.location).attr("href", url);
		})
		
		$("td.Depth03:contains('상품 검색')").on("click", function() {
			url = "/product/listUserProduct/1";
			$(window.parent.frames["rightFrame"].document.location).attr("href", url);
		})
		
		$("td.Depth03:contains('구매 이력 조회')").on("click", function() {
			url = "/purchase/listPurchase/1";
			$(window.parent.frames["rightFrame"].document.location).attr("href", url);
		})
	})

</script>
</head>

<body background="/images/left/imgLeftBg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

<table width="159" border="0" cellspacing="0" cellpadding="0">
<!-- jQuery 사용 -->
<!--menu 01 line : User -->
<tr>
	<td valign="top"> 
		<table  border="0" cellspacing="0" cellpadding="0" width="159" >	
			<c:if test = "${not empty user}">
				<tr>
					<td class="Depth03"> 
						<span class = "menu" data-userId = "${user.userId}">개인정보 조회</span>
					</td>
				</tr>
			</c:if>
			<!-- admin -->
			<c:if test = "${user.role eq 'admin'}">
				<tr>
					<td class="Depth03" >
						<!-- <a href="/user/listUser/1" target="rightFrame">회원목록 조회</a> -->
						회원목록 조회
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="DepthEnd">&nbsp;</td>
			</tr>
		</table>
	</td>
</tr>

<!--menu 02 line : Product-->


<tr>
	<td valign="top"> 
		<table  border="0" cellspacing="0" cellpadding="0" width="159">
			<!-- admin -->
			<c:if test = "${user.role eq 'admin'}">
				<tr>
					<td class="Depth03">
						<!-- a href="../product/addProductView.jsp;" target="rightFrame">상품 등록</a> -->
						상품 등록
					</td>
				</tr>
				<tr>
					<td class="Depth03">
						<!-- <a href="/product/listAdminProduct/1" target="rightFrame">상품 관리</a> -->
						상품 관리
					</td>
				</tr>
				<tr>
					<td class="Depth03">
						<!-- <a href="/purchase/listPurchase/1" target="rightFrame">판매 관리</a> -->
						판매 관리
					</td>
				</tr>
				<tr>
					<td class="DepthEnd">&nbsp;</td>
				</tr>
			</c:if>
			<c:if test = "${user.role ne 'admin'}">
				<tr>
					<td class="Depth03">
						<!-- <a href="/product/listUserProduct/1" target="rightFrame">상품 검색</a> -->
						상품 검색
					</td>
				</tr>
			</c:if>
		</table>
	</td>
</tr>

<!--menu 03 line : Purchase -->
<tr>
<td valign="top">
	<table  border="0" cellspacing="0" cellpadding="0" width="159">
		<!-- user -->
		<c:if test = "${user.role eq 'user'}">
			<tr>
				<td class="Depth03">
					<!-- <a href="/purchase/listPurchase/1" target="rightFrame">구매 이력 조회</a> -->
					구매 이력 조회
				</td>
			</tr>
			<tr>
				<td class="DepthEnd">&nbsp;</td>
			</tr>
		</c:if>
	</table>
</td>
</tr>
<tr>
<td valign="top">
	<table  border="0" cellspacing="0" cellpadding="0" width="159">
			<tr>
				<td class="Depth03">
					<p>최근 본 상품 목록(5개)</p>
				</td>
			</tr>	
				<%
				String historyInfo = null;
				Cookie[] cookies = request.getCookies();
	
				if (cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("historyInfo")) {
					historyInfo = cookie.getValue();
					}
				}
		
			if (historyInfo != null) {
				String[] h = historyInfo.split("/");
				for (int i = 0; i < h.length; i++) {
					if (!h[i].equals("null")) {
				%>
				<tr>
					<td>
						<h4>&nbsp;&nbsp;<a href="/product/getProduct/<%=h[i]%>" target="rightFrame"><%=h[i]%></a></h4>
					</td>
				</tr>
				<%
				}
			}
		}
	}
%>
			</tr>
			<tr>
				<td class="DepthEnd">&nbsp;</td>
			</tr>
	</table>
</td>
</tr>
</table>
</body>
</html>