function purchaseMenu() {
	// 유효성 확인
	let count = parseInt($("#count").text());

	if(count <= 0) {
		alert("상품이 매진되었습니다!");
		return;
	}
	
	$("div[name='purchaseMenu']").css("display", "block");
}

// 가격 업데이트
function updateTotalPrice() {
	let price = Number($("#price").text());
	let purchaseCount = Number($("input[name='prodCount']").val());
	$("#totalPrice").text(price * purchaseCount);
}

$("button[name='down']").on("click", function() {
	previousNum = Number($("input[name='prodCount']").val());
	if(previousNum > 0) {
		$("input[name='prodCount']").val(previousNum - 1);
	}
	
	updateTotalPrice();
});

$("button[name='up']").on("click", function() {
	previousNum = Number($("input[name='prodCount']").val());
	$("input[name='prodCount']").val(previousNum + 1);
	
	updateTotalPrice();
});

// Button Control
$("button[name='back']").on("click", function() {
	history.go(-1);
});

$("button[name='delete']").on("click", function() {
	result = window.confirm("정말로 삭제하시겠습니까?");
	if(result) {
		$("form[name='deleteProduct']").submit();
	}
});

$("button[name='update']").on("click", function() {
	$("form[name='updateProduct']").submit();
});

$("button[name='purchase']").on("click", function() {
	purchaseMenu();
});

// PurchaseMenu
$("div[name='purchaseMenu']").css("display", "none");

$("button[name='purchaseComplete']").on("click", function() {
	let count = parseInt($("input[name='prodCount']").val());

	if(count <= 0) {
		alert("구매할 상품 개수를 입력해주세요!");
		return;
	}
	
	$("form[ name = 'purchaseForm']").submit();
});