<%@ page isELIgnored="false" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线聊天</title>
<link rel="stylesheet" type="text/css" href="<%=basePath2%>resources/talk/css/chat.css" />
<script type="text/javascript" src="<%=basePath2%>resources/talk/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath2%>resources/talk/js/chat.js"></script>
<script type="text/javascript" src="<%=basePath2%>resources/jquery.js"></script>
<style>
textarea {
	height: 300px;
	width: 100%;
	resize: none;
	outline: none;
}

input[type=button] {
	float: right;
	margin: 5px;
	width: 50px;
	height: 35px;
	border: none;
	color: white;
	font-weight: bold;
	outline: none;
}

.clear {
	background: red;
}

.send {
	background: green;
}

.clear:active {
	background: yellow;
}

.send:active {
	background: yellow;
}

.msg {
	width: 100%;
	height: 25px;
	outline: none;
}

#content {
	border: 1px solid gray;
	width: 100%;
	height: 400px;
	overflow-y: scroll;
}

.from {
	background-color: green;
	width: 80%;
	border-radius: 10px;
	height: 30px;
	line-height: 30px;
	margin: 5px;
	float: left;
	color: white;
	padding: 5px;
	font-size: 22px;
}

.to {
	background-color: gray;
	width: 80%;
	border-radius: 10px;
	height: 30px;
	line-height: 30px;
	margin: 5px;
	float: right;
	color: white;
	padding: 5px;
	font-size: 22px;
}

.name {
	color: gray;
	font-size: 12px;
}

.tmsg_text {
	color: white;
	background-color: rgb(47, 47, 47);
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
}

.fmsg_text {
	color: white;
	background-color: rgb(66, 138, 140);
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
}

.sfmsg_text {
	color: white;
	background-color: rgb(148, 16, 16);
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
}

.tmsg {
	clear: both;
	float: right;
	width: 80%;
	text-align: right;
}

.fmsg {
	clear: both;
	float: left;
	width: 80%;
}
</style>
<script>
		var html;
		function app_del(obj, id) {
			html=id;
		}
		var path = '<%=basePath%>';
		var uid=${uid eq null?-1:uid};
		if(uid==-1){
			location.href="<%=basePath2%>";
		}
		var from=uid;
		var fromName='${name}';
		var to=uid==1?2:1;
		var websocket;
		if ('WebSocket' in window) {
			websocket = new WebSocket("ws://" + path + "/ws?uid="+uid);
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://" + path + "/ws"+uid);
		} else {
			websocket = new SockJS("http://" + path + "/ws/sockjs"+uid);
		}
		websocket.onopen = function(event) {
			console.log("WebSocket:已连接");
			console.log(event);
		};
		websocket.onmessage = function(event) {
			var data=JSON.parse(event.data);
			console.log("WebSocket:收到一条消息",data);
			var textCss=data.from==-1?"sfmsg_text":"fmsg_text";
			$("#content").append("<div class='fmsg'><label class='name'>"+data.fromName+"&nbsp;"+data.date+"</label><div class='"+textCss+"'>"+data.text+"</div></div>");
			scrollToBottom();
		};
		websocket.onerror = function(event) {
			console.log("WebSocket:发生错误 ");
			console.log(event);
		};
		websocket.onclose = function(event) {
			console.log("WebSocket:已关闭");
			console.log(event);
		}
			function sendMsg(){
				var v=$("#msg").val();
				if(v==""){
					return;
				}else{
					var data={};
					data["from"]=from;
					data["fromName"]=fromName;
					var to=html;
					data["to"]=to;
					data["text"]=v;
					websocket.send(JSON.stringify(data));
					$("#content").append("<div class='tmsg'><label class='name'>我&nbsp;"+new Date().Format("yyyy-MM-dd hh:mm:ss")+"</label><div class='tmsg_text'>"+data.text+"</div></div>");
					scrollToBottom();
					$("#msg").val("");
				}
			}
			
			function scrollToBottom(){
				var div = document.getElementById('content');
				div.scrollTop = div.scrollHeight;
			}
			
			Date.prototype.Format = function (fmt) { //author: meizz 
			    var o = {
			        "M+": this.getMonth() + 1, //月份 
			        "d+": this.getDate(), //日 
			        "h+": this.getHours(), //小时 
			        "m+": this.getMinutes(), //分 
			        "s+": this.getSeconds(), //秒 
			        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
			        "S": this.getMilliseconds() //毫秒 
			    };
			    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			    for (var k in o)
			    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			    return fmt;
			}
			
			function send(event){
				var code;
				 if(window.event){
					 code = window.event.keyCode; // IE
				 }else{
					 code = e.which; // Firefox
				 }
				if(code==13){ 
					sendMsg();            
				}
			}
			
			function clearAll(){
				$("#content").empty();
			}
