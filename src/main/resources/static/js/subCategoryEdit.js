$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#subCategoryForm").validate({
        rules : {
            paramKey : {
                required : true
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
                required : icon + "请输入Key"
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
})

function submitSubCategoryForm() {
    if (! $("#subCategoryForm").valid()) {
        return;
    }
    var id = $("#id").val();
    var url ="save";
    var msg = "添加成功"
    if(id!=="0"){
        url = "update";
        msg = "更新成功";
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
            }
        },
        error: function(data) {
            alert("error:"+data.responseText);
        }
    });
}