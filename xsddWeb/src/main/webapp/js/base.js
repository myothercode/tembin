/**
 * Created by wula on 2014/7/26.
 * 遮罩效果依赖于jquery.blockUI.min.js
 */
$.ajaxSettings.traditional = true;
var Base={
    isArray: function (object) {
        return object && object.constructor === Array;
    },
    isFunction: function (object) {
        return typeof object == "function";
    },
    isString: function (object) {
        return typeof object == "string";
    },
    isNumber: function (object) {
        return typeof object == "number";
    },
    isUndefined: function (object) {
        return typeof object == "undefined";
    },
    token: function (async) {
        async = async === undefined ? true : async;
        $().invoke(path+"/ajax/getToken.do", {}, function (message, re) {
            _token=re;
        }, {"async": async});
    },
    handleException: function (exception) {
        var erM='请求出错了！';
        if(exception!=null && exception.responseText!=null && exception.responseText !=''){
            erM=exception.responseText;
        }
        flashMessage_(erM);

        /*var errorDivJquery = $("#_errorDiv");
        if(!errorDivJquery.length) {
            errorDivJquery = $("<div></div>").attr("id", "_errorDiv").css("position", "absolute").css("z-indx",
                99999).css("background", "#aaaaaa").css("top", "30px").css("width", "100%").css("height",
                "80%").css("left", "0").css("color", "#ffffff").click(function () {
                    $(this).hide();
                }).appendTo("body");
        }
        errorDivJquery.html(exception.responseText).show();*/
    }
};

(function ($) {
    var jsonFormat = /^\s*\{[\s\S]*\}\s*$/m;
    $.fn.invoke = function (url, param, fun, config) {
        config = $.extend({}, $.fn.invoke.defaultConfig, config);
        param = param || {};
        //判断是方法数组还是单独的方法
        if(!Base.isArray(fun)) {
            fun = [fun];
        }
        //追加参数
        if(Base.isArray(param)) {
            param.push({name: "_random", value: Math.random()}, {name: "AjaxMode", value: "ajaxFlag"});
            if(_token!=null && _token!=''){
                param.push({name:"_token",value:_token});
            }
        } else if(typeof param == "object") {
            param["_random"] = Math.random();
            param["AjaxMode"] = "ajaxFlag";
            if(_token!=null && _token!=''){
                param["_token"] = _token;
            }
        } else if(Base.isString(param)) {
            if(param.length && param.charAt(param.length - 1) != '&')
                param += "&";
            param += "_random=" + Math.random() + "&AjaxMode=ajaxFlag";
            if(_token!=null && _token!=''){
                param+="&_token="+_token;
            }
        }

if(url.indexOf("?")==-1){
    url+="?math1="+Math.random();
}else{
    url+="&math1="+Math.random();}
        var self = this;

        if(config.isConverPage){
            $.blockUI({ message: '<h1><img src="/xsddWeb/img/busy.gif" /> Just a moment...</h1>' });
        }

        $.ajax({
            url: url,
            type: config.type,
            dataType: config.dataType,
            async: config.async,
            data: param,
            complete: function (res, status) {
                $.unblockUI();
                if(status == "success" || status == "notmodified") {
                    var responseText = res.responseText;
                    if(typeof(_invokeGetData_type)!="undefined" && _invokeGetData_type=='string'){
                        fun[0].apply(self, ["stringData", responseText]);
                        return;
                    }

                    if(jsonFormat.test(responseText)) {
                        var re = eval("(" + responseText + ")");

                        if(re["bool"] === false) {
                            if(re["message"]=='sessionStatusFalse'){//如果是session过期错误
                                alert("登陆已超时!");
                                top.location=path+'/login.jsp';
                                return;
                            }
                            fun[1] ? fun[1].apply(self, [re["message"], re["result"]]) : (alert(re["message"]));
                            return;
                        }else {
                            fun[0].apply(self, [re["message"], re["result"]]);
                        }
                        return;

                    }else{
                        alert("数据格式不对!"+responseText)
                    }


                }else if(status == "error"){
                    var responseText = res.responseText;
                    if(jsonFormat.test(responseText)) {
                        var re = eval("(" + responseText + ")");
                        fun[1] ? fun[1].apply(self, [re["message"], re["result"]]) : (alert(re["message"]));
                        return;
                    }else{
                        Base.handleException(res);
                        fun[1] ? fun[1].apply(self, ["error", "出现错误了！"]) : (alert("出现错误了！"));
                    }

                }
            }
        });
        return this;
    };
    //默认参数，异步，且以对象模式进行ajax访问
    $.fn.invoke.defaultConfig = {type: "POST", dataType: "html", async: true, ajaxMode: "ajaxFlag","isConverPage":false};


})(jQuery);



/**提供组装好的ul 下拉 {liString,ulid,showname,inputid,inputval}
 * */
