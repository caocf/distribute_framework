<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>修改用户</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
		
	// 表单验证
	function updatePwd() {
		
			var oldPassword = $("#oldPassword");
			var newPassword = $("#newPassword");
			var confirmPassword = $("#confirmPassword");
			var errMsg = $("#errMsg");
			
			 if(oldPassword.val().length == 0){
  				errMsg.html("请输入旧密码");
  				oldPassword.focus();
  				return false;
			 }
			 
			 if(newPassword.val().length == 0){
  				errMsg.html("请输入新密码");
  				newPassword.focus();
  				return false;
			 }
			 
			 if(newPassword.val().length < 6){
  				errMsg.html("密码的长度必须大于6");
  				newPassword.focus();
  				return false;
			 }
			 
			 if(confirmPassword.val().length == 0){
  				errMsg.html("请输入确认密码");
  				confirmPassword.focus();
  				return false;
			 }
			 
			 if(oldPassword.val().length == newPassword.val().length){
  				errMsg.html("新密码和旧密码一样，请重新输入");
  				newPassword.focus();
  				return false;
			 }
			 
			 if(confirmPassword.val().length !== newPassword.val().length){
  				errMsg.html("新密码和确认密码不一致，请重新输入");
  				confirmPassword.focus();
  				return false;
			 }
			
			$.ajax({
				type: "post",
				url: "/password/update",
				data: "oldPassword=" + oldPassword.val() + "&newPassword=" + newPassword.val(),
				dataType: "json",
				beforeSend: function(XMLHttpRequest){
					//ShowLoading();
				},
				success: function(data, textStatus){
					if(data.type == 'success') {
						pop_succeed("操作成功", "修改密码成功，请重新登录。", function() {
							location.href = "/logout";
						}, false);
					}
					else {
						errMsg.html(data.content);
					}
				},
				complete: function(XMLHttpRequest, textStatus){
					//HideLoading();
				},
				error: function(){
					//请求出错处理
				}
			});
	}
	
</script>

</head>

<body>

<form id="inputForm" method="post">
	<ul class="pop_list" style="width:400px;">
		<li class="clearfix"><span class="tit">旧密码：</span>
			<input id="oldPassword" name="oldPassword" class="c_input_text" type="password" style="width:200px;">
		</li>
		<li class="clearfix"><span class="tit">新密码：</span>
			<input id="newPassword" name="newPassword" class="c_input_text" type="password" style="width:200px;">
		</li>
		<li class="clearfix"><span class="tit">确认密码：</span>
			<input id="confirmPassword" class="c_input_text" type="password" style="width:200px;">
		</li>
		<li class="clearfix"><span class="tit"></span>
			<span id="errMsg" style="width:200px;color:red;">请输入旧密码</span>
		</li>
		<li class="clearfix">
			<input id="pwdModifyButton" type="button" class="btnA" value="确定" onclick="updatePwd();" />
		</li>
	</ul>
</form>

</body>
</html>