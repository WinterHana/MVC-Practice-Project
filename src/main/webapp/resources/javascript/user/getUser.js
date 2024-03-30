$("button[name='update']").on("click", function() {
	$("form[name='updateUser']").submit();
});

$("button[name='back']").on("click", function() {
	history.go(-1);
});

$("button[name='delete']").on("click", function() {
	result = window.confirm("정말로 탈퇴하시겠습니까?");
	if(result) {
		let url = "/user/deleteUser";
		$("form[name='deleteUser']").attr("method", "POST").attr("action", url).submit();
	}
});

console.log("getUser.js");