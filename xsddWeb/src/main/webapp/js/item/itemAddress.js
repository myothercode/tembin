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

function refreshTableAddress(){
    $("#itemLocation").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
    var pp={"liString":hs};
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