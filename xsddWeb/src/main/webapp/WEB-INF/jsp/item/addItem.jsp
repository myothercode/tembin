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
<script type="text/javascript" src=<c:url value ="/js/item/shipping.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/item/itemAddress.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/item/paypal.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/item/returnpolicy.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/item/buyer.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/item/description.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/item/disprice.js" /> ></script>
<%--<script type="text/javascript" src=
        <c:url value="/js/ueditor/ueditor.config.js"/>></script>
<script type="text/javascript" src=
        <c:url value="/js/ueditor/ueditor.all.js"/>></script>
<script type="text/javascript" src=
        <c:url value="/js/ueditor/lang/zh-cn/zh-cn.js"/>></script>
<script type="text/javascript" src=
        <c:url value="/js/ueditor/dialogs/image/imageextend.js"/>></script>--%>
<script type="text/javascript" src=
        <c:url value="/js/batchAjaxUtil.js"/>></script>
<script type="text/javascript" src=
        <c:url value="/js/item/addItem.js"/>></script>
<script type="text/javascript" src=
        <c:url value="/js/item/addItem2.js"/>></script>
<script type="text/javascript" src=
<c:url value="/js/table/jquery.tablednd.js"/>></script>

<link href=
      <c:url value="/css/bootstrap/bootstrap.css"/> rel="stylesheet">
<link href=
      <c:url value="/css/bootstrap/bootstrap-overrides.css"/> type="text/css" rel="stylesheet">

<!-- global styles -->
<link rel="stylesheet" type="text/css" href=<c:url value="/css/compiled/layout.css"/>>
<link rel="stylesheet" type="text/css" href=<c:url value="/css/compiled/elements.css"/>>
<link rel="stylesheet" type="text/css" href=<c:url value="/css/compiled/icons.css"/>>
<!-- open sans font -->


<link href=
      <c:url value="/js/gridly/css/style.css"/> rel='stylesheet' type='text/css'>
<script src=
        <c:url value="/js/gridly/js/jquery.sortable.js"/> type='text/javascript'></script>


<!-- libraries -->
<link href=
      <c:url value="/css/lib/font-awesome.css"/> type="text/css" rel="stylesheet"/>

<!-- this page specific styles -->
<link rel="stylesheet" href=
<c:url value="/css/compiled/gallery.css"/> type="text/css" media="screen"/>


<!-- bootstrap -->
<link href=
      <c:url value="/js/selectBoxIt/stylesheets/jquery.selectBoxIt.css"/> rel="stylesheet">

<%--<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" />--%>
<%--<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />--%>
<%--<link type="text/css" rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />--%>
<%--<link rel="stylesheet" href="http://gregfranko.com/jquery.selectBoxIt.js/css/jquery.selectBoxIt.css" />--%>


<%--<script type="text/javascript" src=
        <c:url value="/js/selectBoxIt/javascripts/jquery-ui.min.js"/>></script>--%>
<%--<script src="http://gregfranko.com/jquery.selectBoxIt.js/js/jquery.selectBoxIt.min.js"></script>--%>
<script type="text/javascript" src=
        <c:url value="/js/selectBoxIt/javascripts/jquery.selectBoxIt.min.js"/>></script>
    <script type="text/javascript" src=
            <c:url value="/js/jquery-easyui/jquery.easyui.min.js"/>></script>

