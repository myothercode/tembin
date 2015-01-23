<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/1/17
  Time: 9:41
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
            W.itemInformation.close();
        }
        function submitCommit(){
            if(!$("#remarkForm").validationEngine("validate")){
                return;
            }
            var url=path+"/message/ajax/saveComment.do";
            var data=$("#remarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.MessageGetmymessage.close();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function(){
            $("#remarkForm").validationEngine();
        });
    </script>
</head>
<body>
<form id="remarkForm">
    <br/><br/>
    <input type="hidden" name="id" id="id" value="${message.id}">
    <table align="center">
        <tr>
            <td>备注:</td>
            <td><input type="text" class="form-controlsd validate[required]" id="comment" name="comment" value="${message.comment}"/></td>
        </tr>
    </table>
    <%--&nbsp;&nbsp;&nbsp;备注:<input type="text" class="form-controlsd validate[required]" name="comment" value="${information.comment}"/>--%>
</form>
<div class="modal-footer" align="right">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>
</body>
</html>
