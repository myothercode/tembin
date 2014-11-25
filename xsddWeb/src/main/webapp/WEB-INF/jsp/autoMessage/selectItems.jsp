<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/16
  Time: 16:34
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
            $("#orderItemTable").initTable({
                url:path + "/orderItem/ajax/loadOrdersList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption3},
                    {title:"sku",name:"sku",width:"8%",align:"left"},
                    {title:"itemid",name:"itemid",width:"8%",align:"left"},
                    {title:"title",name:"title",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#orderItemTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption3(json){
            var selectItemIdHiddeng=$("#selectItemIdHiddeng").val();
            var hidden1=selectItemIdHiddeng.split(",");
            var htm="";
            var falg=false;
            if(hidden1.length>0){
                for(var i=0;i<hidden1.length;i++){
                    if((json.id+"")==hidden1[i]){
                        falg=true;
                    }
                }
                if(falg){
                    htm = "<input checked type=\"checkbox\" onclick=\"addItemConten(this);\"  name=\"templateId\" value=" + json.id + " value1="+json.sku+">";
                }else{
                    htm = "<input type=\"checkbox\" onclick=\"addItemConten(this);\"  name=\"templateId\" value=" + json.id + " value1="+json.sku+">";
                }
            }else{
                 htm = "<input type=\"checkbox\" onclick=\"addItemConten(this);\"  name=\"templateId\" value=" + json.id + " value1="+json.sku+">";
            }
            return htm;
        }
        function closedialog(){
            W.selectCountry1.close();
        }
        function submitCommit(){
            var checkboxs=$("#selectItemHiddeng").val();
            var orderItems=W.document.getElementById("orderItems");
            var selectItems= W.document.getElementById("selectItems");
            var autoMessageId= W.document.getElementById("id");
            var flag="false";
            if($(selectItems).val()!="指定商品"){
                flag="true";
            }
            if(checkboxs.length>0){
                var itemIds=$("#selectItemIdHiddeng").val();
                var sku=$("#selectItemHiddeng").val();
              /*  for(var i=0;i<checkboxs.length;i++){
                    if(i==(checkboxs.length-1)){
                        itemIds+= $(checkboxs[i]).val();
                        sku+=$(checkboxs[i]).attr("value1");
                    }else{
                        itemIds+=$(checkboxs[i]).val()+",";
                        sku+=$(checkboxs[i]).attr("value1")+",";
                    }
                }*/
                var url=path+"/autoMessage/ajax/saveOrderItem.do?autoMessageId="+$(autoMessageId).val()+"&flag="+flag+"&itemIds="+itemIds+"&sku="+sku;
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
                            selectItems.innerHTML="<font style=\"color: #0000ff\">"+sku+"</font>";
                            $(orderItems).val(coutryid);
                            W.selectCountry1.close();
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }else{
                alert("请选择商品");
            }
        }
        function queryItem(){
            var content=$("#content").val();
            $("#orderItemTable").initTable({
                url:path + "/orderItem/ajax/loadOrdersList.do?&content="+content,
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption3},
                    {title:"sku",name:"sku",width:"8%",align:"left"},
                    {title:"itemid",name:"itemid",width:"8%",align:"left"},
                    {title:"title",name:"title",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function addItemConten(obj){
            if(obj.checked){
                var sku=$(obj).attr("value1");
                var id=$(obj).attr("value");
                var selectItemHiddeng=$("#selectItemHiddeng").val();
                var selectItemIdHiddeng=$("#selectItemIdHiddeng").val();
                var hidden=selectItemHiddeng.split(",");
                var hidden1=selectItemIdHiddeng.split(",");
                var hiddenflag2=false;
                var hiddenflag1=false;
                for(var i=0;i<hidden.length;i++){
                    if(sku==hidden[i]){
                        return;
                    }else{
                        hiddenflag2=true;
                    }
                }
                if(hiddenflag2){
                    if(selectItemHiddeng==""){
                        selectItemHiddeng+=sku;
                    }else{
                        selectItemHiddeng+=","+sku;
                    }
                }
                for(var i=0;i<hidden1.length;i++){
                    if(id==hidden1[i]){
                        return;
                    }else{
                        hiddenflag1=true;
                    }
                }
                if(hiddenflag1){
                    if(selectItemIdHiddeng==""){
                        selectItemIdHiddeng+=id;
                    }else{
                        selectItemIdHiddeng+=","+id;
                    }
                }
                $("#selectItemHiddeng").val(selectItemHiddeng);
                $("#selectItemIdHiddeng").val(selectItemIdHiddeng);
            }else{
                var sku=$(obj).attr("value1");
                var id=$(obj).attr("value");
                var selectItemHiddeng=$("#selectItemHiddeng").val();
                var selectItemIdHiddeng=$("#selectItemIdHiddeng").val();
                var hidden=selectItemHiddeng.split(",");
                var hidden1=selectItemIdHiddeng.split(",");
                var hiddenflag2=false;
                var hiddenflag1=false;
                for(var i=0;i<hidden.length;i++){
                    if(sku==hidden[i]){
                        hiddenflag2=true;
                    }
                }
                if(hiddenflag2){
                    selectItemHiddeng=selectItemHiddeng.replace(","+sku,"");
                    selectItemHiddeng=selectItemHiddeng.replace(sku,"");
                }
                for(var i=0;i<hidden1.length;i++){
                    if(id==hidden1[i]){
                        hiddenflag1=true;
                    }
                }
                if(hiddenflag1){
                    selectItemIdHiddeng=selectItemIdHiddeng.replace(","+id,"");
                    selectItemIdHiddeng=selectItemIdHiddeng.replace(id,"");
                }
                $("#selectItemHiddeng").val(selectItemHiddeng);
                $("#selectItemIdHiddeng").val(selectItemIdHiddeng);
            }
        }
    </script>
</head>
<body>
<span id="sleBG">
<span id="sleHid">
    <input  type="hidden" id="selectItemHiddeng">
    <input  type="hidden" id="selectItemIdHiddeng">
<select id="typeQuery" name="type" class="select">
    <option value="" selected="selected">SKU</option>
</select>
</span>
</span>
<div class="vsearch">
    <input id="content" name="" type="text" class="key_1"><input onclick="queryItem();" name="newbut" type="button" class="key_2"></div><br/><br/>
<div id="orderItemTable"></div><br/><br/><br/>
<div class="suspension">
    <div style="margin-right: 5px;">
        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></div>
</div>
<%-------------------------------------------修改后-----------------------------------------------------------------%>
<%--<span id="sleBG">
<span id="sleHid">
    <input  type="hidden" id="selectItemHiddeng">
    <input  type="hidden" id="selectItemIdHiddeng">
<select id="typeQuery" name="type" class="select">
    <option value="" selected="selected">SKU</option>
</select>
</span>
</span>--%>
<%--<div class="vsearch">--%>
    <%--<input id="content" name="" type="text" class="key_1"><input onclick="queryItem();" name="newbut" type="button" class="key_2"></div><br/><br/>--%>
<%--<div id="orderItemTable"></div><br/><br/><br/>--%>

       <%-- <div class="modal-footer" style="text-align: right;width: 700px;">
        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></div>--%>

</body>
</html>
