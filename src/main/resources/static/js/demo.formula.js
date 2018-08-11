$(function () {
	$("#type").on("change",function(el){
		console.log("change")
		if(table!=null){
			table.clear(); 
			table.destroy(); 	   //销毁datatable
			$("#listTable").find("thead").remove();
		}
		var val = $(el.target).val();
		if(val && val!=""){
			loadFormula(val);
		}	
	})
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
		data.forEach(function(col){
			var sub = col.subList;
			var th = $("<th style='text-align:center'></th>");
			$(th).prop("title",col.name).prop("colspan",sub.length).html(col.name);
			one.append(th);
			
			sub.forEach(function(s){
				var td = $("<th style='text-align:center'></th>").prop("title","参数："+s.paramKey).html(s.name);
				two.append(td);
				columns.push({
					"data": s.paramKey,"orderable": false,"width":"100",
					"render":function(value){
						if(value){
							return "<span key='"+s.paramKey+"' data-id='"+value.id+"'>"+(value==null?"":value.formula)+"</span>";
						}
						return "<span key='"+s.paramKey+"'></span>";
					}
				});
				keys.push(s.paramKey);
			});
		});
		thead.append(one).append(two);
		$("#listTable").append(thead);
	}
	function initTable(columns,keys){
		table = $("#listTable").DataTable({
	        "serverSide": true,
	        "scrollX": true,
	        "paging": false, // 禁止分页
	        "rowCallback":bindEditor,
			"sDom" : "t<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
	        "ajax": {
	            "url": "formula/queryAll",
	            "type": "post",
	            "contentType":"application/json;charset=utf-8",//data.data.unshift(firstCol)
	            data:function(data){
	            	var result = {
	                		"keys":keys
	                	};
	            	return JSON.stringify(result);
	            }
	        },
	        "columns": columns
	    });
	}
	function bindEditor(row,data,displayNum,displayIndex,dataIndex){
		var api = this.api();
		$(row).find("td").on("dblclick",function(){
			var that = $(this).find("span");
			if(that.index()>=0){
				var input = $("<input style='width:80px;' placeholder='请输入'/>");
				var old = that.html();
				that.html("");
				input.val(old);
				input.on("blur",function(){
					var newv = input.val();
					if(newv!=old){
						var formula = {id:that.attr("data-id"),orgId:data.orgId,subCategoryKey:that.attr("key"),formula:newv};
						$.ajax({
							type: "POST",
							url:"formula/merge",
							data:JSON.stringify(formula),
							contentType:"application/json;charset=utf-8",
							success:function(result){
								that.empty();
								console.log(result);
								if(result.success){
									that.attr("data-id",result.data[0].id);
									that.html(newv);
								}else{
									layer.msg("保存失败，请联系管理员!",{
					                    anim: -1,
					                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
					                });
									that.html(old);
								}
							}
						});
					}else{
						that.empty();
						that.html(old);
					}
				});
				that.append(input);
				input[0].focus();
			}
	    });
	}
});
