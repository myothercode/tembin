/**
 * Created by Administrtor on 2014/9/1.
 */



/**如果是编辑页面初始化几个模块选项*/
function initModel(){
    loadDataBuyer();
    loadDiscountPriceInfo();
    loadItemLocation();
    loadPayOption();
    loadReturnpolicy();
    loadShippingDeails();
    loaddescriptiondetails();
    setTimeout(function(){
        myDescription.setHeight(500);
    },5000);
}

/**将几个加载模块的方法定义为一个全局变量*/
var loadModelFunctions={
    "buyer":loadDataBuyer,
    "discountpriceinfo":loadDiscountPriceInfo,
    "itemLocation":loadItemLocation,
    "pay":loadPayOption,
    "returnpolicy":loadReturnpolicy,
    "shippingDeails":loadShippingDeails,
    "descriptiondetails":loaddescriptiondetails
};
/**编辑器的工具栏*/
var ueditorToolBar={
    toolbars:[['source','FullScreen',  'Undo', 'Redo','Bold','fontfamily', 'fontsize','cleardoc','|',
        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
        'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows',
        'splittocols','|','insertimage','imagenone', 'imageleft', 'imageright', 'imagecenter', '|','horizontal','drafts','preview','|'
    ]],
//关闭字数统计
    wordCount:false,
    zIndex : 0,
    //关闭elementPath
    elementPathEnabled:false
    //默认的编辑区域高度
    //initialFrameHeight:500
    //initialFrameWidth:'98%'
};
//数据状态
function makeOption2(json){
    var htm=''
    if(json.checkFlag=="0"){
        htm='已启用';
    }else{
        htm='已禁用';
    }
    return htm;
}
/**加载几个模块的信息*/
/**加载买家要求*/
var loadDataBuyerV=false;
function loadDataBuyer(){
    if(loadDataBuyerV==true){return;}
    $("#buyer").initTable({
        url: path + "/ajax/loadBuyerRequirementDetailsList.do?checkFlag=0",
        columnData: [
            {title: "选择", name: "option1", width: "5%", align: "left", format: returnBuyer},
            {title:"名称",name:"name",width:"30%",align:"left"},
            {title:"站点",name:"siteName",width:"5%",align:"left"},
            {title:"买家要求",name:"option1",width:"45%",align:"left",format:makeOption3buyer},
            {title:"状态",name:"option1",width:"10%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"5%",align:"center",format:makeOption1buyer}
        ],
        selectDataNow: false,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var buyerId = getLocalStorage("buyerId");
                $("input[type='radio'][name='buyerId'][value='" + buyerId + "']").attr("checked", true);
            }else{
                $("input[name='buyerId'][value='" + buyid + "']").attr("checked", "checked");
            }
        }
    });
    refreshTablebuyer();
    loadDataBuyerV=true;
}

//加载折扣信息
var loadDiscountPriceInfoV=false;
function loadDiscountPriceInfo(){
    if(loadDiscountPriceInfoV==true){return;}
    $("#discountpriceinfo").initTable({
        url: path + "/ajax/loadDiscountPriceInfoList.do?checkFlag=0",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnDiscountpriceinfo},
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"账户名称",name:"ebayName",width:"8%",align:"left"},
            {title:"开始时间",name:"disStarttime",width:"8%",align:"left"},
            {title:"结束时间",name:"disEndtime",width:"8%",align:"left"},
            {title:"折扣",name:"madeforoutletcomparisonprice",width:"8%",align:"left"},
            {title:"降价",name:"minimumadvertisedprice",width:"8%",align:"left"},
            {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1Disprice}
        ],
        selectDataNow: false,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var discountpriceinfoId1 = getLocalStorage("discountpriceinfoId");
                $("input[type='radio'][name='discountpriceinfoId'][value='" + discountpriceinfoId1 + "']").attr("checked", true);
            }else{
                $("input[name='discountpriceinfoId'][value='" + discountpriceinfoId + "']").attr("checked", "checked");
            }
        }
    });
    refreshTableDisPrice();
    loadDiscountPriceInfoV=true;
}

