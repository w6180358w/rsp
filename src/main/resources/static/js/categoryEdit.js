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
    
    $("#typeId").on("change",function(e){
    	$("#typeName").val($("#typeId option:selected").text());
    });
    
    initSelectCity(true);
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