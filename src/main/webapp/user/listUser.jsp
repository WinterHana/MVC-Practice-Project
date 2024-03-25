<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<html>
<head>
<title>ȸ����� ��ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<style>
	.disabled {
		pointer-events: none;
	}
</style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script type="text/javascript">
function fncGetUserList(currentPage) {
	let url = '/user/listUser/' + currentPage;	
   	$("form[name='listUserForm']").attr("method", "POST").attr("action", url).submit();
}

$(function() {
	// ó�� ���� ��ư�� ��Ȱ��ȭ
	$("#userButton").addClass("disabled");
	
	$("td.ct_btn01:contains('�˻�')").on("click", function() {
		fncGetUserList($("input[name='currentPage']").val())
	})
	
 	$("input[name='searchKeyword']").on("keydown", function() {
 		let requestURL = ""
 		if($("select[name='searchCondition']").val() === "userId") {
 			requestURL = "/rest/user/getUserIds";
 		} else {
 			requestURL = "/rest/user/getUserNames";
 		}
 		
 		$.ajax({
 			url : requestURL,
			method : "POST",
			dataType : "json",
			header : {
				"Accept" : "application/json",
				"Content-Type" : "application/json"
			},
			success : function(JSONData, status) {
				$("#searchKeyword").autocomplete({
					source : JSONData
				});
			}
 		}); 
	});

	$(".ct_list_pop td:nth-child(3)").on("click", function() {
		var userId = $(this).text().trim();
		
		$.ajax({
			url : "/rest/user/getUser/"+userId,
			method : "POST",
			dataType : "json",
			header : {
				"Accept" : "application/json",
				"Content-Type" : "application/json"
			},
			success : function(JSONData, status) {
				var display = 
					"<h3>"
					+ "���̵� : " + JSONData.userId + "<br/>"
					+ "�̸� : " +  JSONData.userName + "<br/>"
					+ "��й�ȣ : " +  JSONData.password + "<br/>"
					+ "�޴��� ��ȣ : " +  JSONData.phone + "<br/>"
					+ "�ּ� : " +  JSONData.addr + "<br/>"
					+ "�̸��� : " +  JSONData.email + "<br/>"
					+ "<h3>";
					
				$("h3.infoFirst").remove();
				$("#userInformation").html(display);
			}
		});
		
		$("#userId").val(userId);
		$("#userButton").removeClass('disabled');
	});
	
	$(".ct_list_pop td:nth-child(3)").css("color", "blue");
	
	$("span.pageNavigator").on("click", function() {
		fncGetUserList($(this).data("page"));
	});
	
	$("td.ct_btn01:contains('����')").on("click", function() {
		result = window.confirm("������ Ż���Ͻðڽ��ϱ�?");
		if(result) {
			let url = "/user/deleteUser";
			$("form[name='updateOrDeleteForm']").attr("method", "POST").attr("action", url).submit();
		}
	});
	
	$("td.ct_btn01:contains('����')").on("click", function() {
		location.href = "/user/updateUserView/" + $("#userId").val();
	});
})
</script>
</head>                           

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="listUserForm" action="/user/listUser/1" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">ȸ����� ��ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value = "userId" ${not empty search.searchCondition && search.searchCondition == 'userId' ? "selected" : '' }>ȸ��ID</option>
				<option value = "userName" ${not empty search.searchCondition && search.searchCondition == 'userName' ? "selected" : '' }>ȸ����</option>
				<input type="text"  id = "searchKeyword" name="searchKeyword"  value="${not empty search.searchKeyword ? search.searchKeyword : ''}"  
					class="ct_input_g" style="width:200px; height:19px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						�˻�
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<!-- ������ ������ ���̺�� ��� -->
<div style="display: flex;">
	<div style="flex: 1; border: 1px solid black;">
	<table width = "100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td colspan="11" >��ü  ${resultPage.totalCount} �Ǽ�, ����  ${resultPage.currentPage} ������ </td>
		</tr>
		<tr>
			<td class="ct_list_b" width ="100">No</td>
			<td class="ct_line02"></td> 
			<td class="ct_list_b" width ="150"><p>ȸ��ID</p><p>(Ŭ���� ������)</p></td>
			<td class="ct_line02"></td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="808285" height="1"></td>
		</tr>
		<c:set var = "no" value = "0"/>
		<c:forEach var = "user" items = "${list}">
			<c:set var = "no" value = "${no + 1}"/>
			<tr class="ct_list_pop">
			<td align="center">${no}</td>
			<td></td>
			<td align="left">${user.userId}</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
		</c:forEach>
	</table>
	<!--  ������ Navigator -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td align="center">
				<jsp:include page = "../common/pageNavigator.jsp"/>
	    	</td>
		</tr>
	</table>
	</div>
	
	<!-- �� ���� ��� -->
	<div style="flex: 1; background-color: #ccc; border: 1px solid black;">
		<h2>���� Ȯ��</h2>
		<span id = "userInformation">
			<h3 class = "infoFirst">���� ����</h3>
		</span>
		<div id = "userButton" >
			<table border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						����
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					
					<td width="30"></td>					
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						����
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</form>

<form name="updateOrDeleteForm" action="/user/listUser/1" method="post">
	<input type ="hidden" name = "userId"  id = "userId" value = ""/>
</form>
</div>
</body>
</html>