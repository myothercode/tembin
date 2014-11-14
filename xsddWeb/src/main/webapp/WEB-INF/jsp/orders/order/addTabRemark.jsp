<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/23
  Time: 15:42
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
            var url=path+"/order/saveTabremark.do?folderType=${folderType}";
            var data=$("#addRemarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        W.refleshTabRemark("${folderType}");
                        W.addRemark1(r);
                        W.selectTabRemark.close();
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
            W.selectTabRemark.close();
        }
        $(document).ready(function(){
            $("#addRemarkForm").validationEngine();
        });

    </script>
</head>
<body>
<%--<form id="addRemarkForm">
    <br/><br/>&nbsp;文件夹名称:<input name="tabName"  class="validate[required]" id="tabName"/><br/>
</form>--%>

<div class="modal-header">
    <%--<div class="newtitle_1" style="margin-top:20px;">新增文件夹</div>--%>
    <form id="addRemarkForm">
    <table width="100%" border="0" style="margin-top:20px;">

    <tbody><tr>
        <td width="13%" height="28" align="right">文件夹名称：</td>

        <td width="87%" height="28"><div class="newselect"><input name="tabName" id="tabName" class="form-controlsd validate[required]" type="text"></div></td>

    </tr>
    </tbody></table>
    </form>
</div>
<div class="modal-footer" style="border-top: 0px;">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
<%--    <button type="button" class="btn btn-newco" onclick="submitCommit();">保存</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>--%>
</div>
</body>
</html>
