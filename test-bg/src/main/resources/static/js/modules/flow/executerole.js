$(function () {
	
    $("#jqGrid").jqGrid({
        url: baseURL + 'flow/executerole/list',
        datatype: "json",
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		executeRole: {},
		typ:''
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.typ = "add";
			vm.executeRole = {};
			$("#showOrHideCreateAndUpdate").hide();
		},
		update: function (event) {
			var executeRole = getSelectedRow();
			if(executeRole == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.typ = "update";
            vm.getInfo(executeRole);
            $("#showOrHideCreateAndUpdate").show();
            $('#saveBtn').show();
		},
		check: function (event) {
			var executeRole = getSelectedRow();
			if(executeRole == null){
				return ;
			}
			vm.showList = false;
            vm.title = "查看";
            vm.typ = "update";
            vm.getInfo(executeRole);
            $("#showOrHideCreateAndUpdate").show();
            $('#saveBtn').hide();
            $("input[type=text]").each(function(){
            	　　$("#" + this.id).attr("disabled", true);
            });
		},
		saveOrUpdate: function (event) {
			console.log(vm.executeRole.executeRole);
			if(isBlank(vm.executeRole.executeRole)){
				alert("角色编码不能为空");
			}
			if(isBlank(vm.executeRole.flow)){
				alert("流程编码不能为空");
			}
			if(isBlank(vm.executeRole.node)){
				alert("节点编码不能为空");
			}
			var url =  "" ;
			if(vm.typ=="add"){
				url = "flow/executerole/save" ;
			}else{
				url = "flow/executerole/update";
			}
			
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.executeRole),
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
                vm.executeRole = r.executeRole;
            });
		},
		reload: function (event) {
			//文本框启用
			$("input[type='text']").each(function () {
			　　$("#" + this.id).removeAttr("disabled");
			});
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});