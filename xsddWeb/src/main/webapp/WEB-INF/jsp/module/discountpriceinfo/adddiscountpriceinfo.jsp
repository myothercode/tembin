<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<c:set value="${dis}" var="dis" />
<head>
    <title></title>
    <script>
        $(document).ready(function() {
            $("#selectflag").bind("click",function(){
                if($(this).prop("checked")){
                    $("input[name='a1']").attr("disabled",false);
                }else{
                    $("input[name='a1']").attr("disabled",true);
                }
            });
            $("input[name='a1']").bind("click",function(){
                if($(this).val()=="a1"){
                    $("#MadeForOutletComparisonPrice").attr("disabled",false);
                    $("#MinimumAdvertisedPrice").attr("disabled",true);
                }else{
                    $("#MadeForOutletComparisonPrice").attr("disabled",true);
                    $("#MinimumAdvertisedPrice").attr("disabled",false);
                }
            });

            if(${dis.madeforoutletcomparisonprice!=""||dis.minimumadvertisedprice!=""}){
                $("#selectflag").attr("checked",true);

                var madeforoutletcomparisonprice = "${dis.madeforoutletcomparisonprice}";
                var minimumadvertisedprice = "${dis.minimumadvertisedprice}";

                if(madeforoutletcomparisonprice!=""&&madeforoutletcomparisonprice!=null){
                    $("input[name='a1'][value='a1']").prop("checked",true);
                    $("input[name='a1']").attr("disabled",false);
                    $("#MadeForOutletComparisonPrice").attr("disabled",false);
                }
                if(minimumadvertisedprice!="" && minimumadvertisedprice!=null){
                    $("input[name='a1'][value='a2']").prop("checked",true);
                    $("input[name='a1']").attr("disabled",false);
                    $("#MinimumAdvertisedPrice").attr("disabled",false);
                }
            }

            if(${dis.isShippingfee!=""}){
                $("#isShippingFee").attr("checked",true);
            }
            $("#discountPriceInfoForm").validationEngine();
        });
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            if(!$("#discountPriceInfoForm").validationEngine("validate")){
                return;
            }
            var url=path+"/ajax/saveDiscountPriceInfo.do";
            var data=$("#discountPriceInfoForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token();
                        W.refreshTable();
                        W.discountPriceInfo.close();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
    </script>
</head>
<body>

<form id="discountPriceInfoForm">
    <table>
        <tr>
            <td>名称</td>
            <td>
                <input type="hidden" name="id" id="id" value="${dis.id}">
                <input type="text" name="name" id="name" value="${dis.name}" class="validate[required]"/></td>
        </tr>
        <tr>
            <td>eBay 账户</td>
            <td>
                <select name="ebayAccount">
                    <c:forEach var="li" items="${userli}">
                        <c:if test="${li.id==dis.ebayAccount}">
                            <option value="${li.id}" selected="selected">${li.configName}</option>
                        </c:if>
                        <c:if test="${li.id!=dis.ebayAccount}">
                            <option value="${li.id}">${li.configName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>开始时间</td>
            <td><input name="disStarttime"  class="validate[required]" id="disStarttime" value="<fmt:formatDate value="${dis.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm'})"/></td>
        </tr>
        <tr>
            <td>结束时间</td>
            <td><input name="disEndtime"  class="validate[required]" id="disEndtime" value="<fmt:formatDate value="${dis.disEndtime}" pattern="yyyy-MM-dd HH:mm"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm'})"/></td>
        </tr>
        <tr>
            <td>
                优惠明细
            </td>
            <td>
                <table>
                    <tr>
                        <td><input type="checkbox" id="selectflag">价格折扣（不包括拍卖物品）</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="a1" disabled="disabled" value="a1">在原价上给予折扣<input type="text" id="MadeForOutletComparisonPrice"  class="validate[custom[number]]"  name="MadeForOutletComparisonPrice" value="${dis.madeforoutletcomparisonprice}" disabled="disabled" size="10">%</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="a1"  disabled="disabled" value="a2">在原价上降价<input type="text" id="MinimumAdvertisedPrice" class="validate[custom[number]]" name="MinimumAdvertisedPrice" value="${dis.minimumadvertisedprice}" disabled="disabled" size="10"></td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" id="isShippingFee" name="isShippingFee" value="1">免运费（用于第一运输方法）</td>
                    </tr>
                </table>
            </td>
        </tr>
        <div>
            <input type="button" value="保存" onclick="submitCommit();"/>
        </div>
    </table>
</form>
</body>
</html>
