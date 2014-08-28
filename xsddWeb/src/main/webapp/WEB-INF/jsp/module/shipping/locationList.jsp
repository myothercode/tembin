<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        $(document).ready(function() {
            $("input[name='location']").bind("click",function(){
                var loca = $("input[name='location']");
                var locastr= "",locaname="";
                for(var i = 0;i<loca.length;i++){
                    if($(loca[i]).prop("checked")){
                        $(loca[i]).parent().find("span").hide();
                        locastr+=$(loca[i]).val()+",";
                        locaname+=$(loca[i]).attr("value1")+",";
                    }else{
                        $(loca[i]).parent().find("span").show();
                    }
                }

                if(locaname==""){
                    $("#showLocalTionName").text($("#localTionNamechi").val());
                }else{
                    if($("#localTionNamechi").val()==""){
                        $("#showLocalTionName").text(locaname.substr(0,locaname.length-1));
                    }else{
                        $("#showLocalTionName").text(locaname.substr(0,locaname.length-1)+","+$("#localTionNamechi").val());
                    }

                }

                $("#localTionName").val(locaname.substr(0,locaname.length-1))
                $("#localTionValue").val(locastr.substr(0,locastr.length-1))

                if($("#localTionValueAll").val()==""){
                    if($("#localTionValuechi").val()==""){
                        $("#localTionValueAll").val(locastr.substr(0,locastr.length-1));
                    }else{
                        $("#localTionValueAll").val(locastr.substr(0,locastr.length-1)+","+$("#localTionValuechi").val());
                    }
                }else{
                    $("#localTionValueAll").val(locastr.substr(0,locastr.length-1));
                }

                if($("#localTionNameAll").val()==""){
                    if($("#localTionNamechi").val()==""){
                        $("#localTionNameAll").val(locaname.substr(0,locaname.length-1));
                    }else{
                        $("#localTionNameAll").val(locaname.substr(0,locaname.length-1)+","+$("#localTionNamechi").val());
                    }
                    $("#localTionNameAll").val(locaname.substr(0,locaname.length-1)+","+$("#localTionNamechi").val());
                }else{
                    $("#localTionNameAll").val(locaname.substr(0,locaname.length-1));
                }

            });
        });
        var chi =""
         function selectCountry(id){
            var api = frameElement.api, W = api.opener;
            chi = $.dialog({title: '不运送地选项',
                content: 'url:/xsddWeb/countryList.do?parentid='+id,
                icon: 'succeed',
                width:800,
                parent:api,
                lock:true,
                zIndex:2004
            });
        }

        function selectNotLocaltion(){
            var api = frameElement.api, W = api.opener;
            W.document.getElementById('notLocationName').innerHTML=$("#localTionNameAll").val();
            W.document.getElementById('notLocationValue').value=$("#localTionValueAll").val();
            W.par.close();
        }
    </script>
</head>
<body>
<div>
选取一个或多个选项，您可以不运送到某个国家或者整个地区
</div>
<div>
    <div>Domestic</div>
    <div>
        <c:forEach var="data" items="${li1}"  varStatus="da">
            <c:if test="${data.name1=='Domestic'}">
                <c:if test="${da.index%3==0}">
                    </br>
                </c:if>
                <span style="display:-moz-inline-box; display:inline-block; width:250px;"><input type="checkbox" name="location" value="${data.id}" value1="${data.name}">${data.name}</span>
            </c:if>
        </c:forEach>
    </div>
</div>
<br>
<div>
    <div>International</div>
    <div>
        <c:forEach var="data" items="${li2}" varStatus="da">
            <c:if test="${data.name1=='International'}">
                <c:if test="${da.index%3==0}">
                    </br>
                </c:if>
                <span style="text-align:left;display:-moz-inline-box; display:inline-block; width:250px;">
                    <input type="checkbox" name="location" value="${data.id}" value1="${data.name}">${data.name}
                    <span>
                    [<a href="javascript:void(0)" onclick="selectCountry('${data.id}')">
                    显示所有国家
                    </a>]
                    </span>
                    </span>
            </c:if>
        </c:forEach>
    </div>
</div>
<br>
<div>
    <div>Additional Locations</div>
    <div>
        <br>
        <c:forEach var="data" items="${li3}">
            <c:if test="${data.name1=='Additional Locations'}">
                <input type="checkbox" name="location" value="${data.id}" value1="${data.name}">${data.name}
            </c:if>
        </c:forEach>
    </div>
</div>
<div style="background-color: #F1F1F1;height: 60px;" id="showLocalTionName">
    您尚未选择国家或地区
</div>
<input type="hidden" name="localTionValue" id="localTionValue">
<input type="hidden" name="localTionName" id="localTionName">

<input type="hidden" name="localTionValuechi" id="localTionValuechi">
<input type="hidden" name="localTionNamechi" id="localTionNamechi">

<input type="hidden" name="localTionValueAll" id="localTionValueAll">
<input type="hidden" name="localTionNameAll" id="localTionNameAll">

<div>
    <input name="quert" value="确定" onclick="selectNotLocaltion()" type="button">
</div>
</body>
</html>
