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
