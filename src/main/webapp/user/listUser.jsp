<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<html>
<head>
<title>회원목록 조회</title>

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
	// 처음 유저 버튼은 비활성화
	$("#userButton").addClass("disabled");
	
	$("td.ct_btn01:contains('검색')").on("click", function() {
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
					+ "아이디 : " + JSONData.userId + "<br/>"
					+ "이름 : " +  JSONData.userName + "<br/>"
					+ "비밀번호 : " +  JSONData.password + "<br/>"
					+ "휴대폰 번호 : " +  JSONData.phone + "<br/>"
					+ "주소 : " +  JSONData.addr + "<br/>"
					+ "이메일 : " +  JSONData.email + "<br/>"
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
	
	$("td.ct_btn01:contains('삭제')").on("click", function() {
		result = window.confirm("정말로 탈퇴하시겠습니까?");
		if(result) {
			let url = "/user/deleteUser";
			$("form[name='updateOrDeleteForm']").attr("method", "POST").attr("action", url).submit();
		}
	});
	
	$("td.ct_btn01:contains('수정')").on("click", function() {
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
					<td width="93%" class="ct_ttl01">회원목록 조회</td>
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
				<option value = "userId" ${not empty search.searchCondition && search.searchCondition == 'userId' ? "selected" : '' }>회원ID</option>
				<option value = "userName" ${not empty search.searchCondition && search.searchCondition == 'userName' ? "selected" : '' }>회원명</option>
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
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<!-- 간략한 정보를 테이블로 출력 -->
<div style="display: flex;">
	<div style="flex: 1; border: 1px solid black;">
	<table width = "100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td colspan="11" >전체  ${resultPage.totalCount} 건수, 현재  ${resultPage.currentPage} 페이지 </td>
		</tr>
		<tr>
			<td class="ct_list_b" width ="100">No</td>
			<td class="ct_line02"></td> 
			<td class="ct_list_b" width ="150"><p>회원ID</p><p>(클릭시 상세정보)</p></td>
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
	<!--  페이지 Navigator -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<tr>
			<td align="center">
				<jsp:include page = "../common/pageNavigator.jsp"/>
	    	</td>
		</tr>
	</table>
	</div>
	
	<!-- 상세 정보 출력 -->
	<div style="flex: 1; background-color: #ccc; border: 1px solid black;">
		<h2>정보 확인</h2>
		<span id = "userInformation">
			<h3 class = "infoFirst">유저 정보</h3>
		</span>
		<div id = "userButton" >
			<table border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						수정
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					
					<td width="30"></td>					
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						삭제
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