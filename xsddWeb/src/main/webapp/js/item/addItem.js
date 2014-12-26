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

function getCategorySpecificsData(id,indiv,funName,attTable){
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    $('#'+indiv).html('');
    if(localStorage.getItem("category_att_ID"+siteID+""+id)!=null){
        var json= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+id) + ")");
        var jdata=json.result;
        if(jdata==null||jdata==""){
            getSpec(id,indiv,funName,attTable);
        }else{
            getAttMainMenu(jdata,indiv,funName,attTable);
        }
    }else{
        getSpec(id,indiv,funName,attTable);
    }
}

function getSpec(id,indiv,funName,attTable){
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    var url=path+"/ajax/getCategorySpecifics.do";
    var data={"parentCategoryID":id,"siteID":siteID};
   // oldAlert(_invokeGetData_type);
    $().invoke(
        url,
        data,
        [
            function(m,r){
                if(r==null || r==''){return;}
                if(localStorage.getItem("category_att_ID"+siteID+""+id)==null){
                    localStorage.setItem("category_att_ID"+siteID+""+data.parentCategoryID,r);
                }
                var json= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+data.parentCategoryID) + ")");
                var jdata=json.result;
              //  oldAlert(jdata)
                returnSelectStr(jdata);
                getAttMainMenu(jdata,indiv,funName,attTable);
                //alert(localStorage.getItem("aaa").length);
            },
            function(m,r){
                alert(r)}
        ],{stringFormat:true}
    );

}


