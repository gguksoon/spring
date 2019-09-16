<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- el -->
	<h1>${msg }</h1>
	<h3>${nowDt }</h3>
	<h3><fmt:formatDate value="${nowDt }" pattern="yyyy년 MM월 dd일"/></h3>
	
	<!-- 표현식 -->
	<%=request.getAttribute("msg") %>
	<%=request.getAttribute("nowDt") %>
</body>
</html>