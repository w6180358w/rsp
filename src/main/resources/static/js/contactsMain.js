$(function () {
    loadContacts();
});

function loadContacts() {
    $(".mws-datatable-fn").DataTable({
        "serverSide": true,
        "orderMulti": false,
        "ajax": {
            "url": rootpath+"contacts/queryAll",
            "type": "post",
            "data": function(data){
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
            { "data": "name","orderable": false,"title":"姓名","width":"30%",
                "render": function(data, type, record,index) {
                    return "<span title='"+data+"'>"+data+"</span>";
                }},
            { "data": "phone","orderable": false,"title":"电话","width":"30%",
                "render":function(data, type, record,index) {
                    return "<span title='"+data+"'>"+data+"</span>";
                }},
            { "data": "orgId","orderable": false,"title":"机构","width":"20%",
                "render":function(data, type, record,index) {
                	var name = record.orgName || "";
                    return "<span title='"+name+"'>"+name+"</span>";
                }},
            { "data": "order","orderable": false,"title":"是否轮播","width":"10%",
                "render":function(data, type, record,index) {
                	var status = !!data?"否":"是";
                    return "<span title='"+status+"'>"+status+"</span>";
                }},
            { "data": "id","orderable": false,"title":"操作","width":"20%",
                "render": function(data, type, record,index) {
                    return "<button onclick='editContacts("+data+")'>修改</button>" +
                        "<button onclick='deleteContacts("+data+")'>删除</button>";
                }
            }
        ]
    });
}
function deleteContacts(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : rootpath+"contacts/delete",
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
function editContacts(id) {
    layer.open({
        type: 2,
        title: '修改联系人',
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area : ['800px' , '430px'],
        content: 'contacts/add?id='+id,
        end: function(){
            //关闭回调
            $(".mws-datatable-fn").DataTable().ajax.reload();
        }
    });
}
function addContacts() {
    layer.open({
        type: 2,
        title: '添加联系人',
        // title:false,
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area : ['800px' , '430px'],
        content: 'contacts/add',
        end: function(){
            //关闭回调
            $(".mws-datatable-fn").DataTable().ajax.reload();
        }
    });
}

function refreshContacts() {
    $(".mws-datatable-fn").DataTable().ajax.reload();
}