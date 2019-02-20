function contact(cell,name){
	if(!isBlank(cell)&&!isBlank(name)){
		return cell+"-"+name;
	}else if(!isBlank(cell)){
		return cell;
	}else{
		return "";
	}
}
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'flow/node/list',
        datatype:"local",
        colModel: [			
			{ label: '节点编码', name: 'nodeCode', index: 'node_code', width: 50, key: true },
			{ label: '节点名称', name: 'nodeName', index: 'node_name', width: 80 }, 			
			{ label: '流程编码', name: 'flowCode', index: 'flow_code', width: 80 }, 			
			{ label: '节点类型', name: 'typ', index: 'typ', width: 100
				, formatter:function(cellvalue, options,rowObject){
					return dataItem(constans.nodeTyp,cellvalue);
				} 
			},
			{ label: '下一节点1', name: 'posX', index: 'pos_x', width: 80
				, formatter:function(cellvalue, options,rowObject){
					return contact(cellvalue,rowObject.posXName);
				}	
			}, 			
			{ label: '上一节点', name: 'posY', index: 'pos_y', width: 80 
				, formatter:function(cellvalue, options,rowObject){
					return contact(cellvalue,rowObject.posYName);
				}	
			}, 			
			{ label: '下一节点2', name: 'posZ', index: 'pos_z', width: 80 
				, formatter:function(cellvalue, options,rowObject){
					return contact(cellvalue,rowObject.posZName);
				}
			}, 			
			{ label: '下一节点3', name: 'posI', index: 'pos_i', width: 80 
				, formatter:function(cellvalue, options,rowObject){
					return contact(cellvalue,rowObject.posIName);
				}	
			},
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
    $("#jqGrid1").jqGrid({
        url: baseURL + 'flow/list/list',
        datatype: "json",
        colModel: [			
			{ label: '流程编码', name: 'flowCode', index: 'flow_code', width: 50, key: true },
			{ label: '流程名称', name: 'flowName', index: 'flow_name', width: 80 }, 
        ],
		viewrecords: true,
        height: 385,
        width: 130,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        multiboxonly: true,
        beforeSelectRow: function(){
        	$("#jqGrid1").jqGrid('resetSelection');
    	    return true;
        },//js方法
        pager: "#jqGridPager1",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid1").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
    $("#jqGrid2").jqGrid({
        url: baseURL + 'flow/executerole/list',
        datatype: "local",
        colModel: [			
			{ label: '流程角色编码', name: 'executeRole', index: 'execute_role', width: 100, key: true },
			{ label: '流程角色名称', name: 'executeRoleName', index: 'execute_role_name', width: 100},
			{ label: '节点编码', name: 'node', index: 'node', width: 80 }, 			
			{ label: '流程编码', name: 'flow', index: 'flow', width: 80 }, 			
			{ label: '状态 ', name: 'status', index: 'status', width: 80
				, formatter:function(cellvalue, options,rowObject){
					return dataItem(constans.status,cellvalue);
				} 
			}, 			
			{ label: '创建者ID', name: 'createUserId', index: 'create_user_id', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '更新人id', name: 'updateUserId', index: 'update_user_id', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }			
        ],
		viewrecords: true,
        height: 200,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager2",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid2").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		node: {},
		nodeTyp: [],
		typ: '',
		flowCode:'',
		flowName:'',
		selectRole:'',
		roleList:[],
		q:{}
	},
	mounted: function () {
        this.nodeTyp=constansItem.nodeTyp;
    },
	methods: {
		query: function () {
			vm.reload();
		},
		selectRole: function(){},
		add: function(){
			var grid = $("#jqGrid1");
			var rowKey = grid.getGridParam("selrow");
			if (!rowKey) {
				alert("请选择一条流程记录");
				return;
			}
			vm.flowCode = rowKey;
			var rowData = grid.getRowData(rowKey);
			vm.flowName = rowData.flowName;
			vm.flowCode = rowData.flowCode;
			vm1.$data.showList1 = false;
			vm.showList = false;
			vm.title = "新增";
			vm.typ="add";
			vm.node = {};
		},
		update: function (event) {
			var grid = $("#jqGrid1");
			var rowKey = grid.getGridParam("selrow");
			if (!rowKey) {
				alert("请选择一条流程记录");
				return;
			}
			vm.flowCode = rowKey;
			var rowData = grid.getRowData(rowKey);
			vm.flowName = rowData.flowName;
			vm.flowCode = rowData.flowCode;
			
			var nodeCode = getSelectedRow();
			if(nodeCode == null){
				return ;
			}
			vm1.$data.showList1 = false;
			vm.showList = false;
            vm.title = "修改";
            vm.typ="update";
            
            vm.getInfo(vm.flowCode,nodeCode);
            $("#saveBtn").show();
		},
		check: function (event) {
			var grid = $("#jqGrid1");
			var rowKey = grid.getGridParam("selrow");
			if (!rowKey) {
				alert("请选择一条流程记录");
				return;
			}
			vm.flowCode = rowKey;
			var rowData = grid.getRowData(rowKey);
			vm.flowName = rowData.flowName;
			vm.flowCode = rowData.flowCode;
			
			var nodeCode = getSelectedRow();
			if(nodeCode == null){
				return ;
			}
			vm1.$data.showList1 = false;
			vm.showList = false;
			vm.title = "查看";
			vm.typ="update";
			 //文本框禁用
			$("input[type=text]").each(function(){
          	　　	$("#" + this.id).attr("disabled", true);
            });
			$("#typ").prop('disabled', true);
			vm.getInfo(vm.flowCode,nodeCode);
			$("#saveBtn").hide();
			
		},
		saveOrUpdate: function (event) {
			
			if(isBlank(vm.node.nodeCode)){
				alert("节点编码不能为空");
			}
			if(isBlank(vm.flowCode)){
				alert("流程编码不能为空");
			}
			
			var url =  "" ;
			if(vm.typ=="add"){
				url = "flow/node/save" ;
			}else{
				url = "flow/node/update";
			}
			
			vm.node.flowCode=vm.flowCode;
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.node),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var nodeCodes = getSelectedRows();
			if(nodeCodes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "flow/node/delete",
                    contentType: "application/json",
				    data: JSON.stringify(nodeCodes),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(flowCode,nodeCode){
			$.get(baseURL + "flow/node/info/"+flowCode+"/"+nodeCode, function(r){
                vm.node = r.node;
            });
		},
		reload: function (event) {
			var grid = $("#jqGrid1");
			var rowKey = grid.getGridParam("selrow");
			if (!rowKey) {
				alert("请选择一条流程记录");
				return;
			}
			//文本框启用
			$("input[type='text']").each(function () {
			　　$("#" + this.id).removeAttr("disabled");
			});
			$("#typ").prop('disabled', false);
			var rowData = grid.getRowData(rowKey);
			vm.$data.showList = true;
			vm1.$data.showList1 = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                datatype: "json",
                postData:{'flowCode': rowData.flowCode},
            }).trigger("reloadGrid");
		}
	}
});

