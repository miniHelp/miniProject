$(function() {
	$('.submitForm').bind('click', subminForm);
	$("input:enabled").bind('change', sign);
	$('table select').bind('change', sign);
	$('.reset').bind('click', resetData);
	$('#home').bind('click', home);
	$('#functionDiv input').bind('click', changeDiv);
	$('#magicBtn input').bind('click', magic);
	$('#merchantList input').bind('click', showMerchList);
});

var sign = function() {

	var form = $(this).closest('form');

	var signStr = '';
	if ($(this).closest('form').attr('id') == 'sendOrderForm') {
		signStr = 'amount=' + form.find('input[name=amount]').val()
				+ '&clientIp=' + form.find('input[name=clientIp]').val()
				+ '&merId=' + form.find('input[name=merId]').val()
				+ '&merchantNo=' + form.find('input[name=merchantNo]').val()
				+ '&notifyUrl=' + form.find('input[name=notifyUrl]').val()
				+ '&payType=' + form.find('input[name=payType]').val()
				+ '&terminalClient='
				+ form.find('input[name=terminalClient]').val() + '&tradeDate='
				+ form.find('input[name=tradeDate]').val()
				+ '&key=094B4D71F54F9D1FC29B0AB441723716';
	} else {
		console.log('here');
		signStr = 'merId=' + form.find('input[name=merId]').val()
				+ '&merchantNo=' + form.find('input[name=merchantNo]').val()
				+ '&orgId=' + form.find('input[name=orgId]').val()
				+ '&key=094B4D71F54F9D1FC29B0AB441723716';
	}

	var sign = md5(signStr).toUpperCase();

	$('.sign').val(sign);
	$('.signTd').html(sign);
	$('.signStr').text('签名排序：' + signStr);
};

var subminForm = function() {

	var form = $(this).closest('form');

	if ($('.sign').val() == 0) {
		alert('请先 生成验签 再送单');
		return;
	}
	if (form.attr('id') == 'sendOrderForm') {
		var amount = form.find('input[name=amount]').val();
		var regex = /[\d]+.[\d]{2}$/
		if (!regex.test(amount)) {
			alert('金额错误! 请参阅文檔.');
			return;
		}
	}
	$(this).closest('form').submit();
}

var removeSign = function() {
	$('.signTd').text('');
	$('.signStr').text('签名排序：');
	$('.sign').val('');
}

var resetData = function() {
	$('table input[type=text]').each(function() {
		$(this).val('');
	});
}

var changeDiv = function() {

	console.log("aaaa");
	$('div').hide();
	var view2 = $(this).attr('view');
	$('#' + view2).show();
	$('#functionDiv').show();
	view2 = view2.replace('Div', '');
	$('#' + view2 + 'Order').show();
}

var home = function() {
	window.location.href = '/MypayOnline1/index.jsp';
}

// 新增mypay平台
var insertMypay = function(obj) {
	var name = $(obj).attr('dataName');
	var id = $(obj).attr('dataId');

	console.log('name==' + name);
	console.log('id==' + id);

	$('#insertMypayFormId').val(id);
	$('#insertMypayFormId').val(id);
	$('#insertMypayFormName').val(name);
	document.forms['insertMypayPlantForm'].submit();
}



