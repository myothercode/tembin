var returnShipping;
function addshippingDetails(){
    var pp={title: '新增运送选项',
        content: 'url:/xsddWeb/addshippingDetails.do',
        icon: 'succeed',
        width:1000,
        height:700,
        lock:true
    };
    returnShipping=openMyDialog(pp);
       /* $.dialog({title: '新增运送选项',
        content: 'url:/xsddWeb/addshippingDetails.do',
        icon: 'succeed',
        width:1000,
        height:700,
        lock:true
    });*/
}
function editshippingDetails(id){
    var pp={title: '编辑运送选项',
        content: 'url:/xsddWeb/editshippingDetails.do?id='+id,
        icon: 'succeed',
        width:1000,
        height:700,
        lock:true
    };
    returnShipping=openMyDialog(pp);
        /*$.dialog({title: '编辑运送选项',
        content: 'url:/xsddWeb/editshippingDetails.do?id='+id,
        icon: 'succeed',
        width:1000,
        height:700,
        lock:true
    });*/
}
function editshippingDetailsselect(id){
    var pp={title: '查看运送选项',
        content: 'url:/xsddWeb/editshippingDetails.do?id='+id+'&type=01',
        icon: 'succeed',
        width:1000,
        height:700,
        lock:true
    };
    returnShipping=openMyDialog(pp);
       /* $.dialog({title: '查看运送选项',
        content: 'url:/xsddWeb/editshippingDetails.do?id='+id+'&type=01',
        icon: 'succeed',
        width:1000,
        height:700,
        lock:true
    });*/
}

function getSiteImg(json){
    var html='<img title="'+json.siteName+'" src="'+path+json.siteImg+'"/>';
    return html;
}

function loadShipping(){
    $("#shippingDetailsList").initTable({
        url:path + "/ajax/loadShippingDetailsList.do",
        columnData:[
            {title:"&nbsp;&nbsp;名称",name:"shippingName",width:"8%",align:"left",format:getShippingName},
            {title:"站点",name:"siteName",width:"8%",align:"left",format:getSiteImg},
            {title:"ebay账号",name:"option1",width:"8%",align:"left",format:showData},
            {title:"数据状态",name:"option1",width:"8%",align:"center",format:makeOption2s},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"option1",width:"8%",align:"left",format:shippingmakeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTableShipping();
}
function getShippingName(json){
    return "&nbsp;&nbsp;"+json.shippingName;
}
function refreshTableShipping(){
    $("#shippingDetailsList").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
/**组装操作选项*/
function shippingmakeOption1(json){
    var hs="";
    hs+="<li style='height:25px' onclick=editshippingDetailsselect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
    hs+="<li style='height:25px' onclick=editshippingDetails('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    if(json.checkFlag=="0") {
        hs += "<li style='height:25px' onclick=delshippingDetails('" + json.id + "') value='" + json.id + "' doaction=\"look\" >禁用</li>";
    }else {
        hs += "<li style='height:25px' onclick=delshippingDetails('" + json.id + "') value='" + json.id + "' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs,"marginLeft":"-50px"};
    return getULSelect(pp);
}

function delshippingDetails(id){
    var url=path+"/ajax/delshippingDetails.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            alert(r);
            Base.token();
            refreshTableShipping();
        },
            function(m,r){
                Base.token();
                alert(r);

            }]
    );
}

function showData(json){
    var html='';
    html+='<div style="color: #3B9EF3;">'+json.ebayName+'</div>';
    html+='<div><table style="border:1px solid #dddddd;" width="400px">';
    for(var i=0;i<json.lits.length;i++){
        html+='<tr><td style="border:1px solid #dddddd;" width="220px">'+json.lits[i].shippingservice+'</td><td style="border:1px solid #dddddd;" width="60px">'+json.lits[i].shippingservicecost.toFixed(2)+'&nbsp'+json.currencyId+'</td><td style="border:1px solid #dddddd;"  width="60px">'+json.lits[i].shippingserviceadditionalcost.toFixed(2)+'&nbsp'+json.currencyId+'</td><td style="border:1px solid #dddddd;"  width="60px">'+json.lits[i].shippingsurcharge.toFixed(2)+'&nbsp'+json.currencyId+'</td></tr>';
    }
    for(var i=0;i<json.liti.length;i++){
        html+='<tr><td style="border:1px solid #dddddd;" width="220px">'+json.liti[i].shippingservice+'</td><td style="border:1px solid #dddddd;" width="60px">'+json.liti[i].shippingservicecost.toFixed(2)+'&nbsp'+json.currencyId+'</td><td style="border:1px solid #dddddd;"  width="60px">'+json.liti[i].shippingserviceadditionalcost.toFixed(2)+'&nbsp'+json.currencyId+'</td><td style="border:1px solid #dddddd;"  width="60px">&nbsp</td></tr>';
    }
    html+='</table></div>';
    return html;
}