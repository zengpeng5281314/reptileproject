if ($.validator != null) {
	! function (a) {
	    "function" == typeof define && define.amd ? define(["jquery", "jquery.validate.min"], a) : a(jQuery)
	}(function (a) {
	    var icon = "<i class='fa fa-times-circle'></i>  ";
	    a.extend(a.validator.messages, {
	        required: icon + "必填",
	        remote: icon + "请修正此栏位",
	        email: icon + "请输入有效的电子邮件",
	        url: icon + "请输入有效的网址",
	        date: icon + "请输入有效的日期",
	        dateISO: icon + "请输入有效的日期 (YYYY-MM-DD)",
	        number: icon + "请输入正确的数字",
	        digits: icon + "只能输入数字",
	        creditcard: icon + "请输入有效的信用卡号码",
	        equalTo: icon + "你的输入不相同",
	        extension: icon + "请输入有效的后缀",
	        maxlength: a.validator.format(icon + "最多 {0} 个字"),
	        minlength: a.validator.format(icon + "最少 {0} 个字"),
	        rangelength: a.validator.format(icon + "请输入长度为 {0} 至 {1} 之间的字串"),
	        range: a.validator.format(icon + "请输入 {0} 至 {1} 之间的数值"),
	        max: a.validator.format(icon + "请输入不大于 {0} 的数值"),
	        min: a.validator.format(icon + "请输入不小于 {0} 的数值")
	    })
	});
	
	$.validator.setDefaults({
	    highlight: function (element) {
	        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	    },
	    success: function (element) {
	        element.closest('.form-group').removeClass('has-error').addClass('has-success');
	    },
	    errorElement: "span",
	    errorPlacement: function (error, element) {
	        if (element.is(":radio") || element.is(":checkbox")) {
	            error.appendTo(element.parent().parent().parent());
	        } else {
	            error.appendTo(element.parent());
	        }
	    },
	    errorClass: "help-block m-b-none",
	   /* validClass: "help-block m-b-none",*/
	    ignore:"",
	    submitHandler: function(form) {
			$(form).find("button:submit").prop("disabled", true);
			form.submit();
		}
	});
}
if (top == this) {
    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
    $('body').append(gohome);
}

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

