  $(function(){
      var  editor = new Editor({
        type:'scale',
        image:'img/1.png',
        logo:'img/logo.png',
        backgroundImage:'img/test.jpg',
        visitUrl:'http://localhost:8080/Picloud/process/'
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

      $('#test').click(function(){
        // console.log(editor.getVisitLink());
        editor.plugin.setFilters("img/test.jpg");
        // editor.getSize(1366,768);
      });
    });