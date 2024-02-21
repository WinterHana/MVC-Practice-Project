<%@ page language="java" contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<c:if test = "${resultPage.currentPage <= resultPage.pageUnit}">
	�� ����
</c:if>
<c:if test = "${resultPage.currentPage > resultPage.pageUnit}">
	<a href="javascript:fncGetUserList('${resultPage.currentPage - 1 }')">�� ����</a>
</c:if>
<c:forEach var = "i" begin = "${resultPage.beginUnitPage}" end = "${resultPage.endUnitPage}" step = "1" >
		<input type="hidden"  id = "currentPage" name = "currentPage" value ="${i}"/>
		<a href="javascript:fncGetUserList('${i}');">${i}</a>
</c:forEach>
<c:if test = "${resultPage.endUnitPage >= resultPage.maxPage }">
	���� ��
</c:if>
<c:if test = "${resultPage.endUnitPage < resultPage.maxPage }">
	<a href="javascript:fncGetUserList('${resultPage.endUnitPage + 1 }')">���� ��</a>
</c:if>