<script>

    var myDescription = null;
    var payid = '${item.payId}';
    var buyid = '${item.buyerId}';
    var discountpriceinfoId = '${item.discountpriceinfoId}';
    var itemLocationId = '${item.itemLocationId}';
    var returnpolicyId = '${item.returnpolicyId}';
    var shippingDeailsId = '${item.shippingDeailsId}';
    var sellerItemInfoId = '${item.sellerItemInfoId}';
    var imageUrlPrefix = '${imageUrlPrefix}';
    var url = window.location.href;
    var title = '${item.title}';
    var subtitle = '${item.subtitle}';
    $(document).ready(function () {
        $("#showImgico").attr("src",path+"/img/new_list_ico.png");
        var categoryId = '${item.categoryid}';
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").hide();
        $("dt[name='priceMessage']").show();


        $("div[name='showModel']").show();
        $("div[name='showModel']").hide();
        $("div[name='priceMessage']").addClass("new_ic_1");
        $("#priceMessage").show()


        var srcs = '${ttit.templateViewUrl}';
        $("#templateUrl").attr("src", imageUrlPrefix + srcs);

        _sku = '${item.sku}';

        if (url.indexOf("addItem.do") != -1) {
            var numbers = getTemplateNumber();
            var json = eval("(" + localStorage.getItem("template_" + numbers) + ")");
            if (json != null) {
                var result = json.result;
                $("#templateId").val(result.templateId);
                $("#templateUrl").prop("src", result.templateUrl);
                showTemPic();
            }else{
                hiddenTemPic();
            }
            $("#picDivShow").show();
            /*$().image_editor.init("picUrls"); //编辑器的实例id
            $().image_editor.show("apicUrls"); //上传图片的按钮id*/
        }
        _invokeGetData_type = null;

        $("#form").validationEngine();
        /*myDescription = UE.getEditor('myDescription', ueditorToolBar);*/


        <c:forEach items="${lipa}" var="pa">
            $("#attTable").append(addValueTr('${pa.name}', '${pa.value}'));
        </c:forEach>

        var ebayAccount = '${item.ebayAccount}';
        $("input[name='ebayAccounts'][value='" + ebayAccount + "']").attr("checked", "checked");
        var site = '${item.site}';
        if(site!=null&&site!=""){
            $("select[name='site']").find("option[value='" + site + "']").attr("selected", true);
        }else{
            $("select[name='site']").find("option[value='311']").attr("selected", true);
        }

        var ebayaccount = '${item.ebayAccount}';
        $("select[name='ebayAccount']").find("option[value='" + ebayaccount + "']").attr("selected", true);
        var ConditionID = '${item.conditionid}';
        $("select[name='ConditionID']").find("option[value='" + ConditionID + "']").attr("selected", true);
        var listingType = '${item.listingtype}';
        if(listingType==""){
            listingType="FixedPriceItem";
        }
        $("select[name='listingType']").find("option[value='" + listingType + "']").attr("selected", true);

        $("#incount").text(title.length);
        changeRadio(listingType);

        $("select").selectBoxIt({});

        if(site!=""&&categoryId!="") {

            if (localStorage.getItem("category_att_ID" + site + "" + categoryId) != null) {
                var json = eval("(" + localStorage.getItem("category_att_ID" + site + "" + categoryId) + ")");
                var jdata = json.result;
                returnSelectStr(jdata);
            } else {
                getRequestJson(site, categoryId);
            }
        }
        //多属性
        <c:forEach items="${liv}" var="liv" varStatus="status">
        var price='${liv.startprice}';
        var str = "";
        str += "<tr style='height: 32px;'><td class='dragHandle' width='15px;'></td>";
        str += "<td width='100px'><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${liv.sku}</span><input type='hidden' name='SKU' onblur='clearThisText(this);' onkeyup='getJoinValue(this)' class='validate[required] form-control' value='${liv.sku}'></td>";
        str += "<td width='100px'><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${liv.quantity}</span><input type='hidden' name='Quantity' onblur='clearThisText(this);' onkeyup='getJoinValue(this)' size='8' class='validate[required] form-control' value='${liv.quantity}'></td>";
        str += "<td width='100px'><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>"+parseFloat(price).toFixed(2)+"</span><input type='hidden' name='StartPrice.value' onblur='clearThisText(this);' onkeyup='getJoinValue(this)'  size='8' class='validate[required] form-control' value='"+parseFloat(price).toFixed(2)+"'>&nbsp;<abbr name='curName'></abbr></td>";
        <c:forEach items="${liv.tradingPublicLevelAttr}" var="ta">
        str += "<td width='100px' style='text-align: right;'><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${ta.value}</span><input type='hidden' name='attr_Value' onkeyup='getJoinValue(this)'  class='validate[required] more-control' onblur='addb(this)' size='10' value='${ta.value}'>";
        //str +="&nbsp;<div class='numlist' style='padding-left: 8px;'><div class='ui-select' style='background-image:url("+path+"/img/arrow.gif);height:26px;margin-top:1px; width:80px;min-width:0px;'><select style='width: 80px;padding: 0px;' name='selAttValue_sel' onchange='selectAttrMorValue(this)'></select></div><div>";
        str +="&nbsp;<div class='ui-select' style='margin-bottom: 5px;background-image:url("+path+"/img/arrow.gif);height:26px; width:15px;min-width:0px;border: 0px;'><select style='width: 80px;padding: 9px;' name='selAttValue_sel' onchange='selectAttrMorValue(this)'></select></div>";
        str+="</td>";
        </c:forEach>
        str += "<td name='del' style='text-align: center;'><img src='"+path+"/img/del2.png' onclick='removeCloums(this)'></td>";
        str += "</tr>";
        $("#moreAttrs").append(str);
        </c:forEach>
        <c:forEach items="${clso}" var="lis" varStatus="status">
        $("#moreAttrs tr:eq(0)").find("th").each(function (i, d) {
            if ($(d).attr("name") == "del") {
                $(d).before("<th width='100px' style='text-align: right;'><img src='"+path+"/img/del2.png' style='vertical-align: sub;' onclick='removeCols(this)'>&nbsp;<span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${lis.value}</span><input type='hidden' onkeyup='getJoinValue(this)'"+
                "size='8' value='${lis.value}' name='attr_Name' class='' onblur='addc(this)'>&nbsp;" +
                "<div class='ui-select more-control' style='margin-bottom: 6px;background-image:url("+path+"/img/arrow.gif);height:26px; width:15px;min-width:0px;border: 0px;'>"+attrValueName+"</div>" +
                        "</th>");
            }
        });
        </c:forEach>
        $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});

        if(attrValueName!=null){
            $("#moreAttrs tr:eq(0) th").find("select").each(function(i,d){
                $(d).find("option[value='"+$(d).parent().parent().find("[name='attr_Name']").val()+"']").attr("selected","selected");
            });
        }
        changeBackcolour();
        //加载选择值的选项
        loadSelectValue();
        //加载货币符号
        var curName=$("select[name='site']").find("option:selected").attr("curid");
        $("abbr[name='curName']").text(curName);
        $("label[name='curName']").text(curName);
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
        var morePicid="";
        for (var i = 0; i < attrValue.keys.length; i++) {
            /*var m = dicMap.get(attrValue.get(attrValue.keys[i]));
             for(var j = 0;j< m.keys.length;j++){
             $('#'+attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='"+attrValue.get(attrValue.keys[i])+"' value='"+ m.get(j)+"'><img src='"+m.get(j)+"' height='50' width='50' />");
             }*/
            $("#picMore").append(addPic($("#moreAttrs tr:eq(0) th:eq(4)").find("input").val(), attrValue.get(attrValue.keys[i])));
            /*$().image_editor.init($("#moreAttrs tr:eq(0) th:eq(4)").find("input").val().replace(" ","_") + "." + attrValue.get(attrValue.keys[i]).replace(" ","_")); //编辑器的实例id

            $().image_editor.show(attrValue.get(attrValue.keys[i]).replace(" ","_")); //上传图片的按钮id
*/
            if(i==attrValue.keys.length-1){
                morePicid+='{"a":"'+$("#moreAttrs tr:eq(0) th:eq(4)").find("input").val().replace(" ","_") + '.' + attrValue.get(attrValue.keys[i]).replace(" ","_")+'","b":"'+attrValue.get(attrValue.keys[i]).replace(" ","_")+'"}';
            }else{
                morePicid+='{"a":"'+$("#moreAttrs tr:eq(0) th:eq(4)").find("input").val().replace(" ","_") + '.' + attrValue.get(attrValue.keys[i]).replace(" ","_")+'","b":"'+attrValue.get(attrValue.keys[i]).replace(" ","_")+'"},';
            }
        }
        if(morePicid!=""){
            morePicid="["+morePicid+"]";
        }
        var picstr = '';
        <c:if test="${lipic!=null}">
        var showStr = "<div class='panel' style='display: block'>";
        showStr += " <section class='example'><ul class='gbin1-list' style='padding-left: 20px;' id='picture_" + ebayAccount + "'></ul></section> ";
        showStr += " <script type=text/plain id='picUrls_" + ebayAccount + "'/>";

        showStr += "<div style='padding-left: 20px;'>&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='upload' id='apicUrls_" + ebayAccount + "' onclick='selectPic(this)' style=''>选择图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='online' id='apicUrlsSKU_" + ebayAccount + "' onclick='selectPic(this)' style=''>选择SKU图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='remote' id='apicUrlsOther_" + ebayAccount + "' onclick='selectPic(this)' style=''>选择外部图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' id='apicUrlsclear_" + ebayAccount + "' onclick='clearAllPic(this)' style=''>清空所选图片</a></b>" +
                "</div> </div> ";
        $("#showPics").append(showStr);
        /*$().image_editor.init("picUrls_" + ebayAccount); //编辑器的实例id
        $().image_editor.show("apicUrls_" + ebayAccount); //上传图片的按钮id
        $().image_editor.show("apicUrlsSKU_" + ebayAccount); //上传图片的按钮id
        $().image_editor.show("apicUrlsOther_" + ebayAccount); //上传图片的按钮id*/
        <c:forEach items="${lipic}" var="lipic" varStatus="status">
        picstr += '<li><div style="position:relative"><input type="hidden" name="pic_mackid" value="${lipic.attr1}"/><input type="hidden" name="PictureDetails_' + ebayAccount + '.PictureURL" value="${lipic.value}">' +
                '<img src='+chuLiPotoUrl("${lipic.value}")+' height="80px" width="78px" />' +
                '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div>';
        picstr += "</li>";
        </c:forEach>
        $("#picture_" + ebayAccount).append(picstr);
        </c:if>


        /*    var str = '';
        <c:forEach items="${litam}" var="tam" varStatus="status">
         str += "<div class='brick small'>";
         str += '<span><input type="hidden" name="PictureDetails.PictureURL[
        ${status.index}]" value="
        ${tam.value}"><img src="
        ${tam.value}" height="100%" width="100%" /></span>';
         str += "<a class='delete' href='#'>&times;</a></div>";
         $("#picture").append(str);
         str = "";
        </c:forEach>*/

        <c:forEach items="${lipics}" var="pics">
        $("#picturemore_${pics.tamname}").append("<input type='hidden' name='VariationSpecificValue_${pics.tamname}' value='${pics.tamname}'>");
        <c:forEach items="${pics.litam}" var="pi">
        $("#picturemore_${pics.tamname}").append("<li><div style='position:relative'><input type='hidden' name='pic_mackid_more' value='${pi.attr1}'/><input type='hidden' name='${pics.tamname}' value='${pi.value}'><img src="+chuLiPotoUrl('${pi.value}')+" height='80' width='78' /> <div style='text-align: right;background-color: dimgrey;'><img src='"+path+"/img/newpic_ico.png' onclick='removeThis(this)'></div></div></li>");
        </c:forEach>
        </c:forEach>

        var privatelisting = '${tai.privatelisting}';
        $("input[name='PrivateListing'][value='" + privatelisting + "']").attr("checked", true);
        var listingduration = '${item.listingduration}';
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
        setTimeout(function(){
            initModel();
        },100);

        var item_id = '${item.id}';
        if(item_id!=null&&item_id!=""){
            addTypeAttr();
        }

        if(site!=""&&categoryId!=""){
            getCategoryName(categoryId,site);
            $("#PrimaryCategoryshow").show();
        }else{
            $("#PrimaryCategoryshow").hide();
        }
        loadEditor(ebayAccount,morePicid);
        initTitle();
    });
