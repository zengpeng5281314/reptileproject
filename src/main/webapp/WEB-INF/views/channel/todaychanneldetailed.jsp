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
   
        <div class="x-nav">
            <span class="layui-breadcrumb">
                <a href="">首页</a>
                <a>
                    <cite>导航元素</cite></a>
            </span>
            <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
                <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i>
            </a>
        </div>
        <div class="layui-fluid">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-body ">
                            <form class="layui-form layui-col-space5">
                                <div class="layui-input-inline layui-show-xs-block">
                                    <input class="layui-input" placeholder="开始日" name="startTime" id="start"></div>
                                <div class="layui-input-inline layui-show-xs-block">
                                    <input class="layui-input" placeholder="截止日" name="endTime" id="end"></div>
                                <div class="layui-input-inline layui-show-xs-block">
                                    <select name="channelId">
                                     	<option value="0">渠道</option>
                                    	<c:forEach items="${listTChannleInfoPo}" var="item">
                                        	<option value="${item.id}">${item.channelName}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                                <div class="layui-input-inline layui-show-xs-block">
                                    <button class="layui-btn" lay-submit="" lay-filter="sreach">
                                        <i class="layui-icon">&#xe615;</i></button>
                                </div>
                            </form>
                        </div>
                            <table class="layui-table layui-form">
                                <thead>
                                    <tr>
                                        <th>日期</th>
                                        <th>姓名</th>
                                        <th>渠道名称</th>
                                        <th>注册数</th>
                                        <th>借款人数</th>
                                        <th>放款人数</th>
                                        <th>放款转化率(%)</th>
                                        <th>通过率(%)</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${listChannleDetail}" var="item">
	                                    <tr>
	                                        <td>${item.createTime}</td>
	                                        <td>${item.userInfoPo.nickName}</td>
	                                        <td>${item.channleInfoPo.channelName}</td>
	                                        <td>${item.registNum}</td>
	                                        <td>${item.applicantsNum}</td>
	                                        <td>${item.loanNum}</td>
	                                        <td>${item.loanRate}</td>
	                                        <td>${item.passingRate}</td>
	                                        <td class="td-manage">
			                                    <a title="编辑"  onclick="xadmin.open('编辑','/channelDetailed/editchanneldetailedshow?id=${item.id}')" href="javascript:;">
			                                      <i class="layui-icon">&#xe642;</i>
			                                    </a>
			                                  </td>
	                                    </tr>
                                   </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="layui-card-body ">
                            <div class="page">
                                <div>
                                	<a class="num" href="">共${total}条</a>
                                	<a class="num" href="">注册数:${registTotal}</a>
                                	<a class="num" href="">借款人数:${applicantsTotal}</a>
                                	<a class="num" href="">放款人数:${loanTotal}</a>
                                    <!-- <a class="prev" href="">&lt;&lt;</a>
                                    <a class="num" href="">1</a>
                                    <span class="current">2</span>
                                    <a class="num" href="">3</a>
                                    <a class="num" href="">489</a>
                                    <a class="next" href="">&gt;&gt;</a> -->
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>layui.use(['laydate', 'form'],
        function() {
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#start' //指定元素
            });

            //执行一个laydate实例
            laydate.render({
                elem: '#end' //指定元素
            });
        });

        /*用户-停用*/
        function member_stop(obj, id) {
            layer.confirm('确认要停用吗？',
            function(index) {

                if ($(obj).attr('title') == '启用') {

                    //发异步把用户状态进行更改
                    $(obj).attr('title', '停用');
                    $(obj).find('i').html('&#xe62f;');

                    $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
                    layer.msg('已停用!', {
                        icon: 5,
                        time: 1000
                    });

                } else {
                    $(obj).attr('title', '启用');
                    $(obj).find('i').html('&#xe601;');

                    $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
                    layer.msg('已启用!', {
                        icon: 5,
                        time: 1000
                    });
                }

            });
        }

        /*用户-删除*/
        function member_del(obj, id) {
            layer.confirm('确认要删除吗？',
            function(index) {
                //发异步删除数据
                $(obj).parents("tr").remove();
                layer.msg('已删除!', {
                    icon: 1,
                    time: 1000
                });
            });
        }

        function delAll(argument) {

            var data = tableCheck.getData();

            layer.confirm('确认要删除吗？' + data,
            function(index) {
                //捉到所有被选中的，发异步进行删除
                layer.msg('删除成功', {
                    icon: 1
                });
                $(".layui-form-checked").not('.header').parents('tr').remove();
            });
        }</script>
</html>