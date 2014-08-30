<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
<title></title>
<script type="text/javascript" src=
<c:url value="/js/ueditor/ueditor.config.js"/>></script>
<script type="text/javascript" src=
<c:url value="/js/ueditor/ueditor.all.js"/>></script>
<script type="text/javascript" src=
<c:url value="/js/ueditor/lang/zh-cn/zh-cn.js"/>></script>
<script type="text/javascript" src=
<c:url value="/js/ueditor/dialogs/image/imageextend.js"/>></script>
<script type="text/javascript" src=
<c:url value="/js/item/addItem.js"/>></script>
<script type="text/javascript" src=<c:url value ="/js/table/jquery.tablednd.js" /> ></script>
<!-- bootstrap -->
<link href=
      <c:url value="/css/bootstrap/bootstrap.css"/> rel="stylesheet">
<link href=
      <c:url value="/css/bootstrap/bootstrap-overrides.css"/> type="text/css" rel="stylesheet">

<!-- global styles -->
<link rel="stylesheet" type="text/css" href=<c:url value="/css/compiled/layout.css"/>>
<link rel="stylesheet" type="text/css" href=<c:url value="/css/compiled/elements.css"/>>
<link rel="stylesheet" type="text/css" href=<c:url value="/css/compiled/icons.css"/>>

<!-- libraries -->
<link href=
      <c:url value="/css/lib/font-awesome.css"/> type="text/css" rel="stylesheet"/>

<!-- this page specific styles -->
<link rel="stylesheet" href=
<c:url value="/css/compiled/gallery.css"/> type="text/css" media="screen"/>

<!-- open sans font -->


<link href=
      <c:url value="/js/gridly/css/jquery.gridly.css"/> rel='stylesheet' type='text/css'>
<link href=
      <c:url value="/js/gridly/css/sample.css"/> rel='stylesheet' type='text/css'>
<script src=
        <c:url value="/js/gridly/js/jquery-ui.js"/> type='text/javascript'></script>
<script src=
        <c:url value="/js/gridly/js/jquery.gridly.js"/> type='text/javascript'></script>
<script src=
        <c:url value="/js/gridly/js/sample.js"/> type='text/javascript'></script>
<script src=
        <c:url value="/js/gridly/js/rainbow.js"/> type='text/javascript'></script>

<script>
var myDescription = null;


