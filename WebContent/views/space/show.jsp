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
					<div class="col-lg-3">
						<div class="ibox ">
							<div class="ibox-content">
								<div class="file-manager">
									<h4>${space.name}</h4>
									<h5 class="space-desc">${space.desc}</h5>
									<div class="hr-line-dashed"></div>
									<a class="jet-button btn btn-primary btn-block"
										href="${ROOT}/space/${space.key}/upload">上传图片</a>
									<div class="hr-line-dashed"></div>
									<label class="control-label">其他空间</label>
									<ul class="folder-list" style="padding: 0">
										<c:forEach items="${spaces}" var="otherspace">
											<c:if test="${space.name ne otherspace.name}">
												<li><a href="${ROOT}/space/${otherspace.key}/0">${otherspace.name }</a></li>
											</c:if>
										</c:forEach>
									</ul>
									<div class="picture-search">
										<label class="control-label">搜索图片</label>
										<form action="${ROOT}/space/${space.key}/search" method="get">
											<input type="text" placeholder="搜索" name="key"
												class="form-control jet-input">
										</form>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-9 animated fadeInRight">
						<div class="row">
							<c:forEach items="${images}" var="image">
								<div class="file-box col-lg-4">
									<div class="file">
										<a href="${ROOT }/server/${image.key}/view"> <span
											class="corner"></span>
											<div class="image">
												<img alt="image" class="img-responsive"
													src="${ROOT}/process/${image.key}/scale[198,-]" width=198>
											</div>
											<div class="file-name">
												${image.name} <br> <small> <c:out
														value="${jt.getStrTime(image.createTime)}"></c:out>
												</small>
											</div>
										</a>
									</div>
								</div>
							</c:forEach>
							<div class="clear"></div>
							<nav class="pull-right gallery-pag">
							<ul class="pagination jet-pagination">
								<c:if test="${sessionScope.imagePagePnfo.page < 1 }">
									<li class="disabled"><a href="">&laquo;</a></li>
								</c:if>
								<c:if test="${sessionScope.imagePagePnfo.page >= 1}">
									<li><a
										href="${ROOT}/space/${space.key}/${sessionScope.imagePagePnfo.page-1}">&laquo;</a></li>
								</c:if>
								<c:if test="${sessionScope.imagePagePnfo.ifHaveNext =='false' }">
									<li class="disabled"><a href="">&raquo;</a></li>
								</c:if>
								<c:if test="${sessionScope.imagePagePnfo.ifHaveNext =='true' }">
									<li><a href="${ROOT}/space/${space.key}/${sessionScope.imagePagePnfo.page+1}" >&raquo;</a></li>
								</c:if>
							</ul>
							</nav>
						</div>
					</div>
				</div>

				</block> </block>
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