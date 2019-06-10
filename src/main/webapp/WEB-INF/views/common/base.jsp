<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%  
    String path =  request.getScheme()
    		+ "://"
    		+ request.getServerName()
    		+ (request.getServerPort() != 80 ? ":"
    				+ request.getServerPort() : "") +request.getContextPath();
    String url = request.getServletPath();
	request.setAttribute("path",path);
	request.setAttribute("url",url);
%>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
  
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN CORE PLUGINS -->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="${path}/assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!-- END CORE PLUGINS -->
<script type="text/javascript">
	var path ="${path}";
</script>
