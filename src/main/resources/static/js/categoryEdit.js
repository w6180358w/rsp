$(function () {

})

function submitCategoryForm() {
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
            }
        },
        error: function(data) {
            alert("error:"+data.responseText);
        }
    });
}