<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/22
  Time: 14:55
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
      /*  function submitForm1(){*/
            /*var url=path+"/sellertransactions/apiOrderSellerTransactions.do";
            var data=null;
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
           *//* var d=[{"callBackFunction":xxx,"url":path+"/sellertransactions/apiOrderSellerTransactions.do"}
            ];
            batchPost(d);*//*
        }
        *//*function xxx(opt){
            alert(opt);
        }*/
        function submitForm1(){
            var boxs=$("input[scope=checkbox]");
            var d=[];
            var j=0;
            for(var i=0;i<boxs.length;i++){
                var box=boxs[i];
                if(box.checked){
                    d[j]={"callBackFunction":xxx,"url":path+"/sellertransactions/apiOrderSellerTransactions.do","id":"1","ebayId":box.value};
                    j++;
                }
            }
            batchPost(d);
        }

        function xxx(opt){
            alert(opt);
        }
    </script>
</head>
<body>
<form id="orderForm">
    <table>
        <tr>
            <td>请选择需要同步的ebay账号:</td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <c:forEach items="${ebays}" var="ebay">
                    <input type="checkbox" scope="checkbox" value="${ebay.id}"/>${ebay.ebayName}<br/>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="button" value="同步" onclick="submitForm1();"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
