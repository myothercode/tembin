<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/2
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style>
        .table-a table{border:3px solid rgba(0, 0, 0, 0.23)
        }
    </style>
    <script type="text/javascript">
        var flag=false;
        var CaseDetails;
        function caseDetails(){
            flag=!flag;
            var casesdetail=document.getElementById("casesdetail");
            var h1="<div><b>The case details:</b><br/>${ebpCaseDetail.openreason}<br/>the buyer paid on <fmt:formatDate value="${order.paidtime}" pattern="yyyy-MM-dd HH:mm"/><br/></div>";
            var hhh="<div><b>additional information:</b><br/><c:forEach items="${responses}" var="res">${res.note}<br/></c:forEach></div><div><b>the buyer requested:</b><br/>${ebpCaseDetail.initialbuyerexpectationdetail}</div>";
            if(flag){
                casesdetail.innerHTML=h1+hhh;
            }else{
                casesdetail.innerHTML="";
            }
        }
        function respondCase(){
            var url=path+"/userCases/responseDispute.do?transactionid=${cases.transactionid}";
            CaseDetails=openMyDialog({title: '响应纠纷',
                content: 'url:'+url,
                icon: 'succeed',
                width:600,
                height:600,
                lock:true
            });
        }
    </script>
</head>
<body>
<b>Your case details</b>
<hr/>
<div class="table-a">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 560px;" >
        <tr><td><b>Please respond to your buyer.</b></td><td align="right"><input type="button" value="Respond to case" onclick="respondCase();"/></td></tr>
    </table>
</div><br/>
<a href="javascript:void()" onclick="caseDetails();"><b>The Buyer opened a case:${ebpCaseDetail.openreason}</b></a> <b><fmt:formatDate value="${cases.creationdate}" pattern="yyyy-MM-dd HH:mm"/></b>
<div id="casesdetail"></div>
<%--<div>
    <b>The case details:</b><br/>
${ebpCaseDetail.openreason}<br/>
    the buyer paid on <fmt:formatDate value="${paydate}" pattern="yyyy-MM-dd HH:mm"/><br/>
</div>
<div>
    <b>additional information:</b><br/>
    <c:forEach items="${responses}" var="res">
        ${res.note}<br/>
    </c:forEach>
</div>
<div>
    <b>the buyer requested:</b><br/>
    ${ebpCaseDetail.initialbuyerexpectationdetail}
</div>--%>

</body>
</html>
