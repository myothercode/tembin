<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/5
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>

</head>
<script  type="text/javascript">
    _sku="_template";
    var ue =UE.getEditor('myEditor');
    var api = frameElement.api, W = api.opener;
    function submitCommit(){
        $("#templateHtml").val(ue.getContent());
        var url=path+"/ajax/saveTemplateInitTable.do";
        var data=$("#TemplateInitTableForm").serialize();
        $().invoke(url,data,
                [function(m,r){
                    alert(r);
                    W.refreshTable();
                    W.TemplateInitTable.close();
                    //Base.token();

                },
                    function(m,r){
                        alert(r);
                       // Base.token();
                    }]
        );
    }
</script>
<body>
<div style="height: 500px">
    <form id="TemplateInitTableForm">
        <input type="hidden" name="id" value="${TemplateInitTable.id}"/>
        <input type="hidden" name="templateHtml" id="templateHtml"/>
         <%--level:<input type="text" name="level" value="${TemplateInitTable.TLevel}"/>--%>
        <c:if test="${TemplateInitTable.id==null}">
            <select name="level">
                <option value="0">预设</option>
                <option value="1">用户自定义</option>
             </select>
        </c:if>
        <c:if test="${TemplateInitTable.id!=null}">
            <select name="level">
                <c:if test="${TemplateInitTable.TLevel==0}">
                    <option value="0">预设</option>
                    <option value="1">用户自定义</option>
                </c:if>
                <c:if test="${TemplateInitTable.TLevel==1}">
                    <option value="1">用户自定义</option>
                    <option value="0">预设</option>
                </c:if>
            </select>
        </c:if><br/>
        模板名字:<input type="text" name="templateName" value="${TemplateInitTable.templateName}"/>
    </form>
        <script id="myEditor" type="text/plain" style="width:975px;height:400px;">${TemplateInitTable.templateHtml}</script>
    <input type="button" value="保存" onclick="submitCommit();"/>
</div>

</body>
</html>
