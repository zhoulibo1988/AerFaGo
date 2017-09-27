<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>在线聊天</title>
<script type="text/javascript" src="resources/jquery.js"></script>
<script language="javascript"type="text/javascript"> 
	function fanhui(){
		window.history.back(-1); 
	}
</script> 
</head>
<body>
	<div class="page-container">
		<h5>登录失败，<button onclick="fanhui()">返回首页</button></h5>
	</div>
</body>
</html>
