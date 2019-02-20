$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'flow/executehis/list',
        datatype: "json",
        colModel: [			
			{ label: 'node', name: 'node', index: 'node', width: 50, key: true },
			{ label: '流程编码', name: 'flowCode', index: 'flow_code', width: 80 }, 			
			{ label: '备注', name: 'comment', index: 'comment', width: 80 }, 			
			{ label: '状态  0：禁用   1：正常', name: 'status', index: 'status', width: 80 }, 			
			{ label: '创建者ID', name: 'createUserId', index: 'create_user_id', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }			
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
		executeHis: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.executeHis = {};
		},
		update: function (event) {
			var node = getSelectedRow();
			if(node == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(node)
		},
		saveOrUpdate: function (event) {
			var url = vm.executeHis.node == null ? "flow/executehis/save" : "flow/executehis/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.executeHis),
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
			var nodes = getSelectedRows();
			if(nodes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "flow/executehis/delete",
                    contentType: "application/json",
				    data: JSON.stringify(nodes),
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
		getInfo: function(node){
			$.get(baseURL + "flow/executehis/info/"+node, function(r){
                vm.executeHis = r.executeHis;
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