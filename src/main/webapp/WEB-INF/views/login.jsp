<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>后台登录</title>
	<meta name="author" content="" />
	<meta name="copyright" content="" />
	<%@include file="/WEB-INF/views/common/base.jsp"%>
    <link rel="shortcut icon" href="/favicon.ico"> 
 	<link href="${path}/commons/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/commons/css/font-awesome.css" rel="stylesheet">
    <link href="${path}/commons/css/login.css"  rel="stylesheet"/>
    <link href="${path}/commons/css/sweetalert.css" rel="stylesheet">
	<style type="text/css">
		.gohome{display:none}
	</style>
</head>
<body class="form-bg">
	<div class="container" >
	    <div class="row ">
	        <div class="sign-wrap">
	            <form class="form-horizontal" action="login" method="post" id="loginForm">
	            	<input type="hidden" id="enPassword" name="password" />
	            	<input type="hidden" id="erroMsg" value="${erroMsg}"/>
	                <span class="heading">
	                	<img src="${path}/commons/picture/default_avatar.jpg" class="img-circle" />
	                </span>
	                
	                <div class="form-group help">
	                    <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
	                    <i class="fa fa-user"></i>
	                </div>
	                <div class="form-group help">
	                    <input type="password" class="form-control" id="password" placeholder="密　码" >
	                    <i class="fa fa-lock"></i>
	                </div>
	                <!-- <div class="form-group ">
	                	<img src="static/picture/b4f7c3efe978489ab17a0c749f02ea3c.gif" class="captcha" id="captchaImg"  title="切换验证码"/>
	                    <input type="text" class="form-control captcha-input" name="captcha" id="captcha" placeholder="验证码">
	                    <i class="fa fa-shield"></i>
	                </div> -->
	                <div class="form-group">
	                    <div class="main-checkbox">
	                        <input type="checkbox" value="true" id="isRememberUsername"  checked="checked"/>
	                        <label for="isRememberUsername"></label>
	                    </div>
	                    <span class="text">记住用户名</span>
	                    
	                    <button type="submit" class="btn btn-default" id="loginBtn">登录</button>
	                </div>
	            </form>
	           
	        </div>
	    </div>
	</div>

	<!-- 全局js -->
    <script src="${path}/commons/js/jquery.min.js"></script>
    <script src="${path}/commons/js/bootstrap.min.js"></script>
    <script src="${path}/commons/js/sweetalert.min.js"></script>
    <script src="${path}/commons/js/jquery.validate.min.js"></script>
    <script src="${path}/commons/js/login.js"></script>
    <script src="${path}/commons/js/jsbn.js"></script>
	<script src="${path}/commons/js/prng4.js"></script>
	<script src="${path}/commons/js/rng.js"></script>
	<script src="${path}/commons/js/rsa.js"></script>
	<script src="${path}/commons/js/base64.js"></script>
	
    <script>
    	$().ready(function(){
    		var erroMsg = $("#erroMsg").val();
    		if(erroMsg!="")
    			$.message("error", erroMsg);
    		var $loginForm = $("#loginForm");
    		var $loginBtn = $("#loginBtn");
			var $enPassword = $("#enPassword");
			var $username = $("#username");
			var $password = $("#password");
			var $captcha = $("#captcha");
			var  $captchaImg = $("#captchaImg");
			var $isRememberUsername = $("#isRememberUsername");
			var $avatar = $(".img-circle");
			
			// 记住用户名
			if(getCookie("adminUsername") != null) {
				$isRememberUsername.prop("checked", true);
				$username.val(getCookie("adminUsername"));
				$password.focus();
			} else {
				$isRememberUsername.prop("checked", false);
				$username.focus();
			}
			
			if( getCookie("businessAvatar") != null ){
				$avatar.attr("src", getCookie("businessAvatar"));
			}
				// 表单验证、记住用户名
			$loginForm.submit( function() {
				
				var username = $username.val();
				var password = $password.val();
				var captcha = $captcha.val();
				
				if( $.trim( username ).length == 0 ){
					$.message("warning", "请输入用户名");
					return false;
				}
				
				if( $.trim(password).length == 0 ){
					$.message("warning", "请输入密码");
					return false;
				}
				
				/* if( $.trim(captcha).length != 4 ){
					$.message("warning", "请输入验证码");
					return false;
				} */
				
				
				if ($isRememberUsername.prop("checked")) {
					addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
				} else {
					removeCookie("adminUsername");
				}
				
				/* var rsaKey = new RSAKey();
				rsaKey.setPublic(b64tohex("AITv/LGRSJGv3G1MUHWfwF/L6Ogbja6lCE9x+ush7j0I12J3r63qimjSMwV7j8tzb7bXYv6qd2WV+SNdhQKFVFHgImlHwvcL814ET8JH5TV/gSNofP5qtw8zU6dI79xiRzlMvVdoadwGxChc5gk5vHhOBnSY4sZSaGx4Ou5oezn1"), b64tohex("AQAB"));
				var enPassword = hex2b64(rsaKey.encrypt(password)); */
				$enPassword.val(password);
				
				$loginBtn.prop("disabled", "disabled").addClass("disabled");
			});
    
    		// 切换验证码
    		$captchaImg.click( function(){
    			$(this).attr("src", "/business/common/captcha?" + Math.random());
    		});
    		
    		/*
    		var adminAvatar = getCookie("adminAvatar");
    		var $heading = $(".heading");
    		
    		if( adminAvatar != null ){
    			$heading.find("i").remove();
    			var avatar = '<img src="' + adminAvatar + '" alt="管理员头像" class="img-circle">';
    			$heading.append( avatar );	
    		}
    		*/
    		
    	})
    </script>
</body>
</html>