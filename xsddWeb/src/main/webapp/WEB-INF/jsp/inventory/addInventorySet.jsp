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
            var url=path+"/inventoryset/ajax/saveInventorySet.do";
            var data=$("#payPalForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r)
                        W.refreshTable();
                        W.inventory.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        $(document).ready(function() {
            jQuery("#payPalForm").validationEngine();
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
            $("select").selectBoxIt({});
        });
        function winClose(){
            W.inventory.close();
        }
        function showRemark(obj){
            $("div[name='remark']").hide();
            if($(obj).find("option:selected").text()=="出口易"){
                $("#cky").show();
            }else if($(obj).find("option:selected").text()=="第四方"){
                $("#dsf").show();
            }else if($(obj).find("option:selected").text()=="四海邮"){
                $("#shy").show();
            }
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
                <td height="28" align="right">站点：</td>
                <td height="28">
                    <input value="${inventory.id}" name="id" id="id" type="hidden">
                    <select name="dataType" width="300px;" onchange="showRemark(this)">
                        <c:forEach items="${inventoryList}" var="inv">
                            <c:if test="${inventory.dataType==sites.id}">
                                <option value="${inv.id}" selected="selected">${inv.name}</option>
                            </c:if>
                            <c:if test="${inventory.dataType!=sites.id}">
                                <option value="${inv.id}">${inv.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td width="16%" height="28" align="right">用户名：</td>
                <td width="41%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <input type="text" name="userName" style="width: 300px;" id="userName" value="${inventory.userName}"
                               placeholder="" class="form-control validate[required]">
                    </div>
                </td>
                <td width="43%" height="28"></td>
            </tr>
            <tr>
                <td width="16%" height="28" align="right">用户key：</td>
                <td width="41%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <input type="text" name="userKey" style="width: 300px;" id="userKey" value="${inventory.userKey}"
                               placeholder="" class="form-control validate[required]">
                    </div>
                </td>
                <td width="43%" height="28"></td>
            </tr>
        </table>
        <div id="cky" name="remark" style="padding-top: 30px;padding-left: 50px;color: red;">注意：用户名为token、用户key均为出口易官网提供。</div>
        <div id="shy" name="remark" style="display: none;padding-top: 30px;padding-left: 50px;color: red;">注意：用户名、用户key均为四海邮官网提供。</div>
        <div id="dsf" name="remark" style="display: none;padding-top: 30px;padding-left: 50px;color: red;">注意：用户名是第四方官网提供的一个用户编号，如：100800;用户key是第四方官方提供的一个字串，也叫token,如：oDuCfVi88b40oOuMYQUOcTh2b/T+uJdDBsJ+VOrlG6Q=1</div>
    </form>
</div>

<div class="modal-footer">
    <button type="button" class="net_put" onclick="submitCommit()">确定</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="winClose()">关闭</button>
</div>
</body>
</html>
