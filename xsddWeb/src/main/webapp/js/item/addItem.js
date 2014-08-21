/**
 * Created by Administrtor on 2014/8/15.
 * 类别id，存放属性大类的div id，点击div后执行的方法名,选择属性值的table
 * getCategorySpecificsData("63516","attList","afterClickAttr","attTable");
 * 在提交之前调用asyCombox2InputData()同步数据
 *
 * 页面上应该定义两个元素
 * <div id="attList"></div>
 <table id="attTable">
 <tr>
 <th>属性名</th>
 <th>属性值</th>
 <th>操作</th>
 </tr>
 </table>
 * */

var _invokeGetData_type=null;
function getCategorySpecificsData(id,indiv,funName,attTable){
    _invokeGetData_type="string";
    $('#'+indiv).html('');
    if(localStorage.getItem("category_att_ID"+id)!=null){
        var json= eval("(" + localStorage.getItem("category_att_ID"+id) + ")");
        var jdata=json.result;
        getAttMainMenu(jdata,indiv,funName,attTable);
    }else{
        var url=path+"/ajax/getCategorySpecifics.do";
        var data={"parentCategoryID":id};
        $().invoke(
            url,
            data,
            [
                function(m,r){
                    localStorage.setItem("category_att_ID"+data.parentCategoryID,r);
                    var json= eval("(" + localStorage.getItem("category_att_ID"+data.parentCategoryID) + ")");
                    var jdata=json.result;
                    getAttMainMenu(jdata,indiv,funName,attTable);
                    //alert(localStorage.getItem("aaa").length);
                },
                function(m,r){alert(r)}
            ]
        );
    }
}
/**获取属性的种类列表*/
function getAttMainMenu(json,indiv,funName,attTable){
    _invokeGetData_type=null;
    if((json==null || json.length==0)||json[0]['itemEnName']=='noval'){return;}
    var m=new Array();
    for(var i in json){
        var m1=json[i]['itemId'];
        m.push(m1);
    }
    var finalm=arrDistinct(m);
    var parentid=json[0]['itemParentId'];
    for(var i in finalm){
        var domid=replaceTSFH((finalm[i]));
        var dv="<div id=div"+domid+" onclick="+funName+"(this,'"+parentid+"','"+attTable+"') class='att_mb-tag'>"+(finalm[i])+"</div>";
        $('#'+indiv).append(dv);
    }
    m=null;
}

/**筛选出满足条件的数据*/
function queryData(queryParm,parentid){
    var json1= eval("(" + localStorage.getItem("category_att_ID"+parentid) + ")");
    var json=json1.result;
    return  $.grep(json,function(n,i){
        return n['itemId']==queryParm;
    });
}

/**点击div后的事件*/
function afterClickAttr(obj, parentid,attTable) {
    var domid=replaceTSFH($(obj).html());
    var optionData = queryData($(obj).html(), parentid);
    var select = "<select id=\""+"_select"+domid+"\" class='easyui-combobox' style='width:200px;'>";
    for (var i in optionData) {
        select += "<option value=\"" + optionData[i]['itemEnName'] + "\">" + optionData[i]['itemEnName'] + "</option>";
    }
    select += "</select>";

    var tr = "<tr id=tr"+domid+" >" + "<td><input type='hidden' name='name' value=\""+$(obj).html()+"\" >" +
        $(obj).html() + "</td>" +
        "<td><input id=_value"+domid+" type='hidden' name='value'>" + select + "</td>" +
        "<td>" + "<a href='javascript:void(0)' onclick='removeThisTr(this)'>移除</a>" + "</td></tr>";
    $('#'+attTable).append(tr);
    $(obj).hide();
    $.parser.parse();
}

/**同步combox的数据*/
function asyCombox2InputData(){
    //alert($("#ddd").combobox('getValue'))
    $("select[id^='_select']").each(function(i,d){
        var baseID=d.id.substr(7, d.id.length-7);
        $('#_value'+baseID).val($("#_select"+baseID).combobox('getValue'))
    });

}

