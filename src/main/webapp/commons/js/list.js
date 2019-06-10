function optionFormater( value, row, index ){
	var viewButton = '<a href="view/'+ row.id +'" class="btn btn-white btn-sm" title="查看"><i class="fa fa-eye"></i></a>&nbsp;&nbsp;';
	var editButton = '<a href="update/'+ row.id +'" class="btn btn-info btn-sm" title="编辑"><i class="fa fa-edit"></i></a>';
	var buttons = '';
	
	if( typeof(canDoEdit) != "undefined"){
		if( canDoEdit ){
			buttons += editButton + "&nbsp;&nbsp;"
		}
	}
	if( typeof( canDoView ) != "undefined" ){
		if( canDoView){
			buttons += viewButton;
		}
	}
	return buttons;
}

function booleanFormater( value, row, index){
	if( value ){
		return '<span class="fa-stack fa-lg" style="color:#18a689"><i class="fa fa-circle fa-stack-2x "></i><i class="fa fa-check fa-stack-1x fa-inverse"></i></span>';
	}else{
		return '<span class="fa-stack fa-lg" style="color:red"><i class="fa fa-circle fa-stack-2x "></i><i class="fa fa-close fa-stack-1x fa-inverse"></i></span>';
	}
}
function imageFormater( value, row, index ){
	if( value == undefined){
		return "";
	}
	return "<img src='" + value + "' class='img-circle' width='36px' height='36px'/>";
}

function dataQueryParams( params ){
	$('#toolbar').find('input[name]').each(function () {
        params[$(this).attr('name')] = $(this).val();
    });
	$("#toolbar").find("select").each(function(){
		 params[$(this).attr('name')] = $(this).val();
	})
    return params;
}

/**
 * Bootstrap Table Chinese translation
 * Author: Zhixin Wen<wenzhixin2010@gmail.com>
 */
(function ($) {
    'use strict';

    $.fn.bootstrapTable.locales['zh-CN'] = {
        formatLoadingMessage: function () {
            return '正在努力地加载数据中，请稍候……';
        },
        formatRecordsPerPage: function (pageNumber) {
            return '每页显示 ' + pageNumber + ' 条记录';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
        },
        formatSearch: function () {
            return '搜索';
        },
        formatNoMatches: function () {
            return '没有找到匹配的记录';
        },
        formatPaginationSwitch: function () {
            return '隐藏/显示分页';
        },
        formatRefresh: function () {
            return '刷新';
        },
        formatToggle: function () {
            return '切换布局';
        },
        formatColumns: function () {
            return '显示更多';
        }
    };

    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

})(jQuery);



