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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;

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
                sku=sku.substring(0,(sku.length-1));
                itemIds=itemIds.substring(0,(itemIds.length-1));

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

    </script>

</head>
<body>

<%-------------------------------------------修改后-----------------------------------------------------------------%>
<br/><br/>
<label  class="control-label" style="line-height: 30px;" >指定商品SKU:</label>
<input  type="hidden" id="selectItemHiddeng">
<input  type="hidden" id="selectItemIdHiddeng">
<div id="add3">
    <c:forEach items="${items}" var="item">
        <c:if test="${item.type=='orderItem'}">
            <input type="hidden" name2="${item.dictionaryId}" name1="${item.value}">
        </c:if>
    </c:forEach>
</div>
<div id="add"></div>
<div id="add1">
    <c:forEach items="${items}" var="item">
        <c:if test="${item.type=='orderItem'}">
        <input type="hidden" name2="${item.dictionaryId}" name1="${item.value}">
        </c:if>
    </c:forEach>
</div>
<div class="controls">
    <input onchange="addItemConten1(this);" name="worker" id="worker" multiple class="multiSelect" style="width:400px;">
</div>
<input type="hidden" name="workers" id="workers"/>

<div class="modal-footer" style="text-align: right;width: 700px;">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></div>
</div>
<script type="text/javascript">
    /*$(document).ready(function() {
       *//* $(".multiSelect").select2();*//*
    });*/
    var preload_data=new Array();

   /* $(document).ready(function () {
        var inps=$("#add3").find("input");
        for(var i=0;i<inps.length;i++){
            var sku=$(inps[i]).attr("name1");
            var id1=$(inps[i]).attr("name2");
            preload_data[i]={ id: id1, text: sku,text1: sku};
        }

        $('.multiSelect').select2({
            multiple: true, query: function (query) {
                var content=query.term;
                var data = {results: []};
                if(content&&content!=""){
                    var url=path+"/orderItem/ajax/loadOrdersList.do";
                    $().invoke(url,{"content":content},
                            [function(m,r){
                                for(var i=0;i< r.length;i++){
                                    preload_data[i]={ id: r[i].id, text: r[i].sku,text1: r[i].sku};
                                }
                                var div=document.getElementById("add");
                                div.innerHTML="";
                                $.each(preload_data, function () {

                                    if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                                        data.results.push({id: this.id, text: this.text });
                                        var input="<input type='hidden' name2='"+this.id+"' name1='"+this.text1+"'>";
                                        $(div).append(input);
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
                            var input="<input type='hidden' name2='"+this.id+"' name1='"+this.text1+"'>";
                            $(div).append(input);
                        }
                    });
                    query.callback(data);
                }
            }
        });
        $('.multiSelect').select2('data', preload_data);
    });*/
    //------------------
    $(document).ready(function () {

        mySelect2I([{url:path+"/orderItem/ajax/loadOrdersList.do",
            data:{currInputName:"content"},bs:".multiSelect",multiple:true,fun:querySelect,maping:{id:"id",text:"sku"}}]);
        var inps=$("#add3").find("input");
        for(var i=0;i<inps.length;i++){
            var sku=$(inps[i]).attr("name1");
            var id1=$(inps[i]).attr("name2");
            preload_data[i]={ id: id1, text: sku,text1: sku};
        }
        $('.multiSelect').select2('data', preload_data);
        addItemConten1(null,$("#worker").val());
    });
    function querySelect(query){

        var content = query.term;
        var data = {results: []};
        if (content && content != "") {
            var url = path+"/orderItem/ajax/loadOrdersList.do";
            $().delayInvoke(url, {"content":content},
                    [function(m,r){
                        for(var i=0;i< r.length;i++){
                            preload_data[i]={ id: r[i].id, text: r[i].sku,text1: r[i].sku};
                        }
                        var div=document.getElementById("add");
                        div.innerHTML="";
                        $.each(preload_data, function () {

                            if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                                data.results.push({id: this.id, text: this.text });
                                var input="<input type='hidden' name2='"+this.id+"' name1='"+this.text1+"'>";
                                $(div).append(input);
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
        } else {
            var div=document.getElementById("add");
            var data = {results: []};
            $.each(preload_data, function () {
                if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                    data.results.push({id: this.id, text: this.text });
                    var input="<input type='hidden' name2='"+this.id+"' name1='"+this.text1+"'>";
                    $(div).append(input);
                }
            });
            query.callback(data);
        }
    }
    //---------------------
    function addItemConten1(obj,val123){
        var value="";
        if(obj){
            value=obj.value;
       }else{
            value=val123;
        }
        var values=value.split(",");
        var add=document.getElementById("add");
        var add1=document.getElementById("add1");
        var selectItemHiddeng="";
        var selectItemIdHiddeng="";
        for(var i=0;i<values.length;i++){
            var addv=$(add).find("input[name2="+values[i]+"]");
            var addv1=$(add1).find("input[name2="+values[i]+"]");

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

