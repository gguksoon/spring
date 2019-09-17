<%@page import="kr.or.ddit.user.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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

<title>Signin Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link
	href="${cp }/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${cp }/css/signin.css"
	rel="stylesheet">

<script src="${cp }/js/jquery-3.4.1.min.js"></script>

<script src="${cp }/js/js.cookie.js"></script>

<script>
	$(function() {
		
		// 쿠키에 있는 userId를 불러온다.
		var userId = Cookies.get('userId');
		if(userId != null) {
			$("#userId").val(userId);
			$("#rememberMe").prop('checked', true);
			$('#pass').focus();
		}
		
		// signin btn 클릭 이벤트 핸들러
		$('#signinBtn').on('click', function() {
			// remember me check box가 체크가 되었는가?

			// 체크 되었으면
			if($('#rememberMe').prop('checked')) {
				// userId 쿠키를 생성하고 값은 userId input의 값을 쿠기 값으로 설정
				Cookies.set("userId", $("#userId").val(), {expires : 30});
			// 체크 안되었으면
			} else {
				// 기존에 사용자가 아이디를 쿠키에 저장하는 기능을 사용하다가 더 이상 사용하지 않는 경우
				// 처음부터 아이디 쿠키 저장 기능을 사용하지 않는 경우
				// ==> userId 쿠키를 삭제
				Cookies.remove("userId");
			}
			
			// 로그인 요청(현재 button으로 되있으므로 submit해준다.)
			$("#frm").submit();
		})
		
		$("#userId, #pass").keydown(function(key) {
			if (key.keyCode == 13) {
				$("#frm").submit();
			}
		});
		
	});
</script>
</head>

<body>

	<div class="container">
		<%
			HttpSession httpSession = request.getSession();
			User userVo = (User) httpSession.getAttribute("S_USERVO");
			String userName = "";
			userName = userVo == null ? "" : userVo.getUserNm();
		%>
		사용자 이름:
		<%=userName %>

		<form id="frm" class="form-signin"
			action="${cp }/login" method="post">
			<h2 class="form-signin-heading">Please sign in</h2>

			<label for="userId" class="sr-only">UserId</label>
			<%
        	//String userId = (String)request.getAttribute("userId");
        	String userId = request.getParameter("userId");
        	userId = userId == null ? "" : userId;
        %>
			<input type="text" id="userId" name="userId" class="form-control"
				placeholder="userId" required autofocus value="brown">

			<label for="pass" class="sr-only">Password</label> <input
				type="password" id="pass" name="pass" class="form-control"
				placeholder="Password" required value="brown1234">

			<div class="checkbox">
				<label> <input id="rememberMe" name="rememberMe" type="checkbox" value="remember-me">
					Remember me
				</label>
			</div>
			<button id="signinBtn" class="btn btn-lg btn-primary btn-block" type="button">Sign in</button>
		</form>

	</div>
	<!-- /container -->

</body>
</html>
