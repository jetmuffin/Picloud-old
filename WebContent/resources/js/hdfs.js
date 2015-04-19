/**
 * 
 */
$(document).ready(function(){
	var url_base = $('#url_base').attr('data-url');
	var url_hdfs = url_base + '/inspect/hdfs.json';
    var color = [
                 "#1ab394",
                 "#79d2c0",
                 "#53F5D4",
                 "#169C80",
                 "#3FB4A7",
                 "#3FADB4"
                 ]
	$.getJSON(url_hdfs, function(data){
			var dfsUsed = percentToNum(data.dfsUsedPercent);
			var dfsRemaining = percentToNum(data.dfsRemainingPercent);
			var nonDFSUsed = 100 - dfsUsed - dfsRemaining;
			
			console.log(data);
			var data = new Array();
			var v = {
					label : 'dfsUsed',
					data : dfsUsed,
					color : color[0]					
			}
			data.push(v);
			var v = {
					label : 'dfsRemaining',
					data : dfsRemaining,
					color : color[1]					
			}
			data.push(v);
			var v = {
					label : 'nonDFSUsed',
					data : nonDFSUsed,
					color : color[2]					
			}
			data.push(v);			
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
    
  function percentToNum(percent){
    	var num = percent.substring(0,percent.length-1); 
    		return num;
  	}
});