function getULSelect(par){
    var lis=par["liString"];
    var ulid_=par["ulid"]==null?"":("id="+par["ulid"]);
    var inputid_=par["inputid"]==null?"":("id="+par["inputid"]);
    var inputval_=par["inputval"]==null?"":(par["inputval"]);

    var setDivStyle = par["setDivStyle"]==null?"":(par["setDivStyle"]);

    var firstLi=lis.substring(lis.indexOf("<li"),lis.indexOf("</li>")+5);
    var ldom=$(firstLi);
    var lfunction=ldom.attr("onclick");
    var lvalue=ldom.attr("value");
    var ldo=ldom.attr("doaction");
    var lhtml=ldom.html();

    var showname_=lhtml==null?"请选择":(lhtml);
    lfunction=(lfunction==null)?"":("onclick="+lfunction);
    lvalue=(lvalue==null)?"":("value="+lvalue);
    ldo=(ldo==null)?"":("doaction="+ldo);


    var sdiv="<div name=\"sdddropdown\" style='height: 25px;"+setDivStyle+"' class=\"wrapper-dropdown-5\" tabindex=\"1\">" +
        "<span "+lfunction+" "+lvalue+" "+ldo+" >"+showname_+"</span>";
    sdiv+="<ul class=\"dropdown\">";
    sdiv+=lis.replace(firstLi,"")
        .replace(/<li style='height:25px'/ig,"<li><a href=javascript:void(0) ")
        .replace(/<li style="height:25px;"/ig,"<li><a href=javascript:void(0) ")
        .replace(/<li style='left:1px;'/ig,"<li><a href=javascript:void(0) ")
        .replace(/<\/li>/ig,"</a></li>");
    sdiv+="</ul></div>";

    /*var hs="<ul style='margin-left: "+ulMarginLeft+"' "+ulid_+">" +
        "<li style='width: 70px;height: 25px;' class=\"select_box\">" +
        "<span style='color: blue;text-align: center' "+lfunction+" "+lvalue+" "+ldo+">"+showname_+"</span><ul class=\"son_ul\">";
    hs+="<input type='hidden' "+inputid_+" value="+inputval_+"  />";
    hs+=lis.replace(/style='height:25px'/ig,"style='height:25px;text-align: center'");
    hs+="</ul></li></ul>";*/
    ldom.remove();
    return sdiv;
}

function initULSelect(){
    function DropDown(el) {
        this.dd = el;
        this.initEvents();
    }
    DropDown.prototype = {
        initEvents : function() {
            var obj = this;
            obj.dd.on('mouseover', function(event){
                $(this).addClass('active');
                event.stopPropagation();
            }).on("mouseleave",function(event){
                $(this).removeClass('active');
                event.stopPropagation();
            });
        }
    }

    $("div[name='sdddropdown']").each(function(i,d){
        new DropDown($(d));
    });

    //var dd = new DropDown( $('#dd') );
    /*$(document).click(function() {
        // all dropdowns
        $('.wrapper-dropdown-5').removeClass('active');
    });*/

    /*$('.son_ul').hide(); //初始ul隐藏
    $('.select_box span').hover(function(){ //鼠标移动函数
            $(this).parent().find('ul.son_ul').slideDown();  //找到ul.son_ul显示
            $(this).parent().find('li').hover(function(){$(this).addClass('hover')},function(){$(this).removeClass('hover')}); //li的hover效果
            $(this).parent().hover(function(){},
                function(){
                    $(this).parent().find("ul.son_ul").slideUp();
                }
            );
        },function(){}
    );
    $('ul.son_ul li').click(function(){
       // $(this).parents('li').find('span').html($(this).html());
        $(this).parents('li').find('ul').slideUp();
    });*/
}


/**一闪即逝的提示*/
function flashMessage_(erM){
    $.blockUI({
        message: erM,
        fadeIn: 700,
        fadeOut: 700,
        timeout: 5000,
        showOverlay: false,
        centerY: false,
        css: {
            width: '350px',
            top: '200px',
            left: '',
            right: '10px',
            border: 'none',
            padding: '5px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: .6,
            color: '#fff'
        }
    });
}

/**google自带的提示*/
function chrome_Notice(tit,m) {
    var notification_ = null;
    var pp={"body": m,"icon":"/xsddWeb/img/smalllogopic.gif"};
    if(tit==null || tit==""){
        tit="通知";
    }
    if (!("Notification" in window)) {
        alert("This browser does not support desktop notification");
        return;
    }
    else if (Notification.permission === "granted") {
        notification = new Notification(tit,pp);
        notification.onshow = function () {
            setTimeout(notification.close.bind(notification), 5000);
        }
    }

    else if (Notification.permission === "denied") {
        flashMessage_("您设置了拒绝桌面通知");
    }
    else {
        Notification.requestPermission(function (status) {
            if (!('permission' in Notification)) {
                Notification.permission = permission;
            }
            if (permission === "granted") {
                notification = new Notification(tit,pp);
            }
        });
    }
}

/**获取页面的宽和高*/
function getCurrPageWH(){
    var eHeight=(document.documentElement.scrollHeight > document.documentElement.clientHeight) ? document.documentElement.scrollHeight : document.documentElement.clientHeight;
    //alert(eHeight)
    var eWidth=(document.documentElement.scrollWidth>document.documentElement.clientWidth) ? document.documentElement.scrollWidth : document.documentElement.scrollWidth;
    //alert(eWidth)
    return {"eHeight":eHeight,"eWidth":eWidth};
}





