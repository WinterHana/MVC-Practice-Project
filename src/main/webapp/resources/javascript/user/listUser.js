// autoComplete
$("input[name='searchKeyword']").on("keydown", function() {
	let requestURL = ""
	
	if($("select[name='searchCondition']").val() === "userId") {
		requestURL = "/rest/user/getUserIds";
	} else {
		requestURL = "/rest/user/getUserNames";
	}
	
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

// 무한 스크롤
let lastScroll = 0;			// 스크롤을 아래로 내리는지 위로 올리는지 확인
let page = 1;				// 처음 page 1을 불러오고 다음 페이지 불러오기
let isGetList = true;
let isGetDataFinish = true;	// 데이터를 들고 올 여부 확인

function getUserData(searchCondition, searchKeyword) {
	
	console.log("searchCondition : " + searchCondition);
	console.log("searchKeyword : " + searchKeyword);
	
	// 만약 값 들 중 하나가 null 이면 (null, null)로 검색한다.
	if(searchCondition == null || searchKeyword == null) {
		searchCondition = null;
		searchKeyword = null;
	}
	
	let obj = {
		"searchCondition" : searchCondition,
   		"searchKeyword" : searchKeyword
	}
	
	$.ajax({
		url : "/rest/user/listUser/" + page,
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
	let html = '<div class="row">'
				+ '<div class = "col-md-3"></div>'
				+ '<div class="col-md-2 bg-primary p-2 text-dark bg-opacity-25 border border-dark">'
				+ '<div class="card" style="width: 12rem; height: 12rem;">'
				+ '<img src="#" class="card-img-top" alt="" width="200" height="150" />'
				+ '</div></div>'
				+ '<div class="col-md-4 bg-primary p-2 text-dark bg-opacity-25 border border-dark">'
				+ '<div class="card-body">'
				+ '<br />'
				+ '<h5 class="card-title">아이디 : ' + result.userId + '</h5>'
				+ '<h5 class="card-text">이름 : ' + result.userName + '</h5>'
				+ '<br /><br />'
				+ '<form action = "/user/getUser/' +  result.userId +'" method = "post">'
				+ '<button class="btn btn-danger">상세 보기</button>'
				+ '</form>'
				+ '</div></div></div><br/>'
				
	// Debug
	// console.log(html);
	
	$("div.userList").append(html);
	
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
/*		console.log("현재 스크롤 위치 : " + currentScroll);
		console.log("브라우저 창의 높이 : " + height);
		console.log("전체 문서의 높이 : " +  documentHeight);*/
		if(currentScroll + height + 1 >= documentHeight 
		&& isGetList === true
		&& isGetDataFinish === true) {
			console.log("끝!");
			isGetDataFinish = false;			// 데이터 가져오기 잠시 멈춤
			getUserData($("select[name='searchCondition']").val(), $("input[name='searchKeyword']").val());
		}
	}
	
	lastScroll = currentScroll;
});


// 첫 시작 : page 1
getUserData(null ,null);

// Searching
function searchList() {
	$("div.userList").html("");
	page = 1;
	isGetList = true;
	getUserData($("select[name='searchCondition']").val(), $("input[name='searchKeyword']").val());
}

$("button.btn:contains('검색')").on("click", function() {
	searchList()
})

// Enter로 입력
$("input[name='searchKeyword']").on("keypress", function(event) {
	if (event.which === 13) {
		event.preventDefault(); // 기본 동작 방지 (폼 제출 등)
		searchList();
	}
});

// Debug
console.log("listUser.js");


