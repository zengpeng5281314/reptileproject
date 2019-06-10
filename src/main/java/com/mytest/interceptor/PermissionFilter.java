package com.mytest.interceptor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 接口调用加密与并发限制
 */
public class PermissionFilter extends OncePerRequestFilter{

	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionFilter.class);
	Map<String, Integer> map = new HashMap<String, Integer>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	public String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	@Override
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		response.setHeader("Content-type", "text/html;charset=UTF-8");
//		// 这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
//		response.setCharacterEncoding("UTF-8");
//		String uri = request.getRequestURI();
//		if(!uri.endsWith(".html")&&!uri.endsWith(".jsp")&&!uri.endsWith("ssww")){
//			LOGGER.info("uri=" + uri);
//		}
		
		
		filterChain.doFilter(request, response);

	}
}
