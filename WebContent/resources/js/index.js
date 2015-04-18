$(document).ready(function(){
    
    var url_base = $('#url_base').attr('data-url');
    var url = url_base + '/space/spaces.json';
    var color = [
        "#1ab394",
        "#79d2c0",
        "#53F5D4",
        "#169C80",
        "#3FB4A7",
        "#3FADB4"
        ]
			$.getJSON(url, function(spaces) {
				var tot_size = 0;

				console.log(spaces);
				$.each(spaces, function(i, val) {
					tot_size += parseFloat(val.storage);
				});

				console.log(tot_size);
				var data = new Array();
				
				$.each(spaces, function(i, val) {
					var v = {
						label : val.name,
						data : Math.round(val.storage / tot_size * 100),
						color : color[i]
					};
					data.push(v);
				});
				console.log(data);
				 var plotObj = $.plot($("#flot-pie-chart"), data, {
			     series: {
				     pie: {
				    	 			show: true,
				                }
			            },
			     grid: {
			       hoverable: true
			            },
			    tooltip: true,
			    tooltipOpts: {
			   content: "%p.0%, %s", // show percentages,
													// rounding to 2 decimal
													// places
			    shifts: {
			         x: 20,
			         y: 0
			                },
			      defaultTheme: false
			            }
			        });
			    });				
			});



    //Flot Pie Chart
    // var data = [{
    //     label: "空间1",
    //     data: 21,
    //     color: "#1ab394",
    // },{
    //     label: "空间2",
    //     data: 15,
    //     color: "#79d2c0",
    // }, {
    //     label: "未使用",
    //     data: 52,
    //     color: "#d3d3d3",
    // }];
        


