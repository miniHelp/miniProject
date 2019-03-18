<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="errors" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<!-- 值班小帮手 -->
<html>
<head>
    <title>值班小帮手</title>
    <meta charset="utf-8">
    <link href="https://fonts.googleapis.com/css?family=Roboto+Mono" rel="stylesheet">
    <script type="text/javascript" src="js/md5.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/pop.js"></script>
    <script type="text/javascript" src="js/indexFunction.js"></script>

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
            filter: alpha(opacity=50);
            display: none;
            z-index: 99;
        }

        .mess {
            position: absolute;
            display: none;
            width: fit-content;
            height: fit-content;
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
            width: 850px;
            margin-top: 8px;
        }

        #resultDiv table td{
            text-align:center;
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

        .merchantDetalDiv > td {
            background-color: #d6c9b9;
        }

        .hideDiv{
            display:none;
        }
    </style>
</head>

<body>
<div id='functionDiv'>
    <input type="button" id='changeSearchOrder' view='searchDiv' value='查詢接口'>
    <input type="button" id='changeAuthOrder' view='authDiv' value='獲取授權'>
    <input type="button" view='justinMagicDiv' value='賈斯小魔法' onclick='justinMagic(this);'>
</div>
<!--
獲得權限的 還沒做好
 -->

<%------------------------------隱藏的form表單區----------------------------%>
<!--  查詢接口資料 -->
<form name='merchantListForm' action="<%=request.getContextPath()%>/merchantList" method='post'>
    <input type='hidden' id='merchantListId' name='id'>
    <input type="hidden" name="method" value="merchantList">
</form>
<!--  新增接口orderpage -->
<form id="insertMypayPlatform" action="${pageContext.request.contextPath}/insertMypay" method='post'>
    <input type='hidden' name='order_page_id' id="insertMypayFormId">
    <input type='hidden' name='order_page_name' id="insertMypayFormName">
</form>
<!--  刪除商戶 -->
<form id="deleteMertchantForm" method='post'>
    <input type='hidden' name='_method' value="delete">
</form>
<%---------------------------------------------------------------------------%>

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
                <td><input type='text' name='id' value='${param.id}' id="id"/></td>
            </tr>
            <tr>
                <td>支付網址：</td>
                <td><input type='text' name='url' value='${param.url}'
                           id="url"/></td>
            </tr>
            <tr>
                <td>接口名稱：</td>
                <td><input type='text' name='name' value='${param.name}'
                           id="name"/></td>
            </tr>

        </table>

        <input type="submit" value='送出' id="authSub"/>
        <input type="button" class="reset" value='清空'/>
        <input type="hidden" name="method" value="auth"/>
    </form>

</div>
<!--  查詢接口的form表單 -->
<div id='searchDiv' class='formDiv hideDiv'
     style="position: fixed; top: 38px">
    <h5>MyPay Query Order 查詢是哪個接口</h5>

    <form id='SeaechOrderForm' action="<%=request.getContextPath()%>/query" method='post' name='SeaechOrderForm'>
        <table class="tftable" border="1">
            <tr>
                <td>接口編號：</td>
                <td><input type='text' name='platformId' value='${param.platformId}' /></td>
            </tr>
            <tr>
                <td>接口名稱：</td>
                <td><input type='text' name='platformName' value='${param.platformName}' /></td>
            </tr>
            <tr>
                <td>支付網址：</td>
                <td><input type='text' name='platformUrl' value='${param.platformUrl}' /></td>
            </tr>
        </table>
        <input type="submit" value='送出' id="searchSub"/>
        <input type="button" class="reset" value='清空'/>
    </form>
</div>

<!-- 神奇小魔法 日誌產生器	 -->
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
                <input type='text' name='magicName' value='' id="magicName"/>
            </td>
        </tr>
        <tr>
            <td>支付方式：</td>
            <td>
                <input type='radio' name='type' value='微信支付' id="wx" checked/>微信支付
                <input type='radio' name='type' value='支付宝支付' id="zf"/>支付宝支付
            </td>
        </tr>
        <tr>
            <td>accID：</td>
            <td><input type='text' name='accId' value='2'
                       id="accId"/></td>
        </tr>
        <tr>
            <td>支付金額：</td>
            <td><input type='text' name='name' value='300.00'
                       id="money"/></td>
        </tr>
        <tr>
            <td>时间：</td>
            <td><input type='text' name='time' value='[10-06 15:04:42]'
                       id="time"/></td>
        </tr>
    </table>
    <input type="button" value='Magic' id="magicBtn" onclick='magic();'/>
    <input type="button" class="reset" value='清空'/>
</div>

<!-- 魔法小日志的div	 -->
<div id='justinMagicShowDiv' class='formDiv' style='display: none; position: relative; left: 540px; top: 85px'>
    <table id="tfhover" class="tftable" border="1">
        <tr>
            <th>信息</th>
            <td>
                <textarea id='magicShow' style='height:250px;width:700px'>
                </textarea>
            </td>
        </tr>
    </table>
