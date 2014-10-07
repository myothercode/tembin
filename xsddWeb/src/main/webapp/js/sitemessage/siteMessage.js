/**
 * Created by Administrtor on 2014/9/4.
 */

$(document).ready(function(){
    queryMessageList();
});


/**获取消息列表*/
function queryMessageList(){
    $("#siteMessageDiv").initTable({
        url:path + "/sitemessage/selectSiteMessage.do",
        columnData:[
            {title:"类型",name:"messageType",width:"8%",align:"left"},
            {title:"标题",name:"messageTitle",width:"8%",align:"left"},
            {title:"内容",name:"message",width:"8%",align:"left"},
            {title:"时间",name:"createTime",width:"8%",align:"left"},
            {title:"操作",name:"op1",width:"8%",align:"left",format:makeOptionM}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        onlyFirstPage:false
    });
    refreshRoleTable({});
}
function refreshRoleTable(p){
    if(p==null){p={}}
    $("#siteMessageDiv").selectDataAfterSetParm(p);
}
function makeOptionM(json){
    var select1="<div class=\"ui-select\" style=\"width:8px\">" +
        "<select onchange='doMessageAction(this)'>" ;
    select1+= "<option  value='x'>请选择</option>" ;
    if(json.readed==0 || json.readed=='0'){
        select1+= "<option  value='"+json.id+"' doaction=\"readed\">标记已读</option>" ;
    }
    select1+= "<option  value='"+json.id+"' doaction=\"look\">查看</option>" ;
    select1+= "</select></div>";
    return select1;
}

function doMessageAction(obj){
    var optionV=$(obj).find("option:selected");
    var v=$(optionV).attr('value');
    if(v=='x'){return;}
    var d=$(optionV).attr('doaction');
}

var readMessageWindow;
function openMessage