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
<script type="text/javascript" src=
        <c:url value="/js/item/addItem2.js"/>></script>
<script type="text/javascript" src=
<c:url value="/js/table/jquery.tablednd.js"/>></script>

<!-- bootstrap -->
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
    $(document).ready(function () {
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
            }
            $("#picDivShow").show();
            $().image_editor.init("picUrls"); //编辑器的实例id
            $().image_editor.show("apicUrls"); //上传图片的按钮id
        }
        _invokeGetData_type = null;

        $("#form").validationEngine();
        myDescription = UE.getEditor('myDescription', ueditorToolBar);


        <c:forEach items="${lipa}" var="pa">
            $("#attTable").append(addValueTr('${pa.name}', '${pa.value}'));
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
        str += "<tr style='height: 32px;'><td class='dragHandle' width='5px;'></td>";
        str += "<td><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${liv.sku}</span><input type='hidden' name='SKU' onblur='clearThisText(this);' onkeyup='getJoinValue(this)' class='validate[required] form-control' value='${liv.sku}'></td>";
        str += "<td><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${liv.quantity}</span><input type='hidden' name='Quantity' onblur='clearThisText(this);' onkeyup='getJoinValue(this)' size='8' class='validate[required] form-control' value='${liv.quantity}'></td>";
        str += "<td><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${liv.startprice}</span><input type='hidden' name='StartPrice.value' onblur='clearThisText(this);' onkeyup='getJoinValue(this)'  size='8' class='validate[required] form-control' value=${liv.startprice}></td>";
        <c:forEach items="${liv.tradingPublicLevelAttr}" var="ta">
        str += "<td><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${ta.value}</span><input type='hidden' name='attr_Value' onkeyup='getJoinValue(this)'  class='validate[required] form-control' onblur='addb(this)' size='10' value='${ta.value}'></td>";
        </c:forEach>
        str += "<td name='del'><img src='"+path+"/img/del.png' onclick='removeCloums(this)'></td>";
        str += "</tr>";
        $("#moreAttrs").append(str);
        </c:forEach>
        <c:forEach items="${clso}" var="lis" varStatus="status">
        $("#moreAttrs tr:eq(0)").find("td").each(function (i, d) {
            if ($(d).attr("name") == "del") {
                $(d).before("<td width='10%'><span style='color: dodgerblue;' onclick='showMoreAttrsText(this)'>${lis.value}</span><input type='hidden' onkeyup='getJoinValue(this)' size='8' value='${lis.value}' name='attr_Name' onblur='addc(this)'>&nbsp;&nbsp;<img src='"+path+"/img/del.png' onclick='removeCols(this)'></td>");
            }
        });
        </c:forEach>
        $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
        changeBackcolour();

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
            $("#picMore").append(addPic($("#moreAttrs tr:eq(0) td:eq(4)").find("input").val(), attrValue.get(attrValue.keys[i])));
            $().image_editor.init($("#moreAttrs tr:eq(0) td:eq(4)").find("input").val() + "." + attrValue.get(attrValue.keys[i])); //编辑器的实例id
            $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
        }
        var picstr = '';
        <c:if test="${lipic!=null}">
        var showStr = "<div class='panel' style='display: block'>";
        showStr += " <section class='example'><ul class='gbin1-list' style='padding-left: 20px;' id='picture_" + ebayAccount + "'></ul></section> ";
        showStr += " <script type=text/plain id='picUrls_" + ebayAccount + "'/>";
        showStr += " <div style='padding-left: 60px;'>&nbsp;&nbsp;&nbsp;&nbsp;<b class='new_button'><a href='javascript:void(0)' id='apicUrls_" + ebayAccount + "' onclick='selectPic(this)' style=''>选择图片</a></b></div> </div> ";
        $("#showPics").append(showStr);
        $().image_editor.init("picUrls_" + ebayAccount); //编辑器的实例id
        $().image_editor.show("apicUrls_" + ebayAccount); //上传图片的按钮id
        <c:forEach items="${lipic}" var="lipic" varStatus="status">
        picstr += '<li><div style="position:relative"><input type="hidden" name="pic_mackid" value="${lipic.attr1}"/><input type="hidden" name="PictureDetails_' + ebayAccount + '.PictureURL" value="${lipic.value}">' +
                '<img src=${lipic.value} height="80" width="80" />' +
                '<a onclick="deletePic(this)" style="position: absolute;top: -45px;right: -15px;" href=\'javascript:void(0)\'>&times;</a></div>';
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
        $("#${pics.tamname}").before("<input type='hidden' name='VariationSpecificValue_${pics.tamname}' value='${pics.tamname}'>");
        <c:forEach items="${pics.litam}" var="pi">
        $("#${pics.tamname}").before("<span><input type='hidden' name='pic_mackid_more' value='${pi.attr1}'/><input type='hidden' name='${pics.tamname}' value='${pi.value}'><img src='${pi.value}' height='50' width='50' /> <img src='"+path+"/img/del.png' onclick='removeThis(this)'></span>&nbsp;&nbsp;&nbsp;&nbsp;");
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
        initModel();
        var item_id = '${item.id}';
        if(item_id!=null&&item_id!=""){
            addTypeAttr();
        }
        var categoryId = '${item.categoryid}';
        if(site!=""&&categoryId!=""){
            getCategoryName(categoryId,site);
            $("#PrimaryCategoryshow").show();
        }else{
            $("#PrimaryCategoryshow").hide();
        }



        <c:if test="${templi!=null}">
            var strss="<div style='position:relative'>";
            <c:forEach items="${templi}" var="temp">
                strss += '<span style="padding-left: 14px;"><input type="hidden" name="blankimg" value="${temp.value}">' +
                    '<img src="${temp.value}" height=\"80px\" width=\"80px\" />' +
                    '<a onclick="removeTemplatePic(this)" style="position: absolute;top: 0px;" href=\'javascript:void(0)\'>&times;</a></span>';
            </c:forEach>
            strss += "</div>";
            $("#showTemplatePic").append(strss);
            $().image_editor.init("blankImg_main"); //编辑器的实例id
            $().image_editor.show("blankImg_id"); //上传图片的按钮id
        </c:if>


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
</style>
</head>
<c:set var="item" value="${item}"/>
<body>
<div class="new_all">
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
            <input type="hidden" id="ListingMessage" name="ListingMessage"/>
            <input type="text" class="validate[required] form-control" style="width:300px;" name="itemName"
                   id="itemName"
                   value="${item.itemName}">
        </div>
    </li>
    <li>
        <dt>刊登类型</dt>
        <em style="color:#48a5f3"><input type="radio" name="listingType" value="Chinese"
                                         onchange="changeRadio('Chinese')">拍买</em>
        <em style="color:#48a5f3"><input type="radio" name="listingType" value="FixedPriceItem"
                                         onchange="changeRadio('FixedPriceItem')" checked="checked">固价</em>
        <em style="color:#48a5f3"><input type="radio" name="listingType" value="2" onchange="changeRadio('2')">多属性</em>
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
    <li>
        <dt>无货在线</dt>
        <em style="color:#48a5f3"><input type="checkbox" name="OutOfStockControl" value="1" checked>是否开启无货在线</em>
    </li>

    <div id="titleDiv">
        <li>
            <dt>物品标题</dt>
            <div class="new_left">
                <input type="text" name="Title_${item.ebayAccount}" id="Title" style="width:400px;"
                       class="validate[required,maxSize[80]] form-control" value="${item.title}" size="100"
                       onkeyup="incount(this)"><span id="incount">0</span>/80
            </div>
        </li>
        <li style="display:none;">
            <dt>子标题</dt>
            <div class="new_left">
                <input type="text" name="SubTitle_${item.ebayAccount}" style="width:400px;" class="form-control"
                       id="SubTitle"
                       value="${item.subtitle}" size="100">
            </div>
        </li>
        <li class="flip" style=" padding-left:260px;padding-top:9px;" onclick="showSubTitle(this)"><img src="img/new_list_ico.png"></li>
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
            <dt>属性</dt>
            <div>
                <table width="80%" id="moreAttrs" class="tablednd">
                    <tr style="height: 32px;" class="nodrop nodrag">
                        <td style="width: 15px">&nbsp;</td>
                        <td width="20%">Sub SKU</td>
                        <td width="10%">数量</td>
                        <td width="10%">价格</td>
                        <td name="del" width="30%">操作</td>
                    </tr>
                </table>
                <div style="padding-left: 120px;height: 40px;padding-top: 16px;">
                    <b class="new_button_1"><a data-toggle="modal" href="javascript:void(0);" onclick="addInputSKU(this)">添加SKU项</a></b>
                    <b class="new_button_1"><a data-toggle="modal" href="javascript:void(0);" onclick="addMoreAttr(this);">添加属性</a></b>
                </div>
            </div>
            <div id="picMore" style="padding-left: 60px;width: 150px;">

            </div>
        </li>
    </div>
    <h1>自定义物品属性</h1>
    <li style="height: 100%;">
        <dt>属性</dt>
        <div>
            <table id="attTable" width="600px;">
                <tr>
                    <td align="center" width="30%">名称</td>
                    <td align="center" width="30%">值</td>
                    <td align="center" width="30%">操作</td>
                </tr>
                <%--<tr>
                    <td colspan="3" id="trValue"></td>
                </tr>--%>
            </table>
            <div style="padding-left: 120px;height: 40px;padding-top: 16px;">
                <b class="new_button_1"><a data-toggle="modal" href="javascript:void(0);" onclick="addValue();">添加新的物品属性</a></b>
            </div>


            <div style="padding-left: 80px;" id="typeAttrs"></div>
        </div>
    </li>
    <li style="padding-top: 9px;">
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
    <div class="new_tab_ls" id="selectModel" style="padding-left: 20px;border-bottom-width: 1px;border-bottom-color: gainsboro;">
        <dt id=menu1 name="template" class=new_tab_1 onclick="setTemplate(this)">模板</dt>
        <dt id=menu2 name="templatepic" class=new_tab_2 onclick="setTemplate(this)">模板图片</dt>
    </div>
    <li style="height: 100%;padding-top: 10px;">
        <div  style="display: block;" id="template">
            <dt>模板</dt>
            <div class="new_left">
                <div id="showTemplate">
                    <input type="hidden" id="templateId" name="templateId" value="${ttit.id}">
                    <img src="" width="200px" height="200px" id="templateUrl" onerror="this.src=''">
                </div>
                <b class="new_button"><a href="javascript:void(0)" onclick="selectTemplate(this)">选择模板</a></b>
            </div>
        </div>
        <div style="display: none;" id="templatepic">
            <dt>模板图片</dt>
            <div style='display: block; vertical-align:text-bottom;height: 100px;'>
                <script type=text/plain id='blankImg_main'></script>
                <span id="showTemplatePic"></span>
                <div style="height: 8px;"></div>
                <div style="padding-left: 100px;">
                    <b class="new_button"><a style="padding-bottom: 4px;" href='javascript:void(0)' id='blankImg_id' onclick='selectTemplatePic(this)'>选择模板图片</a></b>
                </div>
            </div>
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

