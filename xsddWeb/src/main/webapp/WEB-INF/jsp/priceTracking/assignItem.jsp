<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/16
  Time: 11:32
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
        function closedialog(){
            W.priceTracking.close();
        }
        $(document).ready(function(){
            $("#priceItemForm").validationEngine();
        });
        function submitCommit(){
            if(!$("#priceItemForm").validationEngine("validate")){
                return;
            }
            var url=path+"/priceTracking/ajax/saveItemPrice.do?";
            var data=$("#priceItemForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.priceTracking.close();
                        /*W.viewsendMessage1.close();*/
                        Base.token;
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
<br/><br/>
<form id="priceItemForm">
    <table >
        <tr>
            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;物品号:</td>
            <td ><input name="itemId"   class="form-controlsd  validate[required]" id="itemId" style="width: 250px;"/></td>
        </tr>
        <tr>
            <td></td>
            <td align="right" style="line-height: 5px;"> <br/><br/><button type="button" class="net_put" onclick="submitCommit();">保存</button>
                <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
        </tr>
    </table>
    <%--<br/><br/>&nbsp;备注信息:<input name="comment"  class="form-controlsd  validate[required]" id="comment"/>--%>
</form>
</body>
</html>
