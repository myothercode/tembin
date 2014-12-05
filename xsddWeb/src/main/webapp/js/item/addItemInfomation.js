/**
 * Created by Administrator on 2014/12/5.
 * 商品信息的添加或者修改页面页面
 */
$(document).ready(function () {
    $().image_editor.init("picUrls"); //编辑器的实例id
    $().image_editor.show("apicUrls"); //上传图片的按钮id
    $("#informationForm").validationEngine();
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
    })
})


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
            console.log(elem.tagName)
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


}
