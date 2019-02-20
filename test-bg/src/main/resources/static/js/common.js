// 请求前缀
var baseURL = "http://127.0.0.1:8086/zzg/";
var baseWebURL = "http://127.0.0.1:8086/zzg/";
//字典
function dataItem(dataItem, key, css) {
	var item = "";
	key = parseInt(key);
	if(dataItem[key]!=undefined&&dataItem[key]!=""){
		item = dataItem[key];
	}
	if (css == undefined || css == '') {
		css = "success";
	}
	return '<span class="label label-' + css + '">' + item + '</span>';
}
function dataItem1(dataItem, key, css) {
	var item = "";
	for(var i=0;i<dataItem.length;i++){
		if(dataItem[i].id==key){
			item=dataItem[i].name;
			break;
		}
	}
	if (css == undefined || css == '') {
		css = "success";
	}
	return '<span class="label label-' + css + '">' + item + '</span>';
}
function dataItemText(dataItem, key, css) {
	if(dataItem[key]!=undefined&&dataItem[key]!=""){
		return dataItem[key];
	}
}
function setSelectData(dataItem){
	var arr = [];
	for(var i=0;i<dataItem.length;i++){
		arr.push({
			id:i,
			text:dataItem[i]
		});
	} 
	return arr;
}
var constans = {
	status : [ '禁用', '正常' ]
	,status1 : [ '未知','正常', '完成' ]
	,status2 : [ '未知','未生成凭据', '生成凭据' ]
	,status3 : [ '未知','未处理', '已处理' ]
	,confirm : [ '是', '否' ]
	,nodeStatus : [ '', '已处理', '未处理', '待处理' ]
	,nodeTyp:[ '', '开始', '结束', '普通' ]
}
var constansItem = {
	nodeTyp:[ {id:1,name:"开始"}, {id:2,name:"结束"}, {id:3,name:"普通"} ]
	,status2:[ {id:1,name:"未生成凭据"}, {id:2,name:"生成凭据"}]
	,costType:[ ]
}

function getDicName(arr, key) {
	var result = "";
	$.each(arr, function(i, o) {
		if(o.id == key) {
			result = o.name;
		}
	})
	return result;
}

function getUrlKey(name){
	return decodeURIComponent((new RegExp('[?|&]'+name+'='+'([^&;]+?)(&|#|;|$)').exec(location.href)||[,""])[1].replace(/\+/g,'%20'))||null;
}
//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

// 工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};
T.p = url;

// 登录token
var token = localStorage.getItem("token");
//debugger
if (token == 'null') {
	parent.location.href = baseWebURL + 'login.html';
}

// jquery全局配置
$.ajaxSetup({
	dataType : "json",
	cache : false,
	headers : {
		"token" : token
	},
	xhrFields : {
		withCredentials : true
	},
	complete : function(xhr) {
		// token过期，则跳转到登录页面
		//debugger
		if (xhr.responseJSON.code == 401) {
			parent.location.href = baseWebURL + 'login.html';
		}else if(xhr.responseJSON==undefined){
			alert("系统访问异常，请联系管理员");
		}else{
			if(localStorage.getItem("token")==null){
				parent.location.href = baseWebURL + 'login.html';
			}
		}
	}
});

// jqgrid全局配置
$.extend($.jgrid.defaults, {
	ajaxGridOptions : {
		headers : {
			"token" : token
		}
	}
});

// 权限判断
function hasPermission(permission) {
	if (window.parent.permissions.indexOf(permission) > -1) {
		return true;
	} else {
		return false;
	}
}

// 重写alert
window.alert = function(msg, callback) {
	parent.layer.alert(msg, function(index) {
		parent.layer.close(index);
		if (typeof (callback) === "function") {
			callback("ok");
		}
	});
}

// 重写confirm式样框
window.confirm = function(msg, callback) {
	parent.layer.confirm(msg, {
		btn : [ '确定', '取消' ]
	}, function() {// 确定事件
		if (typeof (callback) === "function") {
			callback("ok");
		}
	});
}

