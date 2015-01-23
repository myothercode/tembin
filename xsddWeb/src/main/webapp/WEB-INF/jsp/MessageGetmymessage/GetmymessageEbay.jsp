<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/11
  Time: 17:54
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
    <script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function submitForm1(){
            var boxs=$("input[scope=checkbox]:checked");
         /*   var d=[];
            var j=0
            for(var i=0;i<boxs.length;i++){
                var box=boxs[i];
               if(box.checked){
                   d[j]={"callBackFunction":xxx,"url":path+"/message/apiGetMyMessagesRequest.do","id":"1","ebayId":box.value};
                   j++;
               }
            }
            batchPost(d);*/
            /*----------------------------------*/
            var ebayIds="";
            for(var i=0;i<boxs.length;i++){
                if(i==(boxs.length-1)){
                    ebayIds+=boxs[i].value;
                }else{
                    ebayIds+=boxs[i].value+",";
                }
            }
            var url=path+"/message/apiGetMyMessagesRequest.do?ebayIds="+ebayIds;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        W.MessageGetmymessage.close();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
            /*----------------------------------*/

        }

        function xxx(opt){
            alert(opt);
            W.MessageGetmymessage.close();
            W.refreshTable();
        }
        function closedialog(){
            W.MessageGetmymessage.close();
        }
        function selectAllcheckBox(obj){
            if(obj.checked){
                var inputs=$(document).find("input[name=checkbox][scope=checkbox]");
                for(var i=0;i<inputs.length;i++){
                    inputs[i].checked=true;
                }
            }else{
                var inputs=$(document).find("input[name=checkbox][scope=checkbox]");
                for(var i=0;i<inputs.length;i++){
                    inputs[i].checked=false;
                }
            }
        }
    </script>
    
</head>
<body>
<%--<form id="ebayForm">
    <input type="hidden" name="id" value="1">
    <table>
        <tr>
            <td>请选择需要同步的ebay账号:</td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>
                &lt;%&ndash;<select name="ebayId">&ndash;%&gt;
                    <c:forEach items="${ebays}" var="ebay">
                        <input type="checkbox" scope="checkbox" value="${ebay.id}"/>${ebay.ebayName}<br/>
                        &lt;%&ndash;<option value="${ebay.id}">${ebay.ebayName}</option>&ndash;%&gt;
                    </c:forEach>
            &lt;%&ndash;    </select>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="button" value="同步" onclick="submitForm1();"/>
            </td>
        </tr>
    </table>
</form>--%>

<%--<div class="modal-header">
    <h4 class="modal-title" style="color:#2E98EE"></h4>
    <div class="newtitle">请选择要同步的ebay账号</div>
</div>--%>
<div class="modal-body">
    <form class="form-horizontal" role="form" id="ebayForm">
        <table width="100%" border="0" style="margin-left:20px;">
            <tbody>
            <tr>
                <td width="20%" height="28"><input type="checkbox" name="checkbox" scope="allCheckbox" onchange="selectAllcheckBox(this);" > 全选</td>
                <td width="80%" height="28" align="left">上次同步时间</td>
            </tr>
            <c:forEach items="${ebays}" var="ebay" varStatus="status" begin="0">
                <c:if test="${status.index==0}">
                    <tr>
                        <td width="20%" height="28"><input type="checkbox" name="checkbox" scope="checkbox" value="${ebay.id}" > ${ebay.ebayName}</td>
                        <td width="80%" height="28"><c:if test="${ebayDates[status.index]!=null}">(${ebayDates[status.index]})</c:if></td>
                    </tr>
                </c:if>
                <c:if test="${status.index!=0}">
                    <tr>
                        <td width="20%" height="28"><input type="checkbox" name="checkbox" scope="checkbox" value="${ebay.id}" > ${ebay.ebayName}</td><td width="80%" height="28"><c:if test="${ebayDates[status.index]!=null}">(${ebayDates[status.index]})</c:if></td>
                    </tr>
                </c:if>

            </c:forEach>
            <%--   <tr>
                   <td width="92%" height="28"><input type="checkbox" name="checkbox" > vsadf</td>
               </tr>
               <tr>
                   <td width="92%" height="28"><input type="checkbox" name="checkbox" > vsadf</td>
               </tr>
               <tr>
                   <td width="92%" height="28"><input type="checkbox" name="checkbox" > vsadf</td>
               </tr>--%>
            </tbody></table>

        <div class="modal-footer">
            <button type="button" class="net_put" onclick="submitForm1();">同步</button>
            <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
        </div>
    </form></div>
</body>
</html>
