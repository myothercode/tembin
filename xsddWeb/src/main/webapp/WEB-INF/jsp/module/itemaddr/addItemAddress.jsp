<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.WEB-INF/jsp/commonImport.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
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
                        Base.token();
                        W.refreshTable();
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
            }
        });
    </script>
</head>
<c:set value="${itemAddress}" var="item" />
<body>
<form id="itemAddressForm">
    <table>
        <tr>
            <td>名称:</td>
            <td>
                <input value="${item.id}" name="id" id="id" type="hidden">
                <input type="text" name="name" id="name" value="${item.name}" class="validate[required]"></td>
        </tr>
        <tr>
            <td>物品所在地:</td>
            <td><input type="text" name="address" id="address" value="${item.address}"  class="validate[required]"></td>
        </tr>
        <tr>
            <td>国家:</td>
            <td>
                <select name="countryList">
                    <c:forEach items="${countryList}" var="countryList">
                        <c:if test="${item.countryId==countryList.id}">
                            <option value="${countryList.id}" selected="selected">${countryList.name}</option>
                        </c:if>
                        <c:if test="${item.countryId!=countryList.id}">
                            <option value="${countryList.id}">${countryList.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>邮编:</td>
            <td><input type="text" name="postalCode" id="postalCode" value="${item.postalcode}"  class="validate[required]"></td>
        </tr>
    </table>
    <div>
        <div><input type="button" value="确定" onclick="submitCommit();"/></div>
    </div>
</form>
</body>
</html>
