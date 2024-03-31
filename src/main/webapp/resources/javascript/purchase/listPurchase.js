function fncGetPurchaseList(currentPage) {
	let url = '/purchase/listPurchase/' + currentPage;
	$("form").attr("method", "POST").attr("action", url).submit();
}

function updateTranCode(tranNo, updateTranCode) {
	let url = "/purchase/updateUserTranCode?tranNo=" + tranNo + "&updateTranCode=" + updateTranCode;
	window.location.href = url; 
}


$("span.getPurchase").on("click", function() {
	let url = "/purchase/getPurchase/"+ $(this).data("no");
	$(window.location).attr("href" ,url);
})

$("span.updateTranCode").on("click", function() {
	updateTranCode($(this).data("no"), "003");
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
