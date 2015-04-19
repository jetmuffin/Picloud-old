/**
 * 
 */

$(document).ready(function(){
	var url_ganglia = "http://localhost/ganglia/graph.php";
	var container = $("#flot-line-moving");
	
	var url = getDataUrl('Picloud','hour','cpu_report');
	$.getJSON(url, function(data){
        console.log(data);
        var dataPoint = [];

        function getRandomData() {
            if (dataPoint.length) {
            	dataPoint = dataPoint.slice(1);
            }
            while (dataPoint.length < maximum) {
                var previous = dataPoint.length ? dataPoint[dataPoint.length - 1] : 50;
                var y = previous + Math.random() * 10 - 5;
                dataPoint.push(y < 0 ? 0 : y > 100 ? 100 : y);
            }
            // zip the generated y values with the x values
            var res = [];
            for (var i = 0; i < dataPoint.length; ++i) {
                res.push([i, dataPoint[i]])
            }
            return res;
        }
        console.log(getRandomData());

        
        series = [{
            data: data[2].datapoint,
            lines: {
                fill: true
            }
        }];
        
        var plot = $.plot(container, series, {
	        grid: {
	            color: "#999999",
	            tickColor: "#D4D4D4",
	            borderWidth:0,
	            minBorderMargin: 20,
	            labelMargin: 10,
	            backgroundColor: {
	                colors: ["#ffffff", "#ffffff"]
	            },
	            margin: {
	                top: 8,
	                bottom: 20,
	                left: 20
	            },
	            markings: function(axes) {
	                var markings = [];
	                var xaxis = axes.xaxis;
	                for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
	                    markings.push({
	                        xaxis: {
	                            from: x,
	                            to: x + xaxis.tickSize
	                        },
	                        color: "#fff"
	                    });
	                }
	                return markings;
	            }
	        },
	        colors: ["#1ab394"],
	        xaxis: {
	            tickFormatter: function() {
	                return "";
	            }
	        },
	        yaxis: {
	            min: 0,
	            max: 110
	        },
	        legend: {
	            show: true
	        }
	    });

	   function euroFormatter(v, axis) {
	        return v.toFixed(axis.tickDecimals) ;
	    }
	    

	      $.plot($("#flot-line-chart-multi"), [{
	            data: data[0].datapoint,
	            label: "Oil price ($)"
	        }, {
	            data: data[1].datapoint,
	            label: "USD/EUR exchange rate",
	            yaxis: 2
	        }], {
	            xaxes: [{
	                mode: 'time'
	            }],
	            yaxes: [{
	                min: 0
	            }, {
	                // align if we are to the right
	                position: 'right',
	                tickFormatter: euroFormatter
	            }],
	            legend: {
	                position: 'sw'
	            },
	            colors: ["#1ab394"],
	            grid: {
	                color: "#999999",
	                hoverable: true,
	                clickable: true,
	                tickColor: "#D4D4D4",
	                borderWidth:0,
	                hoverable: true //IMPORTANT! this is needed for tooltip to work,

	            },
	            tooltip: true,
	            tooltipOpts: {
	                content: "%s for %x was %y",
	                xDateFormat: "%y-%0m-%0d",

	                onHover: function(flotItem, $tooltipEl) {
	                    // console.log(flotItem, $tooltipEl);
	                }
	            }
	        });
	      
});
	
	  
	      
	function getDataUrl(node,timestep,type){
		return url_ganglia + '?r=' + timestep + '&c=' + node + '&g=' + type + '&json=1'; 
	}
});