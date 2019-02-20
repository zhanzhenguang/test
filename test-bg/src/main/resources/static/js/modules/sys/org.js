var setting = {
    data: {
    	simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl",
            name:"orgName"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        showCode : true,
        title: null,
        org:{
            parentName:null,
            parentId:0,
            orderNum:0,
            orgName:"",
        },
        orgTypes:[
            {name:'请选择类型',id:''},
            {name:'集团',id:'0'},
            {name:'区域',id:'1'},
            {name:'公司',id:'2'},
            {name:'门店',id:'3'},
            {name:'其它',id:'4'}
		]
    },
    methods: {
        getOrg: function(){
            //加载机构树
            $.get(baseURL + "sys/org/select", function(r){
                ztree = $.fn.zTree.init($("#orgTree"), setting, r.orgList);
                var node = ztree.getNodeByParam("id", vm.org.parentId);
                ztree.selectNode(node);
                
                vm.org.parentName = node.orgName;
            })
        },
        add: function(){
            vm.showList = false;
            vm.showCode = false;
            vm.title = "新增";
            vm.org = {parentName:null,parentId:0,type:1,orderNum:0};
            vm.getOrg();
        },
        update: function () {
         vm.showCode = true;
            var id = getOrgId();
            if(id == null){
                return ;
            }
            
            $.get(baseURL + "sys/org/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.org = r.org;
				
                vm.getOrg();
            });
            $('#primary').show();
        },
        check: function () {
            vm.showCode = true;
               var id = getOrgId();
               if(id == null){
                   return ;
               }
               
               $.get(baseURL + "sys/org/info/"+id, function(r){
                   vm.showList = false;
                   vm.title = "查看";
                   vm.org = r.org;
   				
                   vm.getOrg();
               });
               $('#primary').hide();
           },
        del: function () {
            var id = getOrgId();
            if(id == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/org/delete",
                    data: "id=" + id,
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
            });
        },
        saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

            var url = vm.org.id == null ? "sys/org/save" : "sys/org/update";
            console.log(JSON.stringify(vm.org));
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.org),
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
                    //选择上级机构
                    vm.org.parentId = node[0].id;
                    vm.org.parentName = node[0].orgName;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Org.table.refresh();
        },
        validator: function () {
            if(isBlank(vm.org.orgName)){
                alert("机构名称不能为空");
                return true;
            }
        }
    }
});


var Org = {
    id: "orgTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Org.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '机构编码', field: 'orgCode',  align: 'center', valign: 'middle', width: '80px'},
        {title: '机构名称', field: 'orgName', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: 'SAP公司编号', field: 'sapCode', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '上级机构', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}]
    return columns;
};


function getOrgId () {
    var selected = $('#orgTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = Org.initColumn();
    var table = new TreeTable(Org.id, baseURL + "sys/org/list", colunms, "id");
    console.log(table)
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Org.table = table;
});
