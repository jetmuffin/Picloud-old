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
  				<c:if test="${not empty userMsg}">
	    			<div class="alert alert-info alert-dismissable">
						<button aria-hidden="true" data-dismiss="alert" class="close"
							type="button">×</button>
								${userMsg}
					</div>					
  				</c:if>				
				<div class="row">
					<div class="col-md-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									基本信息 : <span class="account-item">${LoginUser.nickname }</span>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<sf:form method="post"  class="form-horizontal" modelAttribute="user" action="${ROOT}/user/update">
									<div class="form-group">
										<label class="col-lg-2 control-label">用户名</label>
										<div class="col-lg-10">
											<p class="form-control-static">${LoginUser.uid }</p>
											<sf:input path="uid"  style="display:none" value="${LoginUser.uid }"/>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-lg-2 control-label">账户类型</label>
										<div class="col-lg-10">
											<p class="form-control-static">
												<c:choose>
													<c:when test="${LoginUser.accountType == 1 }">
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
										<div class="col-sm-6">
											<sf:input type="text" path="nickname"
												class="jet-input form-control" value="${LoginUser.nickname }" />
										</div>
										<div class="col-sm-4"><sf:errors path="nickname" /></div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">网站地址</label>
										<div class="col-sm-6">
											<sf:input type="text" path="website"
												class="jet-input form-control" value="${LoginUser.website }" />
											<span class="help-block "><small>（请填写您的网站地址，以进行图片外链处理）</small></span>
										</div>
										<div class="col-sm-4"><sf:errors path="website" /></div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">当前密码</label>
										<div class="col-sm-6">
											<input type="password" class="jet-input form-control"
												name="pwd_old" />
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">新密码</label>
										<div class="col-sm-6">
											<sf:password class="jet-input form-control" path="password" />
										</div>
										<span class="col-sm-4"><sf:errors path="password" /></span>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<div class="col-sm-4 col-sm-offset-2">
											<button class="btn btn-white btn-default " type="reset">取消修改</button>
											<button class="btn btn-primary jet-button" type="submit">保存修改</button>
										</div>
									</div>
								</sf:form>
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
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
</body>
</html>