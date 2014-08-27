/**
 * Created by Administrtor on 2014/8/26.
 */


    $(document).ready(function(){
        var ppath = $('#templateViewUrl').val();
        if(ppath==null || ppath==''){return;}
        $('#multipartFiles').after("<img style=\"width: 100px; height: 100px;\" id='templateViewPic' src="+imageUrlPrefix+ppath+" />")
    });

/**编辑器的工具栏*/
var ueditorToolBar={
    toolbars:[['source','FullScreen',  'Undo', 'Redo','Bold','fontfamily', 'fontsize','cleardoc','|',
    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows',
    'splittocols','|','insertimage','imagenone', 'imageleft', 'imageright', 'imagecenter', '|','horizontal','preview','|'
]],
//关闭字数统计
    wordCount:false,
    //关闭elementPath
    elementPathEnabled:false
    //默认的编辑区域高度
    //initialFrameHeight:500 ,
    //initialFrameWidth:'98%'
};

/**增加模板缩略图*/
function uploadViewImg(){
    if($('#multipartFiles').val()==null || $('#multipartFiles').val()==''){
        alert('请选择图片');
        return;
    }
    $('#templateViewPic').remove();
    $('#templateViewUrl').val('')
    $.ajaxFileUpload({
        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
        url:path+'/upLoadImage.do',
        secureuri:false,                       //是否启用安全提交,默认为false
        fileElementId:'multipartFiles',           //文件选择框的id属性
        files:[$('#multipartFiles')],
        dataType:'text',                       //服务器返回的格式,可以是json或xml等
        data:{},
        fileFilter:".jpg,.gif",
        fileSize:1000000,//1000是1k
        success:function(data, status){        //服务器响应成功时的处理函数
            if(data=='nofile'){alert('没有选择文件');return;};
            $('#templateViewUrl').val(data)
            $('#multipartFiles').after("<img style=\"width: 100px; height: 100px;\" id='templateViewPic' src="+imageUrlPrefix+data+" />")
            //alert(data);
        },
        error:function(data, status, e){ //服务器响应失败时的处理函数
            alert('图片上传失败，请重试！！');
        }

    });
}



/**新增插入空白图片按钮*/
var _UE_ME;
UE.registerUI('blankimg',function(editor,uiName){
    _UE_ME = this;
    editor.registerCommand(uiName,{
        execCommand:function(){
            var imgsrc=imageUrlPrefix+"/systemIMG/blank.jpg";
            _UE_ME.execCommand('insertHtml',"<img style=\"width: 200px; height: 180px;\" name='blankimg' src=" + imgsrc + " />&nbsp;" );
        }
    });

    //创建一个button
    /*var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:'插入标识图片',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -500px 0;',
        //点击时执行的命令
        onclick:function () {
            var imgsrc=imageUrlPrefix+"/systemIMG/blank.jpg";
            //_UE_ME.execCommand('insertHtml',"<span id=\"WRAPDOM_ELECTRONIC_SIGNATURE\" style=\"BACKGROUND-COLOR: rgb(215,227,188)\"><img name='blankimg' onerror=this.parentNode.innerHTML='"+imgsrc+"' src=" + imgsrc + " /></span>&nbsp;" );
            _UE_ME.execCommand('insertHtml',"<img style=\"width: 200px; height: 180px;\" name='blankimg' src=" + imgsrc + " />&nbsp;" );

        }
    });
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiName);
        if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
        } else {
            btn.setDisabled(false);
            btn.setChecked(state);
        }
    });
    return btn;*/
});

/**新增插入Product Detail*/
UE.registerUI('paymentmethod',function(editor,uiName) {
    editor.registerCommand(uiName, {
        execCommand: function () {
            _UE_ME.execCommand('insertHtml',"{PaymentMethod}" );
        }
    });
});
UE.registerUI('paymentmethodtitle',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{PaymentMethodTitle}" );
        }
    });
});

/**新增插入ShippingDetail*/
UE.registerUI('shippingdetail',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{ShippingDetail}" );
        }
    });
});
UE.registerUI('shippingdetailtitle',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{ShippingDetailTitle}" );
        }
    });
});

/**新增插入SalesPolicy*/
UE.registerUI('salespolicy',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{SalesPolicy}" );
        }
    });
});
UE.registerUI('salespolicytitle',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{SalesPolicyTitle}" );
        }
    });
});

/**新增插入AboutUs*/
UE.registerUI('aboutus',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{AboutUs}" );
        }
    });
});
UE.registerUI('aboutustitle',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{AboutUsTitle}" );
        }
    });
});

/**新增插入ContactUs*/
UE.registerUI('contactustitle',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{ContactUsTitle}" );
        }
    });
});
UE.registerUI('contactus',function(editor,uiName){
    editor.registerCommand(uiName,{
        execCommand:function(){
            _UE_ME.execCommand('insertHtml',"{ContactUs}" );
        }
    });
});
