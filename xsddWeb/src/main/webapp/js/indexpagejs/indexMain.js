/**
 * Created by Administrator on 2014/11/5.
 */
var _token;
var _getOrderCountData="/xsddWeb/getOrderCountData.do";
var _getTrenchData="/xsddWeb/getTrenchData.do";
$(document).ready(function(){
    queryIndexEbayList();//查询ebay账户
    queryPaypalList();
   // doContainer();
    getCharData(doContainer,_getTrenchData,{});//渠道分布
    getCharData(doKnobs,_getOrderCountData,{});//单量走势
    loadFeedBackReportData();
    loadItemReportData();

    showBanner_(false);
    /**滚动条滚动到顶部和底部的时候触发事件显示和隐藏banner栏*/
    $(window).scroll(function() {
        if($(document).scrollTop()>0){//如果不是在顶端，隐藏顶部栏
            hideBanner_();
        };
        if($(document).scrollTop() <= 0){//如果到顶端，显示顶部栏
            showBanner_(true);
        }
    });
});

/**处理container容器的初始化方法*/
function doContainer(xzb,r){
    var h = new Highcharts.Chart({
        chart: {
            renderTo:"container",
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
            //events:{load:function(){alert(1)}}
        },
        title: {
            //text: '渠道分布'
            text: ''
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y:.1f}</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: r,
        credits: {
            enabled : false, //设置false就不会显示右下角的官网链接
//右下角连接的显示位置
            position:{ align: 'right',x: -8, verticalAlign: 'bottom',y: -390 },
//右下角链接的地址href:'<%=basePath%>shop/newOrder/orderPre/orderSearch4HighCharts.do?type=1',
            text:'区域图表',//右下角连接的名字
            style : {cursor:'pointer',color:'#909090',fontSize:'20px'}
        }
    });

    //alert(h.series)
    //getCharData(h,"/xsddWeb/getTrenchData.do",{});
}

/**获取图表数据*/
function getCharData(obj,url,data){
    if(obj==null || url ==null){return;}
    if(data==null){data={}}
    $().invoke(
        url,
        data,
        [function(m,r){
            //console.log(r.length)
            //obj.series[0].name=(r[0].name);
            //obj.series[0].setData(r[0].data);
            obj(m,r);
        },
        function(m,r){
            alert(r);
        }]
    );
}




