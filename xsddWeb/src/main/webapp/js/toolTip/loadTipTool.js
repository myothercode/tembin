/**
 * Created by Administrator on 2014/10/22.
 * 必须先引入
 * <script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
 * <link rel="stylesheet" type="text/css" href="<c:url value ="/js/toolTip/qtip2/jquery.qtip.min.css"/> "/>
 */

$(document).ready(function() {
  var resourcsJS=[path+"/js/toolTip/qtip2/jquery.qtip.min.js"];
    parallelLoadScripts(resourcsJS,function(){
        afterTipFunction("houtaikd","刊登后无需等待，你可以做其他操作，我们会以消息的形式将刊登结果通知您!");
        afterTipFunction("whzxhelp","在GTC刊登里的物品数量为0时，刊登自动对买家进行隐藏。当卖家补充物品数量后，刊登会再次在买家搜索中出现，刊登的销售等历史记录会继续保留。");
    });
});

/**
 * 显示tip
 * @param objid   tip要显示在哪个对象上
 * @param message_ 要显示的内容
 * @param defaultShow 师傅打开页面的时候就显示
 */
function afterTipFunction(objid,message_,defaultShow){
    var shoCon={ effect: function () {
            $(this).show('slide', 500);
        }};
    if(defaultShow!=null && defaultShow==true){
        shoCon=true;
    }
    /**增加提示信息*/
    var tiptt = $('#'+objid).qtip({
        content: message_,
        style: {
            classes: 'qtip-rounded qtip-shadow'
        },
        position: {
            my: 'center center',  // Position my top left...
            at: 'top center', // at the bottom right of...
            target: $('#'+objid), // my target
            adjust: {
                y: -20
            }
        },
        show: shoCon,
        hide: {
            effect: function () {
                $(this).hide('puff', 500);
            }
        }
    });

}
