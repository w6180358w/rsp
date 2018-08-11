$(function () {
	var form = $("#searchForm").validate();
	$("#searchForm").find("select[name=type]").on("change",function (el){
		console.log(el)
		if(table!=null){
			table.clear(); 
			table.destroy(); 	   //销毁datatable
			$("#listTable").find("thead").remove();
		}
		var val = $(el.target).val();
		if(val && val!=""){
			loadFormula(val);
		}
	});
	$("#searchForm").find("button[name=search]").on("click",function (){
		console.log(form.valid());
		if($("#searchForm").valid()){
			if(table!=null){
				table.ajax.reload(function(json){
					console.log(json);
				});
			}
		}
		
	});
	var table = null;
	function loadFormula(type) {
		$.get("filter/columns/"+type,function(data){
			if(data.success){
				var columns = [{"data": "name","orderable": false,"width":"100"}];
				var keys = [];
				generateTitle(data.data,columns,keys);
				initTable(columns,keys);
			}
		});
	}
	function generateTitle(data,columns,keys){
		var thead = $("<thead></thead>");
		var one = $("<tr><th style='text-align:center'></th></tr>");
		var two = $("<tr><th style='text-align:center'>名称</th></tr>");
		var three =  $("<tr><td style='text-align:center'></td></tr>");
		data.forEach(function(col){
			var sub = col.subList;
			var th = $("<th style='text-align:center'></th>");
			$(th).prop("title",col.name).prop("colspan",sub.length).html(col.name);
			one.append(th);
			
			sub.forEach(function(s){
				var td = $("<th style='text-align:center'></th>").prop("title",s.name).html(s.name);
				two.append(td);
				three.append($("<td key='"+s.paramKey+"'><input type='number' placeholder='请输入' style='width:80px;margin:5px 10px;'/></td>"));
				columns.push({
					"data": s.paramKey,"orderable": false,"width":"100","defaultContent": ""
				});
				keys.push(s.paramKey);
			});
		});
		thead.append(one).append(two).append(three);
		$("#listTable").append(thead);
	}
	function initTable(columns,keys){
		table = $("#listTable").DataTable({
	        "serverSide": true,
	        "scrollX": true,
	        "paging": false, // 禁止分页
	        "sDom" : "t<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
	        "ajax": {
	            "url": "filter/tableFilter",
	            "type": "post",
	            "contentType":"application/json;charset=utf-8",//data.data.unshift(firstCol)
	            data:function(data){
	            	var param = getColParams();
	            	console.log(param);
	            	var result = {
	                		"keys":keys,
	                		"param":param,
	                		"draw":data.draw
	                	};
	            	return JSON.stringify(result);
	            }
	        },
	        "columns": columns
	    });
	}
	function initDataTable(columns,keys,firstCol){
		table = $("#listTable").DataTable({
	        select: {
	            style: 'single'
	        },
	        "scrollX": true,
	        "data":[firstCol,firstCol],
	        "paging": false, // 禁止分页
	        "rowCallback":bindEditor,
	        "oLanguage":oLanguage,
	        "columns": columns
	    });
	}
	function bindEditor(row,data,displayNum,displayIndex,dataIndex){
		var api = this.api();
		if(displayNum==0){
			$(row).find("td").on("dblclick",function(){
	    		var data = api.row($(this).parent("tr")).data();
	    		var that = $(this);
	    		if(that.index()>=0){
	    			var old = that.html();
	        		that.html("");
	    			var input = $("<input width='100%'/>");
	    			input.on("blur",function(){
	    				console.log("ajax");
	    				that.html(old);
	    				$(this).remove();
	    			});
	    			that.append(input);
	    			input[0].focus();
	    		}
	        });
		}
	}
	function getColParams(){
		var result = [];
		var tds = $(".dataTables_scrollHead").find("table>thead>tr:last>td");
		for(var i=1;i<tds.length;i++){
			var td = tds[i];
			var value = $(td).find("input").val();
			if(value==null || value==""){
				value = 0;
			}
			result.push({
				key:$(td).attr("key"),
				value:parseInt(value)
			});
		}
		return result;
	}
});