/**
 * Created by wula on 2014/7/26.
 */
$.ajaxSettings.traditional = true;
(function ($) {
    var _jsonpStart = 1;
    var jsonFormat = /^\s*\{[\s\S]*\}\s*$/m;
    $.fn.invoke = function (url, param, fun, config) {
        config = $.extend({}, $.fn.invoke.defaultConfig, config);
        param = param || {};

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