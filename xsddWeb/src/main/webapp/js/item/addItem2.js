/**
 * Created by Administrtor on 2014/9/1.
 */



/**如果是编辑页面初始化几个模块选项*/
function initModel(){
    /*loadPayOption();*/
    loadDataBuyer();
    loadDiscountPriceInfo();
    loadItemLocation();
    loadReturnpolicy();
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
    /*"pay":loadPayOption,*/
    "returnpolicy":loadReturnpolicy,/*
    "shippingDeails":loadShippingDeails,*/
    "descriptiondetails":loaddescriptiondetails
};
/**编辑器的工具栏*/
var ueditorToolBar={
    toolbars:[['source','FullScreen',  'Undo', 'Redo','|','link','unlink','strikethrough','forecolor','Bold','fontfamily', 'fontsize','|','cleardoc','|',
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
        htm='<img src="'+path+'/img/new_yes.png"/>';
    }else{
        htm='<img src="'+path+'/img/new_no.png"/>';
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
            {title:"站点",name:"siteName",width:"5%",align:"left",format:getSiteImg},
            {title:"买家要求",name:"option1",width:"45%",align:"left",format:makeOption3buyer},
            {title:"状态",name:"option1",width:"10%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"5%",align:"center",format:makeOption1buyer}
        ],
        selectDataNow: false,
        showIndex: false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1||url.indexOf("information/editItem.do") != -1) {
                var buyerId = getLocalStorage("buyerId");
                $("input[type='radio'][name='buyerId'][value='" + buyerId + "']").attr("checked", true);
            }else{
                $("input[name='buyerId'][value='" + buyid + "']").attr("checked", "checked");
            }
            $(".page_newlist").css("visibility","hidden");
            $(".maage_page").css("visibility","hidden");
        },isrowClick: true,
        rowClickMethod: function (obj,o){
            $("input[type='radio'][name='buyerId']").attr("checked", false);
            $("input[type='radio'][name='buyerId'][value='" + obj.id + "']").attr("checked", true);
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
        showIndex: false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1||url.indexOf("information/editItem.do") != -1) {
                var discountpriceinfoId1 = getLocalStorage("discountpriceinfoId");
                $("input[type='radio'][name='discountpriceinfoId'][value='" + discountpriceinfoId1 + "']").attr("checked", true);
            }else{
                $("input[name='discountpriceinfoId'][value='" + discountpriceinfoId + "']").attr("checked", "checked");
            }
            $(".page_newlist").css("visibility","hidden");
            $(".maage_page").css("visibility","hidden");
        },isrowClick: true,
        rowClickMethod: function (obj,o){
            $("input[type='radio'][name='discountpriceinfoId']").attr("checked", false);
            $("input[type='radio'][name='discountpriceinfoId'][value='" + obj.id + "']").attr("checked", true);
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
         showIndex: true,
         sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
         onlyFirstPage:false,
         afterLoadTable:function(){
             if (url.indexOf("addItem.do") != -1||url.indexOf("information/editItem.do") != -1) {
                 var itemLocationId1 = getLocalStorage("itemLocationId");
                 $("input[type='radio'][name='itemLocationId'][value='" + itemLocationId1 + "']").attr("checked", true);
             }else{
                 $("input[name='itemLocationId'][value='" + itemLocationId + "']").attr("checked", "checked");
             }
             $(".page_newlist").css("visibility","hidden");
             $(".maage_page").css("visibility","hidden");
         },isrowClick: true,
         rowClickMethod: function (obj,o){
             $("input[type='radio'][name='itemLocationId']").attr("checked", false);
             $("input[type='radio'][name='itemLocationId'][value='" + obj.id + "']").attr("checked", true);
         }
     });
     refreshTableAddress();
     loadItemLocationV=true;
 }

