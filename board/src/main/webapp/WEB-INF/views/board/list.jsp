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

	<div class="d-flex">
		<div style="width:50%;">
			<h2>게시판</h2>
		</div>
		
		<div class="ml-auto input-group" style="width:50%;">
			<select name="_searchType" id="_searchType" class="custom-select" style="width:auto;">
				<option>작성자</option>
				<option>제목</option>
				<option>내용</option>
			</select>
			<input type="text" name="_searchValue" id="_searchValue" class="form-control mx-1" maxlength="20" style="width:auto;ime-mode:active" placeholder="조회값을 입력하세요" />
			<button type="button" id="btnSearch" class="btn btn-secondary mb-3 mx-1">조회</button>
		</div>	
	</div>
	
	<table class="table table-hover">
		<thead>
			<tr style="background-color:#dee2e6;">
				<th scope="col" class="text-center" style="width:10%;">번호</th>
				<th scope="col" class="text-center" style="width:55%;">제목</th>
				<th scope="col" class="text-center" style="width:10%;">작성자</th>
				<th scope="col" class="text-center" style="width:15%;">날짜</th>
				<th scope="col" class="text-center" style="width:10%;">조회수</th>
			</tr>
		</thead>
		
		<tbody>
		
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="5"></td>
			</tr>
		</tfoot>
	
	</table>
	
	
	




</div>





</body>
</html>