(function(document, window, $) {
  	'use strict';
	(function() {
		var tableComplete = true;
   
	var $listForm = $("#listForm");
	var $searchButton = $("#searchButton");
	var $batchDelete = $(".batch-delete");
	var $clearInput = $(".clearInput");
	var $toolbar = $("#toolbar input")
	
	$searchButton.click(function(){
		if(tableComplete){
			tableComplete = false;
			$listForm.bootstrapTable('refresh');
		}
	});
	
	$toolbar.focus( function(){
		$(this).next().show();
	}).keyup( function( event){
		var value = $(this).val( );
		
		if( $.trim( value ).length == 0 ){
			return;
		}
		
		if( event.keyCode == 13 ){
			$listForm.bootstrapTable('refresh');
		}
	})
	
	$clearInput.click( function(){
		$(this).hide().prev().val("");
	})
	
	$batchDelete.click( function(){
		
		var _this = $(this);
		
		if( _this.hasClass("disabled")){
			return;
		}
		
		var url = _this.attr("url");
		if( !url ){
			url = "delete";
		}
		
		swal({
            title: "您确定要删除勾选的数据吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "是的，我要删除！",
            cancelButtonText: "让我再考虑一下…",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
        	
            if (isConfirm) {
            	// 发送ajax数据
            	var ids = $.map($listForm.bootstrapTable('getSelections'), function (row) {
 	                return  row.id
 	            });
            	
            	var idsArray = ids.toString().split(",");
            	var data = "";
            	for( var i = 0; i < idsArray.length; i++ ){
            		data += "ids=" + idsArray[i] + "&";
            	}
            	
            	$.ajax({
            		url:url,
            		type: "POST",
					data: data,
					dataType: "json",
					cache: false,
					beforeSend:function(){
						$(".confirm").prop("disabled", true);
					},
            		success:function( message ){
            			if( message.type == "success"){
            				 swal({
        	                    title: "删除成功！",
        	                    text: "您已经永久删除了这条信息。",
        	                    type: "success",
        	                   	timer: 2000,
        	  					showConfirmButton: false
        	                });
            				 $listForm.bootstrapTable('remove', { field: 'id',  values: ids });
            		         _this.addClass("disabled");
            			}else{
            				 swal({
         	                    title: "删除失败",
         	                    text: message.content,
         	                    type: message.type,
         	                   	timer: 2000,
         	  					showConfirmButton: false
         	                });
            			}
            		},
            		error: function (XMLHttpRequest, textStatus, errorThrown) {
            			 swal({
      	                    title: "删除失败",
      	                    text: textStatus + "|" + errorThrown,
      	                    type: "error",
      	                   	timer: 2000,
      	  					showConfirmButton: false
      	                });
            		},
            		complete:function(){
            			$(".confirm").prop("disabled", false);
            		}
            		
            	})
            } 
        });
		
	})
	
	$(".scroll-div a").click( function(){
		var _this = $(this);
		var id = _this.attr("name");
		var value = _this.attr("val");
		var multiple = _this.attr("multiple");
		var $id = $("#" + id );
		var $toolbar = $("#toolbar .form-inline");
		
		if( multiple ){
			var idVal = _this.attr("idVal");
			
			
			if( idVal == 0 ){
				$(".multipleHiddenInputs").remove( );
				_this.parent().parent().find("a").removeClass("btn-primary").addClass("btn-link");
				_this.parent().parent().find(".multipleAll").removeClass("btn-link").addClass("btn-primary");
			}else{
				_this.parent().parent().find(".multipleAll").removeClass("btn-primary").addClass("btn-link");
				
				if( _this.hasClass("btn-primary")){
					_this.removeClass("btn-primary").addClass("btn-link");
					$("#" + id + "_" + idVal).remove( );
					
				}else{
					_this.removeClass("btn-link").addClass("btn-primary");
					$toolbar.append('<input type="hidden" name="'+ id + "[" + ( idVal - 1 )+  ']" id="' + id + "_" + idVal + '" value="' + value + '" class="multipleHiddenInputs">');
				}
			}
			
			
		}else{
			
			if( _this.hasClass("btn-primary")){
				return;
			}
			
			_this.parent().parent().find("a").removeClass("btn-primary").addClass("btn-link");
			_this.removeClass("btn-link").addClass("btn-primary");
			$id.val( value );
		}

		$listForm.bootstrapTable('refresh');
		
	})
	
	$listForm.on('all.bs.table', function(e, name, args) {
	    //console.log('Event:', name, ', data:', args);
	})
	.on('click-row.bs.table', function(e, row, $element) {
	    //$result.text('Event: click-row.bs.table');
	})
	.on('dbl-click-row.bs.table', function(e, row, $element) {
	   //$result.text('Event: dbl-click-row.bs.table');
	})
	.on('sort.bs.table', function(e, name, order) {
	   //$result.text('Event: sort.bs.table');
	})
    .on('check.bs.table', function(e, row) {
      	var ids = $.map($listForm.bootstrapTable('getSelections'), function (row) {
           return row.id;
        });
        
        if( ids.length > 0 ){
        	$(".batch-delete").removeClass("disabled");
        }
    })
  	.on('uncheck.bs.table', function(e, row) {
	  	var ids = $.map($listForm.bootstrapTable('getSelections'), function (row) {
	       return row.id;
	    });
	    
	    if( ids.length <= 0 ){
	    	$(".batch-delete").addClass("disabled");
	    }
  	})
  	.on('check-all.bs.table', function(e) {
  		var ids = $.map($listForm.bootstrapTable('getSelections'), function (row) {
           return row.id;
        });
        
        if( ids.length > 0 ){
        	$(".batch-delete").removeClass("disabled");
        }
  	})
  	.on('uncheck-all.bs.table', function(e) {
	    var ids = $.map($listForm.bootstrapTable('getSelections'), function (row) {
	       return row.id;
	    });
	    
	    if( ids.length <= 0 ){
	    	$(".batch-delete").addClass("disabled");
	    }
  	})
  	.on('load-success.bs.table', function(e, data) {
		tableComplete = true;
   		//$result.text('Event: load-success.bs.table');
  	})
  	.on('load-error.bs.table', function(e, status) {
    	//$result.text('Event: load-error.bs.table');
  	})
  	.on('column-switch.bs.table', function(e, field, checked) {
    	//$result.text('Event: column-switch.bs.table');
  	})
  	.on('page-change.bs.table', function(e, size, number) {
    	//$result.text('Event: page-change.bs.table');
  	})
	.on('refresh.bs.table', function(e, size, number) {
		//$result.text('Event: page-change.bs.table');
		tableComplete = false;
	})
  	.on('search.bs.table', function(e, text) {
    	//$result.text('Event: search.bs.table');
  	});
  })();
})(document, window, jQuery);
