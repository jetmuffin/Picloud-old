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
<link rel="stylesheet" href="${RESOURCES}/css/common.css" />
<link rel="stylesheet" href="${RESOURCES}/css/picserver.css" />
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp" />
		<div class="page-wrapper">
			<jsp:include page="../common/navbar.jsp" />
			<jsp:include page="../common/breadcrumb.jsp" />
			<div class="wrapper wrapper-content animated fadeInDown">
				<block name="content"> <block name="content">
				<div class="row">
					<c:forEach items="${spaces}" var="space">  
					<div class="col-md-4">
						<div class="ibox ">
							<div class="ibox-title">
								<h5>${space.name}</h5>
							</div>
							<div class="ibox-content  space-pic">
								<a href="${space.key}/0">
									<c:choose>
										<c:when test="${space.cover=='' }">
										<img src="${RESOURCES}/images/p3.jpg">
										</c:when>
										<c:otherwise>
											<img src="${ROOT}/process/${space.cover}/scale[400,-]"/>
										</c:otherwise>
									</c:choose>
								</a>
							</div>
							<div class="ibox-content profile-content">
								<div class="row space-info">
									<div class="col-xs-6">
										<i class="fa-picture-o fa"></i>图 片<span class="info-figure">${space.number}
											张</span>
									</div>
									<div class="col-xs-6">
										<i class="fa-database fa"></i>用 量<span class="info-figure">
											<c:out value="${jt.cutLength(space.storage)}"></c:out>MB 
									</div>
								</div>
								<div class="space-desc">
									<p>${space.desc}</p>
								</div>
								<div class="user-button">
									<div class="row">
										<div class="col-md-6">
											<a href="${space.key }"
												type="button" class="btn btn-primary btn-block jet-button"><i
												class="fa fa-folder-open"></i> 查看</a>
										</div>
										<div class="col-md-6">
											<a
												href="${space.name }/delete"
												class="btn btn-default btn-block"><i class="fa fa-trash"></i>
												删除</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					</c:forEach>
					<div class="col-md-4">
						<div class="ibox">
							<div class="ibox-title">
								<h5>创建空间</h5>
							</div>
							<sf:form method="post" modelAttribute="space" action="add">
								<div class="ibox-content  space-pic">
									<div class="form-group">
										<label class="control-label">空间名称</label>
										<sf:errors path="name"/>
									</div>
									<div class="form-group">
											<sf:input path="name" class="form-control jet-input" placeholder="请输入空间名称"/>
									</div>
									<div class="form-group">
										<label class="control-label">空间描述</label>
										<sf:errors path="desc"/>
									</div>
									<div class="form-group">
										<sf:textarea path="desc" class="form-control jet-input" placeholder="请输入空间描述"/>
									</div>
								</div>
								<div class="ibox-content profile-content">
									<div class="user-button">
										<div class="row">
											<div class="col-md-6">
												<button type="submit"
													class="btn btn-primary btn-block jet-button">
													<i class="fa fa-plus"></i> 创建
												</button>
											</div>
											<div class="col-md-6">
												<a type="reset" class="btn btn-default btn-block"><i
													class="fa fa-reply"></i> 取消</a>
											</div>
										</div>
									</div>
								</div>
							</sf:form>
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
</body>
</html>