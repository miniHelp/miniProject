<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<!--
	Hielo by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
	<head>
		<title>值班小幫手</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="css/main.css" />
	</head>
	<body>

		<!-- Header -->
			<header id="header" class="alt">
				<div class="logo"><a href='<c:url value ="${request.contextPath}/newIndex.jsp" />'>mimiProject <span>by CoCo</span></a></div>
				<a href="#menu">Menu</a>
			</header>

		<!-- Nav -->
			<nav id="menu">
				<ul class="links">
					<li><a href='<c:url value ="${request.contextPath}/newIndex.jsp" />'>Home</a></li>
					<li><a href='<c:url value ="${request.contextPath}/generic.html"/>'>Generic</a></li>
					<li><a href='<c:url value ="${request.contextPath}/elements.html"/>'>Elements</a></li>
				</ul>
			</nav>

		<!-- Banner -->
			<section class="banner full">
				<article>
					<img src="images/slide01.jpg" alt="" />
					<div class="inner">
						<header>
							<p>技術 幫我檢查一下這個商戶是不是有問題?<a href="https://templated.co">你煩躁了嗎?</a></p>
							<h2>快速建立測試商戶</h2>
						</header>
					</div>
				</article>
				<article>
					<img src="images/slide02.jpg" alt="" />
					<div class="inner">
						<header>
							<p>Help me check this ~~please~~~ Quckly Useing three types to shearch thich you want</p>
							<h2>Find interface quickly</h2>
						</header> 
					</div>
				</article>
				<article>
					<img src="images/slide03.jpg"  alt="" />
					<div class="inner">
						<header>
							<p>天啊! 根據研究 平均每位程序員在對接時都會浪費將近20%的時間在建立這些重複的動作  !!</p>
							<h2>快速建立接口</h2>
						</header>
					</div>
				</article>
				<article>
					<img src="images/slide04.jpg"  alt="" />
					<div class="inner">
						<header>
							<p>I'm lazy coming soon</p>
							<h2>強大的對應商戶列表</h2>
						</header>
					</div>
				</article>
				<article>
					<img src="images/slide05.jpg"  alt="" />
					<div class="inner">
						<header>
							<p>Qickly making logs for YouPay</p>
							<h2>Justin Magic</h2>
						</header>
					</div>
				</article>
			</section>

		<!-- One -->
			<section id="one" class="wrapper style2">
				<div class="inner">
					<div class="grid-style">

						<div>
							<div class="box">
								<div class="image fit">
									<img src="images/pic02.jpg" alt="" />
								</div>
								<div class="content">
									<header class="align-center">
										<p>一鍵幫你查到有無相似的接口</p>
										<h2>查詢接口資料</h2>
									</header>
									<p> 每次為了查找接口 比對 總是要先登入系統等轉圈圈浪費大量時間嗎? 這個小幫手 幫你 從接口ID 簡體接口名稱 或是 提交網址 都讓你一鍵快速找出來</p>
									
									<footer class="align-center">
										<a href="#" class="button alt">Use now</a>
									</footer>
								</div>
							</div>
						</div>

						<div>
							<div class="box">
								<div class="image fit">
									<img src="images/pic03.jpg" alt="" />
								</div>
								<div class="content">
									<header class="align-center">
										<p>接口平台 或是 測試商戶 一鍵快速建立</p>
										<h2>快速建立</h2>
									</header>
									<p>每次建立完myPayCenter 就要進DB insert 一筆資料 而新增商戶更是最頭痛且繁瑣的地方  只要填入接口ID 一鍵替您節省20%的寶貴時間 快速方便 再也不用讓客戶催商務等 </p>
									
									<footer class="align-center">
										<a href="#" class="button alt">馬上體驗</a>
									</footer>
								</div>
							</div>
						</div>

					</div>
				</div>
			</section>

		<!-- Two -->
			<section id="two" class="wrapper style3">
				<div class="inner">
					<header class="align-center">
						<p>Nam vel ante sit amet libero scelerisque facilisis eleifend vitae urna</p>
						<h2>Morbi maximus justo</h2>
					</header>
				</div>
			</section>

		<!-- Three -->
			<section id="three" class="wrapper style2">
				<div class="inner">
					<header class="align-center">
						<p class="special">Nam vel ante sit amet libero scelerisque facilisis eleifend vitae urna</p>
						<h2>Morbi maximus justo</h2>
					</header>
					<div class="gallery">
						<div>
							<div class="image fit">
								<a href="#"><img src="images/pic01.jpg" alt="" /></a>
							</div>
						</div>
						<div>
							<div class="image fit">
								<a href="#"><img src="images/pic02.jpg" alt="" /></a>
							</div>
						</div>
						<div>
							<div class="image fit">
								<a href="#"><img src="images/pic03.jpg" alt="" /></a>
							</div>
						</div>
						<div>
							<div class="image fit">
								<a href="#"><img src="images/pic04.jpg" alt="" /></a>
							</div>
						</div>
					</div>
				</div>
			</section>


		<!-- Footer -->
			<footer id="footer">
				<div class="container">
					<ul class="icons">
						<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
						<li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
						<li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
						<li><a href="#" class="icon fa-envelope-o"><span class="label">Email</span></a></li>
					</ul>
				</div>
				<div class="copyright">
					&copy; Untitled. All rights reserved.
				</div>
			</footer>

		<!-- Scripts -->
		<script type="text/javascript" src="<c:url value ="/js/jquery.min.js" />"></script>
		<script type="text/javascript" src="<c:url value ="/js/jquery.scrollex.min.js" />"></script>
		<script type="text/javascript" src="<c:url value ="/js/skel.min.js" />"></script>
		<script type="text/javascript" src="<c:url value ="/js/util.js" />"></script>
		<script type="text/javascript" src="<c:url value ="/js/main.js" />"></script>
	
	</body>
</html>