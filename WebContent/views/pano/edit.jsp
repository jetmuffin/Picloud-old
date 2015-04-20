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
<link rel="stylesheet" href="${RESOURCES}/font/css/font-awesome.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${PLUGIN}/chosen/chosen.css" />
<link rel="stylesheet" href="${PLUGIN}/step/jquery.steps.css" />
<link rel="stylesheet" href="${RESOURCES}/css/common.css" />
<link rel="stylesheet" href="${RESOURCES}/css/appcenter.css" />
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp" />
		<div class="page-wrapper">
			<jsp:include page="../common/navbar.jsp" />
			<jsp:include page="../common/breadcrumb.jsp" />
			<div class="wrapper wrapper-content animated fadeInDown">
  <block name="content">
  				<c:if test="${not empty editMsg}">
	    			<div class="alert alert-info alert-dismissable">
						<button aria-hidden="true" data-dismiss="alert" class="close"
							type="button">×</button>
								${editMsg}
					</div>					
  				</c:if>
   		<div class="row">
					<div class="col-md-12">
						<div class="ibox ">
							<div class="ibox-title">
								<h5>全景项目编辑</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div class="project row">
									<div class="col-lg-12">
										<h2>项目名称</h2>
										<div class="row">
											<div class="col-md-5">
												<dl class="dl-horizontal">
													<dt>项目名称:</dt>
													<dd>${panoImage.name}</dd>

												</dl>
											</div>
											<div class="col-md-7">
												<dl class="dl-horizontal">
													<dt>项目创建时间:</dt>
													<dd>${jt.getStrTime(panoImage.createTime) }</dd>
												</dl>
											</div>
											<div class="col-md-12">
											<dl class="dl-horizontal">
											<dt>项目描述:</dt>
													<dd>${panoImage.info}</dd>
											</dl>
											</div>
											<div class="col-lg-12">
												<dl class="dl-horizontal">
													<dt>完成度:</dt>
													<dd>
														<c:set value="0" var="finish" />
														<c:if test="${not empty panoImage.mus_path}">
															<c:set value="${finish+25}" var="finish"/>
														</c:if>
														<c:if test="${not empty panoImage.scene}">
															<c:set value="${finish+25}" var="finish"/>
														</c:if>						
															<c:if test="${not empty panoImage.info}">
															<c:set value="${finish+25}" var="finish"/>
														</c:if>			
															<c:if test="${not empty panoImage.name}">
															<c:set value="${finish+25}" var="finish"/>
														</c:if>																																						
														<div class="progress progress-striped active m-b-sm">
															<div style="width: ${finish}%;" class="progress-bar"></div>
														</div>
														<small>全景完成度 <strong>${finish}%</strong>.
															添加全部内容,可以使全景项目展示效果达到最佳!
														</small>
													</dd>
												</dl>
											</div>
											<div class="col-lg-12">
												<dl class="dl-horizontal">
													<dt>完成度:</dt>
													<dd><a href="${ROOT}/pano/${panoImage.key}" class="btn jet-button btn-primary">立即查看</a></dd>											
											</div>
										</div>
									</div>
								</div>
								<div role="tabpanel">
								  <!-- Nav tabs -->
								  <ul class="nav nav-tabs" role="tablist">
								    <li role="presentation" class="active"><a href="#info" aria-controls="info" role="tab" data-toggle="tab">项目信息</a></li>
								    <li role="presentation"><a href="#music" aria-controls="profile" role="tab" data-toggle="tab">背景音乐</a></li>
								    <li role="presentation"><a href="#scene" aria-controls="messages" role="tab" data-toggle="tab">场景编辑</a></li>
								    <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">设置</a></li>
								  </ul>
								
								  <!-- Tab panes -->
								  <div class="tab-content">
								    <div role="tabpanel" class="tab-pane active" id="info">
											<form action="${ROOT}/pano/${panoImage.key}/info" class="form-horizontal pano-form" method="post">
												<div class="form-group">
													<label class="col-sm-2 control-label">项目名称</label>
													<div class="col-sm-10">
													 <input type="text" value="${panoImage.name}" class="form-control jet-input" name="panoName" id="panoName"/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">项目介绍</label>
													<div class="col-sm-10">
													 <textarea type="text" value="" class="form-control jet-input" name="panoDesc" id="panoDesc">${panoImage.info}</textarea>
													</div>
												</div>												
												<div class="hr-line-dashed"></div>
												<div class="form-group">
													<div class="col-sm-4 col-sm-offset-2">
														<button class="btn btn-primary jet-button" type="submit">保存</button>
													</div>
												</div>
											</form>
								    </div>
								    <div role="tabpanel" class="tab-pane" id="music">
											<form action="${ROOT}/pano/${panoImage.key}/music" enctype="multipart/form-data" class="form-horizontal pano-form" method="post">
												<div class="form-group">
													<label class="col-sm-2 control-label" >背景音乐</label>
													<div class="col-sm-10">
													<label class="col-sm-2 control-label" style="white-space: nowrap;">${panoImage.mus_path}</label>
														
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">修改音乐</label>
													<div class="col-sm-10">
													 <input type="file" class="form-control jet-input" name="panoMusic" id="panoMusic"/>
													</div>
												</div>
												<div class="hr-line-dashed"></div>
												<div class="form-group">
													<div class="col-sm-4 col-sm-offset-2">
														<button class="btn btn-primary jet-button" type="submit">保存</button>
													</div>
												</div>
											</form>
										</div>
								    <div role="tabpanel" class="tab-pane" id="scene">
											
											<div class="row scene-box">

												<div class="col-lg-12">
												<c:forEach items="${panoImage.scene}" var="scene" varStatus="status">
													
													<div class="file-box">
														<div class="file">
															<a href="#"> <span class="corner"></span>
																<div class="icon">
																	<img src="${ROOT}/pano/readFile?path=${jt.urlEncode(scene.thumb)}" alt="${scene.name}" />
																</div>
																<div class="file-name">
																	${scene.name} <br> <small>${scene.desc}</small>
																</div>
															</a>
														</div>
													</div>													
												</c:forEach>												
														<button class="btn btn-primary jet-button"  data-toggle="modal" data-target="#sceneModal" >添加场景</button>
												</div>
											</div>
										</div>
								    <div role="tabpanel" class="tab-pane" id="settings">
										<form action="${ROOT}/pano/${panoImage.key}/music" enctype="multipart/form-data" class="form-horizontal pano-form" method="post">
												<div class="form-group">
													<label class="col-sm-2 control-label">音乐自动播放</label>
													<div class="col-sm-10">
														<input class="form-control jet-input" name="musicOn" type="checkbox" checked value="1" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">播放介绍文字</label>
													<div class="col-sm-10">
													 <input class="form-control jet-input" name="textOn" type="checkbox" checked value="1" />
													</div>
												</div>
												<div class="hr-line-dashed"></div>
												<div class="form-group">
													<div class="col-sm-4 col-sm-offset-2">
														<button class="btn btn-primary jet-button" type="submit">保存</button>
													</div>
												</div>
											</form>								    
								    </div>
								  </div>
								
								</div>
							</div>
						</div>
					</div>
				</div>
					<!-- Modal -->
				<div class="modal" id="sceneModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog hd-dialog  ">
						<div class="modal-content bounceIn">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">取消</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">添加场景</h4>
							</div>
							<div class="modal-body">
								<form action="${ROOT}/pano/${panoImage.key}/scene" method="POST"
									enctype="multipart/form-data">
										
									<div class="form-group">
										<label class="control-label">场景名称</label> <input
											class="form-control jet-input" type="text" name="sceneName" />
									</div>									
									<div class="form-group">
										<label class="control-label">鱼眼图片</label> <input
											class="form-control jet-input" type="file" name="sceneImage" />
									</div>
									<div class="form-group">
										<label class="control-label">场景介绍</label> <textarea
											class="form-control jet-input" type="text" name="sceneDesc" ></textarea>
									</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">取消</button>
								<button type="submit" class="btn jet-button btn-primary">确认</button>
							</div>
						</form>
						</div>
					</div>
				</div>
<div id="url_base" style="display: none">${IP}${ROOT}</div>
  </block>
		</div>
		<jsp:include page="../common/footer.jsp" />
	</div>
	</div>
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="${PLUGIN}/chosen/chosen.jquery.js"></script>
		<script type="text/javascript" src="${PLUGIN}/step/jquery.steps.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
	<script>
	$(document).ready(function(){
		$("#form").steps({
	         bodyTag: "fieldset"
		 });
	});

	</script>
</body>
</html>