! function (a) {
    "function" == typeof define && define.amd ? define(["jquery", "jquery.validate.min"], a) : a(jQuery)
}(function (a) {
//  var icon = "<i class='fa fa-times-circle'></i>  ";
	var icon = "";
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
    }
});

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

$().ready(function() {
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
	
});


(function($) {

	// 消息框
	var $message;
	$.message = function() {
		var message = {};
		if ($.isPlainObject(arguments[0])) {
			message = arguments[0];
		} else if (typeof arguments[0] === "string" && typeof arguments[1] === "string") {
			message.type = arguments[0];
			message.content = arguments[1];
		} else {
			return false;
		}
		
		if (message.type == null || message.content == null) {
			return false;
		}
		
		swal({
		    title: message.content,
		    type: message.type,
		    timer: 2000,
		    allowOutsideClick:true,
			showConfirmButton: false
		});
	
		return $message;
	};

})(jQuery);



