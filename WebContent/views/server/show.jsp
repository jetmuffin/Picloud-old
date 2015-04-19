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
<link rel="stylesheet" href="${PLUGIN}/chosen/chosen.css" />
<link rel="stylesheet" href="${PLUGIN}/imageEditor/css/editor.css" />
<link rel="stylesheet" href="${PLUGIN}/imageEditor/css/spectrum.css" />
<link rel="stylesheet" href="${RESOURCES}/css/picserver.css" />
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
			<div class="col-lg-9">
				<div class="ibox float-e-margins">
    			<div class="ibox-title">
        			<h5>${image.name}</h5>
    			</div>
          <div class="ibox-content" style="display: block;">
	      		<div class="row">
	        		<div class="view-picture col-md-7">
	        			<a href="${ROOT}/server/${image.key}" target="_blank">
	        			<img src='${ROOT}/process/${image.key}/scale[400,-]' width=400>
						</a>
	        		</div>	
	        		<div class="view-profile col-md-5">
	        			<dl class="dl-horizontal">
	        				<div class="dl-group row" id='picture_name'><dt class="col-xs-6">图片名：</dt> <dd class="col-xs-6">${image.name}</dd></div>
	        				<div class="dl-group row"><dt class="col-xs-6">图片类型：</dt> <dd class="col-xs-6">images / ${image.type}</dd></div>
	        				<div class="dl-group row"><dt class="col-xs-6">图片大小：</dt> <dd class="col-xs-6"><c:out value="${jt.cutLength(image.size)}"></c:out>MB</dd></div>
	        				<div class="dl-group row"><dt class="col-xs-6">上传时间：</dt> <dd class="col-xs-6"><c:out value="${jt.getStrTime(image.createTime)}"></c:out></dd></div>
              	</dl>
                	<div class="picture-button">
                		<a class="btn btn-primary jet-button"  data-toggle="modal" data-target="#imageEditor" id="openEditor"><i class="fa-paint-brush fa"></i>处理图片</a>
                		<a  href="${ROOT}/server/${image.key}/delete" class="btn btn-default"><i class="fa-trash fa"></i>删除图片</a>
                	</div>
            		</div>		                          			
          		</div>
          </div>
    		</div>	
			</div>
			<div class="col-lg-3">
				<div class="wrapper view-others project-manager">
					<div class="logo">
						<i class="fa-cloud fa"></i>Picloud
					</div>
					<h4 ><i class="fa fa-circle text-warning"></i>${space.name }</h4>
					<p class="small">
				      ${space.desc}
            </p>
	        	<div class="other-group">
            	<a href="${ROOT}/server/${space.key}/cover[${image.key}]" class="btn btn-xs btn-primary jet-button">设为封面</a>
            	<a href="${ROOT}/server/${space.cover}/view" class="btn btn-xs btn-default ">查看封面</a>
	          </div>	
            <h4><i class="fa fa-circle text-warning"></i>其他图片</h4>
						<ul class="tag-list" style="padding: 0">
				<c:if test="${otherImages!=null }">
					<c:forEach items="${otherImages}" var="otherImage">
						 <li><a href="${ROOT}/server/${otherImage.key}/view"><i class="fa fa-file"></i>${otherImage.name}</a></li>
					</c:forEach>
				</c:if>
            </ul>	
            <div class="other-group">
            	<a  href="${ROOT}/space/${space.key}/upload" class="btn btn-xs btn-primary jet-button">上传图片</a>
            	<a href="${ROOT}/space/${space.key}" class="btn btn-xs btn-default">查看全部</a>
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

		<div class="green-editor modal inmodal in " id="imageEditor" tabindex="-1" role="dialog"
			aria-hidden="false" data-image="${image.name}" data-key="${image.key}"data-imageUrl="${IP}${ROOT}/server/${image.key}" data-visit="${IP}${ROOT}/process/" data-update="${IP}${ROOT}/process/update/">
			<div class="modal-dialog animated bounceInRight">
				<div class='picloud-container' id='picloud-container' ></div>
			</div>
		</div>

    <!-- Modal -->
    <div class="modal fade" id="logoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog hd-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">取消</span></button>
            <h4 class="modal-title" id="logoModalLabel">选择Logo</h4>
          </div>
          <div class="modal-body">
              <div class="form-group">
                <label class="control-label">选择空间</label>
                <select class="form-control  jet-input" name="account" id='spaces_select'>
                  <c:forEach items="${spaces}" var="space">
                    <option value="${space.key}">${space.name}</option>
                  </c:forEach>
               </select>
              </div>
                  <div class="form-group">
                <label class="control-label">选择Logo</label>
                <select data-placeholder="请选择图片" name='imageKey' class="chosen-select form-control jet-input"tabindex="-1" id='pictures_select'>
                 </select>
              </div>
          </div>
        </div>
      </div>
    </div>
    	<div class="modal" id="confirmModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog hd-dialog  ">
						<div class="modal-content bounceIn">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">取消</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">保存图片</h4>
							</div>
							<div class="modal-body">
								保存图片会覆盖原图片,确认保存吗?
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">取消</button>
								<button type="submit" class="btn jet-button btn-primary" id="confirmSave">确认</button>
							</div>
						</div>
					</div>
				</div>
		<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
	<script type="text/javascript" src="${PLUGIN}/chosen/chosen.jquery.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/imageshow.js"></script>
	<script type="text/javascript" src="${PLUGIN}/imageEditor/js/spectrum.js"></script>
	<script type="text/javascript" src="${PLUGIN}/imageEditor/js/jquery.nstSlider.js"></script>
	<script type="text/javascript" src="${PLUGIN}/imageEditor/js/fabric.js"></script>
	<script type="text/javascript" src="${PLUGIN}/imageEditor/js/crop.js"></script>
	<script type="text/javascript" src="${PLUGIN}/imageEditor/js/editor.js"></script>
	<script type="text/javascript" src="${PLUGIN}/zclip/jquery.zclip.min.js"></script>
</body>
</html>