/**处理单量走势的初始化*/
function doKnobs(xzb,r){
    var h = new Highcharts.Chart({
        chart: {
            renderTo:"statsChart",
            type:"line"
            //events:{load:function(){alert(1)}}
        },
        title: {
            //text: '单量走势'
            text: ''
        },
        subtitle: {
            text: '..'
        },
        tooltip: {
            //pointFormat: '{series.name}: <b>{point.y:.1f}</b>'
            valueSuffix: '单'
        },
        /*legend: {
            layout: 'vertical',
            //align: 'rigth',
            verticalAlign: 'middle',
            borderWidth: 0
        },*/
        xAxis: {
            //categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
            categories: eval(xzb)
        },
        yAxis: {
            title: {
                text: '走势图'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        series: r,
        credits: {
            enabled : false, //设置false就不会显示右下角的官网链接
//右下角连接的显示位置
            position:{ align: 'right',x: -8, verticalAlign: 'bottom',y: -390 },
            text:'区域图表',//右下角连接的名字
            style : {cursor:'pointer',color:'#909090',fontSize:'20px'}
        }
    });

    //alert(h.series)
    //getCharData(h,"/xsddWeb/getOrderCountData.do",{});




    /*// jQuery Knobs
    $(".knob").knob();
    // jQuery UI Sliders
    $(".slider-sample1").slider({
        value: 100,
        min: 1,
        max: 500
    });
    $(".slider-sample2").slider({
        range: "min",
        value: 130,
        min: 1,
        max: 500
    });
    $(".slider-sample3").slider({
        range: true,
        min: 0,
        max: 500,
        values: [ 40, 170 ]
    });



    // jQuery Flot Chart
    var visits = [[1, 50], [2, 40], [3, 45], [4, 23],[5, 55],[6, 65],[7, 61],[8, 70],[9, 65],[10, 75],[11, 57],[12, 59]];
    var visitors = [[1, 25], [2, 50], [3, 23], [4, 48],[5, 38],[6, 40],[7, 47],[8, 55],[9, 43],[10,50],[11,47],[12, 39]];

    var plot = $.plot($("#statsChart"),
        [ { data: visits, label: "Signups"},
            { data: visitors, label: "Visits" }], {
            series: {
                lines: { show: true,
                    lineWidth: 1,
                    fill: true,
                    fillColor: { colors: [ { opacity: 0.1 }, { opacity: 0.13 } ] }
                },
                points: { show: true,
                    lineWidth: 2,
                    radius: 3
                },
                shadowSize: 0,
                stack: true
            },
            grid: { hoverable: true,
                clickable: true,
                tickColor: "#f9f9f9",
                borderWidth: 0
            },
            legend: {
                // show: false
                labelBoxBorderColor: "#fff"
            },
            colors: ["#a7b5c5", "#30a0eb"],
            xaxis: {
                ticks: [[1, "JAN"], [2, "FEB"], [3, "MAR"], [4,"APR"], [5,"MAY"], [6,"JUN"],
                    [7,"JUL"], [8,"AUG"], [9,"SEP"], [10,"OCT"], [11,"NOV"], [12,"DEC"]],
                font: {
                    size: 12,
                    family: "Open Sans, Arial",
                    variant: "small-caps",
                    color: "#697695"
                }
            },
            yaxis: {
                ticks:3,
                tickDecimals: 0,
                font: {size:12, color: "#9da3a9"}
            }
        });



    var previousPoint = null;
    $("#statsChart").bind("plothover", function (event, pos, item) {
        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;

                $("#tooltip").remove();
                var x = item.datapoint[0].toFixed(0),
                    y = item.datapoint[1].toFixed(0);
                var month = item.series.xaxis.ticks[item.dataIndex].label;
                showTooltip(item.pageX, item.pageY,
                        item.series.label + " of " + month + ": " + y);
            }
        }
        else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });*/
}
/*
function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css( {
        position: 'absolute',
        display: 'none',
        top: y - 30,
        left: x - 50,
        color: "#fff",
        padding: '2px 5px',
        'border-radius': '6px',
        'background-color': '#000',
        opacity: 0.80
    }).appendTo("body").fadeIn(200);
}*/

/**获取ebay帐号列表*/
function queryIndexEbayList(){
    $("#ebay_indexdiv").initTable({
        url:"/xsddWeb/user/queryEbaysForCurrUser.do",
        columnData:[
            {title:"代码",name:"ebayNameCode",width:"8%",align:"left"},
            {title:"ebay账户",name:"ebayName",width:"8%",align:"left"},
            {title:"信用评价",name:"",width:"8%",align:"left"},
            {title:"密钥有效期",name:"op",width:"8%",align:"left",format:mCanUseDate},
            {title:"状态",name:"op",width:"8%",align:"left",format:makeStatus}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:true
    });
    refreshIndexEbayTable({});
}
/**刷新列表*/
function refreshIndexEbayTable(p){
    if(p==null){p={}}
    $("#ebay_indexdiv").selectDataAfterSetParm(p);
}
/**组装密钥有效期*/
function mCanUseDate(json){
    //var da=json['useTimeStart']+" 至 "+json['useTimeEnd'];
   // alert(json['useTimeEnd'])

    if(strIsDateOrTime(json['useTimeEnd'])=='idatetime'){
        return new Date(json['useTimeEnd']).format("yyyy-MM-dd")
    }
    return json['useTimeEnd']
    /*var t= new Date().format("yyyy-MM-dd");
    //var da=json['useTimeEnd'];
    return t;*/
}
/**状态*/
function makeStatus(json){
    var imgurlpr="/xsddWeb/img/";
    if(json.ebayStatus==1 || json.ebayStatus=='1'){
        imgurlpr+="new_yes.png";
    }else if(json.ebayStatus==0 || json.ebayStatus=='0'){
        imgurlpr+="new_no.png";
    }else{
        imgurlpr+="";
    }

    return "<img src='"+imgurlpr+"' />";
}

/**获取paypal帐号列表*/
function queryPaypalList(){
    $("#indexPayPal").initTable({
        url:"/xsddWeb/paypal/queryPaypalList.do",
        columnData:[
            {title:"paypal帐号",name:"paypalAccount",width:"8%",align:"left"},
            {title:"今天",name:"",width:"8%",align:"left"},
            {title:"昨天",name:"",width:"8%",align:"left"},
            {title:"本周",name:"",width:"8%",align:"left"},
            {title:"上周",name:"",width:"8%",align:"left"},
            {title:"本月",name:"",width:"8%",align:"left"},
            {title:"上月",name:"",width:"8%",align:"left"},
            {title:"状态",name:"op",width:"8%",align:"left",format:makePaypalStatus}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:true
    });
    refreshPayPalTable({});
}
/**状态*/
function makePaypalStatus(json){
    var imgurlpr="/xsddWeb/img/";
    if(json.status==1 || json.status=='1'){
        imgurlpr+="new_yes.png";
    }else if(json.status==0 || json.status=='0'){
        imgurlpr+="new_no.png";
    }else{
        imgurlpr+="";
    }
    return "<img src='"+imgurlpr+"' />";
}
/**刷新paypal列表*/
function refreshPayPalTable(p){
    if(p==null){p={}}
    $("#indexPayPal").selectDataAfterSetParm(p);
}


/**页面加载完成后执行的方法*/
var bbs_=true;
var bbc_=true;
function showBanner_(o){
    if(parent.document==document){return}
    $("#navbar",parent.document.body).show();
    $("#contentMaindiv",parent.document.body).css("top","58px");
    if(!o){
        $("#contentMaindiv",parent.document.body).css("height",(parseInt(parent.mainHei_)-60)+"px");

        return;
    }
    if(bbs_){
        var parentDivH = $("#contentMaindiv",parent.document.body).css("height").replace("px");
        $("#contentMaindiv",parent.document.body).css("height",(parseInt(parentDivH)-60)+"px");
        bbc_=true;
        bbs_=false;
    }
}
function hideBanner_(){
    if(parent.document==document){return;}
    $("#navbar",parent.document.body).hide();
    $("#contentMaindiv",parent.document.body).css("top","0px");

    if(bbc_){
        var parentDivH = $("#contentMaindiv",parent.document.body).css("height").replace("px");
        $("#contentMaindiv",parent.document.body).css("height",(parseInt(parentDivH)+60)+"px");
        bbc_=false;
        bbs_=true;
    }

}