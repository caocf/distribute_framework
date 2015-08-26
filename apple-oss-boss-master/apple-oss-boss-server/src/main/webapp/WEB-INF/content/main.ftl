<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>OSS平台_控制面板</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<!--[if IE 6]>
<script src="js/DD_belatedPNG.js"></script>
<script>
DD_belatedPNG.fix('*');
</script>
<![endif]-->
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/slideimg.js"></script>
<script type="text/javascript" src="js/artDialog/artDialog.source.js?skin=apple"></script>
<script type="text/javascript" src="js/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="js/pop.js"></script>
<script type="text/javascript" src="js/password_th.js"></script>
<script>
$(document).ready(function(){
	$("#slideimg2").slideimg({slide_class:'.slideimg_con'});
});

function gotoPassword() {
	pop_password_th();
}
</script> 
</head>

<body>
<!-- start of header -->
<div class="header clearfix">
    <h1><a class="LOGO" href="/main">OSS平台</a></h1>
    <div class="head_top_link clearfix">
        <ul class="ul_head_top_link right">
            <li><span class="name">${(CAS_USER.realname)!}</span></li>
            <li><a href="javascript:void(0);" onclick="gotoPassword();">账户设置</a></li>
            <li><a href="logout">退出</a></li>
        </ul>
    </div>
</div>
<!-- end of header -->

<div class="control_bg">
    <!-- start of container -->
    <div id="slideimg2" class="container">
        <div class="slideimg_show">
            <div class="slideimg_con">
                <ul class="control_panel_list clearfix">
                	<#assign menuCount = 0 />
                	<#list ROOT_MENU_LIST as menu>
                   	<@authorize menuId=menu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
						<#if hasRights == 1>
							<li><a class="ico" <#if menu.openStyle=0> href="menu_main?id=${(menu.id)?c}"<#else> href="${(menu.baseUrl)!''}" target="_blank"</#if>><img src="${(menu.iconUrl)!}"></a><h3><a href="#">${menu.urlName}</a></h3></li>
							<#assign menuCount=menuCount+1 />
						</#if>
					</@authorize>
                    <#if (menuCount > 1 && menuCount % 8 == 0)>
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
    
    <!-- start of footer -->
    <div class="footer">
        <p>Copyright © 2012-2013 深圳XXX有限公司 版权所有</p>
    </div>
    <!-- end of footer -->
</div>
</body>
</html>