(function($) {

	// 消息框
	var $message;
	$.message = function() {
		var message = {};
		if ($.isPlainObject(arguments[0])) {
			message = arguments[0];
			message.type = "info";
		} else if (typeof arguments[0] === "string" && typeof arguments[1] === "string") {
			message.type = arguments[0];
			message.content = arguments[1];
		}else if( typeof arguments[0] === "string" && typeof arguments[1] === "string" && typeof arguments[2] === "number" ){
			message.type = arguments[0];
			message.content = arguments[1];
			message.timer = arguments[ 2 ];
		} else {
			return false;
		}
		
		if (message.type == null || message.content == null) {
			return false;
		}
	
		var timer = 2000;
		
		if( message.timer != null && message.timer == 0){
			timer = null;
		}
		swal({
		    title: message.content,
		    type: message.type,
		    timer: timer,
		    allowOutsideClick:true,
			showConfirmButton: false
		});
	
		return $message;
	};
	
	// 对话框
	$.dialog = function(options) {
		var settings = {
			title:null,
			text:"",
			type:"warning",
			showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: true,
            url:null,
            data:null,
            method:"POST",
            dataType:"json"
		};
		$.extend(settings, options);
		
		if (settings.title == null || settings.url == null) {
			return false;
		}
		
		swal({
            title: settings.title,
            text: settings.text,
            type: settings.type,
            showCancelButton: settings.showCancelButton,
            confirmButtonColor: settings.confirmButtonColor,
            confirmButtonText: settings.confirmButtonText,
            cancelButtonText: settings.cancelButtonText,
            closeOnConfirm: settings.closeOnConfirm,
            closeOnCancel: settings.closeOnCancel
        },
        function (isConfirm) {
            if (isConfirm) {
            	$.ajax({
            		url:settings.url,
            		type: settings.method,
					data: settings.data,
					dataType: settings.dataType,
					cache: false,
            		success:function( message ){
            			if (settings.success && typeof settings.success == "function") {
            				settings.success( message );
        				}
            		}
            	})
            } 
        });
	};
	
	
	
	
	
	
	/*
	$.fn.extend({
		// 文件上传
		uploader: function(options) {
			var settings = {
				url: '/lutoudai-admin/admin/file/upload',
				fileType: "image",
				fileName: "file",
				data: {},
				maxSize: 10,
				extensions: null,
				before: null,
				complete: null
			};
			$.extend(settings, options);
			
			if (settings.extensions == null) {
				switch(settings.fileType) {
					case "media":
						settings.extensions = 'swf,flv,mp3,wav,avi,rm,rmvb,mp4';
						break;
					case "file":
						settings.extensions = 'zip,rar,7z,doc,docx,xls,xlsx,ppt,pptx';
						break;
					case "avatar":
						settings.extensions = 'jpg,jpeg,png';
					default:
						settings.extensions = 'jpg,jpeg,bmp,gif,png';
				}
			}
			
			var $progressBar = $('<div class="progressBar"><\/div>').appendTo("body");
			return this.each(function() {
				var element = this;
				var $element = $(element);
				
				var webUploader = WebUploader.create({
					swf: '/lutoudai-admin/resources/admin/js/plugins/webuploader/webuploader.swf',
					server: settings.url + (settings.url.indexOf('?') < 0 ? '?' : '&') + 'fileType=' + settings.fileType + '&token=' + getCookie("token"),
					pick: {
						id: element,
						multiple: false
					},
					fileVal: settings.fileName,
					formData: settings.data,
					fileSingleSizeLimit: settings.maxSize * 1024 * 1024,
					accept: {
						extensions: settings.extensions
					},
					fileNumLimit: 1,
					auto: true
				}).on('beforeFileQueued', function(file) {
					if ($.isFunction(settings.before) && settings.before.call(element, file) === false) {
						return false;
					}
					if ($.trim(settings.extensions) == '') {
						this.trigger('error', 'Q_TYPE_DENIED');
						return false;
					}
					this.reset();
					$progressBar.show();
					
					$.message("info", "正在上传", 3000, false );
					
				}).on('uploadProgress', function(file, percentage) {
					$progressBar.width(percentage * 100 + '%');
				}).on('uploadAccept', function(file, data) {
					$progressBar.fadeOut("slow", function() {
						$progressBar.width(0);
					});
					if (data.message.type != 'success') {
						$.message(data.message);
						return false;
					}
					$element.prev("input:text").val(data.url);
					if ($.isFunction(settings.complete)) {
						settings.complete(element, file, data);
					}
				}).on('error', function(type) {
					switch(type) {
						case "F_EXCEED_SIZE":
							$.message("warn", "上传文件大小超出限制");
							break;
						case "Q_TYPE_DENIED":
							$.message("warn", "上传文件格式不正确");
							break;
						default:
							$.message("warn", "上传文件出现错误");
					}
				});
				
				$element.mouseover(function() {
					webUploader.refresh();
				});
			});
		},
		
		// 编辑器
		editor: function(options) {
			window.UEDITOR_CONFIG = {
				UEDITOR_HOME_URL: '/lcyy-admin/resources/admin/ueditor/',
				serverUrl: '/lcyy-admin/admin/file/upload',
				imageActionName: "uploadImage",
				imageFieldName: "file",
				imageMaxSize: 10485760,
				imageAllowFiles: ['.jpg', '.jpeg', '.bmp', '.gif', '.png'],
				imageUrlPrefix: "",
				imagePathFormat: "",
				imageCompressEnable: false,
				imageCompressBorder: 1600,
				imageInsertAlign: "none",
				videoActionName: "uploadMedia",
				videoFieldName: "file",
				videoMaxSize: 10485760,
				videoAllowFiles: ['.swf', '.flv', '.mp3', '.wav', '.avi', '.rm', '.rmvb'],
				videoUrlPrefix: "",
				videoPathFormat: "",
				fileActionName: "uploadFile",
				fileFieldName: "file",
				fileMaxSize: 10485760,
				fileAllowFiles: ['.zip', '.rar', '.7z', '.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx'],
				fileUrlPrefix: "",
				filePathFormat: "",
				toolbars: [[
					'fullscreen', 'source', '|',
					'undo', 'redo', '|',
					'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|',
					'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
					'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
					'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
					'directionalityltr', 'directionalityrtl', 'indent', '|',
					'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
					'touppercase', 'tolowercase', '|',
					'link', 'unlink', 'anchor', '|',
					'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
					'insertimage', 'insertvideo', 'attachment', 'map', 'insertframe', 'pagebreak', '|',
					'horizontal', 'date', 'time', 'spechars', '|',
					'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|',
					'print', 'preview', 'searchreplace', 'drafts'
				]],
				lang: 'zh_CN',
				iframeCssUrl: null,
				pageBreakTag: 'lcyy_page_break_tag',
				wordCount: false
			};
			
			UE.Editor.prototype.getActionUrl = function(action) {
				var serverUrl = this.getOpt('serverUrl');
				switch(action) {
					case "uploadImage":
						return serverUrl + (serverUrl.indexOf('?') < 0 ? '?' : '&') + 'fileType=image';
					case "uploadMedia":
						return serverUrl + (serverUrl.indexOf('?') < 0 ? '?' : '&') + 'fileType=media';
					case "uploadFile":
						return serverUrl + (serverUrl.indexOf('?') < 0 ? '?' : '&') + 'fileType=file';
				}
				return null;
			};
			
			UE.Editor.prototype.loadServerConfig = function() {
				this._serverConfigLoaded = true;
			};
			
			return this.each(function() {
				var element = this;
				var $element = $(element);
				
				UE.getEditor($element.attr("id"), options).ready(function() {
					this.execCommand("serverparam", {
						token: getCookie("token")
					});
				});
			});
		}
	
	});
	*/
	

})(jQuery);

