<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${RESOURCES}/font/css/font-awesome.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/common.css" />
<link rel="stylesheet" href="${RESOURCES}/css/usercenter.css" />
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
									基本信息 : <span class="account-item">${user.nickname }</span>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<form method="get" class="form-horizontal"
									action="{:U('Index/Usercenter/modifyBasicInfo')}">
									<div class="form-group">
										<label class="col-lg-2 control-label">用户名</label>
										<div class="col-lg-10">
											<p class="form-control-static">${user.uid }</p>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-lg-2 control-label">账户类型</label>
										<div class="col-lg-10">
											<p class="form-control-static">
											   <c:choose>
													<c:when test="${user.accountType == 1 }">
															企业
													</c:when>
													 <c:otherwise>
														    个人
													 </c:otherwise>
												 </c:choose>
											</p>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">昵称</label>
										<div class="col-sm-10">
											<input type="text" name="nickname"
												class="jet-input form-control"
												value="${user.nickname }">
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">网站地址</label>
										<div class="col-sm-10">
											<input type="text" name="website"
												class="jet-input form-control" value="${user.website }">
											<span class="help-block "><small>（请填写您的网站地址，以进行图片外链处理）</small></span>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<div class="col-sm-4 col-sm-offset-2">
											<button class="btn btn-white btn-default " type="reset">取消修改</button>
											<button class="btn btn-primary jet-button" type="submit">保存修改</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									密码 : <span class="account-item">*******</span>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<form method="get" class="form-horizontal"
									action="{:U('Index/Usercenter/modifyPass')}">
									<div class="form-group">
										<label class="col-sm-2 control-label">当前密码</label>
										<div class="col-sm-10">
											<input type="password" class="jet-input form-control"
												name="pwd-old">
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">新密码</label>
										<div class="col-sm-10">
											<input type="password" class="jet-input form-control"
												name="pwd">
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">确认新密码</label>
										<div class="col-sm-10">
											<input type="password" class="jet-input form-control"
												name="pwd-again">
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<div class="col-sm-4 col-sm-offset-2">
											<button class="btn btn-white btn-default " type="reset">取消修改</button>
											<button class="btn btn-primary jet-button" type="submit">保存修改</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									邮箱 : <span class="account-item">xxxxxxx@qq.com</span> <small
										class="account-vldt">（未验证，点击<a href="#">验证邮箱</a>）
									</small>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<p>功能未实现</p>
							</div>
						</div>
					</div>
				</div>
				<br />
				</block>
			</div>
			<jsp:include page="../common/footer.jsp" />
		</div>
	</div>
	<script type="text/javascript" src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
</body>
</html>