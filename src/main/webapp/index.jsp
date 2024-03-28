<%@ page contentType="text/html; charset=euc-kr"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script defer type="text/javascript" src="/javascript/common.js"></script>
<jsp:include page="toolbar.jsp" flush="true"/>
<title>index</title>
</head>

<body>
	
	<div class="container">
		<!-- Carousel -->
		<div id="carouselExampleIndicators" class="carousel slide"
			data-bs-ride="carousel">
			<div class="carousel-indicators">
				<button type="button" data-bs-target="#carouselExampleIndicators"
					data-bs-slide-to="0" class="active" aria-current="true"
					aria-label="Slide 1"></button>
				<button type="button" data-bs-target="#carouselExampleIndicators"
					data-bs-slide-to="1" aria-label="Slide 2"></button>
				<button type="button" data-bs-target="#carouselExampleIndicators"
					data-bs-slide-to="2" aria-label="Slide 3"></button>
			</div>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="#" class="d-block w-100" alt="First">
				</div>
				<div class="carousel-item">
					<img src="#" class="d-block w-100" alt="Second">
				</div>
				<div class="carousel-item">
					<img src="#" class="d-block w-100" alt="Third">
				</div>
			</div>
			<button class="carousel-control-prev" type="button"
				data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button"
				data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Next</span>
			</button>
		</div>
	</div>

	<br />
	<br />
	<br />
	<br />

	<div class="container">
		<div class="row">
			<div class="col-5">여기에 설명을 적습니다~</div>
			<div class="col-7">
				<img src="#" class="d-block w-100" alt="임시 설명 이미지">
			</div>
		</div>
	</div>

	<br />
	<br />
	<br />
	<br />
	
	  <div class="container">
        <h1>상품 목록<h1></h1>
        <div class="row">
            <div class="col-sm-3">
                <div class="card text-center" style="width: 20rem; height:30rem;">
                    <img src="#" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">대상혁</h5>
                        <p class="card-text">숭배하라</p>
                        <a href="#" class="btn btn-primary">상세 정보</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card text-center" style="width: 20rem; height:30rem;">
                    <img src="#" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">대상혁</h5>
                        <p class="card-text">숭배하라</p>
                        <a href="#" class="btn btn-primary">상세 정보</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card text-center" style="width: 20rem; height:30rem;">
                    <img src="#" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">대상혁</h5>
                        <p class="card-text">숭배하라</p>
                        <a href="#" class="btn btn-primary">상세 정보</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card text-center" style="width: 20rem; height:30rem;">
                    <img src="#" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">대상혁</h5>
                        <p class="card-text">숭배하라</p>
                        <a href="#" class="btn btn-primary">상세 정보</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>