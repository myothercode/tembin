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
            {title:"",name:"",align:"left",width:"2%",format:returnCheckBox},
            {title:"类型",name:"messageType",width:"8%",align:"left",format:function(json){
                if(json.readed==0 || json.readed=='0'){
                    return json.messageType;
                }else{
                    return "<span style='color: #999999;'>"+json.messageType +"</span>";
                }
                 }
            },
            {title:"标题",name:"messageTitle",width:"8%",align:"left",format:function(json){
                if(json.readed==0 || json.readed=='0'){
                    return json.messageTitle;
                }else{
                    return "<span style='color: #999999;'>"+json.messageTitle +"</span>";
                }
                 }},
            {title:"内容",name:"m",width:"16%",align:"left",format:subMessage},
            {title:"时间",name:"createTime",width:"8%",align:"left",format:function(json){
                if(json.readed==0 || json.readed=='0'){
                    return json.createTime;
                }else{
                    return "<span style='color: #999999;'>"+json.createTime +"</span>";
                }
                 }},
            {title:"操作",name:"op1",width:"8%",align:"left",format:makeOptionM}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        onlyFirstPage:false
    });
    refreshRoleTable({});
}

/**组装复选框*/
function returnCheckBox(json){
  var box="<input name='ckmessage' isread='"+json.readed+"' type='checkbox' value="+json.id+" />";
    return box;
}
/**全选或者取消全选*/
function ckOrCancle(obj){
    if($(obj).prop("checked")){
        $("input[name='ckmessage']").prop("checked",true);
    }else{
        $("input[name='ckmessage']").prop("checked",false)
    }


}
/**标记已读*/
function markReaded(sourc){
    if(sourc=='part'){
        var ids="";
        $("input[name='ckmessage']:checked").each(function(i,d){
            var isR=$(d).attr("isread");
            if(isR==0){
                ids+=$(d).val()+",";
            }
        });
        if(ids!=null && ids!=''){
            ids = ids.substr(0,ids.length-1);
            postMarkRequest({"ids":ids});
        }else{
            alert("请至少选择一个未读内容！");
        }
    }else if(sourc=='all'){
        postMarkRequest({});
    }
}
/**发起标记请求*/
function postMarkRequest(parData){
    var url=path+"/sitemessage/markReaded.do";
$().invoke(
    url,
    parData,
    [function(m,r){
        alert(r);
        refreshRoleTable({});
        Base.token();
    },
    function(m,r){
        alert(r);
        Base.token();
    }]
);
}

/**截取过长字符*/
function subMessage(json){
    if(json.message!=null && json.message.length>40){
        if(json.readed==0 || json.readed=='0'){
            return json.message.substr(0,39)+"...";
        }else{
            return "<span style='color: #999999;'>"+json.message.substr(0,39)+"..."+"</span>";
        }
    }else{
        if(json.readed==0 || json.readed=='0'){
            return json.message;
        }else{
            return "<span style='color: #999999;'>"+json.message+"</span>";
        }

    }
}

function refreshRoleTable(p){
    if(p==null){p={}}
    $("#siteMessageDiv").selectDataAfterSetParm(p);
}
function makeOptionM(json){
    var hs="";
    if(json.readed==0 || json.readed=='0'){
        hs+="<li onclick=doMessageAction(this) value='"+json.id+"' doaction=\"readed\" >标记已读</li>";
    }
    hs+="<li style='left:1px;' onclick=doMessageAction(this) value='"+json.id+"' doaction=\"look\" >查看</li>";
    var pp={"liString":hs};
    return getULSelect(pp);

    /*var select1="<div class=\"ui-select\" style=\"width:8px\">" +
        "<select onchange='doMessageAction(this)'>" ;
    select1+= "<option  value='x'>请选择</option>" ;
    if(json.readed==0 || json.readed=='0'){
        select1+= "<option  value='"+json.id+"' doaction=\"readed\">标记已读</option>" ;
    }
    select1+= "<option  value='"+json.id+"' doaction=\"look\">查看</option>" ;
    select1+= "</select></div>";
    return select1;*/
}

function doMessageAction(obj){
    var optionV=obj;//$(obj).find("option:selected");
    var v=$(optionV).attr('value');
    if(v=='x'){return;}
    var d=$(optionV).attr('doaction');
    if(d=='look'){
        openMessage(v);
    }else if(d=='readed'){
        postMarkRequest({"ids":v})
    }
    $(obj).val('x');
}

/**根据消息类型来重新查询消息列表*/
function queryBySelect(obj){
    var d=$(obj).val();
    var data={"messageType":d};
    refreshRoleTable(data);
}

/**打开消息查看窗口*/
var readMessageWindow;
function openMessage(mid){
    readMessageWindow=$.dialog({
        title:'查看信息',
        id : "digMessage" ,
        content:"url:"+path+"/sitemessage/readMessagePage.do?messageId="+mid,
        data:{"messageId":mid},
        width : 650,
        height : 300,
        max:false,
        min:false,
        lock : true

    });
}