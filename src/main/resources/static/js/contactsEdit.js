$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#contactsForm").validate({
        rules : {
            orgId : {
                required : true
            },
            phone : {
            	required : true,
            	_mobile : true
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
            	required : icon + "请输入联系电话",
                _mobile : icon + "请正确填写联系电话"
            }
        }
    });
 // 手机号码验证
    jQuery.validator.addMethod("_mobile",function (value, element) {
        var length = value.length;
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");
    
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
        data: $('#contactsForm').serialize(),
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