//付款选项
function loadPayOption(paypalId){
    $("#pay").initTable({
        url: path + "/ajax/loadPayPalList.do?checkFlag=0"+paypalId,
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnPay},
            {title:"名称",name:"payName",width:"8%",align:"left"},
            {title:"站点",name:"siteName",width:"8%",align:"left",format:getSiteImg},
            {title:"paypal账号",name:"payPalName",width:"8%",align:"left"},
            {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"动作",name:"option1",width:"8%",align:"left",format:makeOption1paypal}
        ],
        selectDataNow: false,
        showIndex: false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1||url.indexOf("information/editItem.do") != -1) {
                var payId1 = getLocalStorage("payId");
                $("input[type='radio'][name='payId'][value='" + payId1 + "']").attr("checked", true);
            }else{
                $("input[name='payId'][value='" + payid + "']").attr("checked", "checked");
            }
            $(".page_newlist").css("visibility","hidden");
            $(".maage_page").css("visibility","hidden");
        },isrowClick: true,
        rowClickMethod: function (obj,o){
            $("input[type='radio'][name='payId']").attr("checked", false);
            $("input[type='radio'][name='payId'][value='" + obj.id + "']").attr("checked", true);
        }
    });
    refreshTablepaypal();
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
            {title:"站点",name:"siteName",width:"10%",align:"left",format:getSiteImg},
            {title:"退货明细",name:"option1",width:"50%",align:"left",format:makeOption3returnpolicy},
            {title:"数据状态",name:"option1",width:"10%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"5%",align:"left",format:makeOption1returnpolicy}
        ],
        selectDataNow: false,
        showIndex: false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1||url.indexOf("information/editItem.do") != -1) {
                var returnpolicyId1 = getLocalStorage("returnpolicyId");
                $("input[type='radio'][name='returnpolicyId'][value='" + returnpolicyId1 + "']").attr("checked", true);
            }else{
                $("input[name='returnpolicyId'][value='" + returnpolicyId + "']").attr("checked", "checked");
            }
            $(".page_newlist").css("visibility","hidden");
            $(".maage_page").css("visibility","hidden");
        },isrowClick: true,
        rowClickMethod: function (obj,o){
            $("input[type='radio'][name='returnpolicyId']").attr("checked", false);
            $("input[type='radio'][name='returnpolicyId'][value='" + obj.id + "']").attr("checked", true);
        }
    });
    refreshTablereturnpolicy();
    loadReturnpolicyV=true;
}

