/**
 * Created by Administrator on 2014/9/28.
 */
/**获取ebay帐号列表*/
function queryPaypalList(){
    $("#paypalManager").initTable({
        url:path + "/paypal/queryPaypalList.do",
        columnData:[
            {title:"paypal帐号",name:"paypalAccount",width:"8%",align:"left"},
            {title:"email",name:"email",width:"8%",align:"left"},
            {title:"状态",name:"op",width:"8%",align:"left",format:makePaypalStatus},
            {title:"是否验证",name:"op",width:"8%",align:"left",format:function(json){
                return (json.sfCheck==1)?"是":"否";
            }},
            {title:"操作",name:"op1",width:"8%",align:"left",format:makeOptionPaypal}
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
    var imgurlpr=path+"/img/";
    if(json.status==1 || json.status=='1'){
        imgurlpr+="new_yes.png";
    }else if(json.status==0 || json.status=='0'){
        imgurlpr+="new_no.png";
    }else{
        imgurlpr+="";
    }
    return "<img src='"+imgurlpr+"' />";
}
/**操作*/
function makeOptionPaypal(json){
    var stopButton="<span onclick=operationPaypal(0,'"+json.id+"') class='newusa_ici'><b style=\"color:#FF6060;font-weight: normal;\">停用</b>帐号</span>";
    var startButton="<span onclick=operationPaypal(1,'"+json.id+"') class='newusa_ici'><b style=\"color:#93B937;font-weight: normal;\">启用</b>帐号</span>";

    var yanzheng="<span onclick=operationPaypal(2,'"+json.id+"') class='newusa_ici'><b style=\"color:#93B937;font-weight: normal;\">验证</b>帐号</span>";

    if(json.status==1 || json.status=='1'){
        if(json.sfCheck!=1){
            return stopButton+yanzheng;
        }else{
            return stopButton;
        }
    }else if(json.status==0 || json.status=='0'){
        return startButton;
    }
}

/**刷新paypal列表*/
function refreshPayPalTable(p){
    if(p==null){p={}}
    $("#paypalManager").selectDataAfterSetParm(p);
}

/**停用/启用paypal账户*/
function operationPaypal(bs,id){

    if(bs=='0' && !confirm("停用将会清理掉ebay账户绑定的该帐号数据，是否继续?")){
        return;
    }

    var data={"paypalId":id,"stat":bs};
    var url=path+"/paypal/operationPayPalAccount.do";
    if(bs=='2'){
        data={"id":id};
        url=path+"/paypal/getPaypalYanZheng.do";
    }
    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r);
            refreshPayPalTable();
            Base.token();
        },
        function(m,r){
            alert(r);
            Base.token();
        }]
    );
}


/**打开新增paypal账户窗口*/
var opAddPaypalPageW;
function opAddPaypalPage(){
    var url=path+"/paypal/addPayPalPage.do";
    opAddPaypalPageW=$.dialog({
        title:'',
        id : "dig" + (new Date()).getTime(),
        content:"url:"+url,
        width : 600,
        height : 600,
        max:false,
        min:false,
        lock : true

    });

}