<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.WEB-INF/jsp/commonImport.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<%--<%@include file="/WEB-INF/jsp/smallFormImport.jsp" %>--%>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
        .selectboxit-container .selectboxit{
            width: 300px;
        }
        .selectboxit-options {
            width: 270px;
        }
    </style>
    <script>
        var api = frameElement.api, W = api.opener;
        function submitCommit() {
            if (!$("#itemAddressForm").validationEngine("validate")) {
                return;
            }
            var url = path + "/ajax/saveItemAddress.do";
            var data = $("#itemAddressForm").serialize();
            $().invoke(url, data,
                    [function (m, r) {
                        alert(r);
                        W.refreshTableAddress();
                        W.itemAddressList.close();
                    },
                        function (m, r) {
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function () {
            $("#itemAddressForm").validationEngine();
            var type = '${type}';
            if (type == "01") {
                $("input").each(function (i, d) {
                    $(d).attr("disabled", true);
                });
                $("select").each(function (i, d) {
                    $(d).attr("disabled", true);
                });
                $("textarea").attr("disabled", true);
                $("button").hide();
            }
            $("select").selectBoxIt({});
        });
        function winColse(){
            W.itemAddressList.close();
        }
    </script>
    <link href=
          <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
</head>
<c:set value="${itemAddress}" var="item"/>
<body>

<%--<div class="modal-header">
    <div class="newtitle">物品所在地</div>
</div>--%>
<div class="modal-body">
    <form class="form-horizontal" role="form" id="itemAddressForm">
        <table width="90%" border="0" style="margin-left:40px;">
            <tr style="height: 34px;">
                <td width="13%" height="28" align="right">名称：</td>
                <td width="87%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <input type="text" name="name" id="name" style="width: 300px;" value="${item.name}" placeholder=""
                               class="form-control validate[required]">
                        <input value="${item.id}" name="id" id="id" type="hidden">
                    </div>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td width="20%" height="28" align="right">物品所在地：</td>
                <td width="87%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <input type="text" placeholder="" name="address" id="address"
                               value="${item.address}" style="width: 300px;" class="form-control validate[required]">
                    </div>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td height="28" align="right">国家：</td>
                <td height="28">
                    <select name="countryList">
                        <c:forEach items="${countryList}" var="countryList">
                            <c:if test="${item.countryId==countryList.id}">
                                <option value="${countryList.id}"
                                        selected="selected">${countryList.name}</option>
                            </c:if>
                            <c:if test="${item.countryId!=countryList.id}">
                                <option value="${countryList.id}">${countryList.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td width="13%" height="28" align="right">邮编：</td>
                <td width="87%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <input type="text" name="postalCode" style="width: 300px;" id="postalCode" value="${item.postalcode}"
                               placeholder="" class="form-control validate[required]">
                    </div>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td height="28" align="right"></td>
                <td height="28" style=" padding-top:22px;">
                    <button type="button" class="net_put" onclick="submitCommit();">确定</button>
                    <button type="button" class="net_put_1" data-dismiss="modal" onclick="winColse()">关闭</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
