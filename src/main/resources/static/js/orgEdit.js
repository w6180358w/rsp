$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#orgForm").validate({
        rules : {
            term : {
                required : true,
                digits:true
            },
            limitMin : {
                required : true,
                digits:true
            },
            limitMax : {
                required : true,
                digits:true
            },
            interestRateMin : {
                number:true
            },
            interestRateMax : {
                number:true
            },
            phone : {
                _mobile : true
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
            limitMin : {
                required : icon + "请输入额度",
                digits : icon + "请输入整数"
            },
            limitMax : {
                required : icon + "请输入额度",
                digits : icon + "请输入整数"
            },
            interestRateMin : {
                number : icon + "请输入合法的数字"
            },
            interestRateMax : {
                number : icon + "请输入合法的数字"
            },
            phone : {
                _mobile : icon + "请正确填写联系电话"
            },
            name : {
                required : icon + "请输入名称"
            }
        },
    });
    // 手机号码验证
    jQuery.validator.addMethod("_mobile",function (value, element) {
        var length = value.length;
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");
    if($.fn.filestyle) {
        $("input[type='file']").filestyle({
            imagewidth: 78,
            imageHeight: 28
        });
        $("input.file").attr("readonly", true);
    }
})

function submitOrgForm() {
    if (! $("#orgForm").valid()) {
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
    var formData = new FormData($("#orgForm")[0]);
    $.ajax({
        type: "POST",
        url: url,
        processData: false,contentType: false,
        data: formData,
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