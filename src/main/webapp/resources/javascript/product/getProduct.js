function purchaseProduct() {
	// ��ȿ�� Ȯ��
	let count = parseInt($("#count").text());

	if(count <= 0) {
		alert("��ǰ�� �����Ǿ����ϴ�!");
		return;
	}
	
	alert("����");
	// $("form[name='updateUser']").submit();
}

// ���� ������Ʈ
function updateTotalPrice() {
	let price = Number($("#price").text());
	let purchaseCount = Number($("input[name='purchaseCount']").val());
	$("#totalPrice").text(price * purchaseCount);
}

$("button[name='down']").on("click", function() {
	previousNum = Number($("input[name='purchaseCount']").val());
	if(previousNum > 0) {
		$("input[name='purchaseCount']").val(previousNum - 1);
	}
	
	updateTotalPrice();
});

$("button[name='up']").on("click", function() {
	previousNum = Number($("input[name='purchaseCount']").val());
	$("input[name='purchaseCount']").val(previousNum + 1);
	
	updateTotalPrice();
});

// Button Control
$("button[name='back']").on("click", function() {
	history.go(-1);
});

$("button[name='delete']").on("click", function() {
	result = window.confirm("������ �����Ͻðڽ��ϱ�?");
	if(result) {
		$("form[name='deleteProduct']").submit();
	}
});

$("button[name='update']").on("click", function() {
	$("form[name='updateProduct']").submit();
});

$("button[name='purchase']").on("click", function() {
	purchaseProduct();
});



