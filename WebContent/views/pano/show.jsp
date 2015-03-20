<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>

  <style type="text/css">
      body {
        background-color: #000000;
        margin: 0px;
        overflow: hidden;
      }
  </style>
</head>
<body>
  <div class="bg-wrapper">
    <div id="container"  data-key="${panoImage.key}" ></div>
    <div id="url_base" data-url = "${IP}${ROOT}"></div>
    </if>
  </div>
  <script type="text/javascript" src="${RESOURCES}/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="${PLUGIN}/pano/js/three.min.js"></script>
  <script type="text/javascript" src="${PLUGIN }/pano/js/pano.js"></script>
  <script type="text/javascript">

  </script>
</html>