/**物品所在地*/
var loadItemLocationV=false;
 function loadItemLocation(){
     if(loadItemLocationV==true){return;}
     $("#itemLocation").initTable({
         url: path + "/ajax/loadItemAddressList.do?checkFlag=0",
         columnData: [
             {title: "选项", name: "option1", width: "8%", align: "left", format: returnItemLocation},
             {title:"名称",name:"name",width:"8%",align:"left"},
             {title:"地址",name:"address",width:"8%",align:"left"},
             {title:"国家",name:"countryName",width:"8%",align:"left"},
             {title:"邮编",name:"postalcode",width:"8%",align:"left"},
             {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2},
             {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1address}
         ],
         selectDataNow: false,
         isrowClick: false,
         showIndex: false,
         afterLoadTable:function(){
             if (url.indexOf("addItem.do") != -1) {
                 var itemLocationId1 = getLocalStorage("itemLocationId");
                 $("input[type='radio'][name='itemLocationId'][value='" + itemLocationId1 + "']").attr("checked", true);
             }else{
                 $("input[name='itemLocationId'][value='" + itemLocationId + "']").attr("checked", "checked");
             }
         }
     });
     refreshTableAddress();
     loadItemLocationV=true;
 }

//付款选项
var loadPayOptionV=false;
function loadPayOption(){
    if(loadPayOptionV==true){return;}
    $("#pay").initTable({
        url: path + "/ajax/loadPayPalList.do?checkFlag=0",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnPay},
            {title:"名称",name:"payName",width:"8%",align:"left"},
            {title:"站点",name:"siteName",width:"8%",align:"left"},
            {title:"paypal账号",name:"payPalName",width:"8%",align:"left"},
            {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"动作",name:"option1",width:"8%",align:"left",format:makeOption1paypal}
        ],
        selectDataNow: false,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var payId1 = getLocalStorage("payId");
                $("input[type='radio'][name='payId'][value='" + payId1 + "']").attr("checked", true);
            }else{
                $("input[name='payId'][value='" + payid + "']").attr("checked", "checked");
            }
        }
    });
    refreshTablepaypal();
    loadPayOptionV=true;
}

//退货选项
var loadReturnpolicyV=false;
function loadReturnpolicy(){
if(loadReturnpolicyV==true){return;}
    $("#returnpolicy").initTable({
        url: path + "/ajax/loadReturnpolicyList.do?checkFlag=0",
        columnData: [
            {title: "选项", name: "option1", width: "5%", align: "left", format: returnReturnpolicy},
            {title:"名称",name:"name",width:"20%",align:"left"},
            {title:"站点",name:"siteName",width:"10%",align:"left"},
            {title:"退货明细",name:"option1",width:"50%",align:"left",format:makeOption3returnpolicy},
            {title:"数据状态",name:"option1",width:"10%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"5%",align:"left",format:makeOption1returnpolicy}
        ],
        selectDataNow: false,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var returnpolicyId1 = getLocalStorage("returnpolicyId");
                $("input[type='radio'][name='returnpolicyId'][value='" + returnpolicyId1 + "']").attr("checked", true);
            }else{
                $("input[name='returnpolicyId'][value='" + returnpolicyId + "']").attr("checked", "checked");
            }
        }
    });
    refreshTablereturnpolicy();
    loadReturnpolicyV=true;
}

