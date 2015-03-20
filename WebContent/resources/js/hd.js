$(document).ready(function(){
	//url定义
	var url_base = $('#url_base').html();
	var val_default = $('#spaces_select').val();

	//select定义
	var img_chosen = $(".chosen-select");
	var img_default = img_chosen.attr("data-default");
	var img_select = $('#pictures_select');
	
	select_reload(getJsonUrl(val_default));
	console.log(getJsonUrl(val_default));
	img_chosen.chosen();
	
	//select 重新加载方法
	function select_reload(url){
		$.getJSON(url, function(json){
			if(json) {
				console.log(json);
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
	
	//获取空间图片json地址
	function getJsonUrl(val){
		return url_base + "/space/" + val +"/images.json";
	}
});