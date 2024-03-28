function fncAddUser() {
	// Form 유효성 검증
	let id = $("input[name='userId']").val();
	let pw = $("input[name='password']").val();
	let pw_confirm = $("input[name = 'password2']").val();
	let name = $("input[name = 'userName']").val();
	
	// Validation Check
	if(id == null || id.length <1) {
		alert("아이디는 반드시 입력하셔야 합니다.");
		return;
	}
	
	if(pw == null || pw.length <1){
		alert("패스워드는  반드시 입력하셔야 합니다.");
		return;
	}
	
	if(pw_confirm == null || pw_confirm.length <1){
		alert("패스워드 확인은  반드시 입력하셔야 합니다.");
		return;
	}
	
	if(name == null || name.length <1){
		alert("이름은  반드시 입력하셔야 합니다.");
		return;
	}
	
	if(pw != pw_confirm) {
		alert("비밀번호가 일치하지 않습니다.");
		$("input:text[name='password2']").focus();
		return;
	}
	
	// 전화번호 입력 여부에 대하여
	let value = "";
	if($("input:text[name='phone2']").val() != "" 
			&& $("input:text[name='phone3']").val() != "") {
		value = $("option:selected").val() 
		+ "-" + $("input[name='phone2']").val()
		+ "-" + $("input[name='phone3']").val();
	}
	
	$("input:hidden[name='phone']").val(value);
	
	$("form").attr("method", "POST").attr("action", "/user/addUser").submit();
};

function fncCheckDuplication() {
	if($("#userId").val() != null && $("#userId").val().length > 0) {
		$("form").attr("method", "POST");
		$("form").submit();
	} else {
		alert("아이디는 반드시 입력하셔야 합니다.");
	}
	$("#userId").focus();
};

function PortalJuminCheck(fieldValue){
    var pattern = /^([0-9]{6})-?([0-9]{7})$/; 
	var num = fieldValue;
    if (!pattern.test(num)) return false; 
    num = RegExp.$1 + RegExp.$2;

	var sum = 0;
	var last = num.charCodeAt(12) - 0x30;
	var bases = "234567892345";
	for (var i=0; i<12; i++) {
		if (isNaN(num.substring(i,i+1))) return false;
		sum += (num.charCodeAt(i) - 0x30) * (bases.charCodeAt(i) - 0x30);
	}
	var mod = sum % 11;
	return ((11 - mod) % 10 == last) ? true : false;
}

$("button.btn:contains('가입하기')").on("click", function() {
	fncAddUser();
});

$("input[name='email']").on("change", function() {
	let email = $("input[name='email']").val();
	
	if(email != "" && (email.indexOf("@") < 1 || email.indexOf(".") == -1)) {
		alert("이메일 형식이 아닙니다.");
		$("input[name='email']").val("");
	}
});

$("button.btn:contains('중복 확인')").on("click", function() {
	if($("#userId").val() == null || $("#userId").val().length <= 0) {
		alert("아이디는 반드시 입력하셔야 합니다.");
		return;
	}
	
	let requestURL = "/rest/user/checkDuplication";
	let obj =  {
		"userId" : 	$("#userId").val()
	}
	
	$.ajax({
		url : requestURL,
		method : "POST",
		contentType : "application/json",
		data : JSON.stringify(obj),
		success : function(JSONData, status) {
			let result = JSONData.result;
			if(result == true) {
				$("#duplicationResult").html("사용 가능합니다.");
			} else {
				$("#duplicationResult").html("사용 불가능합니다.");
			}
		}
	}); 
});

$("input[name='ssn']").on("change", function() {
	var ssn1, ssn2; 
	var nByear, nTyear; 
	var today; 

	// ssn = document.detailForm.ssn.value;
	ssn = $("input[name='ssn']").val();
	
	// 유효한 주민번호 형식인 경우만 나이 계산 진행
	// PortalJuminCheck 함수는 CommonScript.js 의 공통 주민번호 체크 함수임 
	if(!PortalJuminCheck(ssn)) {
		alert("잘못된 주민번호입니다.");
		return false;
	}
});



/* function fncCheckDuplication() {
	popWin = window.open("/user/checkDuplication.jsp","popWin", "left=300,top=200,width=300,height=200,marginwidth=0,marginheight=0,scrollbars=no,scrolling=no,menubar=no,resizable=no");
} */

console.log("signup.js")