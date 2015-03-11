$(document).ready(function(){
	var image_name = 'test.jpg';
	var scale_link = 'http://localhost:8080/PicServer/ScaleImage';
	var uid = $('#user_id').html();

	var val = $('#spaces_select').val();
	var url = "http://localhost:8080/PicServer/ListPicture";

	var pic_chosen = $(".chosen-select");
	var pic_default = pic_chosen.attr("data-default");
	var pic_select = $('#pictures_select');
	pic_chosen.chosen();
	select_reload(val,uid,url);

	//select 重新加载方法
	function select_reload(val,uid,url){
		$.getJSON(url, { space: val, uid: uid }, function(json){
			if(json.Picture) {
	 		 	var options = '<option></option>';
			 	$.each(json.Picture,function(n,value){
			 		if(value.name == pic_default)
						options += '<option selected="selected">' + value.name + '</option>';
			 		else	
			 			options += '<option>' + value.name + '</option>';
			 	});
			 	pic_select.html(options);
			 	pic_chosen.trigger("chosen:updated");			
			 } else {
			 	pic_select.html('<option></option>');
			 	pic_chosen.trigger("chosen:updated");
			 }
		});	
	}

	//ajax添加chosen-select
	$('#spaces_select').change(function(){
		var val = $(this).val();
		var url = "http://localhost:8080/PicServer/ListPicture";
		select_reload(val,uid,url);
	});

	//ajax加载图片
	pic_chosen.chosen().change(function(){
		image_name = $('.chosen-single span').html();
		var url_base = 'http://localhost:8080/PicServer/ScaleImage?uid='+uid+'&image=';
		var url_overview = url_base + image_name + '&width=270';
		var url_crop = url_base + image_name + '&width=500';
		var url_watermask = url_base + image_name + '&width=500';

		var pic_overview = "<img src='" + url_overview + "'/>";
		console.log(pic_overview);
		$(".overview-pic").html('');
		$(pic_overview).appendTo($(".overview-pic"));
		
		// $('#picture_overview').attr('src',url_overview);
	});

	//缩放输入框成比例调整
	var height_input = $("#pic-height");
	var width_input = $("#pic-width");
	var pic_height = height_input.attr("value");
	var pic_width = width_input.attr("value");
	var scale = pic_width/pic_height;

	//width_input 键盘监听
	width_input.keyup(function (e){
		var new_width = $(this).val();
		var new_height = parseInt(new_width / scale);
		height_input.val(new_height);
		$(".scale-link").html(scale_link +'?image=' + image_name + "&width=" + new_width + "&height=" + new_height + "&uid="+uid);
	});

	//height_input 键盘监听
	height_input.keyup(function (e){
		var new_height = $(this).val();
		var new_width = parseInt(new_height*scale);
		width_input.val(new_width);
		$(".scale-link").html(scale_link +'?image=' + image_name + "&width=" +new_width + "&height=" + new_height+ "&uid="+uid);
	});	

	//reset事件
	$("#scale-reset").click(function(){
		height_input.val(pic_height);
		width_input.val(pic_width);
		$("#basic_slider").val(100);
	});

	//noUiSlider设置
	$("#basic_slider").noUiSlider({
	            start: 100,
	            behaviour: 'tap',
	            connect: 'lower',
	            range: {
	                'min':  0,
	                'max':  100
	            }
	});
	$("#basic_slider").Link('lower').to($("#slider-value"), null, wNumb({
		decimals: 0,
		postfix:'%'
	}));
	$("#basic_slider").Link('lower').to($("#pic-width"), null, wNumb({
		decimals: 0,
		encoder:function( value ){
			return value * pic_width / 100;
		}
	}));
	$("#basic_slider").Link('lower').to($("#pic-height"), null, wNumb({
		decimals: 0,
		encoder:function( value ){
			return value * pic_height / 100;
		}
	}));		
	$("#basic_slider").on({
		slide: function(){
			var value = $(this).val();
			var height = parseInt(value * pic_height / 100);
			var width = parseInt(value * pic_width / 100);
			var new_link = scale_link +'?image=' + image_name + "&width=" +
										 width + "&height=" + height+"&uid="+uid;
			$(".scale-link").html(new_link);
		}
	});

});