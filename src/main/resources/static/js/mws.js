$(document).ready(function() {
	if($.fn.dataTable!=null){
		$.fn.dataTable.defaults.oLanguage={
		        "sProcessing": "处理中...",
		        "sLengthMenu": "显示 _MENU_ 项结果",
		        "sZeroRecords": "没有匹配结果",
		        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
		        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
		        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
		        "sInfoPostFix": "",
		        "sSearch": "搜索:",
		        "sUrl": "",
		        "sEmptyTable": "表中数据为空",
		        "sLoadingRecords": "载入中...",
		        "sSearchPlaceholder":"搜索",
		        "sInfoThousands": ",",
		        "oPaginate": {
		            "sFirst": "首页",
		            "sPrevious": "上页",
		            "sNext": "下页",
		            "sLast": "末页"
		        },
		        "oAria": {
		            "sSortAscending": ": 以升序排列此列",
		            "sSortDescending": ": 以降序排列此列"
		        }
		    }

	}
	
	$.extend($.validator.messages, {
	    required: "这是必填字段",
	    remote: "请修正此字段",
	    email: "请输入有效的电子邮件地址",
	    url: "请输入有效的网址",
	    date: "请输入有效的日期",
	    dateISO: "请输入有效的日期 (YYYY-MM-DD)",
	    number: "请输入有效的数字",
	    digits: "只能输入数字",
	    creditcard: "请输入有效的信用卡号码",
	    equalTo: "你的输入不相同",
	    extension: "请输入有效的后缀",
	    maxlength: $.validator.format("最多可以输入 {0} 个字符"),
	    minlength: $.validator.format("最少要输入 {0} 个字符"),
	    rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
	    range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
	    max: $.validator.format("请输入不大于 {0} 的数值"),
	    min: $.validator.format("请输入不小于 {0} 的数值")
	});
	$.datepicker.regional['zh-CN'] = {  
	        closeText: '关闭',  
	        prevText: '上月',  
	        nextText: '下月',  
	        currentText: '今天',  
	        monthNames: ['一月','二月','三月','四月','五月','六月',  
	        '七月','八月','九月','十月','十一月','十二月'],  
	        monthNamesShort: ['一','二','三','四','五','六',  
	        '七','八','九','十','十一','十二'],  
	        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
	        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
	        dayNamesMin: ['日','一','二','三','四','五','六'],  
	        weekHeader: '周',  
	        dateFormat: 'yy-mm-dd',  
	        firstDay: 1,  
	        isRTL: false,  
	        showMonthAfterYear: true,  
	        yearSuffix: '年'};  
	    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);  
});
