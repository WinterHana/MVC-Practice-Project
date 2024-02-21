<%@ page language="java" contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<c:if test = "${resultPage.currentPage <= resultPage.pageUnit}">
	◀ 이전
</c:if>
<c:if test = "${resultPage.currentPage > resultPage.pageUnit}">
	<a href="javascript:${getList}('${resultPage.currentPage - 1 }')">◀ 이전</a>
</c:if>
<c:forEach var = "i" begin = "${resultPage.beginUnitPage}" end = "${resultPage.endUnitPage}" step = "1" >
		<input type="hidden"  id = "currentPage" name = "currentPage" value ="${i}"/>
		<a href="javascript:${getList}('${i}');">${i}</a>
</c:forEach>
<c:if test = "${resultPage.endUnitPage >= resultPage.maxPage }">
	이후 ▶
</c:if>
<c:if test = "${resultPage.endUnitPage < resultPage.maxPage }">
	<a href="javascript:${getList}('${resultPage.endUnitPage + 1 }')">이후 ▶</a>
</c:if>