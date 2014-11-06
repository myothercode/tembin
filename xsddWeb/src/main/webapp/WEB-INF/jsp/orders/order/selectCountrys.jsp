<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/11/4
  Time: 17:29
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
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        $(document).ready(function(){
            var name="new_svt_1";
            var countryId="${countrys[0].id}";
            <c:forEach items="${countrys}" var="country" begin="0" varStatus="status">
                selectCountry("new_svt_"+${status.index+1},${country.id});
            </c:forEach>
        });
        function dialogClose(){
            W.OrderGetOrders.close();
        }
        function submitCommit(){
            var inputs=$("input[type=checkbox]:checked");
            var lis=$(W.document).find("li[name=selectCountrys]");
       /* <span class="newusa_ici_1" scop="queryCountry1" onclick="queryCountry(1,1,null);">全部&nbsp;</span>
            <span class="newusa_ici_1" scop="queryCountry2" onclick="queryCountry(2,1,null);">全部&nbsp;</span>
            <span class="newusa_ici_1" scop="queryCountry3" onclick="queryCountry(3,1,null);">全部&nbsp;</span>
            <span class="newusa_ici_1" scop="queryCountry4" onclick="queryCountry(4,1,null);">全部&nbsp;</span>
            <span class="newusa_ici_1" scop="queryCountry5" onclick="queryCountry(5,1,null)">全部&nbsp;</span>*/
            for(var i=0;i<lis.length;i++){
                var input="<span class=\"newusa_i\">收件人国家：</span><a href=\"#\"><span class=\"newusa_ici\" scop=\"queryCountry"+(i+1)+"\"  onclick=\"queryCountry("+(i+1)+",1,null);\">全部&nbsp;</span></a>";
                for(var j=0;j<inputs.length;j++){
                    var value=$(inputs[j]).attr("value");
                    var value1=$(inputs[j]).attr("value1");
                    if(value1.length==3){
                        value1=value1+"&nbsp;";
                    }else if(value1.length==2){
                        value1=value1+"&nbsp;&nbsp;";
                    }
                    var value2=$(inputs[j]).attr("value2");
                    if(value1>=5){
                        input+="<a href=\"#\"><span class=\"newusa_ici_2\" scop=\"queryCountry"+(i+1)+"\"  onclick=\"queryCountry("+(i+1)+","+(j+2)+",'"+value+"');\"><img src='"+value2+"'/>"+value1+"</span></a>";
                    }else{
                        input+="<a href=\"#\"><span class=\"newusa_ici_1\" scop=\"queryCountry"+(i+1)+"\"  onclick=\"queryCountry("+(i+1)+","+(j+2)+",'"+value+"');\"><img src='"+value2+"'/>"+value1+"</span></a>";
                    }

                }
                input+="<a href=\"javascript:void(0)\" onclick=\"selectCountrys();\"><span style=\"padding-left: 20px;vertical-align: middle;color: royalblue\">更多...</span></a>";
                $(lis[i]).empty();
                $(lis[i]).append(input);
            }
            W.OrderGetOrders.close();
        }
        function selectCountry(name,countryId){
            var url=path+"/order/selectCountry.do?countryId="+countryId;
            $().invoke(url,null,
                    [function(m,r){
                        var table="&nbsp;<table>";
                        var checkbox="";
                        for(var i=0;i< r.length;i++){
                            var td="<td width='8%'><input type='checkbox' value='"+r[i].value+"' value1='"+r[i].name+"'value2='${root}"+r[i].imgurl+"'><span><img src='${root}"+r[i].imgurl+"'>"+r[i].name+"</span></td>";
                            if(r[i].value=='US'||r[i].value=='GB'||r[i].value=='DE'||r[i].value=='AU'){
                                td="<td width='8%'><input type='checkbox' checked value='"+r[i].value+"' value1='"+r[i].name+"'value2='${root}"+r[i].imgurl+"'><span><img src='${root}"+r[i].imgurl+"'>"+r[i].name+"</span></td>";
                            }
                            if(i%8==0){
                                checkbox+="<tr>"
                                checkbox+=td;
                            }else if(i%8==7){
                                checkbox+=td;
                                checkbox+="</tr>"
                            }else{
                                checkbox+=td;
                            }
                        }
                        table+=checkbox+"</table>";
                        var div=document.getElementById(name);
                        div.innerHTML=table;
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
    </script>
</head>
<body>
<div class="modal-body">
<script type="text/javascript">
    function setvTab(name,cursel,n,countryId){
        for(i=1;i<=n;i++){
            var svt=document.getElementById(name+i);
            var con=document.getElementById("new_"+name+"_"+i);
            svt.className=i==cursel?"new_ic_1":"";
            con.style.display=i==cursel?"block":"none";
        }

    }
</script>
<div class="modal-header">
<table width="100%" border="0" style="margin-top:-20px;">
    <tbody>
    <tr>
        <td><div class="new_tab">
            <div class="new_tab_left"></div>
            <div class="new_tab_right"></div>
                <c:forEach items="${countrys}" var="country" begin="0" varStatus="status">
                    <c:if test="${status.index==0}">
                        <dt id="svt${status.index+1}" class="new_ic_1" onclick="setvTab('svt',${status.index+1},${count},${country.id})">${country.name}</dt>
                    </c:if>
                    <c:if test="${status.index!=0}">
                        <dt id="svt${status.index+1}"  onclick="setvTab('svt',${status.index+1},${count},${country.id})">${country.name}</dt>
                    </c:if>
                </c:forEach>
        </div></td>
    </tr>
    </tbody></table>
<c:forEach items="${countrys}" var="country" begin="0" varStatus="status">
    <c:if test="${status.index==0}">
        <div id="new_svt_${status.index+1}"  style="height:400px;display: block;border-left:1px solid lightgray;border-right:1px solid lightgray;border-bottom:1px solid lightgray;margin-left: 1px;margin-right: 1px;">
            <%--<link href="../../css/compiled/layout.css" rel="stylesheet" type="text/css">--%>
        </div>
    </c:if>
    <c:if test="${status.index!=0}">
        <div id="new_svt_${status.index+1}"  style="height:400px;display: none;border-left:1px solid lightgray;border-right:1px solid lightgray;border-bottom:1px solid lightgray;margin-left: 1px;margin-right: 1px;">
                <%--<link href="../../css/compiled/layout.css" rel="stylesheet" type="text/css">--%>
        </div>
    </c:if>
</c:forEach>
<div style="text-align: right;width: 800px;line-height: 100px;" >
    <button type="button" class="btn btn-primary" onclick="submitCommit();">选择</button>
    <button type="button" class="btn btn-default" onclick="dialogClose();" data-dismiss="modal">关闭</button>
</div>
</div>
</body>
</html>
