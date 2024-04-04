<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html lang="en">

<head>
<script defer type="text/javascript" src ="/javascript/common.js"></script>
<script defer type="text/javascript" src ="/javascript/user/listUser.js"></script>
<jsp:include page="../toolbar.jsp" flush="true"/>
<title>User List</title>
</head>                           

<body bgcolor="#ffffff" text="#000000">

    <div class="container">
    	<br/>
    	<h1>유저 목록</h1>
    	<div class = "row">
    		<div class = "col-sm-3"></div>
    		<div class = "col-sm-3">
		        <div class="input-group mb-3">
		           	<span class="input-group-text">검색</span>
		            <select class="form-select"  name="searchCondition">
		                <option value = "userId" ${not empty search.searchCondition && search.searchCondition == 'userId' ? "selected" : '' }>회원 ID</option>
		                <option value = "userName" ${not empty search.searchCondition && search.searchCondition == 'userName' ? "selected" : '' }>회원명</option>
		            </select>
		       </div>
	       </div>
	       <div class = "col-sm-3">
		       <div class="input-group mb-3">   
		            <input type="text" class="form-control"  name="searchKeyword"  value="${not empty search.searchKeyword ? search.searchKeyword : ''}">
		       	 	<button class="btn btn-outline-secondary" type="button">검색</button>
		       </div>
		   </div>
       </div>
       		<!-- List 출력 -->
      		<div class = "userList"></div>
       </div>
       
</body>
</html>