</script>
<style type="text/css">
    body {
        background-color: #ffffff;
    }
</style>
<style>
    .price_div{
        float: left;
    }
    .price_div li{
        float: left;
        width: 100%;
        list-style-type: none;
        height: 45px;
    }
    .price_div li dt{
        float: left;
        text-align: right;
        width: 100px;
        line-height: 30px;
        padding-right: 20px;
        font-weight: normal;
    }
    .price_div li em{
        float: left;
        padding-right: 9px;
        font-style: normal;
        line-height: 35px;
    }
    .price_div li em input{
        padding-right: 8px;
    }
    body .dt{
        font-size: 12px;

    }
    .ui-select{
        height: 35px;
    }

    .selectboxit-options li {
        line-height: 30px;
        height: 30px;
    }
    .new h1{
        font-size: 12px;
        font-weight: bold;
    }
    .new{
        font-size: 12px;
    }
    .selectboxit-options {
        width: 270px;
    }
    #moreAttrs .form-control{
        height: 20px;
        width: 50px;
        margin-left: 0px;
        padding: 0px;
    }

    .DivSelect
    {
        position: relative;
        background-color: transparent;
        width:  18px;
        height: 17px;
        vertical-align: bottom;
        display: inline-block;
    }
    .new li dt{
        color:#000000;
    }
    .form-control{
        height:26px;
    }
    #moreAttrs .more-control{
        border: 1px solid #cccccc;
        border-radius: 4px;
    }
    .selectboxit-container span, .selectboxit-container .selectboxit-options a{
        height: 26px;
        line-height: 26px;
    }
    .selectboxit-options li {
        line-height: 26px;
        height: 26px;
    }
    .new_button{
        margin-top: 0px;
    }
    .new_view{
        background-color: #E2E1E1;
    }
    #moreAttrs tr{
        height: 32px;
    }

    .new_view li:hover{
        border:#35a5e5 1px solid;
        box-shadow: 0 0 5px rgba(81, 203, 238, 1);
        -webkit-box-shadow: 0 0 5px rgba(81, 203, 238, 1);
        -moz-box-shadow: 0 0 5px rgba(81, 203, 238, 1);
        border-radius: 5px;
    }
    .new_view li a{
        text-decoration:none
    }

    .abc{
        -moz-transform:scale(-1,1);
        -webkit-transform:scale(-1,1);
        -o-transform:scale(-1,1);
        transform:scale(-1,1);
        filter:FlipH;
    }

    .abc1 {
        -moz-transform: scale(1, -1);
        -webkit-transform: scale(1 , -1);
        -o-transform: scale(1, -1);
        transform: scale(1, -1);
        filter: FlipV;
    }

