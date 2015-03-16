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
											<tr>
												<td class="project-title"><a href="project_detail.html">示例物品</a>
												</td>
												<td class="project-completion">图片大小: 4.8MB</td>
												<td class="project-create">创建时间：2014-12-10</td>
												<td class="project-actions"><if
														condition="($pano['name'] eq 'example.jpg')"> <a
														href="{:U('Appcenter/overallview/example.jpg')}"
														class="btn btn-default btn-sm" target="_blank"><i
														class="fa fa-folder"></i> 查看 </a> <a href="#"
														class="btn btn-default btn-sm"><i class="fa fa-pencil"></i>
														删除 </a></td>
											</tr>
											<volist name="panos" id="pano">
											<td class="project-title"><a href="project_detail.html">{$pano.name}</a>
											</td>
											<td class="project-completion">图片大小: <?php echo sprintf("%.2f", $pano['size']/1024/1024);?>MB
											</td>
											<td class="project-create">创建时间：<?php echo timestr_totime($pano['createTime']);?>
											</td>
											<td class="project-actions"><a
												href="{:U('Appcenter/overallview/'.$pano['name'])}"
												class="btn btn-default btn-sm" target="_blank"><i
													class="fa fa-folder"></i> 查看 </a> <a href="#"
												class="btn btn-default btn-sm"><i class="fa fa-pencil"></i>
													删除 </a></td>
											</tr>
											</volist>
										</tbody>
									</table>
									<button type="button" class="btn btn-info " data-toggle="modal"
										data-target="#myModal">制作全景图片</button>
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
								<h4 class="modal-title" id="myModalLabel">制作高清图片</h4>
							</div>
							<div class="modal-body">
								<form action="${ROOT}/pano/add" method="POST"
									enctype="multipart/form-data">
									<div class="form-group">
										<label class="control-label">上传全景图片</label> <input
											class="form-control jet-input" type="file" name="upl-file" />
									</div>
									<div class="form-group">
										<label class="control-label">注意</label>
										<p>上传图片需要为标准的鱼眼图片，否则无法得到效果完美的全景图片。</p>
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
	<script type="text/javascript" src="${RESOURCES }/js/appcenter.js"></script>
</body>
</html>