<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${RESOURCES }/font/css/font-awesome.min.css"  />
<link rel="stylesheet" href="${RESOURCES }/css/bootstrap.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/login.css" />
</head>
<body>
<div class="logo">
		<h1 class="logo-name">
			<div class="logo-icon">
				<i class="fa fa-cloud"></i>
			</div>
			Picloud
		</h1>
	</div>
<!-- 	<div class="slogan">
		<p>提供优质的图片存储云服务</p>
	</div> -->
	<present name="login_msg_box">
		<div class="login-alert">
		  <strong>${LOGIN_MSG}</strong>	
		</div>
	</present>
	<div class="middle-box">
		<form method="post" action="user/login">
			<div class="form-group">
				<input type="text" class="form-control login-input" name="uid" title="uid" id="uid" placeholder="请输入用户名/邮箱" >
			</div>
			<div class="form-group">
				<input type="password" class="form-control login-input" name="password" title="pwd" id="pwd"  placeholder="请输入密码">
			</div>
			<div class="form-group">
				<button type="submit" class="form-control btn btn-primary login-btn" name="submit" title="submit" id="submit" >登录</button>
			</div>
			<div class="form-group">
				<a class="btn btn-default form-control" href="user/register">注册</a>
			</div>
		</form>
	</div>
	<div class="copyright">
		<small>Developed by JetMuffin, Sloric, InnerAc, Goodpj </small>
		<br/>© <small>2014 copyright </small>
	</div>
<script type="text/javascript" src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
</body>
</html>