/**运输选项*/
var loadShippingDeailsV=false;
function loadShippingDeails(){
    if(loadShippingDeailsV==true){
        return;
    }
    $("#shippingDeails").initTable({
        url: path + "/ajax/loadShippingDetailsList.do?checkFlag=0",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnShippingDeails},
            {title:"名称",name:"shippingName",width:"8%",align:"left"},
            {title:"站点",name:"siteName",width:"8%",align:"left"},
            {title:"ebay账号",name:"option1",width:"8%",align:"left",format:showData},
            {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"8%",align:"left",format:shippingmakeOption1}

        ],
        selectDataNow: false,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var shippingDeailsId1 = getLocalStorage("shippingDeailsId");
                $("input[type='radio'][name='shippingDeailsId'][value='" + shippingDeailsId1 + "']").attr("checked", true);
            }else{
                $("input[name='shippingDeailsId'][value='" + shippingDeailsId + "']").attr("checked", "checked");
            }
        }
    });
    refreshTableShipping();
    loadShippingDeailsV=true;
}

/**卖家描述*/
var descriptiondetailsV=false;
function loaddescriptiondetails(){
    if(descriptiondetailsV==true){return;}
    $("#descriptiondetails").initTable({
        url:path + "/ajax/loadDescriptionDetailsList.do?checkFlag=0",
        columnData:[
            {title: "选项", name: "option1", width: "2%", align: "left", format: returnDescriptiondetails},
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1descript}
        ],
        selectDataNow: false,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var sellerItemInfoId1 = getLocalStorage("sellerItemInfoId");
                $("input[type='radio'][name='sellerItemInfoId'][value='" + sellerItemInfoId1 + "']").attr("checked", true);
            }else{
                $("input[name='sellerItemInfoId'][value='" + sellerItemInfoId + "']").attr("checked", "checked");
            }
        }
    });
    refreshTableDesciption();
    descriptiondetailsV=true;
}



/**返回买家要求单选框*/
function returnBuyer(json) {
    var htm = "<input type=\"radio\" name=\"buyerId\" value=" + json.id + ">";
    return htm;
}

/**返回折扣信息单选框*/
function returnDiscountpriceinfo(json) {
    var htm = "<input type=\"radio\" name=\"discountpriceinfoId\" value=" + json.id + ">";
    return htm;
}

/**返回物品所在地信息单选框*/
function returnItemLocation(json) {
    var htm = "<input type=\"radio\" name=\"itemLocationId\" value=" + json.id + ">";
    return htm;
}
/**返回付款选项信息单选框*/
function returnPay(json) {
    var htm = "<input type=\"radio\" name=\"payId\" value=" + json.id + ">";
    return htm;
}


/**返回退货项信息单选框*/
function returnReturnpolicy(json) {
    var htm = "<input type=\"radio\" name=\"returnpolicyId\" value=" + json.id + ">";
    return htm;
}

/**返回运输选项信息单选框*/
function returnShippingDeails(json) {
    var htm = "<input type=\"radio\" name=\"shippingDeailsId\" value=" + json.id + ">";
    return htm;
}

function returnDescriptiondetails(json) {
    var htm = "<input type=\"radio\" name=\"sellerItemInfoId\" value=" + json.id + ">";
    return htm;
}
function clearMoreAttrsText(){
    $("#moreAttrs").find("span").each(function(i,d){
        $(d).show();
    });
    $("#moreAttrs").find("[type='text']").each(function(i,d){
        if($(d).val()!="") {
            $(d).prop("type", "hidden");
        }
    });
}
function showMoreAttrsText(obj){
    clearMoreAttrsText();
    $(obj).parent().find("[type='hidden']").prop("type","text").focus();
    $(obj).hide();
}
function clearThisText(obj) {
    if ($(obj).val() != "") {
        if(
            $(obj).validationEngine("validate")){
            $(obj).validationEngine();
            return;
        }else{
            $(obj).prop("type", "hidden");
            $(obj).parent().find("span").show();
        }
    }
}
function bodyClick(){
    $("#attTable").find("span").each(function(i,d){
        if(!$(d).hasClass("combo")){
            $(d).show();
        }

    });
    $("#attTable").find("[type='text']").each(function(i,d){
        if($(d).val()!=""){
            if(!$(d).hasClass("combo-text")){
                $(d).prop("type","hidden");
            }
        }
    });
}

