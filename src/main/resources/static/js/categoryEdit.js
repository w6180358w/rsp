$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#categoryForm").validate({
        rules : {
            type : {
                required : true
            },
            name:{
                required : true
            }
        },
        messages : {
            type : {
                required : icon + "请选择类型"
            },
            name : {
                required : icon + "请输入名称"
            }
        },
    });
    var singleSelect1 = $('#single-select-1').citySelect({
		dataJson: cityData,     //数据源
		multiSelect: false,     //单选
		convert:false,
		shorthand: false,        //简称
		search: true,           //搜索
		onCallerAfter: function (target, values) {  //选择后回调
			city = values.id;
			getType();
		}
	});
    singleSelect1.setCityVal($("#cityName").val());
    var city = null,group = $("#group").val();
    
    $("#group").on("change",function(e){
    	group = $("#group").val();
    	getType();
    });
    
    $("#type").on("change",function(e){
    	$("#typeName").val($("#type option:selected").text());
    });
    
    function getType(){
    	if(!city){
    		layer.msg("请选择城市",{
                anim: -1,
                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
            });
    		return;
    	}
    	if(!group){
    		layer.msg("请选择抵押类型",{
                anim: -1,
                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
            });
    		return;
    	}
    	$.ajax({
    		type: "post",
            dataType: "json",
            url: rootpath+"type/city",
            data:{city:city,group:group},
            success: function (data) {
                if(!!data && !!data.success){
                    var type = $("#categoryForm").find("select[name=type]");
                    type.empty();
                    data.data.forEach(function(d){
                    	var option = $("<option value='"+d.key+"'>"+d.name+"</option>");
                    	type.append(option);
                    });
                }else{
                    layer.msg("查询类型失败，请联系管理员!",{
                        anim: -1,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                }
            },
            error: function(data) {
                alert("error:"+data.responseText);
            }	
    	})
    }
})

function submitCategoryForm() {
    if (! $("#categoryForm").valid()) {
        return;
    }
    var id = $("#id").val();
    var url ="category/save";
    var msg = "添加成功"
    var errMsg = "添加失败"
    if(id!=="0"){
        url = "category/update";
        msg = "更新成功";
        errMsg = "更新失败"
    }
    $.ajax({
        type: "POST",
        dataType: "html",
        url: rootpath+url,
        data: $('#categoryForm').serialize(),
        success: function (data) {
            if(data==="success"){
                var index = parent.layer.getFrameIndex(window.name);
                layer.msg(msg,{
                    anim: -1,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                }, function(){
                    parent.layer.close(index)
                });
            }else{
                layer.msg(errMsg,{
                    anim: -1,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
            }
        },
        error: function(data) {
            alert("error:"+data.responseText);
        }
    });
}