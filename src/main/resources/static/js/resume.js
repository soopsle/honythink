$(document).ready(function() {
	init()
	$('#recommendTime').datebox('setValue', myformatter(new Date()));
});
	function init(){
		$('#datagrid').datagrid({
		rownumbers:true,
		singleSelect:false,
		pageSize:20,           
		pagination:true,
		multiSort:true,
		//autoRowHeight:false,
		editorHeight:24,
		//fitColumns:true,
		fit:true,
		url:'list',
		columns:[[
		{ field:'resumeName',title:'简历名称',width:100,editor:'text'},
		{ field:'gender',title:'性别',width:60,editor:{
						type:'combobox',
						options:{
							valueField:'value',
							textField:'key',
							data:genders,
							required:true
						}
					}},
		{ field:'birthday',title:'出生日期',width:100,editor:'text'},
		{ field:'age',title:'年龄',width:35,editor:'text'},
		{ field:'seniority',title:'工作年限',width:100,editor:'text'},
		{ field:'card',title:'身份证',width:100,editor:'text'},
		{ field:'mobile',title:'手机',width:100,editor:'text'},
		{ field:'marriage',title:'婚姻状况',width:60,editor:{
						type:'combobox',
						options:{
							valueField:'value',
							textField:'key',
							data:marriages,
							required:true
						}
					}},
         { field:'city',title:'居住地',width:100,editor:'text'},
         { field:'residence',title:'户口',width:100,editor:'text'},
         { field:'school',title:'学校',width:100,editor:'text'},
         { field:'major',title:'专业',width:100,editor:'text'},
         { field:'education',title:'最高学历',width:100,editor:'text'},
         { field:'educationtime',title:'在校时间',width:100,editor:'text'},
         { field:'self',title:'自我评价',width:100,
				formatter:function(value,rowData,rowIndex){
								if(value != null){
									return '<a href="#"  onclick="openDirectionDialog('+rowIndex+', \'self\')">'+value.replace(/<[^>]*?>/g,'')+'</a> ';
								}else{
									return ;
								}
							}	
		 }, 
         { field:'work',title:'工作经历',width:100,
				formatter:function(value,rowData,rowIndex){
								if(value != null){
									return '<a href="#"  onclick="openDirectionDialog('+rowIndex+', \'work\')">'+value.replace(/<[^>]*?>/g,'')+'</a> ';
								}else{
									return ;
								}
							}	
		 }, 
         { field:'project',title:'项目经历',width:100,
				formatter:function(value,rowData,rowIndex){
								if(value != null){
									return '<a href="#"  onclick="openDirectionDialog('+rowIndex+', \'project\')">'+value.replace(/<[^>]*?>/g,'')+'</a> ';
								}else{
									return ;
								}
							}	
		 }, 
         { field:'train',title:'培训经历',width:100,
				formatter:function(value,rowData,rowIndex){
								if(value != null){
									return '<a href="#"  onclick="openDirectionDialog('+rowIndex+', \'train\')">'+value.replace(/<[^>]*?>/g,'')+'</a> ';
								}else{
									return ;
								}
							}	
		 }, 
         { field:'time',title:'录入时间',width:130},
         { field:'username',title:'录入人员',width:130}
		]],
		frozenColumns:[[ 
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
			{ field:'name',title:'姓名',width:100,sortable:true,editor:'text'}
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
                        "name":row.name,
                        "resume_name":row.resume_name,
                        "gender":row.gender,
                        "birthday":row.birthday,
                        "age":row.age,
                        "seniority":row.seniority,
                        "card":row.card,
                        "mobile":row.mobile,
                        "marriage":row.marriage,
                        "city":row.city,
                        "residence":row.residence,
                        "school":row.school,
                        "major":row.major,
                        "education":row.education,
                        "educationtime":row.educationtime
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
	}
	
    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }

	function add(){
		$('#form_add').form('submit', {
			url:'',
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
	function openPush(){
		$('#dialog_down').dialog({
			closed: false,
			modal:true,
            title: "推荐",
            buttons: [
//                      {
//            	id:"saveDownBT",
//                text: '生成下载',
//                iconCls: 'icon-save',
//                handler: down
//            },
            {
            	id:"saveDownBT",
                text: '推送',
                iconCls: 'icon-ok',
                handler: push
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#dialog_down').dialog('close');                    
                }
            }]
        });
	}
	function down(){
		var valArr = new Array; 
		var c=0;
		$('input[name="id"]:checked').each(function(i) {
			valArr[i] = $(this).val();
			c++;
		});
		if (c == 0) {
			alert("请选择需要下载的文件！");
			return;
		} else {
			var vals = valArr.join(','); //转换为逗号隔开的字符串 
			window.location.href = "download/?ids="+vals;
		}
	}
	function push(){
		var valArr = new Array; 
		var c=0;
		$('input[name="id"]:checked').each(function(i) {
			valArr[i] = $(this).val();
			c++;
		});
		var vals = valArr.join(','); //转换为逗号隔开的字符串 
		$('#form_down').form('submit', {
			 url:'../interview/add?ids='+vals,
			 onSubmit: function(){  
				$("#saveDownBT").hide();
				return true;
				},
			success:function(data){
				if(data){
					$.messager.alert('信息提示','提交成功！','info');
					$('#datagrid').datagrid('load');//重新加载datagrid，刷新功能
					$('#dialog_down').dialog('close');
				}
				else
				{
					$.messager.alert('信息提示','提交失败！','info');
				}
			}
		});
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
	var genders = [
		    {key:'男',value:'男'},
		    {key:'女',value:'女'}
		];
    var marriages = [
		    {key:'已婚',value:'已婚'},
		    {key:'未婚',value:'未婚'},
		    {key:'离异',value:'离异'}
		];

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
    function ww3(date){  
        var y = date.getFullYear();  
        var m = date.getMonth()+1;  
        var d = date.getDate();  
        var h = date.getHours();  
        var min = date.getMinutes();  
        var sec = date.getSeconds();  
        var str = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);  
        return str;  
    }  
    function w3(s){  
        if (!s) return new Date();  
        var y = s.substring(0,4);  
        var m =s.substring(5,7);  
        var d = s.substring(8,10);  
        var h = s.substring(11,14);  
        var min = s.substring(15,17);  
        var sec = s.substring(18,20);  
        if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(sec)){  
            return new Date(y,m-1,d,h,min,sec);  
        } else {  
            return new Date();  
        }  
    }  