</style>
</head>
<c:set var="item" value="${item}"/>
<body>
<div class="new_all">
<form id="form" class="new_user_form">
<div class="here">当前位置：首页 > 刊登管理 > <b>刊登</b></div>
<div class="a_bal"></div>
<div class="new">
    <h1>一般信息</h1>
    <li>
        <dt>名称</dt>
        <div class="new_left">
            <input type="hidden" id="ListingMessage" name="ListingMessage"/>
            <input type="text" class="validate[required] form-control" style="width:300px;" name="itemName"
                   id="itemName"
                   value="${item.itemName}">
        </div>
    </li>
    <li>
        <dt>刊登类型</dt>
            <select name="listingType" onchange="changeRadio(this)" style="width: 100px;">
                <option value="Chinese">拍买</option>
                <option value="FixedPriceItem">固价</option>
                <option value="2">多属性</option>
            </select>
    </li>

    <li>
        <dt>站点</dt>
            <select name="site" data-size="8" onchange="selectSiteAfter()">
                <c:forEach items="${siteList}" var="sites">
                    <option value="${sites.id}" curid="${sites.value1}">${sites.name}</option>
                </c:forEach>
            </select>
    </li>
    <li>
        <dt>ebay账户</dt>
        <c:forEach items="${ebayList}" var="ebay">
            <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}"
                                             shortName="${ebay.ebayNameCode}"
                                             onchange="selectAccount(this)">${ebay.ebayNameCode}</em>
        </c:forEach>
        <%--<div class="ui-select dt5">
            <select name="ebayAccount" class="validate[required]">
                <c:forEach items="${ebayList}" var="ebay">
                    <option value="${ebay.id}">${ebay.configName}</option>
                </c:forEach>
            </select>
        </div>--%>
        <em style="color:#48a5f3">&nbsp;&nbsp;<input type="checkbox" name="OutOfStockControl" value="1" checked>是否开启无货在线 &nbsp;<img id="whzxhelp" width="18px" height="18px" src="/xsddWeb/img/help.png" /></em>
        <c:if test="${ta.apprange=='2'}">
            <em style="color:#48a5f3"><input type="checkbox" name="setView" value="1" checked>是否在描述中统计评价信息</em>
        </c:if>

    </li>
    <li>
        <dt>SKU</dt>
        <div class="new_left">
            <input type="text" name="SKU" id="sku" style="width:300px;" class="validate[required] form-control"
                   onblur="onShow(this)" value="${item.sku}">
            <b class="new_button"><a data-toggle="modal" href="javascript:void(0)"
                                     onclick="selectProduct()">选择产品</a></b>
        </div>
    </li>

    <div id="titleDiv">
        <li style="height: 35px;">
            <dt>物品标题</dt>
            <div class="new_left">
                <input type="text" name="Title_${item.ebayAccount}" id="Title" style="width:400px;"
                       class="validate[required,maxSize[80]] form-control" value="${item.title}" size="100"
                       onkeyup="incount(this)"><span id="incount">0</span>/80
            </div>
        </li>
        <li style="display:none;height: 35px;">
            <dt>子标题</dt>
            <div class="new_left">
                <input type="text" name="SubTitle_${item.ebayAccount}" style="width:400px;" class="form-control"
                       id="SubTitle"
                       value="${item.subtitle}" size="100">
            </div>
        </li>
        <li class="flip" style=" padding-left:260px;padding-bottom: 20px;height: 5px;" onclick="showSubTitle(this)"><img id="showImgico" src="" class="abc"></li>
    </div>

    <h1>分类</h1>
    <li>
        <dt>第一分类</dt>
        <div class="new_left">
            <input type="text" id="PrimaryCategory" style="width:300px;" name="PrimaryCategory.categoryID"
                   onblur="addTypeAttr(this)" class="validate[required,custom[number]] form-control"
                   title="PrimaryCategory.categoryID"  value="${item.categoryid}">
            <b class="new_button"><a data-toggle="modal" href="javascript:void(0)" onclick="selectType()">选择分类</a></b>

            <b class="new_button"><a data-toggle="modal" href="javascript:void(0)" onclick="queryType()">搜索分类</a></b>
        </div>
    </li>
    <li id="PrimaryCategoryshow" style="padding-left: 100px;"></li>
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
<span id="showPics">

