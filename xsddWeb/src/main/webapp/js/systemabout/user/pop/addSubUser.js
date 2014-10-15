/**
 * 添加成员页面d js
 * Created by Administrator on 2014/9/23.
 */

$(document).ready(function(){
    getCurAllRole();
    getCurAllEbay();
    selectActionByURL();
});
/**根据本页地址决定是做什么操作*/
    function selectActionByURL(){
    var selfurl=document.location.href;
    if(selfurl.indexOf("editSubUserInit.do?userID")>-1){
        $('#titleleg').html("编辑成员");
        $("input[name='loginID']").prop("disabled",true);
    }
}

var _role=null;
var _ebay=null;

/**获取当前登录用户所有可用的角色*/
function getCurAllRole(){
    $().invoke(
        path+"/systemuser/queryMySpecAllRole.do",
        {},
        [function(m,r){
            if(r==null || r.length==0){return;}
            var html="";
            for(var i in r){
                html+="<label class=\"checkbox inline\">" +
                    "<input  name='roles[0].roleID' type=\"checkbox\" value='"+(r[i]["roleID"])+"'>" + //class='validate[required] checkbox'
                    (r[i]["roleName"])+"</label>";
            }
            $('#roleList').html(html);
            html="";
            _role="1";
            getUserAccountInfo()
        }]
    );
}

/**编辑页面获取用户信息*/
function getUserAccountInfo(){
    if(_ebay==null || _role==null){return;}
    if(userID==null||userID==""){
           return;
    }
    var data={"userID":userID};
    var url=path+"/systemuser/queryUserAccountInfo.do";
    $().invoke(
        url,
        data,
        [function(m,r){
            $("input[name='name']").val(r.name);
            $("input[name='loginID']").val(r.loginID);
            $("input[name='email']").val(r.email);
            $("input[name='phone']").val(r.phone);
            $("input[name='address']").val(r.address);

            var ebays= r.ebays;
            var roles= r.roles;

            for(var i in ebays){
                $("input[name^='ebays'][value="+(ebays[i]["id"])+"]").prop("checked",true);
            }
            for(var i in roles){
                $("input[name^='roles'][value="+(roles[i]["roleID"])+"]").prop("checked",true);
            }

            _role=null;
            _ebay=null
        }]
    );
}

/**获取当前登录用户所有可用的绑定的ebay帐号*/
function getCurAllEbay(){
    $().invoke(
            path+"/systemuser/queryMySpecAllEbay.do",
        {},
        [function(m,r){
            if(r==null || r.length==0){return;}
            var html="";
            for(var i in r){
                html+="<label class=\"checkbox inline\">" +
                    "<input  name='ebays[0].id' type=\"checkbox\" value='"+(r[i]["id"])+"'>" + //class='validate[required] checkbox'
                    (r[i]["ebayName"])+"</label>";
            }
            $('#ebayList').html(html);
            html="";
            _ebay="1";
            getUserAccountInfo()
        }]
    );
}

function checkCheckBox(){
    var roles=$("input[type='checkbox'][name^='roles']:checked").length;
    if(roles==0){alert("必须选择至少一个角色");return false;}
    var ebays=$("input[type='checkbox'][name^='ebays']:checked").length;
    if(ebays==0){alert("必须选择至少一个ebay帐号");return false;}
    return true;
}
/**提交表单*/
function submitf(){
    if(!checkCheckBox()){
        return;
    }
    jQuery("#addSunUserForm").validationEngine();

    if(!jQuery("#addSunUserForm").validationEngine("validate"))
    {
        return;
    }

    domReIndex("roleList","roles");
    domReIndex("ebayList","ebays");
    var data=$("#addSunUserForm").serialize();

    var url=path+"/systemuser/";
    if(userID!=null && userID!=''){
        url+="editSubUser.do?userID="+userID;
    }else{
        url+="addSubUser.do";
    }

    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r)
            var api = frameElement.api, W = api.opener;
            W.showStopAccount(W.document.getElementById("showStop"));
            W.openAdduserWindowD.close();
        },
        function(m,r){
            alert(r)
            Base.token();
        }]
    )
}