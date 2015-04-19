<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${RESOURCES}/font/css/font-awesome.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/common.css" />
<link rel="stylesheet" href="${RESOURCES}/css/picserver.css" />
<link rel="stylesheet" href="${PLUGIN}/dropzone/css/dropzone.css" />
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp" />
		<div class="page-wrapper">
			<jsp:include page="../common/navbar.jsp" />
			<jsp:include page="../common/breadcrumb.jsp" />
			<div class="wrapper wrapper-content animated fadeInDown">
				<block name="content">
				<div class="row">
					<div class="col-md-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									上传图片<small>- ${space.name}<c:if test="${spaces!=null}">快速上传</c:if></small>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<form id="my-awesome-dropzone" class="dropzone dz-clickable"
									action="#">
									<div class="dropzone-previews"></div>
									<div class="dropzone-group">
										<button type="submit" class="btn btn-primary pull-right">提交</button>
										<label for="" style="float:left">空间:</label>
										<c:if test="${spaces!=null}">
											<select class="form-control jet-input" name="space">
												<c:forEach items="${spaces}" var="space">
												<option>${space.name}</option>
												</c:forEach>
											</select>
										</c:if>
									</div>
									<div class="dz-default dz-message">
										<span>Drop files here to upload</span>
									</div>
								</form>
								<div class=" text-right">
									<small>点击以上灰色区域上传图片，或者直接将图片拖至该区域内</small>
								</div>
							</div>
						</div>
					</div>
				</div>
				</block>
			</div>
			<jsp:include page="../common/footer.jsp" />
		</div>
	</div>
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/picserver.js"></script>
	<script type="text/javascript" src="${PLUGIN }/dropzone/dropzone.min.js"></script>
</body>
</html>