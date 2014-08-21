<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<c:set var="shipping" value="${tradingShippingdetails}"/>
<head>
    <title></title>
    <script>
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
            tables +=' <select name="ShippingService"> ';
            tables +=' <optgroup label="Economy services">';
            <c:forEach var="li1" items="${li1}">
                if(obj1==${li1.id}){
                    tables +=' <option value="${li1.id}" selected="selected">${li1.name}</option>';
                }else{
                    tables +=' <option value="${li1.id}">${li1.name}</option>';
                }
            </c:forEach>
            tables +=' </optgroup>';
            tables +=' <optgroup label="Expedited services">';
            <c:forEach var="li2" items="${li2}">
            if(obj1==${li2.id}){
                tables +=' <option value="${li2.id}"  selected="selected">${li2.name}</option>';
            }else{
                tables +=' <option value="${li2.id}">${li2.name}</option>';
            }
            </c:forEach>
            tables +=' </optgroup>';
            tables +=' <optgroup label="One-day services">';
            <c:forEach var="li3" items="${li3}">
            if(obj1==${li3.id}){
                tables += ' <option value="${li3.id}" selected="selected">${li3.name}</option>';
            }else {
                tables += ' <option value="${li3.id}">${li3.name}</option>';
            }
            </c:forEach>
            tables +=' </optgroup>';
            tables +=' <optgroup label="Other services">';
            <c:forEach var="li4" items="${li4}">
            if(obj1==${li4.id}) {
                tables += ' <option value="${li4.id}" selected="selected">${li4.name}</option>';
            }else{
                tables += ' <option value="${li4.id}">${li4.name}</option>';
            }
            </c:forEach>
            tables +=' </optgroup>';
            tables +=' <optgroup label="Standard services">';
            <c:forEach var="li5" items="${li5}">
            if(obj1==${li5.id}) {
                tables += ' <option value="${li5.id}" selected="selected">${li5.name}</option>';
            }else{
                tables += ' <option value="${li5.id}">${li5.name}</option>';
            }
            </c:forEach>
            tables +=' </optgroup>';
            tables +=' </select> ';
            tables +=' </td> ';
            tables +=' </tr> ';
            tables +=' <tr> ';
            tables +=' <td align="right">运费</td> ';
            tables +=' <td> ';
            tables +=' <input type="text" name="ShippingServiceCost.value" value="'+obj2+'" id="numberShippingServiceCost"> ';
            if(obj3=="1"){
                tables +=' <input type="checkbox" name="FreeShipping" value="1" checked> 免费 ';
            }else{
                tables +=' <input type="checkbox" name="FreeShipping" value="1"> 免费 ';
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
        $(document).ready(function() {
            <c:if test="${litso==null}">
                $("#shippingMore").append(createTables('','','','',''));
            </c:if>
            <c:forEach var="obj" items="${litso}">
                $("#shippingMore").append(createTables('${obj.shippingservice}','${obj.shippingservicecost}','${obj.freeshipping}','${obj.shippingserviceadditionalcost}','${obj.shippingsurcharge}'));
            </c:forEach>

            if($("#del").parent().parent().find("table").length>3){
                $("#del").show();
            }
            <c:if test="${litio!=null}">
                <c:forEach var="obj" items="${litio}">
                    var ss = $("#inter").append(createInterTables('${obj.shippingservice}','${obj.shippingservicecost}','${obj.shippingserviceadditionalcost}'));
                </c:forEach>
            </c:if>

            if($("#delinter").parent().parent().find("table").length<2){
                $("#delinter").hide();
            }
            checkNumber();
        });
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
            par = $.dialog({title: '不运送地选项',
                content: 'url:/xsddWeb/locationList.do',
                icon: 'succeed',
                width:800,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function saveData(objs) {
            var moreTable  = $("table[name='moreTable']").each(function(i,d){
                $(d).find("select,input").each(function(ii,dd){
                    var name_= $(dd).prop("name");
                    var t="ShippingServiceOptions["+i+"].";
                    $(dd).prop("name",t+name_);
                });
            });

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
            var data = $('#form').serialize();
            var urll = "/xsddWeb/saveShippingDetails.do";
            $(objs).attr("disabled",true);
            var api = frameElement.api, W = api.opener;
            $().invoke(
                    urll,
                    data,
                    [function (m, r) {
                        Base.token();
                        alert(r);
                        $(objs).attr("disabled",false);
                        W.refreshTable();
                        W.returnShipping.close();
                    },
                        function (m, r) {
                            Base.token();
                            $(objs).attr("disabled",false);
                        }]
            )
        }
        function selectShippingType(obj){
            if($("#interCountType").val()=="1"||$("#countType").val()=="1"){
                $("#countTitle").show();
                $("#countMessage").show();
            }else{
                $("#countTitle").hide();
                $("#countMessage").hide();
            }
        }
    </script>
</head>
<body>
<form id="form">
    <table width="100%">
        <tr>
            <td colspan="2">
                运输选项
                <hr/>
            </td>
        </tr>
        <tr>
            <td align="right" width="200">名称</td>
            <td>
                <input type="hidden" name="id" id="id" value="${shipping.id}">
                <input type="text" name="shippingName" id="shippingName" value="${shipping.shippingName}"></td>
        </tr>
        <tr>
            <td align="right">ebay账号</td>
            <td >
                <select name="ebayAccount">
                    <c:forEach items="${ebayList}" var="ebay">
                        <c:if test="${shipping.ebayAccount==ebay.id}">
                            <option value="${ebay.id}" selected="selected">${ebay.configName}</option>
                        </c:if>
                        <c:if test="${shipping.ebayAccount!=ebay.id}">
                            <option value="${ebay.id}">${ebay.configName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right">站点</td>
            <td>
                <select name="site">

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
            <select name="countType" id="countType" onchange="(selectShippingTypethis)">
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
            <td colspan="2">
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
                            深度<input type="text" size="8" name="PackageDepth" id="PackageDepth">inch
                            长度<input type="text" size="8" name="PackageLength" id="PackageLength">inch
                            宽度<input type="text" size="8" name="PackageWidth" id="PackageWidth">inch
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            估量重量
                        </td>
                        <td>
                            <input type="text" size="8" name="WeightMajor" id="WeightMajor">lbs
                            <input type="text" size="8" name="WeightMinor" id="WeightMinor">oz
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
            <td colspan="2">
                <br/>
                国内运输
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <table>
                    <tr>
                        <td align="right">处理时间</td>
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
                </table>
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
                <select name="selecttype" onchange="selectType(this)">
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
    <div>
        <input type="button" value="确定" onclick="saveData(this)">
    </div>
</form>
</body>
</html>
