$(function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#typeForm").validate({
        rules : {
            type : {
                required : true
            },
            name:{
                required : true
            },
            group:{
                required : true
            }
        },
        messages : {
            key : {
                required : icon + "请输入唯一标识"
            },
            name : {
                required : icon + "请输入名称"
            },
            group : {
                required : icon + "请选择是否抵押"
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
			$("#city").val(values.id);
			$("#cityName").val(values.name);
		}
	});
	
    // 单选设置默认城市
    var defValue = $("#cityName").val() || "北京市";
    singleSelect1.setCityVal(defValue);
})

function submitTypeForm() {
    if (! $("#typeForm").valid()) {
        return;
    }
    var id = $("#id").val();
    var url ="type/save";
    var msg = "添加成功"
    var errMsg = "添加失败"
    if(id!=="0"){
        url = "type/update";
        msg = "更新成功";
        errMsg = "更新失败"
    }
    $.ajax({
        type: "POST",
        dataType: "html",
        url: rootpath+url,
        data: $('#typeForm').serialize(),
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