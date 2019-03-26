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
<div class="layui-form-item" style="margin-top: 10px;margin-left: 10px;">
    <a href="${pageContext.request.contextPath}/newIndex.jsp"><button class="layui-btn layui-btn-normal">回首页</button></a>
</div>
<%
    Cookie [] cookies = request.getCookies();
    for (Cookie cookie : cookies){
        if(cookie.getName().equals("userName")){
            pageContext.setAttribute("userName",cookie.getValue());
        }
        if(cookie.getName().equals("passWord")){
            pageContext.setAttribute("passWord",cookie.getValue());
        }
        if(cookie.getName().equals("remember")){
            pageContext.setAttribute("remember",cookie.getValue());
        }
    }

%>
<form class="layui-form" action="${pageContext.request.contextPath}/user/loginCheckUser" method="post">
    <div class="login_face"><img src="${pageContext.request.contextPath}/images/timg.jpg" class="userAvatar"></div>
    <div class="layui-form-item input-item layui-input-focus">
        <label for="userName">后台账号</label>
        <input type="text" placeholder="请输入商家账号"  autocomplete="off"
               name="userName" id="userName" class="layui-input" lay-verify="required" value="${userName}">
        <c:if test="${!empty errorMsg && errorMsg=='没有此账号'}"><span style="color: red">${errorMsg}</span></c:if>
    </div>
    <div class="layui-form-item input-item layui-input-focus">
        <label for="password">后台密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off"
               name="passWord" id="password" class="layui-input" lay-verify="required" value="${passWord}">
        <c:if test="${!empty errorMsg && errorMsg=='密码错误'}"><span style="color: red">${errorMsg}</span></c:if>
    </div>
    <div class="checked_box">
        <input type="checkbox" lay-skin="primary" id="ck_rmbUser" name="ck_rmbUser" value="记住帐密" ${!empty remember ? "checked" : ""}/> 记住用户名和密码
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

        })

    })
</script>

</body>
</html>

