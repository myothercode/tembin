/**
 * Created by Administrator on 2014/9/25.
 */

$(document).ready(function(){
    queryEbayList();
});


/**获取ebay帐号列表*/
function queryEbayList(){
    $("#ebayManager").initTable({
        url:path + "/user/queryEbaysForCurrUser.do",
        columnData:[
            {title:"代码",name:"ebayNameCode",width:"8%",align:"left"},
            {title:"ebay账户",name:"ebayName",width:"8%",align:"left"},
            {title:"密钥有效期",name:"op",width:"8%",align:"left",format:mCanUseDate},
            {title:"状态",name:"op",width:"8%",align:"left",format:makeStatus},
            {title:"操作",name:"op1",width:"8%",align:"left",format:function(){}}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        onlyFirstPage:true
    });
    refreshRoleTable({});
}
/**组装密钥有效期*/
function mCanUseDate(json){
    var da=json['useTimeStart']+" 至 "+json['useTimeEnd'];
    return da;
}
/**状态*/
function makeStatus(json){
    var imgurlpr=path+"/img/";
    if(json.ebayStatus==1 || json.ebayStatus=='1'){
        imgurlpr+="new_yes.png";
    }else if(json.ebayStatus==0 || json.ebayStatus=='0'){
        imgurlpr+="new_no.png";
    }else{
        imgurlpr+="";
    }

    return "<img src='"+imgurlpr+"' />";
}
/**刷新列表*/
function refreshRoleTable(p){
    if(p==null){p={}}
    $("#ebayManager").selectDataAfterSetParm(p);
}


/**tab切换*/
function setTab(name,cursel,n){
    for(i=1;i<=n;i++){
    var menu=document.getElementById(name+i);
    var con=document.getElementById("con_"+name+"_"+i);
    menu.className=i==cursel?"new_tab_1":"new_tab_2";
    con.style.display=i==cursel?"block":"none";
    }
    if(cursel==1){
        refreshRoleTable({});
    }
    }