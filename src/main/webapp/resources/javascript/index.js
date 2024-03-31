function getProductDataIndex() {
	
	let obj = {
		"searchCondition" : null,
   		"searchKeyword" : null,
   		"searchKeywordSub": null,
        "searchKeywordThird": null,
        "sortCondition": null,
	}
	
	$.ajax({
		url : "/rest/product/listProduct/1",
		method : "POST",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(obj),
		success : function(JSONData) {
			let resultList = JSONData.list; 
			
			console.log(resultList);
			
			$(resultList).each(
				function() {
					renderList(this);
				}
			)
		},
		error : function() {
		}, 
		complete : function() {
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

getProductDataIndex();