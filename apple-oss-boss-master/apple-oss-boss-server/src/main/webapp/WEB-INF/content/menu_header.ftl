<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>菜单头部</title>

<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/artDialog/artDialog.source.js?skin=apple"></script>
<script type="text/javascript" src="js/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="js/pop.js"></script>
<script type="text/javascript" src="js/password_th.js"></script>

</head>
<body>

<!-- start of header -->
<div class="header clearfix">
	<h1><a class="LOGO" href="#">OSS平台</a></h1>
    <div class="head_right">
    	<div class="head_top_link clearfix">
            <ul class="ul_head_top_link right">
                <li><span class="name">${CAS_USER.realname}</span>|</li>
                <li><a href="javascript:void(0);" onclick="gotoPassword();">账户设置</a>|</li>
                <li><a class="down_menu" href="javascript:void(0);" onclick="gotoPanel();">控制面板</a>|
                    <ul class="ul_down_menu" style="display:none;">
                    <#list ROOT_MENU_LIST as menu>
                	<@authorize menuId=menu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
						<#if hasRights == 1><li><a href="${menu.id}">${menu.urlName}</a></li></#if>
					</@authorize></#list>
                    </ul>
                </li>
                <li><a id="logout" href="logout">退出</a></li>
            </ul>
        </div>
    	<div class="main_nav">
        	<ul class="nav clearfix">
        		<#list SECOND_MENU_LIST as menu>
        			<@authorize menuId=menu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
						<#if hasRights == 1>
							<li><a class="showmenu<#if menu_index == 0> cur</#if>" href="javascript:void(0);" id="${(menu.id)?c}">${menu.urlName}</a></li>
						</#if>
					</@authorize>
            	</#list>
            </ul>
        </div>
    </div>
</div>
<!-- end of header -->

<script type="text/javascript">
$(document).ready(function() {
     $("a.showmenu").each(function () {
         $(this).bind("click",function(){
         	 var id = $(this).attr("id");
             var url = "menu_left?id=" + id;
             //self.top.frames[1].location.href=url;
             self.parent.frames[1].location.href = url;
             
             $("a.showmenu").removeClass("cur");
             $(this).addClass("cur");
         });
     });
	$("a.showmenu").eq(0).click();
    	
});

function gotoPassword() {
	pop_password_th();
}

function gotoPanel(){
	art.dialog.open('/panel', {
		id: 'gotoPanel',
		title: '导航面板',
		close: function () {
			
		}
	}, false);
}

</script>
</body>
</html>