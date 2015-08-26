<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>添加菜单</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			"urlName": "required",
			"iorder": {
				required: true,
				digits: true
			}
		},
		messages: {
			"iorder": {
				required: "必填",
				digits: "只允许输入正整数"
			}
		},
		submitHandler:function(form){
            form.submit();
        }
	});
		
	
});
</script>

</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="inputForm" method="post" action="save">
	<input type="hidden" name="state" value="1" />
	
    <!-- start of con_search -->
	<div class="con_search">
	
    	<span class="con_box_BL"></span>
        
        <!-- start of add_list_table -->
        <table class="add_list_table input tabContent">
            <tr>
                <th class="padT20">菜单名称：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="urlName" value=""></td>
            </tr>
            <tr>
                <th>打开模式：</th>
                <td>
                	<input name="openStyle" value='0' type='radio' checked="checked" >本系统
                	<input name="openStyle" value='1' type='radio' >新系统
                </td>
            </tr>
            
          	<#if parentId == 0>
          	<tr>
                <th>ICON地址：</th>
                <td><input class="c_input_text text" type="text" name="iconUrl" value=""></td>
            </tr>
            <tr>
                <th>顶级菜单地址：</th>
                <td><input class="c_input_text text" type="text" name="baseUrl" value=""></td>
            </tr>
            <#else>
	            <#if PARENT_MENU.grade == 3 >
	            <tr>
	                <th>菜单地址：</th>
	                <td>${(PARENT_MENU.baseUrl)!}<input class="c_input_text text" type="text" name="url" value=""></td>
	            </tr>
	            <tr>
	                <th>是否隐藏左侧菜单：</th>
	                <td>
	                	<input name="isHidden" value='0' type='radio' checked="checked" >否
                		<input name="isHidden" value='1' type='radio' >是
	                </td>
	            </tr>
	            </#if>
            </#if>
            
            <tr>
                <th>上级菜单：</th>
                <td>
                	<#if parentId != 0>${(PARENT_MENU.urlName)!}<#else>顶级菜单</#if>
					<input type="hidden" name="parentId" value="${parentId}" />
                </td>
            </tr>
            
            <tr>
                <th>排序：</th>
                <td><input class="c_input_text text" type="text" name="iorder" value="1"></td>
            </tr>
        </table>
        <!-- end of add_list_table -->
        
        <span class="con_box_BR"></span>
    </div>
    <!-- end of con_search -->
    
    <!-- start of main_slide_con -->
    <div class="main_slide_con">
    	<input type="submit" class="btnC" value="确定" />
        <a class="btnD" href="#" onclick="location.href='list'">取消</a>
        
    </div>
    <!-- end of main_slide_con -->
    
    </form>
</div>
<!-- end of con_right_main -->




</body>
</html>