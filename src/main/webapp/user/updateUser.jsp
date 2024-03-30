<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html lang="en">

<head>
<script defer type="text/javascript" src ="/javascript/user/updateUser.js"></script>
<script defer type="text/javascript" src ="/javascript/common.js"></script>
<jsp:include page="../toolbar.jsp" flush="true"/>
<title>User Update</title>
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm"  method="post" >

<input type="hidden" name="userId" value="${user.userId}">
<input type="hidden" name="role" value="${user.role}">
<input type="hidden" name="phone" >
    <br />
    <div class="container">
        <h1>사용자 수정</h1>
        <div class="row">
            <div class="col-md-5">
                <img src="#" class="img-rounded" width="100%" />
            </div>
            <div class="col-md-7">
                <div class="input-group input-group-lg flex-nowrap mb-3">
                    <span class="input-group-text" id="addon-wrapping">아이디</span>
                    <span class="input-group-text" id="addon-wrapping">${user.userId}</span>
                </div>

                <div class="input-group input-group-lg flex-nowrap mb-3">
                    <span class="input-group-text" id="addon-wrapping">이름</span>
                    <input type="text" name="userName" value="${user.userName}" class="form-control">
                </div>

                <div class="input-group input-group-lg flex-nowrap mb-3">
                    <span class="input-group-text" id="addon-wrapping">주소</span>
                    <input type="text" name="addr" value="${user.addr}" class="form-control">
                </div>

                <div class="input-group input-group-lg flex-nowrap mb-3">
                    <span class="input-group-text" id="addon-wrapping">전화 번호</span>
                </div>
                <div class="input-group input-group-lg flex-nowrap mb-3">
                    <div class="col-md-3">
                        <select name="phone1" class="form-select">
                            <option value="010" ${(not empty user.phone1 && user.phone1=="010" ) ? "selected" : '' }>010
                            </option>
                            <option value="011" ${(not empty user.phone1 && user.phone1=="011" ) ? "selected" : '' }>011
                            </option>
                            <option value="016" ${(not empty user.phone1 && user.phone1=="016" ) ? "selected" : '' }>016
                            </option>
                            <option value="018" ${(not empty user.phone1 && user.phone1=="018" ) ? "selected" : '' }>018
                            </option>
                            <option value="019" ${(not empty user.phone1 && user.phone1=="019" ) ? "selected" : '' }>019
                            </option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <input type="text" name="phone2" value="${not empty user.phone2 ? user.phone2 : '' }"
                            class="form-control" placeholder="Username">
                    </div>
                    <div class="col-md-3">
                        <input type="text" name="phone3" value="${not empty user.phone3 ? user.phone3 : '' }"
                            class="form-control" placeholder="Username">
                    </div>
                </div>

                <div class="input-group input-group-lg flex-nowrap mb-3">
                    <span class="input-group-text" id="addon-wrapping">이메일</span>
                    <input type="text" name="email" value="${user.email}" class="form-control">
                </div>

                <button type="button" name = "update" class="btn btn-warning">수정하기</button>

                <button type="button" name = "back" class="btn btn-success">뒤로가기</button>
            </div>
        </div>
    </div>
    
</form>

</body>
</html>
