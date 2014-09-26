/**
 * Created by Administrator on 2014/9/25.
 */

$(document).ready(function(){
    //getAllDevSelect();
});


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
        },function(m,r){Base.token();alert(r)}],
        {async:false}
    );
}

/**获取token*/
function fetchToken(){
    //var devAccountID=$('#devSelect').val();
    //if(devAccountID==null || devAccountID ==0){alert('请选择开发帐号');return;}
    var url=path+"/user/apiFetchToken.do";
    var name=$('#bm').val();//别名
    var code = $('#code').val();//别名代码缩写

    var data={strV1:sessid,id:"0",strV3:code,strV2:name};
    $().invoke(
        url,
        data,
        [function(m,r){
            Base.token();
            alert(r)
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