$(function () {
    loadOrg();
});
function loadOrg() {
    $(".mws-datatable-fn").DataTable({
        "serverSide": true,
        "orderMulti": false,
        bAutoWidth:false,
        // searching:false,
        "ajax": {
            "url": "org/queryAll",
            "type": "post",
            "data": function(data){
                console.log(data);
                startNum = data.start;
                return {
                    "start":data.start,
                    "pageSize":data.length,
                    "sSearch":data.search.value,
                    "colName":data.columns[data.order[0].column].data,
                    "dir":data.order[0].dir,
                    "draw":data.draw
                }
            }
        },
        "oLanguage": {//国际语言转化
            "oAria": {
                "sSortAscending": " - click/return to sort ascending",
                "sSortDescending": " - click/return to sort descending"
            },
            "sLengthMenu": "显示 _MENU_ 记录",
            "sZeroRecords": "对不起，查询不到任何相关数据",
            "sEmptyTable": "未有相关数据",
            "sLoadingRecords": "正在加载数据-请等待...",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。",
            "sInfoEmpty": "当前显示0到0条，共0条记录",
            "sInfoFiltered": "（数据库中共为 _MAX_ 条记录）",
            "sProcessing": "<img src='../resources/user_share/row_details/select2-spinner.gif'/> 正在加载数据...",
            "sSearch": "模糊查询：",
            "sUrl": "",
            //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": " 上一页 ",
                "sNext": " 下一页 ",
                "sLast": " 尾页 "
            }
        },
        "columns": [
            // { "data": "id","orderable": false,"title":"ID","width":"40%"},
            { "data": "name","orderable": false,"title":"名称","width":"40px"},
            { "data": "limitString","orderable": false,"title":"额度","width":"40px"},
            { "data": "term","orderable": false,"title":"期限","width":"40px"},
            { "data": "interestRateString","orderable": false,"title":"利率","width":"40px"},
            // { "data": "requirements","orderable": false,"title":"申请条件","width":"40%"},
            // { "data": "material","orderable": false,"title":"申请材料","width":"40%"},
            // { "data": "logo","orderable": false,"title":"logo","width":"40px"},
            // { "data": "desc","orderable": false,"title":"描述","width":"40%"},
            { "data": "contacts","orderable": false,"title":"联系人","width":"40px"},
            { "data": "phone","orderable": false,"title":"联系电话","width":"40px"},
            { "data": "strengths","orderable": false,"title":"优势","width":"40px",
                "render": function(data, type, record,index) {
                    return "<span title='"+data+"'>"+(data.length>10?(data.substr(0,10)+"..."):data)+"</span>";
                }
            },
            { "data": "id","orderable": false,"title":"操作","width":"20px",
                "render": function(data, type, record,index) {
                    return "<button onclick='editOrg("+data+")'>修改</button>" +
                        "<button onclick='deleteOrg("+data+")'>删除</button>";
                }
            }
        ]
    });
}
function deleteOrg(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : "org/delete",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r==="success") {
                    layer.msg("删除成功");
                    $(".mws-datatable-fn").DataTable().ajax.reload();
                }else{
                    layer.msg("删除失败");
                }
            }
        });
    })
}
function editOrg(id) {
    layer.open({
        type: 2,
        title: '修改机构',
        // title:false,
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area : ['800px' , '100%'],
        content: 'org/add?id='+id,
        end: function(){
            //关闭回调
            $(".mws-datatable-fn").DataTable().ajax.reload();
        },
        success:function () {
            //加载完成的回调
            $("#id").val(id)
        }
    });
}
function addOrg() {
    layer.open({
        type: 2,
        title: '添加机构',
        // title:false,
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area : ['800px' , '100%'],
        content: 'org/add',
        end: function(){
            //关闭回调
            $(".mws-datatable-fn").DataTable().ajax.reload();
        }
    });
}

function refreshOrg() {
    $(".mws-datatable-fn").DataTable().ajax.reload();
}