<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.mytest.admin.po.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% 
	String sessionIdValue = null;
	Cookie[] cookies = request.getCookies();
	String style_color = "default";	
	String yuntuo_maintain_user_image ="";
	if(cookies !=null && cookies.length>0){
		for (Cookie cookie : cookies) {
			if ("adminInfo".equals(cookie.getName())) {
				sessionIdValue = java.net.URLDecoder.decode(cookie.getValue(),"utf-8");
			}/* 
			if ("yuntuo_maintain_user_image".equals(cookie.getName())) {
				yuntuo_maintain_user_image = java.net.URLDecoder.decode(cookie.getValue(),"utf-8");
			}
			if ("style_color".equals(cookie.getName())) {
				style_color = cookie.getValue();
			} */
		}
	}
	/* request.setAttribute("yuntuo_maintain_user_image", yuntuo_maintain_user_image);
	request.setAttribute("style_color", style_color); */
	AdminInfoPo po = null;
	if(sessionIdValue != null){
		po = JSON.parseObject(sessionIdValue, AdminInfoPo.class);
	}
	if(po == null) {
	   po = (AdminInfoPo)request.getAttribute("adminInfo");
	}
	request.setAttribute("adminInfo", po);
	%>
   
<!--notification menu start -->
      <div class="menu-right">
          <ul class="notification-menu">
              <li>
				 <span id="clock"></span>
                  <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                      <img src="${path}/commons/adminex/images/photos/user-avatar.png" alt="" />
                     ${adminInfo.userName }
                      <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                      <li><a href="#"><i class="fa fa-user"></i>  Profile</a></li>
                      <li><a href="#"><i class="fa fa-cog"></i>  Settings</a></li>
                      <li><a href="#"><i class="fa fa-sign-out"></i> Log Out</a></li>
                  </ul>
              </li>

          </ul>
      </div>
<!--notification menu end -->
 <!-- END JAVASCRIPTS -->
<script>
    jQuery(document).ready(function() {
    	showTime();
    });
 	// 定义获取和更新时间的函数
	function showTime() {
	    var curTime = new Date();
	    $("#clock").html(curTime.toLocaleString());
	    setTimeout("showTime()", 1000);
	}
 
</script>
        