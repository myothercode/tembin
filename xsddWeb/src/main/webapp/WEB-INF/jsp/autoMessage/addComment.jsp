<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/11/4
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        $(document).ready(function(){
            $("#addAutoMessageCommentForm").validationEngine();
        });
        function submitCommit(){
            if(!$("#addAutoMessageCommentForm").validationEngine("validate")){
                return;
            }
            var url=path+"/autoMessage/ajax/saveComment.do?";
            var data=$("#addAutoMessageCommentForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
                        W.autoMessage.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.autoMessage.close();
        }
    </script>
</head>
<body>
<br/>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="addAutoMessageCommentForm">
            <input type="hidden" name="id" value="${id}">
            <table width="90%" border="0" style="margin-left:40px;">
                <tbody><tr>
                    <td width="16%" height="28" align="right">备注信息：</td>
                    <td width="41%" height="28"><div class="newselect" style="margin-top:9px;">
                        <input name="comment" class="form-controlsd validate[required]" type="text">
                    </div></td>
                </tr>
                </tbody></table>
            <div  style="text-align: right;width: 500px;float: left">
                <br/>
                <button type="button" class="net_put" onclick="submitCommit();">保存</button>
                <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
            </div>
        </form></div>

</body>
</html>
