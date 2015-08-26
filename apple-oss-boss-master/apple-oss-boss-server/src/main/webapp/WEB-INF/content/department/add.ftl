<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>添加部门</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			"name": "required",
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
                <th class="padT20">部门名称：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="name" value=""></td>
            </tr>
                        
            <tr>
                <th>上级部门：</th>
                <td>
                	<#if parentId != 0>${(PARENT_DEPARTMENT.name)!}<#else>顶级菜单</#if>
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