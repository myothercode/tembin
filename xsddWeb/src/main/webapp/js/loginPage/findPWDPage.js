/**
 * Created by Administrator on 2014/10/29.
 */

$(document).ready(function(){
    if(safeCode_==null || safeCode_=='' || safeCode_=='null'){
        $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"210px"});
        $("#spaninfo").css("margin-top","-187px");
        $("#spaninfol").css("margin-top","-160px");
        $("#sureSu,#pwd1,#pwd2,#pwd1l,#pwd2l").hide();
        $("#safeCode,#safeCodel").hide();
        //$("#pwd1,#pwd2").prop("disabled",true);
    }else{
        $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"350px"});
        //$("#spaninfo").css("margin-top","-30px");
        $("#spaninfol").css("margin-top","-10px");
        $("#spaninfo").hide()
        $("#safeCode,#safeCodel,#loginUserID,#button1,#loginUserIDl").hide();
        $("#pwd1l").css("margin-top","-120px");
        $("#pwd1").css("margin-top","-100px");
        $("#pwd2l").css("margin-top","-80px");
        $("#pwd2").css("margin-top","-60px");
        $("#safeCode").val(safeCode_);
        $("#sid").val(sid_);
        $("#loginUserID").val(email_);

    }

});

function getSafeCode(){
    var ld=$("#loginUserID").val();
    if(ld==null || ld==''){
        alert("请输入邮箱！")
        return;
    }
    var url="/xsddWeb/systemuser/getSafeCodelogin.do";
    var data=$("#regForm").serialize();
    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r);
            $("#button1").hide();
            //$("#sureSu").show();

            //$("#pwd1,#pwd2").prop("disabled",false);
        },
            function(m,r){
                alert(r);
            }],{isConverPage:true}
    );
};

function changePassWord(){
    var ld=$("#loginUserID").val();
    var safeCode1=$("#safeCode").val();
    var pw1=$("#pwd1").val();
    var pw21=$("#pwd2").val();
    if(ld==null || safeCode1==null || pw1==null){
        $.blockUI({
            message: '<h1>帐号、验证码、密码不能为空</h1>',
            timeout: 2000
        });
        return;
    }
    if(pw1!=pw21){
        $.blockUI({
            message: '<h1>两次密码输入不一致!</h1>',
            timeout: 2000
        });
        return;
    }

    var url="/xsddWeb/systemuser/changePWDBySafeCodelogin.do";
    var data=$("#regForm").serialize();
    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r);
            document.location.href='login.jsp';
        },
            function(m,r){
                alert(r);
            }]
    );

}
