<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>部门列表</title>

<#include "../commons/page_js.ftl" />

<link rel="StyleSheet" href="/css/dtree/dtree.css" type="text/css" />
<script type="text/javascript" src="/js/dtree/dtree.js"></script>

<Style type="text/css">
.a1{color:#ff0000}
</Style>

<script type="text/javascript">
dTree.prototype.cc = function(nId, pId) {
	//if(pId == -1) {
	//	var obj = $("#cd" + nId).prop("checked");
	//	$("[name='ckd']").attr("checked", obj);
	//}
};

//dTree.prototype.s = function(nId) {
//	var obj = $("#cd" + nId);
//	obj.attr("checked", "checked");
//};

</script>

</head>

<body>

		<div id="context">
			<script type="text/javascript">
				d = new dTree('d');
				d.setConfig(true, 2);
				//d.add(0,-1,'部门选择');
				
				<#list DEPARTMENT_LIST as department>
		     		d.add(${(department.id)?c},<#if department.parentId == 0>-1<#else>${(department.parentId)?c}</#if>,"<a id='${(department.id)?c}'>${department.name}<a>");
				</#list>
					
				document.write(d);
				d.openAll();
			</script>
		</div>
		
</body>
</html>