$(document).ready(function () {
    var url = window.location.href;
    _sku = '${item.sku}';
    $().image_editor.init("picUrls"); //编辑器的实例id
    $().image_editor.show("apicUrls"); //上传图片的按钮id
    //加载买家要求
    _invokeGetData_type = null;
    $("#buyer").initTable({
        url: path + "/ajax/loadBuyerRequirementDetailsList.do",
        columnData: [
            {title: "选择", name: "option1", width: "8%", align: "left", format: returnBuyer},
            {title: "名称", name: "name", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "所有买家购买", name: "buyerFlag", width: "8%", align: "left"}
        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var buyerId = getLocalStorage("buyerId");
                $("input[type='radio'][name='buyerId'][value='" + buyerId + "']").attr("checked", true);
            }
        }
    });

    //加载折扣信息
    $("#discountpriceinfo").initTable({
        url: path + "/ajax/loadDiscountPriceInfoList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnDiscountpriceinfo},
            {title: "名称", name: "name", width: "8%", align: "left"},
            {title: "账户名称", name: "ebayName", width: "8%", align: "left"},
            {title: "开始时间", name: "disStarttime", width: "8%", align: "left"},
            {title: "结束时间", name: "disEndtime", width: "8%", align: "left"},
            {title: "降价", name: "madeforoutletcomparisonprice", width: "8%", align: "left"},
            {title: "是否免运费", name: "isShippingfee", width: "8%", align: "left"}
        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var discountpriceinfoId = getLocalStorage("discountpriceinfoId");
                $("input[type='radio'][name='discountpriceinfoId'][value='" + discountpriceinfoId + "']").attr("checked", true);
            }
        }
    });

    //物品所在地
    $("#itemLocation").initTable({
        url: path + "/ajax/loadItemAddressList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnItemLocation},
            {title: "名称", name: "name", width: "8%", align: "left"},
            {title: "地址", name: "address", width: "8%", align: "left"},
            {title: "国家", name: "countryName", width: "8%", align: "left"},
            {title: "邮编", name: "postalcode", width: "8%", align: "left"}
        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var itemLocationId = getLocalStorage("itemLocationId");
                $("input[type='radio'][name='itemLocationId'][value='" + itemLocationId + "']").attr("checked", true);
            }
        }
    });
    //付款选项
    $("#pay").initTable({
        url: path + "/ajax/loadPayPalList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnPay},
            {title: "名称", name: "payName", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "paypal账号", name: "payPalName", width: "8%", align: "left"},
            {title: "描述", name: "paymentinstructions", width: "8%", align: "left"}

        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var payId = getLocalStorage("payId");
                $("input[type='radio'][name='payId'][value='" + payId + "']").attr("checked", true);
            }
        }
    });

    //退货选项
    $("#returnpolicy").initTable({
        url: path + "/ajax/loadReturnpolicyList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnReturnpolicy},
            {title: "名称", name: "name", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "退货政策", name: "returnsAcceptedOptionName", width: "8%", align: "left"},
            {title: "退货天数", name: "returnsWithinOptionName", width: "8%", align: "left"},
            {title: "退款方式", name: "refundOptionName", width: "8%", align: "left"},
            {title: "退货运费由谁负担", name: "shippingCostPaidByOptionName", width: "8%", align: "left"}

        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var returnpolicyId = getLocalStorage("returnpolicyId");
                $("input[type='radio'][name='returnpolicyId'][value='" + returnpolicyId + "']").attr("checked", true);
            }
        }
    });
    //运输选项
    $("#shippingDeails").initTable({
        url: path + "/ajax/loadShippingDetailsList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnShippingDeails},
            {title: "名称", name: "shippingName", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "ebay账号", name: "ebayName", width: "8%", align: "left"}

        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var shippingDeailsId = getLocalStorage("shippingDeailsId");
                $("input[type='radio'][name='shippingDeailsId'][value='" + shippingDeailsId + "']").attr("checked", true);
            }
        }
    });
    //买家描述
    $("#descriptiondetails").initTable({
        url:path + "/ajax/loadDescriptionDetailsList.do",
        columnData:[
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnDescriptiondetails},
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"pay",name:"payInfo",width:"8%",align:"left"},
            {title:"shipping",name:"shippingInfo",width:"8%",align:"left"},
            {title:"contact",name:"contactInfo",width:"8%",align:"left"},
            {title:"Guarantee",name:"guaranteeInfo",width:"8%",align:"left"},
            {title:"Feedback",name:"feedbackInfo",width:"8%",align:"left"}
        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var sellerItemInfoId = getLocalStorage("sellerItemInfoId");
                $("input[type='radio'][name='sellerItemInfoId'][value='" + sellerItemInfoId + "']").attr("checked", true);
            }
        }
    });


    $("#form").validationEngine();
    myDescription = UE.getEditor('myDescription');

    var payid = '${item.payId}';
    var buyid = '${item.buyerId}'
    var discountpriceinfoId = '${item.discountpriceinfoId}';
    var itemLocationId = '${item.itemLocationId}';
    var returnpolicyId = '${item.returnpolicyId}';
    var shippingDeailsId = '${item.shippingDeailsId}';
    var sellerItemInfoId = '${item.sellerItemInfoId}';
    $("input[name='buyerId'][value='" + buyid + "']").attr("checked", "checked");
    $("input[name='payId'][value='" + payid + "']").attr("checked", "checked");
    $("input[name='discountpriceinfoId'][value='" + discountpriceinfoId + "']").attr("checked", "checked");
    $("input[name='itemLocationId'][value='" + itemLocationId + "']").attr("checked", "checked");
    $("input[name='returnpolicyId'][value='" + returnpolicyId + "']").attr("checked", "checked");
    $("input[name='shippingDeailsId'][value='" + shippingDeailsId + "']").attr("checked", "checked");
    $("input[name='sellerItemInfoId'][value='" + sellerItemInfoId + "']").attr("checked", "checked");
    <c:forEach items="${lipa}" var="pa">
    $("#trValue").after().append(addValueTr('${pa.name}', '${pa.value}'));
    </c:forEach>

    var ebayAccount = '${item.ebayAccount}';
    $("input[name='ebayAccounts'][value='" + ebayAccount + "']").attr("checked", "checked");


    var site = '${item.site}';
    $("select[name='site']").find("option[value='" + site + "']").attr("selected", true);
    var ebayaccount = '${item.ebayAccount}';
    $("select[name='ebayAccount']").find("option[value='" + ebayaccount + "']").attr("selected", true);
    var ConditionID = '${item.conditionid}';
    $("select[name='ConditionID']").find("option[value='" + ConditionID + "']").attr("selected", true);
    var listingType = '${item.listingtype}';
    $("input[name='listingType'][value='" + listingType + "']").attr("checked", true);
    var title = '${item.title}';
    $("#incount").text(title.length);
    changeRadio(listingType);


    //多属性
    <c:forEach items="${liv}" var="liv" varStatus="status">
    var str = "";
    str += "<tr>";
    str += "<td><input type='text' name='SKU' class='validate[required] form-control' value='${liv.sku}'></td>";
    str += "<td><input type='text' name='Quantity' class='validate[required] form-control' value='${liv.quantity}'></td>";
    str += "<td><input type='text' name='StartPrice.value' class='validate[required] form-control' value=${liv.startprice}></td>";
    <c:forEach items="${liv.tradingPublicLevelAttr}" var="ta">
    str += "<td><input type='text' name='attr_Value'  class='validate[required] form-control' onblur='addb(this)' size='10' value='${ta.value}'></td>";
    </c:forEach>
    str += "<td name='del'><a href='javascript:void(0)' onclick='removeCloums(this)'>删除</a></td>";
    str += "</tr>";
    $("#moreAttrs").append(str);
    </c:forEach>
    <c:forEach items="${clso}" var="lis" varStatus="status">
    $("#moreAttrs tr:eq(0)").find("td").each(function (i, d) {
        if ($(d).attr("name") == "del") {
            $(d).before("<td><a href='javascript:void(0)' onclick='removeCols(this)'>移除</a><input type='text' size='8' value='${lis.value}' name='attr_Name' onblur='addc(this)'></td>");
        }
    });
    </c:forEach>


    var attrValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
        if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
            attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
        }
    });
    /*var dicMap = new Map()
     for(var i = 0;i<attrValue.keys.length;i++){
     var url = $("input[name='"+attrValue.get(attrValue.keys[i])+"']");
     var dics = new Map();
     for(var j = 0;j<url.length;j++){
     dics.put(j,$(url[j]).val());
     }
     dicMap.put(attrValue.get(attrValue.keys[i]),dics);
     }*/
    $("#picMore").html("");
    for (var i = 0; i < attrValue.keys.length; i++) {
        /*var m = dicMap.get(attrValue.get(attrValue.keys[i]));
         for(var j = 0;j< m.keys.length;j++){
         $('#'+attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='"+attrValue.get(attrValue.keys[i])+"' value='"+ m.get(j)+"'><img src='"+m.get(j)+"' height='50' width='50' />");
         }*/
        $("#picMore").append(addPic($("#moreAttrs tr:eq(0) td:eq(3)").find("input").val(), attrValue.get(attrValue.keys[i])));
        //$().image_editor.init($("#moreAttrs tr:eq(0) td:eq(3)").find("input").val()+"."+attrValue.get(attrValue.keys[i])); //编辑器的实例id
        $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
    }
    var str = '';
    <c:forEach items="${litam}" var="tam" varStatus="status">
    str += "<div class='brick small'>";
    str += '<span><input type="hidden" name="PictureDetails.PictureURL[${status.index}]" value="${tam.value}"><img src="${tam.value}" height="100%" width="100%" /></span>';
    str += "<a class='delete' href='#'>&times;</a></div>";
    $("#picture").append(str);
    str = "";
    </c:forEach>

    <c:forEach items="${lipics}" var="pics">
        $("#${pics.tamname}").before("<input type='hidden' name='VariationSpecificValue_${pics.tamname}' value='${pics.tamname}'>");
        <c:forEach items="${pics.litam}" var="pi">
            $("#${pics.tamname}").before("<span><input type='hidden' name='${pics.tamname}' value='${pi.value}'><img src='${pi.value}' height='50' width='50' /> <a href='javascritp:void(0)' onclick='removeThis(this)'>移除</a></span>");
        </c:forEach>
    </c:forEach>

    var privatelisting = '${tai.privatelisting}';
    $("input[name='PrivateListing'][value='" + privatelisting + "']").attr("checked", true);
    var listingduration = '${tai.listingduration}';
    $("select[name='ListingDuration']").find("option[value='" + listingduration + "']").attr("selected", true);
    //ReservePrice,BuyItNowPrice,ListingFlag,ListingScale,SecondFlag
    var reserveprice = '${tai.reserveprice}';
    var buyitnowprice = '${tai.buyitnowprice}';
    var listingscale = '${tai.listingscale}';
    var listingflag = '${tai.listingflag}';
    var secondflag = '${tai.secondflag}';
    $("#ReservePrice").val(reserveprice);
    $("#BuyItNowPrice").val(buyitnowprice);
    $("#ListingScale").val(listingscale);
    $("select[name='ListingFlag']").find("option[value='" + listingflag + "']").prop("selected", true);
    $("input[name='SecondFlag'][value='" + secondflag + "']").prop("checked", true);
    initDraug();//初始化拖动图片
    changeBackcolour();
});

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


