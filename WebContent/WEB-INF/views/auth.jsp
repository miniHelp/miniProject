<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>認證跳跳</title>
<style type="text/css">
body {
	font-family: 'Roboto Mono', monospace;
}

h5 {
	margin: 20px;
	font-size: 20px;
}

.formDiv {
	margin-left: 20px;
}

table.tftable {
	font-size: 12px;
	color: #333333;
	width: 700px;
	border-width: 1px;
	border-color: #444444;
	border-collapse: collapse;
	margin-bottom: 20px;
}

table.tftable th {
	font-size: 12px;
	background-color: #00DDDD;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #729ea5;
	text-align: left;
}

table.tftable tr {
	background-color: #77FFEE;
}

table.tftable td {
	font-size: 12px;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #444444;
}

.mask {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #000;
	opacity: 0.5;
	filter: alpha(opacity = 50);
	display: none;
	z-index: 99;
}

.mess {
	position: absolute;
	display: none;
	width: 250px;
	height: 140px;
	border: 1px solid #ccc;
	background: #ececec;
	text-align: center;
	z-index: 101;
}

#functionDiv {
	position:fixed; 
	left:32px;
}

</style>
</head>
<body>
	<div id='authDiv' class='formDiv' style=' position:fixed; top:38px' >
		<h5>MyPay Auth Order 獲得永久授權token</h5>
		<form id='SeaechAuthForm'
			action='<c:url value ="https://www.googleapis.com/oauth2/v3/token" />'
			method='post' name='JumpAuthForm'>

			<table id="tfhover" class="tftable" border="1">
			 <c:set var = "code" value = "${fn:split(param.code, '#')[0]}" />
			 
				<tr>
					<th style="width: 150px;">参数名称</th>
					<th>参数值</th>
				</tr>
				<tr>
					<td>code：</td>
					<td><input type='text' name='code' value= '${code}' id="code"/></td>
				</tr>
				<tr>
					<td>你的client_id：</td>
					<td><input type='text' name='client_id' value='${param.client_id}' id="client_id"/></td>
				</tr>
				<tr>
					<td>你的client_secret：</td>
					<td><input type='text' name='client_secret' value='${param.client_secret}' id="client_secret"/></td>
				</tr>
				<tr>
					<td>你的redirect_uri：</td>
					<td><input type='text' name='redirect_uri' value='${param.redirect_uri}' id="redirect_uri"/></td>
				</tr>
				<tr>
					<td>接口名稱：</td>
					<td><input type='text' name='grant_type' value='authorization_code' id="grant_type"/></td>
				</tr>

			</table>

			<input type="submit" value='送出' id="authSub"/> <input type="button" class="reset"
				value='清空' />
		</form>

	</div>
	

	
</body>
</html>
<script>
// jQuery.ajax({
	
// 	var code = ('${code}').split('#').[0];
// 	alert(code);
	
//     type: "POST",
//     url: "https://www.googleapis.com/oauth2/v4/token",
//     contentType: "application/x-www-form-urlencoded;",
//     data: code+'&'+
//     'client_id=你的client_id.apps.googleusercontent.com&'+
//     'client_secret=你的client_secret&'+
//     'redirect_uri=你的redirect_uri&'+
//     'grant_type=authorization_code',
//     success: function(data) {
//         console.log(data);
//     }
// });
</script>