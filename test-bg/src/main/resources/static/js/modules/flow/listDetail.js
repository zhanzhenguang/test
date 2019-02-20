$(function () {
  
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		list: {}
	},
    mounted: function () {
    	let formId = getUrlKey("formId");
    	let node = getUrlKey("node");
        this.getInfo(getUrlKey("formId"));
    },
	watch: {
		flowRoleIdList: function(val, oldVal){
			this.$data.flowRoleNameList=val;
		},
		flowCode: function(val, oldVal){
			this.$data.list.flowCode=val;
		},		
		flowName: function(val, oldVal){
			this.$data.list.flowName=val;
		},		
		detailUrl: function(val, oldVal){
			this.$data.list.detailUrl=val;
		}	,	
		status: function(val, oldVal){
			this.$data.list.status=val;
		},		
		createUserId: function(val, oldVal){
			this.$data.list.createUserId=val;
		},		
		createTime: function(val, oldVal){
			this.$data.list.createTime=val;
		}	,	
		updateUserId: function(val, oldVal){
			this.$data.list.updateUserId=val;
		},		
		updateTime: function(val, oldVal){
			this.$data.list.updateTime=val;
		}		
	},
	methods: {
		getInfo: function(flowCode){
			$.get(baseURL + "flow/list/info/1233", function(r){
                vm.list = r.list;
                vm.list.status = dataItemText(constans.status,vm.list.status);
            });
		},
	}
});