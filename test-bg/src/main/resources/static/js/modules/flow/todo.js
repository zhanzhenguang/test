$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'flow/nodeinstance/list/todo',
        datatype: "json",
        colModel: [			
			{ label: '流程编码', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			{ label: '业务编码', name: 'formId', index: 'form_Id', width: 80 },
			{ label: '合同编号', name: 'contCode', index: 'cont_code', width: 80 },
			{ label: '租户名称', name: 'tenantName', index: 'tenant_name', width: 100 },
			{ label: '节点名称', name: 'nodeName', index: 'node_name', width: 80 }, 			
			{ label: '流程名称', name: 'flowName', index: 'flow_code', width: 80 }, 			
			{ label: '节点执行角色名称', name: 'executeRoleName', index: 'contr_role', width: 80 }, 			
			{ label: '节点状态 ', name: 'nodeStatus', index: 'node_status', width: 80 
				, formatter:function(cellvalue, options,rowObject){
					return dataItem(constans.nodeStatus,cellvalue);
				} 
			}, 			
			{ label: '状态  ', name: 'status', index: 'status', width: 80 
				, formatter:function(cellvalue, options,rowObject){
					return dataItem(constans.status1,cellvalue);
				} 
			}, 			
			{ label: '创建者ID', name: 'createUserId', index: 'create_user_id', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: 'url', name: 'detailUrl', index: 'detail_url', width: 50, hidden:true }
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
            order: "order",
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
		nodeInstance: {},
		detailUrl:'',
		typ: '',
		flowRecordList: [],
		nextApprovalPeople: [],
		worFlowDto: {},
		q:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		submit: function(){
			var grid = $("#jqGrid");
			var rowKey = grid.getGridParam("selrow");
			if (!rowKey) {
				alert("请选择一条记录");
				return;
			}else{
				var rowData = grid.jqGrid('getRowData',rowKey);
				window.frameElement.src=rowData.detailUrl + "?instanceId=" + rowKey + "&formId=" + rowData.formId +  "&pageType=todo";
				
			}
			
		},
		getRecord: function(formId){
			$.get(baseURL + "flow/executehis/select/"+formId, function(r){
                vm.flowRecordList = r.list;
            });
		},
		getNextPeople: function(code){
		},
		getInfo: function(id){
			$.get(baseURL + "flow/nodeinstance/info/"+id+"?pageType=todo", function(r){
                vm.nodeInstance = r.nodeInstance;
                if(vm.nodeInstance!=null){
                	if(r.nodeInstance.detailUrl!=undefined){
                    	vm.detailUrl = baseURL + r.nodeInstance.detailUrl+"?formId="+vm.nodeInstance.formId+"&node="+vm.nodeInstance.nodeCode;
                    }else{
                    	vm.detailUrl = baseURL;
                    }
                }
            });
		},
		record: function(){
			vm.getRecord(vm.nodeInstance.formId);
			layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "流程审批记录",
                area: ['800px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#flowRecordListLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.close(index);
                }
            });
		},
		nextPeople: function(){
			console.log(vm.nodeInstance);
            vm.getNextPeople(vm.nodeInstance.formId);
			layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "流程下一节点可以审批人名单",
                area: ['300px', '250px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#nextApprovalPeopleLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.close(index);
                }
            });
		},
		start: function(){
			vm.worFlowDto.formId = "11111111222";
			vm.worFlowDto.flowCode = "xiajibagao"; 
			$.ajax({
				type: "POST",
			    url: baseURL + "flow/nodeinstance/start",
                contentType: "application/json",
			    data: JSON.stringify(vm.worFlowDto),
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
		go: function (nodeSeq) {
			vm.worFlowDto.formId = vm.nodeInstance.formId;
			vm.worFlowDto.comment =  vm.nodeInstance.comment;
			vm.worFlowDto.nodeSeq = nodeSeq;
			$.ajax({
				type: "POST",
			    url: baseURL + "flow/nodeinstance/go",
                contentType: "application/json",
			    data: JSON.stringify(vm.worFlowDto),
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
		
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			if (event=='undefined' || event==null){
				page = 1;
			}
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{'pageType':'todo',"contCode":vm.q.contCode,"tenantName":vm.q.tenantName,"flowName":vm.q.flowName,"nodeName":vm.q.nodeName}
            }).trigger("reloadGrid");
		}
	}
});