var returnShipping;
function addshippingDetails(){
    returnShipping=$.dialog({title: '新增运送选项',
        content: 'url:/xsddWeb/addshippingDetails.do',
        icon: 'succeed',
        width:1000,
        lock:true
    });
}
function editshippingDetails(id){
    returnShipping=$.dialog({title: '编辑运送选项',
        content: 'url:/xsddWeb/editshippingDetails.do?id='+id,
        icon: 'succeed',
        width:1000,
        lock:true
    });
}
function refreshTableShipping(){
    $("#shippingDeails").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
    var pp={"liString":hs};
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
                alert(r);
                Base.token();
            }]
    );
}
function editshippingDetailsselect(id){
    returnShipping=$.dialog({title: '编辑运送选项',
        content: 'url:/xsddWeb/editshippingDetails.do?id='+id+'&type=01',
        icon: 'succeed',
        width:1000,
        lock:true
    });
}
function showTextS(obj){
    $(obj).hide();
    $(obj).parent().find("input[type='hidden']").prop("type","text").focus();
}
function showSpan(obj){
    $(obj).prop("type","hidden");
    $(obj).parent().find("span").text($(obj).val());
    $(obj).parent().find("span").show();
}
function showData(json){
    var html='';
    html+='<div style="color: #3B9EF3;">'+json.ebayName+'</div>';
    html+='<div><table style="border:1px solid #dddddd;" width="400px">';
    for(var i=0;i<json.lits.length;i++){
        html+='<tr><td style="border:1px solid #dddddd;" width="220px"><input type="hidden" name="'+json.id+'.sourceId" value="'+json.lits[i].id+'"/><input type="hidden" name="'+json.id+'.shippingservice" value="'+json.lits[i].optionId+'" shippingName="1"/>'+json.lits[i].shippingservice+'</td>' +
            '<td style="border:1px solid #dddddd;" width="60px">' +
            '<span style="color: dodgerblue;" onclick="showTextS(this)">'+json.lits[i].shippingservicecost.toFixed(2)+'</span><input type="hidden" onblur="showSpan(this)" class="newinputt" name="'+json.id+'.ShippingServiceCost.value" onkeypress="return inputNUMAndPoint(event,this,2)" value="'+json.lits[i].shippingservicecost.toFixed(2)+'"/>&nbsp'+json.currencyId+'</td>' +
            '<td style="border:1px solid #dddddd;"  width="60px"><span style="color: dodgerblue;" onclick="showTextS(this)">'+json.lits[i].shippingserviceadditionalcost.toFixed(2)+'</span><input type="hidden" onblur="showSpan(this)" class="newinputt" onkeypress="return inputNUMAndPoint(event,this,2)" name="'+json.id+'.ShippingServiceAdditionalCost.value" value="'+json.lits[i].shippingserviceadditionalcost.toFixed(2)+'"/>&nbsp'+json.currencyId+'</td>' +
            '<td style="border:1px solid #dddddd;"  width="60px"><span style="color: dodgerblue;" onclick="showTextS(this)">'+json.lits[i].shippingsurcharge.toFixed(2)+'</span><input type="hidden" onblur="showSpan(this)" class="newinputt" onkeypress="return inputNUMAndPoint(event,this,2)" name="'+json.id+'.ShippingSurcharge.value" value="'+json.lits[i].shippingsurcharge.toFixed(2)+'"/>&nbsp'+json.currencyId+'</td></tr>';
    }
    for(var i=0;i<json.liti.length;i++){
        html+='<tr><td style="border:1px solid #dddddd;" width="220px"><input type="hidden" name="'+json.id+'.intersourceId" value="'+json.liti[i].id+'"/><input type="hidden" name="'+json.id+'.intershippingservice" value="'+json.liti[i].optionId+'" shippingName="1"/>'+json.liti[i].shippingservice+'</td>' +
            '<td style="border:1px solid #dddddd;" width="60px"><span style="color: dodgerblue;" onclick="showTextS(this)">'+json.liti[i].shippingservicecost.toFixed(2)+'</span><input type="hidden" onblur="showSpan(this)" class="newinputt" onkeypress="return inputNUMAndPoint(event,this,2)" name="'+json.id+'.interShippingServiceCost.value" value="'+json.liti[i].shippingservicecost.toFixed(2)+'"/>&nbsp'+json.currencyId+'</td>' +
            '<td style="border:1px solid #dddddd;"  width="60px"><span style="color: dodgerblue;" onclick="showTextS(this)">'+json.liti[i].shippingserviceadditionalcost.toFixed(2)+'</span><input type="hidden"  onblur="showSpan(this)" class="newinputt" onkeypress="return inputNUMAndPoint(event,this,2)" name="'+json.id+'.interShippingServiceAdditionalCost.value" value="'+json.liti[i].shippingserviceadditionalcost.toFixed(2)+'"/>&nbsp'+json.currencyId+'</td>' +
            '<td style="border:1px solid #dddddd;"  width="60px">&nbsp</td></tr>';
    }
    html+='</table></div>';
    return html;
}