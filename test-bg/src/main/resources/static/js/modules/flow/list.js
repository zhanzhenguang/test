$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'flow/list/list',
        datatype: "json",
        colModel: [			
			{ label: '流程编码', name: 'flowCode', index: 'flow_code', width: 50, key: true },
			{ label: '流程名称', name: 'flowName', index: 'flow_name', width: 80 }, 
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
		list: {},
		typ:'',
		selectRole:''
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.typ = "add";
			vm.list = {};
			
		},
		selectRole:function(){},
		update: function (event) {
			var flowCode = getSelectedRow();
			if(flowCode == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.typ = "update";
            
            vm.getInfo(flowCode);
            $("#saveOrUpdate").show();
		},
		check: function (event) {
			var flowCode = getSelectedRow();
			if(flowCode == null){
				return ;
			}
			vm.showList = false;
            vm.title = "查看";
            vm.typ = "update";
            //文本框禁用
            $("input[type=text]").each(function(){
          	　　$("#" + this.id).attr("disabled", true);
            });
            vm.getInfo(flowCode);
            $("#saveOrUpdate").hide();
		},
		saveOrUpdate: function (event) {
			if(isBlank(vm.list.flowCode)){
				alert("节点编码不能为空");
			}
			var url =  "" ;
			if(vm.typ=="add"){
				url = "flow/list/save" ;
			}else{
				url = "flow/list/update";
			}
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.list),
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
			var flowCodes = getSelectedRows();
			if(flowCodes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "flow/list/delete",
                    contentType: "application/json",
				    data: JSON.stringify(flowCodes),
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
		getInfo: function(flowCode){
			$.get(baseURL + "flow/list/info/"+flowCode, function(r){
                vm.list = r.list;
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