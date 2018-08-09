$(function () {
    console.log("dashboard")
    $(".mws-datatable-fn").dataTable({
        sPaginationType: "full_numbers",
        sAjaxSource:"org/queryAll",
        // 是否允许排序
        sOrdering: false,
        bSort:false,
        bServerSide: true,
        aoColumns:[
            {
                "fnRender": function ( oObj ) {
                    return "<input type=\"checkbox\" value="+oObj.aData[0]+"/>";
                },
                "aTargets" : [ 0 ],"bSortable": false
            },
            { "id": "id","sTitle" : "ID","aTargets" : [ 1 ] },
            { "name": "name","sTitle" : "名称","aTargets" : [ 2 ] },
            { "limit": "limit","sTitle" : "额度","aTargets" : [ 3 ] },
            { "term": "term","sTitle" : "期限","aTargets" : [ 4 ] },
            { "interestRate": "interestRate","sTitle" : "利率","aTargets" : [ 5 ] },
            { "requirements": "requirements","sTitle" : "申请条件","aTargets" : [ 6 ] },
            { "material": "material","sTitle" : "申请材料","aTargets" : [ 7 ] },
            { "logo": "logo","sTitle" : "logo","aTargets" : [ 8 ] },
            { "desc": "desc","sTitle" : "描述","aTargets" : [ 9 ] },
            { "contacts": "contacts","sTitle" : "联系人","aTargets" : [ 10 ] },
            { "phone": "phone","sTitle" : "联系电话","aTargets" : [ 11 ] },
            { "strengths": "strengths","sTitle" : "优势","aTargets" : [ 12 ] },
            {
                "fnRender": function ( oObj ) {
                    return "<span class=\"ui-icon ui-icon-pencil\" onclick='editOrg("+oObj.aData[0]+")'></span>";
                },"aTargets" : [ 13 ]
            }
        ],
        sAjaxDataProp:"content",
        "aoColumnDefs" : [ {
            sDefaultContent : '',
            aTargets : [ '_all' ]
        } ]
    });
    var PageViews = [], Sales = [];
    for (var i = 0; i <= 31; i++) {
        PageViews.push([i, 100+ Math.floor((Math.random() < 0.5? -1 : 1) * Math.random() * 25)]);
        Sales.push([i, 60 + Math.floor((Math.random() < 0.5? -1 : 1) * Math.random() * 40)]);
    }

    var plot = $.plot($("#mws-dashboard-chart"),
           [ { data: PageViews, label: "Page Views", color: "#c75d7b"}, { data: Sales, label: "Sales", color: "#c5d52b" } ], {
               series: {
                   lines: { show: true },
                   points: { show: true }
               },
               grid: { hoverable: true, clickable: true },
               xaxis: { min: 1, max: 31 },
               yaxis: { min: 0, max: 200 }
             });
});
function editOrg(id) {
    console.log(id)
}
function addOrg() {
    layer.open({
        type: 2,
        title: '添加机构',
        // title:false,
        maxmin: true,
        shadeClose: false, //点击遮罩关闭层
        area : ['800px' , '600px'],
        content: 'addOrg.html'
    });
}