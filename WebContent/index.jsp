<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
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
<html>

<head>
<title>MyPay 支付 Demo</title>
<meta charset="utf-8">
<link href="https://fonts.googleapis.com/css?family=Roboto+Mono"
	rel="stylesheet">

<style type="text/css">
body {
	font-family: 'Microsoft YaHei', monospace;
}
 .colorpicker {
	   float: left;
	   margin: 1em;
	 }

input {
	font-family: 'Microsoft YaHei';
	margin: 6px 3px;
	background-color: #e5cdb0;
	border-color: #729ea5;
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
	width: 400px;
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
	position: fixed;
	left: 32px;
}

#toggleTr {
	background-color: #FFEE99;
}

/*
查詢商戶:標頭

*/

/*主標題*/
table.merchantReDiv th {
	background-color: #906e40;
}

table.merchantReDiv tr {
	background-color: #c7ae8f;
}

table.merchantReDiv td {
	background-color: #c7ae8f;
}

table.merchantReDiv {
	width: '850px';
	margin-top: 8px;
}

/*
查詢商戶:詳情

*/

/*主標題*/
.merchantDetalDiv .one {
	background-color: #c3bdb1;
}

/*內容*/
.merchantDetalDiv .two {
	background-color: #d6c9b9;
}

.merchantDetalDiv>td {
	background-color: #d6c9b9;
}
</style>
</head>

