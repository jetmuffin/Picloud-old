$(document).ready(function(){
			var url_base = $('#url_base').attr('data-url');
			var url_upload = url_base + '/threeD/add'; 
            Dropzone.options.myAwesomeDropzone = {
                autoProcessQueue: false,
                uploadMultiple: true,
                parallelUploads: 200,
                maxFiles: 200,
                url: url_upload,
                method: "post",
                // Dropzone settings
                init: function() {
                    var myDropzone = this;
                    this.element.querySelector("button[type=submit]").addEventListener("click", function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        myDropzone.processQueue();
                    });
                    this.on("sendingmultiple", function() {
                    });
                    this.on("successmultiple", function(files, response) {
                    	location.reload();
                    });
                    this.on("errormultiple", function(files, response) {
                    });
                }
            }
       });