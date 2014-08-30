/**
 * Created by wula on 2014/7/26.
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
        var errorDivJquery = $("#_errorDiv");
        if(!errorDivJquery.length) {
            errorDivJquery = $("<div></div>").attr("id", "_errorDiv").css("position", "absolute").css("z-indx",
                99999).css("background", "#aaaaaa").css("top", "30px").css("width", "100%").css("height",
                "80%").css("left", "0").css("color", "#ffffff").click(function () {
                    $(this).hide();
                }).appendTo("body");
        }
        errorDivJquery.html(exception.responseText).show();
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
    url+="&math1="+Math.random();
}
        var self = this;
        $.ajax({
            url: url,
            type: config.type,
            dataType: config.dataType,
            async: config.async,
            data: param,
            complete: function (res, status) {
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
    $.fn.invoke.defaultConfig = {type: "POST", dataType: "html", async: true, ajaxMode: "ajaxFlag"};


})(jQuery);



