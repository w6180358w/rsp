$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#contactsForm").validate({
        rules : {
            orgId : {
                required : true
            },
            phone : {
            	required : true
            },
            name:{
                required : true
            }
        },
        messages : {
        	orgId : {
                required : icon + "请选择机构"
            },
            name : {
                required : icon + "请输入姓名"
            },
            phone : {
            	required : icon + "请输入联系电话"
            }
        }
    });
    
    $('#contactsForm').find("select[name=orgId]").on("change",function(e){
    	$("#orgName").val($(e.target).find("option:selected").text());
    })
})

function submitContactsForm() {
    if (! $("#contactsForm").valid()) {
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
        dataType: "json",
        url: url,
        data: $('#contactsForm').serialize(),
        success: function (data) {
            if(!!data.success){
                var index = parent.layer.getFrameIndex(window.name);
                layer.msg(msg,{
                    anim: -1,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                }, function(){
                    parent.layer.close(index)
                });
            }else{
                layer.msg(data.error,{
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