</script>
</head>
<body class="keBody">
<div class="kePublic">
<!--效果html开始-->
    <div class="content">
        <div class="chatBox">
            <div class="chatLeft">
                <div class="chat01">
                    <div class="chat01_title">
                        <ul class="talkTo">
                            <li><a href="javascript:;"></a></li>
                        </ul>
                        <a class="close_btn" href="javascript:;"></a>
                    </div>
                    <div class="chat01_content" id="content"></div>
                </div>
                <div class="chat02">
                    <div class="chat02_title">
                        <a class="chat02_title_btn ctb01" href="javascript:;"></a>
                        <div class="wl_faces_box">
                            <div class="wl_faces_content">
                                <div class="title">
                                    <ul>
                                        <li class="title_name">常用表情</li><li class="wl_faces_close"><span>&nbsp;</span></li></ul>
                                </div>
                                <div class="wl_faces_main">
                                    <ul>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_01.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_02.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_03.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_04.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_05.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_06.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_07.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_08.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_09.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_10.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_11.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_12.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_13.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_14.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_15.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_16.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_17.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_18.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_19.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_20.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_21.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_22.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_23.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_24.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_25.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_26.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_27.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_28.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_29.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_30.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_31.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_32.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_33.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_34.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_35.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_36.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_37.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_38.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_39.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_40.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_41.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_42.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_43.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_44.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_45.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_46.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_47.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_48.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_49.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_50.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_51.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_52.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_53.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_54.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_55.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_56.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_57.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="<%=basePath2%>resources/talk/img/emo_58.gif" /></a></li><li><a href="javascript:;">
                                                <img src="<%=basePath2%>resources/talk/img/emo_59.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="<%=basePath2%>resources/talk/img/emo_60.gif" /></a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="wlf_icon">
                            </div>
                        </div>
                    </div>
                    <div class="chat02_content">
						<div>
							<textarea placeholder="请输入要发送的信息" id="msg" class="msg" onkeydown="send(event)" id="textarea"></textarea>
							<input type="button" value="发送" class="send" onclick="sendMsg()" >
							<input type="button" value="清空" class="clear" onclick="clearAll()">
						</div>
                    </div>
                </div>
            </div>
            <div class="chatRight">
                <div class="chat03">
                    <div class="chat03_title">
                        <label class="chat03_title_t">
                            成员列表</label>
                    </div>
                    <div class="chat03_content">
                        <ul>
							<c:forEach items="${sessionScope.userList}" var="user">
							<li onclick="app_del(this,${user.id})">
                                <label class="online">
                                </label>
                                <a href="javascript:;">
                                    <img src="<%=basePath2%>resources/talk/img/head/2013.jpg">
                                </a>
                                <a href="javascript:;" class="chat03_name" >${user.admName}</a>
                                <a href="javascript:;">ID:${user.id}</a>
                            </li>
							</c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div style="clear: both;">
            </div>
        </div>
    </div>
<!--效果html结束-->
</div>
<div class="keBottom">
<p class="keTxtP">＊尊重他人劳动成果，转载请自觉注明出处！仅供学习交流，请勿用于商业用途。
<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=4609ote" class="button red" title="点击链接加入群【IT技术服务群】：">加入QQ交流群</a></p>
</div>
</body>
</html>
