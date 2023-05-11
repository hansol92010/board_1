<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<script>
$(document).ready(function() {
	
	$("#userId").focus();
	
	$("#btnReg").on("click", function() {
		
		//공백 체크 정규식
		var empCheck = /\s/g;
		
		//영문 대소문자와 숫자 조합으로 이루어진, 4~12자리 정규식
		var idPwCheck = /^[a-zA-z0-9]{4,12}$/;
		
		//아이디부터 체크
		if($.trim($("#userId").val()).length <= 0) {
			alert("사용자 아이디를 입력하세요");
			$("#userId").val("");
			$("userId").focus();
			return;
		}
		
		if(empCheck.test($("#userId").val())) {
			alert("사용자 아이디는 공백을 포함할 수 없습니다.");
			$("#userId").val("");
			$("#userId").focus();
			return;
		}
		
		if(!idPwCheck.test($("#userId").val())) {
			alert("아이디는 4~12자리의 영문 대소문자와 숫자로만 입력하세요.");
			$("#userId").val("");
			$("#userId").focus();
			return;
		}
		
		if($.trim($("#userPwd1").val()).length <= 0) {
			alert("비밀번호를 입력하세요.");
			$("userPwd1").val("");
			$("userPwd1").focus();
			return;
		}
		
		if(empCheck.test($("#userPwd1").val())) {
			alert("비밀번호는 공백을 포함할 수 없습니다.");
			$("userPwd1").val("");
			$("userPwd1").focus();
			return;
		}
		
		if(!idPwCheck.test($("#userPwd1").val())) {
			alert("비밀번호는 4~12자리의 영문 대소문자와 숫자로 입력하세요.");
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
			alert("이메일 형식이 올바르지 않습니다.");
			$("#userEmail").val("");
			$("#userEmail").focus();
			return;
		}
		
		//hidden타입 userPwd
		$("#userPwd").val($("#userPwd1").val());
		
		//아이디 중복체크
		$.ajax({
			type:"POST",
			url:"/user/idCheck",
			data:{
				userId:$("#userId").val()
			},
			datatype:"JSON",
			beforSend:function(xhr){
				xhr.setRequestHeader("AJAX", "true");
			},
			success:function(response){
				if(response.code == 0) {
					fn_userReg();
				} else if(response.code == 100) {
					alert("중복된 아이디입니다.");
					$("#userId").focus();
				} else if(response.code == 400) {
					alert("파라미터 값이 올바르지 않습니다.")
					$("#userId").focus();
				} else {
					alert("오류가 발생항였습니다.");
					$("#userId").focus();
				}
			},
			error:function(xhr, status, error) {
				icia.common.error(error);
			}

		});
	});
});

function fn_validateEmail(value) {
	
	//이메일 정규식
	var emailReg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	
	return emailReg.test(value);
	
}

function fn_userReg() {
	
	$.ajax({
		type:"POST",
		url:"/user/regProc",
		data:{
			userId:$("#userId").val(),
			userPwd:$("#userPwd").val(),
			userName:$("#userName").val(),
			userEmail:$("#userEmail").val()
		},
		datatype:"JSON",
		beforSend:function(xhr) {
			xhr.setRequestHeader("AJAX", "true");
		},
		success:function(response) {
			if(response.code == 0) {
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			} else if(response.code == 100) {
				alert("회원 아이디가 중복되었습니다.");
				$("#userId").focus();
			} else if(response.code == 400) {
				alert("파라미터 값이 올바르지 않습니다.");
				$("#userId").focus();
			} else if(response.code == 500) {
				alert("회원 가입 중 오류가 발생하였습니다.");
				$("#userId").focus();
			} else {
				alert("회원 가입 중 오류가 발생하였습니다.");
				$("#userId").focus();
			}
		},
		error:function(xhr, status, error) {
			icia.common.error(error);
		}
	});

}
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation.jsp" %>

<div class="container">
	<div class="row mt-5">
		<h1>회원가입</h1>
	</div>
	
	<div class="row mt-2">
		<div class="col-12">
		
			<form id="regForm" method="POST">
				<div class="form-group">
					<label for="userId">사용자 아이디</label>
					<input type="text" class="form-control" id="userId" name="userId" maxlength="12" placeholder="사용자 아이디" />
				</div>
				
				<div class="form-group">
					<label for="userPwd1">비밀번호</label>
					<input type="password" class="form-control" id="userPwd1" name="userPwd1" maxlength="12" placeholder="비밀번호" />
				</div>
				
				<div class="form-group">
					<label for="userPwd2">비밀번호 확인</label>
					<input type="password" class="form-control" id="userPwd2" name="userPwd2" maxlength="12" placeholder="비밀번호 확인" />
				</div>
				
				<div class="form-group">
					<label for="userName">사용자 이름</label>
					<input type="text" class="form-control" id="userName" name="userName" maxlength="15" placeholder="사용자 이름" />
				</div>
				
				<div class="form-group">
					<label for="userEmail">사용자 이메일</label>
					<input type="text" class="form-control" id="userEmail" name="userEmail" maxlength="30" placeholder="사용자 이메일" />
				</div>
				
				<input type="hidden" id="userPwd" name="userPwd" value="" />
				<button type="button" id="btnReg" class="btn btn-primary">등록</button>
			</form>
			
		</div>
	</div>
</div>

</body>
</html>