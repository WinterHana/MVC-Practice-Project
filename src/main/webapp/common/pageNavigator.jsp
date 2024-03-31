<%@ page language="java" contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<nav>
	<ul class="pagination justify-content-center">
	<li class="page-item">
	
	<c:if test = "${resultPage.currentPage <= resultPage.pageUnit}">
		<span class="page-link">
	        &laquo;
	    </span>
	</c:if>
	<c:if test = "${resultPage.currentPage > resultPage.pageUnit}">
		<span  class="page-link" data-page = "${resultPage.currentPage - 1 }" >
        	&laquo;
     	</span>
	</c:if>
	</li>
	
	<c:forEach var = "i" begin = "${resultPage.beginUnitPage}" end = "${resultPage.endUnitPage}" step = "1" >
			<span class = "pageNavigator" data-page ="${i}" >
			<li class="page-item">
				<p class="page-link" >
					<input type="hidden"  id = "currentPage" name = "currentPage" value ="${i}"/>
					${i}	
				</p>
			</li>
			</span>
	</c:forEach>
	
	<li class="page-item">
	<c:if test = "${resultPage.endUnitPage >= resultPage.maxPage }">
		<span class="page-link">
			&raquo;
		</span>
	</c:if>
	<c:if test = "${resultPage.endUnitPage < resultPage.maxPage }">
		<span class="page-link" data-page ="${resultPage.endUnitPage + 1 }">
			&raquo;
		</span>
	</c:if>
	</li>
	</ul>
</nav>