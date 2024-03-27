function fncLogin() {
		let id = $("input:text").val();
		let pw = $("input:password").val();

		if (id == null || id.length < 1) {
			alert('ID 를 입력하지 않으셨습니다.');
			document.loginForm.userId.focus();
			return;
		}

		if (pw == null || pw.length < 1) {
			alert('패스워드를 입력하지 않으셨습니다.');
			document.loginForm.password.focus();
			return;
		}

		$("form").attr("method", "POST").attr("action", "/user/login").attr(
				"target", "_parent").submit();
	}

$("#userId").focus();

$("img[src='/images/btn_login.gif']").on("click", function() {
	fncLogin();
});

$(".ct_input_g").on("keypress", function(event) {
	if (event.which === 13) {
		event.preventDefault(); // 기본 동작 방지 (폼 제출 등)
		fncLogin();
	}
})

$("img[src='/images/btn_add.gif']").on("click", function() {
	self.location = "addUserView.jsp";
});