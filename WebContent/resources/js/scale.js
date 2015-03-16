$(document).ready(function(){
	
	var imageKey = "testKey";
	
	//url定义
	var url_base = $('#url_base').html();
	var val_default = $('#spaces_select').val();

	//select定义
	var img_chosen = $(".chosen-select");
	var img_default = img_chosen.attr("data-default");
	var img_select = $('#pictures_select');
	
	select_reload(getJsonUrl(val_default));
	img_chosen.chosen();
	
	//select 重新加载方法
	function select_reload(url){
		$.getJSON(url, function(json){
			if(json) {
	 		 	var options = '<option></option>';
			 	$.each(json,function(n,value){
			 		console.log(value.key);
			 		if(value.name == img_default)
						options += '<option selected="selected">' + value.name + '</option>';
			 		else	
			 			options += '<option value=' + value.key + '>' + value.name + '</option>';
			 	});
			 	img_select.html(options);
			 	img_chosen.trigger("chosen:updated");			
			 } else {
			 	img_select.html('<option></option>');
			 	img_chosen.trigger("chosen:updated");
			 }
		});	
	}
	
	//添加chosen-select事件,加载相应的图片option
	$('#spaces_select').change(function(){
		var val = $(this).val();
		url = url_base + "/space/" + val +"/images.json";
		select_reload(getJsonUrl(val));
	});
	
	//chosen事件，加载图片
	img_chosen.on('change',function(e,params){
		var pic_overview = "<img src='" + getScaleUrl(270,"-",params.selected) + "'/>";
		$(".overview-pic").html('');
		$(pic_overview).appendTo($(".overview-pic"));
		imageKey = params.selected;
	});
	
	function getAtag(scale_link){
		return "<a target='_blank' href='" + scale_link + "'>" + scale_link + "</a>";
	}
	
	//获取缩放URL方法
	function getScaleUrl(width,height,imageKey){
		return url_base + '/process/' + imageKey + '/scale[' + width + ',' + height + ']';
	}
	
	//获取空间图片json地址
	function getJsonUrl(val){
		return url_base + "/space/" + val +"/images.json";
	}

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
		var scale_link = getScaleUrl(width,height,imageKey);
		$(".scale-link").html(getAtag(scale_link));
	});

	//height_input 键盘监听
	height_input.keyup(function (e){
		var new_height = $(this).val();
		var new_width = parseInt(new_height*scale);
		width_input.val(new_width);
		var scale_link = getScaleUrl(width,height,imageKey);
		$(".scale-link").html(getAtag(scale_link));
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
			var scale_link = getScaleUrl(width,height,imageKey);
			$(".scale-link").html(getAtag(scale_link));
			console.log(getAtag(scale_link));
		}
	});
});