<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>${title}</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">${title}</td>
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
				<option value="prodNo"  ${not empty searchVO.searchCondition && searchVO.searchCondition == "prodNo" ? "selected" : '' }>��ǰ��ȣ</option>
				<option value="prodName"  ${not empty searchVO.searchCondition && searchVO.searchCondition == "prodName" ? "selected" : '' }>��ǰ��</option>
				<option value="price"  ${not empty searchVO.searchCondition && searchVO.searchCondition == "price" ? "selected" : '' }>��ǰ����</option>
			</select>
			<input type="text" name="searchKeyword"  value="${searchVO.searchKeyword}"
					class=ct_input_g" style="width:200px; height:19px">
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">�˻�</a>
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
		<td class="ct_list_b" width="50">��ǰ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�ı�</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��� ����</td>		
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
		<c:set var = "flag" value = "false"/>
		<c:forEach var = "entry"  items = "${pmap}">
			<c:if test="${entry.key == product.prodNo}">
				<td align="center">${product.prodNo}</td>
				<td></td>
				<td align="center">
				<img src = "images/uploadFiles/${product.fileName}" width = "120" height = "90"/>
				</td>
				<td></td>
				<td align = "center">${product.prodName}</td>
				<c:set var = "flag" value = "true"/>
				<td></td>
			</c:if>
		</c:forEach>
		<c:if test="${not flag}">
			<td align="center">${product.prodNo}</td>
			<td></td>
			<td align="center">
			<a href="/${pageTarget}.do?prodNo=${product.prodNo}">
			<img src = "images/uploadFiles/${product.fileName}" width = "120" height = "90"/>
			</a>
			</td>
			<td></td>
			<td align = "center">
			<a href="/${pageTarget}.do?prodNo=${product.prodNo}">${product.prodName}</a>
			</td>
			<td></td>
		</c:if>
		<td align="center">${product.price}</td> 
		<td></td>
		<td align="center">${product.regDate}</td>
		<td></td>
		<td align="center">${product.prodDetail}</td>		
		<td></td>
		<td align="center">
		<c:set var = "isContain" value = "false"/>
		<c:forEach var = "entry"  items = "${pmap}">
			<c:if test="${entry.key == product.prodNo}">
				<c:set var = "tranCode"  value = "${pmap[product.prodNo].tranCode}"/>
				<c:set var = "tranNo" value = "${pmap[product.prodNo].tranNo}"/>
				${messageMap[product.prodNo]}
				<c:if test="${isManager && tranCode == 002}">
					<a href="/updateTranCode.do?tranNo= ${tranNo}&tranCode=${tranCode}&url=listProduct.do?menu=manage">��� �����ϱ�</a>
				</c:if>
				<c:set var = "isContain" value = "true"/>
			</c:if>
		</c:forEach>
		<c:if test="${not isContain}">
			�Ǹ���
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
</div>
</body>
</html>
