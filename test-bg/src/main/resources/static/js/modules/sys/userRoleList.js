var roleId = '';

$(function () {
	defFunction.initGrid1();
});

var defFunction = {
	initGrid1 : function() {
		$("#jqGrid1").jqGrid({
	        url: baseURL + 'sys/user/userRoleList',
	        datatype: "json",
	        colModel: [			
				{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
				{ label: '用户名', name: 'username', width: 75 },
				{ label: '邮箱', name: 'email', width: 90 },
				{ label: '手机号', name: 'mobile', width: 100 },
				{ label: '操作', name: 'cancelUser', formatter:defFunction.formatUserLink, width: 80}
	        ],
			viewrecords: true,
	        height: 385,
	        rowNum: 10,
			rowList : [10,30,50],
	        rownumbers: true, 
	        rownumWidth: 25, 
	        autowidth:true,
	        multiselect: true,
	        pager: "#jqGridPager1",
	        postData: {roleId:roleId },
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
	},
	initGrid2 : function() {
		$("#jqGrid2").jqGrid({
	        url: baseURL + 'sys/user/list',
	        datatype: "json",
	        colModel: [			
				{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
				{ label: '用户名', name: 'username', width: 75 },
				{ label: '邮箱', name: 'email', width: 90 },
				{ label: '手机号', name: 'mobile', width: 100 }
	        ],
			viewrecords: true,
	        height: 385,
	        rowNum: 10,
			rowList : [10,30,50],
	        rownumbers: true, 
	        rownumWidth: 25, 
	        autowidth:false,
	        width:900,
	        multiselect: true,
	        pager: "#jqGridPager2",
	        postData: {notRoleId:roleId },
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
	},
	formatUserLink:function(cellValue, options, record) {
		return "<a href='javascript:void(0)' style='color:blue;' onclick=defFunction.cancelUserLink('"+record.userId+"')>取消授权</a>";
	},
	cancelUserLink:function(userId) {
		var p = {};
		p.userId = userId;
		p.roleId = roleId;
		$.ajax({
			type: "POST",
		    url: baseURL + "sys/user/cancelUser",
		    data: p,
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
	}
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null
		},
		showList1: true,
		showList2: false,
		title:null,
		user:{
			status:1,			
			roleIdList:[]
		},
		org:{
            id:null,
			orgName:null
        }
	},
	mounted: function() {
		roleId = getUrlKey("roleId");
	},
	methods: {
		query1: function () {
			vm.reload();
		},
		backToRole:function() {
			window.frameElement.src="modules/sys/role.html";
		},
		query2: function () {
			vm.reload2();
		},
		addUser: function(){
			vm.showList1 = false;
			vm.showList2 = true;
			
			defFunction.initGrid2();
		},
		save: function(){
			var grid = $("#jqGrid2");
			var rowKey = grid.getGridParam("selrow");
			if (!rowKey) {
				alert("请选择至少一条记录");
				return;
			}

			var userIds = grid.getGridParam("selarrrow");
			var p = {};
			p.userIds = userIds;
			p.roleId = roleId;
			$.ajax({
				type: "POST",
			    url: baseURL + "sys/user/assignUser",
			    data: p,
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
		},
		reload: function () {
			vm.showList1 = true;
			vm.showList2 = false;
			var page = $("#jqGrid1").jqGrid('getGridParam','page');
			$("#jqGrid1").jqGrid('setGridParam',{ 
				postData:{'username': vm.q.username, 'roleId' : roleId},
                page:page
            }).trigger("reloadGrid");
		},
		reload2: function () {
			var page = $("#jqGrid2").jqGrid('getGridParam','page');
			$("#jqGrid2").jqGrid('setGridParam',{ 
				postData:{'username': vm.q.username, 'notRoleId' : roleId},
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if(isBlank(vm.user.username)){
                alert("用户名不能为空");
                return true;
            }

            if(vm.user.userId == null && isBlank(vm.user.password)){
                alert("密码不能为空");
                return true;
            }

            if(isBlank(vm.user.email)){
                alert("邮箱不能为空");
                return true;
            }
			
            if(!validator.isEmail(vm.user.email)){
                alert("邮箱格式不正确");
                return true;
			}
			if(vm.user.roleIdList!=null&&vm.user.roleIdList.length>1){
				alert("角色不能多选");
				return true;
			}
			
        }
	}
});