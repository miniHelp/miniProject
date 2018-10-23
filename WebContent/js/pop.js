$(function() {
		
	$('.btn').click(function() {
		var id = $(this).attr('dataId');
		var url = $(this).attr('dataUrl');
		$('#showUrl').text(url);
		$('.mask').css({
			'display' : 'block'
		});
		center($('.mess'));
		check($(this).parent(), $('.btn1'), $('.btn2'),id);
	});
	// 居中
	function center(obj) {
		var screenWidth = $(window).width();
		screenHeight = $(window).height(); // 当前浏览器窗口的 宽高
		var scrolltop = $(document).scrollTop();// 获取当前窗口距离页面顶部高度
		var objLeft = (screenWidth - obj.width()) / 2;
		var objTop = (screenHeight - obj.height()) / 2 + scrolltop;
		obj.css({
			left : objLeft + 'px',
			top : objTop + 'px',
			'display' : 'block'
		});
		// 浏览器窗口大小改变时
		$(window).resize(function() {
			screenWidth = $(window).width();
			screenHeight = $(window).height();
			scrolltop = $(document).scrollTop();
			objLeft = (screenWidth - obj.width()) / 2;
			objTop = (screenHeight - obj.height()) / 2 + scrolltop;
			obj.css({
				left : objLeft + 'px',
				top : objTop + 'px',
				'display' : 'block'
			});
		});
		// 浏览器有滚动条时的操作、
		$(window).scroll(function() {
			screenWidth = $(window).width();
			screenHeight = $(widow).height();
			scrolltop = $(document).scrollTop();
			objLeft = (screenWidth - obj.width()) / 2;
			objTop = (screenHeight - obj.height()) / 2 + scrolltop;
			obj.css({
				left : objLeft + 'px',
				top : objTop + 'px',
				'display' : 'block'
			});
		});
	}
	// 确定取消的操作
	function check(obj, obj1, obj2,id) {
		obj1.click(function() {
			var modifyUrl = $('#toModifyUrl').val();
			alert("id:"+id+"===url:"+modifyUrl);
			$.post(window.location.href,
				    {	
						'method':'modify', 
				        'id':id,
				        'url':modifyUrl
				    },
				        function(data,status){
				        alert("数据: \n" + data + "\n状态: " + status);
				        
				        //成功了 再發一次
				       $('#SeaechOrderForm input[name=id]').val(id);
				       document.forms['SeaechOrderForm'].submit();
				        
				    });
			closed($('.mask'), $('.mess'));
		});
		obj2.click(function() {
		
			closed($('.mask'), $('.mess'));
		});
	}
	// 隐藏 的操作
	function closed(obj1, obj2) {
		obj1.hide();
		obj2.hide();
	}
});