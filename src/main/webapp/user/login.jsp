<%@ page contentType="text/html; charset=euc-kr"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script defer type="text/javascript" src = "/javascript/user/login.js"></script>
<script defer type="text/javascript" src ="/javascript/common.js"></script>
<jsp:include page="../toolbar.jsp" flush="true"/>
<title>login</title>
</head>

<body bgcolor="#ffffff" text="#000000">

<!-- <form name="loginForm"  method="post" action="/user/login" target="_parent">-->

<body>
	<br/>
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<img src="#" class="img-rounded" width="100%" />
			</div>
			<div class="col-md-6">
				<br />
				<div class="jumbotron">
					<div class="row">
						<h1 class="text-center">로그인</h1>
						<div class="col-sm-2"></div>
						<div class="col-sm-8">
							<form id = "login" class="form-horizontal">
								<div class="form-group">
									<label for="userId" class="col-sm-4 control-label">아 이 디</label>
									<input type="text" class="form-control" name="userId" id="userId" placeholder="아이디">
								</div>
								<br />
								<div class="form-group">
									<label for="password" class="col-sm-4 control-label">패 스 워 드</label> 
									<input type="password" class="form-control" name="password" id="password" placeholder="패스워드">
								</div>
								<br />
								<div class="form-group">
									<div class="d-flex justify-content-between">
										<button type="button" class="btn btn-primary" id = "loginButton">로 그 인</button>
										<a class="btn btn-primary btn" href="/user/signup.jsp" role="button">회 원 가 입</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

