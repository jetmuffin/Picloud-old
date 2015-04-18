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
   				<div class="row">
					<div class="col-md-12">
						<div class="ibox ">
							<div class="ibox-title">
								<h5>所有应用</h5>
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
													<dd>项目名称</dd>
													<dt>项目描述:</dt>
													<dd>项目描述</dd>
												</dl>
											</div>
											<div class="col-md-7">
												<dl class="dl-horizontal">
													<dt>项目创建时间:</dt>
													<dd>time</dd>
													<dt>更新时间:</dt>
													<dd>time</dd>
												</dl>
											</div>
											<div class="col-lg-12">
												<dl class="dl-horizontal">
													<dt>完成度:</dt>
													<dd>
														<div class="progress progress-striped active m-b-sm">
															<div style="width: 60%;" class="progress-bar"></div>
														</div>
														<small>Project completed in <strong>60%</strong>.
															Remaining close the project, sign a contract and invoice.
														</small>
													</dd>
												</dl>
											</div>
										</div>
									</div>
								</div>
								<div role="tabpanel">
								  <!-- Nav tabs -->
								  <ul class="nav nav-tabs" role="tablist">
								    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">项目信息</a></li>
								    <li role="presentation"><a href="#music" aria-controls="profile" role="tab" data-toggle="tab">背景音乐</a></li>
								    <li role="presentation"><a href="#scene" aria-controls="messages" role="tab" data-toggle="tab">场景编辑</a></li>
								    <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
								  </ul>
								
								  <!-- Tab panes -->
								  <div class="tab-content">
								    <div role="tabpanel" class="tab-pane active" id="home">
											<form action="${ROOT}/pano/info" class="form-horizontal pano-form" method="post">
												<div class="form-group">
													<label class="col-sm-2 control-label">项目名称</label>

													<div class="col-sm-10">
													 <input type="text" class="form-control jet-input" id="panoName"/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">项目介绍</label>

													<div class="col-sm-10">
													 <input type="text" class="form-control jet-input" id="panoDesc"/>
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
											<form action="${ROOT}/pano/music" class="form-horizontal pano-form" method="post">
												<div class="form-group">
													<label class="col-sm-2 control-label">选择音乐</label>

													<div class="col-sm-10">
													 <input type="file" class="form-control jet-input" id="panoMusic"/>
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
								    <div role="tabpanel" class="tab-pane" id="scene">...</div>
								    <div role="tabpanel" class="tab-pane" id="settings">...</div>
								  </div>
								
								</div>
							</div>
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