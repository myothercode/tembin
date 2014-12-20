/**
 * Created by Administrtor on 2014/12/17.
 */
var tabName = "setEbayComplement";
$(document).ready(function(){
    $("#addimg").attr("src",path+"/img/adds.png");
    onloadSetEbayComplementTable();
});
function onloadLogTable(){
    $("#complementLog").initTable({
        url:path+"/ajax/loadComplementLog.do",
        columnData:[
            {title:"消息类型",name:"eventname",width:"20%",align:"left"},
            {title:"消息内容",name:"eventdesc",width:"40%",align:"left"},
            {title:"时间",name:"createdate",width:"20%",align:"left"}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshLogTable();
}
function  refreshLogTable(){
    var param={};
    $("#complementLog").selectDataAfterSetParm(param);
}
function onloadTable(){
    $("#complementManager").initTable({
        url:path+"/ajax/loadComplementTable.do",
        columnData:[
            {title:"SKU",name:"skuKey",width:"20%",align:"left",format:getSku},
            {title:"规则类型",name:"autoType",width:"20%",align:"left",format:getTypeDesc},
            {title:"规则描述",name:"complementDesc",width:"40%",align:"left"},
            {title:"EBAY账号",name:"ebayAccount",width:"10%",align:"center"},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"Option1",width:"10%",align:"left",format:Option1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTable();
}
function getSku(json){
    var html="";
    if(json.autoType=="1"){
        html="SKU = "+json.skuKey;
    }else if(json.autoType=="2"){
        html="SKU以"+json.skuKey+"开头";
    }
    return html;
}
function getTypeDesc(json){
    var html="";
    if(json.autoType=="1"){
        html="停止自动补货（按照SKU）";
    }else if(json.autoType=="2"){
        html="停止自动补货（按照SKU起始字符）";
    }
    return html;
}
function addAll(){
    if(tabName=="complementManager"){
        addcomplement();
    }else if(tabName=="setEbayComplement"){
        addSetEbayComplement();
    }else if(tabName=="otherComplementManager"){
        addInventoryComplement();
    }
}
var complement;
function addcomplement(){
    complement=openMyDialog({title: '新增自动补数规则',
        content: 'url:'+path+'/complement/addComplement.do',
        icon: 'succeed',
        width:800,
        lock:true
    });
}
function  refreshTable(){
    var param={};
    $("#complementManager").selectDataAfterSetParm(param);
}
function Option1(json){
    var hs="";
    hs+="<li style='height:25px' onclick=delComplement('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
    var pp={"liString":hs,"marginLeft":"-150px"};
    return getULSelect(pp);
}
function delComplement(id){
    var url=path+"/ajax/delComplement.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            refreshTable();
        },
            function(m,r){
                alert(r);
            }]
    );
}

function setAssessTab(obj){
    var name=$(obj).attr("name");
    $(obj).parent().find("dt").each(function (i, d) {
        if($(d).attr("name")==name){
            $(d).attr("class","new_tab_1");
            tabName=name;
            $("#"+name).show();
        }else{
            $(d).attr("class","new_tab_2");
            $("#"+$(d).attr("name")).hide();
        }
    });
    if(tabName=="complementManager"){
        onloadTable();
    }else if(tabName=="setEbayComplement"){
        onloadSetEbayComplementTable();
    }else if(tabName=="otherComplementManager"){
        loadInventoryComplementTable();
    }else{
        onloadLogTable();
    }

    if(name.indexOf("Log")>0){
        $("#addimg").hide();
    }else{
        $("#addimg").show();
    }

}