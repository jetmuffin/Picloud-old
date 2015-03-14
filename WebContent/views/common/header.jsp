<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<nav class="sidebar navbar-default navbar-static-side" role="navigation">
<div class="sidebar-collapse">
	<ul class="nav sidebar-nav" id="side-menu" style="display: block;">
		<li class="nav-header">
			<div class="dropdown profile-element">
				<span> <img alt="image" class="img-circle"
					src="${RESOURCES}/images/user-thumb.png">
				</span> <span class="block user-name"> <strong class="font-bold">${LoginUser.nickname}
				</strong></span> <span class="block user-lastlogin">上次登录:
					${lastLogin }</span>
			</div>
			<div class="logo-element">
				<i class="fa-cloud fa"></i>
			</div>
		</li>
		<c:forEach items="${MODULE}" var="module">
			<li class="nav-li " data-module="${module.name}"><c:choose>
					<c:when test="${module.url!='#'}">
						<a href="${ROOT }/${module.url}" class="nav-button"> <i
							class="fa fa-${module.icon}"></i> <span class="nav-label">${module.title}</span></i>
						</a>
					</c:when>
					<c:otherwise>
						<a href="#" class="nav-button"><i class="fa fa-${module.icon}"></i>
							<span class="nav-label">${module.title}</span></i></a>
						<ul class="nav nav-second-level collapse in" style="height: auto;">
							<c:forEach items="${module.action}" var="action">
								<li><a href="${ROOT }/${module.name}/${action.url}">${action.title}</a></li>
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose></li>
		</c:forEach>
	</ul>
</div>
</nav>