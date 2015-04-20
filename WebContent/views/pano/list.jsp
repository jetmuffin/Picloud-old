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
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									全景图片 <small>全景图片列表</small>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div class="project-list">
									<table class="table table-hover">
										<tbody>
										    <c:if test="${empty panoImages}">
                 											暂未上传图片
                 		</c:if>
											<c:forEach var="pano"  items="${panoImages}" > 
											<td class="project-title"><a target="_blank" href="${ROOT}/pano/${pano.key }">${pano.name}</a>
											</td>
									
											<td class="project-create">创建时间：${jt.getStrTime(pano.createTime) } 
											</td>
											<td class="project-actions">
											<a href="${ROOT}/pano/${pano.key}/edit"
												class="btn btn-default btn-sm" ><i
													class="fa fa-folder"></i> 编辑 </a>
											<a href="${ROOT}/pano/${pano.key}"
												class="btn btn-default btn-sm" target="_blank"><i
													class="fa fa-folder"></i> 查看 </a> <a href="${ROOT}/pano/${pano.key }/delete/"
												class="btn btn-default btn-sm"><i class="fa fa-pencil"></i>
													删除 </a></td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
							  <button type="button" class="btn btn-info " data-toggle="modal"
										data-target="#myModal">制作全景图片</button>
										<!--	<a href="${ROOT}/pano/add" class="btn btn-info">制作全景图片</a>-->	
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog hd-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">取消</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">制作全景项目</h4>
							</div>
							<div class="modal-body">
								<form action="${ROOT}/pano/${panoImage.key}/add" method="POST"
									enctype="multipart/form-data">
									<div class="form-group">
										<label class="control-label">项目名称</label> <input
											class="form-control jet-input" type="text" name="panoName" />
									</div>
									<div class="form-group">
										<label class="control-label">项目描述</label> <textarea
											class="form-control jet-input" type="text" name="panoDesc" ></textarea>
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
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
</body>
</html>