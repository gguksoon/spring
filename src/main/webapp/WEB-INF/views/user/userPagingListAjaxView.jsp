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
		// 문서 로딩이 되면 자동으로 1페이지 리시트를 가져온다.
		getUserHtmlList(1, 10);
		
		// 사용자 정보 클릭 시 이벤트 핸들러
		$("#userListTbody").on("click", ".userTr", function() {
			// 클릭된 tr태그의 자식태그(td)중 첫번째 자식의 텍스트 문자열
			var tdText = $($(this).children()[1]).text(); // 에러 발생
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
	
	// ajax call을 html로 받는다(html javascript로 생성하는 작업을 줄인다.)
	function getUserHtmlList(page, pagesize) {
		$.ajax({
			url: "${cp}/user/userPagingListHtmlAjax",
			data: "page=" + page + "&pagesize=" + pagesize,
			success: function(data) {
				var html = data.split("##########pagination##############");
				// 유저 리스트 html 생성
				$("#userListTbody").html(html[0]);
				// 페이지 네이션 html 생성
				$(".pagination").html(html[1]);
			}
		});
	} 
	
	// ajax call을 통해 page, pagesize하는 사용자 데이터를 가져온다.
	function getUserList(page, pagesize) {
		$.ajax({
			url: "${cp}/user/userPagingListAjax",
			data: "page=" + page + "&pagesize=" + pagesize,
			success: function(data) {
				// 유저 리스트 html 생성
				createUserListTbody(data.userList);
				// 페이지 네이션 html 생성
				createPagination(data.pageVo, data.paginationSize);
			}
		});
	} 
	
	function createUserListTbody(userList) {
		// 기존 데이터 지우기
		$("#userListTbody").empty();
		
		// 새로운 데이터 생성
		var html = "";
		userList.forEach(function(user, idx){
			html += "<tr class='userTr'>";
			html += "	<td>" + user.userId + "</td>";
			html += "	<td>" + user.userNm + "</td>";
			html += "	<td>" + user.alias + "</td>";
			html += "	<td>" + user.reg_dt + "</td>";
			html += "</tr>";
		});
		
		// 새로운 데이터를 tbody에 추가
		$("#userListTbody").html(html);
	}
	
	function createPagination(pageVo, paginationSize) {
		var html = "";
	
		// --------------------
		if(pageVo.page == 1) {
			html += "<li class='disabled'>";
			html += "	<span aria-hidden='true'>&laquo;</span>";
			html += "</li>";
		} else {
			html += "<li>";
			html += "	<a href='javascript:getUserList(" + (pageVo.page - 1) + "," + pageVo.pagesize + ")' aria-label='Previous'>";
			html += "		<span aria-hidden='true'>&laquo;</span>";
			html += "	</a>";
			html += "</li>";
		}
		// ------------------
		for(var page = 1; page <= paginationSize; page++) {
			if(page == pageVo.page) {
				html += "<li class='active'><span>" + page + "</span></li>";
			} else {
				html += "<li>";
				html += "	<a href='javascript:getUserList(" + page + "," + pageVo.pagesize + ")'>" + page + "</a>";
				html += "</li>";
			}
		}
		// ------------------
		if(pageVo.page == paginationSize) {
			html += "<li class='disabled'>";
			html += "	<span aria-hidden='true'>&raquo;</span>";
			html += "</li>";
		} else {
			html += "<li>";
			html += "	<a href='javascript:getUserList(" + (pageVo.page + 1) + "," + pageVo.pagesize + ")' aria-label='Previous'>";
			html += "		<span aria-hidden='true'>&raquo;</span>";
			html += "	</a>";
			html += "</li>";
		}
		// ------------------
		
		$(".pagination").html(html);
	}
</script>
</head>

<body>
	<!-- 개발자 입장에서 데이터를 전송하기 위하여 사용하는 form -->
	<form id="frm" action="${cp }/user/user" method="get">
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
					<div class="blog-main">
						<h2 class="sub-header">사용자 페이징 리스트(ajax)</h2>
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th>사용자 아이디</th>
									<th>사용자 이름</th>
									<th>사용자 별명</th>
									<th>등록일시</th>
								</tr>
								<tbody id="userListTbody">
								
								</tbody>
								
<%-- 								<c:forEach items="${userList}" var="user"> --%>
<%-- 									<tr class="userTr" data-userId="${user.userId }"> --%>
<%-- 										<input type="hidden" value="${user.userId }"/> --%>
<%-- 										<td>${user.userId}</td> --%>
<%-- 										<td>${user.userNm}</td> --%>
<%-- 										<td>${user.alias}</td> --%>
<%-- 										<td><fmt:formatDate value="${user.reg_dt }" pattern="yyyy/MM/dd"/></td> --%>
<!-- 									</tr> -->
<%-- 								</c:forEach> --%>
								
							</table>
						</div>

						<a href="${cp }/user/userForm" class="btn btn-default pull-right">사용자 등록</a>

						<div class="text-center">
							<ul class="pagination">
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${pageVo.page == 1 }"> --%>
<!-- 										<li class="disabled"> -->
<!-- 											<span aria-hidden="true">&laquo;</span> -->
<!-- 										</li> -->
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<!-- 										<li> -->
<%-- 											<a href="${cp }/user/userPagingList?page=${pageVo.page - 1}" aria-label="Previous"> --%>
<!-- 												<span aria-hidden="true">&laquo;</span> -->
<!-- 											</a> -->
<!-- 										</li> -->
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>
								
<%-- 								<c:forEach begin="1" end="${paginationSize }" var="idx"> --%>
<%-- 									<c:choose> --%>
<%-- 										<c:when test="${idx == pageVo.page }"> --%>
<%-- 											현재 페이지를 클릭했을때도 재요청되므로 막아준다.
<%-- 											<li class="active"><a href="${cp }/userPagingList?page=${idx }&pagesize=10">${idx }</a></li> --%>
<%-- 											<li class="active"><span>${idx }</span></li> --%>
											 
<%-- 										</c:when> --%>
<%-- 										<c:otherwise> --%>
<%-- 											<li><a href="${cp }/user/userPagingList?page=${idx }">${idx }</a></li> --%>
<%-- 										</c:otherwise> --%>
<%-- 									</c:choose> --%>
<%-- 								</c:forEach> --%>
								
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${pageVo.page == paginationSize }"> --%>
<!-- 										<li class="disabled"> -->
<!-- 											<span aria-hidden="true">&raquo;</span> -->
<!-- 										</li> -->
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<!-- 										<li> -->
<%-- 											<a href="${cp }/user/userPagingList?page=${pageVo.page + 1}" aria-label="Previous"> --%>
<!-- 												<span aria-hidden="true">&raquo;</span> -->
<!-- 											</a> -->
<!-- 										</li> -->
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
