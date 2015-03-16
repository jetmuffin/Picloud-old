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
<link rel="stylesheet" href="${RESOURCES}/css/appcenter.css" />
<link rel="stylesheet" href="${PLUGIN}/jcrop/jquery.Jcrop.min.css" />
<link rel="stylesheet" href="${PLUGIN}/chosen/chosen.css" />
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
					<div class="col-md-4">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>选择图片</h5>
							</div>
							<div class="ibox-content" style="display: block;">
								<div class="picture-overview">
										<div class="btn-group">
											<a
												class="btn btn-default <c:if test="${action eq '缩放'}">btn-active</c:if>"
												type="button"
												href="${ROOT}/process/scale">压缩</a> <a
												class="btn btn-default <c:if test="${action eq '裁剪'}">btn-active</c:if>" type="button"
												href="${ROOT}/process/crop"
												>裁剪</a> <a
												class="btn btn-default <c:if test="${action eq '图片水印'}">btn-active</c:if>" type="button"
												href="${ROOT}/process/watermark">图片水印</a>
												 <a
												class="btn btn-default <c:if test="${action eq '文字水印'}">btn-active</c:if>" type="button"
												href="${ROOT}/process/textmark">文字水印</a>
										</div>
								</div>
								<div class="form-group">
									<label class="control-label">选择空间</label>
										<select class="form-control  jet-input" name="account"
											<c:if test="${not empty activeSpace.key}">value='${activeSpace}'</c:if> id='spaces_select'>
											<c:forEach items="${spaces}" var="space">
													<option <c:if test="${space.key eq activeSpace.key}">selected="selected"</c:if>>${space.name}</option>
											</c:forEach>
										</select>
								</div>
								<div class="form-group">
									<label class="control-label">选择图片</label> <select
										data-placeholder="请选择图片"
										class="chosen-select form-control jet-input" tabindex="-1"
										id='pictures_select' data-default="{$picture.name}">
									</select>
								</div>
								<div class="form-group">
									<label class="control-label">图片预览</label>
									<div class="row overview-pic">
										<present name="picture['name']"> <img
											id="picture_overview" class="col-xs-12"
											src="__TOMCAT__/ReadImage?image={$picture.name}&uid={:session('uid')}">
										</present>

									</div>
								</div>
							</div>
						</div>
					</div>
				
				<div class="col-md-8">
					<div class="ibox ">
						<div class="ibox-title">
							<h5>图片裁剪</h5>
							<div class="ibox-tools">
								<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
									class="close-link"><i class="fa fa-times"></i></a>
							</div>
						</div>
						<div class="ibox-content" style="display: block;">
							<div class="process-control">
								<form class="form-horizontal" action="" method="post"
									id="jcrop-form">
									<div class="form-group">
										<label class="col-sm-2 control-label">图片预览</label>
										<div class="col-sm-10">
											<img src="" id="jcrop" alt="[裁剪图片]" style='display: none' />
											<p class="col-sm-10" id='jcrop-word'>请先选择图片</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">X1</label>
										<div class="col-sm-4">
											<input type="text" id="offset-x1"
												class="jet-input form-control">
										</div>
										<label class="col-sm-2 control-label">Y1</label>
										<div class="col-sm-4">
											<input type="text" id="offset-y1"
												class="jet-input form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">X2</label>
										<div class="col-sm-4">
											<input type="text" id="offset-x2"
												class="jet-input form-control">
										</div>
										<label class="col-sm-2 control-label">Y2</label>
										<div class="col-sm-4">
											<input type="text" id="offset-y2"
												class="jet-input form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Width</label>
										<div class="col-sm-4">
											<input type="text" id="jcrop-width"
												class="jet-input form-control">
										</div>
										<label class="col-sm-2 control-label">Height</label>
										<div class="col-sm-4">
											<input type="text" id="jcrop-height"
												class="jet-input form-control">
										</div>

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">图片链接</label>
										<p class="col-sm-10 crop-link">请先选择图片</p>
									</div>
									<div class="button-group pull-right">
										<input class="btn btn-primary jet-button" type="submit"
											id="jcrop-submit" value="保存"> <a
											class="btn btn-default" id="jcrop-reset">取消</a>
									</div>
								</form>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<!-- ibox-end -->
					<div id="user_id" style="display: none">{:session('uid')}</div>
				</div>
			</div>
			</block>
		</div>
		<jsp:include page="../common/footer.jsp" />
	</div>
	</div>
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="${PLUGIN}/chosen/chosen.jquery.js"></script>
		<script type="text/javascript" src="${PLUGIN}/jcrop/jquery.Jcrop.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
<script type="text/javascript" src="${RESOURCES}/js/crop.js"></script>
</body>
</html>