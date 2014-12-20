<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/16
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function closedialog(){
            W.priceTracking.close();
        }
        function submitCommit(){
            if(!$("#autoPriceItemForm").validationEngine("validate")){
                return;
            }
            var itemId=$("#itemId").val();
            if(itemId==""){
                alert("请选择需要调价的物品号")
                return;
            }
            var url=path+"/priceTracking/ajax/saveAutoPriceListingDate.do?";
            var data=$("#autoPriceItemForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.priceTracking.close();
                        /*W.viewsendMessage1.close();*/
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function queryItemId(obj){
            $("#itemId").val(obj.value);
        }
    </script>
</head>
<body>
<br/><br/>
<form id="autoPriceItemForm">
<table>
    <tr>
        <td width="50px;"></td>
        <td style="height: 50px;"><label  class="control-label" style="line-height: 30px;" >物品号:</label></td>
        <td style="height: 50px;"><div class="controls">
            <input onchange="queryItemId(this);" name="worker" id="worker" multiple class="multiSelect" style="width: 300px;margin-left: 5px;">
            <input type="hidden" name="itemId" id="itemId" value="">
        </div>
            <input type="hidden" name="workers" id="workers" /></td>
    </tr>
    <tr>
        <td></td>
        <td style="height: 50px;"><label  class="control-label" style="line-height: 30px;" >临界数量:</label></td>
        <td style="height: 50px;"><input name="quantity" type="text" class="form-controlsd  validate[required]"></td>
    </tr>
    <tr>
        <td></td>
        <td style="height: 50px;"><label  class="control-label" style="line-height: 30px;" >降价比例:</label></td>
        <td style="height: 50px;"><input name="percent" type="text" class="form-controlsd  validate[required]"></td>
    </tr>
</table>
</form>



<div class="modal-footer" style="text-align: right;width: 550px;">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></div>
</div>
<script type="text/javascript">
 /*   $(document).ready(function() {
      $(".multiSelect").select2();
     });*/
    var preload_data=new Array();
    $(document).ready(function () {
        $(document).ready(function(){
            $("#autoPriceItemForm").validationEngine();
        });
        $('.multiSelect').select2({
            multiple: false, query: function (query) {
                var content=query.term;
                var data = {results: []};
                if(content&&content!=""){
                    var url=path+"/priceTracking/ajax/loadItemListing.do?";
                    $().invoke(url,{"content":content},
                            [function(m,r){
                                for(var i=0;i< r.length;i++){
                                    preload_data[i]={ id: r[i].itemId, text: r[i].itemId};
                                }
                                $.each(preload_data, function () {
                                    if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                                        data.results.push({id: this.id, text: this.text });
                                    }
                                });
                                query.callback(data);
                                preload_data=new Array();
                                Base.token;
                            },
                                function(m,r){
                                    alert(r);
                                    Base.token();
                                }]
                    );
                }else{
                    var data = {results: []};
                    $.each(preload_data, function () {
                        if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                            data.results.push({id: this.id, text: this.text });
                        }
                    });
                    query.callback(data);
                }

               /*   preload_data= [
                 { id: 'user0', text: 'Disabled User'}
                 , { id: 'user1', text: 'Jane Doe'}
                 , { id: 'user2', text: 'John Doe' }
                 , { id: 'user3', text: 'Robert Paulson'}
                 , { id: 'user5', text: 'Spongebob Squarepants'}
                 , { id: 'user6', text: 'Planet Bob' }
                 , { id: 'user7', text: 'Inigo Montoya' }
                 ];*/

            }
        });
        $('.multiSelect').select2('data', preload_data)
    });
    function addItemConten1(obj){
        var value=obj.value;
        var values=value.split(",");

        var add=document.getElementById("add");
        var add1=document.getElementById("add1");
        var selectItemHiddeng="";
        var selectItemIdHiddeng="";
        for(var i=0;i<values.length;i++){
            var addv=$(add).find("input[name2="+values[i]+"]");
            var addv1=$(add1).find("input[name2="+values[i]+"]");
            console.debug(addv);
            console.debug(addv1);
            if(addv1.length==0){
                $(add1).append(addv[0]);
            }
        }
        var inputs=$(add1).find("input");
        value=","+value;
        for(var j=0;j<inputs.length;j++){
            var v1=$(inputs[j]).attr("name2");
            if(value.indexOf(v1)>0){
                continue;
            }else{
                $(inputs[j]).remove();
            }
        }
        inputs=$(add1).find("input");
        for(var i=0;i<inputs.length;i++){
            selectItemHiddeng+=$(inputs[i]).attr("name1")+",";
            selectItemIdHiddeng+=$(inputs[i]).attr("name2")+",";
        }
        $("#selectItemHiddeng").val(selectItemHiddeng);
        $("#selectItemIdHiddeng").val(selectItemIdHiddeng);
    }
</script>
</body>
</html>
