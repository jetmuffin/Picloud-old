$(document).ready(function(){
	
	//url定义
	var url_base = $('#url_base').html();
	console.log(url_base);
	var val = $('#spaces_select').val();
	var url_json = url_base + "/space/" + val +"/images.json";
	console.log(url_json);

	//select定义
	var img_chosen = $(".chosen-select");
	var img_default = pic_chosen.attr("data-default");
	var img_select = $('#pictures_select');
	
	function getScaleUrl(width,height,image){
		return scale_link + '/' + image + '/scale[' + width + ',' + height + ']';
	}

/*
	var image_name = 'test.jpg';
	var scale_link = url_base + '/process';
	
	var url = url_base + "/space/" + val +"/images.json";
	var val = $('#spaces_select').val();

	var pic_chosen = $(".chosen-select");
	var pic_default = pic_chosen.attr("data-default");
	var pic_select = $('#pictures_select');
	pic_chosen.chosen();
	select_reload(url);

	//select 重新加载方法
	function select_reload(url){
		$.getJSON(url, function(json){
			if(json) {
	 		 	var options = '<option></option>';
			 	$.each(json,function(n,value){
			 		if(value.name == pic_default)
						options += '<option selected="selected">' + value.name + '</option>';
			 		else	
			 			options += '<option value=' + value.key + '>' + value.name + '</option>';
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
		url = url_base + "/space/" + val +"/images.json";
		select_reload(url);
		console.log(url);
	});


	//ajax加载图片
	pic_chosen.chosen().change(function(){
		image_name = $('.chosen-single span').html();
		var pic_overview = "<img src='" + url_overview + "'/>";
		$(".overview-pic").html('');
		$(pic_overview).appendTo($(".overview-pic"));
		
		// $('#picture_overview').attr('src',url_overview);
	});
*/

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
		$(".scale-link").html(scaleUrl(width,height,'test.jpg'));
	});

	//height_input 键盘监听
	height_input.keyup(function (e){
		var new_height = $(this).val();
		var new_width = parseInt(new_height*scale);
		width_input.val(new_width);
		$(".scale-link").html(scaleUrl(width,height,'test.jpg'));
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
			$(".scale-link").html(scaleUrl(width,height,'test.jpg'));
		}
	});

});