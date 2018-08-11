$(document).ready(function() {
	/* Core JS Functions */
	
	/* Collapsible Panels */
	$(".mws-panel.mws-collapsible .mws-panel-header")
		.append("<div class=\"mws-collapse-button mws-inset\"><span></span></div>")
			.find(".mws-collapse-button span")
				.live("click", function(event) {
					$(this).toggleClass("mws-collapsed")
						.parents(".mws-panel")
							.find(".mws-panel-body")
								.slideToggle("fast");
				});

	/* Side dropdown menu */
	$("div#mws-navigation ul li a, div#mws-navigation ul li span")
	.bind('click', function(event) {
		if($(this).next('ul').size() !== 0) {
			$(this).next('ul').slideToggle('fast', function() {
				$(this).toggleClass('closed');
			});
			event.preventDefault();
		}
	});
	
	/* Message & Notifications Dropdown */
	$("div#mws-user-tools .mws-dropdown-menu a").click(function(event) {
		$(".mws-dropdown-menu.toggled").not($(this).parent()).removeClass("toggled");
		$(this).parent().toggleClass("toggled");
		event.preventDefault();
	});
	
	$('html').click(function(event) {
		if($(event.target).parents('.mws-dropdown-menu').size() == 0) {
			$(".mws-dropdown-menu").removeClass("toggled");
		}
	});
	
	/* Side Menu Notification Class */
	$(".mws-nav-tooltip").addClass("mws-inset");
	
	/* Table Row CSS Class */
	$("table.mws-table tbody tr:even").addClass("even");
	$("table.mws-table tbody tr:odd").addClass("odd");
	
	/* File Input Styling */
	
	if($.fn.filestyle) {
		$("input[type='file']").filestyle({
			imagewidth: 78, 
			imageHeight: 28
		});
		$("input.file").attr("readonly", true);
	}
	
	/* Tooltips */
	
	if($.fn.tipsy) {
		var gravity = ['n', 'ne', 'e', 'se', 's', 'sw', 'w', 'nw'];
		for(var i in gravity)
			$(".mws-tooltip-"+gravity[i]).tipsy({gravity: gravity[i]});
			
		$('input[title], select[title], textarea[title]').tipsy({trigger: 'focus', gravity: 'w'});
	}
	
	/* Dual List Box */
	
	if($.configureBoxes) {
		$.configureBoxes();
	}
	
	if($.fn.placeholder) {
		$('[placeholder]').placeholder();
	}
	
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
});
