<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/30
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
    <style type="text/css">
        div{
            width:100%;
        }
    </style>
</head>
<body>
<div>
    <h1>卖家描述</h1>
<form  id="descriptionDetailsForm" >
    <input type="hidden" name="id"  value="${DescriptionDetails.id}" />
    <input type="hidden" name="Payment" id="Payment"/>
    <input type="hidden" name="Shipping" id="Shipping"/>
    <input type="hidden" name="Contact" id="Contact"/>
    <input type="hidden" name="Guarantee" id="Guarantee"/>
    <input type="hidden" name="Feedback" id="Feedback"/>
    名称：<input type="text" name="name" value="${DescriptionDetails.name}"/>
</form>

            <div class="easyui-tabs" style="width:1000px">
                <div title="Payment" style="padding:10px">
                    <script id="myEditor" type="text/plain" style="width:975px;height:400px;">${DescriptionDetails.payInfo}</script>
                </div>
                 <div title="Shipping & Handing" style="padding:10px">
                      <script type="text/plain" id="myEditor1" style="width:975px;height:400px;">${DescriptionDetails.shippingInfo}</script>
                  </div>
                  <div title="Contact us" style="padding:10px">
                      <script type="text/plain" id="myEditor2" style="width:975px;height:400px;">${DescriptionDetails.contactInfo}</script>
                  </div>
                  <div title="Guarantee" style="padding:10px">
                      <script type="text/plain" id="myEditor3" style="width:975px;height:400px;">${DescriptionDetails.guaranteeInfo}</script>
                  </div>
                  <div title="Feedback" style="padding:10px">
                      <script type="text/plain" id="myEditor4" style="width:975px;height:400px;">${DescriptionDetails.feedbackInfo}</script>
                  </div>
            </div>
    <input type="button" value="保存" onclick="submitCommit();"/>
</div>
<script type="text/javascript">
    var um =UE.getEditor('myEditor');
    var um1 = UE.getEditor('myEditor1');
    var um2 = UE.getEditor('myEditor2');
    var um3 = UE.getEditor('myEditor3');
    var um4 =UE.getEditor('myEditor4');

    _sku="dddd";

</script>
<script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    function submitCommit(){
        $("#Payment").val(um.getContent());
        $("#Shipping").val(um1.getContent());
        $("#Contact").val(um2.getContent());
        $("#Guarantee").val(um3.getContent());
        $("#Feedback").val(um4.getContent());
        var url=path+"/ajax/saveDescriptionDetails.do";
        var data=$("#descriptionDetailsForm").serialize();
        $().invoke(url,data,
                [function(m,r){
                    alert(r);
                    W.descDiag.close();
                    Base.token();
                    W.refreshTable();
                },
                    function(m,r){
                        alert(r);
                        Base.token();
                    }]
        );
    }
</script>
</body>
</html>