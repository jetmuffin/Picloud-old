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
								<h2>制作全景项目</h2>
								<p>注意:上传图片需要为标准的鱼眼图片，否则无法得到效果完美的全景图片。</p>
								<form id="form" action="#" class="wizard-big">
									<h1>填写全景信息</h1>
									<fieldset>
										<h2>项目信息</h2>
										<div class="row">
											<div class="col-lg-8">
												<div class="form-group">
													<label>项目名称</label> <input id="panoName"
														name="panoName" type="text" class="form-control required">
												</div>
												<div class="form-group">
													<label>项目描述</label> <textarea id="panoDesc"
														name="panoDesc" type="text" class="form-control required"></textarea>
												</div>
											</div>
											<div class="col-lg-4">
												<div class="text-center">
													<div style="margin-top: 20px">
														<i class="fa fa-sign-in"
															style="font-size: 180px; color: #e5e5e5"></i>
													</div>
												</div>
											</div>
										</div>

									</fieldset>
									<h1>Profile</h1>
									<fieldset>
										<h2>Profile Information</h2>
										<div class="row">
											<div class="col-lg-6">
												<div class="form-group">
													<label>First name *</label> <input id="name" name="name"
														type="text" class="form-control required">
												</div>
												<div class="form-group">
													<label>Last name *</label> <input id="surname"
														name="surname" type="text" class="form-control required">
												</div>
											</div>
											<div class="col-lg-6">
												<div class="form-group">
													<label>Email *</label> <input id="email" name="email"
														type="text" class="form-control required email">
												</div>
												<div class="form-group">
													<label>Address *</label> <input id="address" name="address"
														type="text" class="form-control">
												</div>
											</div>
										</div>
									</fieldset>

									<h1>Warning</h1>
									<fieldset>
										<div class="text-center" style="margin-top: 120px">
											<h2>You did it Man :-)</h2>
										</div>
									</fieldset>

									<h1>Finish</h1>
									<fieldset>
										<h2>Terms and Conditions</h2>
										<input id="acceptTerms" name="acceptTerms" type="checkbox"
											class="required"> <label for="acceptTerms">I
											agree with the Terms and Conditions.</label>
									</fieldset>
								</form>
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