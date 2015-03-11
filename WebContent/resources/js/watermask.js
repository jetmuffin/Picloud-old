$(document).ready(function(){
	var image_name = 'test.jpg';
	var watermask_text_link = 'http://localhost:8080/PicServer/WaterMask'
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
		$('#watermask-text-pic').attr('src',url_watermask);
		$('.watermask-text-link').hide();

		var pic_overview = "<img src='" + url_overview + "'/>";
		$(".overview-pic").html('');
		$(pic_overview).appendTo($(".overview-pic"));
	});


	//watermask设置
	$("#watermask-text").keyup(function (e){
		var text = $(this).val();
		var new_link = watermask_text_link +'?image=' + image_name +'&type=text&text=' + text+ "&uid="+uid+"&offsetX=300&offsetY=200&fontsize=40";
		$('#watermask-new-link').html(new_link);
	});



});