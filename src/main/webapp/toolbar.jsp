<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!-- jsp에서 동적으로 움직일 필요가 있다. -->

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<link href="/css/font.css" rel="stylesheet" type="text/css">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand" href="/">Aris Shopping</a>
		<button class="navbar-toggler" type="button"
			data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link" aria-current="page" href="/product/listUserProduct.jsp">제품 목록</a>
				</li>
				
				<c:if test = "${empty user}">
				<li class="nav-item">
					<a class="nav-link" href="/user/login.jsp">로그인</a>
				</li>
				</c:if>
				
				<c:if test = "${not empty user}">
				
					<c:if test = "${user.role eq 'user'}">
						<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">${user.userName}</a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
								<li><a class="dropdown-item" href="/user/getUser.jsp">내 정보 보기</a></li>
								<li><a class="dropdown-item" href="/purchase/listUserPurchase.jsp">구매 내역 확인</a></li>
							</ul>
						</li>
					</c:if>
					
					<c:if test = "${user.role eq 'admin'}">
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">${user.userName}</a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
								<li><a class="dropdown-item" href="/user/getUser.jsp">내 정보 보기</a></li>
								<li><a class="dropdown-item" href="/purchase/listUserPurchase.jsp">구매 내역 확인</a></li>
								<li><hr class="dropdown-divider"></li>
								<li><a class="dropdown-item" href="/user/listUser">유저 관리</a></li>
								<li><a class="dropdown-item" href="/product/listAdminProduct">제품 관리</a></li>
								<li><a class="dropdown-item" href="/purchase/listAdminPurchase">구매 관리</a></li>
							</ul>
						</li>
					</c:if>
				
				<li class="nav-item">
					
					<a class="nav-link" href="/user/logout">로그아웃</a>
				</li>
				</c:if>			
			</ul>
			<form class="d-flex">
				<input class="form-control me-2" type="search" placeholder="Search"
						aria-label="Search">
				<button class="btn btn-danger" type="submit">Search</button>
			</form>
		</div>
	</div>
</nav>