function removeThisTr(a){
    var tr= a.parentNode.parentNode;
    var baseId= tr.id.substr(2, tr.id.length-2);
    $('#div'+baseId).show();
    $(tr).remove();
}



/**对商品图片div进行重新排序*/
function reSortItemPic(){
    var m=new Map();
    $('#picture div').each(function(i,d){
        var k=$(d).css("top");
        if(m.containsKey(k)){
            var kkArr= m.get(k);
            kkArr.push(d);
        }else{
            var ka=new Array();
            ka.push(d);
            m.put(k,ka);
        }
    });
    var ks=m.keys;
    var domArr;
    for(var i in ks){
        var dArrKey=ks[i];
        domArr = m.get(dArrKey);
        domArr.sort(function(a,b){
            var al=parseInt(strGetNum($(a).css("left"))) ;
            var bl=parseInt(strGetNum($(b).css("left"))) ;
            if(al>bl){
                return 1;
            }else if(al==bl){
                return 0;
            }else{
                return -1;
            }
        });
    }
    $('#picture').empty();
    for ( var ii in domArr){
        $('#picture').append($(domArr[ii])[0].outerHTML);

    }
    initDraug();
    domArr=null;
    m.clear();
}
/**保存并提交*/
function saveData(objs,name) {
    asyCombox2InputData();//同步comebox的数值
    reSortItemPic();//对经过排序的图片进行重新排列
    domReIndex("picture","PictureDetails");//对重新排列后的元素进行重新索引

    $("#dataMouth").val(name);
    if(!$("#form").validationEngine("validate")){
        return;
    }
    var pciValue = new Map();
    $("#moreAttrs tr td:nth-child(4)").each(function (i,d) {
        if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
            pciValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
        }
    });
    for(var i=0;i<pciValue.keys.length;i++){
        $("input[type='hidden'][name='"+pciValue.keys[i]+"']").each(function(ii,dd){
            $(dd).prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].PictureURL["+ii+"]");
        });
        $("input[type='hidden'][name='VariationSpecificValue_"+pciValue.keys[i]+"']").prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].VariationSpecificValue");
    }

    var nameList = $("input[type='text'][name='name']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var valueList = $("input[type='text'][name='value']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });

    $("input[type='text'][name='attr_Name']").each(function(i,d){
        var t="Variations.VariationSpecificsSet.NameValueList["+i+"].Name";
        $(d).prop("name",t);
    });
    var len = $("#moreAttrs").find("tr").find("td").length/$("#moreAttrs").find("tr").length-4;
    for(var j=0;j<len ;j++){
        $("#moreAttrs tr:gt(0) td:nth-child("+(j+4)+")").each(function (i,d) {
            $(d).find("input[name='attr_Value']").each(function(ii,dd){
                $(dd).prop("name","Variations.VariationSpecificsSet.NameValueList["+j+"].Value["+i+"]");
            });
        });
    }
    $("#moreAttrs tr:gt(0)").each(function(i,d){
        $(d).find("input[name='SKU'],input[name='StartPrice.value'],input[name='Quantity']").each(function(ii,dd){
            var name_ = $(dd).prop("name");
            $(dd).prop("name","Variations.Variation["+i+"]."+name_);
        });
    });



    $("#Description").val(myDescription.getContent());
    var data = $('#form').serialize();
    var urll = "/xsddWeb/saveItem.do";
    $(objs).attr("disabled",true);
    var api = frameElement.api, W = api.opener;
    $().invoke(
        urll,
        data,
        [function (m, r) {
            //Base.token();
            alert(r);
            $(objs).attr("disabled",false);
            W.refreshTable();
            W.returnItem.close();
        },
            function (m, r) {
                //Base.token();
                alert(r)
                $(objs).attr("disabled",false);
            }]
    )
}