</span>

    <div id="twoAttr" style="display: none;">
        <h1>多属性</h1>
        <li style="height: 100%;">
            <dt>&nbsp;</dt>
            <div>
                <table width="80%" id="moreAttrs" class="mytable-striped">
                    <tr style="height: 32px;" class="nodrop nodrag">
                        <th style="width: 15px">&nbsp;</td>
                        <th style="width: 80px;">Sub SKU</td>
                        <th style="width: 100px;">数量</td>
                        <th style="width: 100px;">价格</td>
                        <th name="del" width="80px;" style='text-align: center;'><a href="javascript:void(0);" style="color: #110BF5;text-decoration: underline;" onclick="addMoreAttr(this);">添加属性</a></td>
                    </tr>
                </table>
                <div style="padding-left: 90px;height: 40px;padding-top: 16px;">
                    <b class="new_button_1"><a data-toggle="modal" href="javascript:void(0);" onclick="addInputSKU(this)">添加SKU项</a></b>
                    <%--<b class="new_button_1"><a data-toggle="modal" href="javascript:void(0);" onclick="addMoreAttr(this);">添加属性</a></b>--%>
                </div>
            </div>
            <div id="picMore" style="padding-left: 60px;width: 150px;">

            </div>
        </li>
    </div>
    <h1>自定义物品属性</h1>
    <li style="height: 100%;">
        <dt>&nbsp;</dt>
        <div>
            <table id="attTable" width="600px;" class="mytable-striped">
                <tr style="height: 32px;">
                    <th style="text-align: center;" width="30%">名称</td>
                    <th style="text-align: center;" width="30%">值</td>
                    <th style="text-align: center;" width="30%">操作</td>
                </tr>
                <%--<tr>
                    <td colspan="3" id="trValue"></td>
                </tr>--%>
            </table>
            <div style="padding-left: 90px;height: 40px;padding-top: 16px;">
                <b class="new_button_1"><a data-toggle="modal" href="javascript:void(0);" onclick="addValue();">添加新的物品属性</a></b>
            </div>


            <div style="padding-left: 80px;padding-top: 10px" id="typeAttrs"></div>
        </div>
    </li>
    <h1>模板信息</h1>
    <div class="new_tab_ls" id="selectModel" style="padding-left: 20px;border-bottom-width: 1px;border-bottom-color: gainsboro;">
        <dt id=menu1 name="template" class=new_tab_1 onclick="setTemplate(this)">模板</dt>
        <dt id=menu2 name="templatepic" class=new_tab_2 onclick="setTemplate(this)">模板图片</dt>
    </div>
    <li style="height: 100%;padding-top: 10px;">
        <div  style="display: block;" id="template">
            <dt>模板</dt>
            <div class="new_left" id="temPicImg">
                <div id="showTemplate" style="position: relative;">
                    <input type="hidden" id="templateId" name="templateId" value="${ttit.id}">
                    <img src="" width="100px" height="100px" id="templateUrl" onerror="this.src=''">
                    <div style="text-align: right;background-color: dimgrey;width: 100px;">
                        <img src="/xsddWeb/img/newpic_ico.png" onclick="hiddenTemPic(this)">
                    </div>
                </div>
                <b class="new_button" style="margin-top: 8px;"><a href="javascript:void(0)" onclick="selectTemplate(this)">选择模板</a></b>
            </div>
        </div>
        <div style="display: none;" id="templatepic">
            <dt>模板图片</dt>
            <div style='display: block; vertical-align:text-bottom;height: 100px;'>
                <script type=text/plain id='blankImg_main'></script>
                <section class='example'><ul class='gbin1-list' style='padding-left: 60px;' id='showTemplatePic'></ul></section>
                <div style="padding-left: 60px;">
                    <b class="new_button" style="margin-top: 20px;"><a style="padding-bottom: 4px;" href='javascript:void(0)' id='blankImg_id' onclick='selectTemplatePic(this)'>选择模板图片</a></b>
                </div>
            </div>
        </div>
    </li>

    <h1>商品描述</h1>
    <li style="height: 100%;padding-bottom: 20px;">
        <dt>&nbsp;</dt>
        <div class="new_left">
            <input type="hidden" name="Description" id="Description">
            <script id="myDescription" type="text/plain" style="width:875px;height:470px;">${item.description}</script>
        </div>
    </li>

