<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
</head>
<body>
  <div class="bg-wrapper">
  <!--    <if condition="($picture['name'] eq 'example.jpg')">
      <div class="good" data-uid="{:session('uid')}" data-url="__PLUGIN__/3deye/images/" data-pic="{$picture.name}"  data-num='51' data-type="png"></div>
    <else />    -->
    <div class="good" data-name="${threeDImage.name}" data-num='${threeDImage.number}' data-type="png"></div>
  </div>
  <div id="url_base" data-url='${IP}${ROOT}'></div>
  <script type="text/javascript" src="${RESOURCES}/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="${PLUGIN}/3deye/3deye.min.js"></script>
  <script type="text/javascript">
	var url_base = $('#url_base').attr('data-url');
	var name = $('.good').attr('data-name');
	var url_3d = url_base + '/threeD/read?name=' + name + '&file=';
	var num = $(".good").attr('data-num');
    var type = $(".good").attr('data-type');
    $(".good").vc3dEye({
     imagePath:url_3d ,
      totalImages:num,
      imageExtension:type
    });
  </script>
</html>