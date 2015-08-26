<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>添加角色</title>
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
			"roleName": {
				required: true,
				remote: "check_rolename"
			},
			"depart": "required"
		},
		messages: {
			"roleName": {
				remote: "角色名称已存在"
			}
		},
		submitHandler:function(form){
            form.submit();
        }
	});
		
	$("#cd0").attr("name", "all_select_checkbox");
	

});


dTree.prototype.cc = function(nId, pId) {
	if(pId == -1) {
		var obj = $("#cd" + nId).prop("checked");
		$("[name='ckd']").attr("checked", obj);
	}
	else {
		var xpath = pId + "," + nId;
		if(pId == 0) {
			xpath = nId;
		}
		var obj = $("#cd" + nId);
		
		if (obj.is(":checked")) {
			$(".dTreeNodeCheckBox").each(function(){
				var path = $(this).attr("path");
				if(path.indexOf(xpath) > -1) {
					$(this).attr("checked", "checked");
				}
			});
		}
		else {
			$(".dTreeNodeCheckBox").each(function(){
				var path = $(this).attr("path");
				if(path.indexOf(xpath) > -1) {
					$(this).removeAttr("checked");
				}
			});
		}
	}
};

dTree.prototype.s = function(nId) {
	var obj = $("#cd" + nId);
	if (obj.is(":checked")) {
		obj.removeAttr("checked");
	}
	else {
		obj.attr("checked", "checked");
	}
};
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
                <th class="padT20">角色名称：</th>
                <td class="padT20"><input class="c_input_text text" type="text" name="roleName" value="" maxlength="20"></td>
            </tr>            
            <tr>
                <th class="padB56">角色描述：</th>
                <td class="padB56">
                	<textarea class="c_textarea auto_hint" name="description" cols="" rows="" realValue="输入文本..."></textarea>
                	<span class="in_num_text">0/200</span>
                </td>
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
                <th>菜单选项：</th>
                <td>
               		<script type="text/javascript">
		
						d = new dTree('d');
						d.setConfig(false, 1);
						d.add(0,-1,'全选',"javascript:void(0);", "全选", "0");
						<#list MENU_LIST as menu>
				     		d.add(${(menu.id)?c},${(menu.parentId)?c},"${menu.urlName}","javascript:void(0);", "${menu.urlName}", "${(menu.path)!}");
						</#list>
							
						document.write(d);
						d.openAll();
					</script>
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