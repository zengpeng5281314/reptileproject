<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>商家推广子链接管理</title>
<%@include file="/WEB-INF/views/common/base.jsp"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<meta name="author" content="" />
<meta name="copyright" content="" />
<link rel="shortcut icon" href="/favicon.ico">
<link href="${path}/commons/css/bootstrap.min.css" rel="stylesheet">
<link href="${path}/commons/css/font-awesome.css" rel="stylesheet">
<link href="${path}/commons/css/bootstrap-table.css" rel="stylesheet">
<link href="${path}/commons/css/sweetalert.css" rel="stylesheet">
<link href="${path}/commons/css/animate.css" rel="stylesheet">
<link href="${path}/commons/css/style.css" rel="stylesheet">
<link href="${path}/commons/css/simplealert.css" rel="stylesheet">
<link href="${path}/commons/css/uialertview-1.0.0.css" rel="stylesheet">
<style type="text/css">
    .rz-btn{ display: inline-block; padding: 3px 5px; background-color: #3b7ee9; color: #fff; border-radius: 3px; margin-top: 5px;}
    .my-modal {display: none;position: fixed; top: 0px; left: 0px; right: 0px; bottom: 0px; background-color: rgba(0, 0, 0, 0.5);}
</style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox">
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <div class="example">
                            <div id="toolbar">
                                <div class="form-inline" role="form">

                                    <div class="form-group search-bar">
                                        <input name="linkName" class="form-control" type="text" value="" placeholder="链接名称">
                                        <i class="fa fa-close clearInput"></i>
                                    </div>
                                    <input type="hidden" name="selections[0].property" value="id"/>
                                    <input type="hidden" name="selections[0].alias" value="id"/>
                                    <input type="hidden" name="selections[1].property" value="createDate"/>
                                    <input type="hidden" name="selections[1].alias" value="createDate"/>
                                    <input type="hidden" name="selections[2].property" value="identification"/>
                                    <input type="hidden" name="selections[2].alias" value="identification"/>
                                    <input type="hidden" name="selections[3].property" value="linkName"/>
                                    <input type="hidden" name="selections[3].alias" value="linkName"/>
                                    <input type="hidden" name="selections[4].property" value="url"/>
                                    <input type="hidden" name="selections[4].alias" value="url"/>
                                    <input type="hidden" name="selections[5].property" value="iosUrl"/>
                                    <input type="hidden" name="selections[5].alias" value="iosUrl"/>
                                    <input type="hidden" name="selections[6].property" value="androidUrl"/>
                                    <input type="hidden" name="selections[6].alias" value="androidUrl"/>
                                    <input type="hidden" name="selections[7].property" value="deduction"/>
                                    <input type="hidden" name="selections[7].alias" value="deduction"/>
                                    <input type="hidden" name="selections[8].property" value="state"/>
                                    <input type="hidden" name="selections[8].alias" value="state"/>

                                    <div class="form-group">
                                        <button id="searchButton" type="button" class="btn btn-white">搜索</button>
                                    </div>
                                </div>
                            </div>

                            <div class="btn-group" id="listFormEventToolbar" role="group">
                            </div>

                            <table id="listForm"
                                   data-toggle="table"
                                   data-url="list"
                                   data-show-columns="true"
                                   data-show-toggle="true"
                                   data-show-refresh="true"
                                   data-pagination="true"
                                   data-search="false"
                                   data-toolbar="#listFormEventToolbar"
                                   data-click-to-select="true"
                                   data-side-pagination="server"
                                   data-content-type="application/x-www-form-urlencoded"
                                   data-mobile-responsive="true"
                                   data-icon-size="outline"
                                   data-height="500"
                                   data-page-number="1"
                                   data-query-params-type=""
                                   data-method="post"
                                   data-query-params="dataQueryParams"
                                   class="table table-hover table-condensed table-striped"
                            >
                                <thead>
                                <tr>
                                    <th data-checkbox="true"></th>
                                    <th data-field="id" data-sortable="false" >ID</th>
                                    <th data-field="channleInfoPo.channelMark" data-sortable="false" >子链接标识</th>
                                    <th data-field="channleInfoPo.channelName" data-sortable="false" >链接名称</th>
                                    <th data-field="channleInfoPo.chennelAddress" data-sortable="false" >渠道链接</th>
                                    <th data-field="status" data-formatter="stateFormatter" >链接状态</th>
                                    <th data-field="createTime" data-sortable="false" >创建日期</th>
                                    <th data-field="option" data-formatter="optionFormater">操作</th>
                                </tr>

                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 二维码模态框（Modal） -->

    <div id="myModal" class="my-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        链接二维码
                    </h4>
                </div>
                <div class="modal-body" >
                    <h2 style="text-align: center;">我的二维码</h2>
                    <div style="width: 150px; margin: 0 auto" class="form-control-static" id="qrcode"></div>
                </div>
                <div class="modal-footer" style="text-align: center;">
                    <button style="background-color: #101010;" type="button" class="btn btn-default" onclick="downloadQrcode()">下载</button>
                    <button type="button" class="btn btn-default" onclick="hideMode()">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>


<!-- 二维码模态框（Modal） -->

<div id="my" class="my-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">
                </button>
                <input type="hidden"  id="linkId">
                <h4 class="modal-title" id="myModalLabel">
                    渠道扣量
                </h4>
            </div>
            <div class="modal-body" >
                <h2 style="text-align: center;">扣量百分比</h2>
                   <input type="number" maxlength="4" min="0" max="1" id="kou">
            </div>
            <div class="modal-footer" style="text-align: center;">
                <button style="background-color: #101010;" type="button" class="btn btn-default" onclick="kou()">更新</button>
                <button type="button" class="btn btn-default" onclick="hideMode()">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<script>
                    var businessLook = true;

        var BASE = "${path}";
</script>
<script src="${path}/commons/js/jquery.min.js"></script>
<script src="${path}/commons/js/bootstrap.min.js"></script>
<script src="${path}/commons/js/sweetalert.min.js"></script>
<script src="${path}/commons/js/laydate.js"></script>
<script src="${path}/commons/js/common.js"></script>
<script src="${path}/commons/js/bootstrap-table.min.js"></script>
<script src="${path}/commons/js/bootstrap-table-mobile.min.js"></script>
<script src="${path}/commons/js/list.js"></script>
<script src="${path}/commons/js/simplealert.js"></script>
<script src="${path}/commons/js/jquery.uialertview-1.0.0.js"></script>
<script src="${path}/commons/js/qrcode.js"></script>
<script>
    function optionFormater( value, row, index ){
        var viewButton = '<a href="javascript:;" class="btn btn-warning btn-sm"  onclick="view('+row.id+')" title="查看" >查看</a>&nbsp;&nbsp;';
        var editButton = '<a href="edit/'+ row.id +'" class="btn btn-info btn-sm" title="编辑"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;';
        var qRCodeButtonOne= '<a href="javascript:;" class="btn btn-warning btn-sm"  onclick=\'qrCode("'+row.url+'")\' title="生成二维码" >二维码</a>&nbsp;&nbsp;';        var buttons = '';
        var dataButton = '<a href="javascript:;" class="btn btn-warning btn-sm"  onclick="data('+row.id+')" title="数据查看" >数据查看</a>&nbsp;&nbsp;';
        var robotPreliminaryButton = '<a href="javascript:;" class="btn btn-info btn-sm"  onclick="robotPreliminary('+row.id+')" title="一审数据查看" >一审数据查看</a>&nbsp;&nbsp;';
        var robotButton = '<a href="javascript:;" class="btn btn-info btn-sm"  onclick="robot('+row.id+')" title="二审数据查看" >二审数据查看</a>&nbsp;&nbsp;';
        var stateButton='<a href="javascript:;" class="btn btn-success btn-sm"  onclick="stateZero('+row.id+')" title="启用" >启用</a>&nbsp;&nbsp;';
        if(row.state=="0")
        {
            stateButton = '<a href="javascript:;" class="btn btn-danger btn-sm"  onclick="stateOne('+row.id+')" title="禁用" >禁用</a>&nbsp;&nbsp;';
        }

        if( typeof(canDoEdit) != "undefined"){
            if( canDoEdit ){
                buttons+=editButton;
            }
        }
        //渠道数据查看
        if(typeof(channelLook) != "undefined")
        {
            if(channelLook)
            {
                buttons+=dataButton;
            }
        }
        //链接启用和禁用
        if(typeof(linkState) != "undefined")
        {
            if(linkState)
            {
                buttons+=stateButton;
                buttons += '<a href="/business/link/distribution?linkId=' + row.id + '" target="_blank" class="btn btn-white btn-sm">分配</a>&nbsp;&nbsp;';
            }
        }
        //渠道机审数据
        if(typeof(robotLook) != "undefined")
        {
            if(robotLook)
            {
                buttons+=robotPreliminaryButton;
                buttons+=robotButton;
            }
        }
        //商家数据查看
        if(typeof(businessLook) != "undefined")
        {
            if(businessLook)
            {
                buttons+=viewButton;
            }
        }
        //二维码
        if(typeof(qcode) != "undefined")
        {
            if(qcode)
            {
                buttons+=qRCodeButtonOne;
            }
        }

        // if( typeof( canDoView ) != "undefined" ){
        //     if( canDoView){
        //         buttons += viewButton;
        //     }
        // }
        // if( typeof( canDoAuthenticationProject ) != "undefined" ){
        //     if( canDoAuthenticationProject){
        //         buttons += barsButton;
        //     }
        // }
        //直接在这里异步访问然后查询是否有审核记录
        return buttons
    }

    /**
     *查看数据
     */
    function view(channelId)
    {
        setCookie("channelId",channelId);
        window.location.href=BASE+'/channeldetailed/view?channelId='+channelId;
    }
    /**
     *数据查看
     */
    function data(linkId)
    {
        window.location.href=BASE+'/business/link/sub/data/list?linkId='+linkId;
    }
    /**
     *一审审核数据查看
     */
    function robotPreliminary(linkId)
    {
        window.location.href=BASE+'/business/link/sub/robot/preliminary/view?linkId='+linkId;
    }
    /**
     *二审审核数据查看
     */
    function robot(linkId)
    {
        window.location.href=BASE+'/business/link/sub/robot/view?linkId='+linkId;
    }

    //格式化状态
    function stateFormatter( value, row, index ){

        var va="启用";
       if(value==null)
       {
        return va;
       }
       else if(value=='0')
       {
           return "<font color='red'>禁用</font>";
       }
        return va;
    }
    // let btn = document.getElementById('btn');
    // btn.onclick = function () {
    //     myConfirm('正在检测', 'adasd', function() {
    //
    //     })
    // }
    //设置固定过期时间的cookies
    function setCookie(name,value){
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
    }
    //读取cookies
    function getCookie(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)"); //正则匹配
        if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
        }
        else{
            return null;
        }
    }
      //禁用
      function stateOne(linkId) {
          state(linkId,"1","禁用");
      }
      //启用
      function stateZero(linkId) {
          state(linkId,"0","启用");
      }
    //智能审核
    function state( linkId,state,msg){
        swal({
                    title: "系统操作提示",
                    text: "确定"+msg+"改链接吗？",
                    type: "info",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确认操作！",
                    cancelButtonText: "取消！",
                    closeOnConfirm: true,
                    closeOnCancel: true,
                    allowOutsideClick:true
                },
                function (isConfirm) {
                    if (isConfirm) {
                        $.ajax({
                            url:"../sub/state/update",
                            async:true,
                            beforeSend:function(){
                                myTip("系统操作中...");
                            },
                            data:{"linkId":linkId,"state":state},
                            type:"POST",
                            success:function(message){
                                if( message.type != "success"){
                                    $(".confirm").prop("disabled", false);
                                    myConfirm(message.type,message.content);
                                }else{
                                    $("#listForm").bootstrapTable('refresh');
                                }
                            },
                            complete:function() {
                                document.body.style.overflow = "auto";
                                document.body.removeChild(document.getElementById("mark"));
                            }
                        })
                    }
                });

    }
    /*
       *弹出确定框
       */
    function myConfirm(title, content, callback_ok){
        if(content && typeof content == "function") {
            callback_ok = content;
        }
        let mark = document.createElement('div');//创建遮罩层
        mark.style.position = 'absolute';
        mark.style.top = '0';
        mark.style.left = '0';
        mark.style.right = '0';
        mark.style.height = document.body.clientHeight > window.screen.height ? document.body.clientHeight+'px':'100vh';
        mark.style.background = 'rgba(0, 0, 0, 0.3)';
        mark.style.zIndex = '999';
        document.body.style.overflow = 'hidden';
        document.body.insertBefore(mark,document.body.children[0]);

        let confirmBox = document.createElement('div');//创建确认框
        confirmBox.style.position = 'fixed';
        confirmBox.style.top = '50%';
        confirmBox.style.left = '50%';
        confirmBox.style.transform = 'translate(-50%,-50%)';
        confirmBox.style.backgroundColor = '#fff';
        confirmBox.style.borderRadius = '5px';
        confirmBox.style.zIndex = '1000';
        confirmBox.style.overflow = 'hidden';
        mark.appendChild(confirmBox);

        let  confirmTitle = document.createElement('p');//创建弹出标题
        confirmTitle.style.padding = '20px 15px 15px';
        confirmTitle.style.color = '#3c3b3b';
        confirmTitle.style.textAlign = 'center';
        confirmTitle.style.fontSize = '18px';
        confirmTitle.style.fontWeight = '600';
        confirmTitle.style.minWidth = '260px';
        confirmBox.appendChild(confirmTitle);
        confirmTitle.innerHTML = title;

        let  confirmContent = document.createElement('p');//创建弹出标题
        confirmContent.style.padding = '0px 15px 15px';
        confirmContent.style.color = '#3c3b3b';
        confirmContent.style.textAlign = 'center';
        confirmContent.style.fontSize = '18px';
        confirmContent.style.fontWeight = '600';
        confirmContent.style.minWidth = '260px';
        confirmBox.appendChild(confirmContent);
        if(content && typeof content == "string") {
            confirmContent.innerHTML = content;
        }
        let  trueFalse = document.createElement('div');//创建确认取消按钮
        trueFalse.style.height = '37px';
        trueFalse.style.lineHeight = '37px';
        trueFalse.style.fontWeight = '600';
        trueFalse.style.fontFamily = '华文琥珀';
        trueFalse.style.textAlign = 'center';
        confirmBox.appendChild(trueFalse);

        let  cTrue = document.createElement('div');//确认按钮
        cTrue.innerHTML = '确定';
        cTrue.style.width = '40%';
        cTrue.style.borderRadius = '5px';
        cTrue.style.margin = '0 auto';
        cTrue.style.color = '#fff';
        cTrue.style.backgroundColor = '#4986ea';

        trueFalse.appendChild(cTrue);

        cTrue.onclick = function(){
            if(callback_ok && typeof callback_ok == "function"){
                callback_ok();
            }
            document.body.style.overflow = 'auto';
            document.body.removeChild(mark);
        }
    }


    function myTip(title) {
        let mark = document.createElement('div');//创建遮罩层
        mark.id = 'mark';
        mark.style.position = 'absolute';
        mark.style.top = '0';
        mark.style.left = '0';
        mark.style.right = '0';
        mark.style.height = document.body.clientHeight > window.screen.height ? document.body.clientHeight+'px':'100vh';
        mark.style.background = 'rgba(0, 0, 0, 0.1)';
        mark.style.zIndex = '999';
        document.body.style.overflow = 'hidden';
        document.body.insertBefore(mark, document.body.children[0]);

        let confirmBox = document.createElement('div');//创建确认框
        confirmBox.style.position = 'fixed';
        confirmBox.style.top = '20%';
        confirmBox.style.left = '50%';
        confirmBox.style.transform = 'translate(-50%,-50%)';
        confirmBox.style.backgroundColor = '#fff';
        confirmBox.style.borderRadius = '5px';
        confirmBox.style.zIndex = '1000';
        confirmBox.style.overflow = 'hidden';
        mark.appendChild(confirmBox);

        let  confirmTitle = document.createElement('p');//创建弹出标题
        confirmTitle.style.padding = '0 15px';
        confirmTitle.style.color = '#3c3b3b';
        confirmTitle.style.textAlign = 'center';
        confirmTitle.style.fontSize = '22px';
        confirmTitle.style.fontWeight = '600';
        confirmTitle.style.minWidth = '260px';
        confirmTitle.style.minHeight = '120px';
        confirmTitle.style.lineHeight = '120px';
        confirmBox.appendChild(confirmTitle);
        confirmTitle.innerHTML = title;
    }
    //生成二维码
    function qrCode(url) {
        console.log(typeof url);
        createQrcode(url);
        // $('#myModal').modal('show');
        $('#myModal').show();
    }
    /**
     * 显示二维码
     */
    function createQrcode(url){
        // url=url.replace("%2f","//")
        document.getElementById("qrcode").innerHTML = "";
        var qrcode = new QRCode(document.getElementById("qrcode"), {
            text: url.toString(),  //你想要填写的文本
            width: 150, //生成的二维码的宽度
            height: 150, //生成的二维码的高度
            colorDark : "#000000", // 生成的二维码的深色部分
            colorLight : "#ffffff", //生成二维码的浅色部分
            correctLevel : QRCode.CorrectLevel.H
        });
    }

    /**
     * 下载二维码
     */
    function downloadQrcode(){
        var qrcode = document.getElementById('qrcode');
        var img = qrcode.getElementsByTagName('img')[0];
        var link = document.createElement("a");
        var url = img.getAttribute("src");
        link.setAttribute("href",url);
        link.setAttribute("download",'二维码.png');
        link.click();
    }
    function hideMode() {
        $('#myModal').hide();
        $('#my').hide();
    }

    $().ready(function(){
        //日期范围限制
        var start = {
            elem: '#start',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: '2099-06-16 23:59:59', //最大日期
            istime: true,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };
        var end = {
            elem: '#end',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: '2099-06-16 23:59:59',
            istime: true,
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);
    })
    /**
     *扣量显示
     */
    function show(linkId,deduction)
    {
        alert(deduction);
        $("#linkId").val(linkId);
    }

</script>
</body>
</html>