<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>在线聊天</title>
 <!-- CSS -->
   <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
   <link rel="stylesheet" href="/assets/css/reset.css">
   <link rel="stylesheet" href="/assets/css/supersized.css">
   <link rel="stylesheet" href="/assets/css/style.css">
<script type="text/javascript" src="resources/jquery.js"></script>
</head>
<body>
	<div class="page-container">
		<h1>Login</h1>
		<form action="msg/login" method="post">
			<input type="text" name="admName" class="username"
				placeholder="Username"> <input type="password"
				name="admPassword" class="password" placeholder="Password">
			<button type="submit">Sign me in</button>
			<div class="error">
				<span>+</span>
			</div>
		</form>
	</div>
	<!-- Javascript -->
	<script src="/assets/js/supersized.3.2.7.min.js"></script>
	<script src="/assets/js/supersized-init.js"></script>
	<script src="/assets/js/scripts.js"></script>
</body>
</html>
