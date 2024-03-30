function fncLogin() {
		let id = $("input[name='userId']").val();
		let pw = $("input[name='password']").val();
		
		console.log(id);
		console.log(pw);
		
		if (id == null || id.length < 1) {
			alert('ID 를 입력하지 않으셨습니다.');
			$("input[name='userId']").focus();
			return;
		}

		if (pw == null || pw.length < 1) {
			alert('패스워드를 입력하지 않으셨습니다.');
			 $("input[name='password']").focus();
			return;
		}

		$("form").attr("method", "POST").attr("action", "/user/login").attr("target", "_parent").submit();
	}

$("#loginButton").on("click", function() {
	console.log("click Button");
	fncLogin();
});

$(".form-control").on("keypress", function(event) {
	if (event.which === 13) {
		event.preventDefault(); // 기본 동작 방지 (폼 제출 등)
		fncLogin();
	}
});

// check import
console.log("login.js");