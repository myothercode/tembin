<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.WEB-INF/jsp/commonImport.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>--%>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script>
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            if(!$("#itemAddressForm").validationEngine("validate")){
                return;
            }
            var url=path+"/ajax/saveItemAddress.do";
            var data=$("#itemAddressForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTableAddress();
                        W.itemAddressList.close();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function () {
            $("#itemAddressForm").validationEngine();
            var type = '${type}';
            if(type=="01"){
                $("input").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("select").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("textarea").attr("disabled",true);
                $("button").hide();
            }
        });
    </script>
</head>
<c:set value="${itemAddress}" var="item" />
<body>
<div style="width: 400px;">
    <form class="form-horizontal" id="itemAddressForm">
        <fieldset>
            <div id="legend" class="">
                <legend class="">物品所在地</legend>
            </div>
            <div class="control-group">
                <!-- Text input-->
                <label class="control-label" for="input01">名称</label>
                <div class="controls">
                    <input type="text" name="name" id="name" value="${item.name}" placeholder="" class="input-xlarge validate[required]">
                    <p class="help-block"></p>
                    <input value="${item.id}" name="id" id="id" type="hidden">
                </div>
            </div>

            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">物品所在地</label>
                <div class="controls">
                    <input type="text" placeholder="" name="address" id="address" value="${item.address}" class="input-xlarge validate[required]">
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="control-group">

                <!-- Select Basic -->
                <label class="control-label">国家</label>
                <div class="controls">
                    <select name="countryList" class="input-xlarge">
                        <c:forEach items="${countryList}" var="countryList">
                            <c:if test="${item.countryId==countryList.id}">
                                <option value="${countryList.id}" selected="selected">${countryList.name}</option>
                            </c:if>
                            <c:if test="${item.countryId!=countryList.id}">
                                <option value="${countryList.id}">${countryList.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

            </div>

            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">邮编</label>
                <div class="controls">
                    <input type="text" name="postalCode" id="postalCode" value="${item.postalcode}" placeholder="" class="input-xlarge validate[required]">
                    <p class="help-block"></p>
                </div>
            </div>

        </fieldset>
    </form>

    <div  class="control-group" style="text-align: center;">
        <div class="controls">
            <button class="btn btn-success" onclick="submitCommit();">确定</button>
        </div>
    </div>
</div>
</body>
</html>