//添加Cookie
function addCookie(name, value, options) {
	if (arguments.length > 1 && name != null) {
		if (options == null) {
			options = {};
		}
		if (value == null) {
			options.expires = -1;
		}
		if (typeof options.expires == "number") {
			var time = options.expires;
			var expires = options.expires = new Date();
			expires.setTime(expires.getTime() + time * 1000);
		}
		if (options.path == null) {
			options.path = "/";
		}
		if (options.domain == null) {
			options.domain = "";
		}
		document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires != null ? "; expires=" + options.expires.toUTCString() : "") + (options.path != "" ? "; path=" + options.path : "") + (options.domain != "" ? "; domain=" + options.domain : "") + (options.secure != null ? "; secure" : "");
	}
}

// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}

// 移除Cookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}

//货币格式化
function currency(value, showSign, showUnit) {
	if (value != null) {
			var price = (Math.round(value * Math.pow(10, 2)) / Math.pow(10, 2)).toFixed(2);
		if (showSign) {
			price = '￥' + price;
		}
		if (showUnit) {
			price += '元';
		}
		return price;
	}
}

function currentTime(){
	var now = new Date( );
	
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var day = now.getDate();
	var hours = now.getHours();
	var minutes = now.getMinutes();
	var second = now.getSeconds();
	
	if( day < 10 ){
		day = "0" + day;
	}
	
	if( hours < 10 ){
		hours = "0" + hours;
	}
	
	if( minutes < 10 ){
		minutes = "0" + minutes;
	}
	
	if( second < 10 ){
		second = "0" + second;
	}
	
	return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + second;
	
}

/**
 * 获取倒计时
 * @param countDownTime
 * 倒计时时间-单位毫秒
 */
function countDown( countDownTime, params ){
	
	var day = Math.floor(countDownTime/1000/60/60/24);  
    var hour = Math.floor(countDownTime/1000/60/60%24);  
    var minute = Math.floor(countDownTime/1000/60%60);  
    var second = Math.floor(countDownTime/1000%60); 
    if( !params ){
    	return {"day":day, "hour": hour, "minute": minute, "second": second};
    }
    
    if( params.day ){
    	$(params.day).html( day );
    }
    
    if( params.hour ){
    	$(params.hour).html( hour );
    }
    
    if( params.minute ){
    	$(params.minute).html( minute );
    }
    
    if( params.second ){
    	$(params.second).html( second );
    }
}