<body>
	<div id='functionDiv'>
		<input type="button" id='changeSearchOrder' view='searchDiv'
			value='查詢接口'> <input type="button" id='changeAuthOrder'
			view='authDiv' value='獲取授權'>
			<input type="button"  view='justinMagicDiv' value='賈斯小魔法' onclick='justinMagic(this);'>
	</div>




	<!-- 
	獲得權限的 還沒做好
	 -->

	<div id='authDiv' class='formDiv'
		style='display: none; position: fixed; top: 38px'>
		<h5>MyPay Auth Order 查詢是哪個授權</h5>
		<form id='SeaechAuthForm'
			action='<c:url value ="${request.contextPath}/serverlet/OAuth2" />'
			method='post' name='SeaechAuthForm'>

			<table id="tfhover" class="tftable" border="1">
				<tr>
					<th style="width: 150px;">參數名稱</th>
					<th>參數值</th>
				</tr>
				<tr>
					<td>接口編號：</td>
					<td><input type='text' name='id' value='${param.id}' id="id" /></td>
				</tr>
				<tr>
					<td>支付網址：</td>
					<td><input type='text' name='url' value='${param.url}'
						id="url" /></td>
				</tr>
				<tr>
					<td>接口名稱：</td>
					<td><input type='text' name='name' value='${param.name}'
						id="name" /></td>
				</tr>

			</table>

			<input type="submit" value='送出' id="authSub" /> <input type="button"
				class="reset" value='清空' /> <input type="hidden" name="method"
				value="auth" />
		</form>

	</div>

	<!--  
		查詢接口的from表單 -->
	

	<div id='searchDiv' class='formDiv'
		style='display: none; position: fixed; top: 38px'>
		<h5>MyPay Query Order 查詢是哪個接口</h5>
		<form id='SeaechOrderForm'
			action='<c:url value ="${request.contextPath}/serverlet/ListServerlet" />'
			method='post' name='SeaechOrderForm'>

			<table class="tftable" border="1">
				<tr>
					<th style="width: 150px;">參數名稱</th>
					<th>參數值</th>
				</tr>
				<tr>
					<td>接口編號：</td>
					<td><input type='text' name='id' value='${param.id}' id="id" /></td>
				</tr>
				<tr>
					<td>支付網址：</td>
					<td><input type='text' name='url' value='${param.url}'
						id="url" /></td>
				</tr>
				<tr>
					<td>接口名稱：</td>
					<td><input type='text' name='name' value='${name}' id="name" /></td>
				</tr>

			</table>

			<input type="submit" value='送出' id="searchSub" /> <input
				type="button" class="reset" value='清空' /> <input type="hidden"
				name="method" value="query" />
		</form>

	</div>
	
	<!-- 
	神奇小魔法 日誌產生器
	 -->

	<div id='justinMagicDiv' class='formDiv'
		style='display: none; position: fixed; top: 38px'>
		<h5>MyPay Log Magic </h5>
		
			<table id="tfhover" class="tftable" border="1">
				<tr>
					<th style="width: 150px;">Magic</th>
					<th>Magic</th>
				</tr>
				<tr>
					<td>账号名称：</td>
					<td>
						<input type='text' name='magicName' value='' id="magicName" />
					</td>
				</tr>
				<tr>
					<td>支付方式：</td>
					<td>
						<input type='radio' name='type' value='微信支付' id="wx" checked/>微信支付
						<input type='radio' name='type' value='支付宝支付' id="zf" />支付宝支付
						</td>
				</tr>
				<tr>
					<td>accID：</td>
					<td><input type='text' name='accId' value='2'
						id="accId" /></td>
				</tr>
				<tr>
					<td>支付金額：</td>
					<td><input type='text' name='name' value='300.00'
						id="money" /></td>
				</tr>
				<tr>
					<td>时间：</td>
					<td><input type='text' name='time' value='[10-06 15:04:42]'
						id="time" /></td>
				</tr>

			</table>

			<input type="button" value='Magic' id="magicBtn" onclick='magic();' /> 
			<input type="button" class="reset" value='清空' /> 
	</div>
	
	
	<!-- 
	魔法小日志的div
	 -->
	<div id='justinMagicShowDiv' class='formDiv'
		style='display: none; position: relative; left: 800px'>
		<table id="tfhover" class="tftable" border="1">
			<tr>
				<th>信息</th>
				<td >
					<textarea id='magicShow' style='height:250px;width:800px'>
					</textarea>
				</td>
			</tr>
		</table>
	</div>
	
	


	<!-- 
		查詢接口的List
		 -->

	<div id='resultDiv' class='formDiv'
		style='display: none; position: relative; left: 700px'>
		<h5>查詢接口:${param.url}${name}</h5>


		<table class="merchantReDiv" border="1" width='850px'>
			<tr>

				<th width='10%'>接口ID</th>
				<th width='20%'>接口</th>
				<th width='60%'>支付網址</th>
				<th width='10%'>操作</th>
				<th width='10%'>商戶部分</th>
			</tr>
			<c:if test="${fn:length(list) eq 0 }">
				<tr>
					<td colspan="4">查無此資料</td>
				</tr>
			</c:if>
			<c:forEach items="${list }" var="data">
				<tr>
					<td>${data.id}</td>
					<td>${data.name}</td>
					<td>${data.url}</td>
					<td><input type="button" class="btn" value="修改"
						id="modifyPop${data.id}" dataId="${data.id}"
						dataName="${data.name}" dataUrl="${data.url}" /></td>
					<td><input type="button" value="超懶新增mypay平台" id="insertMypay"
						dataId="${data.id}" dataName="${data.name}"
						onclick='insertMypay(this);' /> <input type="button"
						value="超懶一鍵新增商戶" id="insertMypayMerchent" dataId="${data.id}"
						dataName="${data.name}"
						dataUrl="<c:url value ="${request.contextPath}/serverlet/ListServerlet" />"
						onclick='PlantDetal(this);' /> <input type="button"
						id="merchantList" value="mypay商戶列表" dataId="${data.id}"
						dataName="${data.name}" onclick='showMerchList(this);' /></td>
				</tr>
			</c:forEach>
		</table>
		<input type="button" name="back" value="上一頁"
			onclick="javascript:history.back(-1);" /> <input type="button"
			id="home" value="回首頁" />
	</div>

	<!--  
		查詢接口資料
	-->
	<form name='merchantListForm'
		action='<c:url value ="${request.contextPath}/serverlet/ListServerlet" />'
		method='post'>
		<input type='hidden' id='merchantListId' name='id' value=''> <input
			type="hidden" id='insertMypayForm' name="method" value="merchantList" />

	</form>


	<!-- 
			查詢商戶資料
		 -->

	<div id='merchantReDiv' class='formDiv'
		style='display: none; position: relative; left: 700px'>
		<h5>查詢商戶:</h5>

		<c:if test="${fn:length(merList) eq 0 }">
			<table class="merchantReDiv" border="1" width='850px'>
				<tr>
					<td colspan="5">查無此資料 新增一筆吧?</td>
					<td><input type="button" value="新增mypay商戶"
						id="insertMerchantMypay"
						dataUrl="<c:url value ="${request.contextPath}/serverlet/ListServerlet" />"
						dataId="${data.id}" dataName="${data.name}"
						onclick='insertMypayMerchent(this);' /></td>
				</tr>
			</table>
		</c:if>

		<c:forEach items="${merList }" var="data">
			<table class="merchantReDiv" border="1" width='850px'>
				<tr>

					<th width='10%'>接口</th>
					<th width='12%'>商戶編號</th>
					<th width='40%'>商戶名稱</th>
					<th width='28%'>商戶號</th>
					<th width='10%' colspan="2">操作</th>
				</tr>

				<tr class='three'>
					<td>${data.PAYMENT_PLATFORM_ID}</td>
					<td>${data.ID}</td>
					<td>${data.NAME}</td>
					<td>${data.MERCHANT_NO}</td>
					<td colspan="2"><input type="button" value="詳情"
						dataId="${data.ID}" id="merListPop${data.ID}"
						onclick='merchantDetal(this);' />
						
						<input type="button" value="幹掉他"
						dataId="${data.ID}" dataPlant="${data.PAYMENT_PLATFORM_ID}" id="merDel${data.ID}"
						onclick='merchantDetele(this);' />
						
						</td>
				</tr>
			</table>
			<table class="merchantReDiv" border="1" id="togglePop${data.ID}"
				style='display: none' width='850px'>
				<tr>
					<!-- 
				將多餘的信息 隱藏起來
					 -->

					<th colspan="6" id="toggleTr">詳情</th>
				</tr>
				<tr class='merchantDetalDiv'>
					<th class='one'>平台號 :</th>
					<th class='two'>${data.PLATFORM_NO}</th>
					<th class='one'>商戶密碼 :</th>
					<th class='two'>${data.MERCHANT_PWD}</th>
					<th class='one'>簽章種類 :</th>
					<th class='two' id="signType">${data.SIGNATURE_TYPE}</th>
				</tr>
				<tr class='merchantDetalDiv'>
					<th class='one'>MD5密鑰 :</th>
					<td colspan="5">${data.SIGNATURE_KEY}</td>
				</tr>
				<tr class='merchantDetalDiv'>
					<th class='one'>RSA私鑰 :</th>
					<td colspan="5">${data.RSA_MERCHANT_PRIVATE_KEY}</td>
				</tr>
				<tr class='merchantDetalDiv'>
					<th class='one'>RSA公鑰 :</th>
					<td colspan="5">${data.RSA_SERVER_PUBLIC_KEY}</td>
				</tr>



			</table>
		</c:forEach>
		<input type="button" name="back" value="上一頁"
			onclick="javascript:history.back(-1);" /> <input type="button"
			id="home" value="回首頁" />
	</div>

	<!-- 
	新增一筆mypay平台的from表單
	 -->


	<form name='insertMypayPlantForm'
		action='<c:url value ="${request.contextPath}/serverlet/ListServerlet" />'
		method='post'>
		<input type='hidden' id='insertMypayFormId' name='id' value=''>
		<input type='hidden' id='insertMypayFormName' name='name' value=''>
		<input type="hidden" id='insertMypayForm' name="method"
			value="insertMypay" />

	</form>


	<!-- 
	新增一筆mypay商戶的from表單
	 -->
	<div id='insertMypayMerchentTable' class='formDiv'
		style='display: none; position: relative; left: 700px'>
		<form name='insertMypayMerchentForm'
			action='<c:url value ="${request.contextPath}/serverlet/ListServerlet" />'
			method='post'>
			<c:forEach items="${list}" var="data">
				<table class="merchantReDiv" id="appendTable" name="merchantReName"
					border="1" width='850px'>
					<tr>
						<th style="width: 150px;">參數名稱</th>
						<th>參數值</th>
					</tr>
					<tr>
						<td>接口：</td>
						<td><input type='text' id='plantName' name='plantName'
							value='' readOnly></td>
					</tr>
					<tr>
						<td>商戶名稱：</td>
						<td><input type='text' id='insertMypayMerchentFormName'
							name='merchentName' value=''><span id="merchentTips"
							style='color: red'></span></td>
					</tr>
					<tr>
						<td>商戶號：</td>
						<td><input type='text' id='merchentNo' name='merchentNo'
							value=''></td>
					</tr>
				</table>





				<input type='hidden' id='insertMypayMerchentFormId' name='id'
					value=''>
				<input type="hidden" id='insertMypayMerchentForm' name="method"
					value="insertMerchant" />
			</c:forEach>
		</form>
	</div>
	<!-- 
	修改網關地址的彈窗
	 -->
	<div id='modifyPop'>
		<div class="mask"></div>
		<div class="mess">
			原網址:
			<p id="showUrl"></p>
			<p>
				欲修改網址:<input type="text" id="toModifyUrl">
			</p>
			<p>
				<input type="button" value="确定" class="btn1" id="modifyUrl" /> <input
					type="button" value="取消" class="btn2" />
			</p>
		</div>
	</div>



	<!-- 
	新增mypay平台後的回傳信息
	 -->
	<div id='insertReDiv' class='formDiv'
		style='display: none; position: relative; left: 800px'>
		<table id="tfhover" class="tftable" border="1">
			<tr>
				<th>回傳信息</th>
				<td>${meString}</td>
			</tr>
		</table>
	</div>




	<script type="text/javascript" src="<c:url value ="/js/md5.js" />"></script>
	<script type="text/javascript"
		src="<c:url value ="/js/jquery-2.1.1.min.js" />"></script>
<!--  <script type="text/javascript" charset="utf-8" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> -->
	<script type="text/javascript"
		src="<c:url value ="/js/jquery.validate.min.js" />"></script>
	<script type="text/javascript" src="<c:url value ="/js/pop.js" />"></script>
	<script type="text/javascript"
		src="<c:url value ="/js/indexFunction.js" />"></script>
		
<script type="text/javascript" charset="utf-8">

	</script>

	<c:if test="${method == 'query' }">
		<script>
			$('#searchDiv').show();
			$('#resultDiv').show();
			$('#sendDiv').hide();
		</script>
	</c:if>
	<c:if test="${method == 'insertMypay' }">
		<script>
			$('#searchDiv').show();
			$('#insertReDiv').show();
		</script>
	</c:if>
	<c:if test="${method == 'merchantList' }">
		<script>
			$('#merchantReDiv').toggle();
			$('#searchDiv').show();
			$('#merchantList').val('隱藏列表');
			$('#resultDiv').show();
		</script>
	</c:if>
	
	<c:if test="${not empty msg }">
		<script>
			alert("${msg}");
			console.log("${msg}");
		</script>
	</c:if>
	


</body>

</html>