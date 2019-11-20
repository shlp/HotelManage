<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
 <meta name="Author" content="实训1组">
  <meta name="Keywords" content="酒店信息管理系统">
  <meta name="Description" content="酒店信息管理系统">
  <title>酒店信息管理系统登录页面</title>
  <link href="../resources/home/css/index.css" type="text/css" rel="Stylesheet" />
  <link href="../resources/home/css/login.css" type="text/css" rel="Stylesheet" />
	 <link href="../resources/home/jslib/hplus/css/bootstrap.min.css" rel="stylesheet">
	 <link href="../resources/home/jslib/hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
	 <link href="../resources/home/jslib/hplus/css/animate.min.css" rel="stylesheet">
	 <link href="../resources/home/jslib/hplus/css/style.min.css" rel="stylesheet">
	 <link href="../resources/home/jslib/hplus/css/login.min.css" rel="stylesheet">

	 <link href="../resources/home/css/qy-style.css" rel="stylesheet">

	 <!-- jQuery -->
	 <script src="../resources/home/jslib/hplus/js/jquery.min.js"></script>
	 <script src="../resources/home/jslib/hplus/js/jquery-ui-1.10.4.min.js"></script>

	 <script src="../resources/home/jslib/hplus/js/plugins/validate/jquery.validate.min.js"></script>

	 <script src="../resources/home/jslib/hplus/js/plugins/validate/messages_zh.min.js"></script>


	 <script src="../resources/home/jslib/CommonValue.js"></script>
 </head>
 <style>

	 body.signin {
		 background:url(../resources/home/images/loginq.jpg) no-repeat center fixed;

	 }
	 .signinPanal{
		 width: 1000px;
		 margin: 10% auto 0;
		 height:400px;
		 position: relative;
	 }
	 .signup-footer {
		 position: absolute;
		 border-top: solid 2px rgba(255,255,255,0.3);
		 top:105%;
		 width: 1000px;
		 padding-top: 15px;
	 }
	 .signinPanal .uname {
		 background: #fff url(../resources/home/images/user.png) no-repeat 95% center;
	 }
	 .signinPanal .pword {
		 background: #fff url(../resources/home/images/locked.png) no-repeat 95% center;
	 }
	 .form-control{
		 display:inline;
	     color:#333;
	 }
 </style>
 <body class="signin">
 <div style="position:absolute;width: 100%;height: 1000px;background:#c6c6ca70;">
 	<div class="signinPanal">

	 <div style="position: absolute;  width: 306px; height: 313px; top: 0; left:65%; background: rgba(255,255,255,0.2);    border: 1px solid rgba(255,255,255,0.3) ">

			 <p class="m-t-md" id="title" style="position: absolute; width: 237px; height: 26px; z-index: 1; top: 8%; left: 13%;">登录</p>
			 <div style="position: absolute; width: 237px; height: 26px; z-index: 1; top: 28%; left: 13%;">
				 <input type="text" class="form-control uname" placeholder="用户名" id="name" name="uname" style="color:#030303;"/>
			 </div>
			 <div style="position: absolute; width: 237px; height: 26px; z-index: 2; top: 49%; left: 13%;">
				 <input type="password" class="form-control pword m-b" placeholder="密码" id="password" name="upwd" type="password"  style="color:#030303;"/>
			 </div>



			 <div class="codes" style="position: absolute; width: 237px; height: 26px; z-index: 3; top: 65%; left: 13%;">
				 <input id="vcode" maxlength="4" type="text" class="form-control" style="width:120px;"placeholder="验证码"/>
				 <img id="cpacha-img" src="../system/get_cpacha?vl=4&w=173&h=33&type=accountLoginCpacha" onclick="changeVcode()" class="code" style="cursor:pointer;width:100px;height:34px;float:right;"/>
			 </div>


			 <div style="position: absolute; width:237px; height: 40px; z-index: 4; top: 80%; left: 13%; ">
				 <button type="submit" id="bt_login" class="btn btn-primary btn-block">登　录</button>
			 </div>

	 </div>


	 <div class="reg" style="position:absolute;top:79%;left:88%;">
		 <a href="reg">立即注册 &gt;&gt;</a>
	 </div>
		<%@include file="../common/footer.jsp"%>
	 <div class="signup-footer">
		 <div class="pull-left" style="color:#fff;">
			 © 2020 All Rights Reserved.
		 </div>
	 </div>
 </div>
 </div>

 <script src="../resources/home/js/jquery-1.11.3.js"></script>
<script>
function changeVcode(){
	$("#cpacha-img").attr("src",'../system/get_cpacha?vl=4&w=173&h=33&type=accountLoginCpacha&t=' + new Date().getTime());
}
$(document).ready(function(){
	$("#bt_login").click(function(){
		var name = $("#name").val();
		var password = $("#password").val();
		var vcode = $("#vcode").val();
		if(name == '' || password == '' || vcode == ''){
			alert('请填写完成信息再提交!');
			return;
		}
		$.ajax({
			url:'login',
			type:'post',
			dataType:'json',
			data:{name:name,password:password,vcode:vcode},
			success:function(data){
				if(data.type == 'success'){
					window.location.href = 'index';
				}else{
					alert(data.msg)
					changeVcode();
				}
			}
		});
	})
});
</script>
 </body>
</html>
