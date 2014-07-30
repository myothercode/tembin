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
<head>
    <title></title>
    <script>
        //用于国内运输选项
        var tables = "";
        tables +=' <table>';
        tables +=' <tr> ';
        tables +=' <td colspan="2">第一运输</td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right"  width="200">运输方式</td> ';
        tables +=' <td> ';
        tables +=' <select name="ShippingService"> ';
        tables +=' </select> ';
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right">运费</td> ';
        tables +=' <td> ';
        tables +=' <input type="text" name="ShippingServiceCost" value=""> ';
        tables +=' <input type="checkbox" name="isFee"> 免费 ';
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right">额外每件加收</td> ';
        tables +=' <td> ';
        tables +=' <input type="text" name="ShippingServiceAdditionalCost" value=""> ';
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' <tr> ';
        tables +=' <td align="right">AK,HI,PR 额外收费</td> ';
        tables +=' <td> ';
        tables +=' <input type="text" name="ShippingSurcharge" value=""> ';
        tables +=' </td> ';
        tables +=' </tr> ';
        tables +=' </table> ';
        //用于国际运输选项
        var intertable = "";
        intertable +=' <table>';
        intertable +=' <tr> ';
        intertable +=' <td colspan="2">第一运输</td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right" width="200">运输方式</td> ';
        intertable +=' <td> ';
        intertable +=' <select name="ShippingServiceInter"> ';
        intertable +=' </select> ';
        intertable +=' </td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right">运费</td> ';
        intertable +=' <td> ';
        intertable +=' <input type="text" name="ShippingServiceCostInter" value=""> ';
        intertable +=' <input type="checkbox" name="isFee"> 免费 ';
        intertable +=' </td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right">额外每件加收</td> ';
        intertable +=' <td> ';
        intertable +=' <input type="text" name="ShippingServiceAdditionalCostInter" value=""> ';
        intertable +=' </td> ';
        intertable +=' </tr> ';
        intertable +=' <tr> ';
        intertable +=' <td align="right" style="vertical-align: top;">运到</td> ';
        intertable +=' <td> ';
        intertable +=' <input type="checkbox" name="ShipToLocationInter" onclick="selectLocation(this)"/> 全球 <a href="javascript:void(0)" onclick="selectAllLocation(this)">选择以下所有国家和地区</a>';
        intertable +='</br> <input type="checkbox" name="ShipToLocation" value="Asia"> 亚洲 ';
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


        $(document).ready(function() {
            $("#shippingMore").append(tables);
        });
        //添加国内运输选项
        function addShippingDetial(obj) {
            $("#shippingMore").append(tables);
            if($(obj).parent().parent().find("table").length>3){
                $("#del").show();
            }
        }
        //删除国内运输选项
        function deleteShippingDetial(obj){
            $(obj).parent().parent().find("table").last().remove();
            if($(obj).parent().parent().find("table").length<=3){
                $("#del").hide();
            }
        }
        //添加国际运输选项
        function addShippingDetialInter(obj){
            $("#inter").append(intertable);
            if($(obj).parent().parent().find("table").length>1){
                $("#delinter").show();
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
        function createNoLocationList(){
            var api = frameElement.api, W = api.opener;
            $.dialog({title: '不运送地选项',
                content: 'url:/xsddWeb/locationList.do',
                icon: 'succeed',
                width:800,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
    </script>
</head>
<c:set value="${shipping}" var="shipping"/>
<body>
<form action="/xsddWeb/saveshippingDetails.do">
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
                <input type="text" name="name" id="name" value="${shipping.payName}"></td>
        </tr>
        <tr>
            <td align="right">ebay账号</td>
            <td >
                <select name="ebayAccount">
                    <c:forEach items="${ebayList}" var="ebay">
                        <c:if test="${shipping.paypal==ebay.id}">
                            <option value="${ebay.id}" selected="selected">${ebay.configName}</option>
                        </c:if>
                        <c:if test="${shipping.paypal!=ebay.id}">
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
            <select name="sippingtype">
                <option value="0">标准</option>
                <option value="1">计算</option>
            </select>
        </td>
        </tr>

        <tr>
            <td align="right">国际运输类型</td>
            <td>
                <select name="intershippingtype">
                    <option value="0">标准</option>
                    <option value="1">计算</option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right"><input type="checkbox" name="ifDescribe" value="1"></td>
            <td>
                运输条款已在刊登描述中
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
                            <select>
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
                            工作日<input type="checkbox" name="isf">快速寄货
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
                <a href="javascript:void(0)" onclick="createNoLocationList()" style="display: none;" id="createNoLocationList">创建不运送地区列表</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