/**属性数组去重*/
function AttrArrDistinct(ar){
    var m,n=[],o= {};
    for(var i in ar){
        var ob=ar[i]["itemId"];
        var bbb=true;
        for(var ii in n){
            var ob1=n[ii]["itemId"];
            if(ob==ob1){
                bbb=false;
                break;
            }
        }
        if(bbb){
            n.push(ar[i])
        }
    }
return n
}
/**获取属性的种类列表*/
function getAttMainMenu(json,indiv,funName,attTable){
    if((json==null || json.length==0)||json[0]['itemEnName']=='noval'){return;}
    var m=new Array();
    for(var i in json){
        //console.log(json[i]['itemId'])
        var m1={"itemId":json[i]['itemId'],"minV":json[i]['minV']};
        m.push(m1);
    }
    var finalm=AttrArrDistinct(m);
    var parentid=json[0]['itemParentId'];
    for(var i in finalm){
        var domid=replaceTSFH((finalm[i]["itemId"]));
         //var dv="<a data-toggle=\"modal\" href=\"#myModal\" ><img src=\"../../img/new_add.png\" width=\"18\" height=\"18\"> Country / Region of Manufacture</a>"
        //var dv="<a id=div"+domid+" onclick="+funName+"(this,'"+parentid+"','"+attTable+"') class='att_mb-tag'>"+(finalm[i])+"</a>";
        var dv="<a style='padding-left: 10px' id=div"+domid+" onclick="+funName+"(this,'"+parentid+"','"+attTable+"') data-toggle=\"modal\">" ;
        if((finalm[i]["minV"])==1){
            dv+= "<img  src="+path+"/img/new_add1.png width=\"18\" height=\"18\">" ;
        }else{
            dv+= "<img  src="+path+"/img/new_add.png width=\"18\" height=\"18\">" ;
        }

            dv+= ""+(finalm[i]["itemId"])+"</a>";
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
    var ohtmlTem=$(obj).html();
    var ohtml=ohtmlTem.substr(ohtmlTem.indexOf(">")+1,ohtmlTem.length-1);
    var domid=replaceTSFH(ohtml);
    var optionData = queryData(ohtml, parentid);
    var select = "<select id=\""+"_select"+domid+"\" class='easyui-combobox' style='width:200px;'>";
    for (var i in optionData) {
        select += "<option value=\"" + optionData[i]['itemEnName'] + "\">" + optionData[i]['itemEnName'] + "</option>";
    }
    select += "</select>";

    var tr = "<tr id=tr"+domid+" >" + "<td style='text-align: center'><input type='hidden' name='name' value=\""+ohtml+"\" >" +
        ohtml + "</td>" +
        "<td onclick='onclickmulAttrTD(this)' style='text-align: center'><span name='values' style='color: rgb(30, 144, 255); display: inline;'>"+optionData[0]['itemEnName']+"</span>" +
        "<input id=_value"+domid+" type='hidden' name='value'>" + select + "</td>" +
        "<td style='text-align: center'>" + "<img src='/xsddWeb/img/del1.png' style='width: 8px;height: 8px;' onclick='removeThisTr(this)' />" + "</td></tr>";
    $('#'+attTable).append(tr);
    $(obj).hide();
    //try{

        $.parser.parse();
        $('#'+attTable).find("span[class='combo']").hide();
        $('#'+attTable).find("span[name='values']").show();

        $('#'+attTable).find("input").each(function(i,d){
            if($(d).hasClass("combo-text")){
                $(d).removeAttr("onblur");
                $(d).attr("onblur","onblurMulAttrInput(this)")
            }

        });



    //}catch (e){}
}
/**combox失去焦点的时候执行*/
function onblurMulAttrInput(inpu){

    var bst=false;

    setTimeout(function(){
        $("div").each(function(i,d){
            if($(d).hasClass("panel combo-p")){
                if($(d).css("display")=='block'){
                    bst=true;
                }
            }
        });

        if(bst==false){
            var selectid=$(inpu).parents("td").eq(0).find("select[id^='_select']").eq(0).attr("id");
            setTimeout(function(){
                $('#'+selectid).siblings("span[name='values']").html($("#"+selectid).combobox('getValue'));
                $('#attTable').find("span[class='combo']").hide();
                $('#attTable').find("span[name='values']").show();
            },200);
        }

    },500);

    return;
}
function onclickmulAttrTD(td){
    $(td).find("span").hide();
    $(td).find("span").each(function(i,d){
        if($(d).attr("name")==null){
            $(d).show();
        }
    });
    $(td).find("input").each(function(i,d){
        if($(d).hasClass("combo-text")){
            $(d).focus();
        }
    });
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
    $("ul[id^='picture']").find("li").each(function(i,d){
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
    $("ul[id^='picture']").empty();
    for ( var ii in domArr){
        $("ul[id^='picture']").append($(domArr[ii])[0].outerHTML);
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
var timerPage
function selectTimer(obj){
    var urls = path+'/selectTimer.do';
    timerPage = $.dialog({title: '选择定时时间',
        content: 'url:'+urls,
        icon: 'succeed',
        width:500,
        lock:true
    });
    //saveData(this,'timeSave')
}
/**保存并提交*/
function saveData(objs,name) {
    if($("input[type='radio'][name='buyerId']:checked").val()==null||$("input[type='radio'][name='buyerId']:checked").val()==""){
        alert("买家要求未选择");
        return ;
    }
    if($("input[type='radio'][name='itemLocationId']:checked").val()==null||$("input[type='radio'][name='itemLocationId']:checked").val()==""){
        alert("物品所在地未选择！");
        return ;
    }
    if($("input[type='radio'][name='payId']:checked").val()==null||$("input[type='radio'][name='payId']:checked").val()==""){
        alert("支付方式未选择！");
        return ;
    }
    if($("input[type='radio'][name='returnpolicyId']:checked").val()==null||$("input[type='radio'][name='returnpolicyId']:checked").val()==""){
        alert("退货政策未选择！");
        return ;
    }
    if($("input[type='radio'][name='shippingDeailsId']:checked").val()==null||$("input[type='radio'][name='shippingDeailsId']:checked").val()==""){
        alert("运输选项未选择！");
        return ;
    }
    if($("input[type='radio'][name='sellerItemInfoId']:checked").val()==null||$("input[type='radio'][name='sellerItemInfoId']:checked").val()==""){
        var conf = confirm("你确定不选择商品描述？");
        if(conf == true) {

        } else {
            return;
        }
    }

    if($.type(objs)=="string"){
        objs=$("a[name='"+objs+"']");
    }
    bodyClick();//自定义属性
    asyCombox2InputData();//同步comebox的数值
    //reSortItemPic();//对经过排序的图片进行重新排列,后台按前台这个序顺读取
    domReIndex("picture","PictureDetails");//对重新排列后的元素进行重新索引
    $("#dataMouth").val(name);
    if(name=="othersave"){
        $("#id").val(null);
    }
    if(name=="Backgrounder"){
        $("#ListingMessage").val("1");
    }else{
        $("#ListingMessage").val("0");
    }
    if(!$("#form").validationEngine("validate")){
        $('#form').validationEngine('updatePromptsPosition')
        return;
    }
    if(countChoosePic()>11){
        alert("最多只能上传12张图片，上传图片已超过上传限制！");
        return;
    }
    var pciValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i,d) {
        if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
            pciValue.put($(d).find("input[name='attr_Value']").val().replace(" ","_").replace(".","").replace("+",""),$(d).find("input[name='attr_Value']").val().replace(" ","_").replace(".","").replace("+",""));
        }
    });
    for(var i=0;i<pciValue.keys.length;i++){
        $("input[type='hidden'][name='"+pciValue.keys[i].replace(" ","_").replace(".","").replace("+","")+"']").each(function(ii,dd){
            $(dd).prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].PictureURL["+ii+"]");
        });
        $("input[type='hidden'][name='VariationSpecificValue_"+pciValue.keys[i].replace(" ","_").replace(".","").replace("+","")+"']").prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].VariationSpecificValue");
    }

    var nameList = $("input[type='hidden'][name='name']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var valueList = $("input[type='hidden'][name='value']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    $("input[type='hidden'][name='attr_Name']").each(function(i,d){
        var t="Variations.VariationSpecificsSet.NameValueList["+i+"].Name";
        $(d).prop("name",t);
    });
    var len = $("#moreAttrs tr:eq(0) th").length - 5;
    for(var j=0;j<len ;j++){
        $("#moreAttrs tr:gt(0) td:nth-child("+(j+5)+")").each(function (i,d) {
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
    $().invoke(
        urll,
        data,
        [function (m, r) {
            //oldAlert(r);
            //Base.token();
            alert(r);
            $(objs).attr("disabled",false);
            if(url.indexOf("information/editItem.do")>0){
                var api = frameElement.api, W = api.opener;
                W.itemInformation.close();
            }else if(url.indexOf("source=listingManager")>0){
                window.close();
            }else{
                document.location = path+"/itemManager.do";
            }

        },
            function (m, r) {
                Base.token();
                alert(r)
                $(objs).attr("disabled",false);
                //document.location = path+"/itemManager.do";
            }],{isConverPage:true}
    )
}


function checkEbayFee(){
    if(!$("#form").validationEngine("validate")){
        return;
    }
    if(($("#showPics").find("img").length+$("#picMore").find("img").length/2)>8){
        alert("最多只能上传8张图片，上传图片已超过上传限制！");
        return;
    }
    var pciValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i,d) {
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

    var nameList = $("input[type='hidden'][name='name']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var valueList = $("input[type='hidden'][name='value']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });

    $("input[type='hidden'][name='attr_Name']").each(function(i,d){
        var t="Variations.VariationSpecificsSet.NameValueList["+i+"].Name";
        $(d).prop("name",t);
    });
    var len = $("#moreAttrs").find("tr").find("td").length/$("#moreAttrs").find("tr").length-5;
    for(var j=0;j<len ;j++){
        $("#moreAttrs tr:gt(0) td:nth-child("+(j+5)+")").each(function (i,d) {
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
    var urll = path+"/ajax/checkEbayFee.do";
    $().invoke(
        urll,
        data,
        [function (m, r) {
            var str = "";
            if(r.length>0){
                for(var i=0;i< r.length;i++) {
                    var m = r[i];
                    str+="<div>"+m.name+":"+ m.value+"</div>";
                }
            }else{
                str+="<div>暂时未发现收费项目</div>";
            }
            var tent = "<div>"+str+"</div>";
            var editPage = $.dialog({title: '收费项目',
                content: tent,
                icon: 'succeed',
                width: 400
            });
        },
            function (m, r) {
                alert(r)
            }]
    )
}
/**
 * 预览商品
 */
function previewItem(){
    if(!$("#form").validationEngine("validate")){
        return;
    }
    if(($("#showPics").find("img").length+$("#picMore").find("img").length/2)>8){
        alert("最多只能上传8张图片，上传图片已超过上传限制！");
        return;
    }
    var pciValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i,d) {
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

    var nameList = $("input[type='hidden'][name='name']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var valueList = $("input[type='hidden'][name='value']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });

    $("input[type='hidden'][name='attr_Name']").each(function(i,d){
        var t="Variations.VariationSpecificsSet.NameValueList["+i+"].Name";
        $(d).prop("name",t);
    });
    var len = $("#moreAttrs").find("tr").find("td").length/$("#moreAttrs").find("tr").length-5;
    for(var j=0;j<len ;j++){
        $("#moreAttrs tr:gt(0) td:nth-child("+(j+5)+")").each(function (i,d) {
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
    window.open("/xsddWeb/previewItem.do?"+data,null,null,null);
}

function selectAccount(obj){
    if($("select[name='listingType']").find("option:selected").val()==""||$("select[name='listingType']").find("option:selected").val()==null){
        alert("请先选择刊登类型！");
        $(obj).prop("checked",false);
    }
    if($("select[name='listingType']").find("option:selected").val()=="2"&&$("input[type='checkbox'][name='ebayAccounts']:checked").length>1){
        alert("多属性不允许多账号刊登！");
        $(obj).prop("checked",false);
    }
    $("#showPics").text("");
    isShowPicLink();
    initTitle();
    initPrice();
}
function getSiteImg(json){
    var html='<img title="'+json.siteName+'" src="'+path+json.siteImg+'"/>';
    return html;
}
/**判断是否显示图片上传按钮*/
function isShowPicLink(){

    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d){
        if(_sku==null || _sku ==''){
            return;
        }
        if($("#apicUrls_"+$(d).val())[0]!=null){
            return;
        }
        //初始化上传图片
        var showStr = "<div class='panel' style='display: block'>";
        showStr +=" <section class='example' ><ul class='gbin1-list' style='padding-left: 20px;' id='picture_"+$(d).val()+"'></ul></section> ";
        showStr +=" <script type=text/plain id='picUrls_"+$(d).val()+"'></script> ";
        showStr +=" <div style='padding-left: 20px;'>" +
            "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='upload' id='apicUrls_"+$(d).val()+"' onclick='selectPic(this)'>选择图片</a></b>" +
            "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='online' id='apicUrlsSKU_" + $(d).val() + "' onclick='selectPic(this)' style=''>选择SKU图片</a></b>" +
            "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='remote' id='apicUrlsOther_" + $(d).val() + "' onclick='selectPic(this)' style=''>选择外部图片</a></b>" +
            "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' id='apicUrlsclear_" + $(d).val() + "' onclick='clearAllPic(this)' style=''>清空所选图片</a></b>" +
            "</div> </div> ";
        $("#showPics").append(showStr);
        $().image_editor.init("picUrls_"+$(d).val()); //编辑器的实例id
        $().image_editor.show("apicUrls_"+$(d).val()); //上传图片的按钮id        }
        $().image_editor.show("apicUrlsSKU_" + $(d).val()); //上传图片的按钮id
        $().image_editor.show("apicUrlsOther_" + $(d).val()); //上传图片的按钮id
        //增加标题跟数量与价格
    });
}


function initTitle(){
    $("#titleDiv").text("");
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d) {
        var titleHtml="";
        titleHtml+=' <li  style="height: 35px;"> ';
        titleHtml+=' <dt>物品标题('+$(d).attr("shortName")+')</dt> ';
        titleHtml+=' <div class="new_left"> ';
        titleHtml+=' <input type="text" name="Title_'+$(d).val()+'" value="'+title+'" id="Title" style="width:600px;" ';
        titleHtml+=' class="validate[required,maxSize[80]] form-control" size="100" ';
        titleHtml+=' onkeyup="incount(this)"><span id="incount">0</span>/80 ';
        titleHtml+=' </div> ';
        titleHtml+=' </li> ';
        titleHtml+=' <li style="height: 35px;display:none;"> ';
        titleHtml+=' <dt>子标题('+$(d).attr("shortName")+')</dt> ';
        titleHtml+=' <div class="new_left"> ';
        titleHtml+=' <input type="text" name="SubTitle_'+$(d).val()+'" value="'+subtitle+'" style="width:600px;" class="form-control" id="SubTitle_'+$(d).val()+'" ';
        titleHtml+=' size="100"> ';
        titleHtml+=' </div> ';
        titleHtml+=' </li><li class="flip" style="padding-left:260px;padding-bottom: 20px;height: 5px;" onclick="showSubTitle(this)"><img src="'+path+'/img/new_list_ico.png" class="abc"></li> ';
        $("#titleDiv").append(titleHtml);
    });
}
function initPrice(){
    $("#oneAttr").text("");
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d) {
        var priceHtml="";
        priceHtml+=' <li> ';
        priceHtml+=' <dt>商品价格('+$(d).attr("shortName")+')</dt> ';
        priceHtml+=' <div class="new_left"> ';
        priceHtml+=' <input type="text" name="StartPrice.value" style="width:300px;" class="validate[required] form-control" /> ';
        priceHtml+=' </div> ';
        priceHtml+=' </li> ';
        priceHtml+=' <li> ';
        priceHtml+=' <dt>商品数量('+$(d).attr("shortName")+')</dt> ';
        priceHtml+=' <div class="new_left"> ';
        priceHtml+=' <input type="text" style="width:300px;" class="validate[required] form-control" name="Quantity"  /> ';
        priceHtml+=' </div> ';
        priceHtml+='  </li> ';
        $("#oneAttr").append(priceHtml);

    });
}
//选择产品
var Porduct;
function selectProduct(){
    if($("select[name='listingType']").find("option:selected").val()==""||$("select[name='listingType']").find("option:selected").val()==null){
        alert("请先选择刊登类型！");
        return;
    }
    if($("input[type='checkbox'][name='ebayAccounts']:checked").length<1){
        alert("请选择刊登账号！");
        return;
    }
    Porduct = $.dialog({
        id:"itemClass_",
        title: '选择产品信息',
        content: 'url:' + path + '/selectItemInformation.do',
        icon: 'succeed',
        zIndex:2000,
        width: 750,
        height:700,
        lock: true
    });
}

function showSubTitle(obj){
    if($(obj).find("img").attr("class")=="abc"){
        $(obj).find("img").attr("class","abc1");
    }else{
        $(obj).find("img").attr("class","abc");
    }
    $(obj).parent().find("li").each(function(i,d){
        if(i==($(obj).index()-1)){
            if($(d).is(":hidden")){
                $(d).show();
            }else{
                $(d).hide();
            }
        }
    });
}

/**异步加载ueditor的几个js*/
function loadEditor(ebayAccount,jsonstr){
    seriesLoadScripts(ueditorJSS_,function(){
        //初始化描述信息编辑器
        myDescription = UE.getEditor('myDescription', ueditorToolBar);
        //初始化图片上传按扭
        if(ebayAccount!=""){
            $().image_editor.init("picUrls_" + ebayAccount); //编辑器的实例id
            $().image_editor.show("apicUrls_" + ebayAccount); //上传图片的按钮id
            $().image_editor.show("apicUrlsSKU_" + ebayAccount); //上传图片的按钮id
            $().image_editor.show("apicUrlsOther_" + ebayAccount); //上传图片的按钮id
        }
        //初始化多属性图片按扭
        if(jsonstr!=""){
            var json = eval("(" + jsonstr + ")");
            if(json!=null){
                for(var i=0;i<json.length;i++){
                    $().image_editor.init(json[i].a);
                    $().image_editor.show(json[i].b);
                }
            }
        }
        //模板图片初始化
        $().image_editor.init("blankImg_main"); //编辑器的实例id
        $().image_editor.show("blankImg_id"); //上传图片的按钮id
        if(url.indexOf("addItem.do") != -1){
            $().image_editor.init("picUrls"); //编辑器的实例id
            $().image_editor.show("apicUrls"); //上传图片的按钮id
        }
    });
}


