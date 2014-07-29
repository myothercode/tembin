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
        if(!Base.isArray(fun)) {
            fun = [fun];
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
                    if(jsonFormat.test(responseText)) {
                        var re = eval("(" + responseText + ")");

                        if(re["bool"] === false) {
                            fun[1] ? fun[1].apply(self, [re["message"], re["result"]]) : (alert(re["message"]));
                            return;
                        }else {
                            fun[0].apply(self, [re["message"], re["result"]]);
                        }
                        return;

                    }else{
                        alert("数据格式不对")
                    }


                    }else if(status == "error"){
                    var responseText = res.responseText;
                    var re = eval("(" + responseText + ")");
                    fun[1] ? fun[1].apply(self, [re["message"], re["result"]]) : (alert(re["message"]));
                    return;
                }
            }
        });
        return this;
    };
    //默认参数，异步，且以对象模式进行ajax访问
    $.fn.invoke.defaultConfig = {type: "POST", dataType: "html", async: true, ajaxMode: "object"};


})(jQuery);