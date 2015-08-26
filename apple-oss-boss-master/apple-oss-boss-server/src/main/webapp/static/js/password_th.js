// 修改密码
function pop_password_th(){
	art.dialog.open('/password/modify', {
		id: 'modifyPassword',
		title: '修改密码',
		close: function () {
			
		}
	}, false);
}