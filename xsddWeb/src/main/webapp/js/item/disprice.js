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
    $("#discountpriceinfo").selectDataAfterSetParm({"bedDetailVO.deptId": "", "isTrue": 0});
}
/**组装操作选项*/
function makeOption1Disprice(json){
    var hs="";
    hs+="<li style='height:25px' onclick=editdiscountpriceinfoSelect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
    hs+="<li style='height:25px' onclick=editdiscountpriceinfo('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    if(json.checkFlag=="2") {
        hs += "<li style='height:25px' onclick=delDiscountprice('" + json.id + "') value='" + json.id + "' doaction=\"look\" >禁用</li>";
    }else {
        hs += "<li style='height:25px' onclick=delDiscountprice('" + json.id + "') value='" + json.id + "' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs};
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