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
            initfun();
        });
        function initfun(){
            $("input[name='location']").bind("click",function(){
                var loca = $("input[name='location']");
                var locastr= "",locaname="";
                for(var i = 0;i<loca.length;i++){
                    if($(loca[i]).prop("checked")){
                        $(loca[i]).parent().find("span").hide();
                        $(loca[i]).parent().find(".abcd").hide();
                        $(loca[i]).parent().find(".abcd").find("input[name='location']").attr("checked",false);
                        $(loca[i]).parent().find("a").text("显示所有国家");
                        locastr+=$(loca[i]).val()+",";
                        locaname+=$(loca[i]).attr("value1")+",";
                    }else{
                        $(loca[i]).parent().find("span").show();
                    }
                }
                if(locastr!=""){
                    $("#showLocalTionName").text(locaname.substr(0,locaname.length-1));
                    $("#localTionNameAll").val(locaname.substr(0,locaname.length-1));
                    $("#localTionValueAll").val(locastr.substr(0,locastr.length-1));
                }
            });

        }
        var chi =""
         function selectCountry(id,obj){
             if($(obj).parent().parent().find(".abcd").html()==""||$(obj).parent().parent().find(".abcd").html()==null){
                 $(obj).parent().parent().find(".abcd").show();
                 var urll = path+'/ajax/countryList.do?parentid='+id;
                 $().invoke(
                         urll,
                         {},
                         [function (m, r) {
                             for(var i = 0;i< r.length;i++){
                                 var data = r[i];
                                 var str = "<div style='padding-left: 10px;'><input type='checkbox' name='location' value1="+data.name+" value ='"+data.value+"'>"+data.name+"</div>";
                                 <c:forEach items="${litam}" var="tam">
                                        if(data.value=='${tam.value}'){
                                            str = "<div style='padding-left: 10px;'><input type='checkbox' name='location' value1="+data.name+" value ='"+data.value+"' checked>"+data.name+"</div>";
                                        }
                                 </c:forEach>
                                 $(obj).parent().parent().find(".abcd").append(str);
                             }
                             $(obj).text("隐藏所有国家");
                             initfun();
                         },
                             function (m, r) {
                             }]
                 );
             }else {
                 if ($(obj).text() == "显示所有国家") {
                    $(obj).parent().parent().find(".abcd").show();
                     $(obj).text("隐藏所有国家");
                 }else{
                     $(obj).parent().parent().find(".abcd").hide();
                     $(obj).text("显示所有国家");
                 }
             }
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
<br>
<br>
<div style="padding-left: 5px;">
选取一个或多个选项，您可以不运送到某个国家或者整个地区
</div>
<div style="padding-left: 5px;">
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
<div style="padding-left: 5px;">
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
                        <select name="${data.name}" id="${data.id}">

                        </select>
                    [<a href="javascript:void(0)" onclick="selectCountry('${data.id}',this)">
                    显示所有国家
                    </a>]
                    </span>
                    <div class="abcd"></div>
                </span>
            </c:if>
        </c:forEach>
    </div>
</div>
<br>
<div style="padding-left: 5px;">
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
<div style="background-color: #F1F1F1;height: 60px;padding-left: 5px;" id="showLocalTionName">
    您尚未选择国家或地区
</div>
<input type="hidden" name="localTionValue" id="localTionValue">
<input type="hidden" name="localTionName" id="localTionName">

<input type="hidden" name="localTionValuechi" id="localTionValuechi">
<input type="hidden" name="localTionNamechi" id="localTionNamechi">

<input type="hidden" name="localTionValueAll" id="localTionValueAll">
<input type="hidden" name="localTionNameAll" id="localTionNameAll">

<div class="tbbay">
    <a href="javascript:void(0)" onclick="selectNotLocaltion()">确定</a>
</div>
</body>
</html>
