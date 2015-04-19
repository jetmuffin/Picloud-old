/**
 * 
 */

  $(function(){
		var imageEditor = $('#imageEditor');
 		var image = imageEditor.attr('data-image');
 		var imageKey = imageEditor.attr('data-key');
 		var imageUrl = imageEditor.attr('data-imageUrl');
 		var visitUrl = imageEditor.attr('data-visit');
 		var updateUrl = imageEditor.attr('data-update');
 		
 		var  editor = new Editor({
    type:'scale',
    image:imageUrl,
    imageName:image,
   // logo:'img/logo.png',
    backgroundImage:imageUrl,
    visitUrl:visitUrl,
    	updateUrl:updateUrl
  		});
 		editor.render();

  	
	    var controlButton = $('.picloud-controls-list li');
	      var controlGroup = $('.picloud-controls-process');
	      var editorDefault = 'scale';
	      var listDefault = $('.picloud-controls-list li:first');
	      var canvasWidth = $('.picloud-drop-area').width();
	      var canvasHeight = $('.picloud-drop-area').height();

	      //工具栏鼠标点击加载编辑器
	      controlButton.click(function(e){    	  			
	        editorType = $(this).attr('data-identifier');
	        changeEditor(editorType,$(this));
	      });

	      //切换编辑器
	      function changeEditor(editorType,listSelector){
	        //加载工具栏
	        controlButton.each(function(i){
	          $(this).removeClass('active');
	        });
	        controlGroup.each(function(i){
	          $(this).hide();
	        });
	        listSelector.addClass('active');
	        var editorControlTag = '.picloud-controls-' + editorType;
	        $(editorControlTag).fadeIn();

	        //重新加载编辑器
	        editor.setType(editorType);
	        editor.renderAll();
	      }

	      //裁剪按钮
	      var cropButton = $('.picloud-controls-item-group');
	      function changeCropSize(size,cropSelector){
	        cropButton.each(function(i){
	          $(this).removeClass('active');
	        });        
	        cropSelector.addClass('active');
	        editor.plugin.setCropSize(size);
	      }
	      cropButton.click(function(){
	        var size = $(this).attr('data-size');
	        changeCropSize(size,$(this));
	      });
	      
	      //旋转按钮
	      $('#rotate-r').click(function(){
	        var angle = editor.plugin.image.angle;
	        if(angle%90!=0)
	          editor.plugin.setRotation(90);
	        else
	          editor.plugin.setRotation(angle+90);
	      });
	      $('#rotate-l').click(function(){
	        var angle = editor.plugin.image.angle;
	        if(angle%90!=0)
	          editor.plugin.setRotation(-90);
	        else
	          editor.plugin.setRotation(angle-90);
	      });
	      $('#flip-v').click(function(){
	        editor.plugin.setFlip(0);
	      });
	      $('#flip-h').click(function(){
	        editor.plugin.setFlip(1);
	      });
	      //图片水印滑动
	      $('.opacity-slider').nstSlider({
	          "left_grip_selector": ".leftGrip",
	          "value_bar_selector": ".bar",
	          "value_changed_callback": function(cause, leftValue, rightValue) {
	              if(leftValue != 100) {
	                var opacity = leftValue / 100;
	                var logo = editor.plugin.logo;
	                var canvas = editor.plugin.canvas;
	                logo.setOpacity(opacity);
	                canvas.renderAll();                
	              }
	          }
	      });

	      //亮度滑动
	      $('.brightness-slider').nstSlider({
	          "left_grip_selector": ".leftGrip",
	          "value_bar_selector": ".bar",
	          "value_changed_callback": function(cause, leftValue, rightValue) {
	              if(leftValue != 0){
	                editor.plugin.setBrightness(leftValue);                
	              }
	          }
	      });

	      //文字水印颜色
	      $('#color-control').spectrum({
	        color: "#000",
	        showAlpha: true,
	        move: function(color) {
	          var opacity = color._a;
	          color = color.toHexString(); 
	          $(this).css("background",color);
	          var text = editor.plugin.text;
	          var canvas = editor.plugin.canvas;
	          text.setColor(color);
	          text.setOpacity(opacity);
	          canvas.renderAll();                
	        }
	      });
	      $(".picloud-new").zclip({ 
	    	  path: "/Picloud/resources/plugins/zclip/ZeroClipboard.swf", 
	    	  copy: function(){ 
	    		  			console.log(123);
	    		  return $(this).prev().val(); 
	    		  } 
	      		}); 

	      $('#test').click(function(){
	        // console.log(editor.getVisitLink());
	        editor.plugin.setFilters("img/test.jpg");
	        // editor.getSize(1366,768);
	      });		
	      
	      
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
	  	 		 	var options = '<option></option>';
	  			 	$.each(json,function(n,value){
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
	  	
	  	//滤镜变化
	  $('.picloud-controls-filters .picloud-controls-item-group').click(function(){
	  		var filter = $(this).attr('data-filter');
	  			var newUrl = url_base+'/process/'+imageKey+'/'+filter;
	  			editor.plugin.setFilters(newUrl,filter);
	  	});
	  
		//添加chosen-select事件,加载相应的图片option
		$('#spaces_select').change(function(){
			var val = $(this).val();
			url = url_base + "/space/" + val +"/images.json";
			select_reload(getJsonUrl(val));
		});
		
		//chosen事件，加载Logo
		img_chosen.on('change',function(e,params){
			var image_name = $('.chosen-single span').html();
			$('#addLogo').html(image_name);
			var logoKey = params.selected;
			var logoUrl = url_base + '/server/' + logoKey;
			editor.setLogo(logoUrl);
			editor.renderAll();
			console.log(logoUrl);
			$('#logoModal').modal('hide')
		});
		
		//获取空间图片json地址
		function getJsonUrl(val){
			return url_base + "/space/" + val +"/images.json";
		}
		
		//获取访问地址
		$('.picloud-new').click(function(){
			var visitLink = editor.getVisitLink();
			window.open(visitLink);     
		});
		
		$('.picloud-save').click(function(){
			var updateLink = editor.getUpdateLink();
			$('#confirmModal').modal();
			
			$('#confirmSave').click(function(){
				$.ajax({
					  url: updateLink,
					  success: function(html){
						  location.reload();
					  }
					});
			})
		});
		

    });