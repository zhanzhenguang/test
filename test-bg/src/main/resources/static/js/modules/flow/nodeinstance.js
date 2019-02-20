$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'flow/nodeinstance/list',
        datatype: "json",
        colModel: [			
			{ label: 'nodeCode', name: 'nodeCode', index: 'node_code', width: 50, key: true },
			{ label: '节点名称', name: 'nodeName', index: 'node_name', width: 80 }, 			
			{ label: '流程编码', name: 'flowCode', index: 'flow_code', width: 80 }, 			
			{ label: '节点x', name: 'posX', index: 'pos_x', width: 80 }, 			
			{ label: '节点y', name: 'posY', index: 'pos_y', width: 80 }, 			
			{ label: '节点z', name: 'posZ', index: 'pos_z', width: 80 }, 			
			{ label: '节点执行角色', name: 'contrRole', index: 'contr_role', width: 80 }, 			
			{ label: '节点状态  1-已经处理   2-未处理', name: 'nodeStatus', index: 'node_status', width: 80 }, 			
			{ label: '状态  ', name: 'status', index: 'status', width: 80 }, 			
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
		nodeInstance: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.nodeInstance = {};
		},
		update: function (event) {
			var nodeCode = getSelectedRow();
			if(nodeCode == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(nodeCode)
		},
		saveOrUpdate: function (event) {
			var url = vm.nodeInstance.nodeCode == null ? "flow/nodeinstance/save" : "flow/nodeinstance/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.nodeInstance),
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
				    url: baseURL + "flow/nodeinstance/delete",
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
		getInfo: function(nodeCode){
			$.get(baseURL + "flow/nodeinstance/info/"+nodeCode, function(r){
                vm.nodeInstance = r.nodeInstance;
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