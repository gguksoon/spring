<%@page import="kr.or.ddit.user.model.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<title>Jsp-basicLib</title>
<%@ include file="/WEB-INF/views/commonJsp/basicLib.jsp" %>
<style>
	tr:nth-child(odd) {
		background: #FAFAFA;
	}
	
	.userTr:hover {
		background: #FFCA6C;
		cursor: pointer;
	}
</style>
<script>
	$(document).ready(function() {
		
		// 사용자 정보 클릭 시 이벤트 핸들러
		$(".userTr").on("click", function() {
			// 클릭된 tr태그의 자식태그(td)중 첫번째 자식의 텍스트 문자열
			var tdText = $(this).children().eq(1).text();
			console.log("tdText: " + tdText);
			
			// input 태그에 저장된 값 확인
			var inputValue = $(this).find("input").val();
			console.log("inputValue: " + inputValue);
			
			// data 속성으로 값(tr 태그에 저장된 값) 확인
			var dataValue = $(this).data("userid"); // 모두 소문자로 치환됨
			console.log("dataValue: " + dataValue);
			
			// input 태그에 값 설정
			$("#userId").val(dataValue);
			
			// form 태그 이용하여 전송
			console.log("serialize: " + $("#frm").serialize());
			
			$("#frm").submit();
		});
		
	});
</script>
</head>

<body>
	<!-- 개발자 입장에서 데이터를 전송하기 위하여 사용하는 form -->
	<form id="frm" action="${cp }/user/user" method="post">
		<input type="hidden" id="userId" name="userId"/>
	</form>
	
	<!-- header -->
	<%@ include file="/WEB-INF/views/commonJsp/header.jsp" %> 

	<div class="container-fluid">
		<div class="row">

			<div class="col-sm-3 col-md-2 sidebar">
				<!-- left -->
				<%@ include file="/WEB-INF/views/commonJsp/left.jsp" %>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<div class="row">
					<div class="col-sm-8 blog-main">
						<h2 class="sub-header">사용자 리스트</h2>
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th>사용자 아이디</th>
									<th>사용자 이름</th>
									<th>사용자 별명</th>
									<th>등록일시</th>
								</tr>
								
								<c:forEach items="${userList}" var="user">
									<tr class="userTr" data-userId="${user.userId }">
										<input type="hidden" value="${user.userId }"/>
										<td>${user.userId}</td>
										<td>${user.userNm}</td>
										<td>${user.alias}</td>
										<td><fmt:formatDate value="${user.reg_dt }" pattern="yyyy/MM/dd"/></td>
									</tr>
								</c:forEach>
								
							</table>
						</div>

						<a class="btn btn-default pull-right">사용자 등록</a>

						<div class="text-center">
							<ul class="pagination">
								<li><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