$().ready(function() {

	// AJAX全局设置
	$.ajaxSetup({
		traditional: true
	});
	
	// 令牌
	$(document).ajaxSend(function(event, request, settings) {
		if (!settings.crossDomain && settings.type != null && settings.type.toLowerCase() == "post") {
			var token = getCookie("token");
			if (token != null) {
				request.setRequestHeader("token", token);
			}
		}
	});
	
	$(document).ajaxError( function(event, XMLHttpRequest, ajaxOptions, thrownError){
		var error = "Status:" + XMLHttpRequest.status + "，StatusText:" + XMLHttpRequest.statusText
		$.message("error", error );
	});
	
	// 令牌
	$("form").submit(function() {
		var $this = $(this);
		if ($this.attr("method") != null && $this.attr("method").toLowerCase() == "post" && $this.find("input[name='token']").size() == 0) {
			var token = getCookie("token");
			if (token != null) {
				$this.append('<input type="hidden" name="token" value="' + token + '" \/>');
			}
		}
	});
	
	// 状态
	$(document).ajaxComplete(function(event, request, settings) {
		var tokenStatus = request.getResponseHeader("tokenStatus");
		var validateStatus = request.getResponseHeader("validateStatus");
		var loginStatus = request.getResponseHeader("loginStatus");
		var authenticationStatus = request.getResponseHeader("authenticationStatus");
		var expiredStatus = request.getResponseHeader("expiredStatus");
		
		if (tokenStatus == "accessDenied") {
			var token = getCookie("token");
			if (token != null) {
				$.extend(settings, {
					global: false,
					headers: {token: token}
				});
				$.ajax(settings);
			}
		} else if (validateStatus == "accessDenied") {
			$.message("warning", "非法字符");
		} else if (loginStatus == "accessDenied") {
			$.message("warning", "登录超时，请重新登录");
			setTimeout(function() {
				location.reload(true);
			}, 2000);
		} else if (loginStatus == "unauthorized") {
			$.message("warning", "对不起，您无此操作权限！");
		}else if( expiredStatus == "expired"){
			$.message("warning", "对不起，您的会员已过期，请重新激活！");
			
		}else if( authenticationStatus == "disabled" ){
			$.message("warning", "对不起，该认证项目不存在或者已禁用！");
		}else if( authenticationStatus == "customerIsNotExists" ){
			$.message("warning", "对不起，该客户不存在或已删除！");
		}else if( authenticationStatus == "dataIsEmpty"){
			$.message("warning", "对不起，无法获取对应数据！");
		}else if( authenticationStatus == "realnameAuthenticated" ){
			$.message("warning", "对不起，不可重复进行实名认证！");
		}else if( authenticationStatus == "orderIsError"){
			$.message("warning", "对不起，请按照顺序来完成认证！");
		}else if( authenticationStatus == "projectIsNotExpired"){
			$.message("warning", "对不起，当前认证项目未过期，请勿重复认证！");
		}
		
		
	});
});

var os = function() {  
    var ua = navigator.userAgent,  
    isWindowsPhone = /(?:Windows Phone)/.test(ua),  
    isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,   
    isAndroid = /(?:Android)/.test(ua),   
    isFireFox = /(?:Firefox)/.test(ua),   
    isChrome = /(?:Chrome|CriOS)/.test(ua),  
    isTablet = /(?:iPad|PlayBook)/.test(ua) || (isAndroid && !/(?:Mobile)/.test(ua)) || (isFireFox && /(?:Tablet)/.test(ua)),  
    isPhone = /(?:iPhone)/.test(ua) && !isTablet,  
    isPc = !isPhone && !isAndroid && !isSymbian;  
    return {  
         isTablet: isTablet,  
         isPhone: isPhone,  
         isAndroid : isAndroid,  
         isPc : isPc  
    };  
}();  

/**
 * 判断是否是浮点数
 * @param value
 * 值
 * @param param
 * 参数
 * @returns
 */
function isNumber( value, param ){
	return new RegExp("^-?\\d{1," + (param.integer != null ? param.integer : "") + "}" + (param.fraction != null ? (param.fraction > 0 ? "(\\.\\d{1," + param.fraction + "})?$" : "$") : "(\\.\\d+)?$")).test(value);
}

function popoverModal( obj ){
	var _this = $(obj);
	var $popoverModalContent = $("#popoverModalContent");
	
	$popoverModalContent.empty();
	
	var dataType = _this.attr("data-type");
	var dataContent = _this.attr("data-content");
	if( dataType == "url"){
		$popoverModalContent.append('<iframe src="' + dataContent + '" frameborder="0" width="100%" height="500"></iframe>');
	}else if( dataType = "text"){
		$popoverModalContent.append("");
	}
 	
 	$("#popoverModal").modal("show");
}



