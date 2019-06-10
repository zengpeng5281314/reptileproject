<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@include file="/WEB-INF/views/common/base.jsp"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="author" content="" />
	<meta name="copyright" content="" />
    <link rel="shortcut icon" href="/favicon.ico"> 
    <link href="${path}/commons/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/commons/css/font-awesome.css" rel="stylesheet">
    <link href="${path}/commons/css/animate.css" rel="stylesheet">
    <link href="${path}/commons/css/style.css" rel="stylesheet">
    <style type="text/css">
    	.img-circle{ width: 100%; border-radius: 0;}
    </style>
<script>
    // 是否开启刷新记忆tab功能
    // var is_remember = false;
</script>
</head>
 <body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i></div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <img alt="头像"  class="img-circle" src="${path}/commons/picture/main.jpg" />
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li class="divider"></li>
                                <li><a href="${path}/signout">安全退出</a></li>
                            </ul>
                        </div>
                        <div class="logo-element" title="Credit System Manager">CSM</div>
                    </li>
                    <li>
                        <a href="${path}/qrcode" class="J_menuItem">
                            <i class="fa fa-th-large"></i>
                            <span class="nav-label">系统版本</span>
                        </a>
                    </li>
					<li>
					    <a href="#">
					        <i class="fa fa-link"></i>
					        <span class="nav-label">渠道管理</span>
					        <span class="fa arrow"></span>
					
					    </a>
					    <ul class="nav nav-second-level">
					        <li>
					            <a class="J_menuItem" href="${path}/channeldetailed/channel">
					                <i class="fa fa-handshake-o"></i>子链接管理
					            </a>
					        </li>
					    </ul>
					</li>

                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                    <ul class="nav navbar-top-links navbar-right">
                    </ul>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i></button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${path}/qrcode">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭<span class="caret"></span></button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>当前选项卡</a></li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部</a></li>
                        <li class="J_tabCloseOther"><a>关闭其他</a></li>
                    </ul>
                </div>
                <a href="${path}/signout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i>退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${path}/qrcode" frameborder="0" data-id="${path}/qrcode" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2017-2017 <a href="/" target="_blank">Chemers</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>

    <!-- 全局js -->
    <script src="${path}/commons/js/jquery.min.js"></script>
    <script src="${path}/commons/js/bootstrap.min.js"></script>
    <script src="${path}/commons/js/jquery.metismenu.js"></script>
    <script src="${path}/commons/js/jquery.slimscroll.min.js"></script>
    <script src="${path}/commons/js/contabs.js"></script>
    <script src="${path}/commons/js/pace.min.js"></script>
    <script>	
    	
    	$().ready( function(){
    			var businessAvatar = getCookie("businessAvatar");
    			if( businessAvatar == null ){
    				addCookie("businessAvatar", "http://avatartest.oss-cn-hangzhou.aliyuncs.com/upload/image/avatar/201905/e79c2a47-9c8e-421c-8280-fca0c388870e.png", {expires: 7 * 24 * 60 * 60});
    			}else{
    				$("#avatar").attr("src", getCookie("businessAvatar"));
    			}
    	})
    </script>
</body>
</html>