$("button[name='update']").on("click", function() {
	result = window.confirm("수정하시겠습니까?");
	if(result) {
		$("form[name='updatePurchase']").submit();
	}
})

$("button[name='back']").on("click", function() {
	history.go(-1);
})