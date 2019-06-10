<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
<%@include file="/WEB-INF/views/common/base.jsp"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<link rel="stylesheet" href="/commons/css/font.css">
<link rel="stylesheet" href="/commons/css/xadmin.css">
<link rel="stylesheet" href="/commons/css/theme5.css">
<script src="/commons/lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="/commons/js/xadmin.js"></script>
</head>
	<body>
   		<div class="layui-fluid">
            <div class="layui-row">
                <form class="layui-form" >
                <input type="hidden" id="channelId" name="channelId" value="${channleDetailedInfoPo.id }" class="layui-input">
                 <div class="layui-form-item">
                      <label for="nickName" class="layui-form-label">
                          <span class="x-red"></span>用户姓名
                      </label>
                       <div class="layui-input-inline">
                       		<input type="text" id="nickName" name="nickName" disabled="" value="${channleDetailedInfoPo.userInfoPo.nickName }" class="layui-input">
                       </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="channelName" class="layui-form-label">
                          <span class="x-red"></span>渠道
                      </label>
                       <div class="layui-input-inline">
                            <input type="text" id="channelName" name="channelName" disabled="" value="${channleDetailedInfoPo.channleInfoPo.channelName }" class="layui-input">
                       </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="registNum" class="layui-form-label">
                          <span class="x-red">*</span>注册数
                      </label>
                      <div class="layui-input-inline">
                          <input type="text" id="registNum" name="registNum" required="" lay-verify="required"
                          autocomplete="off" class="layui-input" value="${channleDetailedInfoPo.registNum }">
                      </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="applicantsNum" class="layui-form-label">
                          <span class="x-red">*</span>借款人数
                      </label>
                      <div class="layui-input-inline">
                          <input type="text" id="applicantsNum" name="applicantsNum" required="" lay-verify="applicantsNum"
                          autocomplete="off" class="layui-input" value="${channleDetailedInfoPo.applicantsNum }">
                      </div>
                  </div>
                  <div class="layui-form-item">
                      <label for="loanNum" class="layui-form-label">
                          <span class="x-red">*</span>放款人数
                      </label>
                      <div class="layui-input-inline">
                          <input type="text" id="loanNum" name="loanNum" required="" lay-verify="loanNum"
                          autocomplete="off" class="layui-input" value="${channleDetailedInfoPo.loanNum }">
                      </div>
                  </div>
                 
                
                  <div class="layui-form-item">
                      <label for="L_repass" class="layui-form-label">
                      </label>
                      <button  class="layui-btn" lay-filter="add" lay-submit="">
                          	修改
                      </button>
                  </div>
              </form>
            </div>
        </div>
        <script>
        layui.use(['form', 'layer'],
            function() {
                $ = layui.jquery;
                var form = layui.form,
                layer = layui.layer;

                //自定义验证规则
                form.verify({
                    /* nikename: function(value) {
                        if (value.length < 5) {
                            return '昵称至少得5个字符啊';
                        }
                    },
                    pass: [/(.+){6,12}$/, '密码必须6到12位'],
                    repass: function(value) {
                        if ($('#L_pass').val() != $('#L_repass').val()) {
                            return '两次密码不一致';
                        }
                    } */
                });

                //监听提交
                form.on('submit(add)',
                function(data) {
                    console.log(data.field);
                    //发异步，把数据提交给php
                    $.ajax({
		    			type : "POST",
		    			url : "/channelDetailed/editchanneldetailed",
		    			dataType : "json",
		    			data : data.field,
		    			success : function(res) {
		    				if(res.success){
		    					layer.alert("修改成功", {
		                            icon: 6
		                         },
		                         function() {
		                             //关闭当前frame
		                             xadmin.close();

		                             // 可以对父窗口进行刷新 
		                             xadmin.father_reload();
		                            
		                         }); 
		    				}else{
		    					alert(res.msg);
		    				}
		    			},
		    			error : function(data) {
		    				alert("登录失败");
		    			}
		    		}); 
                    
                    return false;
                });
                    
            });</script>
	</body>
</html>