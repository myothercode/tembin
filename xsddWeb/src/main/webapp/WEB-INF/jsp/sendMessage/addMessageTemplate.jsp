<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/13
  Time: 17:51
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
            W.sendGetmymessage.close();
        }
        function submitCommit(){
            if(!$("#messageForm").validationEngine("validate")){
                return;
            }

            var caseType=$("input[name=caseType]");
            var autoType=$("input[name=autoType]");
            var messageType=$("input[name=messageType]");
            caseType.val("0");
            autoType.val("0");
            messageType.val("0");
            if(caseType[0].checked){
                caseType.val("1");
            }
            if(autoType[0].checked){
                autoType.val("1");
            }
            if(messageType[0].checked){
                messageType.val("1");
            }
            var url=path+"/sendMessage/saveMessageTemplate.do";
            var data=$("#messageForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token;
                        W.refreshTable();
                        W.sendGetmymessage.close();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function(){
            $("#messageForm").validationEngine();
        });

    </script>
</head>
<body>
<div class="modal-body">
    <form class="form-horizontal" role="form" id="messageForm">
        <input type="hidden" name="id" value="${id}"/>
        <div class="form-group">

            <label class="newdt control-label" style="margin-top:9px;">选择类型</label>
            <div class="col-lg-10">
                <div class="newselect span5" style="margin-top:12px; margin-left:8px;width: 750px;">
                    <c:if test="${template.casetype==1}">
                        <input name="caseType" type="checkbox" checked> CASE模板
                    </c:if>
                    <c:if test="${template.casetype!=1}">
                        <input name="caseType" type="checkbox" > CASE模板
                    </c:if>

                    <c:if test="${template.autotype==1}">
                        <input name="autoType" type="checkbox" checked>  自动消息模板
                    </c:if>
                    <c:if test="${template.autotype!=1}">
                        <input name="autoType" type="checkbox" >  自动消息模板
                    </c:if>

                    <c:if test="${template.messagetype==1}">
                        <input name="messageType" type="checkbox" checked >  一般消息模板
                    </c:if>
                    <c:if test="${template.messagetype!=1}">
                        <input name="messageType" type="checkbox" >  一般消息模板
                    </c:if>
                </div>
            </div>

            <label  class="newdt control-label" style="margin-top:9px;">模板名称</label>
            <div class="col-lg-10">
                <div class="newselect" style="margin-top:9px;width: 750px;">
                    <input name="name" class="form-controlsd validate[required]" type="text" value="${template.name}">
                </div>
            </div><br/>
            <label  class="newdt control-label" style="margin-top:9px;">模板内容</label>
            <div class="col-lg-10">
                <div class="newselect" style="margin-top:9px;width: 750px;">
                <textarea name="content" class="form-control validate[required]" cols="" rows="4" style="width:600px;height: 200px;">${template.content}</textarea>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="submitCommit();">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>
    </div>
</div>
</body>
</html>
