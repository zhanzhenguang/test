$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'flow/executeroleuser/list',
        datatype: "json",
        colModel: [			
			{ label: 'executeRole', name: 'executeRole', index: 'execute_role', width: 50, key: true },
			{ label: '用户id', name: 'userId', index: 'user_id', width: 80 }			
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
		executeRoleUser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.executeRoleUser = {};
		},
		update: function (event) {
			var executeRole = getSelectedRow();
			if(executeRole == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(executeRole)
		},
		saveOrUpdate: function (event) {
			var url = vm.executeRoleUser.executeRole == null ? "flow/executeroleuser/save" : "flow/executeroleuser/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.executeRoleUser),
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
				    url: baseURL + "flow/executeroleuser/delete",
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
			$.get(baseURL + "flow/executeroleuser/info/"+executeRole, function(r){
                vm.executeRoleUser = r.executeRoleUser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});