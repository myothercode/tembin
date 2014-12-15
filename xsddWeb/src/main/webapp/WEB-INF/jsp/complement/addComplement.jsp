<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<%--<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>--%>
<html>
<head>
    <title></title>
    <script>
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            if(!jQuery("#payPalForm").validationEngine("validate"))
            {
                return;
            }
            var url=path+"/ajax/saveComplement.do";
            var data=$("#payPalForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        W.refreshTable();
                        W.complement.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        $(document).ready(function() {
            jQuery("#payPalForm").validationEngine();

            $("select").selectBoxIt({});
        });
        function winClose(){
            W.complement.close();
        }
    </script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <link href=
          <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
</head>
<body>
<%--<div class="modal-header">
    <div class="newtitle">支付方式</div>
</div>--%>
<div class="modal-body">
    <form class="form-horizontal" role="form" id="payPalForm">
        <table width="90%" border="0" style="margin-left:40px;">
            <tr>
                <td height="28" align="right" style="width: 40%;">规则类型：</td>
                <td height="28">
                    <select name="autoType" width="300px;">
                        <option value="1">停止自动补货（按照SKU）</option>
                        <option value="2">停止自动补货（按照SKU起始字符）</option>
                    </select>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td height="28" align="right" style="width: 40%;">ebay账户：</td>
                <td height="28">
                    <c:forEach items="${ebayList}" var="ebay">
                        <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}" />${ebay.ebayNameCode}</em>
                    </c:forEach>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td width="20%" height="28" align="right">SKU：</td>
                <td width="41%" height="28">
                    <div class="newselect">
                        <input type="text" name="skuKey" id="skuKey" value=""  placeholder="" class="form-control validate[required]">
                    </div>
                </td>
                <td width="43%" height="28"></td>
            </tr>
            <tr>
                <td width="16%" height="28" align="right">规则说明：</td>
                <td width="41%" height="28">
                    <div class="col-lg-10">
                        <textarea name="complementDesc"  class="form-control" cols="" rows="2" style="width:560px;"></textarea>
                    </div>
                </td>
                <td width="43%" height="28"></td>
            </tr>
        </table>
    </form>
</div>

<div class="modal-footer">
    <button type="button" class="net_put" onclick="submitCommit()">确定</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="winClose()">关闭</button>
</div>
</body>
</html>
