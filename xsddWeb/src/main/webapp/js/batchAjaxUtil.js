/**
 * Created by Administrtor on 2014/8/12.
 * 需要批量请求ajax的时候，使用此方法，避免并发过大
 * 参数形式[{"callBackFunction":xxxx,"url":"diz","k":"v","k":"v"}]
 */
function batchPost(params) {
    if (params == null || params.length == 0) {
        alert('请求参数不能为空');
        return;
    }
    if(!$.isArray(params)){
        params=[params];
    }
    for (var i in params) {
        var p = params[i];
        var callF = p.callBackFunction;
        if(callF==null){return}
        var url = p.url;
        if(url==null){return}
        delete p.callBackFunction;
        delete p.url;
        $('body').queue(function () {
            $().invoke(
                url,
                p,
                [function(m,r){
                    var jsonFormat1 = /^\s*\{[\s\S]*\}\s*$/m;
                    if(jsonFormat1.test(r)){
                        r=eval("(" + r + ")");
                    }
                    callF(r);
                    $('body').dequeue();
                },
                    function(m,r){alert(r)}]
            );
        });
    }
}

/*
$(document).ready(function(){
    var d=[{"callBackFunction":xxx,"url":path+"/test2.do","tt":"v1","tt1":"v1"},
        {"callBackFunction":xxx,"url":path+"/test2.do","tt":"x2"},
        {"callBackFunction":xxx,"url":path+"/test2.do","tt":"v3"}
    ];
    batchPost(d);
})

function xxx(op) {

    // alert(op)
}*/



/**提供方法，实现资源的异步加载*/
/**
 * var scripts = [
 "http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js",
 "http://wellstyled.com/files/jquery.debug/jquery.debug.js"
 ];
 这两个文件分别是 jQuery 1.4.的库文件和 jQuery Debug 插件
 然后你可以使用下面的方法调用并在成功后执行回调了。
 seriesLoadScripts(scripts,function(){
   /*
   debug = new $.debug({
       posTo : { x:'right', y:'bottom' },
       width: '480px',
       height: '50%',
       itemDivider : '<hr>',
       listDOM : 'all'
   });

alert('脚本加载完成啦');
});
 * 串联加载指定的脚本
 * 串联加载[异步]逐个加载，每个加载完成后加载下一个
 * 全部加载完成后执行回调
 * @param array|string 指定的脚本们
 * @param function 成功后回调的函数
 * @return array 所有生成的脚本元素对象数组
 */
function seriesLoadScripts(scripts,callback) {
    if(typeof(scripts) != "object") var scripts = [scripts];
    var HEAD = document.getElementsByTagName("head").item(0) || document.documentElement;
    var s = new Array(), last = scripts.length - 1, recursiveLoad = function(i) {  //递归
        s[i] = document.createElement("script");
        s[i].setAttribute("type","text/javascript");
        s[i].onload = s[i].onreadystatechange = function() { //Attach handlers for all browsers
            if(!/*@cc_on!@*/0 || this.readyState == "loaded" || this.readyState == "complete") {
                this.onload = this.onreadystatechange = null; this.parentNode.removeChild(this);
                if(i != last) recursiveLoad(i + 1); else if(typeof(callback) == "function") callback();
            }
        }
        s[i].setAttribute("src",scripts[i]);
        HEAD.appendChild(s[i]);
    };
    recursiveLoad(0);
}

/**
 * 并联加载指定的脚本
 * 并联加载[同步]同时加载，不管上个是否加载完成，直接加载全部
 * 全部加载完成后执行回调
 * @param array|string 指定的脚本们
 * @param function 成功后回调的函数
 * @return array 所有生成的脚本元素对象数组
 */
function parallelLoadScripts(scripts,callback) {
    if(typeof(scripts) != "object") var scripts = [scripts];
    var HEAD = document.getElementsByTagName("head").item(0) || document.documentElement, s = new Array(), loaded = 0;
    for(var i=0; i<scripts.length; i++) {
        s[i] = document.createElement("script");
        s[i].setAttribute("type","text/javascript");
        s[i].onload = s[i].onreadystatechange = function() { //Attach handlers for all browsers
            if(!/*@cc_on!@*/0 || this.readyState == "loaded" || this.readyState == "complete") {
                loaded++;
                this.onload = this.onreadystatechange = null; this.parentNode.removeChild(this);
                if(loaded == scripts.length && typeof(callback) == "function") callback();
            }
        };
        s[i].setAttribute("src",scripts[i]);
        HEAD.appendChild(s[i]);
    }
}







