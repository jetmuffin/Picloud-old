<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="jt" class="com.Picloud.utils.JspUtil" scope="page" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${RESOURCES}/font/css/font-awesome.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/common.css" />
<link rel="stylesheet" href="${RESOURCES}/css/index.css" />
</head>
<body>
	<div class="wrap">
		<jsp:include page="common/header.jsp" />
		<div class="page-wrapper">
			<jsp:include page="common/navbar.jsp" />
			<jsp:include page="common/breadcrumb.jsp" />
			<div class="wrapper wrapper-content animated fadeInDown">
				<block name="content">
				<div class="row">
					<div class="col-md-12 dashboard-heading">
						<div class="ibox">
							<div class="ibox-title">
								<h5>概况</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div class="row">
									<div class="user-info col-md-4">
										<div class="widget-head-color-box navy-bg p-lg text-center">
											<h2 class="font-bold no-margins">${LoginUser.nickname}</h2>
											<h5>
												<c:choose>
													<c:when test="${LoginUser.accountType == 1 }">
															企业
													</c:when>
													<c:otherwise>
														    个人
													 </c:otherwise>
												</c:choose>
												用户
											</h5>
										</div>
										<div>
											<img alt="image" class="img-circle"
												src="${RESOURCES }/images/user.png">
										</div>
										<div>
											点击<a href="user/update">修改个人信息</a>
										</div>
									</div>
									<div class="cloud-info col-md-4">
										<div class="info-header">图片云信息</div>
										<div class="hr-line-dashed"></div>
										<div class="info-item">
											<div>
												<span>存储空间：</span> <small class="pull-right">
													<!-- TODO 保留两位小数 -->
													<c:out value="${jt.cutLength(LoginUser.imageTotalSize)}"></c:out>MB
												</small>
											</div>
											<div class="progress progress-small">
												<div style="width: <?php echo $user['totSize']/5;?>%;"
													class="progress-bar progress-default"></div>
											</div>
										</div>
										<div class="hr-line-dashed"></div>
										<div class="info-item">
											<span>图片空间数：</span> <small class="pull-right">${LoginUser.spaceNum}</small>
										</div>
										<div class="hr-line-dashed"></div>
										<div class="info-item">
											<span>总图片数：</span> <small class="pull-right">${LoginUser.imageNum}</small>
										</div>
									</div>
									<div class="use-chart col-md-4">
										<div class="info-header">云空间使用：</div>
										<div class="flot-chart">
											<c:if test="${empty spaces}">还未创建空间</c:if>
											<div class="flot-chart-content" id="flot-pie-chart"
												style="width: 300px; height: 180px"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="uid" style="display: none">${LoginUser.uid }</div>
					<div class="col-md-6">
						<div class="ibox">
							<div class="ibox-title">
								<h5>空间信息</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div class="space-info ">
									<c:choose>
										<c:when test="${spaces==null}">还未创建空间</c:when>
										<c:otherwise>
											<c:forEach items="${spaces}" var="space" varStatus="status">
												<div class="info-item">
													<c:set var="t" value="${status.count%4 }"></c:set>
													<c:choose>
														<c:when test="${t==1 }">
															<span class="label label-success">${k}</span>
														</c:when>
														<c:when test="${t==2 }">
															<span class="label label-info">${k}</span>
														</c:when>
														<c:when test="${t==3 }">
															<span class="label label-default">${k}</span>
														</c:when>
														<c:when test="${t==0 }">
															<span class="label label-primary">${k}</span>
														</c:when>
													</c:choose>
													<a href="space/${space.key}/0"><span>${space.name}</span></a>
													<small class="pull-right">${space.number}张 /<c:out value="${jt.cutLength(space.storage)}"></c:out>MB</small>
												</div>
												<div class="hr-line-dashed"></div>
											</c:forEach>
										</c:otherwise>
									</c:choose>


								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="ibox">
							<div class="ibox-title">
								<h5>最近操作</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div class="recent-control ">
									<c:if test="${logs==null }">未进行任何操作</c:if>
									<c:forEach items="${logs}" var="log"  begin="0" end="1">
									<div class="info-item">
										<p>${log.operation}</p>
										<small class="block text-muted"><i
											class="fa fa-clock-o"></i><c:out value="${jt.getStrTime(log.time)}"></c:out> </small>
									</div>
									<div class="hr-line-dashed"></div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="ibox">
							<div class="ibox-title">
								<h5>最新图片</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div class="row upload-gallery">
									<c:forEach items="${recentImages}" var="image">
									<div class="file-box col-md-3">
										<div class="file">
											<a href="#"><span class="corner"></span>
												<div class="image">
													<img  class="img-responsive"
													src="${ROOT}/process/${image.key}/scale[198,-]" width=198/>
												</div>
												<div class="file-name">
													${image.name} <br> 
												</div> </a>
										</div>
									</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="url_base" data-url="${IP}${ROOT}"></div>
				</block>
			</div>
			<jsp:include page="common/footer.jsp" />
		</div>
	</div>
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
		<script type="text/javascript" src="${PLUGIN}/flot/jquery.flot.min.js"></script>
		<script type="text/javascript" src="${PLUGIN}/flot/jquery.flot.pie.min.js"></script>
		<script type="text/javascript" src="${PLUGIN}/flot/jquery.flot.tooltip.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/index.js"></script>
</body>
</html>