/**运输选项*/
var loadShippingDeailsV=false;
function loadShippingDeails(ebayAcc){
    $("#shippingDeails").initTable({
        url: path + "/ajax/loadShippingDetailsList.do?checkFlag=0&docId="+docId+ebayAcc,
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnShippingDeails},
            {title:"名称",name:"shippingName",width:"8%",align:"left"},
            {title:"站点",name:"siteName",width:"8%",align:"left",format:getSiteImg},
            {title:"ebay账号",name:"option1",width:"8%",align:"left",format:showData},
            {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"8%",align:"left",format:shippingmakeOption1}

        ],
        selectDataNow: false,
        showIndex: false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1||url.indexOf("information/editItem.do") != -1) {
                var shippingDeailsId1 = getLocalStorage("shippingDeailsId");
                $("input[type='radio'][name='shippingDeailsId'][value='" + shippingDeailsId1 + "']").attr("checked", true);
            }else{
                $("input[name='shippingDeailsId'][value='" + shippingDeailsId + "']").attr("checked", "checked");
            }
            $(".page_newlist").css("visibility","hidden");
            $(".maage_page").css("visibility","hidden");
        },
        isrowClick: true,
        rowClickMethod: function (obj,o){
            $("input[type='radio'][name='shippingDeailsId']").attr("checked", false);
            var val = $(o).find("input[type='radio'][name='shippingDeailsId']").val();
            $("input[type='radio'][name='shippingDeailsId'][value='" + val + "']").attr("checked", true);
        }
    });
    refreshTableShipping();
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
        showIndex: false,
        sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
        onlyFirstPage:false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1||url.indexOf("information/editItem.do") != -1) {
                var sellerItemInfoId1 = getLocalStorage("sellerItemInfoId");
                $("input[type='radio'][name='sellerItemInfoId'][value='" + sellerItemInfoId1 + "']").attr("checked", true);
            }else{
                $("input[name='sellerItemInfoId'][value='" + sellerItemInfoId + "']").attr("checked", "checked");
            }
            $(".page_newlist").css("visibility","hidden");
            $(".maage_page").css("visibility","hidden");
        },isrowClick: true,
        rowClickMethod: function (obj,o){
            $("input[type='radio'][name='sellerItemInfoId']").attr("checked", false);
            $("input[type='radio'][name='sellerItemInfoId'][value='" + obj.id + "']").attr("checked", true);
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
    if($(obj.parentNode)[0].cellIndex==2||$(obj.parentNode)[0].cellIndex==3){
        var indexs=$(obj.parentNode)[0].cellIndex;
        var val = $(obj).val();
        if(indexs==3){
            val=parseFloat(val).toFixed(2)
        }
        $("#moreAttrs tr td:nth-child(" + (indexs+ 1) + ")").each(function(i,d){
            if($(d).find("input").val()==""||$(d).find("input").val()==null){
                $(d).find("span").text(val);
                $(d).find("span").show();
                $(d).find("input").val(val);
                if($(d).find("input").attr("type")=="text"){
                    $(d).find("input").attr("type","hidden");
                }
            }
        });
    }

    if ($(obj).val() != "") {
        if($(obj).validationEngine("validate")){
            $(obj).validationEngine();
            return;
        }else{
            if($(obj).prop("name")=="StartPrice.value"){
                var vl = $(obj).val();
                $(obj).val(parseFloat(vl).toFixed(2));
                $(obj).parent().find("span").text(parseFloat(vl).toFixed(2));
            }
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
        trStr = '<tr height="32px;"><td onclick="showText(this)" style="text-align: center"><span name="name" style="color: dodgerblue;">'+obj1+'</span><input type="hidden" onblur="bodyClick();"  onkeyup="getJoinValue(this)" name="name"  class="validate[required] form-control" value="' + obj1 + '"></td><td  onclick="showText(this)"  style="text-align: center"><span name="value"  style="color: dodgerblue;">'+obj2+'</span><input type="hidden"  onkeyup="getJoinValue(this)" name="value" class="validate[required] form-control"  onblur="bodyClick();" value="' + obj2 + '"></td><td style="text-align: center"><img src="'+path+'/img/del1.png"  style="width: 8px;height: 8px;" onclick="removeROW(this)"></td></tr>';
    }else{
        trStr = '<tr height="32px;"><td onclick="showText(this)" style="text-align: center"><span name="name" style="display: none;color: dodgerblue;">'+obj1+'</span><input  onblur="bodyClick();" onkeyup="getJoinValue(this)" type="text" name="name"  class="validate[required] form-control" value="' + obj1 + '"></td><td  onclick="showText(this)" style="text-align: center"><span name="value"  style="display: none;color: dodgerblue;">'+obj2+'</span><input type="text"  onkeyup="getJoinValue(this)" name="value"  onblur="bodyClick();" class="validate[required] form-control" value="' + obj2 + '"></td><td  style="text-align: center"><img src="'+path+'/img/del1.png" style="width: 8px;height: 8px;" onclick="removeROW(this)"></td></tr>';
    }
    return trStr;
}
function removeROW(obj) {
    $(obj).parent().parent().remove();
}
function addAttrTr(showName, name, value) {
    var trStr = '<tr height="32px;"><td onclick="showText(this)" style="text-align: center"><span name="name">'+showName+'</span><input type="hidden" name="name" val="1" class="validate[required] form-control" value="' + name + '"></td><td  onclick="showText(this)"  style="text-align: center"><span name="value">'+value+'</span><input type="hidden" onkeyup="getJoinValue(this)" name="value" class="validate[required] form-control" value="' + value + '"  onblur="bodyClick();"></td><td style="text-align: center"><img src="'+path+'/img/del1.png" style="width: 8px;height: 8px;" onclick="removeROW(this)"></td></tr>';
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
function changeRadio(th) {
    var obj = "";
    if($.type(th)=="object"){
        obj = $(th).val();
    }else{
        obj = th;
    }
    if (obj == "2") {
        if($("input[type='checkbox'][name='ebayAccounts']:checked").length>1){
            alert("多属性不允许多账号刊登！");
            $("input[type='radio'][name='listingType'][value='"+obj+"']").attr("checked",false);
        }
        $("#oneAttr").hide();
        $("#twoAttr").show();
        $("#Auction").hide();
        $("dt[name='priceMessage']").show();
    } else if (obj == "FixedPriceItem"||obj == "fixedpriceitem") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").hide();
        $("dt[name='priceMessage']").show();
        $("#picMore").html("");
        $("#moreAttrs").find("tr").each(function(i,d){
            if(i!=0){
                $(d).remove();
            }
        });
        $("#moreAttrs tr:eq(0) th").each(function(i,d){
            if($(d).find("select").html()!=undefined&&$(d).find("select").html()!=""){
                $(d).remove();
            }
        });
    } else if (obj == "Chinese"||obj == "chinese") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").show();
        $("dt[name='priceMessage']").show();
        $("#picMore").html("");
        $("#moreAttrs").find("tr").each(function(i,d){
            if(i!=0){
                $(d).remove();
            }
        });
        $("#moreAttrs tr:eq(0) th").each(function(i,d){
            if($(d).find("select").html()!=undefined&&$(d).find("select").html()!=""){
                $(d).remove();
            }
        });
    }
    PrimaryCategoryShowFlag();
}
//点击添回SKU输入项
function addInputSKU(obj) {
    /*var len = $(obj).parent().parent().parent().find("table").find("tr").find("td").length / $(obj).parent().parent().parent().find("table").find("tr").length - 5;
    $(obj).parent().parent().parent().parent().find("table").append(addTr(len));*/
    var len =$("#moreAttrs tr:eq(0) th").length - 5;
    $("#moreAttrs").append(addTr(len));
    $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
    changeBackcolour();
    loadSelectValue();
}

