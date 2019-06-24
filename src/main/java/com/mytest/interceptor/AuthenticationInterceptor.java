package com.mytest.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.rest.action.admin.indices.validate.query.RestValidateQueryAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.mytest.admin.po.TUserInfoPo;
import com.mytest.utils.WebUtils;

/**
 * 登录 权限等拦截器 **
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	private PathMatcher pathMatcher = new AntPathMatcher();
	Map<String, Integer> map = new HashMap<String, Integer>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private List<String> excludedUrls;
	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}
	

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String userId = request.getParameter("userId");
		long userid = userId != null && !userId.equals("") ? Long.parseLong(userId) : 0;
		String uri = request.getRequestURI();
		
		Enumeration<String> enumeration = request.getParameterNames();
		StringBuffer sb = new StringBuffer("");
			// 输出参数
			while (enumeration.hasMoreElements()) {
				String arg = enumeration.nextElement();
				String value = request.getParameter(arg);
				sb.append(arg + "=" + value + " ");
			}
		LOGGER.info( "uri:"+uri+ "\r\n args[" + sb.toString() + "]");


		  StringBuilder sb1 = new StringBuilder();
	        InputStream inputStream = null;
	        BufferedReader reader = null;
	        try {
	            inputStream = request.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
	            String line = "";
	            while ((line = reader.readLine()) != null) {
	                sb1.append(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		LOGGER.info("sb1:" + sb1);
		
		
		

		if(uri.equals("/"))
			return true;
		//过滤不需要拦截的
        for (String url : this.excludedUrls) {
            if (uri.contains(url)) {
                return true;            
            }
        }
        /*
		Cookie[] cookies = request.getCookies();
		boolean isLogin = false;
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if ("adminInfo".equals(cookie.getName())) {
					String value = java.net.URLDecoder.decode(cookie.getValue().toString(), "utf-8");
					TUserInfoPo adminInfo = JSON.parseObject(value, TUserInfoPo.class);
					if (adminInfo == null) {
						isLogin = false;
					} else {
						request.setAttribute("adminInfo", adminInfo);
						isLogin = true;
						break;
					}
				}else{
					isLogin = false;
				}
			}
		} else {
			isLogin = false;
		}
		if(!isLogin)
			response.sendRedirect(request.getContextPath() + "/login");
		
		return isLogin;
		*/
        return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	protected boolean pathsMatch(String path, ServletRequest request) {
		String requestURI = getPathWithinApplication(request);

		return pathsMatch(path, requestURI);
	}

	protected boolean pathsMatch(String pattern, String path) {
		return this.pathMatcher.match(pattern, path);
	}

	protected String getPathWithinApplication(ServletRequest request) {
		return WebUtils.getPathWithinApplication((HttpServletRequest) request);
	}
}
