$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#orgForm").validate({
        rules : {
            term : {
                required : true,
                digits:true
            },
            limit : {
                required : true,
                number:true
            },
            name:{
                required : true
            }
        },
        messages : {
            term : {
                required : icon + "请输入期限",
                digits : icon + "请输入整数"
            },
            limit : {
                required : icon + "请输入额度",
                number : icon + "请输入合法的数字"
            },
            name : {
                required : icon + "请输入名称"
            }
        },
    });
})

function submitOrgForm() {
    if (! $("#orgForm").valid()) {
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
        data: $('#orgForm').serialize(),
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