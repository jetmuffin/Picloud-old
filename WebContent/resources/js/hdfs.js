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
  	
  function volumnToNum(volumn){
	  var unit = volumn.substr(volumn.length-2);
	  var num = volumn.substr(0,volumn.length-2);
	  if(unit == "MB") return parseFloat(num);
	    if(unit == "GB") return parseFloat(num)*1000;
  }
  	var datanodes = $('.flot-pie-chart-datanode');
  	for(var i = 0;i < datanodes.length;i++){
  			var datanode = datanodes[i];
  			    var used = volumnToNum($(datanode).attr("data-used"));
  			    var nonDFSUsed =  volumnToNum($(datanode).attr("data-nondDfsUsed"));
  			    var remaining =  volumnToNum($(datanode).attr("data-remaining"));
  			    	
  			    console.log(used+","+nonDFSUsed+","+remaining);
  			    			var total = used + nonDFSUsed + remaining;
  			    			used = used/total*100;
  			    			nonDFSUsed = nonDFSUsed/total*100;
  			    			remaining = remaining/total*100;
  				var data = new Array();
  				var v = {
  						label : 'dfsUsed',
  						data : used,
  						color : color[0]					
  				}
  				data.push(v);
  				var v = {
  						label : 'dfsRemaining',
  						data : nonDFSUsed,
  						color : color[1]					
  				}
  				data.push(v);
  				var v = {
  						label : 'nonDFSUsed',
  						data : remaining,
  						color : color[2]					
  				}
  				data.push(v);		
  				 var plotObj = $.plot($("#flot-pie-chart-datanode-"+i), data, {
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
  	    }
});