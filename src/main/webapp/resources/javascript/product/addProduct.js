function fncAddProduct(){
	//Form 유효성 검증
	let name = $("input[name='prodName']").val();
	let detail = $("input[name='prodDetail']").val();
	let manuDate = $("input[name='manuDate']").val();
	let price = $("input[name='price']").val();
	let count = $("input[name='count']").val();

	if(name == null || name.length<1){
		alert("상품명은 반드시 입력하여야 합니다.");
		return;
	}

	if(detail == null || detail.length<1){
		alert("상품상세정보는 반드시 입력하여야 합니다.");
		return;
	}

	if(manuDate == null || manuDate.length<1){
		alert("제조일자는 반드시 입력하셔야 합니다.");
		return;
	}

	if(price == null || price.length<1){
		alert("가격은 반드시 입력하셔야 합니다.");
		return;
	}

	if(count == null || parseInt(count) < 0){
		alert("개수는 반드시 입력해야 합니다. 또는 양수여야 합니다.");
		return;
	}
	
	result = window.confirm("제품을 추가하시겠습니까?");
	if(result) {
		$("form").submit();
	}
}


$("button[name='add']").on("click", function() {
	fncAddProduct();
});

$("button[name='back']").on("click", function() {
	history.go(-1);
})

console.log("addProduct.js");