</div>
</br>
<div >
<div class="new_tab" style="width: 100%">
    <div class="new_tab_left"></div>
    <div class="new_tab_right" style="width: 10px;"></div>
    <dt class=new_ic_1 name=priceMessage onmouseover="setTabs(this)" style="font-size: 12px;">价格管理</dt>
    <dt name=pay onmouseover="setTabs(this)">付款方式</dt>
    <dt name=shippingDeails onmouseover="setTabs(this)">运输选项</dt>
    <dt name=itemLocation onmouseover="setTabs(this)">物品所在地</dt>
    <dt name=buyer onmouseover="setTabs(this)">买家要求</dt>
    <dt name=returnpolicy onmouseover="setTabs(this)">退货政策</dt>
    <dt name=discountpriceinfo onmouseover="setTabs(this)">折扣信息</dt>
    <dt name=descriptiondetails onmouseover="setTabs(this)">卖家描述</dt>
</div>
<div class="Contentbox">
<div name="showModel" id="priceMessage"  style="width: 980px;height: 500px" class="price_div">
    <br/>
    <li style="padding-top: 9px;margin-bottom: 8px;">
        <dt>物品状况</dt>
            <select name="ConditionID"  style="width: 300px;height:35px;">
                <option selected="selected" value="1000">New</option>
                <option value="1500">New other (see details)</option>
                <option value="2000">Manufacturer refurbished</option>
                <option value="2500">Seller refurbished</option>
                <option value="3000">Used</option>
                <option value="7000">For parts or not working</option>
            </select>
    </li>
    </br>
    <li>
        <dt>刊登天数</dt>
            <select name="ListingDuration"  style="width: 300px;height:35px;">
                <option value="Days_1">1 days</option>
                <option value="Days_3">3 days</option>
                <option value="Days_5">5 days</option>
                <option value="Days_7">7 days</option>
                <option value="Days_10">10 days</option>
                <option value="GTC" selected>GTC</option>
            </select>
    </li>
    <div id="Auction" style="display: none;">
