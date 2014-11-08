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

    <script type="text/javascript">
        var imageUrlPrefix = '${imageUrlPrefix}';//图片前缀
    </script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/templateinittable/addTemplatePage.js" /> ></script>

    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/ajaxFileUpload/ajaxfileupload.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/ajaxfileupload.js" /> ></script>

</head>
<script  type="text/javascript">
    _sku="_template";
    var ue =UE.getEditor('myEditor',ueditorToolBar);


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
<br/>
<div style="height: 500px">
    <form id="TemplateInitTableForm">
        <input type="hidden" name="id" value="${TemplateInitTable.id}"/>
        <input type="hidden" name="templateHtml" id="templateHtml"/>
        <input type="hidden" name="templateViewUrl" id="templateViewUrl" value="${TemplateInitTable.templateViewUrl}" />
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
    选择图片:<input type="file" accept="image/*" id="multipartFiles" name="multipartFiles"/>

    &nbsp;<button onclick="uploadViewImg()">上传</button>


    <div id="editor">
        <div id="editor_toolbar_box">
            <div id="editor_toolbar">
                <input type="button" value="插入空白图片" onclick="ue.execCommand('blankimg')" style="height:24px;line-height:20px"/>
                &nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="PaymentMethodTitle" onclick="ue.execCommand('paymentmethodtitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="PaymentMethod" onclick="ue.execCommand('paymentmethod')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="ShippingDetailTitle" onclick="ue.execCommand('shippingdetailtitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="ShippingDetail" onclick="ue.execCommand('shippingdetail')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="SalesPolicyTitle" onclick="ue.execCommand('salespolicytitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="SalesPolicy" onclick="ue.execCommand('salespolicy')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="AboutUsTitle" onclick="ue.execCommand('aboutustitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="AboutUs" onclick="ue.execCommand('aboutus')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="ContactUsTitle" onclick="ue.execCommand('ContactUsTitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="ContactUs" onclick="ue.execCommand('contactus')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;
            </div>
        </div>
        <script id="myEditor" type="text/plain" style="width:975px;height:400px;">${TemplateInitTable.templateHtml}</script>
    </div>



    <input type="button" value="保存" onclick="submitCommit();"/>
</div>

</body>
</html>
