<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/17
  Time: 10:02
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
            $("#serviceTable").initTable({
                url:path + "/autoMessage/ajax/shippingServiceOptionList.do?siteId=${siteId}",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption3},
                    {title:"国内运输方式",name:"subject",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#serviceTable1").initTable({
                url:path + "/autoMessage/ajax/internationalShippingServiceList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                    {title:"国际运输方式",name:"subject",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable1();
            refreshTable();
        });
        function refreshTable(){
            $("#serviceTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function refreshTable1(){
            $("#serviceTable1").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption3(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId\" onclick=\"selectService(this);\" value=" + json.id + " value1='"+json.subject+"'>";
            return htm;
        }
        function makeOption4(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId1\" onclick=\"selectService(this);\" value=" + json.id + " value1='"+json.subject+"'>";
            return htm;
        }
        function closedialog(){
            W.selectShippingService1.close();
        }
        function selectService(obj){
            var txtBloodType= W.document.getElementById("txtBloodType");
            var name="";
            if(obj.checked){
                if(txtBloodType.innerHTML==""){
                    name+="<font id=\""+$(obj).val()+"\" value1='"+$(obj).attr("value1")+"' name='"+$(obj).attr("name")+"'>"+$(obj).attr("value1")+"</font>";
                }else{
                    name+="<font id='"+$(obj).val()+"' value1='"+$(obj).attr("value1")+"' name='"+$(obj).attr("name")+"'>"+(","+$(obj).attr("value1"))+"</font>";
                }
                $(txtBloodType).append($(name));
            }else{
                var lable= W.document.getElementById($(obj).val());
                $(lable).remove();
            }
        }
        function submitCommit(){
            /*var s= W.document.getElementById("shippingServiceIds");
            var s1= W.document.getElementById("internationalShippingServiceIds");
            var txtBloodType= W.document.getElementById("txtBloodType");
            var service=$("input[type=checkbox][name=templateId]:checked");
            var service1=$("input[type=checkbox][name=templateId1]:checked");
            var shippingServiceIds="";
            var internationalShippingServiceIds="";
            var name="国内运输方式:";
            var name1="国际运输方式:";
            for(var i=0;i<service.length;i++){
                if(i==(service.length-1)){
                    shippingServiceIds+=$(service[i]).val();
                    name+=$(service[i]).attr("value1");
                }else{
                    shippingServiceIds+=$(service[i]).val()+",";
                    name+=$(service[i]).attr("value1")+",";
                }
            }
            for(var i=0;i<service1.length;i++){
                if(i==(service1.length-1)){
                    internationalShippingServiceIds+=$(service1[i]).val();
                    name1+=$(service1[i]).attr("value1");
                }else{
                    internationalShippingServiceIds+=$(service1[i]).val()+",";
                    name1+=$(service1[i]).attr("value1")+",";
                }
            }
            $(s).val(shippingServiceIds);
            $(s1).val(internationalShippingServiceIds);
            $(txtBloodType).val(name+";"+name1);*/
            W.selectShippingService1.close();
        }
    </script>
</head>
<body>
<table align="center">
    <tr>
        <td align="center"><div id="serviceTable" style="padding: 10px;"></div></td>
    </tr>
    <tr>
        <td align="center"><div id="serviceTable1" style="padding: 10px;"></div></td>
    </tr>
</table>
<div class="modal-footer" style="text-align: right;width: 700px;">
    <button type="button" class="btn btn-primary" onclick="submitCommit();">保存</button>
    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>

<div class="label_box">
    <ul class="label"></ul>
</div>
</body>


</html>