</div>

<!-- 查詢接口的List，回傳的查詢訊息會顯示在這邊	 -->
<div id='resultDiv' class='formDiv hideDiv' style="position: relative; left: 540px; top:36px">
    <h5>查詢接口:${param.platformId}</h5>
    <table class="merchantReDiv" border="1" width='850px'>
        <tr>
            <th width='10%'>接口ID</th>
            <th width='20%'>接口</th>
            <th width='60%'>支付網址</th>
            <th width='10%'>操作</th>
            <th width='10%'>商戶部分</th>
        </tr>
        <c:if test="${fn:length(platformInfoList) eq 0 }">
            <tr>
                <td colspan="4">查無此資料</td>
            </tr>
        </c:if>
        <c:forEach items="${platformInfoList}" var="data">
            <tr>
                <td>${data.platform_id}</td>
                <td>${data.platform_name}</td>
                <td>${data.platform_url}</td>
                <td>
                    <input type="button" class="btn" value="修改" id="modifyPop${data.platform_id}" dataId="${data.platform_id}"
                           dataName="${data.platform_name}" dataUrl="${data.platform_url}"/>
                </td>
                <td>
                    <input type="button" value="超懶新增mypay平台" id="insertMypay" dataId="${data.platform_id}"
                           dataName="${data.platform_name}" onclick='insertMypay(this);'/>
                    <input type="button" value="超懶一鍵新增商戶" id="insertMypayMerchent" dataId="${data.platform_id}"
                           dataName="${data.platform_name}" dataUrl="<c:url value ="${request.contextPath}/insertMerchantLazy" />" onclick='PlantDetal(this);'/>
                    <input type="button" id="merchantList" value="mypay商戶列表" dataId="${data.platform_id}"
                           dataName="${data.platform_name}" onclick='showMerchList(this);'/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a><input type="button" name="back" value="上一頁" onclick="javascript:history.back(-1);"/></a>
    <a href="${pageContext.request.contextPath}/newIndex.jsp"><input type="button" value="回首頁"/></a>
</div>


<!-- 查詢商戶資料	 -->
<div id='merchantReDiv' class='formDiv' style='display: none; position: relative; left: 540px ; margin-top:50px '>
    <h5>查詢商戶:</h5>
    <c:forEach items="${merList}" var="data">
        <table class="merchantReDiv" border="1" width='850px'>
            <tr>
                <th width='12.5%'>接口</th>
                <th width='12.5%'>商戶編號</th>
                <th width='30%'>商戶名稱</th>
                <th width='12.5%'>商戶號</th>
                <th width='12.5%'>最大停用金額</th>
                <th width='12.5%'>累計筆數</th>
                <th width='12.5%'>累計金額</th>
                <th width='10%' colspan="2">操作</th>
            </tr>
            <tr class='three'>
                <td>${data.payment_platform_id}</td>
                <td>${data.merchantId}</td>
                <td>${data.merchant_name}</td>
                <td>${data.merchant_no}</td>
                <td>${data.max_stop_amount}</td>
                <td>${data.accumulate_record}</td>
                <td>${data.accumulate_amount}</td>
                <td colspan="2">
                    <input type="button" value="詳情"
                                       dataId="${data.merchantId}" id="merListPop${data.merchantId}"
                                       onclick='merchantDetal(this);'/>
                    <input type="button" value="幹掉他" dataId="${data.merchantId}" dataPlant="${data.payment_platform_id}" class="deleteMerchant"
                           id="${pageContext.request.contextPath}/merchantDetele/${data.payment_platform_id}/${data.merchantId}" onclick="merchantDetele(this)"/>
                </td>
            </tr>
        </table>
        <table class="merchantReDiv" border="1" id="togglePop${data.merchantId}"
               style='display: none' width='850px'>
            <tr>
                <!-- 將多餘的信息 隱藏起來	 -->
                <th colspan="6" id="toggleTr">詳情</th>
            </tr>
            <tr class='merchantDetalDiv'>
                <th class='one'>平台號 :</th>
                <th class='two'>${empty data.platform_no ? "無" : data.platform_no}</th>
                <th class='one'>商戶密碼 :</th>
                <th class='two'>${empty data.merchant_pwd ? "無" : data.merchant_pwd}</th>
                <th class='one'>簽章種類 :</th>
                <th class='two' id="signType">
                    <c:if test="${data.signature_type == 1}">MD5</c:if>
                    <c:if test="${data.signature_type == 2}">RSA</c:if>
                    <c:if test="${data.signature_type == 3}">RSA(PFX)</c:if>
                </th>
            </tr>
            <tr class='merchantDetalDiv'>
                <th class='one'>MD5密鑰 :</th>
                <td colspan="5">${data.signature_key}</td>
            </tr>
            <tr class='merchantDetalDiv'>
                <th class='one'>RSA商戶私鑰 :</th>
                <td colspan="5">${data.rsa_merchant_private_key}</td>
            </tr>
            <tr class='merchantDetalDiv'>
                <th class='one'>RSA平台公鑰 :</th>
                <td colspan="5">${data.rsa_server_public_key}</td>
            </tr>
        </table>
    </c:forEach>
    <input type="button" name="back" value="上一頁" onclick="javascript:history.back(-1);"/>
    <a href="${pageContext.request.contextPath}/newIndex.jsp"><input type="button" id="home" value="回首頁"/></a>
