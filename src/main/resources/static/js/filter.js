$(function () {
	var form = $("#searchForm");
	var type = -1;
	form.validate();
	form.find("select[name=typeId]").on("change",function (el){
		if(table!=null){
			table.clear(); 
			table.destroy(); 	   //销毁datatable
			$("#listTable").find("thead").remove();
		}
		type = parseInt($(el.target).val());
		loadFormula(type);
	});
	
	initSelectCity(true);
	
	form.find("button[name=search]").on("click",function (){
		if(form.valid()){
			if(table!=null){
				table.ajax.reload();
			}
		}
		
	});
	var table = null;
	function loadFormula(type) {
		$.get("filter/columns/"+type,function(data){
			if(data.success){
				var columns = [{"data": "name","orderable": false,"width":"100","defaultContent": "",
                    "render": function(data, type, record,index) {
                        return "<span title='"+data+"'>"+data+"</span>";
                    }}];
				var ids = [];
				generateTitle(data.data,columns,ids);
				initTable(columns,ids);
			}
		});
	}
	function generateTitle(data,columns,ids){
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
				three.append($("<td key='"+s.paramKey+"' defaultValue="+s.defaultValue+"><input type='number' placeholder='请输入' style='width:80px;margin:5px 10px;'/></td>"));
				columns.push({
					"data": s.id,"orderable": false,"width":"100","defaultContent": ""
				});
				ids.push(s.id);
			});
		});
		thead.append(one).append(two).append(three);
		$("#listTable").append(thead);
	}
	function initTable(columns,ids){
		table = $("#listTable").DataTable({
	        "serverSide": true,
	        "scrollX": true,
	        "paging": false, // 禁止分页
	        "sDom" : "t<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
	        "ajax": {
	            "url": rootpath+"filter/tableFilter",
	            "type": "post",
	            "dataType":"json",
	            "contentType":"application/json;charset=utf-8",//data.data.unshift(firstCol)
	            data:function(data){
	            	var param = getColParams();
	            	var result = {
	                		"ids":ids,
	                		"param":param,
	                		"typeId":type,
	                		"draw":data.draw
	                	};
	            	return JSON.stringify(result);
	            },
	            "error":function(data){
	            	var result = JSON.parse(data.responseText);
	            	layer.confirm(result.message, {
	                    btn : [ '确定' ]
	                }, function() {
	                   window.location.reload();
	                })
	            }
	        },
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
		
		var x = form.find("input[name=sqed]").val();
		var y = form.find("input[name=wdyhk]").val();
		var z = form.find("input[name=xykfz]").val();
		result.push({key:"x",value:(x==null || x=="")?0:parseFloat(x)});
		result.push({key:"y",value:(y==null || y=="")?0:parseFloat(y)});
		result.push({key:"z",value:(z==null || z=="")?0:parseFloat(z)});
		var tds = $(".dataTables_scrollHead").find("table>thead>tr:last>td");
		for(var i=1;i<tds.length;i++){
			var td = tds[i];
			//获取默认值  如果为空 默认值为0
			var defaultValue = $(td).attr("defaultValue");
			if(!!eval(defaultValue)){
				defaultValue = parseFloat(defaultValue);
			}else{
				defaultValue = 0;
			}
			//获取用户输入值  如果为空  设置默认值
			var value = $(td).find("input").val();
			if(!value){
				value = defaultValue;
			}
			result.push({
				key:$(td).attr("key"),
				value:parseFloat(value)
			});
		}
		return result;
	}
});
