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
            var conf = confirm("如果ebay账号已设置，这次修改了补数方式，那么之前设置的规则将全部删掉，你确定保存修改？");
            if(conf == true) {

            } else {
                return;
            }
            var url=path+"/ajax/saveSetEbayComplement.do";
            var data=$("#payPalForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        W.refreshSetEbayComplementTable();
                        W.ebaycomplement.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        $(document).ready(function() {
            jQuery("#payPalForm").validationEngine();
            var complementType='${tsc.complementType}';
            var ebayId = '${tsc.ebayId}';
            if(complementType!=null&&complementType!=""){
                $("select[name='complementType']").find("option[value='"+complementType+"']").attr("selected", true);
            }
            if(ebayId!=null&&ebayId!=""){
                $("input[type='checkbox'][name='ebayAccounts'][value='"+ebayId+"']").attr("checked", "checked");
            }
            $("select").selectBoxIt({});
        });
        function winCloseSetEbay(){
            W.ebaycomplement.close();
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
<div class="modal-body" style="padding-bottom: 5px;">
    <form class="form-horizontal" role="form" id="payPalForm">
        <table width="90%" border="0" style="margin-left:40px;">
            <tr>
                <td height="28" align="right" style="width: 20%;">补数类型：</td>
                <td height="28">
                    <select name="complementType" width="300px;">
                        <option value="1">自动补数</option>
                        <option value="2">库存补数</option>
                    </select>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td height="28" align="right">ebay账户：</td>
                <td height="28">
                    <c:forEach items="${ebayList}" var="ebay">
                        <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}" />${ebay.ebayNameCode}</em>
                    </c:forEach>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
        </table>
    </form>
</div>
<div class="modal-footer" style="margin-top:0px; ">
    <button type="button" class="net_put" onclick="submitCommit()">确定</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="winCloseSetEbay()">关闭</button>
</div>
</body>
</html>
