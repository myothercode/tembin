/**
 * Created by Administrtor on 2014/12/17.
 */
function setInventoryOption(json){
    var hs="";
    hs+="<li style='height:25px' onclick=delInventoryComplement('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
    hs+="<li style='height:25px' onclick=editInventoryComplement('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    var pp={"liString":hs,"marginLeft":"-150px"};
    return getULSelect(pp);
}
function delInventoryComplement(id){
    var conf = confirm("你确定删除补数设置？");
    if(conf == true) {
    } else {
        return;
    }
    var url=path+"/ajax/delInventoryComplement.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            refreshInventoryComplementTable();
        },
            function(m,r){
                alert(r);
            }]
    );
}
function loadInventoryComplementTable(){
    $("#otherComplementManager").initTable({
        url:path+"/ajax/loadInventoryComplementTable.do",
        columnData:[
            {title:"EBAY账号",name:"ebayAccount",width:"15%",align:"left"},
            {title:"商品SKU",name:"itemSku",width:"15%",align:"left"},
            {title:"创建用户",name:"createUserName",width:"15%",align:"left"},
            {title:"创建时间",name:"createDate",width:"15%",align:"left"},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"Option1",width:"10%",align:"left",format:setInventoryOption}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshInventoryComplementTable();
}
function  refreshInventoryComplementTable(){
    var param={};
    $("#otherComplementManager").selectDataAfterSetParm(param);
}

var inventorycomplement;
function addInventoryComplement(){
    inventorycomplement=openMyDialog({title: '新增库存补数规则',
        content: 'url:'+path+'/complement/addInventoryComplement.do',
        icon: 'succeed',
        width:800,
        lock:true
    });
}

function editInventoryComplement(id){
    inventorycomplement=openMyDialog({title: '编辑补数设置',
        content: 'url:'+path+'/complement/editInventoryComplement.do?id='+id,
        icon: 'succeed',
        width:800,
        lock:true
    });
}
