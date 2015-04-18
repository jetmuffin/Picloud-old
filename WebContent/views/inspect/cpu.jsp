<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${TITLE}</title>
<link rel="stylesheet" href="${RESOURCES}/font/css/font-awesome.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${RESOURCES}/css/common.css" />
<style>
#flot-line-moving {
	height: 500px;
}
#flot-line-chart-multi{
	height:500px;'
}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="../common/header.jsp" />
		<div class="page-wrapper">
			<jsp:include page="../common/navbar.jsp" />
			<jsp:include page="../common/breadcrumb.jsp" />
			<div class="wrapper wrapper-content animated fadeInDown">
				<block name="content">
				<div class="row">
					<div class="col-md-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									全景图片 <small>全景图片列表</small>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div id="flot-line-moving"></div>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									全景图片 <small>全景图片列表</small>
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
										class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content" style="display: block;">
								<div id="flot-line-chart-multi"></div>
							</div>
						</div>
					</div>
				</div>
				
				</block>
			</div>
			<jsp:include page="../common/footer.jsp" />
		</div>
	</div>
	<script type="text/javascript"
		src="${RESOURCES }/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${RESOURCES }/js/common.js"></script>
	<script src="${PLUGIN}/flot/jquery.flot.min.js"></script>
		<script src="${PLUGIN}/flot/jquery.flot.time.min.js"></script>
	<script>
	$(document).ready(function(){
		 var container = $("#flot-line-moving");
		     var maximum = container.outerWidth() / 2 || 300;

    //

    var data = [];

    function getRandomData() {
        if (data.length) {
            data = data.slice(1);
        }
        while (data.length < maximum) {
            var previous = data.length ? data[data.length - 1] : 50;
            var y = previous + Math.random() * 10 - 5;
            data.push(y < 0 ? 0 : y > 100 ? 100 : y);
        }
        // zip the generated y values with the x values
        var res = [];
        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }
        return res;
    }

    series = [{
        data: getRandomData(),
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
            data: getRandomData(),
            label: "Oil price ($)"
        }, {
            data: getRandomData(),
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
	</script>
	<script src="${RESOURCES}/js/cpu.js"></script>
</body>
</html>