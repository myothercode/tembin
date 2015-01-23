/**
 * Created by Administrator on 2014/11/19.
 */

function getOrderList1(){
    $("#OrderGetOrdersListTable1").initTable({
        url:path + "/order/ajax/loadOrdersList.do?",
        columnData:[
            {title:"",name:"ch",width:"1%",align:"top",format:makeOption5},
            {title:"图片/交易号",name:"orderid",width:"10%",align:"left",format:makeOption2},
            {title:"买家/SKU/承运商/追踪号",name:"orderid",width:"20%",align:"left",format:makeOption3},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc3');\"><font style='color: #0000ff'>站点</font></a>",name:"itemSite",width:"3%",align:"left",format:makeOption20},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc');\"><font style='color: #0000ff'>售价</font></a>",name:"itemUrl",width:"6%",align:"left",format:makeOption6},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'desc1');\"><font style='color: #0000ff'>售出日期</font></a>",name:"createdtime",width:"5%",align:"left"},
            {title:"更新时间",name:"lastmodifiedtime",width:"5%",align:"left"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc2');\"><font style='color: #0000ff'>数量</font></a>",name:"quantitypurchased",width:"3%",align:"center"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc4');\"><font style='color: #0000ff'>EBAY账号</font></a>",name:"selleruserid",width:"8%",align:"left"},
            {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
            {title:"操作&nbsp;&nbsp;&nbsp;&nbsp;",name:"option1",width:"8%",align:"center",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTable1();

}
function orderByTransactionPrice(obj,orderby){
    var table=$(obj).parent().parent().parent().parent().parent();
    var tableid=$(table).attr("id");
    var i=parseInt(tableid.substring(tableid.length-1));
    var url=path + "/order/ajax/loadOrdersList.do?";
    if(i==1){
        url+="orderby="+orderby;
    }
    if(i==2){
        url+="status=Incomplete&orderby="+orderby;
    }
    if(i==3){
        url+="status=notAllComplete&orderby="+orderby;
    }
    if(i==4){
        url+="status=Complete&orderby="+orderby;
    }
    if(i==5){
        url+="status=Cancelled&orderby="+orderby;
    }
    if(i>5){
        var folderId=$("#menu"+i).attr("name2");
        url+="folderId="+folderId+"&orderby="+orderby
    }
    var orderby1=orderby;
    var orderby2=orderby;
    var orderby3=orderby;
    var orderby4=orderby;
    var orderby5=orderby;
    if(orderby=="asc"){
        orderby1="desc";
        orderby2="asc1";
        orderby3="asc2";
        orderby4="asc3";
        orderby5="asc4"
    }else if(orderby=="desc"){
        orderby1="asc";
        orderby2="desc1";
        orderby3="desc2";
        orderby4="desc3";
        orderby5="desc4";
    }else if(orderby=="asc1"){
        orderby2="desc1";
        orderby1="asc";
        orderby3="asc2";
        orderby4="asc3";
        orderby5="asc4";
    }else if(orderby=="desc1"){
        orderby2="asc1";
        orderby1="desc";
        orderby3="desc2";
        orderby4="desc3";
        orderby5="desc4";
    }else if(orderby=="asc2"){
        orderby3="desc2";
        orderby1="asc";
        orderby2="asc1";
        orderby4="asc3";
        orderby5="asc4";
    }else if(orderby=="desc2"){
        orderby3="asc2";
        orderby1="desc";
        orderby2="desc1";
        orderby4="desc3";
        orderby5="desc4";
    }else if(orderby=="asc3"){
        orderby4="desc3";
        orderby1="asc";
        orderby2="asc1";
        orderby3="asc2";
        orderby5="asc4";
    }else if(orderby=="desc3"){
        orderby4="asc3";
        orderby1="desc";
        orderby2="desc1";
        orderby3="desc2";
        orderby5="desc4";
    }
    else if(orderby=="asc4"){
        orderby5="desc4";
        orderby1="asc";
        orderby2="asc1";
        orderby3="asc2";
        orderby4="asc3";
    }else if(orderby=="desc4"){
        orderby5="asc4";
        orderby1="desc";
        orderby2="desc1";
        orderby3="desc2";
        orderby4="desc3";
    }

    $("#"+tableid).initTable({
        url:url,
        columnData:[
            {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
            {title:"图片/交易号",name:"orderid",width:"10%",align:"left",format:makeOption2},
            {title:"买家/SKU/承运商/追踪号",name:"orderid",width:"13%",align:"left",format:makeOption3},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'"+orderby4+"');\"><font style='color: #0000ff'>站点</font></a>",name:"itemSite",width:"8%",align:"left",format:makeOption20},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'"+orderby1+"');\"><font style='color: #0000ff'>售价</font></a>",name:"itemUrl",width:"8%",align:"left",format:makeOption6},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'"+orderby2+"');\"><font style='color: #0000ff'>售出日期</font></a>",name:"createdtime",width:"8%",align:"left"},
            {title:"更新时间",name:"lastmodifiedtime",width:"5%",align:"left"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'"+orderby3+"');\"><font style='color: #0000ff'>数量</font></a>",name:"quantitypurchased",width:"8%",align:"center"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'"+orderby5+"');\"><font style='color: #0000ff'>EBAY账号</font></a>",name:"selleruserid",width:"8%",align:"left"},
            {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
            {title:"操作&nbsp;&nbsp;&nbsp;&nbsp;",name:"option1",width:"8%",align:"center",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    $("#"+tableid).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function refreshTable1(){
    $("#OrderGetOrdersListTable1").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function refreshTable2(){
    $("#OrderGetOrdersListTable2").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function refreshTable3(){
    $("#OrderGetOrdersListTable3").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function refreshTable4(){
    $("#OrderGetOrdersListTable4").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function refreshTable15(){
    $("#OrderGetOrdersListTable5").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function refreshTable7(table){
    $(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}



/**查看消息*/
function makeOption1(json){
var hs="";
hs="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this,'"+json.id+"'); value='1' doaction=\"readed\" >查看详情</li>";
hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this,'"+json.id+"'); value='2' doaction=\"look\" >上传跟踪</li>";
hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this,'"+json.id+"'); value='3' doaction=\"look\" >发送消息</li>";
hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this,'"+json.id+"'); value='4' doaction=\"look\" >退款功能</li>";
hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this,'"+json.id+"'); value='5' doaction=\"look\" >添加备注</li>";
hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this,'"+json.id+"'); value='6' doaction=\"look\" >删除</li>";
hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this,'"+json.id+"'); value='7' doaction=\"look\" >查看日志</li>";
var pp={"liString":hs};
return getULSelect(pp);
/* return htm;*/
}
function selectOperation(orderid,transactionid,selleruserid,obj,id){
    var value=$(obj).attr("value");
    if(value=="1"){
        viewOrder(orderid,transactionid,selleruserid);
    }
    if(value=="2"){
        modifyOrderStatus(transactionid,selleruserid);
    }
    if(value=="3"){
        sendMessage(transactionid,selleruserid);
    }
    if(value=="4"){
        issueRefund(transactionid,selleruserid);
    }
    if(value=="5"){
        addComment1(id);
    }
    if(value=="6"){
        deleteOrder(id,obj);
    }
    if(value=="7"){
        viewSystemlog();
    }

}

function viewSystemlog(){
    var url=path+'/order/viewSystemlog.do?';
    OrderGetOrders=openMyDialog({title: '查看日志',
        content: 'url:'+url,
        icon: 'succeed',
        width:1200,
        height:500,
        lock:true
    });
}
function deleteOrder(id,obj){
    var url = path + "/order/deleteOrder.do?id="+id;
    $().invoke(url, null,
        [function (m, r) {
            alert(r);
            var div=$(obj).parent().parent().parent().parent().parent().parent().parent().parent();
            var table=$(div).attr("id");
            $("#"+table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
            Base.token();
        },
            function (m, r) {
                alert(r);
                Base.token();
            }]
    );
}
function sendMessage(transactionid,selleruserid){
    var url=path+'/order/initOrdersSendMessage.do?transactionid='+transactionid+'&selleruserid='+selleruserid;
    viewsendMessage=openMyDialog({title: '发送消息',
        content: 'url:'+url,
        icon: 'succeed',
        width:800,
        height:500,
        lock:true
    });
}
function issueRefund(transactionid,selleruserid){
    var url=path+'/order/initIssueRefund.do?transactionid='+transactionid+'&selleruserid='+selleruserid;
    viewsendMessage=openMyDialog({title: '退款选项',
        content: 'url:'+url,
        icon: 'succeed',
        width:600,
        lock:true
    });
}

function makeOption2(json){
    var   htm1="<img onerror='nofind();' src='"+itemListIconUrl_+json.itemid+".jpg' style='width: 50px;height:50px;' /><br>";
    var htm="<font style='color:gray'>"+json.transactionid+"<font>";
    return htm1+htm;
}
function makeOption3(json){
    var imgurl=path+"/img/";
    var imgurl1=path+"/img/";
    var vas=json.variationspecificsMap;
    var con="";
    for(var i=0;i<vas.length;i++){
        con=con+vas[i]+"<br/>"
    }
    if(json.message==null||json.message==""){
        imgurl1=imgurl1+"f.png";
    }else{
        imgurl1=imgurl1+"add.png";
    }
    var htm="<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">"+json.buyeruserid+"  </font> <font style='color: #808080'>( "+json.buyeremail+")</font></span>";
     htm+="<span style=\"width:100%; float:left\"><font color=\"#5F93D7\"><a href='"+json.itemUrl+"' target=\"1\">"+json.title+"</a></font><br><font style='float: left;color: #808080'>("+json.itemid+")</font>";
    if(json.shippingcarrierused&&json.shippingcarrierused!=""){
        htm+="<span class=\"newbgspan_3\" style='margin-top:2px;margin-left:8px;border-radius: 3px;height: 12px;line-height:12px;'>"+json.shippingcarrierused+"</span>&nbsp;<span class=\"newbgspan_3\" style='margin-top:2px;border-radius: 3px;height: 12px;line-height:12px;'>"+json.shipmenttrackingnumber+"</span>";
    }
    htm+="</span><span style=\"width:100%; float:left; color:#8BB51B\">"+json.sku+"</span>" +
        "<span style=\"width:100%; float:left\"><font style='color: #ff7e00'>" +con +
        "</span>";
    if(json.feedbackMessage!=""){
        htm+="<span style='width:100%; float:left'><font color='#5F93D7' style='margin-bottom: 100px;'>B：</font><span style='margin-bottom: 20px;'><img  src='"+path+"/img/f.png' width='14' height='14'></span><font style='color: #000000'>&nbsp;"+json.feedbackMessage+"</font></span>";
    }
    if(json.feedbackMessage1!=""){
        htm+="<span style='width:100%; float:left'><font color='#5F93D7'>S：</font><span><img src='"+path+"/img/add.png' width='12' height='12'></span><font style='color: #000000'>&nbsp;"+json.feedbackMessage1+"</font></span>";
    }
    if(json.comment&&json.comment!=""){
        htm+="<br/><span id='commentId' class=\"newdf\" style='border-radius: 3px;' title=\""+json.comment+"\">备注："+json.comment+"</span>";
    }
    return htm;
}
function makeOption4(json){
    var imgurl1=path+"/img/";
    var htm="";
    if(json.orderstatus=='Cancelled'||json.orderstatus=='CancelPending'){
        htm=htm+"<img src=\""+imgurl1+"cancelled.png\" title=\""+json.cancelstatus+"\" >";
        return htm;
    }
    if(json.flagNotAllComplete){
        htm=htm+"<img src=\""+imgurl1+"money_no.png\" title=\"未结清\" >";
        return htm;
    }
    if(json.status=='Incomplete'){
        htm="<img title=\"未付款\" src=\""+imgurl1+"money.gif \" onmousemove='showInformation();'>"/*"<img src=\""+imgurl1+"money.gif\">"*/;
        /*"<img onmousemove='showInformation();'>"*/
        return htm;
    }
    if(json.status=='Complete'){
        if(json.feedbackMessage!=""){
            htm="<img title=\"签收\" src=\""+imgurl1+"a4.png\" >"
        }else if(json.shipmenttrackingnumbe!=""&&json.shippingcarrierused!=""){
            htm="<img title=\"发货\" src=\""+imgurl1+"a2.png\" >"
        }else{
            htm="<img title=\"已付款\" src=\""+imgurl1+"a1.png\" >";
        }
    }
    return htm;
}
function makeOption5(json){
    var htm="<input type='checkbox' id='checkbox' name='checkbox' value1='"+json.id+"'  value='"+json.orderid+"' />";
    return htm;
}
function makeOption6(json){
    var f=parseFloat(json.transactionprice);
    var f1= f.toFixed(2);
    var htm=f1+" USD";
    return htm;
}
function makeOption20(json){
    var htm="<img src='"+json.itemSite+"'title='"+json.siteName+"' >";
    return htm;
}
function ebayurl(url){
    console.debug(url);
    window.location.href=url;
}
function submitCommit(){
    $("#OrderGetOrdersListTable").initTable({
        url:path + "/order/ajax/loadOrdersList.do?",
        columnData:[
            {title:"",name:"ch",width:"1%",align:"top",format:makeOption5},
            {title:"图片/交易号",name:"orderid",width:"10%",align:"left",format:makeOption2},
            {title:"买家/SKU/承运商/追踪号",name:"orderid",width:"20%",align:"left",format:makeOption3},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc3');\"><font style='color: #0000ff'>站点</font></a>",name:"itemSite",width:"3%",align:"left",format:makeOption20},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc');\"><font style='color: #0000ff'>售价</font></a>",name:"itemUrl",width:"6%",align:"left",format:makeOption6},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'desc1');\"><font style='color: #0000ff'>售出日期</font></a>",name:"createdtime",width:"5%",align:"left"},
            {title:"更新时间",name:"lastmodifiedtime",width:"5%",align:"left"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc2');\"><font style='color: #0000ff'>数量</font></a>",name:"quantitypurchased",width:"3%",align:"center"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc4');\"><font style='color: #0000ff'>EBAY账号</font></a>",name:"selleruserid",width:"8%",align:"left"},
            {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
            {title:"操作&nbsp;&nbsp;&nbsp;&nbsp;",name:"option1",width:"8%",align:"center",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTable();
}
function viewOrder(id,transactionid,selleruserid){
    var url=path+'/order/viewOrderGetOrders.do?orderid='+id+'&transactionid='+transactionid+'&selleruserid='+selleruserid;
    OrderGetOrders=openMyDialog({title: '查看订单详情',
        content: 'url:'+url,
        icon: 'succeed',
        width:1200,
        height:850,
        lock:true
    });
}
function modifyOrderStatus(id,selleruserid){
    var url=path+'/order/modifyOrderStatus.do?transactionid='+id+'&selleruserid='+selleruserid;
    OrderGetOrders=openMyDialog({title: '上传跟踪号',
        content: 'url:'+url,
        icon: 'succeed',
        width:700,
        lock:true
    });
}

function getBindParm(){
    var url=path+"/order/GetOrder.do";
    OrderGetOrders=openMyDialog({title: '同步订单',
        content: 'url:'+url,
        icon: 'succeed',
        width:600,
        lock:true
    });
}
function query1(){
    var daysQ=$("#daysQ1").val();
    var countryQ=$("#countryQ1").val();
    var typeQ=$("#typeQ1").val();
    var itemType=$("#itemType1").val();
    var content=$("#content1").val();
    var table="#OrderGetOrdersListTable1";
    var starttime=$("#starttime1").val();
    var endtime=$("#endtime1").val();
    var statusQ=$("#statusQ1").val();
    var accountQ=$("#accountQ1").val();
    refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,null,starttime,endtime,statusQ,accountQ);
}
function query2(){
    var daysQ=$("#daysQ2").val();
    var countryQ=$("#countryQ2").val();
    var typeQ=$("#typeQ2").val();
    var itemType=$("#itemType2").val();
    var content=$("#content2").val();
    var table="#OrderGetOrdersListTable2";
    var status="Incomplete";
    var starttime=$("#starttime2").val();
    var endtime=$("#endtime2").val();
    var statusQ=$("#statusQ2").val();
    var accountQ=$("#accountQ2").val();
    refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,status,starttime,endtime,statusQ,accountQ);
}
function query3(){
    var daysQ=$("#daysQ3").val();
    var countryQ=$("#countryQ3").val();
    var typeQ=$("#typeQ3").val();
    var itemType=$("#itemType3").val();
    var content=$("#content3").val();
    var table="#OrderGetOrdersListTable3";
    var starttime=$("#starttime3").val();
    var endtime=$("#endtime3").val();
    var statusQ=$("#statusQ3").val();
    var accountQ=$("#accountQ3").val();
    refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,"notAllComplete",starttime,endtime,statusQ,accountQ);
}
function query4(){
    var daysQ=$("#daysQ4").val();
    var countryQ=$("#countryQ4").val();
    var typeQ=$("#typeQ4").val();
    var itemType=$("#itemType4").val();
    var content=$("#content4").val();
    var table="#OrderGetOrdersListTable4";
    var status="Complete";
    var starttime=$("#starttime4").val();
    var endtime=$("#endtime4").val();
    var statusQ=$("#statusQ4").val();
    var accountQ=$("#accountQ4").val();
    refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,status,starttime,endtime,statusQ,accountQ);
}
function query5(){
    var daysQ=$("#daysQ5").val();
    var countryQ=$("#countryQ5").val();
    var typeQ=$("#typeQ5").val();
    var itemType=$("#itemType5").val();
    var content=$("#content5").val();
    var table="#OrderGetOrdersListTable5";
    var status="Cancelled";
    var starttime=$("#starttime5").val();
    var endtime=$("#endtime5").val();
    var statusQ=$("#statusQ5").val();
    var accountQ=$("#accountQ5").val();
    refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,status,starttime,endtime,statusQ,accountQ);
}
function refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,status,starttime,endtime,statusQ,accountQ){
    $(table).initTable({
        url:path + "/order/ajax/loadOrdersList.do?",
        columnData:[
            {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
            {title:"图片/交易号",name:"orderid",width:"10%",align:"left",format:makeOption2},
            {title:"买家/SKU/承运商/追踪号",name:"orderid",width:"20%",align:"left",format:makeOption3},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc3');\"><font style='color: #0000ff'>站点</font></a>",name:"itemSite",width:"3%",align:"left",format:makeOption20},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc');\"><font style='color: #0000ff'>售价</font></a>",name:"itemUrl",width:"6%",align:"left",format:makeOption6},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'desc1');\"><font style='color: #0000ff'>售出日期</font></a>",name:"createdtime",width:"5%",align:"left"},
            {title:"更新时间",name:"lastmodifiedtime",width:"5%",align:"left"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc2');\"><font style='color: #0000ff'>数量</font></a>",name:"quantitypurchased",width:"3%",align:"center"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc4');\"><font style='color: #0000ff'>EBAY账号</font></a>",name:"selleruserid",width:"8%",align:"left"},
            {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
            {title:"操作&nbsp;&nbsp;&nbsp;&nbsp;",name:"option1",width:"8%",align:"center",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    $(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"countryQ":countryQ,"typeQ":typeQ,"daysQ":daysQ,"itemType":itemType,"content":content,"status":status,"starttime1":starttime,"endtime1":endtime,"statusQ":statusQ,"accountQ":accountQ});
}
function queryTime(n,flag,day){
    var scop="span[scop=queryTime"+n+"]";
    var time=$(scop);
    for(var i=0;i<time.length;i++){
        if(i==(flag-1)){
            $(time[i]).attr("class","newusa_ici");
            /* <input type="hidden" id="countryQ"/>
             <input type="hidden" id="typeQ"/>
             <input type="hidden" id="daysQ"/>*/
            $("#daysQ"+n).val(day);
            $("#starttime"+n).val("");
            $("#endtime"+n).val("");
        }else{
            $(time[i]).attr("class","newusa_ici_1");
        }
    }
    if(flag!=6){
        document.getElementById("time"+n).innerHTML="";
        if(n==1){
            query1();
        }
        if(n==2){
            query2();
        }
        if(n==3){
            query3();
        }
        if(n==4){
            query4();
        }
        if(n==5){
            query5();
        }
    }
    if(n>5){
        if(flag!=6) {
            document.getElementById("time"+n).innerHTML="";
            var table = $("#OrderGetOrdersListTable" + n);
            var daysQ = $("#daysQ" + n).val();
            var countryQ = $("#countryQ" + n).val();
            var typeQ = $("#typeQ" + n).val();
            var itemType = $("#itemType" + n).val();
            var content = $("#content" + n).val();
            var folderId = $("#menu" + n).attr("name2");
            var starttime1 = $("#starttime" + n).val();
            var endtime1 = $("#endtime" + n).val();
            var statusQ = $("#statusQ" + n).val();
            var accountQ=$("#accountQ"+n).val();
            refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime1,endtime1,statusQ,accountQ);
        }
    }
}
function queryStatus(n,flag,day){
    var scop="span[scop=queryStatus"+n+"]";
    var time=$(scop);
    for(var i=0;i<time.length;i++){
        if(i==(flag-1)){
            $(time[i]).attr("class","newusa_ici");
            /* <input type="hidden" id="countryQ"/>
             <input type="hidden" id="typeQ"/>
             <input type="hidden" id="daysQ"/>*/
            $("#statusQ"+n).val(day);
            $("#starttime"+n).val("");
            $("#endtime"+n).val("");
        }else{
            $(time[i]).attr("class","newusa_ici_1");
        }
    }
    if(n==1){
        query1();
    }
    if(n==2){
        query2();
    }
    if(n==3){
        query3();
    }
    if(n==4){
        query4();
    }
    if(n==5){
        query5();
    }
    if(n>5){
        var table=$("#OrderGetOrdersListTable"+n);
        var daysQ=$("#daysQ"+n).val();
        var countryQ=$("#countryQ"+n).val();
        var typeQ=$("#typeQ"+n).val();
        var itemType=$("#itemType"+n).val();
        var content=$("#content"+n).val();
        var folderId=$("#menu"+n).attr("name2");
        var starttime1=$("#starttime"+n).val();
        var endtime1=$("#endtime"+n).val();
        var statusQ=$("#statusQ"+n).val();
        var accountQ=$("#accountQ"+n).val();
        refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime1,endtime1,statusQ,accountQ);
    }
}

function queryAttr(n,flag,day){
    var scop="span[scop=queryAttr"+n+"]";
    var time=$(scop);
    for(var i=0;i<time.length;i++){
        if(i==(flag-1)){
            $(time[i]).attr("class","newusa_ici");
            /* <input type="hidden" id="countryQ"/>
             <input type="hidden" id="typeQ"/>
             <input type="hidden" id="daysQ"/>*/
            $("#typeQ"+n).val(day);
        }else{
            $(time[i]).attr("class","newusa_ici_1");
        }
    }
    if(n==1){
        query1();
    }
    if(n==2){
        query2();
    }
    if(n==3){
        query3();
    }
    if(n==4){
        query4();
    }
    if(n==5){
        query5();
    }
    if(n>5){
        var table=$("#OrderGetOrdersListTable"+n);
        var daysQ=$("#daysQ"+n).val();
        var countryQ=$("#countryQ"+n).val();
        var typeQ=$("#typeQ"+n).val();
        var itemType=$("#itemType"+n).val();
        var content=$("#content"+n).val();
        var folderId=$("#menu"+n).attr("name2");
        var starttime1=$("#starttime"+n).val();
        var endtime1=$("#endtime"+n).val();
        var statusQ=$("#statusQ"+n).val();
        var accountQ=$("#accountQ"+n).val();
        refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime1,endtime1,statusQ,accountQ);
    }
}
function queryCountry(n,flag,day){
    var scop="span[scop=queryCountry"+n+"]";
    var time=$(scop);
    for(var i=0;i<time.length;i++){
        if(i==(flag-1)){
            $(time[i]).attr("class","newusa_ici");
            /* <input type="hidden" id="countryQ"/>
             <input type="hidden" id="typeQ"/>
             <input type="hidden" id="daysQ"/>*/
            $("#countryQ"+n).val(day);
        }else{
            $(time[i]).attr("class","newusa_ici_1");
        }
    }
    if(n==1){
        query1();
    }
    if(n==2){
        query2();
    }
    if(n==3){
        query3();
    }
    if(n==4){
        query4();
    }
    if(n==5){
        query5();
    }
    if(n>5){
        var table=$("#OrderGetOrdersListTable"+n);
        var daysQ=$("#daysQ"+n).val();
        var countryQ=$("#countryQ"+n).val();
        var typeQ=$("#typeQ"+n).val();
        var itemType=$("#itemType"+n).val();
        var content=$("#content"+n).val();
        var folderId=$("#menu"+n).attr("name2");
        var starttime1=$("#starttime"+n).val();
        var endtime1=$("#endtime"+n).val();
        var statusQ=$("#statusQ"+n).val();
        var accountQ=$("#accountQ"+n).val();
        refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime1,endtime1,statusQ,accountQ);
    }
}
function selectAccount1(n,flag,day){
    var accounts=$("span[scop=account"+n+"]");
    for(var i=0;i<accounts.length;i++){
        if(i==(flag-1)){
            $(accounts[i]).attr("class","newusa_ici");
            $("#accountQ"+n).val(day);
        }else{
            $(accounts[i]).attr("class","newusa_ici_1");
        }
    }
    if(n==1){
        query1();
    }
    if(n==2){
        query2();
    }
    if(n==3){
        query3();
    }
    if(n==4){
        query4();
    }
    if(n==5){
        query5();
    }
    if(n>5){
        var table=$("#OrderGetOrdersListTable"+n);
        var daysQ=$("#daysQ"+n).val();
        var countryQ=$("#countryQ"+n).val();
        var typeQ=$("#typeQ"+n).val();
        var itemType=$("#itemType"+n).val();
        var content=$("#content"+n).val();
        var folderId=$("#menu"+n).attr("name2");
        var starttime1=$("#starttime"+n).val();
        var endtime1=$("#endtime"+n).val();
        var statusQ=$("#statusQ"+n).val();
        var accountQ=$("#accountQ"+n).val();
        refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime1,endtime1,statusQ,accountQ);
    }
}
function downloadOrders(n){
    var status=null;
    if(n==2){
        status="Incomplete"
    }
    if(n==4){
        status="Complete";
    }
    var url=path+"/order/downloadOrders.do?status="+status;
    window.open(url);
}
function addTabRemark(){
    var url=path+"/order/selectTabRemark.do?folderType=orderFolder";
    OrderGetOrders=openMyDialog({title: '选择文件夹',
        content: 'url:'+url,
        icon: 'succeed',
        width:500,
        lock:true
    });
}
function addComment(i){
    var table="#OrderGetOrdersListTable"+i;
    var checkboxs=$(table).find("input[name=checkbox]:checked");
    if(checkboxs&&checkboxs.length==1){
        var url=path+"/order/addComment.do?id="+checkboxs.attr("value1");
        OrderGetOrders=openMyDialog({title: '添加备注',
            content: 'url:'+url,
            icon: 'succeed',
            width:600,
            lock:true
        });
    }else{
        alert("请选择一个需要添加备注的订单");
    }
}
function addComment1(id){
    var url=path+"/order/addComment.do?id="+id;
    OrderGetOrders=openMyDialog({title: '添加备注',
        content: 'url:'+url,
        icon: 'succeed',
        width:600,
        lock:true
    });
}
function selectAllCheck(i,obj){
    var table="#OrderGetOrdersListTable"+i;
    var checkboxs=$(table).find("input[name=checkbox]");
    if(obj.checked){
        for(var j=0;j<checkboxs.length;j++){
            checkboxs[j].checked=true;
        }
    }else{
        for(var j=0;j<checkboxs.length;j++){
            checkboxs[j].checked=false;
        }
    }
}
function moveFolder(i){
    var table="#OrderGetOrdersListTable"+i;
    var checkboxs=$(table).find("input[name=checkbox]:checked");
    if(checkboxs&&checkboxs.length>0){
        var date="";
        for(var j=0;j<checkboxs.length;j++){
            if(j==0){
                date=date+"orderid["+j+"]="+$(checkboxs[j]).val();
            }else{
                date=date+"&orderid["+j+"]="+$(checkboxs[j]).val();
            }
        }
        var url=path+"/order/moveFolder.do?"+date+"&table="+i+"&table1="+$("count").val();
        OrderGetOrders=openMyDialog({title: '移动订单',
            content: 'url:'+url,
            icon: 'succeed',
            width:800,
            lock:true
        });
    }else{
        alert("请选择需要移动的订单");
    }
}
function sycOneOrder(){
    var url=path+"/order/sycOneOrder.do";
    OrderGetOrders=openMyDialog({title: '同步单个订单',
        content: 'url:'+url,
        icon: 'succeed',
        width:600,
        height:200,
        lock:true
    });
}
function modifyOrderNums(i){
    var table="#OrderGetOrdersListTable"+i;
    var checkboxs=$(table).find("input[name=checkbox]:checked");
    if(checkboxs&&checkboxs.length>0){
        var date="";
        for(var j=0;j<checkboxs.length;j++){
            if(j==0){
                date=date+"id["+j+"]="+$(checkboxs[j]).attr("value1");
            }else{
                date=date+"&id["+j+"]="+$(checkboxs[j]).attr("value1");
            }
        }
        var url=path+"/order/modifyOrderNums.do?"+date;
        OrderGetOrders=openMyDialog({title: '移动订单',
            content: 'url:'+url,
            icon: 'succeed',
            width:800,
            lock:true
        });
    }else{
        alert("请选择需要上传跟踪号的订单");
    }
}
function cleanInput(){
    var inputs=$("input[class=key_2]");
    for(var i=0;i<inputs.length;i++){
        document.getElementById("content"+(i+1)).value="";
    }
}
function selectCountrys(){
    var dts=$("#tabRemark").find("dt");
    var countryNames=$("#countryNames").val();
    var num=dts.length;
    var url=path+"/order/selectCountrys.do?num="+num+"&countryNames="+countryNames;
    OrderGetOrders=openMyDialog({title: '选择国家',
        content: 'url:'+url,
        icon: 'succeed',
        width:1050,
        height:600,
        lock:true
    });
}
function refleshTabRemark(folderType){
    var url=path+"/order/refleshTabRemark.do?folderType="+folderType;
    $().invoke(url,null,
        [function(m,r){
            var div=document.getElementById("tabRemark");
            var remarks=$(div).find("dt[scop=tabRemark]");
            for(var i=0;i<remarks.length;i++){
                $(remarks[i]).remove();
            }
            var htm="";
            for(var i=0;i< r.length;i++){
                htm+="<dt scop=\"tabRemark\" id=\"menu"+(i+5)+"\" name1='"+(i+5)+"' name2='"+r[i].id+"' name='"+r[i].configName+"' class=\"new_tab_2\" onclick=\"setTab1('menu',"+(i+5)+","+ (r.length+4)+")\">"+r[i].configName+"</dt>";
            }
            $(div).append(htm);
            Base.token;
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );
}
function addstartTimeAndEndTime(obj,i){
    var time=$("#time"+i);
    var query="query"+i+"();";
    var t=$("input[id=starttime"+i+"]");
    if(t.length==0){
        var span="<span style=\"float: left;color: #5F93D7;\">从</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9\" id=\"starttime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>" +
            "<span style=\"float: left;color: #5F93D7;\">到</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9\" id=\"endtime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>"
        +"<input value1='input"+i+"' style='border:0;background-color:#ffffff;float: left;color: #5F93D7;height: 26px;' value='确定' onclick='"+query+"' type='button'>";
        $(time).append(span);
    }
    if(i>5){
        var input=$("input[value1=input"+i+"]");
        $(input).removeAttr("onclick");
        $(input[0]).click(function(){
            var table="#OrderGetOrdersListTable"+i;
            var daysQ=$("#daysQ"+(i)).val();
            var countryQ=$("#countryQ"+(i)).val();
            var typeQ=$("#typeQ"+(i)).val();
            var itemType=$("#itemType"+i).val();
            var content=$("#content"+i).val();
            var folderId=$("#menu"+i).attr("name2");
            var starttime1=$("#starttime"+(i)).val();
            var endtime1=$("#endtime"+(i)).val();
            var statusQ=$("#statusQ"+(i)).val();
            var accountQ=$("#accountQ"+(i)).val();
            refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime1,endtime1,statusQ,accountQ);
        });
    }
}


//新增文件夹---------------------------------------------------------------
function setTab1(name,cursel,n){
    for(var i=1;i<=n;i++){
        var na="#"+name+i;
        $(na).attr("onclick","setTab(\"menu\","+i+","+n+")");
        if(i>5&&i==cursel){
            var Contentbox=document.getElementById("ContentboxDiv");
            /* var lastdiv=document.getElementById("con_menu_2").cloneNode();*/
            var lastdiv=$("#con_menu_4").clone();
            $(lastdiv).attr("id","con_menu_"+i);
            var lis=$(lastdiv).find("li[name=selectCountrys]");
            $(lis).attr("name1","selectCountrys"+i);
            var Countrys=$(lastdiv).find("span[scop=queryCountry4]");
            var attrs=$(lastdiv).find("span[scop=queryAttr4]");
            var times=$(lastdiv).find("span[scop=queryTime4]");
            var statuses=$(lastdiv).find("span[scop=queryStatus4]");
            var accounts=$(lastdiv).find("span[scop=account4]");
            var table=$(lastdiv).find("div[id=OrderGetOrdersListTable4]");
            var starttime=$(lastdiv).find("input[id=starttime4]");
            var endtime=$(lastdiv).find("input[id=endtime4]");
            $(table).attr("id","OrderGetOrdersListTable"+i);
            $(starttime).attr("id","starttime"+i);
            $(endtime).attr("id","endtime"+i);
            var id2="#menu"+i;
            var id1=$(id2).attr("name2");
            $(table).initTable({
                url:path + "/order/ajax/loadOrdersList.do?folderId="+id1,
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"图片/交易号",name:"orderid",width:"10%",align:"left",format:makeOption2},
                    {title:"买家/SKU/承运商/追踪号",name:"orderid",width:"20%",align:"left",format:makeOption3},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc3');\"><font style='color: #0000ff'>站点</font></a>",name:"itemSite",width:"3%",align:"left",format:makeOption20},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc');\"><font style='color: #0000ff'>售价</font></a>",name:"itemUrl",width:"6%",align:"left",format:makeOption6},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'desc1');\"><font style='color: #0000ff'>售出日期</font></a>",name:"createdtime",width:"5%",align:"left"},
                    {title:"更新时间",name:"lastmodifiedtime",width:"5%",align:"left"},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc2');\"><font style='color: #0000ff'>数量</font></a>",name:"quantitypurchased",width:"3%",align:"center"},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc4');\"><font style='color: #0000ff'>EBAY账号</font></a>",name:"selleruserid",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
                    {title:"操作&nbsp;&nbsp;&nbsp;&nbsp;",name:"option1",width:"8%",align:"center",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            /*$(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});*/
            for(var j=1;j<=Countrys.length;j++){
                $(Countrys[j-1]).attr("scop","queryCountry"+i);
                if(j==1){
                    $(Countrys[j-1]).attr("onclick","queryCountry("+i+","+j+","+null+");");
                }
                if(j==2){
                    $(Countrys[j-1]).attr("onclick","queryCountry("+i+","+j+",'US');");
                }
                if(j==3){
                    $(Countrys[j-1]).attr("onclick","queryCountry("+i+","+j+",'UK');");
                }
                if(j==4){
                    $(Countrys[j-1]).attr("onclick","queryCountry("+i+","+j+",'DE');");
                }
                if(j==5){
                    $(Countrys[j-1]).attr("onclick","queryCountry("+i+","+j+",'AU');");
                }
            }
            for(var j=1;j<=accounts.length;j++){
                var ebayname=$(accounts[j-1]).attr("name1");
                $(accounts[j-1]).attr("scop","account"+i);
                $(accounts[j-1]).attr("onclick","selectAccount1("+i+","+j+",'"+ebayname+"');");
            }
            for(var j=1;j<=attrs.length;j++){
                $(attrs[j-1]).attr("scop","queryAttr"+i);
                if(j==1){
                    $(attrs[j-1]).attr("onclick","queryAttr("+i+","+j+","+null+");");
                }
                if(j==2){
                    $(attrs[j-1]).attr("onclick","queryAttr("+i+","+j+",'fixation');");
                }
                if(j==3){
                    $(attrs[j-1]).attr("onclick","queryAttr("+i+","+j+",'auction');");
                }
                if(j==4){
                    $(attrs[j-1]).attr("onclick","queryAttr("+i+","+j+",'multiattribute');");
                }
            }
            for(var j=1;j<=times.length;j++){
                $(times[j-1]).attr("scop","queryTime"+i);
                if(j==1){
                    $(times[j-1]).attr("onclick","queryTime("+i+","+j+","+null+");");
                }
                if(j==2){
                    $(times[j-1]).attr("onclick","queryTime("+i+","+j+",'1')");
                }
                if(j==3){
                    $(times[j-1]).attr("onclick","queryTime("+i+","+j+",'2');");
                }
                if(j==4){
                    $(times[j-1]).attr("onclick","queryTime("+i+","+j+",'7');");
                }
                if(j==5){
                    $(times[j-1]).attr("onclick","queryTime("+i+","+j+",'30');");
                }
                if(j==6){
                    $(times[j-1]).attr("onclick","queryTime("+i+","+j+","+null+");");
                }
            }
            for(var j=1;j<=statuses.length;j++){
                $(statuses[j-1]).attr("scop","queryStatus"+i);
                if(j==1){
                    $(statuses[j-1]).attr("onclick","queryStatus("+i+","+j+","+null+");");
                }
                if(j==2){
                    $(statuses[j-1]).attr("onclick","queryStatus("+i+","+j+",'1')");
                }
                if(j==3){
                    $(statuses[j-1]).attr("onclick","queryStatus("+i+","+j+",'2');");
                }
                if(j==4){
                    $(statuses[j-1]).attr("onclick","queryStatus("+i+","+j+",'3');");
                }
                if(j==5){
                    $(statuses[j-1]).attr("onclick","queryStatus("+i+","+j+",'4');");
                }
            }
            var timee4=$(lastdiv).find("span[id=time4]");
            $(timee4).attr("id","time"+(i));
            var addstartTimeAndEndTimeID=$(lastdiv).find("a[id=addstartTimeAndEndTimeID]");
            $(addstartTimeAndEndTimeID).removeAttr("onclick");
            $(addstartTimeAndEndTimeID).click(function(){
                addstartTimeAndEndTime($(addstartTimeAndEndTimeID),(i-1));
                /* var span="<span style=\"float: left;color: #5F93D7;\">从</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9\" id=\"starttime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>" +
                 "<span style=\"float: left;color: #5F93D7;\">到</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9\" id=\"endtime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>";
                 $(addstartTimeAndEndTimeID).before(span);
                 $(addstartTimeAndEndTimeID).remove();*/
            });

            var inp=$(lastdiv).find("input[class=key_2]");
            /* var itemType1=$("#itemType4");
             $(itemType1).attr("id","itemType"+i);
             var content1=$("#content4");
             $(s).attr("id","content"+i);*/
            var itemType1=$(lastdiv).find("select[id=itemType4]");
            var content1=$(lastdiv).find("input[id=content4]");
            $(content1).attr("id","content"+i);
            $(itemType1).attr("id","itemType"+i);
            $(inp).removeAttr("onclick");
            $(inp).click(function(){
                /*alert("#daysQ"+i);
                 alert(table);
                 alert("#countryQ"+i);
                 alert("#typeQ"+i)*/
                var daysQ=$("#daysQ"+(i-1)).val();
                var countryQ=$("#countryQ"+(i-1)).val();
                var typeQ=$("#typeQ"+(i-1)).val();
                var itemType=$(itemType1).val();
                var content=$(content1).val();
                var folderId=id1;
                var starttime1=$("#starttime"+(i-1)).val();
                var endtime1=$("#endtime"+(i-1)).val();
                var statusQ=$("#statusQ"+(i-1)).val();
                var accountQ=$("#accountQ"+(i-1)).val();
                refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime1,endtime1,statusQ,accountQ);
            });
            download=$(lastdiv).find("span[id=downloadOrder4]");
            $(download).removeAttr("onclick");
            $(download).click(function(){
                var url=path+"/order/downloadOrders.do?folderId="+id1;
                window.open(url);
            });
            var addcommet=$(lastdiv).find("span[id=addComment]");
            $(addcommet).removeAttr("onclick");
            $(addcommet).click(function(){
                addComment(i-1);
            });
            var movefolder=$(lastdiv).find("span[id=moveFolder]");
            $(movefolder).removeAttr("onclick");
            $(movefolder).click(function(){
                moveFolder(i-1);
            });
            var checkall=$(lastdiv).find("input[id=allCheckbox]");
            $(checkall).removeAttr("onclick");
            $(checkall).click(function(){
                selectAllCheck(i-1,this);
            });
            $("#ContentboxDiv").append($(lastdiv));
            /*  lastdiv.setAttribute("id","con_menu_")*/
        }
    }
    /*    if(cursel>4){
     $("#OrderGetOrdersListTable"+cursel).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
     }*/
    /* if(n>4){
     setTab(name,cursel,n);
     }*/
}
function refreshTable6(table,countryQ,typeQ,daysQ,itemType,content,folderId,starttime,endtime,statusQ,accountQ){
    $(table).initTable({
        url:path + "/order/ajax/loadOrdersList.do?",
        columnData:[
            {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
            {title:"图片/交易号",name:"orderid",width:"10%",align:"left",format:makeOption2},
            {title:"买家/SKU/承运商/追踪号",name:"orderid",width:"20%",align:"left",format:makeOption3},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc3');\"><font style='color: #0000ff'>站点</font></a>",name:"itemSite",width:"3%",align:"left",format:makeOption20},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc');\"><font style='color: #0000ff'>售价</font></a>",name:"itemUrl",width:"6%",align:"left",format:makeOption6},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'desc1');\"><font style='color: #0000ff'>售出日期</font></a>",name:"createdtime",width:"5%",align:"left"},
            {title:"更新时间",name:"lastmodifiedtime",width:"5%",align:"left"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc2');\"><font style='color: #0000ff'>数量</font></a>",name:"quantitypurchased",width:"3%",align:"center"},
            {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc4');\"><font style='color: #0000ff'>EBAY账号</font></a>",name:"selleruserid",width:"8%",align:"left"},
            {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
            {title:"操作&nbsp;&nbsp;&nbsp;&nbsp;",name:"option1",width:"8%",align:"center",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    $(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"countryQ":countryQ,"typeQ":typeQ,"daysQ":daysQ,"itemType":itemType,"content":content,"folderId":folderId,"starttime1":starttime,"endtime1":endtime,"statusQ":statusQ,"accountQ":accountQ});
}
//--------------------------------------------------------------------------
function setTab(name,cursel,n){
    if(cursel<6&&cursel>1){
        var url="/order/ajax/loadOrdersList.do?";
        if(cursel==2){
            url+="status=Incomplete";
        }
        if(cursel==3){
            url+="status=notAllComplete";
        }
        if(cursel==4){
            url+="status=Complete";
        }
        if(cursel==5){
            url+="status=Cancelled";
        }
        $("#OrderGetOrdersListTable"+cursel).initTable({
            url:path + url,
            columnData:[
                {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                {title:"图片/交易号",name:"orderid",width:"10%",align:"left",format:makeOption2},
                {title:"买家/SKU/承运商/追踪号",name:"orderid",width:"20%",align:"left",format:makeOption3},
                {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc3');\"><font style='color: #0000ff'>站点</font></a>",name:"itemSite",width:"3%",align:"left",format:makeOption20},
                {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc');\"><font style='color: #0000ff'>售价</font></a>",name:"itemUrl",width:"6%",align:"left",format:makeOption6},
                {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'desc1');\"><font style='color: #0000ff'>售出日期</font></a>",name:"createdtime",width:"5%",align:"left"},
                {title:"更新时间",name:"lastmodifiedtime",width:"5%",align:"left"},
                {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc2');\"><font style='color: #0000ff'>数量</font></a>",name:"quantitypurchased",width:"3%",align:"center"},
                {title:"<a href=\"javascript:void(0)\" onclick=\"orderByTransactionPrice(this,'asc4');\"><font style='color: #0000ff'>EBAY账号</font></a>",name:"selleruserid",width:"8%",align:"left"},
                {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
                {title:"操作&nbsp;&nbsp;&nbsp;&nbsp;",name:"option1",width:"8%",align:"center",format:makeOption1}
            ],
            selectDataNow:false,
            isrowClick:false,
            showIndex:false
        });
        if(cursel==2){
            refreshTable2();
        }
        if(cursel==3){
            refreshTable3();
        }
        if(cursel==4){
            refreshTable4();
        }
        if(cursel==5){
            refreshTable15();
        }
    }
    if(cursel>5){
        $("#OrderGetOrdersListTable"+cursel).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
    }
    for(i=1;i<=n;i++){
        var menu=document.getElementById(name+i);
        var con=document.getElementById("con_"+name+"_"+i);
        menu.className=i==cursel?"new_tab_1":"new_tab_2";
        con.style.display=i==cursel?"block":"none";
    }
}



function menuFix() {
    var sfEls = document.getElementById("newtipi").getElementsByTagName("li");
    for (var i = 0; i < sfEls.length; i++) {
        sfEls[i].onmouseover = function() {
            this.className += (this.className.length > 0 ? " " : "") + "sfhover";
        }
        sfEls[i].onMouseDown = function() {
            this.className += (this.className.length > 0 ? " " : "") + "sfhover";
        }
        sfEls[i].onMouseUp = function() {
            this.className += (this.className.length > 0 ? " " : "") + "sfhover";
        }
        sfEls[i].onmouseout = function() {
            this.className = this.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
        }
    }
}
window.onload = menuFix;