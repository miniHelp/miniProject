<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>值班小帮手</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/iconfont.css">
</head>
<body  id="mydiv" class="loginBody">
<form class="layui-form" action="${pageContext.request.contextPath}/user/loginCheckUser" method="post">
    <div class="login_face"><img src="${pageContext.request.contextPath}/images/timg.jpg" class="userAvatar"></div>
    <div class="layui-form-item input-item layui-input-focus">
        <label for="userName">后台账号</label>
        <input type="text" placeholder="请输入商家账号"  autocomplete="off" name="userName" id="userName" class="layui-input  " lay-verify="required">
        <c:if test="${!empty errorMsg}">${errorMsg}</c:if>
    </div>
    <div class="layui-form-item input-item layui-input-focus">
        <label for="password">后台密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off" name="passWord" id="password" class="layui-input " lay-verify="required">
        <c:if test="${!empty errorMsg}">${errorMsg}</c:if>
    </div>
    <div class="checked_box">
        <input type="checkbox" lay-skin="primary" id="ck_rmbUser"/> 记住用户名和密码
    </div>
    <div class="layui-form-item">
        <button class="layui-btn layui-block" lay-filter="login" lay-submit>登录</button>
    </div>
    <div class="layui-form-item">
        <!-- <a href="register.html" class="layui-btn layui-block" >注册</a> -->
    </div>
    <div class="layui-form-item layui-row">
        <a href="javascript:;" class="seraph icon-qq layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
        <a href="javascript:;" class="seraph icon-wechat layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
        <a href="javascript:;" class="seraph icon-sina layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
    </div>
</form>
<script src="${pageContext.request.contextPath}/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/canvas-particle.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="${pageContext.request.contextPath}/js/systemUrl.js"></script>
<script src='${pageContext.request.contextPath}/js/md5.js'></script>
<script type="text/javascript">
    $(function(){
        //配置
        var config = {
            vx: 4,  //小球x轴速度,正为右，负为左
            vy: 4,  //小球y轴速度
            height: 2,  //小球高宽，其实为正方形，所以不宜太大
            width: 2,
            count: 300,     //点个数
            color: "121, 162, 185",     //点颜色
            stroke: "130,255,255",      //线条颜色
            dist: 5000,     //点吸附距离
            e_dist: 30000,  //鼠标吸附加速距离
            max_conn: 10    //点到点最大连接数
        }
        CanvasParticle(config);
        if ($.cookie("rmbUser") == "true") {
            $("#ck_rmbUser").attr("checked", true);
            $("#userName").val($.cookie("userName"));
            $("#password").val($.cookie("passWord"));
        }
        layui.use(['form','layer','jquery'],function(){
            var form = layui.form,
                layer = parent.layer === undefined ? layui.layer : top.layer
            $ = layui.jquery;
            //登录按钮
            // form.on("submit(login)",function(data){
            //     // $(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
            //     setTimeout(function(){
            //         console.log(zLogin);
            //         var data = {'userName':$("#userName").val(),'passWord':$("#password").val()};
            //           $.ajax({
            //                 type:"POST",
            //                 url:'http://localhost:8080/miniProject/ListServerlet?method=loginTest',
            //                 dataType:"json",
            //                 data:{
            //                     userName: $("#userName").val(),
            //                     password: md5($("#password").val())
            //                 },
            //                 success:function(res){
            //                     console.log(res)
            //                     console.log(res.resCode)
            //                     if(res.resCode=="0000"){
            //                            if ($("#ck_rmbUser").is(":checked")== true) {
            //                                 $.cookie("rmbUser", "true", { expires: 3 }); // 存储一个带7天期限的 cookie
            //                                 $.cookie("userName", $("#userName").val(), { expires:3 }); // 存储一个带7天期限的 cookie
            //                                 $.cookie("passWord", $("#password").val(), { expires:3 }); // 存储一个带7天期限的 cookie
            //                             }
            //                             else {
            //                                 $.cookie("rmbUser", "false", { expires: -1 });
            //                                 $.cookie("userName", '', { expires: -1 });
            //                                 $.cookie("passWord", '', { expires: -1 });
            //                             }
            //                             layer.msg(res.resMsg)
            //                         /*
            //                             sessionStorage.setItem("identity",res.result.identity)
            //                             sessionStorage.setItem("token",res.result.token)
            //                             sessionStorage.setItem("merchantId",res.result.merchantId)
            //                             sessionStorage.setItem("parent",res.result.parent)
            //                             sessionStorage.setItem("Name",res.result.merchantName)
            //                         */
            //                           window.location.href = "index.jsp";
            //                     }else{
            //                          layer.msg(res.resMsg)
            //                    $(this).attr("disabled",false);
            //                     }
            //
            //                 },
            //                 error:function(err){
            //                     console.log(err)
            //                       $(this).attr("disabled",false);
            //                      layer.msg("网络错误，请稍后重试")
            //                 }
            //             })
            //
            //     },1000);
            //     return false;
            // })

        })

    })
</script>

</body>
</html>

