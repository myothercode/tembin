/**
 * Created by Administrator on 2014/12/18.
 */
/**根据输入进行动态查询提示的select2
 * currInputName 输入框中输入的内容在post时的名字
 * other 其它参数 json格式
 * bs要初始化select2的对象，jquery的表达式
 * fun 指定要执行的方法，如果指定以后，默认构造方法不执行
 * maping  查询数据与select2数据格式的映射关系
 * 参数为[{url:"",data:{currInputName:"",other:{}},bs:"",multiple:true,fun:fun,maping:{id:"",text:""}},{}]
 * */
function mySelect2I(ps){
    if(ps==null){return;}
    if(!Base.isArray(ps)){
        ps=[ps];
    };

    for(var i in ps){
        var targ=ps[i];
        $(targ["bs"]).select2({
            multiple: targ["multiple"]==null?true:targ["multiple"],
            query: function (query){
                if(targ["fun"]){//如果指定了方法
                    (targ["fun"])(query);
                    return;
                }
                //如果使用默认构造方法
                var content=query.term;
                var queryData=targ["data"];
                var str="{'"+(queryData["currInputName"])+"':'"+content+"'}";
                eval('var jsonv='+str);
                if(queryData["other"]!=null){
                    $.extend(jsonv,queryData["other"]);
                }

                var fdata = {results: []};
                var select2Url=targ["url"];
                if(select2Url==null || select2Url==''){
                    fdata.results.push({id: '', text: content });
                    query.callback(fdata);
                    return;
                }

                if(content&&content!=""){
                    $().delayInvoke(select2Url,
                        jsonv,
                        function(m,r){
                            fdata.results.push({id: '', text: content });
                        for(var ii in r){
                            var t=targ["maping"]["text"];
                            var text11=r[ii][t];
                            var i_=targ["maping"]["id"];
                            var id_=r[ii][i_];
                            if (query.term.length == 0
                                || text11.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                                fdata.results.push({id: id_, text: text11 });
                            }
                        }
                            query.callback(fdata);
                    });
                }
            }
        });
    }


}