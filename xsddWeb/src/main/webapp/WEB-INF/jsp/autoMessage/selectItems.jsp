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
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        $(document).ready(function(){
           /* $("#orderItemTable").initTable({
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
            refreshTable();*/
            /*$("#e9").select2();*/

        });
      /*  function refreshTable(){
            $("#orderItemTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }*/
        /*function makeOption3(json){
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
        }*/
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
        /*function queryItem(){
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
        }*/
        /*function addItemConten(obj){
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
        }*/
    </script>
   <%-- <script type="text/javascript">
        jQuery(function() {

            var formatSelection = function(bond) {
                console.log(bond)
                return bond.sku;
            }

            var formatResult = function(bond) {
                return '<div class="select2-user-result">' + bond.sku + '</div>';
            }

            var initSelection = function(elem, cb) {
                console.log(elem)
                return elem
            }

            $('.select2').select2({
                placeholder: "Policy Name",
                minimumInputLength: 3,
                multiple: false,
                quietMillis: 100,
                ajax: {
                    url: path + "/orderItem/ajax/loadOrdersList.do?",
                    dataType: 'json',
                    type: 'POST',
                    data: function(term, page) {
                        return {
                            search: term,
                            page: page || 1
                        }
                    },
                    results: function(bond, page) {
                        console.debug(bond);
                        console.debug(page);
                        return {results: bond.results, more: (bond.results && bond.results.length > 1 ? true: false)};
                    }
                },
                formatResult: formatResult,
                formatSelection: formatSelection,
                initSelection: initSelection
            })
        })
    </script>--%>
</head>
<body>
<%--<span id="sleBG">
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
</div>--%>
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
        <input type="hidden" name2="${item.dictionaryId}" name1="${item.value}">
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

    $(document).ready(function () {
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
                    var url=path+"/orderItem/ajax/loadOrdersList.do?content="+content;
                    $().invoke(url,null,
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

              /*  preload_data= [
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
