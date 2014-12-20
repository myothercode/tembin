/**
 * Created by Administrtor on 2014/12/17.
 */
function onloadSetEbayComplementTable(){
    $("#setEbayComplement").initTable({
        url:path+"/ajax/loadSetComplementTable.do",
        columnData:[
            {title:"EBAY账号",name:"ebayAccount",width:"15%",align:"left"},
            {title:"补数类型",name:"complementType",width:"15%",align:"left",format:complementType},
            {title:"创建用户",name:"createUserName",width:"15%",align:"left"},
            {title:"创建时间",name:"createDate",width:"15%",align:"left"},
            {title:"修改用户",name:"updateUserName",width:"15%",align:"left"},
            {title:"修改时间",name:"updateDate",width:"15%",align:"left"},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"Option1",width:"10%",align:"left",format:setEbayOption}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshSetEbayComplementTable();
}
function setEbayOption(json){
    var hs="";
    hs+="<li style='height:25px' onclick=delSetEbayComplement('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
    hs+="<li style='height:25px' onclick=editSetEbayComplement('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    var pp={"liString":hs,"marginLeft":"-150px"};
    return getULSelect(pp);
}
function delSetEbayComplement(id){
    var conf = confirm("你确定删除补数设置，同时会删除该ebay账号设置的补数规则？");
    if(conf == true) {

    } else {
        return;
    }
    var url=path+"/ajax/delSetEbayComplement.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            refreshSetEbayComplementTable();
        },
            function(m,r){
                alert(r);
            }]
    );
}
function complementType(json){
    var htm="";
    if(json.complementType=="1"){
        htm="自动补数"
    }else{
        htm="库存补数";
    }
    return htm;
}

function  refreshSetEbayComplementTable(){
    var param={};
    $("#setEbayComplement").selectDataAfterSetParm(param);
}
var ebaycomplement;

function addSetEbayComplement(){
    ebaycomplement=openMyDialog({title: '新增补数设置',
        content: 'url:'+path+'/complement/addSetEbayComplement.do',
        icon: 'succeed',
        width:800,
        lock:true
    });
}

function editSetEbayComplement(id){
    ebaycomplement=openMyDialog({title: '编辑补数设置',
        content: 'url:'+path+'/complement/editSetEbayComplement.do?id='+id,
        icon: 'succeed',
        width:800,
        lock:true
    });
}