// 选择一条记录
function getSelectedRow(id) {
	if(id==undefined){
		id = "#jqGrid";
	}
	var grid = $(id);
	var rowKey = grid.getGridParam("selrow");
	if (!rowKey) {
		alert("请选择一条记录");
		return;
	}

	var selectedIDs = grid.getGridParam("selarrrow");
	if (selectedIDs.length > 1) {
		alert("只能选择一条记录");
		return;
	}

	return selectedIDs[0];
}

// 选择多条记录
function getSelectedRows() {
	var grid = $("#jqGrid");
	var rowKey = grid.getGridParam("selrow");
	if (!rowKey) {
		alert("请选择一条记录");
		return;
	}
	return grid.getGridParam("selarrrow");
}

// 判断是否为空
function isBlank(value) {
	return !value || !/\S/.test(value)
}
function extend(obj1,obj2){
    for(var attr in obj2){
        obj1[attr] =  obj2[attr];
    }
}
function SetStep(arg){
    this.body=document.body;
    this.opt = {
        show:false,
        content:'.stepCont',
        pageCont:'.pageCont',
        imgWidth:20,
        stepContainerMar:20,
        nextBtn:'.nextBtn',
        prevBtn:'.prevBtn',
        steps:['发起','进行','结束'],
        //pageClass:'',//分页的类或则id
        stepCounts:3,//总共的步骤数
        curStep:1,//当前显示第几页
        animating:false,
        showBtn:true,//是否生成上一步下一步操作按钮
        clickAble:true,//是否可以通过点击进度条的节点操作进度
        onLoad: function(){

        }
    }
    this.init(arg)
}
//初始化 生成页面的进度条和按钮
SetStep.prototype.init=function(arg){
    var _that=this;
    extend(this.opt,arg);
    this.opt.stepCounts=this.opt.steps.length;
    this.content=$(this.opt.content);
    this.pageCont=this.content.find(this.opt.pageCont)
    var w_con=$(this.content).width();
    var w_li=(w_con-this.opt.stepContainerMar*2)/this.opt.stepCounts/2;
    var stepContainer=this.content.find('.ystep-container');
    this.stepContainer=stepContainer;
    var stepsHtml=$("<ul class='ystep-container-steps'></ul>");
    var stepDisc = "<li class='ystep-step ystep-step-undone'></li>";
    var stepP=$("<div class='ystep-progress'>"+
                "<p class='ystep-progress-bar'><span class='ystep-progress-highlight' style='width:0%'></span></p>"+
            "</div>");
    var stepButtonHtml =$( "<div class='step-button'><button type='button' class='btn btn-default prevBtn' id='prevBtn' class='prevBtn'>上一步</button>"+
                        "<button type='button' class='btn btn-default nextBtn' id='nextBtn' class='nextBtn'>下一步</button></div>");
    stepP.css('width',w_li*2*(this.opt.stepCounts-1));
    stepP.find('.ystep-progress-bar').css('width',w_li*2*(this.opt.stepCounts-1))
    for(var i=0;i<this.opt.stepCounts;i++){
        if(i==0){
            var _s=$(stepDisc).append("<span style='width:165px;float:left;font-size: 12px;'>"+this.opt.steps[i]+"</span>").addClass('')
        }else{
            var _s=$(stepDisc).append("<span style='width:165px;float:left;font-size: 12px;'>"+this.opt.steps[i]+"</span>")
        }
        stepsHtml.append(_s);
    }
    stepsHtml.find('li').css('width','40px').css('marginRight',w_li*2-40)
    stepContainer.append(stepsHtml).append(stepP);
    
    stepContainer.css('left',(w_con-stepP.width()-this.opt.imgWidth-10-this.opt.stepContainerMar*2)/2)
    this.content.css('overflow','hidden')
    this.setProgress(this.stepContainer,this.opt.curStep,this.opt.stepCounts)
    //判断参数 是否显示按钮 并绑定点击事件
    if(this.opt.showBtn){
        this.content.append(stepButtonHtml)
        this.prevBtn=this.content.find(this.opt.prevBtn)
        this.nextBtn=this.content.find(this.opt.nextBtn)
        this.prevBtn.on('click',function(){
            // if($(this).hasClass('handleAble')){
            if($(_that).attr('disabled')||_that.opt.animating){
                return false;
            }else{
                _that.opt.animating=true;
                _that.opt.curStep--;
                _that.setProgress(_that.stepContainer,_that.opt.curStep,_that.opt.stepCounts)
            }
        })
        this.nextBtn.on('click',function(){
            // if($(this).hasClass('handleAble')){
            if($(_that).attr('disabled')||_that.opt.animating){
                return false;
            }else{
                _that.opt.animating=true;
                _that.opt.curStep++;
                _that.setProgress(_that.stepContainer,_that.opt.curStep,_that.opt.stepCounts)
            }
        })
    }
    //判断时候可点击进度条 并绑定点击事件
    if(this.opt.clickAble){
        stepsHtml.find('li').on('click',function(){
            _that.opt.curStep=$(this).index()+1;
            _that.setProgress(_that.stepContainer,_that.opt.curStep,_that.opt.stepCounts)
        })
    }
     $(window).resize(function(){
        var w_con=$(_that.content).width();
        var w_li=w_con/_that.opt.stepCounts/2;
        stepP.css('width',w_li*2*(_that.opt.stepCounts-1));
        stepP.find('.ystep-progress-bar').css('width',w_li*2*(_that.opt.stepCounts-1))
        stepsHtml.find('li').css('width','40px').css('marginRight',w_li*2-40)
        stepContainer.css('left',(w_con-stepP.width()-_that.opt.imgWidth-10-_that.opt.stepContainerMar*2)/2)
     })
}
//设置进度条
SetStep.prototype.setProgress=function(n,curIndex,stepsLen){
      var _that=this;
        //获取当前容器下所有的步骤
        var $steps = $(n).find("li");
        var $progress =$(n).find(".ystep-progress-highlight");
        //判断当前步骤是否在范围内
        if(1<=curIndex && curIndex<=$steps.length){
          //更新进度
          var scale = "%";
          scale = Math.round((curIndex-1)*100/($steps.length-1))+scale;
          $progress.animate({
            width: scale
          },{
            speed: 1000,
            done: function() {
              //移动节点
              $steps.each(function(j,m){
                var _$m = $(m);
                var _j = j+1;
                if(_j < curIndex){
                  _$m.attr("class","ystep-step-done");
                }else if(_j === curIndex){
                  _$m.attr("class","ystep-step-active");
                }else if(_j > curIndex){
                  _$m.attr("class","ystep-step-undone");
                }
              })
              if(_that.opt.showBtn){
                  if(curIndex==1){
                      _that.prevBtn.attr('disabled','true')
                      _that.nextBtn.removeAttr('disabled')
                  }else if(curIndex==stepsLen){
                      _that.prevBtn.removeAttr('disabled')
                      _that.nextBtn.attr('disabled','true')
                  }else if(1<curIndex<stepsLen){
                      _that.prevBtn.removeAttr('disabled')
                      _that.nextBtn.removeAttr('disabled')
                  }
              }
               _that.checkPage(_that.pageCont,_that.opt.curStep,_that.opt.stepCounts)
               _that.opt.animating=false;
            }
          });  
        }else{
            return false;
        }
}
//改变 分页显示
SetStep.prototype.checkPage=function(pageCont,curStep,steps){
    for(var i = 1; i <= steps; i++){
        if(i === curStep){
          pageCont.find('#page'+i).css("display","block");
        }else{
          pageCont.find('#page'+i).css("display","none");
        }
    }
}