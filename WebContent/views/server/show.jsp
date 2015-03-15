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
	        			<a href="${ROOT}/server/${image.key}">
	        			<img src='${ROOT}/process/${image.key}/scale[400,-]' >
						</a>
	        		</div>	
	        		<div class="view-profile col-md-5">
	        			<dl class="dl-horizontal">
	        				<div class="dl-group row" id='picture_name'><dt class="col-xs-6">图片名：</dt> <dd class="col-xs-6">${image.name}</dd></div>
	        				<div class="dl-group row"><dt class="col-xs-6">图片类型：</dt> <dd class="col-xs-6">images / ${image.type}</dd></div>
	        				<div class="dl-group row"><dt class="col-xs-6">图片大小：</dt> <dd class="col-xs-6">${image.size}MB</dd></div>
	        				<div class="dl-group row"><dt class="col-xs-6">上传时间：</dt> <dd class="col-xs-6">${image.createTime }</dd></div>
              	</dl>
                	<div class="picture-button">
                		<a class="btn btn-primary jet-button" href="{:U('Appcenter/scale/'.$image['name'])}"><i class="fa-paint-brush fa"></i>处理图片</a>
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
              <volist name="images_random" id="ir">
                <li><a href="{:U('Picserver/view/'.$ir['name'])}"><i class="fa fa-file"></i>{$ir.name}</a></li>
              </volist>
            </ul>	
            <div class="other-group">
            	<a  href="${ROOT}/space/${space.key}/upload" class="btn btn-xs btn-primary jet-button">上传图片</a>
            	<a href="${ROOT}/space/${space.key}" class="btn btn-xs btn-default">查看全部</a>
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
	<script type="text/javascript" src="${RESOURCES }/js/picserver.js"></script>
</body>
</html>