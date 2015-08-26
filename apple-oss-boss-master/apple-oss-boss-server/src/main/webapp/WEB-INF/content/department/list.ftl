<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>部门列表</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<link rel="StyleSheet" href="/css/dtree/dtree.css" type="text/css" />
<script type="text/javascript" src="/js/jquery.contextmenu.r2.js"></script>

<script type="text/javascript" src="/js/dtree/dtree.js"></script>

<Style type="text/css">
.a1{color:#ff0000}
</Style>

<script type="text/javascript">
$(function() {
	$('#context a').contextMenu('department', {
 		bindings: {
			'add': function(t, target) {
         		location.href = 'add?pid=' + t.id;
   			},
        	'edit': function(t, target) {
            	location.href = 'edit?id=' + t.id;
  			},
     		'delete': function(t, target) {
  				pop_warning("操作提示", "是否删除部门。", true, function() {
					 $.ajax({
						type : "post",
						url : "delete?id=" + t.id,
						dataType: "json",
						cache : false,
						success: function(data){
							console.log("return data of munu delete:%s",data);
							if(data.type == 'success') {
								pop_succeed("操作成功", "删除部门成功。", function() {
									location.href = "list";
								}, false);
							}
							else {
								pop_error("操作失败", data.content,function() {
								} ,false);
							}
						}					
					});
					
				});
     		}
		},
		onShowMenu: function(e, department) {
   			if (parseInt($("td:eq(0)", e.currentTarget).text()) > 10) {
          		$("#save", department).remove();
     		}
      		$(e.currentTarget).siblings().removeClass("SelectedRow").end().addClass("SelectedRow");
          	return department;
        }
	});
	
	
})

</script>

</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">
    <!-- start of con_search -->
	<div class="con_search">
    	<span class="con_box_BL"></span>
    	
    	<div class="contextMenu" id="department">
            <ul>
                <li id="add"><img src="/images/Home_Age_UnSelect.jpg" width="16" height="16" />新增</li>
                <li id="edit"><img src="/images/Home_Age_UnSelect.jpg" width="16" height="16" />修改</li>
                <li id="delete"><img src="/images/Home_Age_UnSelect.jpg" width="16" height="16" />删除</li>
            </ul>
        </div>
    	
        <div id="context">
       		<!-- start of add_list_table -->
  			<p><a href="javascript: d.openAll();">全部展开</a> | <a href="javascript: d.closeAll();">全部收缩</a></p>
			<script type="text/javascript">
		
				d = new dTree('d');
				d.setConfig(true, 0);
				d.add(0,-1,'部门管理');
				
				<#list DEPARTMENT_LIST as department>
		     		d.add(${(department.id)?c},${(department.parentId)?c},"<a id='${(department.id)?c}'>${department.name}<a>");
				</#list>
					
				document.write(d);
			</script>
		</div>
        <!-- end of add_list_table -->

        <span class="con_box_BR"></span>
    </div>
    <!-- end of con_search -->
</div>
<!-- end of con_right_main -->
</body>
</html>