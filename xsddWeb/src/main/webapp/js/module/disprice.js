var discountPriceInfo;
function adddiscountpriceinfo(){
    discountPriceInfo= $.dialog({title: '新增折扣选项',
        content: 'url:/xsddWeb/addDiscountPriceInfo.do',
        icon: 'succeed',
        width:800,
        lock:true
    });
}

function editdiscountpriceinfo(id){
    discountPriceInfo= $.dialog({title: '编辑折扣选项',
        content: 'url:/xsddWeb/editDiscountPriceInfo.do?id='+id,
        icon: 'succeed',
        width:800,
        lock:true
    });
}
function refreshTableDisPrice() {
    $("#discountPriceInfoListTable").selectDataAfterSetParm({"bedDetailVO.deptId": "", "isTrue": 0});
}
function loadDisPrice(){
    $("#discountPriceInfoListTable").initTable({
        url:path + "/ajax/loadDiscountPriceInfoList.do",
        columnData:[
            {title:"&nbsp;&nbsp;名称",name:"name",width:"8%",align:"left",format:function(json){return "&nbsp;&nbsp;"+json.name}},
            {title:"账户名称",name:"ebayName",width:"8%",align:"left"},
            {title:"开始时间",name:"disStarttime",width:"8%",align:"left"},
            {title:"结束时间",name:"disEndtime",width:"8%",align:"left"},
            {title:"折扣",name:"madeforoutletcomparisonprice",width:"8%",align:"left"},
            {title:"降价",name:"minimumadvertisedprice",width:"8%",align:"left"},
            {title:"数据状态",name:"option1",width:"8%",align:"center",format:makeOption2s},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"option1",width:"8%",align:"left",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTableDisPrice();
}
/**组装操作选项*/
function makeOption1(json){
    var hs="";
    hs+="<li style='height:25px' onclick=editdiscountpriceinfoSelect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
    hs+="<li style='height:25px' onclick=editdiscountpriceinfo('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    if(json.checkFlag=="0"){
        hs+="<li style='height:25px' onclick=delDiscountprice('"+json.id+"') value='"+json.id+"' doaction=\"look\" >禁用</li>";
    }else{
        hs+="<li style='height:25px' onclick=delDiscountprice('"+json.id+"') value='"+json.id+"' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs,"marginLeft":"-50px"};
    return getULSelect(pp);

}

function delDiscountprice(id){
    var url=path+"/ajax/delDiscountprice.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            alert(r);
            Base.token();
            refreshTableDisPrice();
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );
}
function editdiscountpriceinfoSelect(id){
    discountPriceInfo= $.dialog({title: '编辑折扣选项',
        content: 'url:/xsddWeb/editDiscountPriceInfo.do?id='+id+'&&type=01',
        icon: 'succeed',
        width:800,
        lock:true
    });
}