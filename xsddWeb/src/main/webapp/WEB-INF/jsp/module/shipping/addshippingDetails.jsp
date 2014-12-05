<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>--%>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<c:set var="shipping" value="${tradingShippingdetails}"/>
<head>
    <title></title>
    <!-- bootstrap -->
    <link href=
          <c:url value="/js/selectBoxIt/stylesheets/jquery.selectBoxIt.css"/> rel="stylesheet">

    <%--<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" />--%>
    <%--<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />--%>
    <%--<link type="text/css" rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />--%>
    <%--<link rel="stylesheet" href="http://gregfranko.com/jquery.selectBoxIt.js/css/jquery.selectBoxIt.css" />--%>
    <script type="text/javascript" src=
            <c:url value="/js/selectBoxIt/javascripts/jquery-ui.min.js"/>></script>
    <%--<script src="http://gregfranko.com/jquery.selectBoxIt.js/js/jquery.selectBoxIt.min.js"></script>--%>
    <script type="text/javascript" src=
            <c:url value="/js/selectBoxIt/javascripts/jquery.selectBoxIt.min.js"/>></script>

    <script>
        var shippingService = "";
        //国际运输选择所有地区
        function selectAllLocation(obj){
            $(obj).parent().parent().find("input[name='ShipToLocation']").prop("checked",true);
        }
        /**
        *选择全球时，取消已选择的地区
        * @param obj
         */
        function selectLocation(obj){
            if($(obj).prop("checked")){
                $(obj).parent().parent().find("input[name='ShipToLocation']").prop("checked",false);
                $(obj).parent().parent().find("input[name='ShipToLocation']").attr("disabled",true);
            }else{
                $(obj).parent().parent().find("input[name='ShipToLocation']").attr("disabled",false);
            }
        }

        function checkNumber(){
            $("input[id^='number']").bind({
                keypress:function(){return inputNUMAndPoint(event,this,2);} ,
                blur:function(){
                    if($(this).val()=='' || !isNaN($(this).val())){}else{
                        $(this).val(0);
                        alert('请输入数字!');
                        $(this).focus();
                    }
                    return false;
                }
            });
        }

        function createInterTables(obj1,obj2,obj3){
            //用于国际运输选项
            var count =getCount('interMoreTable');
            var intertable = "";
            intertable +=' <table name="interMoreTable">';
            intertable +=' <tr> ';
            intertable +=' <td colspan="2" style="padding-left: 100px;">第'+count+'运输</td> ';
            intertable +=' </tr> ';
            intertable +=' <tr> ';
            intertable +=' <td align="right" width="200">运输方式</td> ';
            intertable +=' <td> ';
            intertable +=' <select name="ShippingService"> ';
            intertable +=' <optgroup label="Expedited services">';
            <c:forEach var="inter1" items="${inter1}">
            if(obj1==${inter1.id}){
                intertable += ' <option value="${inter1.id}" selected="selected">${inter1.name}</option>';
            }else {
                intertable += ' <option value="${inter1.id}">${inter1.name}</option>';
            }
            </c:forEach>
            intertable +=' </optgroup>';
            intertable +=' <optgroup label="Other services">';
            <c:forEach var="inter2" items="${inter2}">
            if(obj1==${inter2.id}){
                intertable += ' <option value="${inter2.id}" selected="selected">${inter2.name}</option>';
            }else {
                intertable += ' <option value="${inter2.id}">${inter2.name}</option>';
            }
            </c:forEach>
            intertable +=' </optgroup>';
            intertable +=' </select> ';
            intertable +=' </td> ';
            intertable +=' </tr> ';
            intertable +=' <tr> ';
            intertable +=' <td align="right">运费</td> ';
            intertable +=' <td> ';
            intertable +=' <input type="text" name="ShippingServiceCost.value" class="validate[required,custom[number]]" value="'+obj2+'" id="numberShippingServiceCost2"> ';
            //intertable +=' <input type="checkbox" name="isFee"> 免费 ';
            intertable += ' <input type="checkbox" name="interFreeShipping" value="1" onclick="shippingfee(this)"> 免费 ';
            intertable +=' </td> ';
            intertable +=' </tr> ';
            intertable +=' <tr> ';
            intertable +=' <td align="right">额外每件加收</td> ';
            intertable +=' <td> ';
            intertable +=' <input type="text" name="ShippingServiceAdditionalCost.value" class="validate[required,custom[number]]" value="'+obj3+'" id="numberShippingServiceAdditionalCost2"> ';
            intertable +=' </td> ';
            intertable +=' </tr> ';
            intertable +=' <tr> ';
            intertable +=' <td align="right" style="vertical-align: top;">运到</td> ';
            intertable +=' <td> ';
            intertable +=' <input type="checkbox" name="ShipToLocationInter" value="Worldwide" onclick="selectLocation(this)"/> 全球 <a href="javascript:void(0)" onclick="selectAllLocation(this)">选择以下所有国家和地区</a>';
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

        function getCount(name){
            var count = $("table[name='"+name+"']").length;
            var str = '';
            count=count+1;
            if(count==1){
                str='一';
            }else if(count==2){
                str='二'
            } else if(count==3){
                str='三'
            } else if(count==4){
                str='四'
            } else if(count==5){
                str='五'
            }else if(count==6){
                str='六'
            }else if(count==7){
                str='七'
            }
            return str;
        }
        //创国内运输表
        function createTables(obj1,obj2,obj3,obj4,obj5){
            //用于国内运输选项
            var count =getCount('moreTable');

            var tables = "";
            tables +=' <table name="moreTable">';
            tables +=' <tr> ';
            tables +=' <td colspan="2" style="padding-left: 100px;">第'+count+'运输</td> ';
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
            if($("#countType").find("option[value='1']:selected").val()=="1"){
                //当选择计算国内运输选项时
            }else {
                tables += ' <tr> ';
                tables += ' <td align="right">运费</td> ';
                tables += ' <td> ';
                tables += ' <input type="text" name="ShippingServiceCost.value"  class="validate[required,custom[number]]" value="' + obj2 + '" id="numberShippingServiceCost"> ';
                if (obj3 == "1") {
                    tables += ' <input type="checkbox" name="FreeShipping" value="1" checked onclick="shippingfee(this)"> 免费 ';
                } else {
                    tables += ' <input type="checkbox" name="FreeShipping" value="1" onclick="shippingfee(this)"> 免费 ';
                }
                tables += ' </td> ';
                tables += ' </tr> ';
                tables += ' <tr> ';
                tables += ' <td align="right">额外每件加收</td> ';
                tables += ' <td> ';
                tables += ' <input type="text" name="ShippingServiceAdditionalCost.value" class="validate[required,custom[number]]" value="' + obj4 + '" id="numberShippingServiceAdditionalCost"> ';
                tables += ' </td> ';
                tables += ' </tr> ';
                tables += ' <tr> ';
                tables += ' <td align="right">AK,HI,PR 额外收费</td> ';
                tables += ' <td> ';
                tables += ' <input type="text" name="ShippingSurcharge.value" class="validate[required,custom[number]]" value="' + obj5 + '" id="numberShippingSurcharge"> ';
                tables += ' </td> ';
                tables += ' </tr> ';
            }
            tables +=' </table> ';
            return tables;
        }

        function shippingfee(obj){
            if($(obj).prop("checked")){
                $(obj).parent().parent().parent().find("input[type='text']").val("0");
            }else{
                $(obj).parent().parent().parent().find("input[type='text']").val("");
            }
        }
        $(document).ready(function() {
            var site = '${shipping.site}';
            <c:if test="${litso==null}">
                $("#shippingMore").append(createTables('','','','',''));
            </c:if>
            <c:if test="${litso!=null}">
                <c:forEach var="obj" items="${litso}">
                    var ss = $("#shippingMore").append(createTables('${obj.shippingservice}','${obj.shippingservicecost}','${obj.freeshipping}','${obj.shippingserviceadditionalcost}','${obj.shippingsurcharge}'));
                </c:forEach>
                selectSite(site);
            </c:if>

            if($("#del").parent().parent().find("table").length>3){
                $("#del").show();
            }
            <c:if test="${litio!=null}">
                <c:forEach var="obj" items="${litio}">
                    var toloList = '${obj.id}';
                    $("#inter").append(createInterTables('${obj.shippingservice}','${obj.shippingservicecost}','${obj.shippingserviceadditionalcost}'));
                    <c:forEach var="tolo" items="${tololi}" varStatus="lo">
                        var inde = '${lo.index}';
                        <c:forEach var="tolos" items="${tolo}">
                            var vals= '${tolos.value}';
                            $("#inter").find("table").eq(inde).find("input[type='checkbox'][name='ShipToLocation'][value='"+vals+"']").attr("checked",true);
                            $("#inter").find("table").eq(inde).find("input[type='checkbox'][name='ShipToLocationInter'][value='"+vals+"']").attr("checked",true);
                        </c:forEach>
                    </c:forEach>
                </c:forEach>
            </c:if>

            if($("#delinter").parent().parent().find("table").length<2){
                $("#delinter").hide();
            }
            var extlo="";
            <c:if test="${tamstr!=null}">
                extlo+='${tamstr}';
                $("select[name='selecttype']").find("option[value='2']").attr("selected",true);
                $("#createNoLocationList").show();
                $("#createNoLocationList").text("选择不运输地列表");
            </c:if>
            $("#notLocationName").text(extlo);
            checkNumber();

            $("#form").validationEngine();
            setTimeout(function(){
                <c:forEach var="obj" items="${litso}" varStatus="ind">
                    $("select[name='ShippingService'][shortName='a1']").each(function(i,d){
                        if('${ind.index}'==i){
                            $(d).find("option[value='${obj.shippingservice}']").attr("selected",true);
                        }
                    });
                </c:forEach>
            },500);
            var dispatchtimemax='${tradingShippingdetails.dispatchtimemax}'
            var getitfast='${tradingShippingdetails.getitfast}'
            $("select[name='dispatchtimemax']").find("option[value='"+dispatchtimemax+"']").attr("selected",true);
            if(getitfast!=null&&getitfast!=""){
                $("input[type='checkbox'][name='getitfast']").attr("checked",true);
            }
            var type = '${type}';
            if(type=="01"){
                $("input[type='text']").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("input[type='button']").each(function(i,d){
                    $(d).hide();
                });
                $("select").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("textarea").attr("disabled",true);
                $(".diagle-button-div").hide();
                converDiv_();
            }

        });
        //添加国内运输选项
        function addShippingDetial(obj) {
            var ss = $("#shippingMore").append(createTables('','','','',''));
            $(obj).parent().parent().find("table").last().find("select[name='ShippingService']").html(shippingService);
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
        //
        function selectType(obj){
            if($(obj).val()=="2"){
                $("#createNoLocationList").show();
            }else{
                $("#createNoLocationList").hide();
            }
        }
        //
        var par="";
        function createNoLocationList(){
            var api = frameElement.api, W = api.opener;
            var sdid = '${tradingShippingdetails.id}';
            var sdname = '${tamstr}';
            par = openMyDialog({title: '不运送地选项',
                content: 'url:/xsddWeb/locationList.do?parentId='+sdid+"&parentName="+sdname,
                icon: 'succeed',
                width:1000,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        var api = frameElement.api, W = api.opener;
        function saveData(objs) {
            var moreTable  = $("table[name='moreTable']").each(function(i,d){
                $(d).find("select,input").each(function(ii,dd){
                    var name_= $(dd).prop("name");
                    var t="ShippingServiceOptions["+i+"].";
                    $(dd).prop("name",t+name_);
                });
            });
            $("input[type='checkbox'][name='ShipToLocationInter']").attr("name","ShipToLocation");
             var interMoreTables = $("table[name='interMoreTable']").each(function(i,d){
                 $(d).find("select,input[type='text']").each(function(ii,dd){
                     var name_= $(dd).prop("name");
                     var t="InternationalShippingServiceOption["+i+"].";
                     $(dd).prop("name",t+name_);
                 });
                 $(d).find("input[type='checkbox'][name='ShipToLocation']:checked").each(function(ii,dd){
                     var name_= $(dd).prop("name");
                     var t="InternationalShippingServiceOption["+i+"].ShipToLocation["+ii+"]";
                     $(dd).prop("name",t);
                 });
             });

            if(!$("#form").validationEngine("validate")){
                return;
            }
            var data = $('#form').serialize();
            var urll = "/xsddWeb/saveShippingDetails.do";
            $(objs).attr("disabled",true);

            $().invoke(
                    urll,
                    data,
                    [function (m, r) {
                        alert(r);
                        $(objs).attr("disabled",false);
                        W.returnShipping.close();
                        W.refreshTableShipping();
                    },
                        function (m, r) {
                            $(objs).attr("disabled",false);
                        }]
            )
        }
        function selectShippingType(obj){
            if($("#interCountType").val()=="1"||$("#countType").val()=="1"){
                $("#countTitle").show();
                $("#countMessage").show();
                $("#shippingMore").html("");
                $("#shippingMore").append(createTables('','','','',''));
            }else{
                $("#countTitle").hide();
                $("#countMessage").hide();
            }
        }
    </script>
    <link href=
          <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
    <style>
        .form-control{
            height:26px;
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
    </style>
</head>
<body>
<br/>
<form id="form">
    <table width="100%">
        <%--<tr>
            <td colspan="2" style="padding-left: 50px;">
                运输选项
                <hr/>
            </td>
        </tr>--%>
        <tr>
            <td align="right" width="200">名称</td>
            <td>
                <input type="hidden" name="id" id="id" value="${shipping.id}">
                <input type="text" name="shippingName" id="shippingName" class="validate[required]"  class="form-control" value="${shipping.shippingName}"></td>
        </tr>
        <tr>
            <td align="right">ebay账号</td>
            <td >
                <select name="ebayAccount">
                    <c:forEach items="${ebayList}" var="ebay">
                        <c:if test="${shipping.ebayAccount==ebay.id}">
                            <option value="${ebay.id}" selected="selected">${ebay.ebayName}</option>
                        </c:if>
                        <c:if test="${shipping.ebayAccount!=ebay.id}">
                            <option value="${ebay.id}">${ebay.ebayName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right">站点</td>
            <td>
                <select name="site" onchange="selectSite(this)">
                    <c:forEach items="${siteList}" var="sites">
                        <c:if test="${shipping.site==sites.id}">
                            <option value="${sites.id}" selected="selected">${sites.name}</option>
                        </c:if>
                        <c:if test="${shipping.site!=sites.id}">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>

        </tr>
        <td align="right">国内运输类型</td>
        <td>
            <select name="countType" id="countType" onchange="selectShippingType(this)">
                <option value="0" <c:if test="${shipping.countType=='0'}">selected="selected" </c:if>>标准</option>
                <option value="1" <c:if test="${shipping.countType=='1'}">selected="selected" </c:if>>计算</option>
            </select>
        </td>
        </tr>

        <tr>
            <td align="right">国际运输类型</td>
            <td>
                <select name="interCountType" id="interCountType"  onchange="selectShippingType(this)">
                    <option value="0" <c:if test="${shipping.interCountType=='0'}">selected="selected" </c:if>>标准</option>
                    <option value="1" <c:if test="${shipping.interCountType=='1'}">selected="selected" </c:if>>计算</option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right"><input type="checkbox" name="ifDescribe" value="1"  <c:if test="${shipping.ifDescribe=='1'}">checked </c:if>></td>
            <td>
                运输条款已在刊登描述中
            </td>
        </tr>
        <tr id="countTitle" style="display: none;">
            <td colspan="2" style="padding-left: 50px;">
                <br/>
                计算
                <hr/>
            </td>
        </tr>
        <tr id="countMessage" style="display: none;">
            <td colspan="2">
                <table>
                    <tr>
                        <td width="200" align="right">邮编</td>
                        <td><input type="text" name="OriginatingPostalCode" id="OriginatingPostalCode"></td>
                    </tr>
                    <tr>
                        <td align="right">测量单位</td>
                        <td>
                            <select name="MeasurementUnit">
                                <option value="0">English</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            尺寸规格
                        </td>
                        <td>
                            深度<input type="text" size="2" name="PackageDepth"  class="validate[custom[number]]" id="PackageDepth">inch
                            长度<input type="text" size="2" name="PackageLength"  class="validate[custom[number]]" id="PackageLength">inch
                            宽度<input type="text" size="2" name="PackageWidth"  class="validate[custom[number]]" id="PackageWidth">inch
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            估量重量
                        </td>
                        <td>
                            <input type="text" size="8" name="WeightMajor"  class="validate[custom[number]]" id="WeightMajor">lbs
                            <input type="text" size="8" name="WeightMinor"  class="validate[custom[number]]" id="WeightMinor">oz
                        </td>
                    </tr>
                    <tr>
                        <td align="right">包裹大小</td>
                        <td>
                            <select name="ShippingPackage">
                                <option selected="selected" value="">-- 选择 --</option>
                                <c:forEach items="${lipackage}" var="lipackage">
                                    <option value="${lipackage.value}">${lipackage.name}</option>
                                </c:forEach>
                            </select>
                            <input type="checkbox" name="ShippingIrregular" id="ShippingIrregular"> 不规则形状
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="padding-left: 50px;">
                <br/>
                国内运输
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="padding-left: 70px;">
                <table style="margin-left: 100px;">
                    <tr>
                        <td align="right">处理时间</td>
                        <td>
                            <select name="dispatchtimemax" style="width: 50px;">
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
                            工作日<input type="checkbox" name="getitfast" value="1">快速寄货
                        </td>
                    </tr>
                </table>
                <table id="shippingMore">

                </table>
                <a href="javascript:void(0)" onclick="addShippingDetial(this)">添加</a>
                <a href="javascript:void(0)" id="del" style="display: none;" onclick="deleteShippingDetial(this)">删除</a>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="padding-left: 50px;">
                <br/>
                国际运输
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="padding-left: 70px;">
                <table id="inter">
                </table>
                <a href="javascript:void(0)" onclick="addShippingDetialInter(this)">添加</a>
                <a href="javascript:void(0)" id="delinter" style="display: none;" onclick="deleteShippingDetialInter(this)">删除</a>
            </td>
        </tr>
        <tr>
            <td align="right">不运送国家</td>
            <td>
                <select name="selecttype" onchange="selectType(this)">
                    <option selected="selected" value="0">运输至所有国家</option>
                    <option value="1">使用 eBay 站点设置</option>
                    <option value="2">选择不运送地区</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="padding-left: 70px;">
                <span id="notLocationName" style="line-height: 32px;"></span>
                <input type="hidden" name="notLocationValue" id="notLocationValue" value="${tamvaluestr}">
                <br/>
                <a href="javascript:void(0)" onclick="createNoLocationList()" style="display: none;" id="createNoLocationList">创建不运送地区列表</a>
            </td>
        </tr>
    </table>
    <div class="diagle-button-div">
 <%--       <div style="margin-right: 5px;">
            <input type="button" value="确定" onclick="saveData(this)">
        </div>--%>
        <div >
            <a class="myjqueryuibutton" href="javascript:void(0)" onclick="saveData(this)">确定</a>
        </div>
    </div>
    <br>
<br>
</form>
</body>
</html>