var vm1 = new Vue({
	el:'#rrapp1',
	data:{
		showList: true,
		showList1: true,
		title: null,
		executeRole: {},
		typ:''
	},
	methods: {
		query: function () {
			vm1.reload();
		},
		add: function(){
			vm1.showList = false;
			vm1.title = "新增";
			vm1.typ = "add";
			vm1.executeRole = {};
			
			var grid2 = $("#jqGrid");
			var rowKey2 = grid2.getGridParam("selrow");
			if (!rowKey2) {
				alert("请选择一个节点");
				return;
			}else{
				vm1.title = "新增";
				vm1.typ = "add";
				vm1.executeRole = {};
				var rowData2 = grid2.getRowData(rowKey2);
				vm1.executeRole.node=rowData2.nodeCode;
				vm1.executeRole.flow=rowData2.flowCode;
				vm1.showList = false;
			}
			
			
		},
		update: function (event) {
			var executeRole = getSelectedRow("#jqGrid2");
			if(executeRole == null){
				return ;
			}
			vm1.showList = false;
            vm1.title = "修改";
            vm1.typ = "update";
            vm1.getInfo(executeRole);
		},
		saveOrUpdate: function (event) {
			console.log(vm1.executeRole.executeRole);
			if(isBlank(vm1.executeRole.executeRole)){
				alert("角色编码不能为空");
			}
			if(isBlank(vm1.executeRole.flow)){
				alert("流程编码不能为空");
			}
			if(isBlank(vm1.executeRole.node)){
				alert("节点编码不能为空");
			}
			var url =  "" ;
			if(vm1.typ=="add"){
				url = "flow/executerole/save" ;
			}else{
				url = "flow/executerole/update";
			}
			
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm1.executeRole),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm1.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var executeRoles = getSelectedRows();
			if(executeRoles == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "flow/executerole/delete",
                    contentType: "application/json",
				    data: JSON.stringify(executeRoles),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(executeRole){
			$.get(baseURL + "flow/executerole/info/"+executeRole, function(r){
                vm1.executeRole = r.executeRole;
            });
		},
		reload: function (event) {
			vm1.showList = true;
			var grid1 = $("#jqGrid1");
			var rowKey1 = grid1.getGridParam("selrow");
			if (!rowKey1) {
				alert("请选择一条流程");
				return;
			}
			var rowData1 = grid1.getRowData(rowKey1);
			
			var grid2 = $("#jqGrid");
			var rowKey2 = grid2.getGridParam("selrow");
			if (!rowKey2) {
				alert("请选择一个节点");
				return;
			}
			var rowData2 = grid2.getRowData(rowKey2);
			
			var page = $("#jqGrid2").jqGrid('getGridParam','page');
			$("#jqGrid2").jqGrid('setGridParam',{ 
                page:page,
                datatype: "json",
                postData:{'flowCode':rowData1.flowCode,'nodeCode':rowData2.nodeCode},
            }).trigger("reloadGrid");
		}
	}
});