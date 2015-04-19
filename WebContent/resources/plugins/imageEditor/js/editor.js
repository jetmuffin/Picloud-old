
    function Editor(params){
      for(var key in params){
        this[key] = params[key];
      }

      this.setType = function(_type){
        this.type = _type;
      }

      this.getType = function(){
        return this.type;
      }

      this.setBackgroundImage = function(_backgroundImage){
        this.backgroundImage = backgroundImage;
      }

      this.getBackgroundImage = function(){
        return this.backgroundImage;
      }

      this.setImage = function(_image){
        this.image = _image;
      }

      this.getImage = function(){
        return this.image;
      }
      this.setLogo = function(_logo){
    	  this.logo = _logo;
      }
      this.getLogo = function(){
    	  return this.logo;
      }
    var editor = this;
    this.appendHtml = function(){
      var insertText = "<div class='picloud-header'>      图片编辑器</div><div class='picloud-top-controls picloud-header-padding'><div class='picloud-top-controls-left'><div class='picloud-new'><i class='fa fa-link'></i>链接</div></div><div class='picloud-top-controls-center'><div class='picloud-controls-scale picloud-controls-process'><div class='picloud-controls-input-group'><input type='text' id='picloud-input-scale-width'><label   class='picloud-controls-input-label'>宽度</label></div><div class='picloud-controls-input-group'><input type='text' id='picloud-input-scale-height'><label  class='picloud-controls-input-label'>高度</label></div></div><div class='picloud-controls-crop picloud-controls-process' style='display:none'><div class='picloud-controls-item-group active' data-size='custom'><div class='picloud-controls-item-input'><input type='text' id='picloud-input-crop-width'><label   class='picloud-controls-input-label'>宽度</label></div><div class='picloud-controls-item-input'><input type='text' id='picloud-input-crop-height'><label  class='picloud-controls-input-label'>高度</label></div><div class='picloud-controls-item-input'><div class='picloud-controls-item-custom picloud-controls-button-item'></div><label   class='picloud-controls-input-label'>自定义</label></div></div><div class='picloud-controls-item-group' data-size='square'><div class='picloud-controls-item-square picloud-controls-button-item'></div><label   class='picloud-controls-input-label'>正方形</label></div><div class='picloud-controls-item-group' data-size='4:3'><div class='picloud-controls-item-4-3 picloud-controls-button-item'></div><label   class='picloud-controls-input-label'>4:3</label></div><div class=' picloud-controls-item-group' data-size='16:9'><div class='picloud-controls-item-16-9 picloud-controls-button-item'></div><label   class='picloud-controls-input-label'>16:9</label></div></div><div class='picloud-controls-textmark picloud-controls-process' style='display:none'><div class='picloud-controls-input-group'><div class='picloud-controls-text-color' id='color-control'></div><label  class='picloud-controls-input-label'>颜色</label></div><div class='picloud-controls-input-group'><input type='text' id='picloud-input-textmark-text' placeholder='请输入水印文字'><label  class='picloud-controls-input-label' >文字内容</label></div></div><div class='picloud-controls-watermark picloud-controls-process' style='display:none'><div class='picloud-controls-input-float'><button id='addLogo' class='btn btn-default' data-toggle='modal' data-target='#logoModal'>选择Logo</button><label class='picloud-controls-input-label'>logo</label></div><div class='picloud-controls-input-float'><div class='leftLabel'>0</div><div class='opacity-slider editor-slider' data-range_min='0' data-range_max='100' data-cur_min='100' data-cur_max='100'><div class='dot dot-right'></div><div class='bar'></div><div class='picloud-slider-label leftGrip'></div></div><div class='picloud-slider-label rightLabel'>1</div><label  class='picloud-controls-input-label' >透明度</label></div></div><div class='picloud-controls-rotate picloud-controls-process' style='display:none'><div class=' picloud-controls-item-group' data-rotate='right'><div class='picloud-controls-item-rotate-r picloud-controls-button-item' id='rotate-r'></div><label   class='picloud-controls-input-label'>顺时针</label></div><div class=' picloud-controls-item-group' data-rotate='left'><div class='picloud-controls-item-rotate-l picloud-controls-button-item' id='rotate-l'></div><label   class='picloud-controls-input-label'>逆时针</label></div><div class=' picloud-controls-item-group' data-rotate='left'><div class='picloud-controls-item-flip-v picloud-controls-button-item' id='flip-v'></div><label   class='picloud-controls-input-label'>竖直</label></div><div class=' picloud-controls-item-group' data-rotate='left'><div class='picloud-controls-item-flip-h picloud-controls-button-item' id='flip-h'></div><label   class='picloud-controls-input-label'>水平</label></div></div><div class='picloud-controls-brightness picloud-controls-process' style='display:none'><div class='picloud-controls-input-group'><div class='brightness-slider editor-slider' data-range_min='-100' data-range_max='100' data-cur_min='0' data-cur_max='0'><div class='dot dot-center'></div><div class='bar'></div><div class='picloud-slider-label leftGrip'></div></div><div class='picloud-controls-slider-plus'></div><div class='picloud-controls-slider-minus'></div><label  class='picloud-controls-input-label' >亮度</label></div></div><div class='picloud-controls-filters picloud-controls-process' style='display:none'><div class='picloud-controls-item-group active ' data-filter='default'><div class='picloud-controls-preview-default picloud-controls-button-item'></div></div><div class=' picloud-controls-item-group' data-filter='lomo'><div class='picloud-controls-preview-lomo picloud-controls-button-item'></div></div><div class=' picloud-controls-item-group' data-filter='gotham'><div class='picloud-controls-preview-gotham picloud-controls-button-item'></div></div><div class=' picloud-controls-item-group' data-filter='blur'><div class='picloud-controls-preview-blur picloud-controls-button-item'></div></div><div class=' picloud-controls-item-group' data-filter='sharpen'><div class='picloud-controls-preview-blur picloud-controls-button-item'></div></div><div class=' picloud-controls-item-group' data-filter='autoGamma'><div class='picloud-controls-preview-autoGamma picloud-controls-button-item'></div></div><div class=' picloud-controls-item-group' data-filter='charcoal'><div class='picloud-controls-preview-charcoal picloud-controls-button-item'></div></div></div></div><div class='picloud-top-controls-right'><div class='picloud-save'> <i class='fa fa-save'></i> 保存</div></div></div><div class='picloud-canvas-container picloud-header-padding'><div id='infoTag' class='picloud-imageinfo'></div><div class='picloud-drop-area-container picloud-header-padding'><div class='picloud-drop-area' id='canvas-zone'></div></div></div><div class='picloud-controls-container'><div class='picloud-controls'><div><div class='picloud-controls-overview'><ul class='picloud-controls-list'><li data-identifier='scale'><img src='/Picloud/resources/plugins/imageEditor/img/radial-blur.png' /></li><li data-identifier='crop'><img src='/Picloud/resources/plugins/imageEditor/img/crop.png' /></li><li data-identifier='watermark'><img src='/Picloud/resources/plugins/imageEditor/img/stickers.png' /></li><li data-identifier='textmark'><img src='/Picloud/resources/plugins/imageEditor/img/text.png' /></li><li data-identifier='filters'><img src='/Picloud/resources/plugins/imageEditor/img/filters.png' /></li><li data-identifier='rotate'><img src='/Picloud/resources/plugins/imageEditor/img/rotation.png' /></li><li data-identifier='brightness'><img src='/Picloud/resources/plugins/imageEditor/img/brightness.png' /></li></ul></div></div></div> ";
      var container = document.getElementById('picloud-container');
      container.innerHTML = insertText;
    }
    this.plugin = {
        //初始化
        init: function(){
          var canvasDom = document.createElement("canvas");
          var canvasZone = document.getElementById('canvas-zone');
          canvasDom.width = canvasZone.offsetWidth;
          canvasDom.height = canvasZone.offsetHeight; 
          canvasDom.id = 'canvas';
          canvasZone.appendChild(canvasDom);
          var infoTag = document.getElementById('infoTag');
          infoTag.innerHTML = editor.imageName;
        },

        //缩放构造方法
        scale: function(imageUrl){
          this.init();
          var plugin = this;
          plugin.canvas = new fabric.Canvas('canvas');
          var widthInput = document.getElementById('picloud-input-scale-width');
          var heightInput = document.getElementById('picloud-input-scale-height');

          var img = new Image();
          var image = plugin.image;
          img.src = imageUrl;
          img.onload = function(){
            plugin.size = editor.getSize(img.width,img.height);
            var displayWidth = Math.round(img.width);
            var displayHeight = Math.round(img.height);
            plugin.image = new fabric.Image(img, {
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2,
              width: plugin.size.width,
              height: plugin.size.height,
              hasRotatingPoint: false,  
            });
            plugin.canvas.add(plugin.image);
          
            widthInput.value = displayWidth;
            heightInput.value = displayHeight;

            var widthTag = new fabric.Text("", {
              fontSize: 14,
              left: plugin.image.left + plugin.image.width * plugin.image.scaleX / 2,
              top: plugin.image.top - 20,
              fontFamily: 'Impact',
              fill: '#fff',
              visible: false,
            });
            plugin.canvas.add(widthTag);

            var heightTag = new fabric.Text("", {
              fontSize: 14,
              left: plugin.image.left + plugin.image.width * plugin.image.scaleX + 20,
              top: plugin.image.top + plugin.image.height * plugin.image.scaleY/2,
              fontFamily: 'Impact',
              fill: '#fff',
              visible: false
            });
            plugin.canvas.add(heightTag);

            plugin.image.on('scaling', function(options) {
              widthInput.value = Math.ceil(plugin.image.width*plugin.image.scaleX/plugin.size.scale).toString();
              widthTag.set({
                visible: true,
                text:Math.ceil(plugin.image.width*plugin.image.scaleX/plugin.size.scale).toString(),
                left:plugin.image.left + plugin.image.width*plugin.image.scaleX/2,
                top:plugin.image.top - 20,
              });

              heightInput.value = Math.ceil(plugin.image.height*plugin.image.scaleY/plugin.size.scale).toString();
              heightTag.set({
                visible: true,
                text:Math.ceil(plugin.image.height*plugin.image.scaleY/plugin.size.scale).toString(),
                left: plugin.image.left + plugin.image.width*plugin.image.scaleX + 20,
                top: plugin.image.top + plugin.image.height*plugin.image.scaleY/2,          
              })
            });

            plugin.image.on('modified',function(options){
              widthTag.setVisible(false);
              heightTag.setVisible(false);
            });

            widthInput.onkeyup = function(event){
              var width = parseInt(widthInput.value);
              plugin.image.setWidth(width*plugin.size.scale);
              plugin.canvas.renderAll();
            }

            heightInput.onkeyup = function(event){
              var height = parseInt(heightInput.value);
              plugin.image.setHeight(height*plugin.size.scale);
              plugin.canvas.renderAll();
            }            
          }
        },

        //裁剪构造方法
        crop: function(imageUrl){
        	
          var img = document.createElement("img");
          var canvasZone = document.getElementById('canvas-zone');
          var widthInput = document.getElementById('picloud-input-crop-width');
          var heightInput = document.getElementById('picloud-input-crop-height');

          img.id = "cropCanvas";
          img.src = imageUrl;
    
          canvasZone.appendChild(img);
          var plugin = this;
          plugin.canvas = new Picrop('#cropCanvas', {
            minWidth: 100,
            minHeight: 100,
            maxWidth: canvasZone.offsetWidth,
            maxHeight: canvasZone.offsetHeight,

            plugins: {
              crop: {
                quickCropKey: 67, //key "c"
              }
            },
            init: function() {
              plugin.cropPlugin = this.getPlugin('crop');
              plugin.cropPlugin.selectZone(170, 25, 300, 200);
              var c = plugin.cropPlugin.Picrop.canvas;
              var image = plugin.cropPlugin.Picrop.image;
              widthInput.value = Math.round(plugin.cropPlugin.cropZone.width/image.scaleX);
              heightInput.value = Math.round(plugin.cropPlugin.cropZone.height/image.scaleY);

              c.on('mouse:up',function(event){
                scaleX = plugin.cropPlugin.cropZone.scaleX;
                scaleY = plugin.cropPlugin.cropZone.scaleY;
                widthInput.value = Math.round(plugin.cropPlugin.cropZone.width*scaleX/image.scaleX);
                heightInput.value = Math.round(plugin.cropPlugin.cropZone.height*scaleY/image.scaleX);
              })
            }
          });

          //宽度Input
          widthInput.onkeyup = function(event){
            var width = parseInt(widthInput.value);
            var height = plugin.cropPlugin.cropZone.height;
            var top = plugin.cropPlugin.cropZone.top;
            var left = plugin.cropPlugin.cropZone.left;
            plugin.cropPlugin.selectZone(left,top,width, height);
          }

          //高度Input
          heightInput.onkeyup = function(event){
            var height = parseInt(heightInput.value);
            var width = plugin.cropPlugin.cropZone.width;
            var top = plugin.cropPlugin.cropZone.top;
            var left = plugin.cropPlugin.cropZone.left;
            plugin.cropPlugin.selectZone(left,top,width, height);
          }
        },

        setCropSize: function(size){
          var widthInput = document.getElementById('picloud-input-crop-width');
          var heightInput = document.getElementById('picloud-input-crop-height');
          if(size == 'custom'){
            heightInput.disabled = false;              
            this.cropPlugin.selectZone(170, 25, 300, 200);
          } else {
            heightInput.disabled;
            if(size == 'square'){
              this.cropPlugin.selectZone(170, 25, 300, 300);
              heightInput.value = 300;
              widthInput.value = 300;            
            } else if(size == '4:3'){
              this.cropPlugin.selectZone(170, 25, 300, 300*0.75);
              heightInput.value = 300;
              widthInput.value = 300*0.75;                
            } else if(size == '16:9'){
              this.cropPlugin.selectZone(170, 25, 300, 300*9/16);
              heightInput.value = 300;
              widthInput.value = Math.round(300*9/16);              
            }
          }
        },

        //文字水印构造方法
        textmark: function(imageUrl){
          this.init();
          var plugin = this;
          plugin.canvas = new fabric.Canvas('canvas');
          var img = new Image();
          img.src = imageUrl;

          img.onload = function(){
            plugin.size = editor.getSize(img.width,img.height);
            plugin.backgroundImage = new fabric.Image(img, {
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2,
              width: plugin.size.width,
              height: plugin.size.height,
              hasControls:false,
              lockMovementY:true,
              lockMovementX:true,
              hoverCursor:"default"
            });
            plugin.canvas.add(plugin.backgroundImage);

            plugin.text = new fabric.Text('请输入水印文字', { 
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2
            });
            plugin.canvas.add(plugin.text);
          };

          var texInput = document.getElementById('picloud-input-textmark-text');
          texInput.onkeyup = function(event){
            var text = texInput.value;
            plugin.text.setText(text);
            plugin.canvas.renderAll();
          }
          return this;
        },

        //图片水印构造方法
        watermark: function(logoUrl,backgroundImageUrl){
          this.init();
          var plugin = this;
          var opacityInput = document.getElementById('picloud-input-watermark-opacity');
          plugin.canvas = new fabric.Canvas('canvas');
          var img = new Image();
          img.src = backgroundImageUrl;
          img.onload = function(){
            plugin.size = editor.getSize(img.width,img.height);
            plugin.backgroundImage = new fabric.Image(img, {
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2,
              width: plugin.size.width,
              height: plugin.size.height,
              hasControls:false,
              lockMovementY:true,
              lockMovementX:true,
              hoverCursor:"default"
            });
            plugin.canvas.add(plugin.backgroundImage);
            
          	if(logoUrl != null){
                var logoImage = new Image();
                logoImage.src = logoUrl;
                logoImage.onload = function(){
                  plugin.logo = new fabric.Image(logoImage,{
                    left: plugin.canvas.width/2-logoImage.width*0.5/2,
                    top: plugin.canvas.height/2-logoImage.height*0.5/2,
                  });
                  plugin.logo.scale(0.5).setCoords();
                  plugin.canvas.add(plugin.logo);
                }
          	}
          }

  
          return this;
        },

        rotate: function(imageUrl){
          this.init();
          var plugin = this;
          plugin.canvas = new fabric.Canvas('canvas');
          var img = new Image();
          img.src = imageUrl;
          img.onload = function(){
            plugin.size = editor.getSize(img.width,img.height);
            var displayWidth = Math.round(img.width);
            var displayHeight = Math.round(img.height);
            plugin.image = new fabric.Image(img, {
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2,
              width: plugin.size.width,
              height: plugin.size.height,
            });
            plugin.canvas.add(plugin.image);
          }
        },

        setRotation: function(angle){
          this.image.setFlipX(false);
          this.image.setFlipY(false);
          this.image.setAngle(angle);
          this.canvas.renderAll();
          this.rotation = true;
          this.flip = false;
        },

        setFlip: function(direction){
          this.image.setAngle(0);
          if(direction){
            this.image.setFlipX(!this.image.flipX);
            this.canvas.renderAll();
          }else{
            this.image.setFlipY(!this.image.flipY);
            this.canvas.renderAll();
          }
          this.flip = true;
          this.rotation = false;
        },

        brightness: function(imageUrl){
          this.init();
          var plugin = this;
          plugin.canvas = new fabric.Canvas('canvas');
          var img = new Image();
          img.src = imageUrl;
          img.onload = function(){
            plugin.size = editor.getSize(img.width,img.height);
            plugin.image = new fabric.Image(img, {
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2,
              width: plugin.size.width,
              height: plugin.size.height,
              hasControls:false,
              lockMovementY:true,
              lockMovementX:true,
              hoverCursor:"default"
            });
            plugin.filter = new fabric.Image.filters.Brightness({
              brightness: 0
            });
            plugin.image.filters.push(plugin.filter);            
            plugin.image.applyFilters(plugin.canvas.renderAll.bind(plugin.canvas));
            plugin.canvas.add(plugin.image);
          }
        },

        setBrightness: function(brightness){
          this.image.filters[0]['brightness'] = brightness;  
          this.image.applyFilters(this.canvas.renderAll.bind(this.canvas));     
        },

        filters: function(imageUrl){
          this.init();
          var plugin = this;
          plugin.canvas = new fabric.Canvas('canvas');
          var img = new Image();
          img.src = imageUrl;
          img.onload = function(){
            plugin.size = editor.getSize(img.width,img.height);
            plugin.image = new fabric.Image(img, {
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2,
              width: plugin.size.width,
              height: plugin.size.height,
              hasControls:false,
              lockMovementY:true,
              lockMovementX:true,
              hoverCursor:"default"
            });
            plugin.canvas.add(plugin.image);
          }
        },

        setFilters: function(newUrl,filter){
          this.canvas.remove(this.image);
          var plugin = this;
          var img = new Image();
          img.src = newUrl;
          plugin.filter = filter;
          img.onload = function(){
            plugin.size = editor.getSize(img.width,img.height);
            plugin.image = new fabric.Image(img, {
              left: (plugin.canvas.width-plugin.size.width)/2,
              top: (plugin.canvas.height-plugin.size.height)/2,
              width: plugin.size.width,
              height: plugin.size.height,
              hasControls:false,
              lockMovementY:true,
              lockMovementX:true,
              hoverCursor:"default"
            });
            plugin.canvas.add(plugin.image);
          }          
        }
      }

      this.link = {
        scale: function(url,img){
          var image = editor.plugin.image;
          var height = Math.round(image.height*image.scaleY/editor.plugin.size.scale);
          var width = Math.round(image.width*image.scaleX/editor.plugin.size.scale);
          return url + img + '/scale[' + width + ',' + height + ']'; 
        },

        crop: function(url,img){
          var cropZone = editor.plugin.cropPlugin.cropZone;
          var image = editor.plugin.cropPlugin.Picrop.image;
          var left = Math.round(cropZone.left/image.scaleX);
          var top = Math.round(cropZone.top/image.scaleY);
          var height = Math.round(cropZone.height*cropZone.scaleY/image.scaleY);
          var width = Math.round(cropZone.width*cropZone.scaleX/image.scaleX);
          return url + img + '/crop[' + left + ',' + top + ',' + width + ',' + height + ']'; 
        },

        textmark: function(url,img){
          var textmark = editor.plugin.text;
          var scale = editor.plugin.size.scale;
          var left = Math.round((textmark.left - editor.plugin.backgroundImage.left)/scale);
          var top = Math.round((textmark.top - editor.plugin.backgroundImage.top)/scale);
          var text = textmark.text;
          var opacity = textmark.opacity*100;
          var fontSize = textmark.fontSize;
          var color = textmark.fill;
          if(color == 'rgb(0,0,0)')
            color = '#000000';
          //TODO add opacity
          color = color.substr(1);
          return url + img + '/textmark[' + left + ',' + top + ',' + text + ',' + fontSize + ',' + color +',' + opacity+']';
        },

        watermark: function(url,img,logo){
          var logo = editor.getImage(editor.logo);
          var watermark = editor.plugin.logo;
          var scale = editor.plugin.size.scale;
          var height = Math.round(watermark.height*watermark.scaleY/scale);
          var width = Math.round(watermark.width*watermark.scaleX/scale);
          var left = Math.round((watermark.left - editor.plugin.backgroundImage.left)/scale);
          var top = Math.round((watermark.top - editor.plugin.backgroundImage.top)/scale);
          var opacity = watermark.opacity*100;
          return url + img + '/watermark[' + left + ',' + top + ',' + width + ',' + height + ',' + logo + ',' + opacity +']';
        },

        rotate: function(url,img){
          var img = editor.getImage(editor.image);
          if(!editor.plugin.flip){
            var angle = editor.plugin.image.angle;
            console.log(editor.plugin);
            return url + img + '/rotate[' + angle + ']';            
          }else{
            var flip = 0;
            if(editor.plugin.image.flipX)
              flip = 1;
            else
              flip = 0;
            return url + img + '/reverse[' + flip + ']';             
          }
        },

        brightness: function(url,img){
          var brightness = editor.plugin.image.filters[0]['brightness'];
          return url + img + '/brightness[' + (brightness/2+100) + ']';
        },
        filters:function(url,img){
        			var filter = editor.plugin.filter;
        						return url+img+'/'+filter;
        }
      }

      this.getImage = function(imageUrl){
        var index = imageUrl.lastIndexOf('/');
        if(index != -1){
          return imageUrl.substr(index+1);
        } else {
          return imageUrl;
        }
      }

      this.getSize = function(width,height){
        var canvas = editor.plugin.canvas;
        var rate = width/height;
        var displayHeight = height > canvas.height ? canvas.height : height;
        var displayWidth = displayHeight * rate;
        var size = new Object();
        size.height = Math.round(displayHeight);
        size.width = Math.round(displayWidth);
        size.scale = height > canvas.height ? canvas.height/height : 1;
        return size;
      }

      this.getVisitLink = function(){
        var image = this.getImage(editor.image);
        
        //TODO get the key
        switch(this.type){
          case 'scale': return this.link.scale(editor.visitUrl,image);break;
          case 'crop': return this.link.crop(editor.visitUrl,image);break;
          case 'textmark': return this.link.textmark(editor.visitUrl,image);break;
          case 'watermark': var logo = this.getImage(editor.logo);return this.link.watermark(editor.visitUrl,image,logo);break;
          case 'rotate': return this.link.rotate(editor.visitUrl,image);break;
          case 'brightness': return this.link.brightness(editor.visitUrl,image);break;
          case 'filters': return this.link.filters(editor.visitUrl,image);break;
          default: return;
        }
      }

      this.getUpdateLink = function(){
          var image = this.getImage(editor.image);
          
          //TODO get the key
          switch(this.type){
            case 'scale': return this.link.scale(editor.updateUrl,image);break;
            case 'crop': return this.link.crop(editor.updateUrl,image);break;
            case 'textmark': return this.link.textmark(editor.updateUrl,image);break;
            case 'watermark': var logo = this.getImage(editor.logo);return this.link.watermark(editor.updateUrl,image,logo);break;
            case 'rotate': return this.link.rotate(editor.updateUrl,image);break;
            case 'brightness': return this.link.brightness(editor.updateUrl,image);break;
            case 'filters': return this.link.filters(editor.updateUrl,image);break;
            default: return;
          }
        }
      
      this.render = function(){
        this.appendHtml();
        document.getElementById("canvas-zone").innerHTML = "";
        switch(this.type){
          case 'scale': this.plugin.scale(this.image);break;
          case 'crop': this.plugin.crop(this.image);break;
          case 'textmark': this.plugin.textmark(this.backgroundImage);break;
          case 'watermark': this.plugin.watermark(this.logo,this.backgroundImage);break;
          case 'rotate': this.plugin.rotate(this.image);break;
          case 'brightness': this.plugin.brightness(this.image);break;
          case 'filters': this.plugin.filters(this.image);break;
          default: return;
        }
      }

      this.renderAll = function(){
        document.getElementById("canvas-zone").innerHTML = "";
        switch(this.type){
          case 'scale': this.plugin.scale(this.image);break;
          case 'crop': this.plugin.crop(this.image);break;
          case 'textmark': this.plugin.textmark(this.backgroundImage);break;
          case 'watermark': this.plugin.watermark(this.logo,this.backgroundImage);break;
          case 'rotate': this.plugin.rotate(this.image);break;
          case 'brightness': this.plugin.brightness(this.image);break;
          case 'filters': this.plugin.filters(this.image);break;
          default: return;
        }
      }
  }

