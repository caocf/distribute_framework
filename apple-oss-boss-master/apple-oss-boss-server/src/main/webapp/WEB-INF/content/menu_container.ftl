<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>OSS平台</title>
</head>

<frameset rows="74,*,42" cols="*" frameborder="no" border="0" framespacing="0">
	<frame src="menu_header?id=${MENU_ID}" name="headerFrame" noresize="noresize" scrolling="no"/>
	<frameset id="framesetContains" rows="*" cols="210, *" frameborder="no" border="0" framespacing="0">
		<frame src="menu_left" name="leftNavFrame" noresize="noresize" scrolling="no"/>
        <frameset id="rightFrame" rows="90, *" cols="*" frameborder="no" border="0" framespacing="0">
            <frame src="menu_right" name="rightHeaderFrame" noresize="noresize" scrolling="no"/>
            <frame src="menu_content" name="rightMainFrame"/>
        </frameset>
	</frameset>
    <frame src="menu_footer" name="footerFrame" noresize="noresize" scrolling="no"/>
</frameset>
<noframes>
    <body>您的浏览器不支持frame框架, 请更换浏览器试试!</body>
</noframes>
</html>