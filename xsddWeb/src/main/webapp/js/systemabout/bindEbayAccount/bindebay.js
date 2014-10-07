/**
 * Created by Administrator on 2014/9/25.
 */

$(document).ready(function(){
    //getAllDevSelect();
    if(ebayId!=null && ebayId!=""){
        $('#pageTitle').html("ebay账户编辑");
        $("div[id^='action']").hide();
        $("#action3").show();
    }
});

var api = frameElement.api, W = api.opener;
/**打开*/
function getBindParm(){
    var devAccountID=$('#devSelect').val();
    //if(devAccountID==null || devAccountID ==0){alert('请选择开发帐号');return;}
    var url=path+"/user/apiGetSessionID.do";
    var data={id:devAccountID};
    $().invoke(
        url,
        data,
        [function(m,r){
            Base.token();
            var rr= $.parseJSON(r)
            sessid=rr.sessionid;
            runName=rr.runName;
            tokenParm="?SignIn&RuName="+rr.runName+"&SessID="+rr.sessionid;
            window.open(tokenPageUrl+tokenParm);

            $.dialog({title: '警告',
                id: 'Alert',
                zIndex: 99999999999999999999,
                icon: 'alert.gif',
                fixed: true,
             //   lock: true,
                content: "请确认授权成功后继续！",
                ok: true,
                resize: false,
                close: function(){$('#action1').hide();$('#action2').show()},
                parent: api || null}
            );
        },function(m,r){Base.token();alert(r)}],
        {async:false}
    );
}

/**获取token*/
function fetchToken(a){
    var name=$('#bm').val();//别名
    var code = $('#code').val();//别名代码缩写
    var payPalId=$("#paypalAccountId").val();
    if(name==null || code==null){alert("请填入别名和简写代码！");return;}
    //var devAccountID=$('#devSelect').val();
    //if(devAccountID==null || devAccountID ==0){alert('请选择开发帐号');return;}
    var url=path+"/user/";
    var data;
    if(a==null){
        url+="apiFetchToken.do";
        data={strV1:sessid,id:"0",strV3:code,strV2:name,payPalId:payPalId};
    }else if(a=="edit"){
        url+="doEditEbayAccount.do";
        data={id:ebayId,strV3:code,strV2:name,payPalId:payPalId};
    }


    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r);
            W.refreshRoleTable({});
            W.bindEbayWindow.close();
        },function(m,r){Base.token();alert(r)}],
        {async:false}
    );

}

/**重新打开页面*/
function reOpenPage(){
    window.open(tokenPageUrl+tokenParm);
}

/**组装选择开发帐号的*/
function getAllDevSelect(){
    var url=path+"/user/queryAllDev.do";
    var data={};
    $().invoke(
        url,
        data,
        [function(m,r){
            for(var i in r){
                var op="<option value='"+ r[i].id+"'>"+ r[i].devUser+"</option>";
                $('#devSelect').append(op);
            }
        }]
    );
}

/**弹出选择绑定paypal帐号提示框*/
var selectPayPalPage;
function selectPaypalWindow(){
   var url=path+"/paypal/selectPayPalPage.do";
    selectPayPalPage=$.dialog({
        title:'选择PayPal帐号',
        id : "dig" + (new Date()).getTime(),
        content:"url:"+url,
        width : 700,
        height : 500,
        max:false,
        min:false,
        lock : true,
        zIndex: 9999,
        parent:api
    });
}

/**将填入的别名进行缩写处理规则为提取字符串中的大写字母和数字的最后一位*/
function getShortName(obj){
    var syy=obj.value;
    if(syy==null || syy==''){return;}

    /**如果没有数字，又没有大写字母*/
    if(!/[A-Z]/g.test(syy) && !/[0-9]/g.test(syy)){
        $('#code').val(syy.substr(0,1).toLocaleUpperCase()+""+subRight(syy,1).toLocaleUpperCase());
        return;
    }

    var shortn;
    if(/[A-Z]/.test(syy.substr(0,1))){
        shortn=getUpperChar(syy);
    }else{
        var firstWord=syy.substr(0,1).toLocaleUpperCase();
        shortn=firstWord+""+getUpperChar(syy);
    }
    var nummm=subRight(strGetNum(syy),1)
    $('#code').val(shortn+nummm)
}