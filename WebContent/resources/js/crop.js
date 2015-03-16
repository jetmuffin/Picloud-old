$(document).ready(function(){
	
	var url_base = $('#url_base').html();
	var image_name = 'test.jpg';
	var image_name = 'test.jpg';
	var crop_link = url_base + '/process';
	
	function cropUrl(width,height,image){
		return crop_link + '/' + image + '[' + width + ',' + height + ']';
	}
	
	/*
	var crop_link = 'http://localhost:8080/PicServer/CropImage';
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
		$('#picture_overview').attr('src',url_overview);
		$('#jcrop-word').hide();
	//	$('#jcrop').attr('src',url).show();

				var pic_overview = "<img src='" + url_overview + "'/>";
		$(".overview-pic").html('');
		$(pic_overview).appendTo($(".overview-pic"));
		
	
		$('#jcrop').Jcrop({
			onChange:   showCoords,
		      	onSelect:   showCoords,
		      	onRelease:  clearCoords
		},function(){
		      	jcrop_api = this;
		});
		*/
	//	jcrop_api.setImage(url_crop);


	
	//jcrop设置
	var jcrop_api;
	$('#jcrop-form').on('change','input',function(e){
	      	var x1 = $('#offset-x1').val();
	      	var x2 = $('#offset-x2').val();
	          	var y1 = $('#offset-y1').val();
	          	var y2 = $('#offset-y2').val();
	          	var width = $("#jcrop-width").val();
	          	var height = $("#jcrop_height").val();
	     	jcrop_api.setSelect([x1,y1,x2,y2]);
	});

	$("#jcrop-reset").click(function(){
		clearCoords();
	});
	function showCoords(c)
	{
		$('#offset-x1').val(c.x);
		$('#offset-x2').val(c.x2);
		$('#offset-y1').val(c.y);
		$('#offset-y2').val(c.y2);
		$('#jcrop-width').val(c.w);
		$('#jcrop-height').val(c.h);
		$('.crop-link').html(crop_link +'?image=' + image_name + '&width=' + c.w +
				'&height=' + c.h + '&offsetX=' + c.x + '&offsetY=' + c.y+ "&uid="+uid);
	};
	function clearCoords()
	{
		$('#jcrop-form input').not("#jcrop-submit").val('');
	};

});