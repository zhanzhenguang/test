var setting = {
    data: {
    	simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
        	name: "orgName",
            url:"nourl"
        }
    }
};
var ztree;
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '机构', name: 'orgName', width: 100 },
			{ label: '机构', name: 'orgId',hidden:true, width: 100 }
			/* { label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}}, */
			/* { label: '创建时间', name: 'createTime', index: "create_time", width: 80} */
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
			username: null
		},
		showList: true,
		title:null,
		roleList:{},
		appList:{},
		showPwd:true,
		flowRoleList:[],
		flowRoleIdList:[],
		flowRoleNameList:[],
		list: [],
		user:{
			status:1,			
			roleIdList:[]
		},
		org:{
            id:null,
			orgName:null
        }
	},
	watch: {
		flowRoleIdList: function(val, oldVal){
			let flowRoleList = this.$data.flowRoleList,arr=[];
			for(var i=0;i<val.length;i++){
				for(var j=0;j<flowRoleList.length;j++){
					if(flowRoleList[j].executeRole==val[i]){
						arr.push(flowRoleList[j].executeRoleName);
						break;
					}
				}
			}
			this.$data.flowRoleNameList=arr;
		}
	},
	methods: {
		getOrg: function(type){
            //加载机构树
            $.get(baseURL + "sys/org/select", function(r){
                ztree = $.fn.zTree.init($("#orgTree"), setting, r.orgList);
                if(type == 'update') {
                	var node = ztree.getNodeByParam("id", vm.org.id);
                    ztree.selectNode(node);

                    vm.org.orgName = node.orgName;
                }
            })
        },
		query: function () {
			vm.reload();
		},
		reset: function(){
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			if(userIds.length>1){
				alert("不能多条数据");
				return ;
			}
			confirm('确定要重置选中的用户密码？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/user/reset/"+userIds[0],
                    contentType: "application/json",
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
		flowRoleListDialog: function(){
			layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择流程角色",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#flowRoleListLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.close(index);
                }
            });
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.showPwd = true;
			vm.roleList = {};
			vm.appList = {};
			vm.flowRoleIdList=[];
			vm.flowRoleNameList=[];
			vm.user = {status:1,roleIdList:[],flowRoleIdList:[]};
			vm.org.orgId="";
			vm.org.orgName="";
			//获取角色信息
			this.getRoleList();
			//获取流程角色信息
			this.getFlowList();
			
			vm.getOrg("add");
			
		},
		update: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
            vm.title = "修改";
            vm.showList = false;
			vm.showPwd = false;
			vm.flowRoleIdList=[];
			vm.flowRoleNameList=[];
			$.ajaxSettings.async = false;
			vm.getUser(userId);
			$.ajaxSettings.async = true;
			//获取角色信息
			this.getRoleList();
			//获取流程角色信息
			this.getFlowList();
            vm.getOrg("update");
            vm.org.orgName = "";
            $("#primary").show();
		},
		check : function() {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
            vm.title = "查看";
            vm.showList = false;
			vm.showPwd = false;
			vm.flowRoleIdList=[];
			vm.flowRoleNameList=[];
			$.ajaxSettings.async = false;
			vm.getUser(userId);
			$.ajaxSettings.async = true;
			//获取角色信息
			this.getRoleList();
			//获取流程角色信息
			this.getFlowList();
            vm.getOrg("check");
            vm.org.orgName = "";
			 //设置禁用
            $("#username").prop('disabled', true);
            $("#email").prop('disabled', true);
            $("#mobile").prop('disabled', true);
            $("#orgName").prop('disabled', true); 
            $("#flowRoleNameList").prop('disabled', true); 
            
            $("#primary").hide();
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
				    data: JSON.stringify(userIds),
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
		orgTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择机构",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#orgLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择机构
                    vm.org.id = node[0].id;
                    vm.org.orgName = node[0].orgName;
                    
                    if(vm.user.orgIdList == null) {
                    	var arr = [];
                    	arr.push(node[0].id);
                    	vm.user.orgIdList = arr;
                    } else {
                    	vm.user.orgIdList[0] = node[0].id;
                    }

                    layer.close(index);
                }
            });
        },
		saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

			var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
			//console.log(vm.user1);
			if(vm.user.roleIdList==null||vm.user.roleIdList.length==0){
				alert("角色不能为空");
				return;
			}
			if(vm.user.orgIdList==null||vm.user.orgIdList.length==0){
				alert("机构不能为空");
				return;
			}
			vm.user.flowRoleIdList = vm.flowRoleIdList;
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
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
		getUser: function(userId){
			$.get(baseURL + "sys/user/info/"+userId, function(r){
				vm.user = r.user;
				vm.org.id = vm.user.orgIdList[0]
				vm.flowRoleIdList=vm.user.flowRoleIdList;
				vm.flowRoleNameList=vm.user.flowRoleNameList;
				vm.user.password = null;
			});
		},
		getRoleList: function(){
			$.get(baseURL + "sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
		getFlowList: function(){
			$.get(baseURL + "flow/list/select", function(r){
				vm.list = r.list;
				//console.log(vm.flowRoleList)
			});
		},
		reload: function () {
			vm.showList = true;
			$("#username").prop('disabled', false);
            $("#email").prop('disabled', false);
            $("#mobile").prop('disabled', false);
            $("#orgName").prop('disabled', false); 
            $("#flowRoleNameList").prop('disabled', false); 
			var page = 1;//$("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
		},
		getFlowRoles: function(t,i,flow){
			this.list.forEach(function (item,index) {
				if(index==i){
					if(item.active){
						Vue.set(item,'active',false);
					}else{
						Vue.set(item,'active',true);
						
						if(item.flowRoleList==null||item.flowRoleList==""||item.flowRoleList==undefined){
							$.get(baseURL + "flow/executerole/selectForUser/"+item.flowCode, function(r){
								Vue.set(item,'flowRoleList',r.list);
							});
							
						}
					}
				}else{
					Vue.set(item,'active',false	);
				}
			});
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
			
			/*if(vm.flowRoleIdList!=null){
				alert("审批角色关联不能为空");
				return true;
			}*/
        }
	}
});