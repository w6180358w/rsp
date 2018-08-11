$(function () {
	loadFormula();

    var PageViews = [], Sales = [];
    for (var i = 0; i <= 31; i++) {
        PageViews.push([i, 100+ Math.floor((Math.random() < 0.5? -1 : 1) * Math.random() * 25)]);
        Sales.push([i, 60 + Math.floor((Math.random() < 0.5? -1 : 1) * Math.random() * 40)]);
    }

    var plot = $.plot($("#mws-dashboard-chart"),
           [ { data: PageViews, label: "Page Views", color: "#c75d7b"}, { data: Sales, label: "Sales", color: "#c5d52b" } ], {
               series: {
                   lines: { show: true },
                   points: { show: true }
               },
               grid: { hoverable: true, clickable: true },
               xaxis: { min: 1, max: 31 },
               yaxis: { min: 0, max: 200 }
             });
});

function loadFormula() {
	$.get("formula/columns/gjj",function(data){
		if(data.success){
			var columns = [{"data": "name","orderable": false,"width":"500"}];
			generateTitle(data.data,columns);
			initTable(columns);
		}
	});
}
function generateTitle(data,columns){
	var thead = $(".mws-datatable-fn").find("thead");
	var one = $("<tr><th style='text-align:center'></th></tr>");
	var two = $("<tr><th style='text-align:center'>名称</th></tr>");
	data.forEach(function(col){
		var sub = col.subList;
		var th = $("<th style='text-align:center'></th>");
		$(th).prop("title",col.name).prop("colspan",sub.length).html(col.name);
		one.append(th);
		
		sub.forEach(function(s){
			var td = $("<th style='text-align:center'></th>").prop("title",s.name).html(s.name);
			two.append(td);
			columns.push({
				"data": s.paramKey,"orderable": false,"width":"500","defaultContent": ""
			});
		});
	});
	thead.empty();
	thead.append(one).append(two);
}
function initTable(columns){
	$(".mws-datatable-fn").DataTable({
        "serverSide": true,
        "scrollX": true,
        "paging": false, // 禁止分页
        "ajax": {
            "url": "formula/tableFilter",
            "type": "post",
            "contentType":"application/json;charset=utf-8",
            data:function(data){
            	var result = {
                		"keys":["a","b","c","d","e"],
                		"param":[{
                			"key":"a",
                			"value":10
                		},{
                			"key":"b",
                			"value":10
                		},{
                			"key":"c",
                			"value":30
                		},{
                			"key":"d",
                			"value":0
                		},{
                			"key":"e",
                			"value":10
                		}]
                	};
            	return JSON.stringify(result);
            }
        },
        "oLanguage": {//国际语言转化
            "oAria": {
                "sSortAscending": " - click/return to sort ascending",
                "sSortDescending": " - click/return to sort descending"
            },
            "sLengthMenu": "显示 _MENU_ 记录",
            "sZeroRecords": "对不起，查询不到任何相关数据",
            "sEmptyTable": "未有相关数据",
            "sLoadingRecords": "正在加载数据-请等待...",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。",
            "sInfoEmpty": "当前显示0到0条，共0条记录",
            "sInfoFiltered": "（数据库中共为 _MAX_ 条记录）",
            "sProcessing": "<img src='../resources/user_share/row_details/select2-spinner.gif'/> 正在加载数据...",
            "sSearch": "模糊查询：",
            "sUrl": "",
            //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": " 上一页 ",
                "sNext": " 下一页 ",
                "sLast": " 尾页 "
            }
        },
        "columns": columns
    });
}
function editCategory(id) {
    console.log(id)
}
function addCategory() {
    layer.open({
        type: 2,
        title: '添加大类',
        // title:false,
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area : ['800px' , '400px'],
        content: 'addCategory.html',
        end: function(){
            //关闭回调
            $(".mws-datatable-fn").DataTable().ajax.reload();
        }
    });
}

function submitCategoryForm() {
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "category/save",
        data: $('#categoryForm').serialize(),
        success: function (data) {
            if(data==="success"){
                var index = parent.layer.getFrameIndex(window.name);
                layer.msg('添加成功',{
                    anim: -1,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                }, function(){
                    parent.layer.close(index)
                });
            }
        },
        error: function(data) {
            alert("error:"+data.responseText);
        }
    });
}