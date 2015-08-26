<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>OSS平台</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
function gotoMain(){
	parent.parent.location.href = '/main';
}
</script>
</head>

<body>
<!-- start of con_right_header -->
<div class="con_right_header">
	<!-- start of 当前位置 -->
    <div class="path">您的当前位置：<a href="javascript:void(0);" onclick="gotoMain();">首页</a> > 
    	<#list PATH_MENU_LIST as menu>
    		<@authorize menuId=menu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
				<#if hasRights == 1>
					<a href="javascript:void(0);">${menu.urlName}</a> >
				</#if>
			</@authorize>
		</#list>
    </div>
    <!-- end of 当前位置 -->
    
    <!-- start of tab_tit -->
    <div class="tab_tit">
    	<span class="con_box_TL"></span>
    	<div class="tab_tit_show clearfix">
    	
    		<#list RIGHT_MENU_LIST as menu>
    			<@authorize menuId=menu.id rights=CAS_USER_RIGHTS isAdmin=CAS_USER.isadmin>
					<#if hasRights == 1>
						<#if menu.openStyle == 0>
							<a href="javascript:void(0);" isHidden="${(menu.isHidden)!}" class="showmenu<#if menu_index == 0> cur</#if>" url="${(menu.baseUrl)!''}${(menu.url)!}"><em>${menu.urlName}</em></a>
						<#else>
							<a href="javascript:void(0);" isHidden="${(menu.isHidden)!}" class="showmenu<#if menu_index == 0> cur</#if>" url="${(menu.url)!}"><em>${menu.urlName}</em></a>
						</#if>
					</#if>
				</@authorize>
			</#list>
        </div>
        <a class="btn_box_tab" href="javascript:void(0);"></a>
        <span class="con_box_TR"></span>
    </div>
    <!-- end of tab_tit -->
</div>
<!-- end of con_right_header -->

<script type="text/javascript">
$(document).ready(function() {
	$("a.showmenu").each(function () {
    	$(this).bind("click",function(){
     		var url = $(this).attr("url");
         	var isHidden = $(this).attr("isHidden");
         	 
        	//self.top.frames[3].location.href=url;
        	self.parent.frames[3].location.href = url;
        	$("a.showmenu").removeClass("cur");
          	$(this).addClass("cur");
             	
         	 if(isHidden == 1) {
         		frameReconstructionBar();
         	 }
         	 else {
         	 	frameReturnBar();
         	 }
         });
     });
      $("a.showmenu").eq(0).click();
});
</script>

</body>
</html>