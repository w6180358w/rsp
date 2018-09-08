$(function () {
	$("#type").on("change",function(el){
		if(table!=null){
			table.clear(); 
			table.destroy(); 	   //销毁datatable
			$("#listTable").find("thead").remove();
		}
		var val = $(el.target).val();
		loadFormula(val);
	})
	initSelectCity(true);
	    
	var table = null;
	function loadFormula(type) {
		$.get(rootpath+"filter/columns/"+type,function(data){
			if(data.success){
				var columns = [{"data": "name","orderable": false,"width":"100",
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
		data.forEach(function(col){
			var sub = col.subList;
			var th = $("<th style='text-align:center'></th>");
			$(th).prop("title",col.name).prop("colspan",sub.length).html(col.name);
			one.append(th);
			
			sub.forEach(function(s){
				var td = $("<th style='text-align:center'></th>").prop("title","参数："+s.paramKey).html(s.name);
				two.append(td);
				columns.push({
					"data": s.id,"orderable": false,"width":"100",
					"render":function(value){
						if(value){
							return "<span subId='"+s.id+"' data-id='"+value.id+"'>"+(value==null?"":value.formula)+"</span>";
						}
						return "<span subId='"+s.id+"'></span>";
					}
				});
				ids.push(s.id);
			});
		});
		thead.append(one).append(two);
		$("#listTable").append(thead);
	}
	function initTable(columns,ids){
		table = $("#listTable").DataTable({
	        "serverSide": true,
	        "scrollX": true,
	        "paging": false, // 禁止分页
	        "rowCallback":bindEditor,
			"sDom" : "t<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
	        "ajax": {
	            "url": rootpath+"formula/queryAll",
	            "type": "post",
	            "contentType":"application/json;charset=utf-8",//data.data.unshift(firstCol)
	            data:function(data){
	            	var result = {
	                		"ids":ids
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
			if($(this).index()>0){
				var that = $(this).find("span");
				var input = $("<input style='width:80px;' placeholder='请输入'/>");
				var old = that.html();
				that.html("");
				input.val(old);
				input.on("blur",function(){
					var newv = input.val();
					if(newv!=old){
						var formula = {id:that.attr("data-id"),orgId:data.orgId,subCategoryId:that.attr("subId"),formula:newv};
						$.ajax({
							type: "POST",
							url:rootpath+"formula/merge",
							data:JSON.stringify(formula),
							contentType:"application/json;charset=utf-8",
							success:function(result){
								that.empty();
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
