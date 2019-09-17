<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${cp }/js/jquery-3.4.1.min.js"></script>
<script>
	$(function() {
		$("#pathBtn").on("click", function() {
			console.log("pathBtn");
			console.log($("input[name=path]:checked").val());
			
			var subpath = $("input[name=path]:checked").val();
			$("#frm").attr("action", "${cp}/mvc/path/" + subpath);
			$("#frm").submit();
		});
	});</script>
</head>
<body>
	<h1>mvc/view.jsp</h1>
	<h3>requestParam</h3>
	<form action="${cp }/mvc/requestParam" method="post">
		userId: <input type="text" name="userId" value="sally"/> <br>
		<input type="submit" value="전송"/>
	</form>
	<hr>
	
	<h3>pathVariable</h3>
	<form action="${cp }/mvc/path/" method="post" id="frm">
		brown<input type="radio" name="path" value="brown" checked><br>
		sally<input type="radio" name="path" value="sally"><br>
		moon<input type="radio" name="path" value="moon"><br>
		<input type="button" id="pathBtn" value="전송"/>
	</form>
	
</body>
</html>