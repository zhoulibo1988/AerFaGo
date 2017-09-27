<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="<%=basePath%>resources/jquery.js"></script>
<script type="text/javascript">
	var path='<%=basePath%>';
	function broadcast(){
		var id=$("#id").val();
		var msg=$("#msg").val();
		$.ajax({
			url:path+'msg/sentFriends?id='+id+"&msg="+msg,
			type:"post",
			dataType:"json",
			success:function(data){
				alert("发送成功");
			}
		});
	}
</script>
</head>
<body>
	选择好友
	<select name="id" id="id">
			<option value="1">张三</option>
			<option value="2">李四</option>
			<option value="3">王五</option>
		</select><br>
	发送消息
	<textarea style="width:100%;height:300px;" id="msg" ></textarea>
	<input type="button" value="发送" onclick="broadcast()">
</body>
</html>
