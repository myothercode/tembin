var itemAddressList;
function addItemAddress(){
    itemAddressList=$.dialog({title: '新增物品所在地',
        content: 'url:/xsddWeb/addItemAddress.do',
        icon: 'succeed',
        width:800,
        lock:true
    });
}

function editItemAddress(id){
    itemAddressList=$.dialog({title: '编辑物品所在地',
        content: 'url:/xsddWeb/editItemAddress.do?id='+id,
        icon: 'succeed',
        width:800,
        lock:true
    });
}
function loadAddress(){

    $("#ItemAddressListTable").initTable({
        url:path + "/ajax/loadItemAddressList.do",
        columnData:[
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"地址",name:"address",width:"8%",align:"left"},
            {title:"国家",name:"countryName",width:"8%",align:"left"},
            {title:"邮编",name:"postalcode",width:"8%",align:"left"},
            {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2s},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1address}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTableAddress();
}
function refreshTableAddress(){
    $("#ItemAddressListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}

/**组装操作选项*/
/**组装操作选项*/
function makeOption1address(json){
    var hs="";
    hs+="<li style='height:25px' onclick=editItemAddressselect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
    hs+="<li style='height:25px' onclick=editItemAddress('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    if(json.checkFlag=="0") {
        hs += "<li style='height:25px' onclick=delItemAddress('" + json.id + "') value='" + json.id + "' doaction=\"look\" >禁用</li>";
    }else {
        hs += "<li style='height:25px' onclick=delItemAddress('" + json.id + "') value='" + json.id + "' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs,"marginLeft":"-50px"};
    return getULSelect(pp);
}

function editItemAddressselect(id){
    itemAddressList=$.dialog({title: '查看付款选项',
        content: 'url:/xsddWeb/editItemAddress.do?id='+id+'&type=01',
        icon: 'succeed',
        width:800,
        lock:true
    });
}
function delItemAddress(id){
    var url=path+"/ajax/delItemAddress.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            alert(r);
            Base.token();
            refreshTableAddress();
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );
}