/**
  *  write by ftt
  *  time 2014.01.03
  */

$(function(){
	// 导航切换
	$('.nav li').delegate("a","click",function(){
		$(".nav li a").removeClass("cur");
		$(this).addClass("cur");
	});
	
	// 左侧二级导航显示隐藏
	$('.sub_nav').delegate("li","click",function(){
		$(this).addClass("cur").siblings().removeClass("cur");
	});
	$(".sub_nav > li > a").eq(0).click();
	
	// 左侧二级导航点击
	$('.sub_nav li').delegate(".ul_sub_nav_down_menu a","click",function(){
		$(".ul_sub_nav_down_menu li a").removeClass("focus");
		$(this).addClass("focus");
	});
	$(".ul_sub_nav_down_menu li a").eq(0).click();
	
	// 右侧搜索区显示隐藏
	$(".btn_box_tab").toggle(function(){
		$(window.parent.rightMainFrame.document).find(".con_search").slideUp(200);
	},function(){
		$(window.parent.rightMainFrame.document).find(".con_search").slideDown(200);
	});
	
	// input激活
	$('.auto_hint').bind("focus", function(){
          if($(this).val() == $(this).attr('realValue')) {
               $(this).val('');
               $(this).removeClass('auto_hint');
          }
     });
	 $('.auto_hint').bind("blur", function(){
          if($(this).val() == '' &&$(this).attr('realValue') != '') {
               $(this).val($(this).attr('realValue'));
               $(this).addClass('auto_hint');
          }
     });
     $('.auto_hint').each(function() {
          if($(this).attr('realValue') == '') {return;}
          if($(this).val() == '') {
               $(this).val($(this).attr('realValue'));
          }else {
               $(this).removeClass('auto_hint');
          }
     });

	// table鼠标经过
	$('.table_list').delegate("td", "mouseover",function(){
		$(this).parent().addClass("hover");
	}).delegate("td", "mouseout", function(){
		$(this).parent().removeClass("hover");
	});
		
	// 车辆监控-左侧栏显示隐藏-重构框架后
	$(".slideFrame_btn").toggle(function(){
		$(this).parents(".container").find(".con_left").css({ "width" : "0" });
		$(this).parents(".container").find(".con_left_mod").hide();
		$(this).parents(".container").css({ "padding-left" : "0" });
	},function(){
		$(this).parents(".container").find(".con_left").css({ "width" : "200px" });
		$(this).parents(".container").find(".con_left_mod").show();
		$(this).parents(".container").css({ "padding-left" : "200px" });
	});
	
	// 车辆监控-隐藏显示table
	$(".carMap_link").toggle(function(){
		$(this).html("显示");
		$(this).parents(".container").find(".carMap_table").hide();
		$(this).parents(".container").find(".carMap_con, .carMap_con .map_show").css({ "height" : $(window).height() - 64 + "px" });
		$(this).parents(".container").find(".carMap_con").css({ "margin-bottom" : "0" });
	},function(){
		$(this).html("隐藏");
		$(this).parents(".container").find(".carMap_table").show();
		$(this).parents(".container").find(".carMap_con, .carMap_con .map_show").css({ "height" : $(window).height() - 273 + "px" });
		$(this).parents(".container").find(".carMap_con").css({ "margin-bottom" : "10px" });
	});
	
	// 车辆监控-下拉
	$(".vehicle").delegate(".btn_vehicle", "click", function(){
		$(this).parents(".vehicle").find(".ul_vehicle_list").show();
	});
	$(".vehicle .ul_vehicle_list").delegate("li", "click", function(){
		$(this).parents(".vehicle").find(".c_input_text").val($(this).html());
		$(this).parent().hide();
	});
	$('.vehicle .ul_vehicle_list').delegate("li", "mouseover",function(){
		$(this).addClass("hover");
	}).delegate("li", "mouseout", function(){
		$(this).removeClass("hover");
	});
	
	// 车辆监控切换-历史轨迹/实时位置
	$(".historical_trajectory").hide();
	$(".map_tLeft").delegate(".history", "click", function(){
		$(this).addClass("cur");
		$(this).parents(".map_tLeft").find(".location").removeClass("cur");
		$(".historical_trajectory").show();
		$("#carTab").hide();
	});
	$(".map_tLeft").delegate(".location", "click", function(){
		$(this).addClass("cur");
		$(this).parents(".map_tLeft").find(".history").removeClass("cur");
		$(".historical_trajectory").hide();
		$("#carTab").show();
	});
	
	//默认页面加载时设置高度
	setHeight();
});

//当浏览器大小改变时重设置高度
onresize = function(){
	setHeight();
}
//打包为一个方法函数
function setHeight(){
	// iframe高度
	$(".adminIframe").css({"height": $(window).height() + "px"});
	
	// 左侧高度
	$(".con_left").css({"height": $(window).height() + "px"});
	$(".slideFrame_btn").css({"top": ($(".con_left").height() / 2) - 50 + "px"});
	
	// 车辆监控-高度
	$(".carMap_con, .carMap_con .map_show").css({"height": $(window).height() - 273 + "px"});
}

// 点击关闭左侧栏
function switchBar(obj){
	if(parent.document.getElementById("framesetContains").cols == "210,*"){
		parent.document.getElementById("framesetContains").cols="10,*";
		$(".con_left").css({"margin-left":"10px"});
	}else{
		parent.document.getElementById('framesetContains').cols = "210,*";
		$(".con_left").css({"margin-left":"0"});
	}
}

// 框架重构
function frameReconstructionBar(){
	parent.document.getElementById("framesetContains").cols="0,*";
	parent.document.getElementById("rightFrame").rows="0,*";
}

//框架重构
function frameReturnBar(){
	parent.document.getElementById("framesetContains").cols="210,*";
	parent.document.getElementById("rightFrame").rows="90,*";
}