//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{},index:0},
    template:[
        /*'<li :class="{active: (item.type===0 && index === 0)}">',*/
        '<li>',
        '<a v-if="item.type === 0" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<i class="fa fa-angle-left pull-right"></i>',
        '</a>',
        '<ul v-if="item.type === 0" class="treeview-menu">',
        '<menu-item :item="item" :index="index" v-for="(item, index) in item.list"></menu-item>',
        '</ul>',
        '<a v-if="item.type === 1" :href="\'#\'+item.url">' +
        '<i v-if="item.icon != null" :class="item.icon"></i>' +
        '<i v-else class="fa fa-circle-o"></i> {{item.name}}' +
        '</a>',
        '</li>'
    ].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

//function getIframeByElement(element){
//    var iframe;
//    $("iframe").each(function(){
//        if(element.ownerDocument === this.contentWindow.document) {
//            iframe = this;
//        }
//        return !iframe;
//    });
//    return iframe;
//}

var vm = new Vue({
	el:'#rrapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"欢迎页"
	},
	mounted: function () {
	    console.log("app2");
	   /* $.ajax({
            type: "POST",
            url: baseURL + "fin/acctadjrec/info/1",
            dataType: "json",
            success: function(r){
            }
        });*/
	},
	methods: {
		getMenuList: function () {
			$.getJSON(baseURL + "sys/menu/nav", function(r){
				var menuList = r.menuList;
				for(var i = 0; i< menuList.length;i++){
                    var seList = menuList[i].list;
					for(var j = 0; j < seList.length; j++){
						var seUrl = seList[j].url;
						if(seUrl.indexOf("?") > 0){
                            seUrl = seUrl+"&v="+(new Date()).valueOf();
						}else{
                            seUrl = seUrl+"?v="+(new Date()).valueOf();
						}
                        seList[j].url = seUrl;
					}
                    menuList[i].list = seList;
				}
                r.menuList = menuList;
				vm.menuList = r.menuList;
                window.permissions = r.permissions;
			});
		},
		getUser: function(){
			$.getJSON(baseURL + "sys/user/info", function(r){
				vm.user = r.user;
			});
		},
		refeshCache: function(){
			confirm('确定要刷新当前缓存？', function(){
				$.get(baseURL + "sys/user/refreshCache/11", function(r){
					console.log(r);
					if(r.code == 0){
						alert('操作成功');
					}else{
						alert(r.msg);
					}
	            });
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: baseURL + "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(r){
							if(r.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(){
									location.reload();
								});
							}else{
								layer.alert(r.msg);
							}
						}
					});
	            }
			});
		},
        logout: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/logout",
                dataType: "json",
                success: function(r){
                    //删除本地token
                    localStorage.removeItem("token");
                    //跳转到登录页面
                    location.href = baseWebURL + 'login.html';
                }
            });
        },
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['', 'no']
            });
        }
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});



function routerList(router, menuList){
	
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
                $(".sidebar-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
