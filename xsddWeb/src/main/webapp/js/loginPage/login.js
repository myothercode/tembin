/**
 * Created by Administrator on 2014/10/28.
 */
$(document).ready(function(){
    if(!ischrom()){
        alert("请使用Chrome浏览器！")
    }
    $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"280px"})
    if(errMessage!=null && errMessage!='' && errMessage!='null'){
        alert(errMessage)
    }
    if(showCapImage!=null && showCapImage!='' && showCapImage != 'null'){
        $("#capli").show();
        changeImg();
        $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"350px"})
    }
    $(document).on("keydown",function(e){
        if(e.keyCode==13){subLoginForm()}
    });
readUserInfoCookies();
});

function subLoginForm(){
    if(!ischrom()){
        alert("请使用Chrome浏览器！")
        return;
    }
    try{
        setUserInfoCookies();
    }catch (e){
        console.log(e)
    }

    $("#loginf").submit();
}

//判断是否是googlr
function ischrom(){
    var isChrome = window.chrome && (window.navigator.userAgent.indexOf("Chrome")>-1);
    return isChrome;
}

//跳转到注册页面
function redirtRegPage(){
    if(!ischrom()){
        alert("请使用Chrome浏览器！")
        return;
    }
    document.location.href='reg.jsp';
}
//跳转到找回密码页面
function findPassWd(){
    if(!ischrom()){
        alert("请使用Chrome浏览器！")
        return;
    }
    document.location.href='findPWD.jsp';
}

function changeImg(){
    $("#capImg").hide().attr('src', '/xsddWeb/captchaActionlogin.do?x=' + Math.floor(Math.random()*100) ).fadeIn();
}
function Encrypt(word){
    var key = CryptoJS.enc.Utf8.parse("0102030405060708");
    var iv  = CryptoJS.enc.Utf8.parse('0102030405060708');
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC});
    return encrypted.toString();
}
function Decrypt(word){
    var key = CryptoJS.enc.Utf8.parse("0102030405060708");
    var iv  = CryptoJS.enc.Utf8.parse('0102030405060708');
    var decrypt = CryptoJS.AES.decrypt(word, key, { iv: iv,mode:CryptoJS.mode.CBC});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}

function jzcheck(ck){
    if(!$(ck).prop("checked")){
        clearCook();
    }
}

function setUserInfoCookies(){
    //$.cookie('cookieName','cookieValue',{expires:7,path:'/',domain: 'chuhoo.com',secure:false,raw:false});
    if(!$("#jzw").prop("checked")){
        return;
    }
    $.cookie('TemBincookieName1122553547556',(Encrypt($("#loginId").val())),{expires:7});
    $.cookie('TemBincookiePass1122553547556',(Encrypt($("#password").val())),{expires:7});
    //注：domain：创建cookie所在网页所拥有的域名；secure：默认是false，如果为true，cookie的传输协议需为https；raw：默认为false，读取和写入时候自动进行编码和解码（使用encodeURIComponent编码，使用decodeURIComponent解码），关闭这个功能，请设置为true。
}

function readUserInfoCookies(){
    var namet=$.cookie('TemBincookieName1122553547556');
    var passt=$.cookie('TemBincookiePass1122553547556');
    if(namet!=null){
        namet=Decrypt(namet);
        $("#loginId").val(namet);
    }
    if(passt!=null){
        passt=Decrypt(passt);
        $("#password").val(passt)
        $("#jzw").prop("checked",true);
    }
}
function clearCook(){
    $.cookie('TemBincookieName1122553547556',null);
    $.cookie('TemBincookiePass1122553547556',null);
}

