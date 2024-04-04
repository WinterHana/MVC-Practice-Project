function limitUpdate(tranNo, tranCode) {
	if(tranCode == '002' || tranCode == '003') {
		alert("상품이 배송중이거나 배송 완료일 때 변경할 수 없습니다.");
		return;
	} else {
		let url = "/purchase/updatePurchaseView/" + tranNo;
		$("form[name='updatePurchase']").submit();
	}
}
	
$("button[name='update']").on("click", function() {
    var tranNo = $("input[name = 'tranNo']").val();
    var tranCode = $("input[name = 'tranCode']").val();
	limitUpdate(tranNo, tranCode);
})
	
$("button[name='delete']").on("click", function() {
	result = window.confirm("정말로 삭제하시겠습니까?");
	if(result) {
		let url = "/purchase/deletePurchase";
		$("form[name='deletePurchase']").attr("method", "POST").attr("action", url).submit();
	}
})

$("button[name='back']").on("click", function() {
	history.go(-1);
})
// https://horangi.tistory.com/417

		