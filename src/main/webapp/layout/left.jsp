<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Model2 MVC Shop</title>

<link href="/css/left.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function history(){
	popWin = window.open("/history.jsp","popWin","left=300, top=200, width=300, height=200, marginwidth=0, marginheight=0, scrollbars=no, scrolling=no, menubar=no, resizable=no");
}
</script>
</head>

<body background="/images/left/imgLeftBg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

<table width="159" border="0" cellspacing="0" cellpadding="0">

<!--menu 01 line : User -->
<tr>
	<td valign="top"> 
		<table  border="0" cellspacing="0" cellpadding="0" width="159" >	
			<c:if test = "${not empty user}">
				<tr>
					<td class="Depth03"> 
						<a href="/user/getUser/${user.userId}"  target="rightFrame">�������� ��ȸ</a>
					</td>
				</tr>
			</c:if>
			<!-- admin -->
			<c:if test = "${user.role eq 'admin'}">
				<tr>
					<td class="Depth03" >
						<a href="/user/listUser/1" target="rightFrame">ȸ����� ��ȸ</a>
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
						<a href="../product/addProductView.jsp;" target="rightFrame">��ǰ ���</a>
					</td>
				</tr>
				<tr>
					<td class="Depth03">
						<a href="/product/listAdminProduct/1" target="rightFrame">��ǰ ����</a>
					</td>
				</tr>
				<tr>
					<td class="Depth03">
						<a href="/purchase/listPurchase/1" target="rightFrame">�Ǹ� ����</a>
					</td>
				</tr>
				<tr>
					<td class="DepthEnd">&nbsp;</td>
				</tr>
			</c:if>
			<c:if test = "${user.role ne 'admin'}">
				<tr>
					<td class="Depth03">
						<a href="/product/listUserProduct/1" target="rightFrame">��ǰ �˻�</a>
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
					<a href="/purchase/listPurchase/1" target="rightFrame">���� �̷� ��ȸ</a>
				</td>
			</tr>
			<tr>
				<td class="DepthEnd">&nbsp;</td>
			</tr>
		</c:if>
		<tr>
			<td class="Depth03">
				<a href="javascript:history()">�ֱ� �� ��ǰ</a>
			</td>
		</tr>
	</table>
</td>
</tr>
<%Cookies cookies = new Cookies(request); %>
<tr>
<td valign="top">
	<table  border="0" cellspacing="0" cellpadding="0" width="159">
			<tr>
				<td class="Depth03">
					<p>�ֱ� �� ��ǰ ���</p>
					<p>�ִ� 5������ ����˴ϴ�.</p>
				</td>
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