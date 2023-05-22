<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%
	// 개행문자 값을 저장한다.
	pageContext.setAttribute("newLine", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<script>
$(document).ready(function() {
	
	$("#btnList").on("click", function() {
		document.bbsForm.action = "/board/list";
		document.bbsForm.submit();
	});
	
	$("#btnReply").on("click", function() {
		document.bbsForm.action = "/board/replyForm";
		document.bbsForm.submit();
	});
	
/* 	$("#btnUpdate").on("click", function() {
		document.bbsForm.hibbsSeq.value = $("#hibbsSeq").val();
		document.bbsForm.action = "/board/delete";
		document.bbsForm.submit();
	}) */
	
})
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation.jsp" %>
<c:if test="${!empty hiBoard}">
<div class="container">
	<h2>게시물 보기</h2>
	<div class="row" style="margin-right=0; margin-left:0;">
		<table class="table">
			<thead>
				<tr class="table-active">
					<th scope="col" style="width:60%">
						${hiBoard.hibbsTitle}<br />
						${hiBoard.userId}&nbsp;&nbsp;&nbsp;
						<a href="mailto:${hiBoard.userEmail}" style="color:#828282;">${hiBoard.userEmail}</a>
					<c:if test="${!empty hiBoard.hiBoardFile}">
						&nbsp;&nbsp;&nbsp;<a href="/board/download?hibbsSeq=${hiBoard.hiBoardFile.hibbsSeq}" style="color:#000;">[첨부파일]</a>
					</c:if>
					</th>
					<th scope="col" style="width:40%" class="text-right">
						조회: <fmt:formatNumber type="number" maxFractionDigits="3" value="${hiBoard.hibbsReadCnt}" /><br/>
						${hiBoard.regDate}
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="2"><pre>${hiBoard.hibbsContent}</pre></td>
				</tr>	
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2"></td>
				</tr>
			</tfoot>
		</table>
	</div>

	<button type="button" id="btnList" class="btn btn-secondary">리스트</button>
	<button type="button" id="btnReply" class="btn btn-secondary">답변</button>

<c:if test="${boardMe eq 'Y'}">
	<button type="button" id="btnUpdate" class="btn btn-secondary">수정</button>
	<button type="button" id="btnDelete" class="btn btn-secondary">삭제</button>
</c:if>
</div>
</c:if>	

<form name="bbsForm" id="bbsForm" method="post">
	<input type="hidden" name="hibbsSeq" id="hibbsSeq" value="${hibbsSeq}" />
	<input type="hidden" name="searchType" id="searchType" value="${searchType}" />
	<input type="hidden" name="searchValue" id="searchValue" value="${searchValue}" />
	<input type="hidden" name="curPage" id="curPage" value="${curPage}" />
</form>

</body>
</html>