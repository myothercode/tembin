var returnPolicy;
function addReturnpolicy(){
    returnPolicy=$.dialog({title: '新增退款选项',
        content: 'url:/xsddWeb/addReturnpolicy.do',
        icon: 'succeed',
        width:800,
        lock:true
    });
}
function editReturnpolicy(id){
    returnPolicy=$.dialog({title: '编辑退款选项',
        content: 'url:/xsddWeb/editReturnpolicy.do?id='+id,
        icon: 'succeed',
        width:800,
        lock:true
    });
}
function makeOption3returnpolicy(json){
    var htm='接受退货</br>'
    if(json.returnsWithinOptionName!=null&&json.returnsWithinOptionName!=""){
        htm+='退货天数：'+json.returnsWithinOptionName+'</br>';
    }
    if(json.returnsAcceptedOptionName!=null&&json.returnsAcceptedOptionName!=""){
        htm+='退货政策：'+json.returnsAcceptedOptionName+'</br>';
    }
    if(json.shippingCostPaidByOptionName!=null&&json.shippingCostPaidByOptionName!=""){
        var period = json.unpaidPeriod;
        htm+='退货运费由谁承担:'+json.shippingCostPaidByOptionName+'</br>';
    }
    if(json.policyCount!=null&&json.policyCount!=""){
        var period = json.policyPeriod;
        htm+='退货方式：'+json.refundOptionName+'</br>';
    }
    return htm;
}
function refreshTablereturnpolicy(){
    $("#returnPolicyListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function loadreturnpolicy(){
    $("#returnPolicyListTable").initTable({
        url:path + "/ajax/loadReturnpolicyList.do?",
        columnData:[
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"站点",name:"siteName",width:"8%",align:"left",format:getSiteImg},
            {title:"退货明细",name:"option1",width:"8%",align:"left",format:makeOption3returnpolicy},
            {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2s},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1returnpolicy}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTablereturnpolicy();
}
/**组装操作选项*/
function makeOption1returnpolicy(json){
    var hs="";
    hs+="<li style='height:25px' onclick=editReturnPolicyselect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
    hs+="<li style='height:25px' onclick=editReturnpolicy('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    if(json.checkFlag=="0") {
        hs += "<li style='height:25px' onclick=delReturnPolicy('" + json.id + "') value='" + json.id + "' doaction=\"look\" >禁用</li>";
    }else {
        hs += "<li style='height:25px' onclick=delReturnPolicy('" + json.id + "') value='" + json.id + "' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs,"marginLeft":"-50px"};
    return getULSelect(pp);
}

function editReturnPolicyselect(id){
    returnPolicy=$.dialog({title: '编辑退款选项',
        content: 'url:/xsddWeb/editReturnpolicy.do?id='+id+'&type=01',
        icon: 'succeed',
        width:800,
        lock:true
    });
}
function delReturnPolicy(id){
    var url=path+"/ajax/delReturnPolicy.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            alert("操作成功！");
            Base.token();
            refreshTablereturnpolicy();
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );
}