function showText(obj){
    bodyClick();
    if($(obj).prop("val")!="1"){
        $(obj).find("[type='hidden']").prop("type","text").focus();
        $(obj).find("span").hide();
    }
}
function getJoinValue(obj){
    $(obj).parent().find("span").text($(obj).val());
}
function addValueTr(obj1, obj2) {
    var trStr = '';
    if(obj1!=""&&obj2!=""){
        trStr = '<tr height="32px;"><td onclick="showText(this)" style="text-align: center"><span name="name" style="color: dodgerblue;">'+obj1+'</span><input type="hidden" onblur="bodyClick();"  onkeyup="getJoinValue(this)" name="name"  class="validate[required] form-control" value="' + obj1 + '"></td><td  onclick="showText(this)"  style="text-align: center"><span name="value"  style="color: dodgerblue;">'+obj2+'</span><input type="hidden"  onkeyup="getJoinValue(this)" name="value" class="validate[required] form-control"  onblur="bodyClick();" value="' + obj2 + '"></td><td style="text-align: center"><img src="'+path+'/img/del.png" onclick="removeROW(this)"></td></tr>';
    }else{
        trStr = '<tr height="32px;"><td onclick="showText(this)" style="text-align: center"><span name="name" style="display: none;color: dodgerblue;">'+obj1+'</span><input  onblur="bodyClick();" onkeyup="getJoinValue(this)" type="text" name="name"  class="validate[required] form-control" value="' + obj1 + '"></td><td  onclick="showText(this)" style="text-align: center"><span name="value"  style="display: none;color: dodgerblue;">'+obj2+'</span><input type="text"  onkeyup="getJoinValue(this)" name="value"  onblur="bodyClick();" class="validate[required] form-control" value="' + obj2 + '"></td><td  style="text-align: center"><img src="'+path+'/img/del.png" onclick="removeROW(this)"></td></tr>';
    }
    return trStr;
}
function removeROW(obj) {
    $(obj).parent().parent().remove();
}
function addAttrTr(showName, name, value) {
    var trStr = '<tr height="32px;"><td onclick="showText(this)" style="text-align: center"><span name="name">'+showName+'</span><input type="hidden" name="name" val="1" class="validate[required] form-control" value="' + name + '"></td><td  onclick="showText(this)"  style="text-align: center"><span name="value">'+value+'</span><input type="hidden" onkeyup="getJoinValue(this)" name="value" class="validate[required] form-control" value="' + value + '"  onblur="bodyClick();"></td><td style="text-align: center"><img src="'+path+'/img/del.png" onclick="removeROW(this)"></td></tr>';
/*    var trStr = '<tr><td>' + showName + '</td><td><input type="text" name="' + name + '" value="' + value + '"></td></tr>';*/
    return trStr;
}

/**
 *添加自定义属性
 */
function addValue() {
    $("#attTable").append(addValueTr('', ''));
}
/**
 *添加定固定属性
 */
function addAttr(showName, name) {
    $("#attTable").append(addAttrTr(showName, name, ''));
}

