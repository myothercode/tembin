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
        var siteidStr = '${siteidStr}';
        $(document).ready(function() {
            loadChecked()
        });
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            var returnHtml = '<span class="newusa_i">选择国家：</span> <a href="javascript:void(0)" onclick="selectCounty(this)" value=""><span class="newusa_ici">全部</span></a>';
            $("input[type='checkbox'][name='siteValue']").each(function(i,d){
                if($(d).prop("checked")){
                    var url = path+$(d).attr("imgurl")
                    returnHtml+='<a href="javascript:void(0)" onclick="selectCounty(this)" value="'+$(d).val()+'"><span class="newusa_ici_1"><img src="'+url+'" />'+$(d).attr("showname")+'</span></a>';
                }
            });
            returnHtml +='<a href="javascript:void(0)" onclick="selectSiteList(this)"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a>';
            $(W.document).find("li[id='li_countyselect']").html(returnHtml);
            W.siteListPage.close();
        }
    </script>
    <style>
        body{
            background-color: #ffffff;
        }
    </style>
</head>
<body>
    <div style="height: 30px;"></div>
    <div style="padding-left: 20px;">选择ebay站点:</div>
    <div style="padding-left: 30px;padding-top: 10px;">
        <table width="100%">
            <c:forEach var="site" items="${siteList}" varStatus="index">
            <c:if test="${index.index%3==0}">
            <tr>
                </c:if>
                <td width="30%">
                    <input type="checkbox" name="siteValue" imgurl="${site.imgurl}" showname="${site.name}" value="${site.id}"/>
                    <img src="<c:url value ='${site.imgurl}'/> " style="vertical-align: baseline;"/><span style="vertical-align: text-bottom;">${site.name}</span>
                </td>
                <c:if test="${(index.index+1)%3==0}">
            <tr>
                </c:if>
                </c:forEach>
            </tr>
        </table>
    </div>
    <div style="padding-right: 20px;padding-top: 10px; text-align: right">
        <input  type="button" value="确定" onclick="submitCommit()"/>
    </div>

</body>
<script>
    function loadChecked(){
        var siteids = siteidStr.split(",");
        for(var i =0;i<siteids.length;i++){
            var siteid = siteids[i];
            $("input[type='checkbox'][name='siteValue'][value='"+siteid+"']").attr("checked","checked");
        }
    }
</script>
</html>
