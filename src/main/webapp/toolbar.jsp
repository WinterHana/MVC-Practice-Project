<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!-- jsp에서 동적으로 움직일 필요가 있다. -->

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>

<script defer type="text/javascript" src ="/javascript/toolbar.js"></script>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<link href="/css/font.css" rel="stylesheet" type="text/css">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand" href="/">이름 뭘로 하지</a>
		<button class="navbar-toggler" type="button"
			data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<!-- 보호가 필요한 정보는 post, 전체 공개는 get -->
		
		
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link" aria-current="page" href="/product/listProduct/1">제품 목록</a>
				</li>
				
				<c:if test = "${empty sessionScope.user}">
				<li class="nav-item">
					<a class="nav-link" href="/user/loginView">로그인</a>
				</li>
				</c:if>
				
				<c:if test = "${not empty sessionScope.user}">
					<!-- getUser post -->
					<form name ="getUser"  action = "/user/getUser/${sessionScope.user.userId}" method = "post"></form>
					
					<c:if test = "${sessionScope.user.role eq 'user'}">
						<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">${sessionScope.user.userName}</a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
								<!-- <li><a class="dropdown-item" >내 정보 보기</a></li> -->
								<li class="dropdown-item">내 정보 보기</li>
								<li><a class="dropdown-item" href="/purchase/listPurchase/1">구매 내역 확인</a></li>
							</ul>
						</li>
					</c:if>
					
					<c:if test = "${sessionScope.user.role eq 'admin'}">
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">${sessionScope.user.userName}</a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
								<li class = "dropdown-item">내 정보 보기</a></li>
								<li><a class="dropdown-item" href="/purchase/listPurchase/1">구매 내역 확인</a></li>
								<li><hr class="dropdown-divider"></li>
								<li><a class="dropdown-item" href="/user/listUser/1">유저 관리</a></li>
								<li><a class="dropdown-item" href="/purchase/listAdminPurchase/1">구매 관리</a></li>
							</ul>
						</li>
					</c:if>
				
				<li class="nav-item">
					<a class="nav-link" href="/user/logout">로그아웃</a>
				</li>
			</c:if>			
			</ul>
		</div>
	</div>
</nav>