function onShow(obj) {
    _sku = obj.value;
    isShowPicLink();
}
//选择刊登 类型，判断显示
function changeRadio(obj) {
    if (obj == "2") {
        if($("input[type='checkbox'][name='ebayAccounts']:checked").length>1){
            alert("多属性不允许多账号刊登！");
            $("input[type='radio'][name='listingType'][value='"+obj+"']").attr("checked",false);
        }
        $("#oneAttr").hide();
        $("#twoAttr").show();
        $("#Auction").hide();
        $("dt[name='priceMessage']").show();
    } else if (obj == "FixedPriceItem") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").hide();
        $("dt[name='priceMessage']").show();
    } else if (obj == "Chinese") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").show();
        $("dt[name='priceMessage']").show();
    }
}
//点击添回SKU输入项
function addInputSKU(obj) {
    var len = $(obj).parent().parent().parent().find("table").find("tr").find("td").length / $(obj).parent().parent().parent().find("table").find("tr").length - 5;
    $(obj).parent().parent().parent().parent().find("table").append(addTr(len));
    $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
    changeBackcolour();
}
function changeBackcolour(){
    $("#moreAttrs tr").hover(function() {
        $(this.cells[0]).addClass('showDragHandle');
    }, function() {
        $(this.cells[0]).removeClass('showDragHandle');
    });
}
//添加一行数据，用于填写
function addTr(len) {
    var str = "";
    str += "<tr>";
    str += "<td class='dragHandle'  width='5px;'></td>";
    str += "<td><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span><input type='text' name='SKU'  onblur='clearThisText(this);' onkeyup='getJoinValue(this)'  class='validate[required] form-control'></td>";
    str += "<td><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span><input type='text' name='Quantity'  onblur='clearThisText(this);' onkeyup='getJoinValue(this)' size='8' class='validate[required,custom[integer]] form-control'></td>";
    str += "<td><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span><input type='text' name='StartPrice.value'  onblur='clearThisText(this);' onkeyup='getJoinValue(this)'  size='8' class='validate[required,custom[number]] form-control'></td>";
    for (var i = 0; i < len; i++) {
        str += "<td><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span><input type='text' name='attr_Value' onkeyup='getJoinValue(this)' class='validate[required] form-control' onblur='addb(this)' size='10' ></td>";
    }
    str += "<td name='del'><img src='"+path+"/img/del.png' onclick='removeCloums(this)'></td>";
    str += "</tr>";
    return str;
}
//添加属性列
function addMoreAttr(obj) {
    $(obj).parent().parent().parent().find("table").find("tr").each(function (i, d) {
        $(d).find("td").each(function (ii, dd) {
            if ($(dd).attr("name") == "del") {
                if (i == 0) {
                    $(dd).before("<td width='10%'><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span>" +
                        "<input type='text' size='8' onkeyup='getJoinValue(this)' class='validate[required] form-control'" +
                        " name='attr_Name' onblur='addc(this)'>&nbsp;&nbsp;<img src='"+path+"/img/del.png' onclick='removeCols(this)'></td>");
                } else {
                    $(dd).before("<td width='10%'><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span>" +
                        "<input type='text' size='10' name='attr_Value' onblur='addb(this)' " +
                        " onkeyup='getJoinValue(this)' class='validate[required] form-control'></td>");
                }
            }
        });
    });
    // $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
}
//删除多属性中的SKU输入项
function removeCloums(obj) {
    $(obj).parent().parent().remove();
    var attrValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
        if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
            attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
        }
    });
    var dicMap = new Map()
    for (var i = 0; i < attrValue.keys.length; i++) {
        var url = $("input[name='" + attrValue.get(attrValue.keys[i]) + "']");
        var dics = new Map();
        for (var j = 0; j < url.length; j++) {
            dics.put(j, $(url[j]).val());
        }
        dicMap.put(attrValue.get(attrValue.keys[i]), dics);
    }
    $("#picMore").html("");
    for (var i = 0; i < attrValue.keys.length; i++) {
        var m = dicMap.get(attrValue.get(attrValue.keys[i]));
        for (var j = 0; j < m.keys.length; j++) {
            $('#' + attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='50' width='50' />");
        }
        $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
    }
    $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
    changeBackcolour()
}
//移除属性值
function removeCols(obj) {
    $("#moreAttrs tr th:eq(" + ($(obj.parentNode)[0].cellIndex + 1) + ")").remove();
    $("#moreAttrs tr td:nth-child(" + ($(obj.parentNode)[0].cellIndex + 1) + ")").remove();

    var attrValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
        if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
            attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
        }
    });
    var dicMap = new Map()
    for (var i = 0; i < attrValue.keys.length; i++) {
        var url = $("input[name='" + attrValue.get(attrValue.keys[i]) + "']");
        var dics = new Map();
        for (var j = 0; j < url.length; j++) {
            dics.put(j, $(url[j]).val());
        }
        dicMap.put(attrValue.get(attrValue.keys[i]), dics);
    }
    $("#picMore").html("");
    for (var i = 0; i < attrValue.keys.length; i++) {

        $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
        var m = dicMap.get(attrValue.get(attrValue.keys[i]));
        for (var j = 0; j < m.keys.length; j++) {
            $('#' + attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='50' width='50' />");
        }
    }
    //$("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
}
var attrName = new Map();
//当输入属性名称时调用的方法
function addc(obj) {
    if ($(obj.parentNode)[0].cellIndex == 4) {
        attrName = obj.value;
    }
    var attrValue = new Map();
    if ($(obj.parentNode)[0].cellIndex == 4) {
        $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
            if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
                attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
            }
        });
        $("#picMore").html("");
        for (var i = 0; i < attrValue.keys.length; i++) {
            $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
            $().image_editor.init(attrName+"."+attrValue.get(attrValue.keys[i])); //编辑器的实例id
            $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
        }
    }
    clearThisText(obj);
}
//当输入属性值时调用的方法
function addb(obj) {
    if(attrName==""||attrName==null){
        attrName = $("#moreAttrs  tr:eq(0) td:eq(4)").find("[type='hidden'][name='attr_Name']").val();
    }
    var attrValue = new Map();
    if ($(obj.parentNode)[0].cellIndex == 4) {
        $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
            if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
                attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
            }
        });
        var dicMap = new Map()
        for (var i = 0; i < attrValue.keys.length; i++) {
            var url = $("input[name='" + attrValue.get(attrValue.keys[i]) + "']");
            var dics = new Map();
            for (var j = 0; j < url.length; j++) {
                dics.put(j, $(url[j]).val());
            }
            dicMap.put(attrValue.get(attrValue.keys[i]), dics);
        }
        $("#picMore").html("");
        for (var i = 0; i < attrValue.keys.length; i++) {
            //alert($("input[name='"+attrValue.get(attrValue.keys[i])+"']").length);
            $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
            var m = dicMap.get(attrValue.get(attrValue.keys[i]));
            for (var j = 0; j < m.keys.length; j++) {
                $('#' + attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='50' width='50' />");
            }
            $().image_editor.init(attrName+"."+attrValue.get(attrValue.keys[i])); //编辑器的实例id
            $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
        }
    }
    clearThisText(obj);
}
function addPic(attrName, attrValue) {
    var str = "";
    str += "<div><div>" + attrName + ":" + attrValue + "</div><script type=text/plain id='" + attrName + "." + attrValue + "' />";
    str += "<div><b class='new_button'><a href='javascript:void(0)' id=" + attrValue + " onClick='selectPic(this)'>选择图片</a></b></div>";
    str += "</div>";
    return str;
}

