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
    <link type="text/css" rel="stylesheet" href="<c:url value="/js/jquery-ui/multiselect/jquery.multiselect.css"/>">
    <link type="text/css" rel="stylesheet"  href="<c:url value="/js/jquery-ui/multiselect/assets/style.css"/>">
    <script type="text/javascript" src=<c:url value="/js/jquery-ui/multiselect/assets/prettify.js"/>></script>
    <script type="text/javascript" src=<c:url value="/js/jquery-ui/multiselect/jquery.multiselect.js"/>></script>

    <script>
        $(document).ready(function() {
            $("select").each(function(i,d){
                var id = $(d).attr("id");
                var urll = path+'/ajax/countryList.do?parentid='+id;
                $().invoke(
                        urll,
                        {},
                        [function (m, r) {
                            var htm = "";
                            for(var i = 0;i< r.length;i++){
                                var data = r[i];
                                htm+="<option value='"+data.value+"'>"+data.name+"</option>";
                            }
                            $(d).append(htm);
                        },
                            function (m, r) {
                            }]
                );
            });
           setTimeout(function(){
               $("select").multiselect({
                   selectList:6,
                   click: function(event, ui){
                       //oldAlert(ui.value + ' ' + (ui.checked ? 'checked' : 'unchecked') );
                       if(!ui.checked){
                           $(this).parent().find("input[type='checkbox'][name='county']").attr("checked",false);
                       }
                   },
                   close: function(){
                       tjSelect();
                   },
                   position: {
                       my: 'left bottom',
                       at: 'left top'
                   }
               });
                },100);
            //初始化选择的点击事件
            initfun();
            setTimeout(function(){
                loadSelectValue();
            },200);

        });
        function loadSelectValue(){
            var strAbc="";
            <c:forEach items="${litam}" var="litam">
            strAbc+="${litam.value},";
            </c:forEach>
            if(strAbc!=""){
                strAbc=strAbc.substr(0,strAbc.length-1);
            }
            $("#localTionValueAll").val(strAbc);
            var strs=strAbc.split(",");
            $("select").each(function(i,d){
                for(var j=0;j<strs.length;j++){
                    $(d).find("option[value='"+strs[j]+"']").attr("selected",true);
                }
                $(d).multiselect('refresh');
            });

            for(var i = 0;i<strs.length;i++){
                $("input[type='checkbox'][name='location'][value='"+strs[i]+"']").prop("checked",true);
                $("input[type='checkbox'][name='county'][value='"+strs[i]+"']").prop("checked",true);
            }
            tjSelect();
        }
        /*计算统计选择了那些国家地区*/
        function tjSelect(){
            var strValue="",strName="";
            $("input[name='location']").each(function(i,d){
                if($(d).attr("checked")=="checked"){
                    strValue+=$(d).val()+",";
                    strName+=$(d).attr("value1")+"，";
                }
            });
            var selectName = "",selectValue="";
            $("select").each(function(i,d){
                if($(d).parent().find("input[type='checkbox'][name='county']").attr("checked")=="checked"){
                    selectName+=$(d).parent().find("input[type='checkbox'][name='county']").attr("value1")+"，";
                    selectValue+=$(d).parent().find("input[type='checkbox'][name='county']").val()+",";
                }
                $(d).find("option:selected").each(function(ii,dd){
                    if($(d).parent().find("input[type='checkbox'][name='county']").attr("checked")!="checked"){
                        selectName+=$(dd).text()+"，";
                    }
                    selectValue+=$(dd).val()+",";
                });

            });

            if(strValue!=""){
                strValue=strValue.substr(0,strValue.length-1);
                if(selectValue!=""){
                    selectValue = selectValue.substr(0,selectValue.length-1);
                    $("#localTionValueAll").val(strValue+","+selectValue);
                }else{
                    $("#localTionValueAll").val(strValue);
                }
            }else{
                if(selectValue!=""){
                    selectValue = selectValue.substr(0,selectValue.length-1);
                }
                $("#localTionValueAll").val(selectValue);
            }
            if(strName!=""){
                strName = strName.substr(0,strName.length-1);
                if(selectName!=""){
                    selectName = selectName.substr(0,selectName.length-1);
                    $("#localTionNameAll").val(strName+","+selectName);
                    $("#showLocalTionName").text(strName+","+selectName);
                }else{
                    $("#localTionNameAll").val(strName);
                    $("#showLocalTionName").text(strName);
                }
            }else{
                if(selectName!=""){
                    selectName = selectName.substr(0,selectName.length-1);
                }
                $("#localTionNameAll").val(selectName);
                $("#showLocalTionName").text(selectName);
            }
        }
        function initfun(){
            //选择单个国家
            $("input[name='location']").bind("click",function(){
                tjSelect();
            });
            //选择洲
            $("input[name='county']").bind("click",function(){
                if($(this).attr("checked")=="checked"){
                    $(this).parent().find("select").find("option").attr("selected",true);
                    $(this).parent().find("select").multiselect('refresh');
                    tjSelect();
                }else{
                    $(this).parent().find("select").find("option").attr("selected",false);
                    $(this).parent().find("select").multiselect('refresh');
                    tjSelect();
                }
            });
        }

        function selectNotLocaltion(){
            var api = frameElement.api, W = api.opener;
            W.document.getElementById('notLocationName').innerHTML=$("#localTionNameAll").val();
            W.document.getElementById('notLocationValue').value=$("#localTionValueAll").val();
            W.par.close();
        }
    </script>
    <style>
        .ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default{
            height: 26px;
        }

        label {
            width: 100%;
            text-align: left;
            padding-left: 8px;
        }
    </style>
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
                <span style="display:-moz-inline-box; display:inline-block; width:250px;"><input type="checkbox" name="location" value="${data.value}" value1="${data.name}">${data.name}</span>
            </c:if>
        </c:forEach>
    </div>
</div>
<br>
<div style="padding-left: 5px;">
    <div>International</div>
    <div style="width: 100%;">
        <c:forEach var="data" items="${li2}" varStatus="da">
            <c:if test="${data.name1=='International'}">
                <c:if test="${da.index%3==0}">
                    </br>
                </c:if>
                <span style="text-align:left;display:-moz-inline-box; display:inline-block; width:300px;margin: 10px;">
                    <input type="checkbox" name="county" value="${data.value}" value1="${data.name}">${data.name}
                    <select id="${data.id}" name="${data.id}" multiple="multiple">
                    </select>
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
<div style="background-color: #F1F1F1;height:auto;padding-left: 5px;margin: 20px;" id="showLocalTionName">
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
