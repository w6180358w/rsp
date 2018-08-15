$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    
    $("#subCategoryForm").find("select[name=type]").on("change",function(){
    	refreshCategory($(this).val());
    });
    
    $("#subCategoryForm").find("select[name=categoryId]").on("change",function(){
    	$("#categoryName").val($(this).find("option:selected").text());
    });
    
    refreshCategory($("#subCategoryForm").find("select[name=type]").val())
    function refreshCategory(type){
    	if(type==null || type=="")type=-1;
    	$.get("../category/type/"+type,function(data){
			if(data.success){
				var select = $("#subCategoryForm").find("select[name=categoryId]");
				select.empty();
				data.data.forEach(function(d){
					select.append($("<option value='"+d["id"]+"'>"+d["name"]+"</option>"));
				});
				var cid = $("#tempCId").val();
				if(cid==null || cid==""){
					select.val(data.data.length>0?data.data[0]["id"]:"");
				}else{
					select.val(cid);
				}
				select.trigger("change");
			}else{
				layer.msg("查询失败，请联系管理员!");
			}
		});
    }
    $("#subCategoryForm").validate({
        rules : {
            paramKey : {
                required : true,
                xyz:true
            },
            categoryId:{
                categoryId:true
            },
            name:{
                required : true
            }
        },
        messages : {
            paramKey : {
                required : icon + "请输入Key",
                xyz:icon + "key的值不能为【x】【y】【z】,不区分大小写"
            },
            categoryId : {
                categoryId : icon + "请选择大类"
            },
            name : {
                required : icon + "请输入名称"
            }
        },
    });
    jQuery.validator.addMethod("categoryId",function (value, element) {
        var returnVal = false;
        var intValue = parseInt(value);
        if(intValue>0){
            returnVal = true;
        }
        return returnVal;
    });
    jQuery.validator.addMethod("xyz",function (value, element) {
        var returnVal = false;
        if(value.toUpperCase()!=="X" && value.toUpperCase()!=="Y" && value.toUpperCase()!=="Z"){
            returnVal = true;
        }
        return returnVal;
    });
})

function submitSubCategoryForm() {
    if (! $("#subCategoryForm").valid()) {
        return;
    }
    var id = $("#id").val();
    var url ="save";
    var msg = "添加成功"
    var errMsg = "添加失败"
    if(id!=="0"){
        url = "update";
        msg = "更新成功";
        errMsg = "更新失败"
    }
    $.ajax({
        type: "POST",
        dataType: "html",
        url: url,
        data: $('#subCategoryForm').serialize(),
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