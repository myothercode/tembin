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
            var url=path+"/information/ajax/saveComment.do";
            var data=$("#remarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        var table=W.document.getElementById("ItemInformationListTable");
                        var inputs=$(table).find("input[scop1=selected]");
                        for(var i=0;i<inputs.length;i++){
                            var tr=$(inputs[i]).parent().parent();
                            var tds=$(tr).find("td");
                            var td1=$("#addPictureId").find("td");
                            var img1=$(td1[0]).find("img[scop=img]")
                            for(var j=0;j<tds.length;j++){
                                if(j==2){
                                    var value=$("#comment").val();
                                    tds[j].innerHTML=value;
                                }
                            }
                        }
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
    <input type="hidden" name="id" id="id" value="${information.id}">
    <table align="center">
        <tr>
            <td>备注:</td>
            <td><input type="text" class="form-controlsd validate[required]" id="comment" name="comment" value="${information.comment}"/></td>
        </tr>
    </table>
<%--&nbsp;&nbsp;&nbsp;备注:<input type="text" class="form-controlsd validate[required]" name="comment" value="${information.comment}"/>--%>
</form>
<div class="modal-footer" align="right">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>
<%--<div class="modal-content">
    <div class="modal-header">
        <h4 class="modal-title" style="color:#2E98EE">添加备注</h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="remarkForm">
            <input type="hidden" name="id" value="${information.id}">
            <table width="100%" border="0" style="margin-left:40px;">
                <tbody><tr>
                    <td width="16%" height="28" align="right">备注信息：</td>
                    <td width="41%" height="28"><div class="newselect" style="margin-top:9px;">
                        <input name="comment" class="form-controlsd validate[required]" type="text" value="${information.comment}">
                    </div></td>
                    <td width="43%" height="28">&lt;%&ndash;<button type="button" class="net_put">保存</button><button type="button" class="net_put_1" data-dismiss="modal">关闭</button>&ndash;%&gt;</td>
                </tr>
                <tr>
                    <td width="16%" height="28" align="right"></td>
                    <td width="41%" height="28" align="right"><br/><button type="button" class="net_put" onclick="submitCommit();">保存</button><button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
                    <td width="43%" height="28"></td>
                </tr>
                </tbody></table>

        </form></div><!-- /.modal-content -->
</div>--%>
</body>
</html>