var afterUploadCallback = null;
var sss;
//当选择图片后生成图片地址
function selectPic(a) {
    if(($("#showPics").find("img").length+$("#picMore").find("img").length/2)>8){
        setTimeout(function(){closeSelectPicWindow()},200) ;
        alert("最多只能上传8张图片，上传图片已超过上传张数！");
        return;
    }
    sss = a.id;
    afterUploadCallback = {"imgURLS": addPictrueUrl};
}
/**关闭打开的图片选择框*/
function closeSelectPicWindow(){
    $(".edui-for-insertimage").each(function(i,d){
        var dis=$(d).css("display")=='block';
        var ishave1=$(d).hasClass("edui-dialog");
        var ishave2=$(d).hasClass("edui-state-centered");
        var didd= d.id;
        var divIndex=parseInt(strGetNum(didd))+1 ;

        if(didd!=null && didd!='' && ishave1 && ishave2 && dis){
            //alert(d.id+"::"+dis+"::"+ishave1+":"+ishave2)
            $EDITORUI["edui"+divIndex]._onClick(event, this)
        }
    });
}

/**移除选中的图片*/
function deletePic(a){
    $(a.parentNode.parentNode).remove();
}

function addPictrueUrl(urls) {
    var ebayid = $("input[type='checkbox'][name='ebayAccounts']:checked").val();
    var siteid = $("select[name='site']").val();

    if (sss.indexOf("apicUrls")!=-1) {//商品图片
        var str = '';
        var urlss= '';
        var isabc = false;
        for (var i = 0; i < urls.length; i++) {
            var imgsrc = urls[i].src.replace("@", ":");
            var idDuff=generateMixedRandom(5);

            str += '<li><div style="position:relative"><input type="hidden" name="pic_mackid"/> <input type="hidden" name="PictureDetails'+sss.substr(sss.indexOf("_"),sss.length)+'.PictureURL" value="' + urls[i].src + '">' +
                '<img id=imgtemp'+idDuff+' src=' + imgsrc + ' height=\"80px\" width=\"80px\" />' +
                '<a onclick="deletePic(this)" style="position: absolute;top: -45px;right: -15px;" href=\'javascript:void(0)\'>&times;</a></div>';
            str += "</li>";
            urlss+=imgsrc+",";
            $("<img/>").attr({"src": imgsrc,"id":"_imgtemp"+idDuff}).load(function() {
                if(this.width<1000 || this.height<1000){
                    var targgetImgIndex=subRight(this.id,5)//strGetNum(this.id);
                    $("#imgtemp"+targgetImgIndex).wrap("<div style=\"border: solid red 1px;width: 85px;\"></div>");
                    afterTipFunction("imgtemp"+targgetImgIndex,"图像大小小于1000像素，可能会影响展示效果",true);
                }
             $("#"+this.id).remove();
            });

        }
        var addhtml = $("#picture"+sss.substr(sss.indexOf("_"),sss.length)).append(str);
        str = "";
        var url=path+"/ajax/saveListingPicUrl.do?urls="+urlss+"&siteid="+siteid+"&ebayid="+ebayid;
        $().invoke(url,{},
            [function(m,r){
                for(var i =0;i< r.length;i++){
                    var tlu = r[i];
                    var len = $(addhtml).find("input[type='hidden'][name='pic_mackid']").length- r.length;
                    $(addhtml).find("input[type='hidden'][name='pic_mackid']").each(function(j,d){
                        if($(d).val()==""&&(i+len)==j){
                            $(d).val(tlu.mackId);
                        }
                    });
                }
            },
                function(m,r){
                    alert(r);
                }]
        );
    } else {//多属性图片
        var str = '';
        var urlss= '';
        if($('#' + sss).parent().find("[name*='VariationSpecificValue_']").length==0){
            $('#' + sss).before("<input type='hidden' name='VariationSpecificValue_" + sss + "' value='" + sss + "'>");
        }
        for (var i = 0; i < urls.length; i++) {
            str+="<span><input type='hidden' name='pic_mackid_more'/><input type='hidden' name='" + sss + "' value='" + urls[i].src + "'><img src='" + urls[i].src.replace("@", ":") + "' height='50' width='50' /><img src='"+path+"/img/del.png' onclick='removeThis(this)'></span>";
            urlss+=urls[i].src.replace("@", ":")+",";
        }
        var addhtmlstr = $('#' + sss).before(str);
        str="";
        var url=path+"/ajax/saveListingPicUrl.do?urls="+urlss+"&siteid="+siteid+"&ebayid="+ebayid;
        $().invoke(url,{},
            [function(m,r){
                for(var i =0;i< r.length;i++){
                    var tlu = r[i];
                    var len = $("#"+sss).parent().find("input[type='hidden'][name='pic_mackid_more']").length- r.length;
                    $("#"+sss).parent().find("input[type='hidden'][name='pic_mackid_more']").each(function(j,d){
                        if($(d).val()==""&&(i+len)==j){
                            $(d).val(tlu.mackId);
                        }
                    });
                }
            },
                function(m,r){
                    alert(r);
                }]
        );
    }
    initDraug();//初始化拖动图片
}
function initDraug(){
    $('.gbin1-list').sortable().bind('sortupdate', function() {
        $('#msg').html('position changed').fadeIn(200).delay(1000).fadeOut(200);
    });
    return $('.gridly .delete').click(function (event) {
        event.preventDefault();
        event.stopPropagation();
        $(this).closest('.brick').remove();
        return $('.gridly').gridly('layout');
    });
}
function removeThis(obj) {
    $(obj).parent().remove();
}
//得到分类名称
function getCategoryName(categoryId,siteId){
    var url=path+"/ajax/getCategoryName.do?categoryId="+categoryId+"&siteId="+siteId;
    $().invoke(url,{},
        [function(m,r){
            $("#PrimaryCategoryshow").html(r);
        },
            function(m,r){
                alert(r);
            }]
    );
}
//当输入分类，
function addTypeAttr() {
    $('#typeAttrs').html('');
    if(jQuery("#PrimaryCategory").validationEngine("validate"))
    {
        return;
    }
    var values = $("#PrimaryCategory").val();
    getCategorySpecificsData(values, "typeAttrs", "afterClickAttr", "attTable");
    var site = $("select[name='site']").find("option:selected").val();
    getCategoryName(values,site);
    $("#PrimaryCategoryshow").show();
}
var CategoryType;
function selectType() {

    CategoryType = $.dialog({
        id:"itemClass_",
        title: '选择商品分类',
        content: 'url:' + path + '/category/initSelectCategoryPage.do',
        icon: 'succeed',
        zIndex:2000,
        width: 650,
        lock: true
    });
}

