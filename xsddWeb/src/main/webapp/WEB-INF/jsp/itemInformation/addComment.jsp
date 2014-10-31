<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/30
  Time: 15:03
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
        function closedialog(){
            W.itemInformation.close();
        }
        function submitCommit(){
            if(!$("#remarkForm").validationEngine("validate")){
                return;
            }
            var url=path+"/information/ajax/saveComment.do";
            var data=$("#remarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
                        W.itemInformation.close();
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
    <input type="hidden" name="id" value="${information.id}">
&nbsp;&nbsp;&nbsp;备注:<input type="text" class="validate[required]" name="comment" value="${information.comment}"/>
</form>
<div class="modal-footer">
    <button type="button" class="btn btn-newco" onclick="submitCommit();">保存</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>
</div>
</body>
</html>