// 撈取mypayCenter的接口資料 這樣才能知道你的商戶裡面需要哪些資料
var PlantDetal = function(obj) {
	var name = $(obj).attr('dataName');
	var id = $(obj).attr('dataId');
	var url = $(obj).attr('dataUrl');
	console.log('name==' + name);
	console.log('id==' + id);

	
	$('#merchantReDiv').hide();//避免已經有查詢過的商戶列表出來亂
	$('#plantName').val(name);
	$('#insertMypayMerchentFormName').val(name + '懶惰建立');
	$('#insertMypayMerchentTable').toggle()
	$('#insertMypayMerchentFormId').val(id);
	$('#PlantName').val(name);
	$("tr").remove(".appendTable");
	var value = $(obj).val();
	if ("超懶一鍵新增商戶" == value) {
//		alert('見證奇蹟的時刻 ✧◝(⁰▿⁰)◜✧');
		$(obj).val('連新增商戶都懶 ◢▆▅▄▃崩╰(〒皿〒)╯潰▃▄▅▇◣');
		$('#merchantList').val('mypay商戶列表');
	} else {
		$(obj).val('超懶一鍵新增商戶');
	}
	

	// 發送ajax
	$
			.ajax({
				type : "POST",
				// async為false -> 同步
				// async為true -> 非同步
				async : false,
				url : url,
				// ↑↑↑↑↑↑反正就是要指到你寫的aspx拉↑↑↑↑↑↑↑↑
				contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
				data : {
					"id" : id,
					"method" : "findPlant"
				},
				success : function(msg) {
					// 後端回傳的東西包成JSONObject回來,
					var Result = msg;
					var platform_no_tips = msg.platform_no_tips;
					var platform_no_name = msg.platform_no_name;
					var merchant_pwd_name = msg.merchant_pwd_name;
					var merchant_pwd_tips = msg.merchant_pwd_tips;
					var merchant_no_tips = msg.merchant_no_tips;
					var rsa_merchant_private_key_tips = msg.rsa_merchant_private_key_tips;
					var rsa_server_public_key_tips = msg.rsa_server_public_key_tips;
					var sign = msg.sign;
					var list = msg.list;
				
					
					console.log("sign=" + sign + "    list :" + list);

					if (!merchant_no_tips == "undefined")
						$('#merchentTips').text("   " + merchant_no_tips);

					// 平台號 跟其顯示名稱 提示
					if (!(platform_no_name == null || platform_no_name == ""
							&& typeof (platform_no_name)) == "undefined") {
						console.log("platform_no_name"+platform_no_name);
						console.log(typeof (platform_no_name));
						$('#appendTable')
								.append(
										"<tr  class='appendTable'><td>"
												+ platform_no_name
												+ ':</td>'
												+ "<td><input type='text' id='plantName' name='plantName'	value="
												+ platform_no_tips
												+ '></td></tr>');
					}

					// 密碼設定 跟其提示信息
					if (!(merchant_pwd_name == null || merchant_pwd_name == "" || merchant_pwd_name == "undefined")) {
						$('#appendTable')
								.append(
										"<tr  class='appendTable'><td>"
												+ merchant_pwd_name
												+ ':</td>'
												+ "<td><input type='text' id='pswName' name='pswName'	value="
												+ merchant_pwd_tips
												+ '></td></tr>');
					}

					//商户密码
					if (!(merchant_pwd_name != null || merchant_pwd_name != "" || merchant_pwd_name == "undefined")) {
						$('#appendTable')
								.append(
										"<tr  class='appendTable'><td>"
												+ platform_no_name
												+ ':</td>'
												+ "<td><input type='text' id='plantName' name='plantName'	value="
												+ platform_no_tips
												+ '></td></tr>');
					}

					// 验证簽名方式
					if (sign == "2") {
						alert("RAS             in");
						$('#appendTable')
								.append(
										"<tr  class='appendTable'><td> RSA 商戶私鑰  :</td>"
												+ "<td><input type='text' id='RSAPrivate' name='RSAPrivate'	value="
												+ rsa_merchant_private_key_tips
												+ '></td></tr>');
						// 商戶公鑰
						$('#appendTable')
								.append(
										"<tr  class='appendTable'><td> 商戶公鑰  :</td>"
												+ "<td><input type='text' id='RSAPublic' name='RSAPublic'	value="
												+ rsa_server_public_key_tips
												+ '></td></tr>');
					}

					//如果是MD5 就是1
					if (sign == "1") {
						console.log("MD5             in");
						$('#appendTable')
								.append(
										"<tr  class='appendTable'><td> MD5 密鑰  :</td>"
												+ "<td><textarea cols='50' rows='3' id='Md5Key' name='Md5Key' value=''></textarea></td></tr>");
					}

					// 把list裡面的支付方式滾出來
					if (!(merchant_no_tips == "undefined")) {
						$('#appendTable').append("<tr  class='appendTable'><td> 支付方式 :</td><td id='wayTd'><span>");

						$.each(list,function(i, v) {
											console.log("排序  :" + i, "   值  :" + list[i]);
											console.log(v);
											var name = way(v);
											$('#wayTd')
													.append(
															"<input type='checkbox' id='list"
																	+ v
																	+ "' name='payList' checked='checked'	value="
																	+ v
																	+ "/>"+ name);
										});
						$('#appendTable').append("</span></td></tr>");

					}
					
					$('#appendTable')
					.append(
							"<tr  class='appendTable'><td> 按下去按下去~:</td><td>"
									+ "<input type='button' id='insertMerId' name='insertMerId'	value='見證奇蹟的時刻 ✧◝(⁰▿⁰)◜✧!!!!' onclick='insertMypayMerchent();'>"+
									"<input type='radio' name='state' value='on' checked> 啟用 (((o(*ﾟ▽ﾟ*)o))) "+
									"<input type='radio' name='state' value='off' > 停用 (｡ŏ_ŏ)</td></tr>"+
									"<input type='hidden' id='sign' name='sign'	value='"+ sign +"'>");

					// do something
				},
				// statusCode範例
				statusCode : {
					403 : function(response) {
						LocationHerf();
					}
				}
			});

}

//一件超懶新增mypay商戶
var insertMypayMerchent = function(obj) {
	
	var  merchentNo = $('#merchentNo').val();
	console.log(merchentNo);
	if(!(merchentNo == null || merchentNo == "" )) {
		document.forms['insertMypayMerchentForm'].submit();
	} else {
		alert('好歹填一下商戶號吧');
	}
	
	
	var name = $("input[name='state']");
	if(name == "off"){
		alert('停用三小');
		return;
	}
}

// 商戶列表
var showMerchList = function(obj) {
	var id = $(obj).attr('dataId');
	$('#merchantListId').val(id);
	document.forms['merchantListForm'].submit();
}


