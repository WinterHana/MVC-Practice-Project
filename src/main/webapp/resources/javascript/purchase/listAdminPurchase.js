function fncGetPurchaseList(currentPage) {
	let url = '/purchase/listAdminPurchase/' + currentPage;
	$("form").attr("method", "POST").attr("action", url).submit();
}

function updateTranCode(tranNo, selectId, page) {
	let selectedValue = $("#" + selectId).val();
	let requesURL = "/rest/purchase/updateTranCode/" + page;
	
	let obj = {
		"tranNo" : tranNo,
		"tranCode" : selectedValue,
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

$("span.tranCode:contains('변경하기')").on("click", function() {
	let tranNo = $(this).data("no");
	let selectId = $(this).data("code");
	let page = $(this).data("page");
	
	console.log("tranNo : " + tranNo);
	console.log("selectId : " + selectId);
	console.log("page : " + page);
	
	updateTranCode(tranNo, selectId, page);
	
	// 새로 고침
	location.reload();
})

$("span.getPurchase").on("click", function() {
	let url = "/purchase/getPurchase/"+ $(this).data("no");
	$(window.location).attr("href" ,url);
})

$("span.getUser").on("click", function() {
	$("form[name='getUser']").submit();
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