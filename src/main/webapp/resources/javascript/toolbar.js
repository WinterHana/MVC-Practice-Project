// toolbar의 form은 전부 name로 처리한다.
$("li.dropdown-item:contains('내 정보 보기')").on("click", function() {
	$("form[name='getUser']").submit();
})

// product autoComplete
$("#searchKeyword").on("keydown", function() {
	let requestURL = "/rest/product/getProdNames";
	
	$.ajax({
		url : requestURL,
		method : "POST",
		dataType : "json",
		contentType : "application/json",
		success : function(JSONData, status) {
			$("#searchKeyword").autocomplete({
				source : JSONData
			});
		}
	}); 
});

console.log("toolbar.js");