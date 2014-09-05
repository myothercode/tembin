/**
 * Created by Administrtor on 2014/8/15.
 * 加载物品类别属性以及保持并提交的js
 *
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
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    $('#'+indiv).html('');
    if(localStorage.getItem("category_att_ID"+siteID+""+id)!=null){
        var json= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+id) + ")");
        var jdata=json.result;
        getAttMainMenu(jdata,indiv,funName,attTable);
    }else{
        var url=path+"/ajax/getCategorySpecifics.do";
        var data={"parentCategoryID":id,"siteID":siteID};
        $().invoke(
            url,
            data,
            [
                function(m,r){
                    if(r==null || r==''){return;}
                    localStorage.setItem("category_att_ID"+siteID+""+data.parentCategoryID,r);
                    var json= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+data.parentCategoryID) + ")");
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
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    var json1= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+parentid) + ")");
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
    //try{
        $.parser.parse();
    //}catch (e){}

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
function getLocalStorage(name){
    var a="",b=0;
    $("input[type='radio'][name='"+name+"']").each(function(i,d){
        if(localStorage.getItem(name+"_userselect." + $(d).val()) != null){
            if(parseInt(localStorage.getItem(name+"_userselect." + $(d).val()))>b){
                b=parseInt(localStorage.getItem(name+"_userselect." + $(d).val()));
                a=$(d).val();
            }
        }
    });
    return a;
}

function getTemplateNumber(){
    var a=0;
    for(var i =0;i<localStorage.length;i++){
        if(localStorage.key(i).indexOf("template_")!=-1){
            var json= eval("("+localStorage.getItem(localStorage.key(i))+")");
            if(parseInt(json.countnumber)>a){
                a=parseInt(json.countnumber);
            }
        }
    }
    return a;
}

//把用户常用的选择信息记录下来
function checkLocalStorage(){
    //模板选择记录
    var templateId = $("#templateId").val();
    if(templateId!=null&&templateId!=""){
        var json= eval("("+localStorage.getItem("template_"+templateId)+")");
        if(json!=null){
            localStorage.setItem("template_" + templateId,"{\"countnumber\":\""+(parseInt(json.countnumber)+1)+"\",\"result\":{\"templateId\":\""+json.result.templateId+"\",\"templateUrl\":\""+json.result.templateUrl+"\"}}");
        }else{
            localStorage.setItem("template_" + templateId,"{\"countnumber\":\"1\",\"result\":{\"templateId\":\""+templateId+"\",\"templateUrl\":\""+$("#templateUrl").attr("src")+"\"}}");
        }
    }


    $("input[type='radio'][name='buyerId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("buyerId_userselect." + $(d).val()) != null) {
                localStorage.setItem("buyerId_userselect." + $(d).val(), parseInt(localStorage.getItem("buyerId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("buyerId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='discountpriceinfoId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("discountpriceinfoId_userselect." + $(d).val()) != null) {
                localStorage.setItem("discountpriceinfoId_userselect." + $(d).val(), parseInt(localStorage.getItem("discountpriceinfoId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("discountpriceinfoId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='itemLocationId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("itemLocationId_userselect." + $(d).val()) != null) {
                localStorage.setItem("itemLocationId_userselect." + $(d).val(), parseInt(localStorage.getItem("itemLocationId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("itemLocationId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='payId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("payId_userselect." + $(d).val()) != null) {
                localStorage.setItem("payId_userselect." + $(d).val(), parseInt(localStorage.getItem("payId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("payId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='returnpolicyId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("returnpolicyId_userselect." + $(d).val()) != null) {
                localStorage.setItem("returnpolicyId_userselect." + $(d).val(), parseInt(localStorage.getItem("returnpolicyId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("returnpolicyId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='shippingDeailsId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("shippingDeailsId_userselect." + $(d).val()) != null) {
                localStorage.setItem("shippingDeailsId_userselect." + $(d).val(), parseInt(localStorage.getItem("shippingDeailsId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("shippingDeailsId_userselect." + $(d).val(), 1);
            }
        }
    });
    $("input[type='radio'][name='sellerItemInfoId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("sellerItemInfoId_userselect." + $(d).val()) != null) {
                localStorage.setItem("sellerItemInfoId_userselect." + $(d).val(), parseInt(localStorage.getItem("sellerItemInfoId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("sellerItemInfoId_userselect." + $(d).val(), 1);
            }
        }
    });
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
    checkLocalStorage();
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
                Base.token();
                alert(r)
                $(objs).attr("disabled",false);
            }]
    )
}

function selectAccount(obj){
    if($("input[type='radio'][name='listingType']:checked").val()==""||$("input[type='radio'][name='listingType']:checked").val()==null){
        alert("请先选择刊登类型！");
        $(obj).prop("checked",false);
    }
    if($("input[type='radio'][name='listingType']:checked").val()=="2"&&$("input[type='checkbox'][name='ebayAccounts']:checked").length>1){
        alert("多属性不允许多账号刊登！");
        $(obj).prop("checked",false);
    }
    $("#showPics").text("");
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d){
        //初始化上传图片
        var showStr = "<div class='panel' style='display: block'>";
        showStr +=" <section class='example' id='picture_"+$(d).val()+"'></section> ";
        showStr +=" <script type=text/plain id='picUrls_"+$(d).val()+"'></script> ";
        showStr +=" <div style='padding-left: 60px;'><a href='javascript:void(0)' id='apicUrls_"+$(d).val()+"' onclick='selectPic(this)'>选择图片</a></div> </div> ";
        $("#showPics").append(showStr);
        $().image_editor.init("picUrls_"+$(d).val()); //编辑器的实例id
        $().image_editor.show("apicUrls_"+$(d).val()); //上传图片的按钮id        }
        //增加标题跟数量与价格
    });
    initTitle();
}

function initTitle(){
    $("#titleDiv").text("");
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d) {
        var titleHtml="";
        titleHtml+=' <li> ';
        titleHtml+=' <dt>物品标题('+$(d).attr("shortName")+')</dt> ';
        titleHtml+=' <div class="new_left"> ';
        titleHtml+=' <input type="text" name="Title_'+$(d).val()+'" id="Title_'+$(d).val()+'" style="width:600px;" ';
        titleHtml+=' class="validate[required,maxSize[80]] form-control" size="100" ';
        titleHtml+=' onkeyup="incount(this)"><span id="incount">0</span>/80 ';
        titleHtml+=' </div> ';
        titleHtml+=' </li> ';
        titleHtml+=' <li> ';
        titleHtml+=' <dt>子标题('+$(d).attr("shortName")+')</dt> ';
        titleHtml+=' <div class="new_left"> ';
        titleHtml+=' <input type="text" name="SubTitle_'+$(d).val()+'" style="width:600px;" class="form-control" id="SubTitle_'+$(d).val()+'" ';
        titleHtml+=' size="100"> ';
        titleHtml+=' </div> ';
        titleHtml+=' </li> ';
        $("#titleDiv").append(titleHtml);
    });
}
