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
            var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + " value1="+json.sku+">";
            return htm;
        }
        function closedialog(){
            W.selectCountry1.close();
        }
        function submitCommit(){
            var checkboxs=$("input[type=checkbox][name=templateId]:checked")
            var orderItems=W.document.getElementById("orderItems");
            var selectItems= W.document.getElementById("selectItems");
            var autoMessageId= W.document.getElementById("id");
            var flag="false";
            if($(selectItems).val()!="指定商品"){
                flag="true";
            }
            if(checkboxs.length>0){
                var itemIds="";
                var sku="";
                for(var i=0;i<checkboxs.length;i++){
                    if(i==(checkboxs.length-1)){
                        itemIds+= $(checkboxs[i]).val();
                        sku+=$(checkboxs[i]).attr("value1");
                    }else{
                        itemIds+=$(checkboxs[i]).val()+",";
                        sku+=$(checkboxs[i]).attr("value1")+",";
                    }
                }
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
                            selectItems.innerHTML=sku;
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
    </script>
</head>
<body>
<span id="sleBG">
<span id="sleHid">
<select id="typeQuery" name="type" class="select">
    <option value="" selected="selected">SKU</option>
</select>
</span>
</span>
<div class="vsearch">
    <input id="content" name="" type="text" class="key_1"><input onclick="queryItem();" name="newbut" type="button" class="key_2"></div><br/><br/>
<div id="orderItemTable"></div><br/><br/><br/>
<div class="modal-footer" style="text-align: right;width: 700px;">
    <button type="button" class="btn btn-primary" onclick="submitCommit();">保存</button>
    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>
</body>
</html>