<%--        <h1 style="padding-left: 50px;">拍买</h1>--%>
        <li>
            <dt>私人拍买</dt>
            <em style="color:#48a5f3"><input type="checkbox" name="PrivateListing" value="on">不向公众显示买家的名称</em>
        </li>

        <li>
            <dt>保留价</dt>
            <div class="new_left">
                <input type="text" name="ReservePrice" style="width:300px;" id="ReservePrice" class="validate[required] form-control">
                <label name="curName" style="border: 1px solid #cccccc;background-color: #eee;line-height: 26px;height: 26px;
                position: absolute;text-align: center;width: 110px;left: 340px;-webkit-border-top-right-radius: 5px;-webkit-border-bottom-right-radius: 5px;"></label>
            </div>
        </li>
        <li>
            <dt>一口价</dt>
            <div class="new_left">
                <input type="text" name="BuyItNowPrice" style="width:300px;" id="BuyItNowPrice" class="validate[required] form-control">
                <label name="curName" style="border: 1px solid #cccccc;background-color: #eee;line-height: 26px;height: 26px;
                position: absolute;text-align: center;width: 110px;left: 340px;-webkit-border-top-right-radius: 5px;-webkit-border-bottom-right-radius: 5px;"></label>
            </div>
        </li>
        <li>
            <dt>销售比基数</dt>
            <div class="new_left">
                <input type="text" name="ListingScale" id="ListingScale" class="validate[required] form-control" style="width:100px;">
            </div>
            <%--<em style="color:#48a5f3"><input type="checkbox" name="SecondFlag" value="on">二次交易机会</em>--%>
        </li>
        <li>
            <dt>是否单物品</dt>
                <select name="ListingFlag">
                    <option value="0">单独物品</option>
                    <option value="1">批量物品</option>
                </select>
        </li>
    </div>
    <div id="oneAttr" style="display: none;">
        <li>
            <dt>商品价格</dt>
            <div class="new_left">
                <input type="text" name="StartPrice.value_${item.ebayAccount}" style="width:300px;"
                       class="validate[required] form-control"
                       value="${item.startprice==null?'0':item.startprice}"/>
                <label name="curName" style="border: 1px solid #cccccc;background-color: #eee;line-height: 26px;height: 26px;
                position: absolute;text-align: center;width: 110px;left: 340px;-webkit-border-top-right-radius: 5px;-webkit-border-bottom-right-radius: 5px;"></label>
            </div>
        </li>
        <li>
            <dt>商品数量</dt>
            <div class="new_left">
                <input type="text" style="width:300px;" class="validate[required] form-control"
                       name="Quantity_${item.ebayAccount}"
                       value="${item.quantity}"/>
                <label style="border: 1px solid #cccccc;background-color: #eee;line-height: 26px;height: 26px;
                position: absolute;text-align: center;width: 110px;left: 340px;-webkit-border-top-right-radius: 5px;-webkit-border-bottom-right-radius: 5px;">PCS</label>
            </div>
        </li>
    </div>
    <br/>
    <br/>
    <br/>
    <br/>
