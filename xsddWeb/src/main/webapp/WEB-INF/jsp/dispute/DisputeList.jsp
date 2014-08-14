<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/12
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
    <script type="text/javascript">
        function submitForm1(){
            var url=path+"/dispute/getDispute.do";
            var data="";
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
           /* var d=[{"callBackFunction":xxx,"url":path+"/dispute/getDispute.do"}];
            batchPost(d);*/
            /*console.debug($(box[0]).attr("spellcheck"));*/
            /*var d=[{"url":path+"/apiGetMyMessagesRequest.do","id":"1","ebayId":"6"}
             *//*{"callBackFunction":xxx,"url":path+"/apiGetMyMessagesRequest","tt":"x2"},
             {"callBackFunction":xxx,"url":path+"/apiGetMyMessagesRequest","tt":"v3"}*//*
             ];
             batchPost(d);*/
        }
        function xxx(opt){
            alert(opt);
        }
    </script>
</head>
<body>
    <input type="button" value="同步纠纷" onclick="submitForm1();"/>
</body>
</html>
