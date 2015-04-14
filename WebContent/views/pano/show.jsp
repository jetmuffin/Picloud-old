<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${PLUGIN}/pano/css/pano.css" />
<link rel="stylesheet" href="${RESOURCES}/font/css/font-awesome.min.css" />
</head>
<body>
  <div class="bg-wrapper">
    <div id="container"  data-key="${panoImage.key}" ></div>
    <div id="url_base" data-url = "${IP}${ROOT}"></div>
		 <div class="toolbar">
		 	<div class="button-group toolbar-left">
		 	<a href="#" class="toolbar-button previous "></a>
		 		<a href="#" class="toolbar-button preview"></a>
		 		<a href="#" class="toolbar-button music-off "></a>
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
  </div>
  <script type="text/javascript" src="${RESOURCES}/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="${PLUGIN}/pano/js/three.min.js"></script>
  <script type="text/javascript" src="${PLUGIN }/pano/js/pano.js"></script>
  <script type="text/javascript">

  </script>
</html>