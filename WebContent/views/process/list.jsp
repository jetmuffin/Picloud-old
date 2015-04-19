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
								<div class="row applist">
									<div class="col-md-4 app-item">
										<a class="btn btn-info btn-large-dim dim"
											href="${ROOT}/hd/list"><i
											class="fa fa-eye"></i> </a>
										<div class="app-detail">
											<h3>高清图片</h3>
											<p>将图片转化为可高清展示的图片</p>
										</div>
									</div>
									<div class="col-md-4 app-item">
										<a class="btn btn-danger  dim btn-large-dim"
											href="${ROOT}/pano/list"><i
											class="fa fa-globe"></i></a>
										<div class="app-detail">
											<h3>全景展示</h3>
											<p>将图片转化为全景展示图片，可360度查看</p>
										</div>
									</div>
									<div class="col-md-4 app-item">
										<a class="btn btn-warning  dim btn-large-dim"
											href="${ROOT}/threeD/list"><i
											class="fa fa-globe"></i></a>
										<div class="app-detail">
											<h3>3D物品</h3>
											<p>360度展示物品</p>
										</div>
									</div>
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
	<script type="text/javascript" src="${RESOURCES }/js/appcenter.js"></script>
</body>
</html>