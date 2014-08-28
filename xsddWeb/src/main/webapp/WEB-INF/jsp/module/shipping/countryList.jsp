<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/30
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        function isSub(){
            var loca = $("input[name='location']");
            var locastr= "",locaname="";
            for(var i = 0;i<loca.length;i++){
                if($(loca[i]).prop("checked")){
                    locastr+=$(loca[i]).val()+",";
                    locaname+=$(loca[i]).attr("value1")+",";
                }
            }
            var api = frameElement.api, W = api.opener;
            //计算并显示
            //计算当然汇总的国家
            if(W.document.getElementById('localTionName').value==""){
                if(W.document.getElementById('localTionNameAll').value==""){
                    W.document.getElementById('localTionNameAll').value=locaname.substr(0,locaname.length-1);
                }else{
                    W.document.getElementById('localTionNameAll').value+=","+locaname.substr(0,locaname.length-1);
                }

                if(W.document.getElementById('showLocalTionName').innerHTML.trim()=="您尚未选择国家或地区"){
                    W.document.getElementById('showLocalTionName').innerHTML=locaname.substr(0,locaname.length-1);
                }else{
                    W.document.getElementById('showLocalTionName').innerHTML+=","+locaname.substr(0,locaname.length-1);
                }
            }else{
                W.document.getElementById('localTionNameAll').value=W.document.getElementById('localTionName').value+","+locaname.substr(0,locaname.length-1);
                W.document.getElementById('showLocalTionName').innerHTML=W.document.getElementById('localTionName').value+","+W.document.getElementById('localTionNamechi').value+","+locaname.substr(0,locaname.length-1);
            }

            if(W.document.getElementById('localTionValue').value==""){
                if(W.document.getElementById('localTionValueAll').value==""){
                    W.document.getElementById('localTionValueAll').value=locastr.substr(0,locastr.length-1);
                }else{
                    W.document.getElementById('localTionValueAll').value+=","+locastr.substr(0,locastr.length-1);
                }

            }else{
                W.document.getElementById('localTionValueAll').value+=","+W.document.getElementById('localTionValue').value+","+locastr.substr(0,locastr.length-1);
            }


            //在父页面存放当前选择的国家
            if(W.document.getElementById('localTionValuechi').value==""){
                W.document.getElementById('localTionValuechi').value=locastr.substr(0,locastr.length-1);
            }else{
                W.document.getElementById('localTionValuechi').value+=","+locastr.substr(0,locastr.length-1);
            }

            if(W.document.getElementById('localTionNamechi').value==""){
                W.document.getElementById('localTionNamechi').value=locaname.substr(0,locaname.length-1);
            }else{
                W.document.getElementById('localTionNamechi').value+=","+locaname.substr(0,locaname.length-1);
            }
            W.chi.close();
        }
    </script>
</head>
<body>
<div>
    <div>
        <c:forEach var="data" items="${lidata}" varStatus="da">
                <c:if test="${da.index%3==0}">
                    </br>
                </c:if>
                <span style="text-align:left;display:-moz-inline-box; display:inline-block; width:250px;">
                    <input type="checkbox" name="location" value="${data.id}" value1="${data.name}">${data.name}
                </span>
        </c:forEach>
    </div>
</div>
<div>
    <input type="button" onclick="isSub()" value="确定">
</div>
</body>
</html>
