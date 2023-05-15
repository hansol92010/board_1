<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation.jsp" %>

<div class="container">
	<h2>게시물 쓰기</h2>
	<form name="writeForm" id="writeForm" method="post" enctype="mutipart/form-data">
		<input type="text" name="userName" id="userName" maxlength="20" value="${user.userName }" style="ime-mode:active;" class="form-control mt-4 mb-2" placeholder="이름을 입력해주세요" readonly />	
		<input type="text" name="userEmail" id="userEmail" maxlength="30" value="${user.userEmail }" style="ime-mode:inactice;" class="form-control mb-2"	 placeholder="이메일을 입력해주세요" readonly />
		<input type="text" name="hiBbsTitle" id="hiBbsTitle" maxlength="100" style="ime-mode:active;" class="form-conrol mb-2" placeholder="제목을 입력해주세요" required />
		
		<div class="form-group">
			<textarea class="form-control" rows="10" name="hiBbsContent" id="hiBbsContent" style="ime-mode:active;" placeholder="내용을 입력해주세요" required></textarea>
		</div>
		
		<input type="file" name="hiBbsFile" name="hiBbsFile" class="form-control mb-2" placeholder="파일을 선택하세요" required />
	
		<div class="form-group row">
			<div>
				<button type="button" id="btnWrite" class="btn btn-primary" title="저장">저장</button>
				<button type="button" id="btnList" class="btn btn-secondary" title="리스트">리스트</button>
			</div>
		
		</div>
	</form>
	
	<form name="bbsForm" id="bbsForm" method="post">
		<input type="hidden" name="searchType" value="" />
		<input type="hidden" name="searchValue" value="" />
		<input type="hidden" name="curpage" value="" />
	</form>


</div>

</body>
</html>