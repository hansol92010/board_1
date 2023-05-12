<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<script>
$(document).ready(function() {
	
	$("#userPwd1").focus();
	
	$("#btnUpdate").on("click", function() {
		
		//공백여부체크
		var empCheck = /\s/g;
		
		//아이디와 비밀번호 형식 체크
		var idPwCheck = /^[a-zA-z0-9]{4,12}$/;
		
		if($.trim($("#userPwd1").val()).length <= 0) {
			alert("비밀번호를 입력하세요.");
			$("#userPwd1").focus();
			return;
		}
		
		if(empCheck.test($("#userPwd1").val())) {
			alert("비밀번호는 공백을 포할 수 없습니다.");
			$("#userPwd1").val("");
			$("#userPwd1").focus();
			return;
		}
		
		if(!idPwCheck.test($("#userPwd1").val())) {
			alert("비밀번호는 4~12자리의 영문 대소문자와 숫자로만 입력하세요.");
			$("#userPwd1").val("");
			$("#userPwd1").focus();
			return;
		}
		
		if($("#userPwd1").val() != $("#userPwd2").val()) {
			alert("비밀번호가 일치하지 않습니다.");
			$("#userPwd2").val("");
			$("#userPwd2").focus();
			return;
		}
		
		if($.trim($("#userName").val()).length <= 0) {
			alert("사용자 이름을 입력하세요.");
			$("#userName").val("");
			$("#userName").focus();
			return;
		}
		
		if($.trim($("#userEmail").val()).length <= 0) {
			alert("이메일을 입력하세요.");
			$("#userEmail").val("");
			$("#userEmail").focus();
			return;
		}
		
		if(!fn_validateEmail($("#userEmail").val())) {
			alert("이메일을 이메일 형식에 맞게 입력하세요.");
			$("#userEmail").val("");
			$("#userEmail").focus();
			return;
		}
		
		//비밀번호 hidden으로 넘겨주기
		$("#userPwd").val($("#userPwd1").val());
		
		$.ajax({
			type:"POST",
			url:"/user/updateProc",
			data:{
				userId:$("#userId").val(),
				userPwd:$("#userPwd").val(),
				userName:$("#userName").val(),
				userEmail:$("#userEmail").val()
			},
			datatype:"JSON",
			beforeSend:function(xhr) {
				xhr.setRequestHeader("AJAX", "true");
			},
			success:function(response) {
				if(response.code == 0) {
					alert("회원정보가 수정되었습니다.");
					location.href = "/";
				} else if(response.code == 400) {
					alert("파마리터 값이 올바르지 않습니다.");
					$("#userPwd1").focus();
				} else if(response.code == 404) {
					alert("회원정보가 존재하지 않습니다.");
					location.href = "/";
				} else if(response.code == 500) {
					alert("회원정보 수정 중에 오류가 발생하였습니다.");
					$("#userPwd1").focus();
				} else {
					alert("회원정보 수정 중에 오류가 발생하였습니다.");
					$("#userPwd1").focus();
				}
			},
			error:function(xhr, status, error) {
				icia.common.error(error);
			}
		});
	});

});

function fn_validateEmail(value) {
	var emailUpdate = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	
	return emailUpdate.test(value);
}
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation.jsp" %>

<div class="container">
	<div class="row mt-5">
		<h1>회원정보수정</h1>
	</div>
	<div class="row mt-5">
		<div class="col-12">
			<form>
				<div class="form-group">
					<label for="userId">사용자 아이디</label>
					${user.userId}
				</div>
				<div class="form-group">
					<label for="userPwd1">비밀번호</label>
					<input type="password" name="userPwd1" id="userPwd1" class="form-control" placeholder="비밀번호" maxlength="12" value="${user.userPwd}"/>
				</div>
				<div class="form-group">
					<label for="userPwd2">비밀번호 확인</label>
					<input type="password" name="userPwd2" id="userPwd2" class="form-control" placeholder="비밀번호 확인" maxlength="12" value="${user.userPwd}" />			
				</div>
				<div class="form-group">
					<label for="userName">사용자 이름</label>
					<input type="text" name="userName" id="userName" class="form-control" placeholder="사용자 이름" maxlength="15" value="${user.userName}"  />			
				</div>
				<div class="form-group">
					<label for="userEmail">사용자 이메일</label>
					<input type="text" name="userEmail" id="userEmail" class="form-control" placeholder="사용자 이메일" maxlength="30" value="${user.userEmail}" />
				</div>
				<input type="hidden" name="userPwd" id="userPwd" value="" />
				<input type="hidden" name="userId" id="userId" value="${user.userId}" />
				<button type="button" id="btnUpdate" class="btn btn-primary">수정</button>
			</form>
		</div>
	</div>
</div>

</body>
</html>