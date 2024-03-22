<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<html>
<head>
<title>구매상세조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	function limitUpdate(tranNo, tranCode) {
		if(tranCode == '002' || tranCode == '003') {
			alert("배송 중이거나 배송 완료가 되면 수정할 수 없습니다.");
			return;
		} else {
			let url = "/purchase/updatePurchaseView/" + tranNo;
			window.location.href = url; 
		}
	}
	
	$(function() {
		$("td.ct_btn01:contains('수정')").on("click", function() {
	        var tranNo = $("input[name = 'tranNo']").val();
	        var tranCode = $("input[name = 'tranCode']").val();
			limitUpdate(tranNo, tranCode);
		})
			
		$("td.ct_btn01:contains('확인')").on("click", function() {
			history.go(-1);
		})
		// https://horangi.tistory.com/417
		$("td.ct_btn01:contains('삭제')").on("click", function() {
			result = window.confirm("정말로 삭제하시겠습니까?");
			if(result) {
				let url = "/purchase/deletePurchase";
				$("form").attr("method", "POST").attr("action", url).submit();
			}
		})
		
	});
</script>

</head>
<body bgcolor="#ffffff" text="#000000">

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif"	width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매상세조회</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif"	width="12" height="37"/>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			물품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">
					${purchase.purchaseProd.prodNo}
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			구매자아이디 <img	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${purchase.buyer.userId}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>

	<tr>
		<td width="104" class="ct_write">구매 방법</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			${paymentOption}
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">개수</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			${purchase.prodCount}
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">구매자이름</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${purchase.receiverName}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">구매자연락처</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${purchase.receiverPhone}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">구매자주소</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${purchase.dlvyAddr}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">구매요청사항</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${purchase.dlvyRequest}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">배송희망일</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${purchase.dlvyDate}</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>

	<tr>
		<td width="104" class="ct_write">주문일</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${purchase.orderDate}</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>

<!-- jQuery 사용 -->
<form name = "deletePurchase" action = "/purchase/deletePurchase" method = "POST">
	<input type ="hidden" name = "tranNo" value = "${purchase.tranNo}"/>
	<input type ="hidden" name = "tranCode" value = "${purchase.tranCode}"/>
	
	<input type ="hidden" name = "prodNo" value = "${purchase.purchaseProd.prodNo}"/>
	<input type ="hidden" name = "prodCount" value = "${purchase.prodCount}">
</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01"  style="padding-top: 3px;">
						수정
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					
					<td width="30"></td>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01"	 style="padding-top: 3px;">
						확인
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif"width="14" height="23"/>
					</td>
					
					<td width="30"></td>
						<td width="17" height="23">
							<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
						</td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01"	 style="padding-top: 3px;">
							삭제
						</td>
						<td width="14" height="23">
							<img src="/images/ct_btnbg03.gif"width="14" height="23"/>
						</td>
					
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>