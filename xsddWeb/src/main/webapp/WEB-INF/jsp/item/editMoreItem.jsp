<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/19
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/dialogs/image/imageextend.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/item/addItem.js" /> ></script>
    <script type="text/javascript" src=<c:url value="/js/item/addItem2.js"/>></script>
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
        var myDescription=null;
        function nextShows(obj){
            if(obj.value=="下一步"){
                var j=1;
                $("input[type='checkbox'][name='selectType']").each(function(i,d){
                    if($(d).prop("checked")){
                        j++;
                        $("#"+$(d).val()).show();
                    }
                });
                if(j==1){
                    alert("请选择需要修改的内容！");
                    return;
                }
                $("#selectId").hide();
                $("#showId").show();
                obj.value="确定";
                $("#previousShow").show();
            }else{
                var moreTable  = $("table[name='moreTable']").each(function(i,d){
                    $(d).find("select,input").each(function(ii,dd){
                        var name_= $(dd).prop("name");
                        var t="ShippingDetails.ShippingServiceOptions["+i+"].";
                        $(dd).prop("name",t+name_);
                    });
                });

                var interMoreTables = $("table[name='interMoreTable']").each(function(i,d){
                    $(d).find("select,input[type='text']").each(function(ii,dd){
                        var name_= $(dd).prop("name");
                        var t="ShippingDetails.InternationalShippingServiceOption["+i+"].";
                        $(dd).prop("name",t+name_);
                    });
                    $(d).find("input[type='checkbox'][name='ShipToLocation']:checked").each(function(ii,dd){
                        var name_= $(dd).prop("name");
                        var t="ShippingDetails.InternationalShippingServiceOption["+i+"].ShipToLocation["+ii+"]";
                        $(dd).prop("name",t);
                    });
                });
                $("input[name='Description']").val(myDescription.getContent());
                var data = $('#form').serialize();
                var urll = path+"/ajax/saveMoreItem.do";
                $(obj).attr("disabled",true);
                var api = frameElement.api, W = api.opener;
                $().invoke(
                        urll,
                        data,
                        [function (m, r) {
                            alert(r);
                            $(obj).attr("disabled",false);
                            W.refreshTable();
                            W.editMorepage.close();
                        },
                            function (m, r) {
                                alert(r);
                                $(obj).attr("disabled",false);
                            }]
                );
            }
        }

        function previousShows(obj){
            $("#selectId").show();
            $("#showId").hide();
            $(obj).hide();
            //$("#showId").hide();
            $("#show1").show();
            $("#show2").show();
            $("#show3").show();
            $("#nextShow").val("下一步");
        }
        var api = frameElement.api, W = api.opener;
        function closeWin(){
            W.editMorepage.close();
        }

        $(document).ready(function() {
            //$().image_editor.init("picUrls"); //编辑器的实例id
            //$().image_editor.show("apicUrls"); //上传图片的按钮id
            _sku = '${sku}';
            var paypal = '${item.payPalEmailAddress}';
            $("select[name='PayPalEmailAddress']").find("option[value='"+paypal+"']").attr("selected",true);
            var RefundOption = '${item.returnPolicy.refundOption}';
            $("select[name='ReturnPolicy.RefundOption']").find("option[value='"+RefundOption+"']").attr("selected",true);

            var ReturnsWithinOption = '${item.returnPolicy.returnsWithinOption}';
            $("select[name='ReturnPolicy.ReturnsWithinOption']").find("option[value='"+ReturnsWithinOption+"']").attr("selected",true);
            var ReturnsAcceptedOption = '${item.returnPolicy.returnsAcceptedOption}';
            $("select[name='ReturnPolicy.ReturnsAcceptedOption']").find("option[value='"+ReturnsAcceptedOption+"']").attr("selected",true);
            var ShippingCostPaidByOption = '${item.returnPolicy.shippingCostPaidByOption}';
            $("select[name='ReturnPolicy.ShippingCostPaidByOption']").find("option[value='"+ShippingCostPaidByOption+"']").attr("selected",true);

            var ConditionID = '${item.conditionID}';
            $("select[name='ConditionID']").find("option[value='"+ConditionID+"']").attr("selected",true);
            var Country = '${item.country}';
            $("select[name='Country']").find("option[value='"+Country+"']").attr("selected",true);
            var DispatchTimeMax = '${item.dispatchTimeMax}';
            var GetItFast = '${item.getItFast}';
            $("select[name='DispatchTimeMax']").find("option[value='"+DispatchTimeMax+"']").attr("selected",true);
            $("input[name='GetItFast']").attr("checked",GetItFast);
            var ListingDuration = '${item.listingDuration}';
            var PrivateListing = '${item.privateListing}';
            $("select[name='ListingDuration']").find("option[value='"+ListingDuration+"']").attr("selected",true);
            $("input[name='PrivateListing']").attr("checked",PrivateListing);

            var policyCount = '${item.buyerRequirementDetails.maximumBuyerPolicyViolations.count}';
            var policyPeriod = '${item.buyerRequirementDetails.maximumBuyerPolicyViolations.period}';
            $("select[name='BuyerRequirementDetails.MaximumBuyerPolicyViolations.Count']").find("option[value='"+policyCount+"']").attr("selected",true);
            $("select[name='BuyerRequirementDetails.MaximumBuyerPolicyViolations.Count']").attr("disabled",false);
            $("select[name='BuyerRequirementDetails.MaximumBuyerPolicyViolations.Period']").find("option[value='"+policyPeriod+"']").attr("selected",true);
            $("select[name='BuyerRequirementDetails.MaximumBuyerPolicyViolations.Period']").attr("disabled",false);
            if(policyCount!=""||policyCount!=""){
                $("input[name='MaximumBuyerPolicyViolations']").attr("checked",true);
            }
            var unpaidCount  = '${item.buyerRequirementDetails.maximumUnpaidItemStrikesInfo.count}';
            var unpaidPeriod = '${item.buyerRequirementDetails.maximumUnpaidItemStrikesInfo.period}';
            $("select[name='BuyerRequirementDetails.MaximumUnpaidItemStrikesInfo.Count']").find("option[value='"+unpaidCount+"']").attr("selected",true);
            $("select[name='BuyerRequirementDetails.MaximumUnpaidItemStrikesInfo.Count']").attr("disabled",false);
            //$("select[name='BuyerRequirementDetails.MaximumUnpaidItemStrikesInfo.Period']").find("option[value='"+unpaidPeriod+"']").attr("selected",true);
            $("select[name='BuyerRequirementDetails.MaximumUnpaidItemStrikesInfo.Period']").attr("disabled",false);
            if(unpaidCount!=""||unpaidPeriod!=""){
                $("input[name='MaximumUnpaidItemStrikesInfo']").attr("checked",true);
            }
            var LinkedPayPalAccount = '${item.buyerRequirementDetails.linkedPayPalAccount}';
            $("input[name='BuyerRequirementDetails.LinkedPayPalAccount']").attr("checked",LinkedPayPalAccount==""?false:LinkedPayPalAccount);
            var ShipToRegistrationCountry = '${item.buyerRequirementDetails.shipToRegistrationCountry}';
            $("input[name='BuyerRequirementDetails.ShipToRegistrationCountry']").attr("checked",ShipToRegistrationCountry==""?false:ShipToRegistrationCountry);

            var MaximumItemCount = '${item.buyerRequirementDetails.maximumItemRequirements.maximumItemCount}';
            var MinimumFeedbackScore = '${item.buyerRequirementDetails.maximumItemRequirements.minimumFeedbackScore}';
            var MinimumFeedbackScores = '${item.buyerRequirementDetails.minimumFeedbackScore}';
            $("select[name='BuyerRequirementDetails.MaximumItemRequirements.MaximumItemCount']").find("option[value='"+MaximumItemCount+"']").attr("selected",true);

            if(MaximumItemCount!=""){
                $("select[name='BuyerRequirementDetails.MaximumItemRequirements.MaximumItemCount']").attr("disabled",false);
                $("input[name='MaximumItemCount_flag']").attr("checked",true);
            }
            $("select[name='BuyerRequirementDetails.MaximumItemRequirements.MinimumFeedbackScore']").find("option[value='"+MinimumFeedbackScore+"']").attr("selected",true);

            if(MinimumFeedbackScore!=""){
                $("select[name='BuyerRequirementDetails.MaximumItemRequirements.MinimumFeedbackScore']").attr("disabled",false);
                $("input[name='FeedbackScore_falg']").attr("checked",true);
            }
            $("select[name='BuyerRequirementDetails.MinimumFeedbackScore']").find("option[value='"+MinimumFeedbackScores+"']").attr("selected",true);

            if(MinimumFeedbackScores!=""){
                $("select[name='BuyerRequirementDetails.MinimumFeedbackScore']").attr("disabled",false);
                $("input[name='MinimumFeedbackScore_flag']").attr("checked",true);
            }
            myDescription = UE.getEditor('myDescription');

            //运输选项
            //国内运输选项
            <c:if test="${item.shippingDetails!=null&&item.shippingDetails.shippingServiceOptions!=null}">
                <c:forEach items="${item.shippingDetails.shippingServiceOptions}" var="shi">
                    $("#shippingMore").append(createTables('${shi.shippingService}','${shi.shippingServiceCost.value}','${shi.freeShipping}','${shi.shippingServiceAdditionalCost.value}','${shi.shippingSurcharge.value}'));
                </c:forEach>
                selectSite('${siteid}');
            </c:if>
            //国际运输选项
            <c:if test="${item.shippingDetails!=null&&item.shippingDetails.internationalShippingServiceOption!=null}">
                <c:forEach items="${item.shippingDetails.internationalShippingServiceOption}" var="shi">
                    var ss = $("#inter").append(createInterTables('${shi.shippingService}','${shi.shippingServiceCost.value}','${shi.shippingServiceAdditionalCost.value}'));
                    <c:forEach items="${shi.shipToLocation}" var="tolo">
                        $(ss).find("[name='ShipToLocation']").each(function(i,d){
                            if($(d).val()=='${tolo}'){
                                $(d).attr("checked",true);
                            }
                        });
                    </c:forEach>
                </c:forEach>
            </c:if>

            var title = '${item.title}';
            $("#incount").text(title.length);

            var picstr = '';
            var ebayAccount = '${ebayAccount}';
            <c:if test="${lipic!=null}">
            var showStr = "<div class='panel' style='display: block'>";
            showStr += " <section class='example'><ul class='gbin1-list' style='padding-left: 20px;' id='picture_" + ebayAccount + "'></ul></section> ";
            showStr += " <script type=text/plain id='picUrls_" + ebayAccount + "'/>";

            showStr += "<div style='height: 110px;'></div> <div style='padding-left: 60px;'>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<b class='new_button'><a href='javascript:void(0)' id='apicUrls_" + ebayAccount + "' onclick='selectPic(this)' style=''>选择图片</a></b>" +
                    "<b class='new_button'><a href='javascript:void(0)' id='apicUrlsSKU_" + ebayAccount + "' onclick='selectPic(this)' style=''>选择SKU图片</a></b>" +
                    "<b class='new_button'><a href='javascript:void(0)' id='apicUrlsOther_" + ebayAccount + "' onclick='selectPic(this)' style=''>选择外部图片</a></b>" +
                    "<b class='new_button'><a href='javascript:void(0)' id='apicUrlsclear_" + ebayAccount + "' onclick='clearAllPic(this)' style=''>清空所选图片</a></b>" +
                    "</div> </div> ";
            $("#showPics").append(showStr);
            $().image_editor.init("picUrls_" + ebayAccount); //编辑器的实例id
            $().image_editor.show("apicUrls_" + ebayAccount); //上传图片的按钮id
            $().image_editor.show("apicUrlsSKU_" + ebayAccount); //上传图片的按钮id
            $().image_editor.show("apicUrlsOther_" + ebayAccount); //上传图片的按钮id
            <c:forEach items="${lipic}" var="lipic" varStatus="status">
            picstr += '<li><div style="position:relative"><input type="hidden" name="pic_mackid" value="${lipic.attr1}"/><input type="hidden" name="PictureDetails_' + ebayAccount + '.PictureURL" value="${lipic.value}">' +
                    '<img src=${lipic.value} height="80px" width="78px" />' +
                    '<div style="text-align: right;background-color: dimgrey;width: 78px;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div>';
            picstr += "</li>";
            </c:forEach>
            $("#picture_" + ebayAccount).append(picstr);
            </c:if>
        });

        function checkData(obj){
            if($(obj).parent().find("select").attr("disabled")=="disabled"){
                $(obj).parent().find("select").attr("disabled",false);
            }else{
                $(obj).parent().find("select").attr("disabled",true);
            }
        }
        var CategoryType;
        function selectType(){
            CategoryType=$.dialog({title: '选择商品分类',
                content: 'url:'+path+'/category/initSelectCategoryPage.do',
                icon: 'succeed',
                width:650,
                lock:true
            });
        }
        function incount(obj){
            $("#incount").text($(obj).val().length);
        }
        var shippingService = "";
        function selectSite(obj){
            var array = new Array();
            <%--<c:forEach var="obj" items="${litso}">
                array.add(${obj.shippingservice});
            </c:forEach>--%>
            var vals = "";
            if(typeof obj=="object"||typeof obj=="Object"){
                vals = obj.value;
            }else{
                vals = obj;
            }
            var urll = path+"/ajax/shipingService.do?parentID="+vals;
            var api = frameElement.api, W = api.opener;
            $().invoke(
                    urll,
                    {},
                    [function (m, r) {
                        shippingService="";
                        for(var i = 0;i< r.length;i++){
                            shippingService +=' <optgroup label="'+r[i].name1+'">';
                            for(var j = 0;j<r[i].dictionaries.length;j++){
                                shippingService +='<option value="'+r[i].dictionaries[j].id+'">'+r[i].dictionaries[j].name+'</option>';
                            }
                            shippingService +='</optgroup>';
                        }
                        $("select[name='ShippingService'][shortName='a1']").each(function(i,d){
                            $(d).html(shippingService);
                        });
                    },
                        function (m, r) {
                            alert(r);
                        }]
            );
        }

        setTimeout(function(){
            <c:forEach var="obj" items="${item.shippingDetails.shippingServiceOptions}" varStatus="ind">
            $("select[name='ShippingService'][shortName='a1']").each(function(i,d){
                if('${ind.index}'==i){
                    $(d).find("option[value='${obj.shippingService}']").attr("selected",true);
                }
            });
            </c:forEach>
        },500);
    </script>
