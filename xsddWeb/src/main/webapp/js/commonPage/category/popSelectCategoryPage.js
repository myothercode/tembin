/**
 * Created by Administrtor on 2014/8/16.
 */

$(document).ready(function(){
    getMenuData();
   // getRese();
})
/**这个就是最终选择的值*/
var _finalSelectedVal='';
function getRese(){
    _invokeGetData_type=null;
    $("#rese1").initTable({
        url:path + "/ajax/getReseCategoryMenu.do?title="+title,
        columnData:[
            {title: "选项", name: "option1", width: "8%", align: "left", format: showRadio},
            {title:"ID",name:"categoryId",width:"8%",align:"left"},
            {title:"名称",name:"categoryName",width:"8%",align:"left"}
        ],
        selectDataNow:true,
        isrowClick:false,
        showIndex:false
    });
    isSearchedCa=true;
}
/**组装操作选项*/
function showRadio(json) {
    var htm = "<input type=\"radio\" name=\"cateId\" showname="+json.categoryName+" value=" + json.categoryId + " onclick='selectCheck(this)'>";
    return htm;
}
var selectFlag = "";
function selectCheck(obj){
    if($(obj).prop("checked")){
        selectFlag="radio";
        _finalSelectedVal=$(obj).val();
        $("#menuPath").text($(obj).attr("showname"));
    }
}

/**添加div到页面*/
function addDvi(){
    var n=makeDivId();
    var id="div_"+ n;
    var className="divl_buju_left";
    if(n%2 ==0){
        className="divl_buju_right";
    }
    var div="<div id="+id+" class="+className+" style=\"width: 250px;height:160px\">1</div>";
    $('#mainDiv').append(div);
    return id;
}
function makeDivId(){
    var n=$("#mainDiv div").length;
    return (parseInt(n)+1);
}


/**获取菜单*/
var isSearchedCa=false;
var _invokeGetData_type=null;
function getMenuData(parentID,level){
    removeDiv(level);
    if(localStorage.getItem("category_menu_"+parentID)!=null){
        var json= eval("(" + localStorage.getItem("category_menu_"+parentID) + ")");
        var jdata=json.result;
        makeMutilSelect(jdata,level);
    }else{
        _invokeGetData_type="string";
        var url=path+"/ajax/getCategoryMenu.do";
        if(parentID==null){parentID=0;level=1;}
        var data={"parentID":parentID};
        $().invoke(
            url,
            data,
            function(m,r){
                localStorage.setItem("category_menu_"+parentID,r);
                var json= eval("(" + localStorage.getItem("category_menu_"+parentID) + ")");
                var jdata=json.result;
                makeMutilSelect(jdata,level);

            },
        {async: true}
        )
    }
}
function showDiv(obj){

    if($(obj).attr("name")=="rese"){
        if(isSearchedCa==false){
            getRese();
        }

        $("#rese1").show();
        $("#choose").hide();
    }else{
        $("#choose").show();
        $("#rese1").hide();
    }
}
/**组装多行select*/
function makeMutilSelect(json,level){
    if(json==null || json.length==0){return;}
    var domnum=$("#mainDiv").find('#div_'+level).length;
    var divid;
    if(domnum==0){
        divid=addDvi();
    }else{
        divid='div_'+level;
    }
    var select="<select size='8' style='width: 250px' multiple=\"multiple\">";
    for(var i in json){
        select+="<option onclick=openSubList('"+level+"',this) value='"+(json[i]['itemId'])+"'>"+(json[i]['itemEnName'])+"</option>";
    }
    select+="</select>"
$('#'+divid).html(select);
}

/**判断需要移除的div*/
function removeDiv(level){
    if(level==undefined){return}
    var levelInt=parseInt(level);
    $("#mainDiv").find('div[id^=div_]').each(function(i,d){
        var idn= strGetNum(d.id.replace("_",""));
        if(parseInt(idn)>level){
            $(d).remove();
        }
    });

}

/**点击option后执行的操作*/
function openSubList(level,obj){
    getMenuData(obj.value,(parseInt(level)+1));
    showMenuPath();
    _finalSelectedVal=obj.value;
}


/**设置选中值的层级路径*/
function showMenuPath(){
    var p="";
    $('select').each(function(i,d){
        var txt=$(d).find("option:selected").text();
        if(txt!=''){
            p+=(txt+" => ")
        }
    });

    $('#menuPath').html(p.substr(0, p.length-3));
    p='';
}
var api = frameElement.api, W = api.opener;
//点击确定按扭
function que(){
    if(selectFlag=="radio"){
        var url=path+"/ajax/saveReseCategory.do?categoryId="+_finalSelectedVal+"&categoryName="+$("#menuPath").text()+"&categoryKey="+title;
        var data=null;
        $().invoke(url,data,
            [function(m,r){
                var rr= $.parseJSON(r)
                W.document.getElementById("PrimaryCategory").value=_finalSelectedVal;
                W.document.getElementById("PrimaryCategoryshow").innerHTML= rr.pathstr;
                W.CategoryType.close();

            },
                function(m,r){
                    alert(r);
                }]
        );
    }else{
        W.document.getElementById("PrimaryCategory").value=_finalSelectedVal;
        W.document.getElementById("PrimaryCategoryshow").innerHTML=$("#menuPath").text();
        W.CategoryType.close();
    }
}


/**选择还是检索页面切换*/



