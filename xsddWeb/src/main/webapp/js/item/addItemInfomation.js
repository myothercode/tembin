/**
 * Created by Administrator on 2014/12/5.
 * 商品信息的添加或者修改页面页面
 */
var ue=null;
$(document).ready(function () {

    //zeroclipInit();
    //$().image_editor.init("picUrls"); //编辑器的实例id
    //$().image_editor.show("apicUrls"); //上传图片的按钮id
    //var ue = UE.getEditor('myEditor',ueditorToolBar);
    $("#informationForm").validationEngine();
    initEventKKButton();

    setTimeout(function(){
        loadEditor();
        initLoadImage();
    },500)

})

/**异步加载ueditor的几个js*/
function loadEditor(){
    /*var jss=[path+"/js/ueditor/ueditor.config.js",
             path+"/js/ueditor/ueditor.all.js",
             path+"/js/ueditor/lang/zh-cn/zh-cn.js",
             path+"/js/ueditor/dialogs/image/imageextend.js"];*/
    seriesLoadScripts(ueditorJSS_,function(){

        ue= UE.getEditor('myEditor',ueditorToolBar);
        $().image_editor.init("picUrls"); //编辑器的实例id
        $().image_editor.show("apicUrls"); //上传图片的按钮id
    });
}


/**初始化一个按钮事件*/
function initEventKKButton(){
    $("#kk").keydown(function(event) {
        if (event.keyCode == 13) {
            var str = $("#kk").val();
            if (isNan(str) != true) {
                var li_id = $(".label li:last-child").attr('id');
                if (li_id != undefined) {
                    li_id = li_id.split('_');
                    li_id = parseInt(li_id[1]) + 1;
                } else {
                    li_id = 0;
                }
                $(".label_box").css("display", "block");
                var text = "<a href='javascript:#' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class=\"icon-remove-sign\" style='margin-right: 2px;'></i><span >" + str + "</span><input type='hidden' name='label' value='" + str + "'></a>";
                var spans=$("#addRemark").find("span");
                for(var i=0;i<spans.length;i++){
                    var span=spans[i].innerHTML;
                    if(str==span){
                        return;
                    }
                }
                $("#kk").before(text);
            }
            $("#kk").val("");
            $("#kk").attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
        }
    });
}

function zeroclipInit(){
    $("a[id^='doCopy']").each(function(i,d){
        var clip = new ZeroClipboard($(d));
        clip.on("ready", function() {
           // debugstr("Flash movie loaded and ready.");

            this.on("aftercopy", function(event) {
               // debugstr("Copied text to clipboard: " + event.data["text/plain"]);
            });
        });

        clip.on("error", function(event) {
            $(".demo-area").hide();
            debugstr('error[name="' + event.name + '"]: ' + event.message);
            ZeroClipboard.destroy();
        });
    });


    // jquery stuff (optional)
    function debugstr(text) {
        console.log(text);
    }

/*$('#global-zeroclipboard-html-bridge').on("mouseover",function(){
    if(showPicMenuBS_==null){return;}
    $("#linkbutton"+showPicMenuBS_).show();
});*/
    $("li[id^='linkbutton']").on("mouseover",function(){
        var indexx=$(this).attr("bs");
        $(this).find("ul").show();
        showPicMenuBS_=indexx;
        var ooo=this;
        $(document).on("mouseover",function(){
            var e = event || window.event;
            var elem = e.srcElement||e.target;
            if(elem.tagName=='IMG' || elem.tagName=='A' || elem.tagName=='OBJECT'){return;}
            $(ooo).find("ul").hide();
            //console.log(elem.tagName)
            $(document).unbind("mouseover");
        });
    })



        /*.on("mouseout",function(){

        if(elem.tagName=='IMG' || elem.tagName=='A'){return;}
        //console.log(elem.tagName)
        $(this).find("ul").hide();
    });*/
}
var showPicMenuBS_=null;

