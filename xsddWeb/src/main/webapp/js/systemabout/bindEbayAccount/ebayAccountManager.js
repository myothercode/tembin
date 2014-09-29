/**
 * Created by Administrator on 2014/9/25.
 */

$(document).ready(function(){
    queryEbayList();
    //queryPaypalList();
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
            {title:"操作",name:"op1",width:"8%",align:"left",format:makeOptionEbay}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        onlyFirstPage:true
    });
    refreshRoleTable({});
}
/**组装操作下拉*/
function makeOptionEbay(json){
    var select1="<div class=\"ui-select\" style=\"width:8px\">" +
        "<select onchange='doEbayAccount(this)'>" ;
    select1+= "<option  value='x'>请选择</option>" ;
    if(json.ebayStatus==1 || json.ebayStatus=='1'){
        select1+= "<option  value='"+json.id+"' doaction=\"stop\">停用</option>" ;
    }else if(json.ebayStatus==0 || json.ebayStatus=='0'){
        select1+= "<option  value='"+json.id+"' doaction=\"start\">启用</option>" ;
    }
    select1+= "<option  value='"+json.id+"' doaction=\"edit\">编辑</option>" ;
    select1+= "</select></div>";
    return select1;
}
/**选择操作后要执行的动作*/
function doEbayAccount(obj){
    var optionV=$(obj).find("option:selected");
    var v=$(optionV).attr('value');
    if(v=='x'){return;}
    var d=$(optionV).attr('doaction');
    if(d=="stop" || d=="start"){
        var data={"act":d,"ebayId":v};
        startOrStop(data);
    }else if(d=='edit'){
        openBindEbayWindow(v);
    }

}
function startOrStop(data){
    var url=path+"/user/startOrStopEbayAccount.do";
    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r);
            refreshRoleTable({});
            Base.token();
        },
            function(m,r){
                alert(r);
                refreshRoleTable({});
                Base.token();
            }]
    );
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
    }else if(cursel==2){
        queryPaypalList();
    }
    }

/**打开ebay帐号绑定窗口!*/
var bindEbayWindow;
function openBindEbayWindow(ebayId){
    var url=path+"/user/";
    if(ebayId==null || ebayId.length==0){
        url+="bindEbayAccount.do";
    }else{
        url+="editEbayAccount.do?id="+ebayId;
    }
    bindEbayWindow=$.dialog({
        title:'',
        id : "dig" + (new Date()).getTime(),
        content:"url:"+url,
        width : 600,
        height : 600,
        max:false,
        min:false,
        lock : true

    });

}