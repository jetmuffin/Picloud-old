<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="jt" class="com.Picloud.utils.JspUtil" scope="page" />
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
<link rel="stylesheet" href="${PLUGIN}/dropzone/css/dropzone.css" />
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
            <h5>3D物品 <small>3D物品列表</small></h5>
          <div class="ibox-tools">
                <a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                <a class="close-link"><i class="fa fa-times"></i></a>
          </div>
          </div>
          <div class="ibox-content" style="display: block;">
            <div class="project-list">
              <table class="table table-hover">
                <tbody>
                 
                 		<c:if test="${empty threeDImages}">
                 											暂未上传图片
                 		</c:if>
                    <c:forEach var="threeD"  items="${threeDImages}" > 
                    <td class="project-title">
                      <a href="${ROOT}/threeD/${threeD.key }">${threeD.name}</a>
                    </td>
                    <td class="project-completion">
                      图片大小: ${jt.cutLength(threeD.size)}MB
                    </td>
                    <td class="project-create">
                      创建时间：${jt.getStrTime(threeD.createTime)}
                    </td>
                    <td class="project-actions">
                      <a href="${ROOT}/threeD/${threeD.key }" class="btn btn-default btn-sm"  target="_blank"><i class="fa fa-folder"></i> 查看 </a>
                      <a href="${ROOT}/threeD/${threeD.key }/delete/" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i> 删除 </a>
                    </td>        
                    </tr>            
                  </c:forEach>
                </tbody>
              </table>
              <button type="button" class="btn btn-primary  jet-button " data-toggle="modal" data-target="#myModal">
              制作3D物品
              </button>
              </div>
          </div>
        </div>  
      </div>
    </div>
        <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog hd-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">取消</span></button>
            <h4 class="modal-title" id="myModalLabel">制作3D物品</h4>
          </div>
          <div class="modal-body">
              <div class="form-group">
                <label class="control-label">上传3D图片</label>
              </div>
            <form id="my-awesome-dropzone" class="dropzone dz-clickable" action="http://localhost:8080/PicServer/Write3D" method="post">
              <div class="dropzone-previews"></div>
              <div class="dropzone-group">
                <div class="form-group">
                <label class="control-label">物品名称</label>
                <input type="text" name="threeDImageName"/>
                <button type="submit" class="btn btn-primary pull-right">提交</button>
              </div>        

                </div>
              <div class="dz-default dz-message"><span>Drop files here to upload</span></div>

            <div class=" text-right">
              <small>(需要按顺时针拍摄图片，并且图片名称按1-n进行排序)</small> 
            </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <!-- <button type="submit" class="btn jet-button btn-primary">确认</button> -->
          </div>
        </div>
      </div>
    </div>
<div id="url_base" data-url='${IP}${ROOT}'></div>
  </block>
		</div>
		<jsp:include page="../common/footer.jsp" />
	</div>
	</div>
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
	<script type="text/javascript" src="${PLUGIN}/dropzone/dropzone.min.js"></script>
	<script src="${RESOURCES}/js/threed.js"></script>
</body>
</html>