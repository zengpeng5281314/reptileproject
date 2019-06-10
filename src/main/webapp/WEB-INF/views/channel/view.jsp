<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>列表</title>
<%@include file="/WEB-INF/views/common/base.jsp"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
<meta name="author" content="" />
 <link rel="stylesheet" type="text/css" href="${path}/commons/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/flatpickr.min.css"/>
    <script src="${path}/commons/js/vue.js"></script>
    <script src="${path}/commons/js/axios.min.js"></script>
    <script src="${path}/commons/js/qs.js"></script>

    <style type="text/css">
        body {
            font-size: 16px;
            background-color: #fff;
        }
        input::-webkit-input-placeholder {
            color: #999;
        }
        .date {
            padding: 7px 10px;
            border: 1px solid #e7eaec;
            border-radius: 5px;
            font-size: 16px;
            color: #999;
        }
        .search-wrap {
            padding: 30px 0 0 25px;
            margin-bottom: 50px;
        }
        /*IOS滚动条*/
        .datalist-wrap::-webkit-scrollbar {
            -webkit-appearance: none;
            width: 10px;
            height: 10px;
        }
        .datalist-wrap::-webkit-scrollbar-thumb {
            border-radius: 5px;
            border: 2px solid #fff;
            background-color: rgba(0, 0, 0, .3);
        }
        .datalist-wrap {
            max-height: 100vh;
            overflow: auto;
            padding: 25px;
        }
        .none-data {
            padding-top: 30px;
            color: #333;
            text-align: center;
        }
        .list-table {
            width: 100%;
            border-collapse:collapse;
        }
        .list-table thead {
            font-weight: bold;
            background-color: #fff;
            border-bottom:2px solid #ccc;
        }
        .list-table tbody {
            background-color: #f9f9f9;
            color: #999;
        }
        .list-table tr td, .list-table tr th {
            padding: 10px;
            white-space: nowrap;
            text-align: left;
            border: 1px solid #ddd;
            border-radius:2px;
            text-shadow:1px 1px 1px #fff;
        }
        .search {
            display: inline-block;
            border: 1px solid #e7eaec;
            border-radius: 5px;
            margin-left: 15px;
            padding: 7px 10px;
            vertical-align: middle;
            color: #999;
        }
        .details {
            border-radius: 5px;
            padding: 5px 10px;
            background-color: #1ab394;
            color: #fff;
            text-decoration: none;
            font-size: 14px;
            text-shadow: 0 0 0;
        }
        .back-btn {
            background-color: #535558;
            border-color: #535558;
            color: #FFFFFF;
            min-width: 110px;
            text-align: center;
            text-decoration: none;
            border-radius: 3px;
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            margin-left: 25px;
            font-size: 14px;
            font-weight: 400;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div id="app">
    <div class="search-wrap">
	        <input class="date" id="date" placeholder="请选择时间"/>
	        <div class="search" id ="search" onclick="search()">搜索</div>
    </div>
        <a class="back-btn" href="javascript:history.go(-1)">返回</a>

        <div class="datalist-wrap">
            <table class="list-table">
                <thead>
                <tr>
                    <th>时间</th>
                    <th>PV点击率</th>
                    <th>注册人数</th>
                    <th>完成认证人数</th>
                    <th>通过人数</th>
                    <th>放款人数</th>
                    <th>转换率</th>
                    <th>首逾率</th>
                    <th>操作</th>
                </tr>
                </thead>
                <div>
                    <tbody>
                    <c:forEach items="${list}" var="item">
	                    <tr>
		                    <td>${item.createTime}</td>
	                        <td>${item.clickNum}</td>
	                        <td>${item.registNum}</td>
	                        <td>${item.certificationNum}</td>
	                        <td>${item.passNum}</td>
	                        <td>${item.loanNum}</td>
	                        <td>${item.conversionRate}%</td>
	                        <td>${item.overdueRate}%</td>
	                        <td><a class="details" href="">详细</a></td>
	                    </tr>
                    </c:forEach>
                    </tbody>
                </div>
            </table>
            <c:if test="${total==0 }">
	            <div style="width: 100%;">
	                <div class="none-data">暂无数据!</div>
	            </div>
            </c:if>
        </div>

</div>
<script src="${path}/commons/js/jquery.min.js"></script>
<script src="${path}/commons/js/flatpickr.min.js"></script>
<script src="${path}/commons/js/laydate.js"></script>
<script>
	function search(){
		var date = $("#date").val();
		window.location.href ="/channeldetailed/view?startTime="+date+"&endTime="+date;
		
		 /* $.ajax({
 			type : "POST",
 			url : "/channeldetailed/view",
 			dataType : "json",
 			data : {
 				"date":date
 			},
 			success : function(res) {
 				if(res.success){
 					alert("ss");
 				}else{
 					alert(res.msg);
 				}
 			},
 			error : function(data) {
 				alert("登录失败");
 			} 
 		}); */
	}

    var app = new Vue({
        el: '#app',
        created() {
            setTimeout(() => {
                let _this = this
                //日期范围限制
                var start = {
                    elem: '#date',
                    format: 'YYYY-MM-DD',
                    max: '2099-06-16 23:59:59', //最大日期
                    istime: true,
                    istoday: false,
                    choose: function (datas) {
                        _this.date = datas;
                    }
                }
                laydate(start);
            }, 20)
            //this._getData()
        },
        data() {
            return {
                date: '',
                list: []
            }
        },
        methods: {
            _getData() {
                let params = window.Qs.stringify({'linkId': getCookie("linkId")})
                axios.post('/channeldetailed/viewlist', params)
                .then((res) => {
                    this.list = res.data
                    console.log(this.list);
                })
                .catch((error) => {
                    console.log(error);
                })
            },
            goSearch() {
                if (!this.date) {
                    return
                }
                let date = this.date
                let params = window.Qs.stringify({'linkId': getCookie("linkId"),"startDate":date})
                axios.post('/register/sub/view', params)
                .then((res) => {
                    this.list = res.data

                })
                .catch((error) => {
                    console.log(error);
                })
            }
        }
    }) 

    //     // 获取Cookie
    //     function getCookie(name) {
    //         if (name != null) {
    //             var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
    //             return value ? decodeURIComponent(value[1]) : null;
    //         }
    //     }
    // });
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
</script>
</body>
</html>