function queryType() {
    var title = $("#Title").val();
    CategoryType = $.dialog({
        id:"itemClass_",
        title: '搜索商品分类',
        content: 'url:' + path + '/category/initQueryCategoryPage.do?title='+title,
        icon: 'succeed',
        zIndex:2000,
        width: 650,
        lock: true
    });
}

function incount(obj) {
    $(obj).parent().find("span").text($(obj).val().length);
}

function setTab(obj) {
    $("div[name='showModel']").each(function (i, d) {
        $(d).hide();
    });
    $(obj).parent().find("dt").each(function (i, d) {
        $(d).removeClass("new_ic_1");
    });
    var name = $(obj).attr("name");
    $(obj).addClass("new_ic_1");
    $("#" + name).show();
    if(name!="priceMessage"){
        loadModelFunctions[name]();
    }
}
function closeWindow(){
    document.location = path+"/itemManager.do";
}
var selectTemplates;
function selectTemplate() {
    selectTemplates = $.dialog({title: '选择模板',
        content: 'url:' + path + '/selectTemplate.do',
        icon: 'succeed',
        lock: true,
        width: 650,
        height: 600
    });
}
//选择模板图片
function selectTemplatePic(obj){
    afterUploadCallback = {"imgURLS": templatePicShow};
}

function templatePicShow(urls) {
    var str="<div style='position:relative'>";
    for (var i = 0; i < urls.length; i++) {
        str += '<span style="padding-left: 14px;"><input type="hidden" name="blankimg" value="' + urls[i].src + '">' +
            '<img src=' + urls[i].src + ' height=\"80px\" width=\"80px\" />' +
            '<a onclick="removeTemplatePic(this)" style="position: absolute;top: 0px;" href=\'javascript:void(0)\'>&times;</a></span>';
    }
    str += "</div>";

    $("#blankImg_main").append(str);
}
function removeTemplatePic(obj){
    $(obj).parent().remove();
}
function setTemplate(obj){
    var name=$(obj).attr("name");
    $(obj).parent().find("dt").each(function (i, d) {
        if($(d).attr("name")==name){
            $(d).attr("class","new_tab_1");
        }else{
            $(d).attr("class","new_tab_2");
        }
    });
    if(name=="template"){
        $("#"+name).show();
        $("#templatepic").hide();
    }else if(name=="templatepic"){
        $("#"+name).show();
        $("#template").hide();
    }
}