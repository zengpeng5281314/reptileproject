<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">

<meta name="viewport" content="width=device-width, initial-scale=1">
<title>产品数据</title>
<%@include file="/WEB-INF/views/common/base.jsp"%>
<link href="${path}/commons/adminex/css/style.css" rel="stylesheet">
<link href="${path}/commons/adminex/css/style-responsive.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="${path}/commons/adminex/js/html5shiv.js"></script>
    <script src="${path}/commons/adminex/js/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript" src="${path}/commons/admin/js/login.js"></script>
<script src="http://malsup.github.io/jquery.form.js"></script>
</head>
<body class="sticky-header"
	style="background-color: white; color: black;">

	<!-- start main div -->
	<div
		style="width: 1100px; height: 630px; background-color: #595959; margin: auto;">
		<div
			style="margin-left: 30px;width: 500px; margin-top: 50px; float: left; text-align: center;">
			<div style="width: 500px; height: 40px; background-color: #D5CCC5;">
				<span
					style="width: 19%; float: left; padding-left: 15px; font-size: 20px; line-height: 40px; font-weight: bold;">${testInfoPo.title_left }</span>
				<span
					style="width: 60%; float: inherit; font-size: 30px; line-height: 40px; font-weight: bold;">${testInfoPo.title }</span>
				<span
					style="width: 20%; float: right; padding-right: 20px; font-size: 20px; line-height: 40px; font-weight: bold;">${testInfoPo.title_right }</span>
			</div>
			<div
				style="text-align: center; width: 500px; height: 40px; background-color: #FED966; margin-top: 2px;">
				<span style="font-size: 30px; line-height: 40px; font-weight: bold;">${testInfoPo.body }</span>
			</div>
			<div
				style="width: 500px; height: 30px; background-color: #D5CCC5; margin-top: 2px;">
				<span
					style="width: 30%; float: left; padding-left: 15px; line-height: 30px; font-weight: bold;">Antal
					sidor:${testInfoPo.boby_left }</span> <span
					style="width: 40%; float: inherit; line-height: 30px; font-weight: bold;">Bunt:${testInfoPo.boby_middle }</span>
				<span
					style="width: 30%; float: right; padding-right: 20px; line-height: 30px; font-weight: bold;">${testInfoPo.boby_right }</span>
			</div>
			<div
				style="width: 50%; float: left; background-color: #3C3837; height: 430px;">
				<div>
					<div
						style="height: 40px; line-height: 40px; color: white; font-size: 15px;">Att
						producera</div>
					<div
						style="margin-left: 28px; width: 200px; height: 60px; background-color: #FFFFFF; font-size: 30px; line-height: 60px;font-weight: bold;">${testInfoPo.bottom_left_1 }</div>
				</div>
				<div style="margin-top: 20px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_left_2 }</div>
				     <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_left_3 }</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_left_4 }</div>
				 	<div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_left_5 }</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_left_6 }</div>
	                <div style="clear:both"></div>			
				</div>
			</div>
			
			<div
				style="width: 50%; float: left; background-color: #3C3837; height: 430px;">
				<div>
					<div
						style="height: 40px; line-height: 40px; color: white; font-size: 15px;">Producerade</div>
					<div
						style="margin-left: 28px; width: 200px; height: 60px; background-color: #FFFFFF; font-size: 30px; line-height: 60px;font-weight: bold;">${testInfoPo.bottom_right_1 }</div>
				</div>
				<div style="margin-top: 20px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_right_2 }</div>
				     <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_right_3 }</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_right_4 }</div>
				 	<div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_right_5 }</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">${testInfoPo.bottom_right_6 }</div>
	                <div style="clear:both"></div>			
				</div>
			</div>
		</div>
		
		
		<div
			style="width: 500px;margin-right: 30px; margin-top: 50px; float: right; text-align: center;">
			<div style="width: 500px; height: 40px; background-color: #D5CCC5;">
				<span
					style="width: 19%; float: left; padding-left: 15px; font-size: 20px; line-height: 40px; font-weight: bold;">30000</span>
				<span
					style="width: 60%; float: inherit; font-size: 30px; line-height: 40px; font-weight: bold;">GRA</span>
				<span
					style="width: 20%; float: right; padding-right: 20px; font-size: 20px; line-height: 40px; font-weight: bold;">25000</span>
			</div>
			<div
				style="text-align: center; width: 500px; height: 40px; background-color: #FED966; margin-top: 2px;">
				<span style="font-size: 30px; line-height: 40px; font-weight: bold;">Smalandsposten</span>
			</div>
			<div
				style="width: 500px; height: 30px; background-color: #D5CCC5; margin-top: 2px;">
				<span
					style="width: 30%; float: left; padding-left: 15px; line-height: 30px; font-weight: bold;">Antal
					sidor:40</span> <span
					style="width: 40%; float: inherit; line-height: 30px; font-weight: bold;">Bunt:100</span>
				<span
					style="width: 30%; float: right; padding-right: 20px; line-height: 30px; font-weight: bold;">27.07.2017</span>
			</div>
			<div
				style="width: 50%; float: left; background-color: #3C3837; height: 430px;">
				<div>
					<div
						style="height: 40px; line-height: 40px; color: white; font-size: 15px;">Att
						producera</div>
					<div
						style="margin-left: 28px; width: 200px; height: 60px; background-color: #FFFFFF; font-size: 30px; line-height: 60px;font-weight: bold;">556677</div>
				</div>
				<div style="margin-top: 20px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				     <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				 	<div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
	                <div style="clear:both"></div>			
				</div>
			</div>
			
			<div
				style="width: 50%; float: left; background-color: #3C3837; height: 430px;">
				<div>
					<div
						style="height: 40px; line-height: 40px; color: white; font-size: 15px;">Producerade</div>
					<div
						style="margin-left: 28px; width: 200px; height: 60px; background-color: #FFFFFF; font-size: 30px; line-height: 60px;font-weight: bold;">554466</div>
				</div>
				<div style="margin-top: 20px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				     <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				 	<div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
				    <div style="clear:both"></div>
				</div>
				<div style="margin-top: 10px;">
					<div style="width: 42%;float: left;text-align: right;line-height: 50px;color: white;">fran fals</div>
					<div style="width: 45%; height: 50px;float: right;background-color: #FFFFFF;margin-right: 22px;text-align: center;line-height: 50px;font-size: 20px;font-weight: 600;">557788</div>
	                <div style="clear:both"></div>			
				</div>
			</div>
		</div>
		
		<!-- <div
			style="width: 500px; height: 30px; background-color: #3C3837; margin-right: 50px; float: right;">闹</div>
	 --></div>

	<!-- end main div -->

	<!-- Placed js at the end of the document so the pages load faster -->
	<script src="${path}/commons/adminex/js/jquery-1.10.2.min.js"></script>
	<script src="${path}/commons/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="${path}/commons/adminex/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${path}/commons/adminex/js/bootstrap.min.js"></script>
	<script src="${path}/commons/adminex/js/modernizr.min.js"></script>
	<script src="${path}/commons/adminex/js/jquery.nicescroll.js"></script>

	<!--data table-->
	<script type="text/javascript"
		src="${path}/commons/adminex/js/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${path}/commons/adminex/js/data-tables/DT_bootstrap.js"></script>

	<!--common scripts for all pages-->
	<script src="${path}/commons/adminex/js/scripts.js"></script>

	<!--script for editable table-->
	<script src="${path}/commons/adminex/js/editable-table.js"></script>

	<!-- END JAVASCRIPTS -->
	<script>
		jQuery(document).ready(function() {

			//showTime();
			setTimeout("showTime()", 2000);
		});

		// 定义获取和更新时间的函数
		function showTime() {
			var curTime = new Date();
			$("#clock").html(curTime.toLocaleString());
			
			location.replace(location.href);
			
		}
	</script>

</body>
</html>