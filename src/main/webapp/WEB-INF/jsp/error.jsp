<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<span>${msg}</span><span id="sec"></span><span>秒后返回登录页面</span>
	<script>
		//设置倒数秒数,使用timeout比较合适，在计时开始前就需要显示秒数
 		var t = 5;
		function showTime(){
			document.getElementById("sec").innerHTML = t;
			if(t == 0){
				location.href = "/common-platform/login.html";
			}
			t--;	//当显示到0秒时才开始跳转
			setTimeout("showTime()",1000);
		}
		showTime(); 
	</script>
</body>
</html>