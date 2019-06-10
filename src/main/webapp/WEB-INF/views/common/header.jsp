<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.mytest.admin.po.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sessionIdValue = null;
	Cookie[] cookies = request.getCookies();
	String style_color = "default";
	String yuntuo_maintain_user_image = "";
	if (cookies != null && cookies.length > 0) {
		for (Cookie cookie : cookies) {
			if ("adminInfo".equals(cookie.getName())) {
				sessionIdValue = java.net.URLDecoder.decode(cookie.getValue(), "utf-8");
			}
			if ("yuntuo_maintain_user_image".equals(cookie.getName())) {
				yuntuo_maintain_user_image = java.net.URLDecoder.decode(cookie.getValue(), "utf-8");
			}
			if ("style_color".equals(cookie.getName())) {
				style_color = cookie.getValue();
			}
		}
	}
	/* request.setAttribute("yuntuo_maintain_user_image", yuntuo_maintain_user_image);
	request.setAttribute("style_color", style_color); */
	TUserInfoPo po = null;
	if (sessionIdValue != null) {
		try {
			po = JSON.parseObject(sessionIdValue, TUserInfoPo.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	if (po == null) {
		po = (TUserInfoPo) request.getAttribute("adminInfo");
	}
	request.setAttribute("adminInfo", po);
%>
