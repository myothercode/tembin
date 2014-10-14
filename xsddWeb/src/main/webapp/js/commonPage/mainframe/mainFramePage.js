/**
 * Created by Administrator on 2014/9/18.
 * 主框架页的js
 */

/**获取菜单的方法*/
function getMenu(){
    var url=path+"/menu/getUserMenuList.do";
    $().invoke(
            url,
            {},
        [function(m,r){
            for(var i in r){
                if(r[i]['permissionLevel']=='1' || r[i]['permissionLevel']==1 ){
                    addMenuLevel1(r[i]);
                }else if(r[i]['permissionLevel']=='2' || r[i]['permissionLevel']==2){
                    addMenuLevel2(r[i]);
                }

            }
            initLeftMenuBar();
        },
        function(m,r){alert(r)}]

    );
}

/**组装一级菜单*/
function addMenuLevel1(json){
    var h="<li id='menu_1_"+json.permissionID+"'> " ;
    if(json.permissionID==1 || json.permissionID=='1'){
        h+=" <a flag='m1' class=\"dropdown-toggle1\" href="+json.permissionURL+">";
    }else{
        h+="<a flag='m1' class=\"dropdown-toggle\" href=\"javascript:void(0)\">" ;
    }
        h+="<i class=\""+json.menuClass+"\"></i>"+
        "<span>"+json.permissionName+"</span>"+
        "<i class=\"icon-chevron-down\"></i>"+
        "</a><ul class=\"submenu\"></ul>" +
        "</li>";
    $('#dashboard-menu').append(h);
}

/**添加二级菜单*/
function addMenuLevel2(json){
    var h="<li><a name='menu2' target='contentMain' " +
        "onclick='addArrow(this)' href="+json.permissionURL+">"+json.permissionName+"</a></li>";
    var parID="menu_1_"+json['parentID'];
    $("#"+parID+" .submenu").append(h);
}

/**为选中的菜单的大菜单增加箭头*/
function addArrow(obj){
    $("a[name='menu2']").css({"background-image":path+"/img/new_list_one.png","color":"666666"});
    $(obj).css({"background-image":path+"/img/new_list_one_1.png","color":"2a6496"});

    $(".pointer[flag='po']").remove();
   var arrow="<div flag='po' class=\"pointer\">"+
            "<div class=\"arrow\"></div>"+
                "<div class=\"arrow_border\"></div>"+
            "</div>";
    if(obj.className=='dropdown-toggle' && $(obj).attr('flag')=='m1'){
        $(obj).prepend(arrow);
    }else{
        var o=obj.parentNode.parentNode.parentNode;
        var parentA=$(o).find("a");
        $(parentA[0]).prepend(arrow);
    }

}

/**注销登录*/
function logout(){
    top.location=path+'/logout.do';
}

var noticeTimerId=0;
/**定时获取消息*/
function getMessagesByTime(){
    noticeTimerId = setInterval(function(){
        getSystemMessage({"jsonBean.pageNum":1,"jsonBean.pageCount":1000,"readed":0,"strV1":"num"});
    } ,300*1000);
}
/**获取ebay消息*/
function getEbayMessage(p){
    var url=path+"/message/noReadMessageGetmymessageList.do";
    var data={};
    if(p==null){
        data={"jsonBean.pageNum":1,"jsonBean.pageCount":5,"readed":"false"};
    }else{
        data=p;
    }
    $().invoke(
        url,
        data,
        [function(m,r){
            if(r==null){return;}
            var dat = r.list;
            var ht="";
            for(var i in dat){
                var subj=dat[i].subject;
                if(subj.length>15){
                    subj=subj.substring(0,15)+"...";
                }
                ht+="<a href=\"#\" onclick='openGetMessage("+dat[i].messageid+");' class=\"item\">" +
                "<i class=\"icon-download-alt\"></i>"+subj+"" +
                "<span class=\"time\"><i class=\"icon-time\"></i>"+dat[i].receivedate+"</span>" +
                "</a>";
            }
            ht+="<div class=\"footer\"><a id=\"ebayMessageLiu\" href=\"message/MessageGetmymessageList.do\" target='contentMain' class=\"logout\">View all notifications</a></div>";
            $("#notifications").html(ht);
        },
            function(m,r){}]
    );
/*    $("#ebaymessageNotice").html("You have "+ 0 +" new notifications")*/
}
function openGetMessage(messageid){
    var url=path+"/message/viewMessageGetmymessage.do";
    MessageGetmymessage=$.dialog({title: '查看消息',
        content: 'url:'+url+'?messageID='+messageid,
        icon: 'succeed',
        width:1025,
        height:500
    });
}
/**获取系统信息*/
function getSystemMessage(p){
    var url=path+"/sitemessage/selectSiteMessage.do";
    var data={};
    if(p==null){
        data={"jsonBean.pageNum":1,"jsonBean.pageCount":3,"readed":0};
    }else{
        data=p;
    }
    $().invoke(
        url,
        data,
        [function(m,r){
            if(r==null){return;}
            if(data.strV1=="num"){
                var re = eval("(" + r + ")");
                //alert(re.systemMessageNum)
                $("#systemMessageCount").html(re.systemMessageNum);
                $("#ebayMessagesCount").html(re.ebayMessageNum);
                $("#ebaymessageNotice").html("You have "+re.ebayMessageNum+" new notifications")
                return;
            }
            var dat = r.list;
            var ht="";
            for(var i in dat){
                var ddd =alySystemMessage(dat[i]);
                ht+="<a onclick='openMessage("+dat[i]["id"]+")' href=\"javascript:void(0)\" class=\"item\">"+
                "<img src=\"/xsddWeb/img/contact-img.png\" class=\"display\" />"+
                    "<div class=\"name\">"+ddd["titl"]+"</div>"+
                    "<div class=\"msg\">"+
                    ddd["cont"]+
                "</div>"+
                    "<span class=\"time\"><i class=\"icon-time\"></i> "+dat[i]["createTime"]+"</span>"+
                "</a>";
            }
            ht+="<div class=\"footer\"><a href=\"sitemessage/siteMessagePage.do\" target='contentMain' class=\"logout\">View all messages</a></div>";
            $("#messages").html(ht);
        },
        function(m,r){}]
    );
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

    var nm = $("#systemMessageCount").html();
    var n=(parseInt(nm)-1)>0?0:(parseInt(nm)-1);
    $("#systemMessageCount").html(n);
}

/**解析系统消息并着色*/
function alySystemMessage(json){
    var titl="";
    var cont="";
    if(json.message!=null && json.message.length>20){
        if(json.readed==0 || json.readed=='0'){
            cont = json.message.substr(0,19)+"...";
            titl = json.messageTitle;
        }else{
            cont = "<span style='color: #999999;'>"+json.message.substr(0,19)+"..."+"</span>";
            titl = "<span style='color: #999999;'>"+json.messageTitle+"</span>";
        }
    }else{
        if(json.readed==0 || json.readed=='0'){
            cont= json.message;
            titl = json.messageTitle;
        }else{
            cont = "<span style='color: #999999;'>"+json.message+"</span>";
            titl = "<span style='color: #999999;'>"+json.messageTitle+"</span>";
        }

    }

    return {"titl":titl,"cont":cont}
}