</div>


<!-- 新增一筆mypay商戶的form表單	 -->
<div id='insertMypayMerchentTable' class='formDiv hideDiv' style="position: relative; left: 540px; margin-top:50px">
    <%--改用spring的form标签来写--%>
    <%--@elvariable id="getContextPath" type=""--%>
    <form:form action="${pageContext.request.contextPath}/insertMerchant" method="post" modelAttribute="merchant"
               name="insertMypayMerchantForm">
        <table class="merchantReDiv" id="appendTable" name="merchantReName" border="1" width='850px'>
            <tr>
                <th style="width: 150px;">參數名稱</th>
                <th>參數值</th>
            </tr>
            <tr>
                <td>接口名稱：</td>
                <td>
                    <input type="text" type="text" id='plantName' name="plantName" disabled/>
                </td>
            </tr>
            <tr>
                <td>商戶名稱：</td>
                <td>
                    <form:input id='insertMypayMerchentFormName' path="merchant_name" name='merchentName'/>
                    <form:errors path="merchant_name" cssStyle="color: red;"/>
                </td>
            </tr>
            <tr>
                <td>商戶號：</td>
                <td>
                    <form:input path="merchant_no" id="merchentNo" name='merchentNo'/>
                    <form:errors path="merchant_no" cssStyle="color: red;"/>
                </td>
            </tr>
            <tr>
                <td> MD5 密鑰：</td>
                <td>
                    <form:textarea path="signature_key" id="signature_key" name='signature_key'
                                   cssStyle="margin: 0px; width: 550px; height: 60px;resize: none"/>
                </td>
            </tr>
            <tr>
                <td> 按下去按下去~:</td>
                <td>
                    <input:hidden id='insertMypayMerchentFormId' path="payment_platform_id"/>
                    <input:hidden path="merchant_status" value="1"/>
                    <input:hidden path="accumulate_record" value="0"/>
                    <input:hidden path="max_stop_amount" value="0"/>
                    <input:hidden path="create_date" value="<%=new Date()%>"/>
                    <input:hidden path="update_date" value="<%=new Date()%>"/>
                    <input type='button' id='insertMerId' name='insertMerId'value='見證奇蹟的時刻 ✧◝(⁰▿⁰)◜✧!!!!' onclick="document.forms['insertMypayMerchantForm'].submit()">
                    <input type='radio' name='state' value='on' checked> 啟用 (((o(*ﾟ▽ﾟ*)o)))
                    <input type='radio' name='state' value='off' > 停用 (｡ŏ_ŏ)

                </td>
            </tr>
        </table>
        <input type='hidden' id='insertMypayMerchentFormId' name='id' value=''>
        <input type="hidden" id='insertMypayMerchentForm' name="method" value="insertMerchant"/>
    </form:form>
</div>

<!-- 修改網關地址的彈窗 -->
<div id='modifyPop'>
    <div class="mask"></div>
    <div class="mess">
        <%--<p><span>接口编号:<span id="showId"></span></span></p>--%>
        <p><span>原网址:<span id="showUrl"></span></span></p>
        <form:form action="${pageContext.request.contextPath}/modifyPlatform" modelAttribute="platform" name="modifyPlatform" method="post">
            <form:hidden path="platform_id" id="modifyPlatformId"/>
            欲修改網址:<form:input path="platform_url" style="width: 300px;"/>
            <input type="button" value="确定" class="btn1" id="modifyUrl" onclick="document.forms['modifyPlatform'].submit()"/>
            <input type="button" value="取消" class="btn2"/>
        </form:form>
    </div>
</div>

<!-- 新增mypay平台後的回傳信息	 -->
<div id='insertReDiv' class='formDiv'
     style='display: none; position: relative; left: 800px'>
    <table id="tfhover" class="tftable" border="1">
        <tr>
            <th>回傳信息</th>
            <td>${meString}</td>
        </tr>
    </table>
</div>
<c:if test="${isDisplay == 'true' && empty method}">
    <script>
        $('.hideDiv').show();
    </script>
</c:if>

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



<%--<c:if test="${not empty msg }">--%>
    <%--<script>--%>
        <%--alert("${msg}");--%>
        <%--console.log("${msg}");--%>
    <%--</script>--%>
<%--</c:if>--%>

</body>
</html>