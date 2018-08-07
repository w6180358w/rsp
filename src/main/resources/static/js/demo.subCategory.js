$(function () {
    console.log("subCategory")
    $(".mws-datatable-fn").dataTable({
        sPaginationType: "full_numbers",
        sAjaxSource:"subCategory/queryAll",
        bServerSide: true,
        aoColumns:[
            {
                "fnRender": function ( oObj ) {
                    return "<input type=\"checkbox\" value="+oObj.aData[0]+"/>";
                },
                "aTargets" : [ 0 ]
            },
            { "name": "name","sTitle" : "用户名","aTargets" : [ 1 ] },
            { "limit": "limit","aTargets" : [ 2 ] },
            {
                "fnRender": function ( oObj ) {
                    return "<span class=\"ui-icon ui-icon-pencil\" onclick='editSubCategory("+oObj.aData[0]+")'></span>";
                },"aTargets" : [ 5 ]
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
function editSubCategory(id) {
    console.log(id)
}