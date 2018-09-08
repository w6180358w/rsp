$(function () {
	var timeChart = echarts.init(document.getElementById('statTime'));
	var orgChart = echarts.init(document.getElementById('statOrg'));
	
	initOrg(null);
	initTime(null);
	$("#statForm").validate();
	$("#search").on("click",function(){
		var start = $('#start').datepicker( "getDate" );
		var end = $('#end').datepicker( "getDate" );
		if(start==null || end ==null){
			layer.msg("请输入搜索时间");
			return;
		}
		$.ajax({
			type: "get",
			url:rootpath+"stat/query",
			data:{start:start.getTime(),end:end.getTime()},
			contentType:"application/json;charset=utf-8",
			success:function(result){
				if(result.success){
					var data = result.data[0];
					initOrg(data["org"]);
					initTime(data["time"]);
				}else{
					layer.msg("查询失败，请联系管理员!");
				}
			}
		})
	});
	$('#start').datepicker({
	    dateFormat: 'yy-mm-dd',
	    onSelect : function(selectedDate) {
			$('#end').datepicker('option', 'minDate', selectedDate);
		}
	});
	
	$('#end').datepicker({
	    dateFormat: 'yy-mm-dd',
	    onSelect : function(selectedDate) {
			$('#start').datepicker('option', 'maxDate', selectedDate);
		}
	});

	function randomData() {
	    now = new Date(+now + oneDay);
	    value = value + Math.random() * 21 - 10;
	    return {
	        name: now.toString(),
	        value: [
	            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
	            Math.round(value)
	        ]
	    }
	}

	function initTime(data){
		var option = {
			    title: {
			        text: '查询明细'
			    },
			    legend: {
			        data:[]
			    },
			    tooltip: {
			        trigger: 'axis',
			        formatter: function (params) {
			        	var result = params[0].name+"</br>";
			        	params.forEach(function(param){
			        		result += (param.seriesName+":"+param.value[1]+"</br>");
			        	})
			            return result;
			        },
			        axisPointer: {
			            animation: false
			        }
			    },
			    toolbox:{
			    	show: true,
			        orient: 'vertical',
			        left: 'right',
			        top: 'center',
			        feature: {
			            mark: {show: true},
			            magicType: {show: true, type: ['line', 'bar']},
			            saveAsImage: {show: true}
			        }
			    },
			    xAxis: {
			        type: 'category'
			    },
			    yAxis: {
			        type: 'value',
			        boundaryGap: [0, '100%'],
			        splitLine: {
			            show: false
			        }
			    },
			    series: []
			};
		if(data!=null){
			option.legend.data = data.legend;
			option.xAxis.data = data.xAxis;
			option.series = data.series;
		}
		timeChart.setOption(option);
	}
	
	function initOrg(data){
		var option = {
				tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'shadow'
			        }
			    },
			    toolbox:{
			    	show: true,
			        orient: 'vertical',
			        left: 'right',
			        top: 'center',
			        feature: {
			        	 mark: {show: true},
			        	 magicType: {show: true, type: ['line', 'bar']},
				         saveAsImage: {show: true}
			        }
			    },
				title: {
				    text: '查询次数统计'
				},
				legend: {
			        data:[]
			    },
			    xAxis: {
			        type: 'category',
			        data: []
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: []
			};
		if(data!=null){
			option.legend.data = data.legend;
			option.xAxis.data = data.xAxis;
			option.series = data.series;
		}
		orgChart.setOption(option);
	}
});
function formatDateTime(inputTime) {  
	    var date = new Date(inputTime);
	    var y = date.getFullYear();  
	    var m = date.getMonth() + 1;  
	    m = m < 10 ? ('0' + m) : m;  
	    var d = date.getDate();  
	    d = d < 10 ? ('0' + d) : d;  
	    var h = date.getHours();
	    h = h < 10 ? ('0' + h) : h;
	    var minute = date.getMinutes();
	    var second = date.getSeconds();
	    minute = minute < 10 ? ('0' + minute) : minute;  
	    second = second < 10 ? ('0' + second) : second; 
	    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;  
	};

