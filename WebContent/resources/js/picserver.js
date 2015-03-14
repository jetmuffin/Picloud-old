$(document).ready(function(){
        var url = window.location.href;  
		Dropzone.options.myAwesomeDropzone = {
           
                autoProcessQueue: false,
                uploadMultiple: true,
                parallelUploads: 200,
                maxFiles: 200,
                url: url,
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
                    });
                    this.on("errormultiple", function(files, response) {
                    });
                }

            }

       });
