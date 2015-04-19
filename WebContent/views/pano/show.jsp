<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<jsp:useBean id="jt" class="com.Picloud.utils.JspUtil" scope="page" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${PLUGIN}/pano/css/pano.css" />
<link rel="stylesheet" href="${RESOURCES}/font/css/font-awesome.min.css" />
<style>

</style>
</head>
<body>
  <div class="bg-wrapper">
    <div id="container"  data-key="${panoImage.key}" ></div>
    <div id="url_base" data-url = "${IP}${ROOT}"></div>
    		<h2 id="title" style="width:100%;position:absolute;top:20px;text-align:center">
    					
    		</h2>
		 <div class="toolbar">
		 	<div class="button-group toolbar-left">
		 	<a href="#" class="toolbar-button previous "></a>
		 	</div>
		 	<div class="button-group toolbar-center">
			 	<a href="#"  class="directionButton toolbar-button left" data-dir="left"></a>
			 	<a href="#"  class="directionButton toolbar-button right" data-dir="right"></a>
			 	<a href="#"  class="directionButton toolbar-button top" data-dir="top"></a>
			 	<a href="#"  class="directionButton toolbar-button bottom" data-dir="bottom"></a>
			 	<a href="#"  class="directionButton toolbar-button forward" data-dir="forward"></a>
			 	<a href="#"  class="directionButton toolbar-button backward" data-dir="backward"></a>
		 </div>
		 <div class="button-group toolbar-right">
		 <a href="#" class="toolbar-button hide "></a>
		 	<a href="#" class="toolbar-button next "></a>
		 </div>
		 </div>
		   	<c:if test="${not empty panoImage.mus_path}">
  			<!--  	<audio src="${IP}${ROOT}/pano/readMusic?path=${jt.urlEncode(panoImage.mus_path)}" loop autoplay>
							Your browser does not support the audio element.
							</audio>-->	
							
							 <embed style="position:absolute;top:0;right:10px;"autoplay="true" src="${IP}${ROOT}/pano/readMusic?path=${jt.urlEncode(panoImage.mus_path)}"/>       
  	</c:if>
  <div id="s" style="width:100%; color:#000;position:absolute;bottom:20px; white-space:nowrap; overflow:hidden; height:20px;">
    <div id="noticeList" style="position:absolute; z-index:1000;top:0; height:20px;"></div>
</div>
  </div>


  <script type="text/javascript" src="${RESOURCES}/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="${PLUGIN}/pano/js/three.min.js"></script>
  <script type="text/javascript" src="${PLUGIN }/pano/js/pano.js"></script>
  <script type="text/javascript">

  </script>
</html>