var merchantDetele = function(obj){
	var id = $(obj).attr('dataId');
	var dataPlant = $(obj).attr('dataPlant');
	alert('我沒有做防呆  資料ㄅㄅ');
	
	var back = confirm('嚇你的 要不要刪掉啦?')
	if(back == false){
		alert('怎樣樣R');
		return;
	}
	// 發送ajax
	$
			.ajax({
				type : "POST",
				// async為false -> 同步
				// async為true -> 非同步
				async : false,
				url : url,
				// ↑↑↑↑↑↑反正就是要指到你寫的aspx拉↑↑↑↑↑↑↑↑
				contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
				data : {
					"id" : id,
					"method" : "merchantDetele"
				},
				success : function(msg) {
					// 後端回傳的東西包成JSONObject回來,
					var Result = msg;
					var mString = msg.mString;
				alert('mString = '+ mString);
				//重新load查詢畫面
				$('#merchantListId').val(dataPlant);
				document.forms['merchantListForm'].submit();
					
				},
				// statusCode範例
				statusCode : {
					403 : function(response) {
						LocationHerf();
					}
				}
			});
}

// 商戶列表詳情
var merchantDetal = function(obj) {
	var id = $(obj).attr('dataId');
	var name = $(obj).val();
	if ("詳情" == name) {
		$(obj).val('收起');
	} else {
		$(obj).val('詳情');
	}
	$('#togglePop' + id).toggle();

}


 function justinMagic(){
	$('#sendDiv').hide();
	$('#authDiv').hide();
	$('#justinMagicShowDiv').show();
}


 function magic(){
	var accId = $('#accId').val();
	var money = $('#money').val();
	var magicName = $('#magicName').val();
	var type = $("input[name='type']:checked").val();
	var time = $('#time').val();
	$('#justinMagicShowDiv').show();
	$('#magicShow').text(
			time +' : 服务端连接通讯超时 \r\n'+
			time +' : 服务端连接通讯超时 \r\n'+
			time +' : 撷取关键标题为 :  '+ type +'\r\n'+
			time +' : 判断关键标题为 :' + type +'\r\n' +
			time +' : 帐号 : '+magicName +'，accID : '+ accId +'，向服务端发送回调资料'+'\r\n'+
			time +' : 监控到微信accID : '+ accId +' 到帐'+ money +'元'+'\r\n'+
			time +' : 侦测到付款通知执行回调时，服务端连接通讯超时'+'\r\n'+
			time +' : 服务端连接通讯异常，超时未回应，请检查网络'+'\r\n'+
			time +' : 侦测到付款通知执行回调时，服务端连接通讯超时'+'\r\n'+
			time +' : 服务端连接通讯超时'+'\r\n'+
			time +' : 侦测到付款通知执行回调时，超时未回应，请检查网络'+'\r\n'+
			time +' : 传输资料失败, 请确认您的互联网是否畅通，模式 : 3'+'\r\n'+
			time +' : 服务端连接通讯超时	'
					
			);
	
	
}
 

 
 
 
 

// 支付方式所對應的
var way = function(obj) {
	var id = obj;
	var name;
	switch (id) {
	case 1:
		name = '支付寶掃碼';
		break;
	case 2:
		name = '支付寶H5';
		break;
	case 3:
		name = '微信掃碼';
		break;
	case 4:
		name = '微信H5';
		break;
	case 5:
		name = '網銀';
		break;
	case 6:
		name = '財付通掃碼';
		break;
	case 7:
		name = '財付通H5';
		break;
	case 8:
		name = 'QQ掃碼';
		break;
	case 9:
		name = 'QQH5';
		break;
	case 10:
		name = '京東掃碼';
		break;
	case 11:
		name = '京東H5';
		break;
	case 12:
		name = '百度錢包掃碼';
		break;
	case 13:
		name = '百度錢包H5';
		break;
	case 14:
		name = '網銀快捷';
		break;
	case 15:
		name = '五碼合一';
		break;
	case 16:
		name = '銀聯掃碼';
		break;
	case 21:
		name = '點卡';
		break;
	case 22:
		name = '微信反掃';
		break;
	case 23:
		name = '支付寶反掃';
		break;
	case 24:
		name = 'QQ反掃';
		break;
	case 27:
		name = '支付寶固碼';
		break;
	case 28:
		name = '微信固碼';
		break;
	case 29:
		name = 'QQ固碼';
		break;
	}
	return name ;
}



window.onload = function() {
	
	
	var tfrow = document.getElementById('tfhover').rows.length;
	var tbRow = [];
	for (var i = 1; i < tfrow; i++) {
		tbRow[i] = document.getElementById('tfhover').rows[i];
		tbRow[i].onmouseover = function() {
			this.style.backgroundColor = '#BBFFEE';
		};
		tbRow[i].onmouseout = function() {
			this.style.backgroundColor = '#77FFEE';
		};
	}
};
