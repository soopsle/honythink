$.extend($.fn.datagrid.defaults.editors, {
    datetimebox: {// datetimebox就是你要自定义editor的名称
        init: function (container, options) {
            var input = $('<input class="easyuidatetimebox" />').appendTo(container);
            return input.datetimebox({
                formatter: function (date) {
                    return new Date(date).format("yyyy-MM-dd hh:mm:ss");
                }
            });
        },
        getValue: function (target) {
            return $(target).parent().find('input.combo-value').val();
        },
        setValue: function (target, value) {
            $(target).datetimebox("setValue", value);
        },
        resize: function (target, width) {
            var input = $(target);
            if ($.boxModel == true) {
                input.width(width - (input.outerWidth() - input.width()));
            } else {
                input.width(width);
            }
        }
    }
});
// 时间格式化
Date.prototype.format = function (format) {
    /*
    * eg:format="yyyy-MM-dd hh:mm:ss";
    */
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }

    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}

	$(document).ready(function() {
		$('#datagrid').datagrid({
			url:'list',
			rownumbers:true,
			singleSelect:false,
			pageSize:20, 
			pagination:true,
			multiSort:true,
			//autoRowHeight:false,
			editorHeight:24,
			//fitColumns:true,
			fit:true,
			queryParams : {//查询参数		
				//"a":b
			},
			columns:[[
			{field:'id',checkbox:true},
			{field:'action',title:'操作',width:70,align:'right',
						formatter:function(value,row,index){
							if (row.editing){
									var s = '<a href="#" onclick="saverow(this)">保存</a> ';
									var c = '<a href="#" onclick="cancelrow(this)">取消</a>';
									return s+c;
								} else {
									var e = '<a href="#" onclick="editrow(this)">修改</a> ';
									var d = '<a href="#" onclick="deleterow(this)">删除</a>';
									return e+d;
								}
						}
			},
			
			{ field:'resumeName',title:'姓名',width:100,editor:'text'},
			{ field:'resumeMobile',title:'电话',width:100,editor:'text'},
			{ field:'recommendTime',title:'推荐时间',width:100,editor:'datebox'},
			{ field:'position',title:'职位',width:100,editor:'text'},
			{ field:'name',title:'客户',width:100},
			{ field:'usernameHr',title:'推荐人',width:100},
			{ field:'salary',title:'薪水',width:100,editor:'text'},
			{ field:'workTime',title:'到岗时间',width:100,editor:'text'},
			{ field:'interviewTime',title:'面试时间',width:150, editor : {  
                    type:'datetimebox',//Extension of time for selecting type  
                    options:{  
                        required: true,  
                    }  
                }
            },
			{ field:'present',title:'是否到场',width:100,editor:'text'},
			{ field:'result',title:'推荐结果',width:100,editor:'text'},
			{ field:'remark',title:'备注',width:100,editor:'text'}
			]],
				onBeforeEdit:function(index,row){
					row.editing = true;
					updateActions(index);
				},
				onAfterEdit:function(index,row){
					row.editing = false;
					updateActions(index);
					 $.ajax({
	                    type: "POST",
	                    url: "update",
	                    data: {
	                    	"id":row.id,
	                        "recommendTime":row.recommendTime,
	                        "position":row.position,
	                        "salary":row.salary,
	                        "workTime":row.workTime,
	                        "interviewTime":row.interviewTime,
	                        "present":row.present,
	                        "result":row.result,
	                        "remark":row.remark
	                    },
	                    success: function(msg){
	                      alert("数据修改成功...");
	                      $('#datagrid').datagrid('load');//重新加载datagrid，刷新功能
	                    }
	                 });
	        	 },
				onCancelEdit:function(index,row){
					row.editing = false;
					updateActions(index);
				},
				onDblClickRow: function (rowIndex, rowData) {
					openDialogViewDetail("viewDialog",rowData.id);
				}//双击事件
		});
	});

	function add(){
		$('#form_add').form('submit', {
			url:'add',
			 onSubmit: function(){  
				$("#saveBT").hide();
				return true;
				},
			success:function(data){
				if(data){
					$.messager.alert('信息提示','提交成功！','info');
					$('#datagrid').datagrid('load');//重新加载datagrid，刷新功能
					$('#dialog_add').dialog('close');
				}
				else
				{
					$.messager.alert('信息提示','提交失败！','info');
				}
			}
		});
	}
	/**
	* Name 修改记录
	*/
	function edit(){
		$('#form_edit').form('submit', {
			url:'',
			success:function(data){
				if(data){
					$.messager.alert('信息提示','提交成功！','info');
					$('#dialog_edit').dialog('close');
				}
				else
				{
					$.messager.alert('信息提示','提交失败！','info');
				}
			}
		});
	}
	
	/**
	* Name 删除记录
	*/
	function remove(){
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				var items = $('#datagrid').datagrid('getSelections');
				var ids = [];
				$(items).each(function(){
					ids.push(this.productid);	
				});
				//alert(ids);return;
				$.ajax({
					url:'',
					data:'',
					success:function(data){
						if(data){
							$.messager.alert('信息提示','删除成功！','info');		
						}
						else
						{
							$.messager.alert('信息提示','删除失败！','info');		
						}
					}	
				});
			}	
		});
	}
	
	/**
	* Name 打开添加窗口
	*/
	function openAdd(){
		$('#form_add').form('clear');
		$('#dialog_add').dialog({
			closed: false,
			modal:true,
            title: "上传简历",
            buttons: [{
            	id:"saveBT",
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#dialog_add').dialog('close');                    
                }
            }]
        });
	}
	
	/**
	* Name 打开修改窗口
	*/
	function openDownload(){
		var valArr = new Array; 
		var c=0;
		$('input[name="id"]:checked').each(function(i) {
			valArr[i] = $(this).val();
			c++;
		});
		if (c == 0) {
			alert("请选择需要处理的文件！");
			return;
		} else {
			var vals = valArr.join(','); //转换为逗号隔开的字符串 
			window.location.href = "download/?ids="+vals;
		}
	}	
	function openDelete(){
		var valArr = new Array; 
		var c=0;
		$('input[name="id"]:checked').each(function(i) {
			valArr[i] = $(this).val();
			c++;
		});
		if (c == 0) {
			alert("请选择删除下载的文件！");
			return;
		} else {
			$.messager.confirm('Confirm','确认删除?',function(r){
				if (r){
					var vals = valArr.join(','); //转换为逗号隔开的字符串 
					 $.ajax({
                    type: "POST",
                    url: "delete",
                    data: {
                    	"ids":vals,
                    },
                    success: function(msg){
                      $('#datagrid').datagrid('load');//重新加载datagrid，刷新功能
                      alert("删除成功...");
                    }
                 });
				}
			});
		}
	}	
	
	
	
	function updateActions(index){
		$('#datagrid').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function getRowIndex(target){
		var tr = $(target).closest('tr.datagrid-row');
		return parseInt(tr.attr('datagrid-row-index'));
	}
	function editrow(target){
		$('#datagrid').datagrid('beginEdit', getRowIndex(target));
	}
	function deleterow(target){
	 	var selectedRow = $('#datagrid').datagrid('getSelected'); //获取选中行
	 	if (null == selectedRow) {
			alert("请选择需要删除的记录！");
	 	}
	 	if (null == selectedRow.id) {
			alert("请选择需要删除的记录！");
	 	}
		if (0 == selectedRow.id) {
			alert("请选择需要删除的记录！");
	 	}
		$.messager.confirm('Confirm','确认删除?',function(r){
			if (r){
				$('#datagrid').datagrid('deleteRow', getRowIndex(target));
					$.ajax({
                    type: "POST",
                    url: "delete/"+selectedRow.id,
                    success: function(msg){
                      alert("数据删除成功...");
                      $('#datagrid').datagrid('load');//重新加载datagrid，刷新功能
                    }
                 });
			}
		});
	}
	function saverow(target){
		$('#datagrid').datagrid('endEdit', getRowIndex(target));
	}
	function cancelrow(target){
		$('#datagrid').datagrid('cancelEdit', getRowIndex(target));
	}
	function openDirectionDialog(index,field){
		$('#datagrid').datagrid("selectRow", index);
		var row = $("#datagrid").datagrid("getSelected");
		if(field == 'self'){
			$("#direction").html(row.self);
		}
		if(field == 'work'){
			$("#direction").html(row.work);
		}
		if(field == 'project'){
			$("#direction").html(row.project);
		}
		if(field == 'train'){
			$("#direction").html(row.train);
		}
		$('#directionDialog').dialog({ top : 100, buttons : [{
			text : '关闭', iconCls : 'icon-cancel', handler : function() {
				$('#directionDialog').dialog('close');
			}}]
	    });
		$('#directionDialog').dialog("open");
	}