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
		{ field:'name',title:'公司名称',width:100,editor:'text'},
		{ field:'shortname',title:'Excel模板',width:100,editor:'text'},
		{ field:'template',title:'简历模板',width:100,editor:'text'},
		{ field:'leader',title:'负责人',width:100,editor:'text'},
		{ field:'mobile',title:'手机',width:35,editor:'text'},
		{ field:'phone',title:'座机',width:100,editor:'text'},
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
	                	"name":row.name,
	                	"shortname":row.shortname,
	                    "template":row.template,
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
        title: "添加",
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
		alert("请选择需要下载的文件！");
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

/**
* Name 分页过滤器
*/
function pagerFilter(data){            
	if (typeof data.length == 'number'){// is array                
		data = {                   
			total: data.length,                   
			rows: data               
		}            
	}        
	var dg = $(this);         
	var opts = dg.datagrid('options');          
	var pager = dg.datagrid('getPager');          
	pager.pagination({                
		onSelectPage:function(pageNum, pageSize){                 
			opts.pageNumber = pageNum;                   
			opts.pageSize = pageSize;                
			pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});                  
			dg.datagrid('loadData',data);                
		}          
	});           
	if (!data.originalRows){               
		data.originalRows = (data.rows);       
	}         
	var start = (opts.pageNumber-1)*parseInt(opts.pageSize);          
	var end = start + parseInt(opts.pageSize);        
	data.rows = (data.originalRows.slice(start, end));         
	return data;       
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
	$('#datagrid').datagrid("selectRow", getRowIndex(target));
	$('#datagrid').datagrid('beginEdit', getRowIndex(target));
}
function deleterow(target){
 	$('#datagrid').datagrid("selectRow", getRowIndex(target));
 	var selectedRow = $('#datagrid').datagrid('getSelected'); //获取选中行
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
