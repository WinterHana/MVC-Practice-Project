function fncUpdateUser() {

	let name= $("input[name = 'userName']").val();
	
	if(name == null || name.length <1){
		alert("이름은 반드시 입력하셔야 합니다.");
		return;
	}
	
	let value = "";
	if($("input[name='phone2']").val() != "" && $("input[name='phone3']").val() != "") {
		value = $("option:selected").val() 
					+ "-" + $("input[name='phone2']").val() 
					+ "-" + $("input[name='phone3']").val();
	}

	$("input:hidden[name='phone']").val(value);
	
	$("form").attr("method", "POST").attr("action", "/user/updateUser").submit();
}

$("button[name='update']").on("click", function() {
	fncUpdateUser();
});

$("input[name = 'email']").on("change", function() {
	let email = $("input[name = 'email']").val();
	
	if(email != "" && (email.indexOf('@') < 1 || email.indexOf('.') == -1)) {
		alert("이메일 형식이 아닙니다.")
	}
});

$("button[name='back']").on("click", function() {
	history.go(-1);
});

console.log("updateUser.js");