function loadSelectValue(){
    $("#moreAttrs tr:eq(0) th").find("select").each(function(i,d){
        var val=$(d).parent().parent().find("[name='attr_Name']").val();
        if(val!=""){
            $("#moreAttrs").find("select[name='selAttValue_sel']").each(function(ii,dd){
                if($(d.parentNode.parentNode)[0].cellIndex==$(dd.parentNode.parentNode)[0].cellIndex){
                    $(dd).html("");
                    var optionData = queryData(val, $("#PrimaryCategory").val());
                    var optionstr= "";
                    for (var j in optionData) {
                        optionstr += "<option value=\"" + optionData[j]['itemEnName'] + "\">" + optionData[j]['itemEnName'] + "</option>";
                    }
                    $(dd).html(optionstr);
                }
            });
        }
    });
}
function changeBackcolour(){
    $("#moreAttrs tr").hover(function() {
        $(this.cells[0]).addClass('showDragHandle');
    }, function() {
        $(this.cells[0]).removeClass('showDragHandle');
    });
}
function selectAttrMorValue(obj){
    $(obj).parent().parent().find("span").text($(obj).val());
    $(obj).parent().parent().find("span").show();
    $(obj).parent().parent().find("[type='hidden']").val($(obj).val());
    $(obj).parent().parent().find("[type='text']").val($(obj).val());
    $(obj).parent().parent().find("[type='text']").attr("type","hidden");
    addb(obj);
}
//添加一行数据，用于填写
function addTr(len) {
    var curName=$("select[name='site']").find("option:selected").attr("curid");
    $("abbr[name='curName']").text(curName);
    var str = "";
    str += "<tr>";
    str += "<td class='dragHandle'  width='5px;'></td>";
    str += "<td><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span><input type='text' name='SKU' style='width:100px;'  onblur='clearThisText(this);' onkeyup='getJoinValue(this)'  class='validate[required] form-control'></td>";
    str += "<td><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span><input type='text' name='Quantity'  onblur='clearThisText(this);'  onkeypress='return inputOnlyNUM(event,this)' onkeyup='getJoinValue(this)' size='8' class='validate[required,custom[integer]] form-control'></td>";
    str += "<td><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span><input type='text' name='StartPrice.value'  onblur='clearThisText(this);' onkeyup='getJoinValue(this)'  onkeypress='return inputNUMAndPoint(event,this,2)'  size='8' class='validate[required,custom[number]] form-control'>&nbsp;<abbr name='curName'>"+curName+"</abbr></td>";
    for (var i = 0; i < len; i++) {
        str += "<td  style='text-align: right;'><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span>" +
            "<input type='text' name='attr_Value' onkeyup='getJoinValue(this)' class='validate[required] more-control' onblur='addb(this)' size='10' >" +
            "&nbsp;<div class='ui-select' style='margin-bottom: 5px;background-image:url("+path+"/img/arrow.gif);height:26px; width:15px;min-width:0px;border: 0px;'><select size='1' style='width: 18px;position: relative;' name='selAttValue_sel' onchange='selectAttrMorValue(this)'></select></div>"+
            "</td>";
    }
    str += "<td name='del'  style='text-align: center;'><img src='"+path+"/img/del2.png' onclick='removeCloums(this)'></td>";
    str += "</tr>";
    return str;
}
function selectSiteAfter(){
    var curName=$("select[name='site']").find("option:selected").attr("curid");
    $("abbr[name='curName']").text(curName);
    $("label[name='curName']").text(curName);
}

var attrValueName="";
function getSelfAttr(id){
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    if(localStorage.getItem("category_att_ID"+siteID+""+id)!=null){
        var json= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+id) + ")");
        var jdata=json.result;
        returnSelectStr(jdata);
    }else{
        getRequestJson(siteID,id);
    }
    $("#moreAttrs tr:eq(0) th").each(function(i,d){
        if($(d).find("div").html()!=undefined){
            $(d).find("div").find("select").remove();
            $(d).find("div").html(attrValueName);
            if(attrValueName==""){
                $(d).find("div").hide();
                $("#moreAttrs").find("select[name='selAttValue_sel']").each(function(i,d){
                    $(d).html("");
                    $(d).parent().hide();
                    $(d).hide();
                });
            }else{
                $(d).find("div").show();
                $("#moreAttrs").find("select[name='selAttValue_sel']").each(function(i,d){
                    $(d).show();
                    $(d).parent().show();
                });
            }
        }
    });
}

