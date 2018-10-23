<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<!--
/*
 * MyPay 在线支付 demo
 *
 * Copyright 2018, Sebastian Tschan
 *
 * auth：Caster Hsu
 * Date：2018/08/01
 */
-->
<html lang="en">

<head>
<title>MyPay 支付 Demo</title>
<meta charset="gbk">
<link href="https://fonts.googleapis.com/css?family=Roboto+Mono"
	rel="stylesheet">
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
	background-color: #FFB7DD;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #729ea5;
	text-align: left;
}

table.tftable tr {
	background-color: #FFCCCC;
}

table.tftable td {
	font-size: 12px;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #444444;
}
</style>
<script src='js/jquery-2.1.1.min.js' charset="gbk">
<script type="text/javascript" src="<c:url value ="js/indexFunction.js" />" ></script>

</head>

<body>
		<div id='functionDiv'>
		<input type="button" id='changeSendOrder' view='sendDiv' value='下单范例' style='display: none'> 
		<input type="button" id='changeQueryOrder' view='queryDiv' value='查单范例'> 
		<input type="button" id='changeSearchOrder' view='searchDiv' value='查詢接口'>
	
	</div>
	<div id='resultDiv' class='formDiv'>
		<h5>查詢接口:${param.url}</h5>


		<table id="tfhover" class="tftable" border="1">
			<tr>
				
				<th>接口ID</th>
				<th>接口</th>
				<th>支付网关</th>
				<th>操作</th>
			</tr>
			<c:if test="${fn:length(list) eq 0 }">
				<tr >
					<td colspan="4">查無此資料</td>
				</tr>
			</c:if>
			<c:forEach items="${list }" var="data">
				<tr>
					<td>${data.id}</td>
					<td>${data.name}</td>
					<td>${data.url}</td>
					<td><input type="button" id='searchModifyOrder' view='' value='修改' /></td>
					
				</tr>
			</c:forEach>
		</table>
		<input type="button" name="back" value="回首頁" onclick="javascript:history.back(-1);"/>
		
	<div id='queryDiv' class='formDiv' style='display: none'>
		<h5>MyPay Query Order Demo 查单范例</h5>
		<form id='queryOrderForm'
			action='http://testapi.MyPayla.com/order/apiOrder/queryOrder.zv'
			method='post'>

			<table id="tfhover" class="tftable" border="1">
				<tr>
					<th style="width: 150px;">参数名称</th>
					<th>参数值</th>
				</tr>
				<tr>
					<td>支付网关：</td>
					<td>http://testapi.MyPayla.com/order/apiOrder/queryOrder.zv</td>
				</tr>
				<tr>
					<td>商户号：</td>
					<td><input type='text' name='merId' value='IIH001' required /></td>
				</tr>
				<tr>
					<td>机构号：</td>
					<td><input type='text' name='orgId' value='IIH' required /></td>
				</tr>
				<tr>
					<td>商户订单号：</td>
					<td><input type='text' id='merchantNo' name='merchantNo'
						value='' required /></td>
				</tr>
				<tr>
					<td class='signStr' colspan="2">签名排序：</td>
				</tr>
				<tr>
					<td>签名：</td>
					<td class='signTd'></td>
				</tr>
				<input type='hidden' id='version' name='version' value='V1.0' />
				<input type='hidden' class='sign' name='sign' value='' />
			</table>

			<input type="button" class="submitForm" value='Submit' /> <input
				type="button" id="reset" value='Reset' />
		</form>

	</div>
	<div id='searchDiv' class='formDiv' style='display: none'>
		<h5>MyPay Query Order 查詢是哪個接口</h5>
		<form id='SeaechOrderForm' action='serverlet/ListServerlet'
			method='post'>

			<table id="tfhover" class="tftable" border="1">
				<tr>
					<th style="width: 150px;">参数名称</th>
					<th>参数值</th>
				</tr>
				<tr>
					<td>接口編號：</td>
					<td><input type='text' name='id' value='' required /></td>
				</tr>
				<tr>
					<td>支付網址：</td>
					<td><input type='text' name='url' value='' required /></td>
				</tr>
				<tr>
					<td>接口名稱：</td>
					<td><input type='text' name='name' value='' required /></td>
				</tr>

			</table>

			<input type="button" class="submitForm" value='Submit' /> <input
				type="button" id="reset" value='Reset' /> <input type="hidden"
				name="method" value="query" />
		</form>

	</div>



	
</body>

</html>