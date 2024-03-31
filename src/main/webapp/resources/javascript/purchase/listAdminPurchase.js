function fncGetPurchaseList(currentPage) {
	let url = '/purchase/listAdminPurchase/' + currentPage;
	$("form").attr("method", "POST").attr("action", url).submit();
}

function updateTranCode(tranNo, selectId) {
	let selectedValue = $("#" + selectId).val();
	let url = "/purchase/updateTranCode?tranNo=" + tranNo + "&updateTranCode=" + selectedValue;
 	$(window.location).attr("href" ,url); 
}

$("span.tranCode:contains('변경하기')").on("click", function() {
	let tranNo = $(this).data("a");
	let selectId = $(this).data("b");
	
	console.log("tranNo : " + tranNo);
	console.log("selectId : " + selectId);
	
	updateTranCode(tranNo, selectId);
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