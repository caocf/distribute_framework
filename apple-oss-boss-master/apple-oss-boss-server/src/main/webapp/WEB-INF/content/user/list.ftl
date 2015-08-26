<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">

$().ready(function() {

	$("#listTable .btn_delete").bind("click", function(){
		var id = $(this).attr("userId");
		pop_warning("操作提示", "是否删除用户？", true, function() {
					 $.ajax({
						type : "post",
						url : "delete?id=" + id,
						dataType: "json",
						cache : false,
						success: function(data){
							console.log("return data of munu delete:%s",data);
							if(data.type == 'success') {
								pop_succeed("操作成功", "删除用户成功。", function() {
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
	
	
	$("#listTable .btn_enable").bind("click", function(){
		var id = $(this).attr("userId");
		pop_warning("操作提示", "是否启用该用户？", true, function() {
					 $.ajax({
						type : "post",
						url : "enable?id=" + id,
						dataType: "json",
						cache : false,
						success: function(data){
							console.log("return data of munu delete:%s",data);
							if(data.type == 'success') {
								pop_succeed("操作成功", "启用用户成功。", function() {
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
	
	$("#listTable .btn_disabled").bind("click", function(){
		var id = $(this).attr("userId");
		pop_warning("操作提示", "是否禁用该用户？", true, function() {
					 $.ajax({
						type : "post",
						url : "disabled?id=" + id,
						dataType: "json",
						cache : false,
						success: function(data){
							console.log("return data of munu delete:%s",data);
							if(data.type == 'success') {
								pop_succeed("操作成功", "禁用用户成功。", function() {
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
	
	
	$("#listTable .btn_send").bind("click", function(){
		var id = $(this).attr("userId");
		pop_warning("操作提示", "是否发送邮件？", true, function() {
					 $.ajax({
						type : "post",
						url : "mail?id=" + id,
						dataType: "json",
						cache : false,
						success: function(data){
							if(data.type == 'success') {
								pop_succeed("操作成功", "发送邮件成功。", function() {}, false);
							}
							else {
								pop_error("操作失败", data.content,function() {} ,false);
							}
						}					
					});
		});
	});
	
});

function callback() {
 	$("#searchButton").click();
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
                    <span class="group"><label>用户名或姓名：</label>
                    	<input type="text" name="keyword" class="c_input_text" realValue="输入角色名称" value="${(search.keyword)!''}" />
                    </span>
                    <span class="group"><a id="searchButton" href="javascript:;" class="btn_search">搜索</a></span>
                </p>
            </div>
            <!--
            <div class="con_search_btn right">
                <a class="btnA" href="add">添加用户</a>
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
			<th width="6%" orderField="info.name">用户名</th>
			<th width="10%" orderField="info.code">真实名称</th>
			<th width="6%"  orderField="info.code">管理员</th>
			<th width="15%" orderField="info.code">拥有角色</th>
			<th width="15%" orderField="info.code">所属部门</th>
			<th width="10%" orderField="info.code">手机号码</th>
			<th width="6%" orderField="info.code">状态</th>
			<th width="10%" orderField="info.code">创建时间</th>
			<th width="20%">操作</th>
        </tr>
        
        <#list page.list as user>
        <tr class="even">
        	<td><input type="checkbox" name="ids" value="${user.id}" />${user.id}</td>
			<td>${(user.username)!}</td>
			<td>${(user.realname)!}</td>
			<td><#if user.isadmin == 0>否<#else>是</#if></td></td>
			<td>
				<#list user.rtsUserRoles as rtsUserRole>
					${rtsUserRole.rtsRole.roleName}<br />
				</#list>
			</td>
			<td>
				<#if user.departPath??>
				<@depart path=user.departPath>
				${pathName}
				</@depart>
				<#else>
				-
				</#if>
			</td>
			<td>${(user.mobile)!}</td>
			<td><#if user.state == 0>停止<#else>启动</#if></td>
			<td><#if user.createTime??>${user.createTime?string('yyyy-MM-dd')}<#else>-</#if></td>
			<td>
				<a class="btn_icon btn_edit" href="edit?id=${user.id}" title="编辑"></a>
				<#if user.state == 0>
					<a class="btn_icon btn_enable" href="javascript:void(0);" title="启用" userId="${user.id}"></a>
				<#else>
					<a class="btn_icon btn_disabled" href="javascript:void(0);" title="禁用" userId="${user.id}"></a>
				</#if>
                <a class="btn_icon btn_detail" href="view?id=${user.id}" title="详情"></a>
                <a class="btn_icon btn_delete" href="javascript:void(0);" title="删除" userId="${user.id}"></a>
                <a class="btn_icon btn_send" href="javascript:void(0);" title="发送邮件" userId="${user.id}"></a>
			</td>
        </tr>
        </#list>
        
    </table>
    <!-- end of table_list -->
    
    
    <#if (page.list?size > 0)>
    
    	<!-- start of table_bottom -->
	    <div class="table_bottom clearfix">
	    	<div class="table_bottom_checkbox left">
	    		<input id="selectAll" name="" type="checkbox" value=""><a class="btn" href="#" id="deleteAll">删除选中</a>
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