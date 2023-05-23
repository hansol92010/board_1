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
<c:choose>
	<c:when test="${empty hiBoard}">
		alert("게시물이 존재하지 않습니다.");
		location.href = "/board/list";
	</c:when>

	<c:otherwise>
		$("#btnList").on("click", function() {
			document.bbsForm.action = "/board/list";
			document.bbsForm.submit();
		});
		
		$("#btnReply").on("click", function() {
			document.bbsForm.action = "/board/replyForm";
			document.bbsForm.submit();
		});
		
			<c:if test="${boardMe eq 'Y'}">
		$("#btnUpdate").on("click", function() {
			document.bbsForm.action = "/board/updateForm";
			document.bbsForm.submit();
		});
		
		$("#btnDelete").on("click", function() {
			if(confirm("게시물을 삭제하시겠습니까?") == true) {
				
				$.ajax({
					type:"POST",
					url:"/board/delete",
					data: {
						hibbsSeq:<c:out value="${hiBoard.hibbsSeq}" />
					},
					dataType:"JSON",
					beforeSend:function(xhr){
			    		xhr.setRequestHeader("AJAX", "true");
			    	},
			    	success:function(response)
			    	{
			    		if(response.code == 0)
			    		{
			    			alert("게시물이 삭제되었습니다.");
			    			location.href = "/board/list";
			    		}	
			    		else if(response.code == 400)
			    		{
			    			alert("파라미터 값이 올바르지 않습니다.");
			    		}
			    		else if(response.code == 404)
			    		{
			    			alert("게시물을 찾을 수 없습니다.");
			    			location.href = "/board/list";
			    		}
			    		else if(response.code == -999)
			    		{
			    			alert("답변 게시물이 존재하여 삭제할 수 없습니다.");
			    		}	
			    		else
			    		{
			    			alert("게시물 삭제 중 오류가 발생하였습니다.");
			    		}	
			    	
			    	},
			    	error:function(xhr, status, error)
			    	{
			    		icia.common.error(error);
			    	}
				});	
			}

		});
			</c:if>
	</c:otherwise>
</c:choose>
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