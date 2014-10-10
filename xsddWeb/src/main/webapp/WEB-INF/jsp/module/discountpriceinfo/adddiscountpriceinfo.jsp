<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>--%>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
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

            var type = '${type}';
            if(type=="01"){
                $("input").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("select").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("textarea").attr("disabled",true);
                $("button").attr("disabled",true);
            }
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
                        W.refreshTable();
                        W.discountPriceInfo.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
    </script>
</head>
<body>
<div style="width: 400px;">
    <form class="form-horizontal"  id="discountPriceInfoForm">
        <fieldset>
            <div id="legend" class="">
                <legend class="">折扣信息</legend>
            </div>
            <div class="control-group">
                <!-- Text input-->
                <label class="control-label" for="input01">名称</label>
                <div class="controls">
                    <input type="text" name="name" id="name" value="${dis.name}"   placeholder="" class="input-xlarge validate[required]">
                    <p class="help-block"></p>
                    <input type="hidden" name="id" id="id" value="${dis.id}">
                </div>
            </div>
            <div class="control-group">
                <!-- Select Basic -->
                <label class="control-label">ebay账户</label>
                <div class="controls">
                    <select name="ebayAccount"  class="input-xlarge validate[required]">
                        <c:forEach var="li" items="${userli}">
                            <c:if test="${li.id==dis.ebayAccount}">
                                <option value="${li.id}" selected="selected">${li.ebayName}</option>
                            </c:if>
                            <c:if test="${li.id!=dis.ebayAccount}">
                                <option value="${li.id}">${li.ebayName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <!-- Select Basic -->
                <label class="control-label">开始时间</label>
                <div class="controls">
                    <input name="disStarttime"  class="validate[required]" id="disStarttime" value="<fmt:formatDate value="${dis.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm'})"/>
                </div>
            </div>
            <div class="control-group">
                <!-- Textarea -->
                <label class="control-label">结束时间</label>
                <div class="controls">
                    <input name="disEndtime"  class="validate[required]" id="disEndtime" value="<fmt:formatDate value="${dis.disEndtime}" pattern="yyyy-MM-dd HH:mm"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm'})"/>
                </div>
            </div>
            <div class="control-group">
                <!-- Textarea -->
                <label class="control-label">优惠明细</label>
                <div class="controls">
                    <input type="checkbox" id="selectflag">价格折扣（不包括拍卖物品）
                </div>
                <div class="controls">
                    <input type="radio" name="a1" value="a1" checked>在原价上给予折扣<input type="text" id="MadeForOutletComparisonPrice"  class="validate[custom[number]]" style="width: 40px;" name="MadeForOutletComparisonPrice" value="${dis.madeforoutletcomparisonprice}" size="10">%
                </div>
                <div class="controls">
                    <input type="radio" name="a1"  value="a2">在原价上降价<input type="text" id="MinimumAdvertisedPrice" class="validate[custom[number]]"  style="width: 40px;" name="MinimumAdvertisedPrice" value="${dis.minimumadvertisedprice}" disabled="disabled" size="10">
                </div>
                <div class="controls">
                    <input type="checkbox" id="isShippingFee" name="isShippingFee" value="1">免运费（用于第一运输方法）
                </div>
            </div>
        </fieldset>
    </form>
    <div class="control-group" style="text-align: center;">
        <div class="controls">
            <button class="btn btn-success" onclick="submitCommit()">确定</button>
        </div>
    </div>
</div>

</body>
</html>
