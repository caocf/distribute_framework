<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>OSS平台_控制面板弹窗</title>
<link rel="stylesheet" type="text/css" href="css/login.css" />
<!--[if IE 6]>
<script src="js/DD_belatedPNG.js"></script>
<script>
DD_belatedPNG.fix('*');
</script>
<![endif]-->
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/slideimg.js"></script>
<script>
$(document).ready(function(){

	$("#slideimg2").slideimg({slide_class:'.slideimg_con'});
	
	$(".gotoMenu").click(function(){
	  	var url = $(this).attr("url");
	  	var openStyle = $(this).attr("openStyle");
	  	
	  	if(openStyle == 0) {
	  		parent.location.href = url;
	  	}
	  	else {
	  		window.open(url);
	  	}
	});
	
	
});
</script>
<style type="text/css">
/*--- 样式重定义 ---*/
.container{ width:560px; height:320px;}
.slideimg_show{ width:560px; height:288px;}
.control_panel_list{ width:560px; padding:0;}
.control_panel_list li{ width:120px; height:124px; padding:10px;}
.control_panel_list li a.ico{ width:100px; height:100px; margin:0 auto 4px;}
.control_panel_list li a.ico img{ width:100px; height:100px;}
</style>
</head>

<body>
<!-- start of container -->
<div id="slideimg2" class="container">
    <div class="slideimg_show">
        <div class="slideimg_con">
            <ul class="control_panel_list clearfix">
					<#assign menuCount = 0 />
                	<#list ROOT_MENU_LIST as menu>
                   	<@authorize menuId=menu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
						<#if hasRights == 1>
							<li><a class="ico gotoMenu" <#if menu.openStyle=0> url="menu_main?id=${(menu.id)?c}"<#else> url="${(menu.baseUrl)!''}"</#if> openStyle="${menu.openStyle}" href="javascript:void(0);"><img src="${(menu.iconUrl)!}"></a><h3><a href="#">${menu.urlName}</a></h3></li>
							 <#assign menuCount=menuCount+1 />
						</#if>
					</@authorize>
                    <#if (menuCount > 0 && menuCount % 8 == 0)>
                    	</ul>
                    	<ul class="control_panel_list clearfix">
                    </#if>
                    </#list>
            </ul>
        </div>
    </div>
    <div class="to_lr_btn">
        <a class="goleftbtn" href="javascript:void(0)"></a>
        <span class="center_dot"></span>
        <a class="gorightbtn" href="javascript:void(0)"></a>
    </div>
</div>
<!-- end of container -->
</body>
</html>