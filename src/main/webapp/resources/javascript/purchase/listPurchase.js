function fncGetPurchaseList(currentPage) {
	let url = '/purchase/listPurchase/' + currentPage;
	$("form").attr("method", "POST").attr("action", url).submit();
}

function updateTranCode(tranNo, updateTranCode, page) {
/*	let url = "/purchase/updateUserTranCode?tranNo=" + tranNo + "&updateTranCode=" + updateTranCode;
	window.location.href = url; */
	
	let requesURL = "/rest/purchase/updateTranCode/" + page;
	
	let obj = {
		"tranNo" : tranNo,
		"tranCode" : updateTranCode,
	}
	
	$.ajax({
		url : requesURL,
		method : "POST",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(obj),
		success : function(JSONData) {
			
		},
	});
}


$("span.getPurchase").on("click", function() {
	let url = "/purchase/getPurchase/"+ $(this).data("no");
	$(window.location).attr("href" ,url);
})

$("span.updateTranCode").on("click", function() {
	let tranNo = $(this).data("no");
	let page = $(this).data("page");
	
	console.log("tranNo : " + tranNo);
	console.log("page : " + page);
	
	
	updateTranCode($(this).data("no"), "003", $(this).data("page"));
	
	location.reload();
})

$("span.page-link").on("click", function() {
	if($(this).data("page") === undefined) {
		alert("페이지의 끝입니다!");
		return;
	}
	fncGetPurchaseList($(this).data("page"));
})

$("span.pageNavigator").on("click", function() {
	console.log($(this).data("page"));
	fncGetPurchaseList($(this).data("page"));
})