function returnSelectStr(jdata){
    if((jdata==null || jdata.length==0)||jdata[0]['itemEnName']=='noval'){
        attrValueName="";
        return;
    }
    var m=new Array();
    for(var i in jdata){
        var m1=jdata[i]['itemId'];
        m.push(m1);
    }
    var finalm=arrDistinct(m);
    attrValueName="<select size='1' style='width: 18px;position: relative;' onchange='selectAttrValue(this)'>";
    attrValueName+="<option value=''>--选择--</option>";
        for(var i=0;i<finalm.length;i++){
            attrValueName+="<option value='"+finalm[i]+"'>"+finalm[i]+"</option>";
        }
    attrValueName+="</select>";
}
function getRequestJson(siteID,id){
    return;//转移至additem.js getSpec方法
    /*var url=path+"/ajax/getCategorySpecifics.do";
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
                returnSelectStr(jdata);
                //alert(localStorage.getItem("aaa").length);
                //_invokeGetData_type=null;
            },
            function(m,r){
                alert(r)}
        ],{stringFormat:true}
    );*/
}
function selectAttrValue(obj){
    $(obj).parent().parent().find("[name='attr_Name']").val($(obj).val());
    $(obj).parent().parent().find("[name='attr_Name']").attr("type","hidden");
    $(obj).parent().parent().find("span").text($(obj).val());
    $(obj).parent().parent().find("span").show();
    if($(obj).val()!=null||$(obj).val()!=""){//当用户选属性时，给下面选择付值
        $("select[name='selAttValue_sel']").each(function(i,d){
            if($(obj.parentNode.parentNode)[0].cellIndex==$(d.parentNode.parentNode)[0].cellIndex){
                $(d).html("");
                var optionData = queryData($(obj).val(), $("#PrimaryCategory").val());
                var optionstr= "";
                for (var i in optionData) {
                    optionstr += "<option value=\"" + optionData[i]['itemEnName'] + "\">" + optionData[i]['itemEnName'] + "</option>";
                }
                $(d).html(optionstr);
            }
        });
    }
}
//添加属性列
function addMoreAttr(obj) {
    $("#moreAttrs").find("tr").each(function (i, d) {
        /*$(obj).parent().parent().parent().find("table").find("tr").each(function (i, d) {*/
        $(d).find("th,td").each(function (ii, dd) {
            if ($(dd).attr("name") == "del") {
                if (i == 0) {
                    $(dd).before("<th width='100px' style='text-align: right;'><img style='vertical-align: sub;' src='"+path+"/img/del2.png' onclick='removeCols(this)'>&nbsp;<span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span>" +
                        "<input type='text' size='8' onkeyup='getJoinValue(this)' class='validate[required]'" +
                        " name='attr_Name' onblur='addc(this)'>&nbsp;<div class='ui-select more-control' style='background-image:url("+path+"/img/arrow.gif);height:26px;margin-top: -5px; width:15px;min-width:0px;border: 0px;'>"
                        +attrValueName+"</div></th>");
                } else {
                    $(dd).before("<td width='100px' style='text-align: right;'><span style='display:none;color: dodgerblue;' onclick='showMoreAttrsText(this)'></span>" +
                        "<input type='text' size='10' name='attr_Value' onblur='addb(this)' " +
                        " onkeyup='getJoinValue(this)' class='validate[required] more-control'>" +
                        "&nbsp;<div class='ui-select' style='margin-bottom: 5px;background-image:url("+path+"/img/arrow.gif);height:26px;margin-top: -5px; width:15px;min-width:0px;border: 0px;'><select size='1' style='width: 18px;position: relative;' name='selAttValue_sel' onchange='selectAttrMorValue(this)'></select></div>"+
                        "</td>");
                }
            }
        });
    });
    // $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
}
function showAttrDiv(obj){
    var x = obj.x
    var y = obj.y;
    $(obj).parent().find("div").show();
}
//删除多属性中的SKU输入项
function removeCloums(obj) {
    $(obj).parent().parent().remove();
    var attrValue = new Map();
    $("#moreAttrs tr th:nth-child(5)").each(function (i, d) {
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
   /* alert($("#moreAttrs tr th:eq(" + ($(obj.parentNode)[0].cellIndex) + ")").html());
    $("#moreAttrs tr th:eq(" + ($(obj.parentNode)[0].cellIndex) + ")").remove();
    alert($("#moreAttrs tr td:nth-child(" + ($(obj.parentNode)[0].cellIndex + 1) + ")"));*/
    $("#moreAttrs tr td:nth-child(" + ($(obj.parentNode)[0].cellIndex + 1) + ")").remove();
    $("#moreAttrs tr th:eq(" + ($(obj.parentNode)[0].cellIndex) + ")").remove();




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
                attrValue.put(replaceTSFH($(d).find("input[name='attr_Value']").val().replace(" ","_").replace(".","")), $(d).find("input[name='attr_Value']").val().replace(" ","_").replace(".",""));
            }
        });
        $("#picMore").html("");
        for (var i = 0; i < attrValue.keys.length; i++) {
            $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
            $().image_editor.init(attrName+"this"+attrValue.get(attrValue.keys[i])); //编辑器的实例id
            $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
        }
    }
    clearThisText(obj);
}
//当输入属性值时调用的方法
function addb(obj) {
    /*if(attrName==""||attrName==null||attrName.size()==0){
        attrName = $("#moreAttrs  tr:eq(0) td:eq(4)").find("[type='hidden'][name='attr_Name']").val();
    }*/
    attrName = $("#moreAttrs  tr:eq(0) th:eq(4)").find("[type='hidden'][name='attr_Name']").val();
    var attrValue = new Map();
    if ($(obj.parentNode)[0].cellIndex == 4||(obj.tagName=="SELECT"&&$(obj.parentNode.parentNode)[0].cellIndex)) {
        $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
            if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
                var vals = $(d).find("input[name='attr_Value']").val();
                attrValue.put(replaceTSFH(vals.replace(" ","_").replace(".","").replace("+","")), replaceTSFH(vals.replace(" ","_").replace(".","").replace("+","")));
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
                $('#picturemore_' + attrValue.get(attrValue.keys[i])).append("<li><div style='position:relative'><input type='hidden' name='pic_mackid_more'/><input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='80' width='78' /><div style='text-align: right;background-color: dimgrey;'><img src='"+path+"/img/newpic_ico.png' onclick='removeThis(this)'></div></div></li>");
                //$('#' + attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='50' width='50' />");
            }
            $().image_editor.init(attrName+"this"+attrValue.get(attrValue.keys[i])); //编辑器的实例id
            $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
        }
    }
    clearThisText(obj);
}
function addPic(attrName, attrValue) {
    var vals = "";
    $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
        if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
            /*var vals = $(d).find("input[name='attr_Value']").val();
            attrValue.put(vals.replace(" ","_").replace(".",""), vals.replace(" ","_").replace(".",""));*/
            if(replaceTSFH($(d).find("input[name='attr_Value']").val().replace(" ","_").replace(".","").replace("+",""))==attrValue||$(d).find("input[name='attr_Value']").val()==attrValue){
                vals=$(d).find("input[name='attr_Value']").val();
            }
        }
    });
    var str = "";
    str += "<div><div style='padding-top: 20px;'>" + attrName + ":" + vals + "</div> <section class='example' style='width: 1200px;'><ul class='gbin1-list' style='padding-left: 20px;' id='picturemore_"+replaceTSFH(attrValue.replace(" ","_").replace(".","").replace("+",""))+"'></ul><div class='a_bal' style='margin-top: 20px;'></div></section> <script type=text/plain id='" + replaceTSFH(attrName.replace(" ","_").replace(".","").replace("+","")) + "this" + replaceTSFH(attrValue.replace(" ","_").replace(".","").replace("+","")) + "' />";
    str += "<div style='padding-left: 20px; '>" +
        "<b style='height: 32px;margin-top: 20px;' class='new_button'><a href='javascript:void(0)' id=" + replaceTSFH(attrValue.replace(" ","_").replace(".","").replace("+","")) + " bsid='"+replaceTSFH(attrValue.replace(" ","_").replace(".","").replace("+",""))+"' onClick='selectPic(this)'>选择图片</a></b>";
    str += "</div></div>";
    return str;
}


