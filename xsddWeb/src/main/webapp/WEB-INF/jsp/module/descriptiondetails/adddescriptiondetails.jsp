<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/30
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>UMEDITOR 完整demo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/umeditor/themes/default/css/umeditor.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
<%-- <link href="/js/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">--%>
    <script typr="text/javascript" src=<c:url value ="/js/umeditor/third-party/jquery.min.js" /> ></script>
    <script typr="text/javascript" src=<c:url value ="/js/umeditor/umeditor.config.js" /> ></script>
    <script typr="text/javascript" src=<c:url value ="/js/umeditor/umeditor.min.js" /> ></script>
    <script typr="text/javascript" src=<c:url value ="/js/umeditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script typr="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
    <style type="text/css">
        p{
            font-family: "微软雅黑";
            font-weight: normal;
        }
    </style>
</head>
<body>
<p>卖家描述</p>
<form action="/xsddWeb/saveDescriptionDetails.do" method="post">
    <input type="hidden" name="id"  value="${DescriptionDetails.id}" />
    <input type="hidden" name="Payment" id="Payment"/>
    <input type="hidden" name="Shipping" id="Shipping"/>
    <input type="hidden" name="Contact" id="Contact"/>
    <input type="hidden" name="Guarantee" id="Guarantee"/>
    <input type="hidden" name="Feedback" id="Feedback"/>
    名称：<input type="text" name="name" value="${DescriptionDetails.name}"/></br>
            <div class="easyui-tabs" style="width:1000px">
                <div title="Payment" style="padding:10px">
                    <script type="text/plain" name="content" id="myEditor" style="width:950px;height:240px;">
                        <p>${DescriptionDetails.payInfo}<p>
                    </script>
                 </div>
                 <div title="Shipping & Handing" style="padding:10px">
                      <script type="text/plain" id="myEditor1" style="width:950px;height:240px;">
                          <p>${DescriptionDetails.shippingInfo}</p>
                      </script>
                  </div>
                  <div title="Contact us" style="padding:10px">
                      <script type="text/plain" id="myEditor2" style="width:950px;height:240px;">
                          <p>${DescriptionDetails.contactInfo}</p>
                      </script>
                  </div>
                  <div title="Guarantee" style="padding:10px">
                      <script type="text/plain" id="myEditor3" style="width:950px;height:240px;">
                          <p>${DescriptionDetails.guaranteeInfo}</p>
                      </script>
                  </div>
                  <div title="Feedback" style="padding:10px">
                      <script type="text/plain" id="myEditor4" style="width:950px;height:240px;">
                          <p>${DescriptionDetails.feedbackInfo}</p>
                      </script>
                  </div>
            </div>
    <input type="button" value="保存" onclick="submitCommit();"/>
</form>
<div class="clear"></div>
<script type="text/javascript">
    //实例化编辑器
    var um = UM.getEditor('myEditor');
    var um1 = UM.getEditor('myEditor1');
    var um2 = UM.getEditor('myEditor2');
    var um3 = UM.getEditor('myEditor3');
    var um4 = UM.getEditor('myEditor4');
    function submitCommit(){
        $("#Payment").val(um.getPlainTxt());
        $("#Shipping").val(um1.getPlainTxt());
        $("#Contact").val(um2.getPlainTxt());
        $("#Guarantee").val(um3.getPlainTxt());
        $("#Feedback").val(um4.getPlainTxt());
        $("form")[0].submit();
    }
</script>

</body>
</html>