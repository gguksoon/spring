<%@page import="kr.or.ddit.user.model.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
								
<c:forEach items="${userList}" var="user">
	<tr class="userTr" data-userId="${user.userId }">
		<td>${user.userId}</td>
		<td>${user.userNm}</td>
		<td>${user.alias}</td>
		<td><fmt:formatDate value="${user.reg_dt }" pattern="yyyy/MM/dd"/></td>
	</tr>
</c:forEach>

##########pagination##############

<c:choose>
	<c:when test="${pageVo.page == 1 }">
		<li class="disabled">
			<span aria-hidden="true">&laquo;</span>
		</li>
	</c:when>
	<c:otherwise>
		<li>
			<a href="javascript:getUserHtmlList(${pageVo.page - 1}, ${pageVo.pagesize })" aria-label="Previous">
				<span aria-hidden="true">&laquo;</span>
			</a>
		</li>
	</c:otherwise>
</c:choose>

<c:forEach begin="1" end="${paginationSize }" var="idx">
	<c:choose>
		<c:when test="${idx == pageVo.page }">
			<%-- 현재 페이지를 클릭했을때도 재요청되므로 막아준다.
			<li class="active"><a href="${cp }/userPagingList?page=${idx }&pagesize=10">${idx }</a></li>
			--%>
			<li class="active"><span>${idx }</span></li>
			 
		</c:when>
		<c:otherwise>
<%-- 			<li><a href="${cp }/user/userPagingList?page=${idx }">${idx }</a></li> --%>
			<li><a href="javascript:getUserHtmlList(${idx }, ${pageVo.pagesize })">${idx }</a></li>
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
			<a href="javascript:getUserHtmlList(${pageVo.page + 1}, ${pageVo.pagesize })" aria-label="Previous">
				<span aria-hidden="true">&raquo;</span>
			</a>
		</li>
	</c:otherwise>
</c:choose>