$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/app/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: 'app名称', name: 'appName', index: 'app_name', width: 200 }, 			
			{ label: 'app版本', name: 'appV', index: 'app_v', width: 200 }, 			
			{ label: '小程序签名', name: 'signature', index: 'signature', width: 200 }, 			
			{ label: '小程序appId', name: 'appid', index: 'appid', width: 200 }, 			
			{ label: '小程序秘钥', name: 'secret', index: 'secret', width: 200 }, 			
			{ label: '微信商户支付账户', name: 'payAcount', index: 'pay_acount', width: 200 }, 			
			{ label: '微信支付的商户密钥', name: 'paySecret', index: 'pay_secret', width: 200 }, 			
			{ label: '微信支付的商户id', name: 'paySign', index: 'pay_sign', width: 200 }, 			
			{ label: '微信支付的商户密码', name: 'payPwd', index: 'pay_pwd', width: 200 }, 			
			{ label: '支付回调URl', name: 'notifyUrl', index: 'notify_url', width: 200 }, 			
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
		app: {},
		q:{
			appName: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.app = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.app.id == null ? "sys/app/save" : "sys/app/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.app),
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/app/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
			$.get(baseURL + "sys/app/info/"+id, function(r){
                vm.app = r.app;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{'appName': vm.q.appName},
            }).trigger("reloadGrid");
		}
	}
});