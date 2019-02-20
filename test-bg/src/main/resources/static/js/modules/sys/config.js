$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/config/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', width: 30, key: true },
			{ label: '参数名', name: 'key', width: 60 },
			{ label: '参数值', name: 'value', width: 100 },
			{label: '状态',name:'status', width:30,formatter:function(cellvalue, options, rowObject){
				if(cellvalue=='0')
					return "隐藏";
				else
					return "显示";
				return }},
			{ label: '备注', name: 'remark', width: 80 }
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
		q:{
			key: null
		},
		showList: true,
		title: null,
		config: {},
		category:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.config = {status:1};
			vm.category=[
                {name:'显示',id:1},
                {name:'隐藏',id:0}
            ];
		},
		update: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			$.get(baseURL + "sys/config/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.config = r.config;
                vm.category=[
                    {name:'显示',id:1},
                    {name:'隐藏',id:0}
                ];
            });
		},
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/config/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		updateFeeCache: function(){
				confirm('确定要刷新费用缓存？', function(){
					$.get(baseURL + "sys/config/refreshCache/fee", function(r){
						console.log(r);
						if(r.code == 0){
							alert('操作成功');
						}else{
							alert(r.msg);
						}
		            });
				});
		},
		updateAllCache: function(){
			confirm('确定要刷新全部缓存？', function(){
				$.get(baseURL + "sys/config/refreshCache/all", function(r){
					console.log(r);
					if(r.code == 0){
						alert('操作成功');
					}else{
						alert(r.msg);
					}
	            });
			});
		},
		saveOrUpdate: function () {
            if(vm.validator()){
				return ;
			}

			var url = vm.config.id == null ? "sys/config/save" : "sys/config/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.config),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		},
		validator: function () {
			if(isBlank(vm.config.key)){
				alert("参数名不能为空");
				return true;
			}

            if(isBlank(vm.config.value)){
                alert("参数值不能为空");
                return true;
            }

        }
	}
});