</div>
<div >
<div class="new_tab" style="width: 980px">
    <dt class=new_ic_1 name=priceMessage onmouseover="setTab(this)">价格管理</dt>
    <dt name=pay onmouseover="setTab(this)">付款方式</dt>
    <dt name=shippingDeails onmouseover="setTab(this)">运输选项</dt>
    <dt name=itemLocation onmouseover="setTab(this)">物品所在地</dt>
    <dt name=buyer onmouseover="setTab(this)">买家要求</dt>
    <dt name=returnpolicy onmouseover="setTab(this)">退货政策</dt>
    <dt name=discountpriceinfo onmouseover="setTab(this)">折扣信息</dt>
    <dt name=descriptiondetails onmouseover="setTab(this)">卖家描述</dt>
</div>
<div class="Contentbox">
<div name="showModel" id="priceMessage"  style="width: 980px;height: 500px" class="price_div">
    <br/>
    <li>
        <dt>刊登天数</dt>
        <div class="ui-select dt5">
            <select name="ListingDuration">
                <option value="Days_1">1 days</option>
                <option value="Days_3">3 days</option>
                <option value="Days_5">5 days</option>
                <option value="Days_7">7 days</option>
                <option value="Days_10">10 days</option>
                <option value="GTC" selected>GTC</option>
            </select>
        </div>
    </li>
    <div id="Auction" style="display: none;">
        <h1 style="padding-left: 50px;">拍买</h1>
        <li>
            <dt>私人拍买</dt>
            <em style="color:#48a5f3"><input type="checkbox" name="PrivateListing" value="on">不向公众显示买家的名称</em>
        </li>

        <li>
            <dt>保留价</dt>
            <div class="new_left">
                <input type="text" name="ReservePrice" style="width:300px;" id="ReservePrice" class="form-control">
            </div>
        </li>
        <li>
            <dt>一口价</dt>
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
    <div id="oneAttr" style="display: none;">
        <li>
            <dt>商品价格</dt>
            <div class="new_left">
                <input type="text" name="StartPrice.value_${item.ebayAccount}" style="width:300px;"
                       class="validate[required] form-control"
                       value="${item.startprice==null?'0':item.startprice}"/>
            </div>
        </li>
        <li>
            <dt>商品数量</dt>
            <div class="new_left">
                <input type="text" style="width:300px;" class="validate[required] form-control"
                       name="Quantity_${item.ebayAccount}"
                       value="${item.quantity}"/>
            </div>
        </li>
    </div>
    <br/>
    <br/>
    <br/>
    <br/>