/**统计当前已经选择了多少图片*/
    function countChoosePic(){
     var ii=0;
    $("#showPics").find("img").each(function(i,d){
       if($(d).attr("src").indexOf("newpic_ico.png")==-1){
            ii++
           //console.log($(d).attr("src")+"+++")
       }
    });
    return ii;
}

var afterUploadCallback = null;
var sss;
var bsid_temp=null;
//当选择图片后生成图片地址
function selectPic(a) {
    bsid_temp=null;
    //$().image_editor.show("apicUrls_" + ebayAccount); //上传图片的按钮id
   // console.log(countChoosePic()+"=====")
    if(countChoosePic()>11){
        setTimeout(function(){closeSelectPicWindow()},200) ;
        alert("最多只能上传12张图片，上传图片已超过上传张数！");
        return;
    }
    sss = a.id;
    bsid_temp = $(a).attr("bsid");
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
        var picArrays = new Array();
        var js = 0;
        for (var i = 0; i < urls.length; i++) {
            var imgsrc = urls[i].src.replace("http@", "http:");
            var idDuff=generateMixedRandom(5);
            str += '<li><div style="position:relative"><input type="hidden" name="pic_mackid"/> <input type="hidden" name="PictureDetails'+sss.substr(sss.indexOf("_"),sss.length)+'.PictureURL" value="' + imgsrc + '">' +
                '<img id=imgtemp'+idDuff+' src=' + chuLiPotoUrl(imgsrc) + ' height=\"80px\" width=\"78px\" />' +
                '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div></div>';
            //<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div>
            str += "</li>";
            urlss+=imgsrc+",";
            $("<img/>").attr({"src": imgsrc,"id":"_imgtemp"+idDuff}).load(function() {
                if((this.width>=500&&this.width<1000) || (this.height>=500&&this.height<1000)){
                    var targgetImgIndex=subRight(this.id,5)//strGetNum(this.id);
                    $("#imgtemp"+targgetImgIndex).wrap("<div style=\"border: solid red 1px;width: 80px;\"></div>");
                    afterTipFunction("imgtemp"+targgetImgIndex,"图像大小小于1000像素，可能会影响展示效果",true);
                }else if(this.width<500 || this.height<500){
                    var targgetImgIndex=subRight(this.id,5)
                    picArrays[js] = targgetImgIndex;
                    js++;
                }
             $("#"+this.id).remove();
            });
        }
        var addhtml = $("#picture"+sss.substr(sss.indexOf("_"),sss.length)).append(str);
        setTimeout(function(){
            for(var n=0;n<picArrays.length;n++){
                //alert(picArrays[n]);
                $("#imgtemp"+picArrays[n]).parent().parent().remove();
            }
        },1000);


        str = "";
        setTimeout(function(){
            var url=path+"/ajax/saveListingPicUrl.do?urls="+urlss+"&siteid="+siteid+"&ebayid="+ebayid;
            $().invoke(url,{},
                [function(m,r){
                    for(var i =0;i< r.length;i++){
                        var tlu = r[i];
                        var len = $(addhtml).find("input[type='hidden'][name='pic_mackid']").length- r.length;
                        $(addhtml).find("input[type='hidden'][name='pic_mackid']").each(function(j,d){
                            if(($(d).val()==""||$(d).val()==null)&&(i+len)==j){
                                $(d).val(tlu.mackId);
                                if(tlu.mackId==""){
                                    $(d).parent().parent().remove();
                                }
                            }
                        });
                        /*$(addhtml).find("input[type='hidden'][name='pic_mackid']").each(function(i,d){
                            if($(d).val()==""||$(d).val()==null){
                                $(d).parent().parent().remove();
                            }
                        });*/
                    }
                },
                    function(m,r){
                        alert(r);
                    }]
            );
        },2000);

        $("#picNumber").text(countChoosePic());
    } else {//多属性图片
        var str = '';
        var urlss= '';
        if($('#picturemore_' + sss).find("[name*='VariationSpecificValue_']").length==0){
            var selectvalue = $('#picturemore_' + sss).parent().parent().find("div :eq(0)").text();
            $('#picturemore_' + sss).append("<input type='hidden' name='VariationSpecificValue_" + sss + "' value='" + selectvalue.substr(selectvalue.indexOf(":")+1) + "'>");
        }else{
            var selectvalue = $('#picturemore_' + sss).parent().parent().find("div :eq(0)").text();
            $("input[name='VariationSpecificValue_"+sss+"']").val(selectvalue.substr(selectvalue.indexOf(":")+1));
        }
        for (var i = 0; i < urls.length; i++) {
            str+="<li><div style='position:relative'><input type='hidden' name='pic_mackid_more'/><input type='hidden' name='" + sss + "' value='" + urls[i].src.replace("http@", "http:") + "'><img src='" + chuLiPotoUrl(urls[i].src.replace("http@", "http:")) + "' height='80' width='78' /><div style='text-align: right;background-color: dimgrey;'><img src='"+path+"/img/newpic_ico.png' onclick='removeThis(this)'></div></div></li>";
            urlss+=urls[i].src.replace("http@", "http:")+",";
        }
        var addhtmlstr = $('#picturemore_' + sss).append(str);
        str="";
        var url=path+"/ajax/saveListingPicUrl.do?urls="+urlss+"&siteid="+siteid+"&ebayid="+ebayid;
        $().invoke(url,{},
            [function(m,r){
                for(var i =0;i< r.length;i++){
                    var tlu = r[i];
                    var len = $("#picturemore_"+sss).parent().find("input[type='hidden'][name='pic_mackid_more']").length- r.length;
                    $("#picturemore_"+sss).parent().find("input[type='hidden'][name='pic_mackid_more']").each(function(j,d){
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
    if($('.gbin1-list')==null){console.log("没有可设置拖动的table");return;}
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
    $(obj).parent().parent().parent().remove();
    $("#picNumber").text(countChoosePic());
}
function hiddenTemPic(){
    $("#showTemplate").hide();
}
function showTemPic(){
    $("#showTemplate").show();
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
    getSelfAttr(values);
    getCategorySpecificsData(values, "typeAttrs", "afterClickAttr", "attTable");
    var site = $("select[name='site']").find("option:selected").val();
    getCategoryName(values,site);
    $("#PrimaryCategoryshow").show();
    PrimaryCategoryShowFlag();
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
        width: 850,
        height:600,
        lock: true
    });
}
function targetSelect(obj,event){
    console.log("aaaaaaaaaaaaaaa");
    $(obj).find("select").trigger("click");
    $(event).stopPropagation();
}
function incount(obj) {
    $(obj).parent().find("span").text($(obj).val().length);
}

function setTabs(obj) {
    $("div[name='showModel']").each(function (i, d) {
        $(d).hide();
    });
    $(obj).parent().find("dt").each(function (i, d) {
        $(d).removeClass("new_ic_1");
    });
    var name = $(obj).attr("name");
    $(obj).addClass("new_ic_1");
    $("#" + name).show();
    if(name!="priceMessage"&&name!="shippingDeails"&&name!="pay"){
        loadModelFunctions[name]();
    }
}
function closeWindow(){
    if(document.location.href.indexOf("information")!=-1){
        var api = frameElement.api, W = api.opener;
        W.itemInformation.close();
    }else{
        if(parent.location.href.indexOf("mainFrame.do")!=-1){
            document.location = path+"/itemManager.do";
        }else{
            window.close();
        }
    }

}
var selectTemplates;
function selectTemplate() {
    selectTemplates = $.dialog({title: '选择模板',
        content: 'url:' + path + '/selectTemplate.do',
        icon: 'succeed',
        lock: true,
        width: 850,
        height: 600
    });
}
//选择模板图片
function selectTemplatePic(obj){
    afterUploadCallback = {"imgURLS": templatePicShow};
}

function templatePicShow(urls) {
    var str="";
    for (var i = 0; i < urls.length; i++) {
        str += '<li><div style="position:relative"><input type="hidden" name="blankimg" value="' + urls[i].src + '">' +
            '<img src=' + urls[i].src + ' height=\"80px\" width=\"78px\" />' +
            '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div></div></li>';
    }
    $("#showTemplatePic").append(str);
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

function clearAllPic(obj){
    $(obj).parent().parent().parent().find("li").remove();
}


function querySelect(query){
    var preload_data = Array();
    var content = query.term;
    var data = {results: []};
    if (content && content != "") {
        var url = path + "/informationType/ajax/loadOrgIdItemInformationList.do";
        $().delayInvoke(url, {"content":content},
            [function (m, r) {
                for (var i = 0; i < r.length; i++) {
                    preload_data[i] = { id: r[i].id, text: r[i].sku, text1: r[i].sku};
                }
                if(preload_data.length==0){
                    preload_data[0] = { id: null, text: content, text1: content};
                }
                $.each(preload_data, function () {
                    if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                        data.results.push({id: this.id, text: this.text });
                    }
                });
                query.callback(data);
                preload_data = new Array();
            },
                function (m, r) {
                    alert(r);
                }]
        );
    } else {
        var data = {results: []};
        $.each(preload_data, function () {
            if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                data.results.push({id: this.id, text: this.text });
            }
        });
        query.callback(data);
    }
}
//初始化选择框
function initSelectMore(){
    var preload_data = new Array();
    mySelect2I([{url:path+"/informationType/ajax/loadOrgIdItemInformationList.do",
        data:{currInputName:"content"},bs:".multiSelect",multiple:false,fun:querySelect,maping:{id:"sku",text:"sku"}}]);
    $('.multiSelect').on("change", function(e) {
        var sku = e.added.text;
        var id = e.added.id;
        _sku = sku;
        $("#sku").val(sku);
        isShowPicLink();
        //加载商品图片
        var ss = new Array() ;
        var ahref = $("#showPics").find("a");
        var j=0;
        for(var i = 0;i<ahref.length;i++){
            if(ahref[i].id!=null&&ahref[i].id!=""){
                ss[j]=ahref[i].id.substr(ahref[i].id.indexOf("_")+1,ahref[i].id.length);
                j++;
            }
        }

        if(id==null){
            $("#itemName").val("");
            $("#Title").val("");
            $("#PrimaryCategory").val("");
            $("#PrimaryCategoryshow").text("");
            $("#PrimaryCategoryshow").hide();
            for(var js = 0;js<ss.length;js++){
                $("#picture_"+ss[js]).html("");
            }
            $("#picNumber").text("0");
            return ;
        }
        //加载商品详情到范本编辑界面
        var url = path + "/informationType/ajax/loadItemInformationMessage.do";
        $().delayInvoke(url, {"id":id},
            [function (m, r) {
                if($("#itemName").val()==""){
                    $("#itemName").val(r.name);
                }
                $("#Title").val(r.name);
                $("#PrimaryCategory").val(r.typeId);
                addTypeAttr();
            },
                function (m, r) {
                    alert(r);
                }]
        );
        var urll = path+"/information/ajax/getPicList.do?informationid="+id;
        $().invoke(
            urll,
            {},
            [function (m, r) {
                for(var js = 0;js<ss.length;js++){
                    var str = '';
                    for(var i =0;i< r.length;i++){
                        str += '<li><div style="position:relative"><input type="hidden" name="pic_mackid" value="'+r[i].uuid+'"/><input type="hidden" name="PictureDetails_'+ ss[js]+'.PictureURL" value="' + r[i].attrvalue + '">' +
                            '<img src=' + r[i].attrvalue + ' height="80px" width="80px" />' +
                            '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div></div>';
                        str += "</li>";
                    }
                    $("#picture_"+ss[js]).html(str);
                }
                $("#picNumber").text(countChoosePic());
                initDraug();
            },
                function (m, r) {
                    alert(r);
                }]
        );

    });
}

