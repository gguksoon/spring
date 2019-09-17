<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
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
	
	.lprodTr:hover {
		background: #FFCA6C;
		cursor: pointer;
	}
</style>
<script>
	$(document).ready(function() {
		
		// 사용자 정보 클릭 시 이벤트 핸들러
		$(".lprodTr").on("click", function() {
			// 클릭된 tr태그의 data 불러오기
			var data = $(this).data("lprodgu");
			console.log("data: " + data);
			
			// input 태그에 값 설정
			$("#lprod_gu").val(data);
			
			// form 태그 이용하여 전송
			console.log("serialize: " + $("#frm").serialize());
			
			$("#frm").submit();
		});
		
	});
</script>
</head>

<body>
	<!-- 개발자 입장에서 데이터를 전송하기 위하여 사용하는 form -->
	<form id="frm" action="${cp }/prodList" method="get">
		<input type="hidden" id="lprod_gu" name="lprod_gu"/>
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
						<h2 class="sub-header">제품 그룹 페이징 리스트</h2>
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th>그룹 아이디</th>
									<th>그룹 코드</th>
									<th>그룹 이름</th>
								</tr>
								
								<c:forEach items="${lprodList}" var="lprodVo">
									<tr class="lprodTr" data-lprodGu="${lprodVo.lprod_gu }">
										<td>${lprodVo.lprod_id}</td>
										<td>${lprodVo.lprod_gu}</td>
										<td>${lprodVo.lprod_nm}</td>
									</tr>
								</c:forEach>
								
							</table>
						</div>

						<a class="btn btn-default pull-right">제품 등록</a>

						<div class="text-center">
							<ul class="pagination">
								<c:choose>
									<c:when test="${pageVo.page == 1 }">
										<li class="disabled">
											<span aria-hidden="true">&laquo;</span>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="${cp }/lprod/lprodPagingList?page=${pageVo.page - 1}" aria-label="Previous">
												<span aria-hidden="true">&laquo;</span>
											</a>
										</li>
									</c:otherwise>
								</c:choose>
								
								<c:forEach begin="1" end="${paginationSize }" var="idx">
									<c:choose>
										<c:when test="${idx == pageVo.page }">
											<li class="active"><span>${idx }</span></li>
											 
										</c:when>
										<c:otherwise>
											<li><a href="${cp }/lprod/lprodPagingList?page=${idx }">${idx }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								
								<c:choose>
									<c:when test="${pageVo.page == paginationSize }">
										<li class="disabled">
											<span aria-hidden="true">&raquo;</span>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="${cp }/lprod/lprodPagingList?page=${pageVo.page + 1}" aria-label="Previous">
												<span aria-hidden="true">&raquo;</span>
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
