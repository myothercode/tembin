/**
 * Created by Administrator on 2014/9/19.
 */

$(document).ready(function(){
    accountManagerTab();
});

/**账户管理获取数据*/
function accountManagerTab(){
    $("#accountManager").initTable({
        url:path + "/systemuser/getSysManData.do",
        columnData:[
            {title:"姓名",name:"userName",width:"8%",align:"left"},
            {title:"角色",name:"roleName",width:"8%",align:"left"},
            {title:"邮箱",name:"userEmail",width:"8%",align:"left"},
            {title:"电话",name:"telPhone",width:"8%",align:"left"},
            {title:"状态",name:"status11",width:"8%",align:"left",format:makeStatus},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTable({});
}
/**操作*/
function makeOption1(json){
    var select1="<div class=\"ui-select\" style=\"width:8px\">" +
        "<select onchange='doAccount(this)'>" ;
    select1+= "<option  value='x'>请选择</option>" ;
    if(json.status==1 || json.status=='1'){
        select1+= "<option  value='"+json.userId+"' doaction=\"stop\">停用</option>" ;
    }else if(json.status==0 || json.status=='0'){
        select1+= "<option  value='"+json.userId+"' doaction=\"start\">启用</option>" ;
    }
    select1+= "<option  value='"+json.userId+"' doaction=\"edit\">编辑</option>" ;
    select1+= "</select></div>";
    return select1;
}
/**关于列表操作的下拉======================*/
function doAccount(obj){
    var optionV=$(obj).find("option:selected");
    var v=$(optionV).attr('value');
    if(v=='x'){return;}
    var d=$(optionV).attr('doaction');
    if(d=='edit'){
        editAccount(v);
        return;
    }
    var data={"userid":v,"doaction":d};
    $().invoke(
        path+"/systemuser/operationAccount.do",
        data,
        [function(m,r){
            alert(r);
            showStopAccount(document.getElementById("showStop"));
            Base.token();

        },
        function(m,r){
            alert(r)
            //showStopAccount(document.getElementById("showStop"));
            Base.token();

        }]
    );
}
/**打开编辑页面*/
function editAccount(useridd){
    openAdduserWindow(useridd);
}
/**关于列表操作的下拉结束======================*/

/**状态*/
function makeStatus(json){
    var imgurlpr=path+"/img/";
    if(json.status==1 || json.status=='1'){
        imgurlpr+="new_yes.png";
    }else if(json.status==0 || json.status=='0'){
        imgurlpr+="new_no.png";
    }else{
        imgurlpr+="";
    }

    return "<img src='"+imgurlpr+"' />";
}
/**显示已禁用的成员*/
function showStopAccount(obj){
    if($(obj).prop("checked")){
        refreshTable({"isShowStop":"all"})
    }else{
        $("#accountManager").deleteSpecUserParm(['isShowStop']);
        refreshTable({});
    }
}
/**刷新帐号列表*/
function refreshTable(p){
    if(p==null){p={}}
    $("#accountManager").selectDataAfterSetParm(p);
}

/**tab切换方法*/
function setTab(name,cursel,n){
    for(i=1;i<=n;i++){
        var menu=document.getElementById(name+i);
        var con=document.getElementById("con_"+name+"_"+i);
        menu.className=i==cursel?"new_tab_1":"new_tab_2";
        con.style.display=i==cursel?"block":"none";
    }
    if(cursel==4){
        queryRoleList();
    }
}

/**添加账户，弹窗*/
var openAdduserWindowD;
function openAdduserWindow(userid){
    var url=path+"/systemuser/";
    if(userid!=null){
       url+="editSubUserInit.do?userID="+userid;
    }else{
        url+="addSubUserInit.do";
    }
openAdduserWindowD = $.dialog({
    title:'',
    id : "dig" + (new Date()).getTime(),
    content:"url:"+url,
    width : 600,
    height : 600,
    max:false,
    min:false,
    lock : true

});
    url="";
}

/**修改密码*/
function changePWDFun(){
    var oldp=$('#oldPWD').val();
    var newp=$('#newPWD').val();
    var newp2=$('#newPWD2').val();
    if(oldp==null || newp==null || newp2==null){alert("原密码和新密码必须输入");return;}
    if(newp!=newp2){alert("两次输入的新密码不同！");return;}
    $().invoke(
        path+"/systemuser/changePWD.do",
        {"oldPWD":oldp,"newPWD":newp},
        [function(m,r){
            alert(r);
            $("#oldPWD,#newPWD,#newPWD2").val('');
            Base.token();
        },
        function(m,r){
            alert(r)
            Base.token();
        }]
    );
}