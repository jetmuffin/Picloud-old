<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet"
	href="${RESOURCES }/font/css/font-awesome.min.css" />
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
	<present name="register_msg_box">
	<div class="login-alert">
		<!--<strong>{$register_msg}</strong>  -->
	</div>
	</present>
	<div class="middle-box">
		<sf:form method="post" modelAttribute="user" action="register">
			<div class="form-group">
				<sf:input path="uid" placeholder="请输入用户名/邮箱"
					class="form-control login-input" />
				<sf:errors path="uid" class="login-error" />
			</div>
			<div class="form-group">
				<sf:password path="password" class="form-control login-input"
					placeholder="请输入密码" />
				<sf:errors path="password" class="login-error" />
			</div>
			<div class="form-group">
				<sf:input path="nickname" class="form-control login-input"
					placeholder="请输入昵称" />
				<sf:errors path="nickname" class="login-error" />
			</div>
			<div class="form-group">
				<button type="submit" class="form-control btn btn-primary login-btn"
					name="submit" title="submit" id="submit">注册</button>
			</div>
			<div class="form-group">
				<a class="btn btn-default form-control" href="${ROOT}/login.jsp">返回登录</a>
			</div>
		</sf:form>
	</div>
	<div class="copyright">
		<small>Developed by JetMuffin, Sloric, InnerAc, GMpj </small> <br />©
		<small>2014 copyright </small>
	</div>
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
</body>
</html>