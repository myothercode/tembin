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
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        var searchCompetitors;
        function closedialog(){
            W.priceTracking.close();
        }
        function submitCommit(){
            if(!$("#autoPriceItemForm").validationEngine("validate")){
                return;
            }
            var itemId=$("#itemId").val();
            if(itemId==""){
                alert("请选择需要调价的物品号");
                return;
            }
            var url=path+"/priceTracking/ajax/saveAutoPriceListingDate.do?";
            var inputs=$("input[name=competitorHidden]");
            console.debug(inputs);
            if(inputs.length>0){
                var ids="";
                for(var i=0;i<inputs.length;i++){
                    console.debug($(inputs[i]));
                    if(i==(inputs.length-1)){
                        ids+=$(inputs[i]).val();
                    }else{
                        ids+=$(inputs[i]).val()+",";
                    }
                }
                url=path+"/priceTracking/ajax/saveAutoPriceListingDate.do?competitorIds="+ids;
            }
            var data=$("#autoPriceItemForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.priceTracking.refreshTable();
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

        function addCompetitors(){
            var url=path+"/priceTracking/addCompetitors.do?";
            searchCompetitors=openMyDialog({title: '添加竞争对手',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                lock:true,
                zIndex:1500
            });
        }
        function deleteTr(obj){
            var tr=$(obj).parent().parent();
            $(tr).remove();
        }
        function insertCompetitors(obj){
            var tr=$(obj).parent().parent();

        }
    </script>
</head>
<body>
<br/><br/>
<form id="autoPriceItemForm">
<table id="competitorId">
    <tr>
        <td width="50px;"></td>
        <td style="height: 50px;"><label  class="control-label" style="line-height: 30px;" >物品号:</label></td>
        <td style="height: 50px;width: 400px;"><div class="controls">
            <input onchange="queryItemId(this);" name="worker" id="worker" multiple class="multiSelect" style="width: 300px;margin-left: 5px;" value=" ">
            <input type="hidden" name="itemId" id="itemId" value="">
        </div>
            <input type="hidden" name="workers" id="workers" /></td>
    </tr>
   <%-- <tr>
        <td></td>
        <td style="height: 50px;"><label  class="control-label" style="line-height: 30px;" >临界数量:</label></td>
        <td style="height: 50px;"><input name="quantity" type="text" class="form-controlsd  validate[required]"></td>
    </tr>
    <tr>
        <td></td>
        <td style="height: 50px;"><label  class="control-label" style="line-height: 30px;" >降价比例:</label></td>
        <td style="height: 50px;"><input name="percent" type="text" class="form-controlsd  validate[required]"></td>
    </tr>--%>
    <tr><td></td><td ><a href="javascript:void(0)" onclick="addCompetitors();" style="color: #0000ff;margin-left: 10px;" >添加竞争对手</a></td><td></td></tr>
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
       /* mySelect2I([{url:path+"/priceTracking/ajax/loadItemListing.do",
                    data:{currInputName:"content"},bs:".multiSelect",multiple:false,fun:null,maping:{id:"id",text:"sku"}}]);*/
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