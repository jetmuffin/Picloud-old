<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${RESOURCES}/css/common.css" />
<style>
.middle-box{
max-width: 400px;
z-index: 100;
margin: 0 auto;
padding-top: 40px;
text-align:center;
margin-top:30px;
}
.middle-box h1{
	font-size:120px;
}
.font-bold{
	font-weight:bold;
	padding:10px;
}
</style>
</head>
<body>

<div class="middle-box text-center animated fadeInDown">
        <h1>出错了</h1>
<h3 class="font-bold">错误信息:</h3>
        <div class="error-desc">
           <h3>${e.message}</h3>
        </div>
    </div>
</body>
</html>