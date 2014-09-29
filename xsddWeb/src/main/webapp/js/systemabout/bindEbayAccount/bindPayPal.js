/**
 * 添加paypal帐号页面的js
 * Created by Administrator on 2014/9/29.
 */

function submitTheForm(){
    if($('#paypalAccount').val()==null){
        alert('paypal帐号不能为空!')
        return;
    }
   var url=path+"/paypal/addPayPalAccount.do";
   var data = $('#paypalForm').serialize();
    $().invoke(
        url,
        data,
        [function(m,r){
            var api = frameElement.api, W = api.opener;
            alert(r);
            W.refreshPayPalTable();
            W.opAddPaypalPageW.close();
        },
        function(m,r){
            alert(r);
            Base.token()
        }]
    );
}
