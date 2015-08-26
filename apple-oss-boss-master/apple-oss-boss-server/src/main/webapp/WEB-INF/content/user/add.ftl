<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>添加用户</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
<link rel="StyleSheet" href="/css/dtree/dtree.css" type="text/css" />
<script type="text/javascript" src="/js/dtree/dtree.js"></script>

<link rel="stylesheet" type="text/css" href="/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="/js/zTreeJs/jquery.ztree.core-3.5.js"></script>

<style type="text/css">
    ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4; width:250px; height:260px;overflow-y:scroll;overflow-x:auto;}
</style>

<script type="text/javascript">

		var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};

		var zNodes =[
				<#list DEPART_LIST as depart>
					<#if depart_has_next>
						{id:${depart.id}, pId:${depart.parentId}, name:"${depart.name}", open:true},
					<#else>
						{id:${depart.id}, pId:${depart.parentId}, name:"${depart.name}", open:true}
					</#if>
				</#list>
		 ];

		function beforeClick(treeId, treeNode) {
			var check = (treeNode && !treeNode.isParent);
			if (!check) alert("只能选择部门...");
			return check;
		}
		
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes(),
			v = "";
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var departObj = $("#depart");
			departObj.attr("value", v);
			$("#departId").val(treeNode.id);
			hideMenu();
		}

		function showMenu() {
			var departObj = $("#depart");
			var cityOffset = $("#depart").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + departObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
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
			"email": "email",
			"depart": "required"
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

	<form id="inputForm" method="post" action="save">
	<input type="hidden" name="isadmin" value="0" />
	
    <!-- start of con_search -->
	<div class="con_search">
	
    	<span class="con_box_BL"></span>
        
        <!-- start of add_list_table -->
        <table class="add_list_table input tabContent">
            <tr>
                <th class="padT20">用户名：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="username" value="" maxlength="19"></td>
            </tr>
            <tr>
                <th class="padT20">真实姓名：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="realname" value="" maxlength="29"></td>
            </tr>
            <tr>
                <th class="padT20">密码：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="password" value="" maxlength="29"></td>
            </tr>
            <tr>
                <th class="padT20">手机：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="mobile" value="" maxlength="64"></td>
            </tr>
            <tr>
                <th class="padT20">邮箱：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="email" value="" maxlength="250"></td>
            </tr>
     		<tr>
                <th class="padT20">所属部门：</th>
                <td class="padT20">
                	<input class="c_input_text text" type="text" id="depart" name="depart" value="" maxlength="250" readonly onclick="showMenu(); return false;" />
                	<input type="hidden" id="departId" name="department.id" value="" />
                	<a id="menuBtn" href="javascript:void(0);" onclick="showMenu(); return false;">选择</a>
                </td>
            </tr>
            <tr>
                <th>角色选项：</th>
                <td>
                	<div class="ullicss_">
                	<ul>
               		<#list ROLE_LIST as role>
						<li><input id="userRole${role.id}" name="roleIds" value='${role.id}' type='checkbox' >${role.roleName}</li>
					</#list>
					</ul>
					</div>
                </td>
            </tr>
            <tr>
                <th class="padT20">状态：</th>
                <td class="padT20">
                	<input name="state" value='1' type='radio' checked="checked" />启动
                	<input name="state" value='0' type='radio' />禁用
                </td>
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

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;"></ul>
</div>

</body>
</html>