</div>
<div name="showModel" id="buyer" style="display: none;width: 100%;height: 500px;"></div>
<div name="showModel" id="discountpriceinfo" style="display: none;width: 100%;height: 500px"></div>
<div name="showModel" id="itemLocation" style="display: none;width: 100%;height: 500px"></div>
<div name="showModel" id="pay" style="display: none;width: 100%;height: 500px"></div>
<div name="showModel" id="returnpolicy" style="display: none;width: 100%;height: 500px"></div>
<div name="showModel" id="shippingDeails" style="display: none;width: 100%;height: 500px"></div>
<div name="showModel" id="descriptiondetails" style="display: none;width: 100%;height: 500px"></div>
</div>
</div>
<input type="hidden" name="id" id="id" value="${item.id}">
<input type="hidden" name="dataMouth" id="dataMouth" value="">
<input type="hidden" name="timerListing"/>
</form>
</div>

<div id="new_view" class="new_view" style="position: fixed;bottom: 0px;overflow: visible" >

    <%--<li><a href="javascript:void(0)">预览</a></li>
    <li><a href="javascript:void(0)">检查eBay费</a></li>--%>
    <c:if test="${item.isFlag==null}">
        <li ><a href="javascript:void(0)" onclick="saveData(this,'all')">立即刊登</a></li>
        <li id="houtaikd"><a  href="javascript:void(0)" onclick="saveData(this,'Backgrounder')">后台刊登</a></li>
    </c:if>
    <li><a href="javascript:void(0)" onclick="saveData(this,'save')">保存范本</a></li>
    <c:if test="${item.isFlag==null}">
        <li><a href="javascript:void(0)" name="timeSave" onclick="selectTimer(this)">定时</a></li>
    </c:if>
    <li><a href="javascript:void(0)" onclick="saveData(this,'othersave')">另存为新范本</a></li>
    <c:if test="${item.itemId!=null}">
        <li><a href="javascript:void(0)" onclick="saveData(this,'updateListing')">更新在线刊登</a></li>
    </c:if>
    <li><a onclick="previewItem()" href="javascript:void(0)" target="_blank">预览</a></li>

    <li><a onclick="checkEbayFee()" href="javascript:void(0)">检查ebay费</a></li>
    <%--<li><a href="javascript:void(0)">更新在线刊登</a></li>
    <li><a href="javascript:void(0)">更新</a></li>--%>
    <li><a href="javascript:void(0)" onclick="closeWindow()">关闭</a></li>
</div>



<link rel="stylesheet" type="text/css" href="<c:url value ="/js/toolTip/qtip2/jquery.qtip.min.css"/> "/>
<%--<script type="text/javascript" src=<c:url value ="/js/toolTip/qtip2/jquery.qtip.min.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/toolTip/qtip2/jquery-ui.min.js" /> ></script>--%>
<script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/toolTip/loadTipTool.js" /> ></script>


<%--<link rel="stylesheet" type="text/css" href="<c:url value ="/js/toolTip/css/jQuery.toolTip.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/toolTip/js/jQuery.toolTip.js" /> ></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('.tooltip').toolTip();
    })

</script>--%>
</body>
<script>

    <c:if test="${templi!=null}">
    var strss="";
    <c:forEach items="${templi}" var="temp">
    strss += '<li><div style="position:relative"><input type="hidden" name="blankimg" value="${temp.value}">' +
            '<img src="'+chuLiPotoUrl("${temp.value}")+'" height=\"80px\" width=\"80px\" />' +
            '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div></div></li>';
    </c:forEach>
    $("#showTemplatePic").append(strss);
    </c:if>
</script>
</html>