function setvTab(name,cursel,n){
    if(cursel==2){
        var addPictureId=document.getElementById("addPictureId");
        if(addPictureId.innerHTML==""){
            $(addPictureId).append("<td align='center' id='lianjie'><br/><a href='javascript:void(0);' onclick='addpictureName(this)'>您还没有上传图片，马上上传</a></td>");
        }
    }
    for(i=1;i<=n;i++){
        var svt=document.getElementById(name+i);
        var con=document.getElementById("new_"+name+"_"+i);
        svt.className=i==cursel?"new_ic_1":"";
        con.style.display=i==cursel?"block":"none";
    }
    if(cursel==3){
        var myEditor12=$("#myEditor12").val();
        $("#myEditor").val(myEditor12);
        UE.getEditor('myEditor').setContent(myEditor12);
    }
}

/**编辑器的工具栏*/
var ueditorToolBar={
    toolbars:[['source','FullScreen',  'Undo', 'Redo','Bold','fontfamily', 'fontsize','cleardoc','|',
        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
        'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows',
        'splittocols','|','insertimage','imagenone', 'imageleft', 'imageright', 'imagecenter', '|','horizontal','drafts','preview','|'
    ]],
//关闭字数统计
    wordCount:false,
    //关闭elementPath
    elementPathEnabled:false
    //默认的编辑区域高度
    //initialFrameHeight:500 ,
    //initialFrameWidth:'98%'
};



function initLoadImage(){

    var changeName=document.getElementById("changeName");
    if(changeName){
        var va=changeName.innerHTML;
        if(va.length>85){
            va=va.substring(0,85);
            changeName.innerHTML=va+"...";
        }
    }
    var li=W.document.getElementById("loadremarks");
    var spans=$(li).find("span[scop=remark]");
    var date1="";
    for(var i=0;i<spans.length;i++){
        var remarkName=spans[i].innerText;
        if(i==(spans.length-1)){
            date1+=remarkName;
        }else{
            date1+=remarkName+",";
        }
    }
    var date={"names":date1};
    var id=$("#id").val();
    var url=path+"/information/ajax/addPictures.do?id="+id;
    $().invoke(url,date,
        [function(m,r){
            var div="";
            var r1= r.list;
            var r2= r.pic;
            if(r2.length>0){
                for(var i=0;i< r2.length;i++){
                    var div1=" <td class=\"spic\" style=\"margin-left: 20px;\">" +
                        "<div id=\"vspic\">" +
                        "<li bs="+i+" id='"+"linkbutton"+i+"'><a href=\"javascript:void(0)\"><img src='"+path+"/img/a1xl.png' width=\"18\" height=\"18\"></a>" +
                    "<ul id='picul"+i+"'>" +
                    "<li><a href=\"javascript:void(0)\" onclick=\"removeThis(this)\">删除</a></li>" +
                    "<li><textarea style='display: none' id='"+"nowimg"+i+"'>"+chuLiPotoUrl(r2[i].attrvalue)+"</textarea></li>" +
                    "<li><a href=\"javascript:void(0)\" id='"+"doCopy"+5+"' data='"+i+"' data-clipboard-target='nowimg"+i+"' >复制链接</a></li>" +
                    "</ul>" +
                    "</li>" +
                    "</div>" +
                    "<div class=\"a1fd\"><a href=\"javascript:void(0)\" title=\"<img  src='"+chuLiPotoUrl(r2[i].attrvalue)+"'/>\" onclick=\"bigfont('"+r2[i].attrvalue+"')\"><img  src='"+path+"/img/a1fd.png'></a></div>" +
                    "<input type=\"hidden\" name=\"Picture\" value=\""+r2[i].attrvalue+"\"><div class=\"jqzoom\" id=\""+r2[i].id+"\"><img onerror='nofind();' scop='img' src=\""+chuLiPotoUrl(r2[i].attrvalue)+"\" alt=\"shoe\"   jqimg=\""+chuLiPotoUrl(r2[i].attrvalue)+"\" width=\"120\" height=\"110\"></div></td>";
                    div+=div1;
                    //var lq=$(div1).find("a[id=d_clip_button]");
                }
                $("#addPictureId").append(div);

            }
            var addPictureId=document.getElementById("addPictureId");
            var div=$(addPictureId).find("div[id=vspic]");
            if(div.length>0) {
                $("#lianjie").remove();
            }
            zeroclipInit();
            Base.token();
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );

};