</head>
<body>
<form id="form">
    <input type="hidden" name="ItemID" value="${itemidstr}">
    <input type="hidden" name="idsStr" value="${idsStr}">
    <input type="hidden" name="listingType" value="${item.listingType}">
    <div id="selectId">
        <table width="70%">
            <tr>
                <td><input type="checkbox" name="selectType" value="StartPrice">价格</td>
                <td><input type="checkbox" name="selectType" value="Quantity">数量</td>
                <td><input type="checkbox" name="selectType" value="PictureDetails">图片</td>
                <td><input type="checkbox" name="selectType" value="PayPal">付款</td>
            </tr>
            <tr>
                <td><input type="checkbox" name="selectType" value="ReturnPolicy">退货政策</td>
                <td><input type="checkbox" name="selectType" value="Title">标题</td>
                <td><input type="checkbox" name="selectType" value="Buyer">买家要求</td>
                <td><input type="checkbox" name="selectType" value="SKU">SKU</td>
            </tr>
            <tr>
                <td><input type="checkbox" name="selectType" value="PrimaryCategory">分类</td>
                <td><input type="checkbox" name="selectType" value="ConditionID">物品状况</td>
                <td><input type="checkbox" name="selectType" value="Location">物品所在地</td>
                <td><input type="checkbox" name="selectType" value="DispatchTimeMax">处理时间</td>
            </tr>
            <tr>
                <td><input type="checkbox" name="selectType" value="PrivateListing">私人拍卖</td>
                <td><input type="checkbox" name="selectType" value="ListingDuration">刊登天数</td>
                <td><input type="checkbox" name="selectType" value="Description">卖家描述</td>
                <td><input type="checkbox" name="selectType" value="ShippingDetails">运输选项</td>
            </tr>
        </table>
    </div>
    <div>
        <table id="showId" style="display: none;">
            <tr id="show1">
                <td colspan="2" width="80%">
                    <br/>
                    一般信息
                    <hr/>
                </td>
            </tr>
            <tr id="show2">
                <td colspan="2">
                    <table>
                        <tr>
                            <td align="right" style="width: 100px;">站点</td>
                            <td>${item.site}</td>
                        </tr>
                        <tr>
                            <td align="right" style="width: 100px;">货币</td>
                            <td>${item.currency}</td>
                        </tr>
                        <tr>
                            <td align="right" style="width: 100px;">ebay账户</td>
                            <td>${item.seller.userID}</td>
                        </tr>
                        <tr>
                            <td align="right" style="width: 100px;"></td>
                            <td><input type="checkbox" name="isUpdateFlag" value="1">是否更新在线商品</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr id="show3">
                <td colspan="2" width="80%">
                    <br/>
                    需要修改信息
                    <hr/>
                </td>
            </tr>
            <c:if test="${item.variations ==null}">
                <tr id="StartPrice" style="display: none;">
                    <td>价格</td>
                    <td><input type="text" name="StartPrice.Value" class="validate[required,custom[number]]" value="${item.startPrice.value}"></td>
                </tr>
                <tr id="Quantity" style="display: none;">
                    <td>数量</td>
                    <td><input type="text" name="Quantity" class="validate[required,custom[integer]]" value="${item.quantity}"></td>
                </tr>
            </c:if>
            <c:if test="${item.variations !=null}">
                <tr>
                    <td colspan="2">
                        <table>
                            <tr>
                                <td>SKU</td>
                                <td>数量</td>
                                <td>价格</td>
                                <c:forEach items="${item.variations.variationSpecificsSet.nameValueList}" var="nvl">
                                    <td>${nvl.name}</td>
                                </c:forEach>
                            </tr>
                            <c:forEach items="${item.variations.variation}" var="tion" varStatus="start">
                                <tr>
                                    <td><input type="text" name="Variations.Variation[${start.index}].SKU" value="${tion.SKU}"></td>
                                    <td><input type="text" name="Variations.Variation[${start.index}].Quantity" value="${tion.quantity}"></td>
                                    <td><input type="text" name="Variations.Variation[${start.index}].StartPrice.value" value="${tion.startPrice.value}"></td>
                                    <c:forEach items="${tion.variationSpecifics}" var="vs">
                                        <c:forEach items="${vs.nameValueList}" var="nvls">
                                            <td>
                                               <c:forEach items="${nvls.value}" var="val">
                                                    ${val}
                                                </c:forEach>
                                            </td>
                                        </c:forEach>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
            </c:if>
            <tr id="PictureDetails" style="display: none;">
                <td>图片</td>
                <td>
                    <div id="showPics">
                        <%--<div id="picture"></div>
                        <script type=text/plain id='picUrls'></script>
                        <div><a href="javascript:void(0)" id="apicUrls" onclick="selectPic(this)">选择图片</a></div>--%>
                    </div>
                </td>
            </tr>
            <tr id="PayPal" style="display: none;">
                <td>付款</td>
                <td>
                    <table>
                        <tr>
                        <td>paypal账号:</td>
                        <td>
                            <select name="PayPalEmailAddress">
                                <c:forEach items="${paypalList}" var="pay">
                                    <option value="${pay.configValue}">${pay.configName}</option>
                                </c:forEach>
                            </select>
                        </td>
                        </tr>
                        <%--<tr>
                            <td>付款说明:</td>
                            <td>
                                <textarea name="paypal_desc" cols="30" rows="5">${paypal.paymentinstructions}</textarea>
                            </td>
                        </tr>--%>
                    </table>
                </td>
            </tr>
            <tr id="ReturnPolicy" style="display: none;">
                <td>退货政策</td>
                <td>
                    <table>
                        <tr>
                            <td>退货政策:</td>
                            <td>
                                <select name="ReturnPolicy.ReturnsAcceptedOption">
                                    <c:forEach items="${acceptList}" var="accept">
                                        <option value="${accept.value}">${accept.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>退货天数:</td>
                            <td>
                                <select name="ReturnPolicy.ReturnsWithinOption">
                                    <c:forEach items="${withinList}" var="within">
                                        <option value="${within.value}">${within.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>退款方式:</td>
                            <td>
                                <select name="ReturnPolicy.RefundOption">
                                    <c:forEach items="${refundList}" var="pay">
                                        <option value="${pay.value}">${pay.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>退货运费由谁承担:</td>
                            <td>
                                <select name="ReturnPolicy.ShippingCostPaidByOption">
                                    <c:forEach items="${costPaidList}" var="pay">
                                        <option value="${pay.value}">${pay.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                        <td>付款说明:</td>
                        <td>
                            <textarea name="ReturnPolicy.Description" cols="30" rows="5">${item.returnPolicy.description}</textarea>
                        </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr id="Description" style="display: none;">
                <td>描述</td>
                <td>
                    <input type="hidden" name="Description">
                    <script id="myDescription" type="text/plain" style="width:775px;height:300px;">${item.description}</script>
                </td>
            </tr>
            <tr id="Title" style="display: none;">
                <td>标题</td>
                <td>
                    <input type="text" name="Title" size="100"  onkeyup="incount(this)"  class="validate[required,maxSize[80]]" value="${item.title}">
                    <span id="incount">0</span>/80
                </td>
            </tr>
            <tr id="Buyer" style="display: none;">
                <td>买家要求</td>
                <td>
                    <div><input type="radio" name="buyer_flag" value="1"/> 允许所有买家购买我的物品</div>
                    <div><input type="radio" name="buyer_flag" value="0"/> 不允许以下买家购买我的物品</div>
                    <div style="margin-left: 25px;">
                        <div><input type="checkbox" name="BuyerRequirementDetails.LinkedPayPalAccount" value="true"/>没有 PayPal 账户</div>
                        <div><input type="checkbox" name="BuyerRequirementDetails.ShipToRegistrationCountry" value="true"/>主要运送地址在我的运送范围之外</div>
                        <div><input type="checkbox" name="MaximumUnpaidItemStrikesInfo" onclick="checkData(this)"/>
                            曾收到<select name="BuyerRequirementDetails.MaximumUnpaidItemStrikesInfo.Count" disabled="disabled">
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>个弃标个案，在过去<select name="BuyerRequirementDetails.MaximumUnpaidItemStrikesInfo.Period" disabled="disabled">
                                <option value="Days_30">30</option>
                                <option value="Days_180">180</option>
                                <option value="Days_360">360</option>
                            </select>天
                        </div>
                        <div><input type="checkbox" name="MaximumBuyerPolicyViolations"  onclick="checkData(this)"/>
                            曾收到<select name="BuyerRequirementDetails.MaximumBuyerPolicyViolations.Count" disabled="disabled">
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                            </select>个违反政策检举，在过去<select name="BuyerRequirementDetails.MaximumBuyerPolicyViolations.Period" disabled="disabled">
                                <option value="Days_30">30</option>
                                <option value="Days_180">180</option>
                            </select>天
                        </div>
                        <div><input type="checkbox" name="MinimumFeedbackScore_flag" onclick="checkData(this)"/>信用指标等于或低于：<select name="BuyerRequirementDetails.MinimumFeedbackScore" disabled="disabled">
                            <option value="-1">-1</option>
                            <option value="-2">-2</option>
                            <option value="-3">-3</option>
                        </select></div>
                        <div><input type="checkbox" name="MaximumItemCount_flag" onclick="checkData(this)"/>在过去10天内曾出价或购买我的物品，已达到我所设定的限制 <select name="BuyerRequirementDetails.MaximumItemRequirements.MaximumItemCount" disabled="disabled">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="25">25</option>
                            <option value="50">50</option>
                            <option value="75">75</option>
                            <option value="100">100</option>
                        </select></div>
                        <div style="margin-left: 15px;"><input type="checkbox" name="FeedbackScore_falg" onclick="checkData(this)"/>这项限制只适用于买家信用指数等于或低于 <select name="BuyerRequirementDetails.MaximumItemRequirements.MinimumFeedbackScore" disabled="disabled">
                            <option value="5">5</option>
                            <option value="4">4</option>
                            <option value="3">3</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                            <option value="0">0</option>
                        </select></div>
                    </div>
                </td>
            </tr>
            <tr id="SKU" style="display: none;">
                <td>SKU</td>
                <td><input type="text" name="SKU" class="validate[required]" value="${item.SKU}"></td>
            </tr>
            <tr id="PrimaryCategory" style="display: none;">
                <td>分类</td>
                <td>
                    <input type="text" id="PrimaryCategory.categoryID" name="PrimaryCategory.CategoryId" value="${item.primaryCategory.categoryID}">
                    <a href="javascript:void(0)" onclick="selectType()">请选择</a>
                    <br/>
                    <div id="PrimaryCategoryshow">
                    </div>
                </td>
            </tr>
            <tr id="ConditionID" style="display: none;">
                <td>物品状况</td>
                <td>
                    <select name="ConditionID">
                        <option selected="selected" value="1000">New</option>
                        <option value="1500">New other (see details)</option>
                        <option value="2000">Manufacturer refurbished</option>
                        <option value="2500">Seller refurbished</option>
                        <option value="3000">Used</option>
                        <option value="7000">For parts or not working</option>
                    </select>
                </td>
            </tr>
            <tr id="Location" style="display: none;">
                <td>物品所在地</td>
                <td>
                    <table>
                        <tr>
                            <td>物品所在地:</td>
                            <td><input type="text" name="Location" value="${item.location}"></td>
                        </tr>
                        <tr>
                            <td>国家:</td>
                            <td>
                                <select name="Country">
                                    <c:forEach items="${countryList}" var="countryList">
                                        <option value="${countryList.value}">${countryList.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>邮编:</td>
                            <td><input type="text" name="PostalCode" value="${item.postalCode}"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr id="DispatchTimeMax" style="display: none;">
                <td>处理时间</td>
                <td>
                    <select name="DispatchTimeMax">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                        <option value="20">20</option>
                        <option value="30">30</option>
                    </select>
                    工作日<input type="checkbox" name="GetItFast" value="1">快速寄货
                </td>
            </tr>
            <tr id="PrivateListing" style="display: none;">
                <td>私人拍买</td>
                <td><input type="checkbox" name="PrivateListing" value="1"></td>
            </tr>
            <tr id="ListingDuration" style="display: none;">
                <td>刊登天数</td>
                <td>
                    <select name="ListingDuration">
                        <option value="GTC">GTC</option>
                        <option value="Days_1">1</option>
                        <option value="Days_3">3</option>
                        <option value="Days_5">5</option>
                        <option value="Days_7">7</option>
                        <option value="Days_10">10</option>
                    </select>
                </td>
            </tr>
            <tr id="ShippingDetails" style="display: none;">
                <td>运输选项</td>
                <td>
                    <table>
                        <tr>
                            <td colspan="2">
                                <br/>
                                国际运输
                                <hr/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table id="shippingMore">
                                </table>
                                <a href="javascript:void(0)" onclick="addShippingDetial(this)">添加</a>
                                <a href="javascript:void(0)" id="del" style="display: none;" onclick="deleteShippingDetial(this)">删除</a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <br/>
                                国际运输
                                <hr/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table id="inter">
                                </table>
                                <a href="javascript:void(0)" onclick="addShippingDetialInter(this)">添加</a>
                                <a href="javascript:void(0)" id="delinter" style="display: none;" onclick="deleteShippingDetialInter(this)">删除</a>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">不运送国家</td>
                            <td>
                                <select name="selecttype" onchange="selectLocation(this)">
                                    <option selected="selected" value="0">运输至所有国家</option>
                                    <option value="1">使用 eBay 站点设置</option>
                                    <option value="2">选择不运送地区</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <span id="notLocationName"></span>
                                <input type="hidden" name="notLocationValue" id="notLocationValue">
                                <br/>
                                <a href="javascript:void(0)" onclick="createNoLocationList()" style="display: none;" id="createNoLocationList">创建不运送地区列表</a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <input type="button" value="下一步" onclick="nextShows(this)" id="nextShow">
        <input type="button" value="上一步" onclick="previousShows(this)" id="previousShow" style="display: none;">
        <input type="button" value="关闭" onclick="closeWin()">
    </div>
</form>
</body>
<script>
    //选择不运送国家下拉列表
    function selectLocation(obj){
        if($(obj).val()=="2"){
            $("#createNoLocationList").show();
        }else{
            $("#createNoLocationList").hide();
        }
    }
    var par="";
    function createNoLocationList(){
        var api = frameElement.api, W = api.opener;
        par = $.dialog({title: '不运送地选项',
            content: 'url:/xsddWeb/locationList.do',
            icon: 'succeed',
            width:800,
            parent:api,
            lock:true,
            zIndex:2000
        });
    }
    //添加国内运输选项
    function addShippingDetial(obj) {
        $("#shippingMore").append(createTables('','','','',''));
        checkNumber();
        if($(obj).parent().parent().find("table").length>2){
            $("#del").show();
        }
    }
    //删除国内运输选项
    function deleteShippingDetial(obj){
        $(obj).parent().parent().find("table").last().remove();
        if($(obj).parent().parent().find("table").length<=2){
            $("#del").hide();
        }
    }
    //添加国际运输选项
    function addShippingDetialInter(obj){
        $("#inter").append(createInterTables('','',''));
        checkNumber();
        if($(obj).parent().parent().find("table").length>1){
            $("#delinter").show();
        }
    }
    //删除国际运输选项
    function deleteShippingDetialInter(obj){
        $(obj).parent().parent().find("table").last().remove();
        if($(obj).parent().parent().find("table").length<2){
            $("#delinter").hide();
        }
    }
    //创国内运输表
    function createTables(obj1,obj2,obj3,obj4,obj5){
        //用于国内运输选项
        var tables = "";
        tables +=' <table name="moreTable">';
        tables +=' <tr> ';
        tables +=' <td colspan="2">第一运输</td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right"  width="200">运输方式</td> ';
        tables +=' <td> ';
        tables +=' <select name="ShippingService" shortName="a1"> ';
        if(shippingService!=""){
            tables +=shippingService;
        }
        tables +=' </select> ';
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right">运费</td> ';
        tables +=' <td> ';
        tables +=' <input type="text" name="ShippingServiceCost.value" value="'+obj2+'" id="numberShippingServiceCost"> ';
        if(obj3=="true"){
            tables +=' <input type="checkbox" name="FreeShipping" value="true" checked> 免费 ';
        }else{
            tables +=' <input type="checkbox" name="FreeShipping" value="true"> 免费 ';
        }
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right">额外每件加收</td> ';
        tables +=' <td> ';
        tables +=' <input type="text" name="ShippingServiceAdditionalCost.value" value="'+obj4+'" id="numberShippingServiceAdditionalCost"> ';
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right">AK,HI,PR 额外收费</td> ';
        tables +=' <td> ';
        tables +=' <input type="text" name="ShippingSurcharge.value" value="'+obj5+'" id="numberShippingSurcharge"> ';
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' </table> ';
        return tables;
    }
    function createInterTables(obj1,obj2,obj3){
        //用于国际运输选项
        var intertable = "";
        intertable +=' <table name="interMoreTable">';
        intertable +=' <tr> ';
        intertable +=' <td colspan="2">第一运输</td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right" width="200">运输方式</td> ';
        intertable +=' <td> ';
        intertable +=' <select name="ShippingService"> ';
        intertable +=' <optgroup label="Expedited services">';
        <c:forEach var="inter1" items="${inter1}">
        if(obj1=='${inter1.value}'){
            intertable += ' <option value="${inter1.value}" selected="selected">${inter1.name}</option>';
        }else {
            intertable += ' <option value="${inter1.value}">${inter1.name}</option>';
        }
        </c:forEach>
        intertable +=' </optgroup>';
        intertable +=' <optgroup label="Other services">';
        <c:forEach var="inter2" items="${inter2}">
        if(obj1=='${inter2.value}'){
            intertable += ' <option value="${inter2.value}" selected="selected">${inter2.name}</option>';
        }else {
            intertable += ' <option value="${inter2.value}">${inter2.name}</option>';
        }
        </c:forEach>
        intertable +=' </optgroup>';
        intertable +=' </select> ';
        intertable +=' </td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right">运费</td> ';
        intertable +=' <td> ';
        intertable +=' <input type="text" name="ShippingServiceCost.value" value="'+obj2+'" id="numberShippingServiceCost2"> ';
        //intertable +=' <input type="checkbox" name="isFee"> 免费 ';
        intertable +=' </td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right">额外每件加收</td> ';
        intertable +=' <td> ';
        intertable +=' <input type="text" name="ShippingServiceAdditionalCost.value" value="'+obj3+'" id="numberShippingServiceAdditionalCost2"> ';
        intertable +=' </td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right" style="vertical-align: top;">运到</td> ';
        intertable +=' <td> ';
        intertable +=' <input type="checkbox" name="ShipToLocationInter" onclick="selectLocation(this)"/> 全球 <a href="javascript:void(0)" onclick="selectAllLocation(this)">选择以下所有国家和地区</a>';
        intertable +=' </br>';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="Asia"> 亚洲 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="AU"> 澳大利亚 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="CA"> 加拿大 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="Europe"> 欧洲 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="DE"> 德国 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="JP"> 日本 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="MX"> 墨西哥 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="Americas"> 美洲 ';
        intertable +=' <input type="checkbox" name="ShipToLocation" value="GB"> 英国 ';
        intertable +=' </td> ';
        intertable +=' </tr> ';
        intertable +=' </table> ';
        return intertable;
    }
</script>
</html>
