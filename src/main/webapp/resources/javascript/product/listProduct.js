// autoComplete
$("input[name='searchKeyword']").on("keydown", function() {
	let requestURL = "/rest/product/getProdNames";
	
	$.ajax({
		url : requestURL,
		method : "POST",
		dataType : "json",
		contentType : "application/json",
		success : function(JSONData, status) {
			$("input[name='searchKeyword']").autocomplete({
				source : JSONData
			});
		}
	}); 
});

// 검색 조건 변화
window.onload = showContentBySelectBox;

function showContentBySelectBox() {
	let selectOption = $("select[name='searchCondition']").val();

	if(selectOption === "price") {
		$("div[name='prodNameContent']").css("display", "none");
		$("div[name='priceContent']").css("display", "block");
	} 

	else {
		$("div[name='prodNameContent']").css("display", "block");
		$("div[name='priceContent']").css("display", "none");
	}
}
$("select[name='searchCondition']").on("change", function() {
	showContentBySelectBox();
})

function fncGetProductList(currentPage) {
	let url = '/product/listUserProduct/' + currentPage;	
		$("form").attr("method", "POST").attr("action", url).submit();
}

function submitDetailForm() {
	$("form").attr("method", "POST").submit();
}

// 무한 스크롤
let lastScroll = 0;			// 스크롤을 아래로 내리는지 위로 올리는지 확인
let page = 1;					// 처음 page 1을 불러오고 다음 페이지 불러오기
let isGetList = true;
let isGetDataFinish = true;	// 데이터를 들고 올 여부 확인

function getProductData(
	searchCondition, searchKeyword, 
	searchKeywordSub, searchKeywordThird, sortCondition) {
	
	console.log("searchCondition : " + searchCondition);
	console.log("searchKeyword : " + searchKeyword);
	console.log("searchKeywordSub : " + searchKeywordSub);
	console.log("searchKeywordThird : " + searchKeywordThird);
	console.log("sortCondition : " + sortCondition);
	
	let obj = {
		"searchCondition" : searchCondition,
   		"searchKeyword" : searchKeyword,
   		"searchKeywordSub": searchKeywordSub,
        "searchKeywordThird": searchKeywordThird,
        "sortCondition": sortCondition,
	}
	
	$.ajax({
		url : "/rest/product/listProduct/" + page,
		method : "POST",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(obj),
		success : function(JSONData) {
			let resultList = JSONData.list; 
			
			console.log(resultList);
			
			// 더 이상 가져올 데이터 없을 때 실행 종료
			if(resultList == null || resultList.length <= 0) {
				isGetList = false;
				return;
			}
			
			$(resultList).each(
				function() {
					renderList(this);
				}
			)
			// 다시 데이터 들고 오기
			isGetDataFinish = true;
		},
		error : function() {
			page = page;
			
		}, 
		complete : function() {
			page = page + 1;
		}
	})
}

// 각 데이터마다 데이터 랜더링
function renderList(result) {
	let html = 	'<div class="col-sm-3">'
				+ '<div class="card text-center" style="width: 20rem;">'
				+ '<img src="/img/uploadFiles/' + (result.fileName != null ? result.fileName[0] : '') +'" class="card-img-top">'
				+ '<div class="card-body">'
				+ '<h5 class="card-title">' + result.prodName +'</h5>'
				+ '<p class="card-text">남은 수량 : ' + result.count + '</p>'
				+ '<p class="card-text">가격 : ' + result.price + '</p>'
				+ '<form action = "/product/getProduct/' +  result.prodNo +'" method = "post">'
				+ '<button class="btn btn-danger">상세 보기</button>'
				+ '</form>'
				+ '</div></div></div>'
				
	// Debug
	// console.log(html);
	
	$("div[name='productList']").append(html);
	
	imageDefault();
}

// 스크롤 감지
$(document).scroll(function(){
	// 현재 스크롤 위치
	let currentScroll = $(window).scrollTop();
	
	// 브라우저 창의 높이
	let height = $(window).height();
	
	// 전체 문서의 높이
	let documentHeight = $(document).height();
	
	// 스크롤이 아래로 내려갔을 때
	if(currentScroll > lastScroll) {
		console.log("현재 스크롤 위치 : " + currentScroll);
		console.log("브라우저 창의 높이 : " + height);
		console.log("전체 문서의 높이 : " +  documentHeight);
		if(currentScroll + height + 1 >= documentHeight 
			&& isGetList === true 
			&& isGetDataFinish === true) {
			console.log("끝!");
			isGetDataFinish = false;			// 데이터 가져오기 잠시 멈춤
			getProductData(
			$("select[name='searchCondition']").val(), 
			$("input[name='searchKeyword']").val(),
			$("input[name='searchKeywordSub']").val(),
			$("input[name='searchKeywordThird']").val(),
			$("select[name='sortCondition']").val());
		}
	}
	
	lastScroll = currentScroll;
});


// 첫 시작 : page 1
getProductData("prodName", "", "", "", "prodName");

// Searching
function searchList() {
	$("div[name='productList']").html("");
	page = 1;
	isGetList = true;
	getProductData(
		$("select[name='searchCondition']").val(), 
		$("input[name='searchKeyword']").val(),
		$("input[name='searchKeywordSub']").val(),
		$("input[name='searchKeywordThird']").val(),
		$("select[name='sortCondition']").val());
}

$("button.btn:contains('검색')").on("click", function() {
	searchList()
})

// Enter에 대한 반응
$("input").on("keypress", function(event) {
	if(event.which === 13) {
		event.preventDefault(); // 기본 동작 방지 (폼 제출 등)
		searchList()
	}
})

console.log("listProduct.js");
