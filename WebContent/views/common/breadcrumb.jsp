<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="row wrapper border-bottom white-bg page-heading">
	<div class="col-lg-10">
		<ol class="breadcrumb">
			<li class="active">${module}</li>
			<c:if test="${action!=null}">
				<li class="active">${action}</li>
			</c:if>
			<c:if test="${activeSpace!=null}">
				<li class="active"><a href="${ROOT}/space/${activeSpace.key}/0">${activeSpace.name}</a></li>
			</c:if>
			<c:if test="${activeImage!=null}">
				<li class="active">${activeImage.name}</li>
			</c:if>			
		</ol>
	</div>
</div>