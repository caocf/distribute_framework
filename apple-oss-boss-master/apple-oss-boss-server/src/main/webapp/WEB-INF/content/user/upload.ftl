<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>添加用户</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
<link rel="StyleSheet" href="/css/dtree/dtree.css" type="text/css" />
<script type="text/javascript" src="/js/dtree/dtree.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			"username": {
				required: true,
				remote: "check_username"
			},
			"password": "required",
			"realname": "required",
			"email": "email"
		},
		messages: {
			"username": {
				remote: "用户名已存在"
			}
		},
		submitHandler:function(form){
            form.submit();
        }
	});
		
	
});


dTree.prototype.cc = function(nId, pId) {
	alert(nId);
	
	
	if(pId == -1) {
		alert(pId);
	}
};
</script>

</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="inputForm" method="post" action="batch_update" enctype="multipart/form-data">
	<input type="hidden" name="isadmin" value="0" />
	
    <!-- start of con_search -->
	<div class="con_search">
	
    	<span class="con_box_BL"></span>
        
        <!-- start of add_list_table -->
        <table class="add_list_table input tabContent">
            <tr>
                <th class="padT20">通讯录：</th>
                <td class="padT20"><input class="c_input_text text" type="file" name="file" value="" ></td>
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