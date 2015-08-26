<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>菜单左部</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<!-- start of con_left -->
<div class="con_left">
	<ul class="sub_nav">
		<#list LEFT_MENU_LIST as menu>
		<@authorize menuId=menu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
			<#if hasRights == 1>
				<li><a href="javascript:void(0);">${menu.urlName}</a>
		     		<ul class="ul_sub_nav_down_menu clearfix">
		     		<#list CHILD_MAP_MENU_LIST[(menu.id)?c + ''] as childMenu>
		     			<@authorize menuId=childMenu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
		     				<#if hasRights == 1>
		     					<li><a class="showmenu" href="javascript:void(0);" id="${childMenu.id}"><em>${childMenu.urlName}</em></a></li>
		     				</#if>
		     			</@authorize>
					</#list>
					</ul>
		        </li>
			</#if>
		</@authorize>
   		</#list>
    </ul>
</div>
<!-- end of con_left -->

<div id="slideFrame_button" class="slideFrame_btn" title="点击关闭左边栏" onClick="switchBar(this)"></div>

<script type="text/javascript">
$(document).ready(function() {
     $("a.showmenu").each(function () {
         $(this).bind("click",function(){
         	 var id = $(this).attr("id");
             var url = "menu_right?id=" + id;
             //self.top.frames[2].location.href=url;
             self.parent.frames[2].location.href = url;
         });
     });
      $("a.showmenu").eq(0).click();
});
</script>

</body>
</html>