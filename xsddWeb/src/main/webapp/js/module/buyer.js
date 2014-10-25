var buyerRequire;
function addBuyer(){
    buyerRequire=$.dialog({title: '新增买家要求',
        content: 'url:/xsddWeb/addBuyer.do',
        icon: 'succeed',
        width:500,
        lock:true
    });
}

function editBuyer(id){
    buyerRequire=$.dialog({title: '编辑买家要求',
        content: 'url:/xsddWeb/editBuyer.do?id='+id,
        icon: 'succeed',
        width:500,
        lock:true
    });
}
function editBuyerselect(id){
    buyerRequire=$.dialog({title: '查看买家要求',
        content: 'url:/xsddWeb/editBuyer.do?id='+id+'&type=01',
        icon: 'succeed',
        width:500,
        lock:true
    });
}

function delBuyer(id){
    var url=path+"/ajax/delBuyer.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            alert(r);
            Base.token();
            refreshTablebuyer();
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );
}
function loadbuyer(){
    $("#buyerRequireTable").initTable({
        url:path + "/ajax/loadBuyerRequirementDetailsList.do",
        columnData:[
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"站点",name:"siteName",width:"8%",align:"left"},
            {title:"买家要求",name:"option1",width:"8%",align:"left",format:makeOption3buyer},
            {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1buyer}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTablebuyer();
}
function refreshTablebuyer(){
    $("#buyerRequireTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
/**组装操作选项*/
function makeOption1buyer(json) {
    var hs = "";
    hs += "<li style='height:25px' onclick=editBuyerselect('" + json.id + "') value='" + json.id + "' doaction=\"look\" >查看</li>";
    hs += "<li style='height:25px' onclick=editBuyer('" + json.id + "') value='" + json.id + "' doaction=\"look\" >编辑</li>";
    if (json.checkFlag == "0") {
        hs += "<li style='height:25px' onclick=delBuyer('" + json.id + "') value='" + json.id + "' doaction=\"look\" >禁用</li>";
    } else{
        hs += "<li style='height:25px' onclick=delBuyer('" + json.id + "') value='" + json.id + "' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs};
    return getULSelect(pp);

}

/**组装操作选项*/
function makeOption3buyer(json){

    var htm=''
    if(json.buyerFlag!=null&&json.buyerFlag=="1"){
        htm+='允许所有买家购买我的物品</br>';
    }else{
        if(json.linkedpaypalaccount!=null&&json.linkedpaypalaccount=="0"){
            htm+='没有PayPal账户</br>';
        }
        if(json.shiptoregistrationcountry!=null&&json.shiptoregistrationcountry=="0"){
            htm+='主要运送地址在我的运送范围之外</br>';
        }
        if(json.policyCount!=null&&json.policyCount!=""){
            var period = json.unpaidPeriod;
            htm+='曾收到'+json.unpaidCount+'个弃标个案，在过去'+period.substr(period.indexOf("_")+1,period.length)+'天</br>';
        }
        if(json.policyCount!=null&&json.policyCount!=""){
            var period = json.policyPeriod;
            htm+='曾收到'+json.policyCount+'个违反政策检举，在过去'+period.substr(period.indexOf("_")+1,period.length)+'天</br>';
        }

        if(json.minimumfeedbackscore!=null&&json.minimumfeedbackscore!=""){
            htm+='信用指标等于或低于'+json.minimumfeedbackscore+"</br>";
        }
        if(json.maximumitemcount!=null&&json.maximumitemcount!=""){
            htm+='在过去10天内曾出价或购买我的物品，已达到我所设定的限制'+json.maximumitemcount+'</br>';
        }
        if(json.feedbackscore!=null&&json.feedbackscore!=""){
            htm+='这项限制只适用于买家信用指数等于或低于'+json.feedbackscore+'</br>';
        }
    }
    return htm;
}