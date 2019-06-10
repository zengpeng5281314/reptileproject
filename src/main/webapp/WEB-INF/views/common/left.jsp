<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js"	>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta charset="utf-8">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- left side start-->
<div class="left-side sticky-left-side">

	<!--logo and iconic logo start-->
	<div class="logo">
		<a href="index.html"><img src="${path}/commons/adminex/images/logo.png" alt=""></a>
	</div>

	<div class="logo-icon text-center">
		<a href="index.html"><img src="${path}/commons/adminex/images/logo_icon.png" alt=""></a>
	</div>
	<!--logo and iconic logo end-->


	<div class="left-side-inner">

		
		<!--sidebar nav start-->
		<ul class="nav nav-pills nav-stacked custom-nav">
			<li><a href="${path}/admin/productlist"><i class="fa fa-home"></i> <span>主页</span></a></li>
					
			<li class="menu-list nav-active"><a href="#"><i
					class="fa fa-th-list"></i> <span>管理模块</span></a>
				<ul class="sub-menu-list">
				
					<li <c:if test="${leftMenu=='物品管理'}">class="active"</c:if>><a href="${path}/admin/productlist">物品管理</a></li>
					<li <c:if test="${leftMenu=='订单管理'}">class="active"</c:if>><a href="${path}/admin/orderlist">订单管理</a></li>
					<li <c:if test="${leftMenu=='库存管理'}">class="active"</c:if>><a href="${path}/admin/stocklist">库存管理</a></li>
					
				</ul>
			</li>
		</ul>
		<!--sidebar nav end-->

	</div>
</div>
<!-- left side end-->