</div>
<div name="showModel" id="buyer" style="display: none;width: 980px;height: 500px;"></div>
<div name="showModel" id="discountpriceinfo" style="display: none;width: 980px;height: 500px"></div>
<div name="showModel" id="itemLocation" style="display: none;width: 980px;height: 500px"></div>
<div name="showModel" id="pay" style="display: none;width: 980px;height: 500px"></div>
<div name="showModel" id="returnpolicy" style="display: none;width: 980px;height: 500px"></div>
<div name="showModel" id="shippingDeails" style="display: none;width: 980px;height: 500px"></div>
<div name="showModel" id="descriptiondetails" style="display: none;width: 980px;height: 500px"></div>
</div>
</div>
<input type="hidden" name="id" id="id" value="${item.id}">
<input type="hidden" name="dataMouth" id="dataMouth" value="">
<input type="hidden" name="timerListing"/>
</form>
</div>

<div id="new_view" class="new_view" style="position: fixed;bottom: 45px;overflow: visible" >

    <%--<li><a href="javascript:void(0)">预览</a></li>
    <li><a href="javascript:void(0)">检查eBay费</a></li>--%>
    <c:if test="${item.isFlag==null}">
        <li ><a href="javascript:void(0)" onclick="saveData(this,'all')">立即刊登</a></li>
        <li id="houtaikd"><a  href="javascript:void(0)" onclick="saveData(this,'Backgrounder')">后台刊登</a></li>
    </c:if>
    <li><a href="javascript:void(0)" onclick="saveData(this,'save')">保存范本</a></li>
    <c:if test="${item.isFlag==null}">
        <li><a href="javascript:void(0)" onclick="saveData(this,'timeSave')">定时</a></li>
        <li><input name="timerStr" style="height: 24px;width: 100px" type="text"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d}'})"></li>
    </c:if>
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
</html>
