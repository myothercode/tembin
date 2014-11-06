<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/24
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            if(!$("#addRemarkForm").validationEngine("validate")){
                return;
            }
            var url=path+"/order/saveComment.do?";
            var data=$("#addRemarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.OrderGetOrders.close();
                        /*W.viewsendMessage1.close();*/
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
        $(document).ready(function(){
            $("#addRemarkForm").validationEngine();
        });
    </script>
</head>
<body>
<form id="addRemarkForm">
    <br/><br/>
    <input type="hidden" name="orderid" value="${orderid}"/>
    <table align="center">
        <tr>
            <td>备注信息:</td>
            <td><input name="comment"  class="form-controlsd  validate[required]" id="comment"/></td>
        </tr>
        <tr>
            <td></td>
            <td align="right"> <br/><br/><button type="button" class="net_put" onclick="submitCommit();">保存</button>
                <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
        </tr>
    </table>
    <%--<br/><br/>&nbsp;备注信息:<input name="comment"  class="form-controlsd  validate[required]" id="comment"/>--%>
</form>
<%--<div class="modal-footer">
    <button type="button" class="btn btn-newco" onclick="submitCommit();">保存</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>
</div>--%>
</body>
</html>
