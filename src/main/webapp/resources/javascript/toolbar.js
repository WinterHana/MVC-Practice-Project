// toolbar�� form�� ���� name�� ó���Ѵ�.
$("li.dropdown-item:contains('�� ���� ����')").on("click", function() {
	$("form[name='getUser']").submit();
})

console.log("toolbar.js");