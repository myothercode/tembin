<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/16
  Time: 18:20
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
        function selectsite(){
            var site=$("#site").val();
            $("#siteId").val(site);
        }
        var selectShippingService1;
        function selectShippingService(){
            var siteId=$("#site").val();
            if(siteId==""){
                return;
            }else{
                var url=path+'/autoMessage/selectShippingService.do?siteId='+siteId;
                selectShippingService1 = openMyDialog({title: '选择运输方式',
                    content:'url:'+url,
                    icon: 'succeed',
                    width:800,
                    height:350,
                    parent:api,
                    lock:true,
                    zIndex:2001
                });
            }
        }
        function closedialog(){
            W.selectCountry1.close();
        }
        function submitCommit(){
            var shippingServiceId=$("font[name=templateId]");
            var internationalShippingServiceId=$("font[name=templateId1]");
            var service="";
            var service1="";
            var name="";
            var name1=""
            for(var i=0;i<shippingServiceId.length;i++){
                if(i==(shippingServiceId.length-1)){
                    service+=$(shippingServiceId[i]).attr("id");
                    name+=$(shippingServiceId[i]).attr("value1");
                }else{
                    service+=$(shippingServiceId[i]).attr("id")+",";
                    name+=$(shippingServiceId[i]).attr("value1")+",";
                }
            }
            for(var i=0;i<internationalShippingServiceId.length;i++){
                if(i==(internationalShippingServiceId.length-1)){
                    service1+=$(internationalShippingServiceId[i]).attr("id");
                    name1+=$(internationalShippingServiceId[i]).attr("value1");
                }else{
                    service1+=$(internationalShippingServiceId[i]).attr("id")+",";
                    name1+=$(internationalShippingServiceId[i]).attr("value1")+",";
                }
            }
            var autoMessageId= W.document.getElementById("id");
            console.debug(autoMessageId);
            var selectShippingServices= W.document.getElementById("selectShippingServices");
            var service12= W.document.getElementById("service");
            var flag="false";
            if($(selectShippingServices).val()!="指定物流方式"){
                flag="true";
            }
            var url=path+"/autoMessage/ajax/saveService.do?service="+service+"&flag="+flag+"&service1="+service1+"&autoMessageId="+$(autoMessageId).val()+"&name="+name+"&name1="+name1;
            $().invoke(url,null,
                    [function(m,r){
                        var coutryid="";
                        for(var i=0;i< r.length;i++){
                            if(i==(r.length-1)) {
                                coutryid+=r[i];
                            }else{
                                coutryid+=r[i]+",";
                            }
                        }
                        selectShippingServices.innerHTML="国内运输方式:"+name+";国际运输方式:"+name1;
                        $(service12).val(coutryid);
                        W.selectCountry1.close();
                        Base.token();
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
<div style="padding: 30px;">
    <div style="width:700px;height:250px;background-color: whitesmoke;border: 1px #acd0f0 solid;">
        <div style="padding: 30px;">
            <select id="site" name="site" onchange="selectsite();">
                <option value="">--请选择站点--</option>
                <c:forEach items="${sites}" var="site">
                    <option value="${site.id}">${site.name}</option>
                </c:forEach>
            </select>
        </div>
        <div style="margin-left: 30px">
            <input type="hidden" id="siteId"/>
            <div id="txtBloodType" style="width: 650px;height: 100px; background-color: #ffffff" onclick="selectShippingService();"></div>
        </div>
    </div>
    <div class="modal-footer" style="text-align: right;width: 700px;">
        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
   <%--     <button type="button" class="btn btn-primary" onclick="submitCommit();">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>--%>
    </div>
</div>

</body>
</html>
