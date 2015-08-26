<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>角色管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">

$().ready(function() {
	$("#listTable .btn_delete").bind("click", function(){
		var id = $(this).attr("roleId");
		
		pop_warning("操作提示", "是否删除角色。", true, function() {
					 $.ajax({
						type : "post",
						url : "delete?id=" + id,
						dataType: "json",
						cache : false,
						success: function(data){
							console.log("return data of munu delete:%s",data);
							if(data.type == 'success') {
								pop_succeed("操作成功", "删除角色成功。", function() {
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
	});
	
	
	});
	
	
	function list_user(id){
		art.dialog.open('/role/userlist?id=' + id, {
			id: 'userlist',
			title: '用户列表',
			close: function () {
				
			}
		}, false);
	}

</script>
</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="listForm" action="list" method="post">
	
    <!-- start of con_search -->
	<div class="con_search">
    	<span class="con_box_BL"></span>
        
        <!-- start of con_search_top -->
        <div class="con_search_top clearfix">
        	<div class="con_search_top_L left">
                <p>
                    <span class="group"><label>角色名称：</label>
                    	<input type="text" name="keyword" class="c_input_text" realValue="输入角色名称" value="${(search.keyword)!''}" />
                		<input type="hidden" name="searchBy" value="roleName" />
                    </span>
                    <span class="group"><a id="searchButton" href="javascript:;" class="btn_search">搜索</a></span>
                </p>
            </div>
            <!--
            <div class="con_search_btn right">
                <a class="btnA" href="add">添加角色</a>
            </div>
            -->
        </div>
        <!-- end of con_search_top -->
        
        <span class="con_box_BR"></span>
    </div>
    <!-- end of con_search -->
    
    <!-- start of table_list -->
    <table id="listTable" class="table_list list">
        <tr>
        	<th width="6%" >序号</th>
			<th width="15%" orderField="info.name">角色名称</th>
			<th width="20%" orderField="info.code">所属部门</th>
			<th width="6%" orderField="info.code">状态</th>
			<th width="6%" orderField="info.code">创建时间</th>
			<th width="15%">操作</th>
        </tr>
        
        <#list page.list as info>
        <tr class="even">
        	<td>
        		<!--<input type="checkbox" name="ids" value="${info.id}" />-->${info.id}
        	</td>
			<td>${(info.roleName)!}</td>
			<td>
				<#if info.departPath??>
				<@depart path=info.departPath>
				${pathName}
				</@depart>
				<#else>
				-
				</#if>
			</td>
			<td><#if info.state == 0>停止<#else>启动</#if></td>
			<td><#if info.createTime??>${info.createTime?string('yyyy-MM-dd')}<#else>-</#if></td>
			<td>
				<a class="btn_icon btn_edit" href="edit?id=${info.id}" title="编辑"></a>
                <a class="btn_icon btn_detail" href="view?id=${info.id}" title="详情"></a>
                <a class="btn_icon btn_delete" href="javascript:void(0);" title="删除" roleId="${info.id}"></a>
        		<a class="btn_icon btn_examine" href="javascript:void(0);" onclick="list_user(${info.id});" title="用户"></a>
			</td>
        </tr>
        </#list>
        
    </table>
    <!-- end of table_list -->
    
    
    <#if (page.list?size > 0)>
    
    	<!-- start of table_bottom -->
	    <div class="table_bottom clearfix">
	    	<div class="table_bottom_checkbox left">
	    		<!--<input id="selectAll" name="" type="checkbox" value=""><a class="btn" href="#">删除选中</a>-->
	    	</div>
	        
	   		<!-- start of 分页 -->
	   		<@paging pageNumber = page.pageNo totalPages = page.totalPage>
				<#include "../commons/pager.ftl">
			</@paging>
	        <!-- end of 分页 -->
	    </div>
	    <!-- end of table_bottom -->
			
	<#else>
		<div class="noRecord">没有找到任何记录!</div>
	</#if>
			
			
    </form>
</div>
<!-- end of con_right_main -->
</body>
</html>