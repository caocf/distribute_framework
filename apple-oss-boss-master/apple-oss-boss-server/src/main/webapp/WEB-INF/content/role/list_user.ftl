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
		var id = $(this).attr("deleteId");
		var roleId = $(this).attr("roleId");
		
		pop_warning("操作提示", "是否删除该角色的用户。", true, function() {
					 $.ajax({
						type : "post",
						url : "userdelete?id=" + id,
						dataType: "json",
						cache : false,
						success: function(data){
							console.log("return data of munu delete:%s",data);
							if(data.type == 'success') {
								pop_succeed("操作成功", "删除用户成功。", function() {
									location.href = "userlist?id=" + roleId;
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
</script>
</head>

<body>

    <div class="pop_main" id="popOwnersCollectionTab" style="width:600px;border: 0px solid;">
        <div class="pop_ownersCollection_con">
            <div class="pop_detailTable_show">
                <table class="table_list table_list2" id="listTable">
                    <tr>
			        	<th width="6%" >序号</th>
						<th width="10%" orderField="info.name">账号</th>
						<th width="6%" orderField="info.code">用户名称</th>
						<th width="6%" orderField="info.code">状态</th>
						<th width="8%">操作</th>
			        </tr>
			        <#if (list?size > 0)>
        			<#list list as info>
                    <tr class="even">
                        <td>
			        		<!--<input type="checkbox" name="ids" value="${info.id}" />-->
			        		${info.user.id}
			        	</td>
						<td>${info.user.username}</td>
						<td>${info.user.realname}</td>
						<td><#if info.user.state == 0>停止<#else>启动</#if></td>
						<td>
			                <a class="btn_icon btn_delete" href="javascript:void(0);" title="删除" roleId="${info.rtsRole.id}" deleteId="${info.id}"></a>
						</td>
                    </tr>
                    </#list>
				  	<#else>
				  		<tr class="even">
							<td colspan="5">没有找到任何记录!</td>
						</tr>
					</#if>
                </table>
            </div>
        </div>
    </div>

</body>
</html>