function addValueTr(obj1, obj2) {
    var trStr = '<tr height="20px;"><td><input type="text" name="name"  class="validate[required] form-control" value="' + obj1 + '"></td><td><input type="text" name="value" class="validate[required] form-control" value="' + obj2 + '"></td><td><a href="javascript:void(0)" onclick="removeROW(this)">移除</a></td></tr>';
    return trStr;
}
function removeROW(obj) {
    $(obj).parent().parent().remove();
}
function addAttrTr(showName, name, value) {
    var trStr = '<tr><td>' + showName + '</td><td><input type="text" name="' + name + '" value="' + value + '"></td></tr>';
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
    $("#trValue").after().append(addAttrTr(showName, name, ''));
}

function onShow(obj) {
    _sku = obj.value;
}
//选择刊登 类型，判断显示
function changeRadio(obj) {
    if (obj == "2") {
        $("#oneAttr").hide();
        $("#twoAttr").show();
        $("#Auction").hide();
    } else if (obj == "FixedPriceItem") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").hide();
    } else if (obj == "Chinese") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").show();
    }
}
//点击添回SKU输入项
function addInputSKU(obj) {
    var len = $(obj).parent().parent().find("table").find("tr").find("td").length / $(obj).parent().parent().find("table").find("tr").length - 5;
    $(obj).parent().parent().find("table").append(addTr(len));
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
    str += "<td class='dragHandle'></td>";
    str += "<td><input type='text' name='SKU'  class='validate[required] form-control'></td>";
    str += "<td><input type='text' name='Quantity' class='validate[required,custom[integer]] form-control'></td>";
    str += "<td><input type='text' name='StartPrice.value' class='validate[required,custom[number]] form-control'></td>";
    for (var i = 0; i < len; i++) {
        str += "<td><input type='text' name='attr_Value' class='validate[required] form-control' onblur='addb(this)' size='10' ></td>";
    }
    str += "<td name='del'><a href='javascript:void(0)' onclick='removeCloums(this)'>删除</a></td>";
    str += "</tr>";
    return str;
}
//添加属性列
function addMoreAttr(obj) {
    $(obj).parent().parent().find("table").find("tr").each(function (i, d) {
        $(d).find("td").each(function (ii, dd) {
            if ($(dd).attr("name") == "del") {
                if (i == 0) {
                    $(dd).before("<td><a href='javascript:void(0)' onclick='removeCols(this)'>移除</a><input type='text' size='8' class='validate[required] form-control' name='attr_Name' onblur='addc(this)'></td>");
                } else {
                    $(dd).before("<td><input type='text' size='10' name='attr_Value' onblur='addb(this)' class='validate[required] form-control'></td>");
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
    if ($(obj.parentNode)[0].cellIndex == 3) {
        attrName = obj.value;
    }

    var attrValue = new Map();
    if ($(obj.parentNode)[0].cellIndex == 3) {
        $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
            if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
                attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
            }
        });
        $("#picMore").html("");
        for (var i = 0; i < attrValue.keys.length; i++) {
            $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
        }
    }
}
//当输入属性值时调用的方法
function addb(obj) {
    var attrValue = new Map();
    if ($(obj.parentNode)[0].cellIndex == 3) {
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
            //$().image_editor.init(attrName+"."+attrValue.get(attrValue.keys[i])); //编辑器的实例id
            $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
        }
    }
}
function addPic(attrName, attrValue) {
    var str = "";
    str += "<div><div>" + attrName + ":" + attrValue + "</div><script type=text/plain id='" + attrName + "." + attrValue + "' />";
    str += "<div><a href='javascript:void(0)' id=" + attrValue + " onClick='selectPic(this)'>选择图片</a></div>";
    str += "</div>";
    return str;
}

var afterUploadCallback = null;
var sss;
//当选择图片后生成图片地址
function selectPic(a) {
    afterUploadCallback = {"imgURLS": addPictrueUrl};
    sss = a.id;
}

function addPictrueUrl(urls) {
    if (sss == "apicUrls") {//商品图片
        var str = '';
        for (var i = 0; i < urls.length; i++) {
            str += "<div class='brick small'>";
            str += '<span><input type="hidden" name="PictureDetails.PictureURL[' + i + ']" value="' + urls[i].src + '"><img src=' + urls[i].src.replace("@", ":") + ' height="100%" width="100%" /></span>'
            str += "<a class='delete' href='#'>&times;</a></div>";
            $("#picture").append(str);
            str = "";
        }
    } else {//多属性图片
        $('#' + sss).before("<input type='hidden' name='VariationSpecificValue_" + sss + "' value='" + sss + "'>");
        for (var i = 0; i < urls.length; i++) {
            $('#' + sss).before("<span><input type='hidden' name='" + sss + "' value='" + urls[i].src + "'><img src='" + urls[i].src.replace("@", ":") + "' height='50' width='50' /> <a href='javascritp:void(0)' onclick='removeThis(this)'>移除</a></span>");
        }
    }
    initDraug();//初始化拖动图片
}
function removeThis(obj) {
    $(obj).parent().remove();
}

//当输入分类，
function addTypeAttr() {
    var values = $("#PrimaryCategory").val();
    getCategorySpecificsData(values, "typeAttrs", "afterClickAttr", "attTable");
}
var CategoryType;
function selectType() {
    var title = $("#Title").val();
    if(title==null||title==""){
        alert("请输入第一标题！");
        return;
    }
    CategoryType = $.dialog({title: '选择商品分类',
        content: 'url:' + path + '/category/initSelectCategoryPage.do?title='+title,
        icon: 'succeed',
        width: 650,
        lock: true
    });
}
function incount(obj) {
    $("#incount").text($(obj).val().length);
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
}
function closeWindow(){
    var api = frameElement.api, W = api.opener;
    W.refreshTable();
    W.returnItem.close();
}
var selectTemplates;
function selectTemplate(){
    var apis = frameElement.api, W = apis.opener;
    selectTemplates = $.dialog({title: '选择模板',
        content: 'url:' + path + '/selectTemplate.do',
        icon: 'succeed',
        parent:apis,
        lock:true,
        width: 650,
        height:600
    });
}
</script>
</head>
<c:set var="item" value="${item}"/>
<body>
<form id="form" class="new_user_form">
<div class="here">当前位置：首页 > 刊登管理 > <b>刊登</b></div>
<div class="a_bal"></div>
<h3>刊登</h3>

<div class="a_bal"></div>
<div class="new">
<h1>一般信息</h1>
<li>
    <dt>名称</dt>
    <div class="new_left">
        <input type="text" class="validate[required] form-control" style="width:300px;" name="itemName" id="itemName"
               value="${item.itemName}">
    </div>
</li>
<li>
    <dt>ebay账户</dt>
    <c:forEach items="${ebayList}" var="ebay">
        <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}">${ebay.ebayNameCode}</em>
    </c:forEach>
    <%--<div class="ui-select dt5">
        <select name="ebayAccount" class="validate[required]">
            <c:forEach items="${ebayList}" var="ebay">
                <option value="${ebay.id}">${ebay.configName}</option>
            </c:forEach>
        </select>
    </div>--%>
</li>
<li>
    <dt>站点</dt>
    <div class="ui-select dt5">
        <select name="site">
            <c:forEach items="${siteList}" var="sites">
                <option value="${sites.id}">${sites.name}</option>
            </c:forEach>
        </select>
    </div>
</li>
<li>
    <dt>刊登类型</dt>
    <em style="color:#48a5f3"><input type="radio" name="listingType" value="Chinese" onchange="changeRadio('Chinese')">拍买</em>
    <em style="color:#48a5f3"><input type="radio" name="listingType" value="FixedPriceItem"
                                     onchange="changeRadio('FixedPriceItem')">固价</em>
    <em style="color:#48a5f3"><input type="radio" name="listingType" value="2" onchange="changeRadio('2')">多属性</em>
</li>
<li>
    <dt>SKU</dt>
    <div class="new_left">
        <input type="text" name="sku" id="sku" style="width:300px;" class="validate[required] form-control"
               onblur="onShow(this)" value="${item.sku}">
    </div>
</li>
<li>
    <dt>无货在线</dt>
    <em style="color:#48a5f3"><input type="checkbox" name="OutOfStockControl" value="1">是否开启无货在线</em>
</li>
<li>
    <dt>物品标题</dt>
    <div class="new_left">
        <input type="text" name="Title" id="Title" style="width:600px;"
               class="validate[required,maxSize[80]] form-control" value="${item.title}" size="100"
               onkeyup="incount(this)"><span id="incount">0</span>/80
    </div>
</li>
<li>
    <dt>子标题</dt>
    <div class="new_left">
        <input type="text" name="SubTitle" style="width:600px;" class="form-control" id="SubTitle"
               value="${item.subtitle}" size="100">
    </div>
</li>
<h1>分类</h1>
<li style="background:#F7F7F7; padding-top:9px;">
    <dt>第一分类</dt>
    <div class="new_left">
        <input type="text" id="PrimaryCategory" style="width:300px;" name="PrimaryCategory.categoryID"
               onblur="addTypeAttr(this)" class="validate[required] form-control" title="PrimaryCategory.categoryID"
               class="validate[required]" value="${item.categoryid}">
        <b class="new_button"><a data-toggle="modal" href="javascript:void(0)" onclick="selectType()">选择分类</a></b>
    </div>
</li>
<li id="PrimaryCategoryshow" style=" padding-left:80px;padding-top:9px;background:#F7F7F7"></li>
<div class="panel">
    <li style="background:#F7F7F7; padding-top:9px;">
        <dt>第二分类</dt>
        <div class="new_left">
            <input type="text" style="width:300px;" class="form-control" name="SecondaryCategory.CategoryID"
                   title="SecondaryCategory.CategoryID" value="${item.secondaryCategoryid}">
        </div>
    </li>
    <li style=" padding-left:80px;padding-top:9px;background:#F7F7F7"></li>
</div>
<h1>商品图片</h1>

<div class="panel" style="display: block">
    <section class='example'>
        <div id="picture" class="gridly">
        </div>
    </section>
    <script type=text/plain id='picUrls'></script>
    <div style="padding-left: 60px;"><a href="javascript:void(0)" id="apicUrls" onclick="selectPic(this)">选择图片</a></div>
</div>

<div id="Auction" style="display: none;">
    <h1>拍买</h1>
    <li>
        <dt>私人拍买</dt>
        <em style="color:#48a5f3"><input type="checkbox" name="PrivateListing" value="on">不向公众显示买家的名称</em>
    </li>
    <li>
        <dt>刊登天数</dt>
        <div class="ui-select dt5">
            <select name="ListingDuration">
                <option value="Days_1">1</option>
                <option value="Days_3">3</option>
                <option value="Days_5">5</option>
                <option value="Days_7">7</option>
                <option value="Days_10">10</option>
            </select>
        </div>
    </li>
    <li>
        <dt>保留价</dt>
        <div class="new_left">
            <input type="text" name="ReservePrice" style="width:300px;" id="ReservePrice" class="form-control">
        </div>
    </li>
    <li>
        <dt>保留价</dt>
        <div class="new_left">
            <input type="text" name="BuyItNowPrice" style="width:300px;" id="BuyItNowPrice" class="form-control">
        </div>
    </li>
    <li>
        <dt>销售比基数</dt>
        <div class="new_left">
            <input type="text" name="ListingScale" id="ListingScale" class="form-control" style="width:100px;">
        </div>
        <em style="color:#48a5f3"><input type="checkbox" name="SecondFlag" value="on">二次交易机会</em>
    </li>
    <li>
        <dt>是否单物品</dt>
        <div class="ui-select dt5">
            <select name="ListingFlag">
                <option value="0">单独物品</option>
                <option value="1">批量物品</option>
            </select>
        </div>
    </li>
</div>

<div id="twoAttr" style="display: none;">
    <h1>多属性</h1>
    <li style="height: 100%;">
        <dt>属性</dt>
        <div>
            <table width="80%" id="moreAttrs" class="tablednd">
                <tr style="height: 32px;" class="nodrop nodrag">
                    <td style="width: 15px">&nbsp;</td>
                    <td>Sub SKU</td>
                    <td>数量</td>
                    <td>价格</td>
                    <td name="del">操作</td>
                </tr>
            </table>
            <a href="javascript:void(0)" style="padding-left: 120px;" onclick="addInputSKU(this)">添加SKU项</a> &nbsp;&nbsp;&nbsp;&nbsp;<a
                href="javascript:void(0)" onclick="addMoreAttr(this)">添加属性</a>
        </div>
        <div id="picMore">

        </div>
    </li>
</div>
<h1>自定义物品属性</h1>

<div id="oneAttr" style="display: none;">
    <li>
        <dt>商品价格</dt>
        <div class="new_left">
            <input type="text" name="StartPrice.value" style="width:300px;" class="validate[required] form-control"
                   value="${item.startprice==null?'0':item.startprice}"/>
        </div>
    </li>
    <li>
        <dt>商品数量</dt>
        <div class="new_left">
            <input type="text" style="width:300px;" class="validate[required] form-control" name="Quantity"
                   value="${item.quantity}"/>
        </div>
    </li>
</div>
<li style="height: 100%;">
    <dt>属性</dt>
    <div>
        <table id="attTable">
            <tr>
                <td align="center">名称</td>
                <td align="center">值</td>
                <td align="center">操作</td>
            </tr>
            <tr>
                <td colspan="3" id="trValue"></td>
            </tr>
        </table>
        <a style="padding-left: 120px;" href="javascript:void(0);" onclick="addValue();">添加自定义属性</a>

        <div id="typeAttrs"></div>
    </div>
</li>
<li>
    <dt>物品状况</dt>
    <div class="ui-select dt5">
        <select name="ConditionID">
            <option selected="selected" value="1000">New</option>
            <option value="1500">New other (see details)</option>
            <option value="2000">Manufacturer refurbished</option>
            <option value="2500">Seller refurbished</option>
            <option value="3000">Used</option>
            <option value="7000">For parts or not working</option>
        </select>
    </div>
</li>
<h1>模板信息</h1>
<li style="height: 100%;">
    <dt>模板</dt>
    <div class="new_left">
        <div id="showTemplate">
            <input type="hidden" id="templateId" name="templateId">
            <img src="" id="templateUrl">
        </div>
        <a href="javascript:void(0)" onclick="selectTemplate(this)">选择模板</a>
    </div>
</li>
<h1>商品描述</h1>
<li style="height: 100%;">
    <dt>描述</dt>
    <div class="new_left">
        <input type="hidden" name="Description" id="Description">
        <script id="myDescription" type="text/plain" style="width:875px;height:300px;">${item.description}</script>
    </div>
</li>
<div class="new_view">
    <%--<li><a href="javascript:void(0)">预览</a></li>
    <li><a href="javascript:void(0)">检查eBay费</a></li>--%>
    <li><input value="立即刊登" onclick="saveData(this,'all')"></li>
    <li><input value="定时" onclick="saveData(this,'timeSave')"></li>
    <li><input value="保存范本" onclick="saveData(this,'save')"></li>
    <%--<li><a href="javascript:void(0)">更新在线刊登</a></li>
    <li><a href="javascript:void(0)">更新</a></li>--%>
    <li><input value="关闭" onclick="closeWindow()"></li>
</div>
</div>
<div class="new_tab">
    <dt class=new_ic_1 name=pay onmouseover="setTab(this)">付款方式</dt>
    <dt name=shippingDeails onmouseover="setTab(this)">运输选项</dt>
    <dt name=itemLocation onmouseover="setTab(this)">物品所在地</dt>
    <dt name=buyer onmouseover="setTab(this)">买家要求</dt>
    <dt name=returnpolicy onmouseover="setTab(this)">退货政策</dt>
    <dt name=discountpriceinfo onmouseover="setTab(this)">折扣信息</dt>
    <dt name=descriptiondetails onmouseover="setTab(this)">卖家描述</dt>
</div>

<div name="showModel" id="buyer" style="display: none;"></div>
<div name="showModel" id="discountpriceinfo" style="display: none;"></div>
<div name="showModel" id="itemLocation" style="display: none;"></div>
<div name="showModel" id="pay"></div>
<div name="showModel" id="returnpolicy" style="display: none;"></div>
<div name="showModel" id="shippingDeails" style="display: none;"></div>
<div name="showModel" id="descriptiondetails" style="display: none;"></div>


<input type="hidden" name="id" id="id" value="${item.id}">
<input type="hidden" name="dataMouth" id="dataMouth" value="">
<br/>
<br/>
<br/>
<br/>
<br/>

</form>

</body>
</html>
