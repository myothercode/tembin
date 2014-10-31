var payPal;
function addPayPal(){
    payPal=$.dialog({title: '新增付款选项',
        content: 'url:/xsddWeb/addPayPal.do',
        icon: 'succeed',
        width:500,
        lock:true
    });
    }

function editPayPal(id){
    payPal=$.dialog({title: '编辑付款选项',
        content: 'url:/xsddWeb/editPayPal.do?id='+id,
        icon: 'succeed',
        width:500,
        lock:true
    });
    }
function editPayPalselect(id){
    payPal=$.dialog({title: '查看付款选项',
        content: 'url:/xsddWeb/editPayPal.do?id='+id+'&type=01',
        icon: 'succeed',
        width:500,
        lock:true
    });
    }
function delPayPal(id){
    var url=path+"/ajax/delPayPal.do?id="+id;
    $().invoke(url,{},
    [function(m,r){
    alert(r);
    Base.token();
        refreshTablepaypal();
    },
    function(m,r){
    alert(r);
    Base.token();
    }]
    );
    }

function loadpaypal(){
    $("#paypallisttable").initTable({
        url:path + "/ajax/loadPayPalList.do",
        columnData:[
            {title:"名称",name:"payName",width:"8%",align:"left"},
            {title:"站点",name:"siteName",width:"8%",align:"left",format:getSiteImg},
            {title:"paypal账号",name:"payPalName",width:"8%",align:"left"},
            {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"动作",name:"option1",width:"8%",align:"left",format:makeOption1paypal}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTablepaypal();
}
function  refreshTablepaypal(){
    $("#paypallisttable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
    }
//数据状态
function makeOption2(json){
    var htm=''
    if(json.checkFlag=="0"){
    htm='已启用';
    }else{
    htm='已禁用';
    }
    return htm;
    }
/**组装操作选项*/
function makeOption1paypal(json){
    var hs="";
    hs+="<li style='height:25px' onclick=editPayPalselect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
    hs+="<li style='height:25px' onclick=editPayPal('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    if(json.checkFlag=="0") {
        hs += "<li style='height:25px' onclick=delPayPal('" + json.id + "') value='" + json.id + "' doaction=\"look\" >禁用</li>";
    }else {
        hs += "<li style='height:25px' onclick=delPayPal('" + json.id + "') value='" + json.id + "' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs,"marginLeft":"-50px"};
    return getULSelect(pp);
}
