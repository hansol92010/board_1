<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<script>
$(document).ready(function() {
<c:choose>
	<c:when test="${empty hiBoard}">
		alert("수정할 게시물이 존재하지 않습니다.");
		location.href = "/board/list";
	</c:when>
	
	<c:otherwise>
		$("#btnUpdate").on("click", function() {
			
			$("#btnUpdate").prop("disabled", true);
			
			if($.trim($("#hibbsTitle").val()).length <= 0) {
				alert("게시물 제목을 입력하세요.");
				$("#hibbsTitle").focus();
				
				$("#btnUpdate").prop("disabled", false);
				return;
			}
			
			if($.trim($("#hibbsContent").val()).length <= 0) {
				alert("게시물 내용을 입력하세요.");
				$("#hibbsContent").focus();
				
				$("#btnUpdate").prop("disalbed", false);
				return;
			}
			
			var form = $("#updateForm")[0];
			var formDate = new FormData(form);
			
			$.ajax({
				type:"POST",
				enctype:"multipart/form-data",
				url:"/board/updateProc",
				data:formData,
				processData:false,
				contentType:false,
				beforeSend:function(xhr) {
					xhr.setRequestHeader("AJAX", "true");
				},
				success:function(response) {
					
				},
				error:function(error) {
					icia.common.errror(error);
				}
			});
			
		});
		
		$("#btnList").on("click", function() {
			document.bbsForm.action = "/board/list";
			document.bbsForm.submit();
		})
	</c:otherwise>
</c:choose>
})
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation.jsp" %>
<c:if test="${!empty hiBoard}">
<div class="container">
	<h2>게시물 수정</h2>
	<form name="updateForm" id="updateForm" method="post" enctype="multipart/form-data">
		<input type="text" name="userName" id="userName" maxlength="20" value="${hiBoard.userName}" style="ime-mode:active;" class="form-control mt-4 mb-2" placeholder="이름을 입력해주세요." readonly/>
		<input type="text" name="userEmail" id="userEmail" maxlength="30" value="${hiBoard.userEmail}" style="ime-mode:inactive;" class="form-control mb-2" placeholder="이메일을 입력해주세요." readonly />
		<input type="text" name="hibbsTitle" id="hibbsTitle" maxlength="100" value="${hiBoard.hibbsTitle}" style="ime-mode:active" class="form-control mb-2" placeholder="제목을 입력해주세요." required />
		<div class="form-group">
			<textarea class="form-control" rows="10" name="hibbsContent" id="hibbsContent" style="ime-mode:active;" placeholder="내용을 입력해주세요." required>${hiBoard.hibbsContent}</textarea>
		</div>
		<input type="file" name="hibbsFile" id="hibbsFile" class="form-control mb-2" placeholder="파일을 선택하세요." required />
		<c:if test="${!empty hiBoard.hiBoardFile}">
		<div style="margin-bottom:0.3em;"><a href="/board/download?hibbs=${hiBoard.hiBoardFile.hibbsSeq}">[첨부파일: ${hiBoard.hiBoardFile.fileOrgName}]</a></div>
		</c:if>
		<input type="hidden" name="hibbsSeq" value="${hiBoard.hibbsSeq}"  />
		<input type="hidden" name="searchType" value="${searchType}"  />
		<input type="hidden" name="searchValue" value="${searchValue}"  />
		<input type="hidden" name="curPage" value="${curPage }"  />
	</form>
	
	<div class="form-group row">
		<div class="col-sm-12">
			<button type="button" id="btnUpdate" class="btn btn-primary" title="수정">수정</button>
			<button type="button" id="btnList" class="btn btn-secondary" title="리스트">리스트</button>
		</div>
	</div>
</div>

<form name="bbsForm" id="bbsForm" method="post">
	<input type="hidden" name="hibbsSeq" value="${hiBoard.hibbsSeq}" />
	<input type="hidden" name="searchType" value="${searchType}" />
	<input type="hidden" name="searchValue" value="${searchValue}" />
	<input type="hidden" name="curPage" value="${curPage}" />
</form>
</c:if>
</body>
</html>