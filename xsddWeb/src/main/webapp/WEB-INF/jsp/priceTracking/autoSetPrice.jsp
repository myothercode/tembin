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
    <style type="text/css">
        *{margin:0;padding:0;list-style-type:none;}
        a,img{border:0;}
        body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}

        .label_box{display:none;width:430px;margin:20px auto;}
        .label{height:30px;margin-left:142px;padding:0 10px;}
        .label li{float:left;line-height:30px;margin-right:15px;}
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
            var remarks=$("input[name=label]");
            var remark="";
            if(remarks.length==0){
                alert("请先添加竞争对手物品号");
                return;
            }else{
                for(var i=0;i<remarks.length;i++){
                    if(i==(remarks.length-1)){
                        remark+=$(remarks[i]).val();
                    }else{
                        remark+=$(remarks[i]).val()+",";
                    }
                }
            }
            $("#remark123").val(remark);
            var url=path+"/priceTracking/ajax/saveAutoPriceListingDate.do?";
            var inputs=$("input[name=competitorHidden]");
            if(inputs.length>0){
                var ids="";
                for(var i=0;i<inputs.length;i++){
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
                        W.refreshTable();
                        W.priceTracking.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function queryItemId(obj){
            console.debug(obj);
            $("#itemId").val(obj.value);
            var url=path+"/priceTracking/ajax/addCompetitorsInformation.do?itemid="+obj.value;
            $().invoke(url,null,
                    [function(m,r){
                        var competitorTable=document.getElementById("competitorId");
                        var tr=document.getElementById("competitorTr");
                        if(tr){
                            $(tr).remove();
                        }
                        var tr="<tr name='competitorTr' id='competitorTr'>" +
                                "<td></td>" +
                                "<td valign='top'><label  class='control-label' style='line-height: 45px;' >对手物品码:</label></td>" +
                                "<td valign='top' align='left'><ul id='addRemark' style='padding: 3px 5px 3px 5px;border: 1px solid lightgray;width: 300px;border-radius: 4px;margin-left: 4px;background-color: #ffffff;margin-top: 3px;float: left'>";
                        var rules=r[0]["rules"];
                        if(rules&&rules.length>0){
                            for(var i=0;i<rules.length;i++){
                                tr+="<a href='javascript:void(0)' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class='icon-remove-sign' style='margin-right: 2px;'></i><span >"+rules[i].competitoritemid+"</span><input type='hidden' name='label' value='"+rules[i].competitoritemid+"'></a>";
                            }
                        }
                        tr+="<input style='margin-left:10px;width:40px;background-color: #fff;border-radius: 5px;' id='kk' type='text' value='' onfocus='addLength(this)' onblur='subLength(this)' onkeyup='addColor(this);' /></ul><br/><br/>" +
                                "<span style='color: #0000ff;float: left'>请使用【enter】键添加物品码</span><br/><table style='float: left'>";
                        for(var i=0;i< r.length;i++){

                                    /*"<tr name='competitorTr' id='competitorTr"+tracking.id+"'>" +
                                    "<td><input type='hidden' name='competitorHidden'value='"+tracking.id+"'></td>" +
                                    "<td valign='top'><label  class='control-label' style='line-height: 30px;' >竞争对手:</label></td>" +
                                    "<td valign='top' align='left'><label style='line-height: 30px;' >"+tracking.sellerusername+"</label><a href='javascript:void(0);' style='color: #0000ff;line-height: 30px;' onclick='deleteTr(this)'>移除</a><br/><br/>" +
                                    "<table>";*/
                            var price=r[i]["price"];
                            var rank=r[i]["rank"];
                            if(price){
                                tr+="<tr>" +
                                    "<td style='width: 300px;'>价格始终" +
                                    "<select name='competitorPrice'>" +
                                        "<option value='0'>竞争对手价格</option>";
                                if(price.ruletype=="priceLowerType"){
                                    tr+="<option value='1' selected>低于竞争对手</option>" +
                                        "<option value='2'>高于竞争对手</option>";
                                }else{
                                    tr+= "<option value='1'>低于竞争对手</option>" +
                                         "<option value='2' selected>高于竞争对手</option>";
                                }
                                tr+="</select>" +
                                    "<select name='competitorPriceAdd'>";
                                if(price.increaseordecrease=="-"){
                                    tr+="<option selected>-</option>" +
                                        "<option  >+</option>";
                                }else{
                                    tr+="<option >-</option>" +
                                        "<option  selected>+</option>";
                                }
                                tr+="</select>" +
                                    "<input name='competitorPriceInput' value='"+price.inputvalue+"' style='width: 50px;margin-left: 5px;margin-right: 5px; '/>" +
                                    "<select name='competitorPriceSymbol'>";
                                if(price.sign=="$"){
                                   tr+= "<option selected>$</option>" +
                                        "<option>%</option>";
                                }else{
                                    tr+= "<option>$</option>" +
                                         "<option selected>%</option>";
                                }
                                tr+="</select></td></tr>";
                            }
                            /*if(rank){
                                tr+=  "<tr>" +
                                        "<td style='width: 300px;'>排名始终" +
                                        "<select name='competitorRanking"+tracking.id+"'>" +
                                        "<option value='0'>竞争对手排名</option>";
                                if(rank.ruletype=="rankLowerType"){
                                    tr+="<option value='1' selected>低于竞争对手</option>" +
                                            "<option value='2'>高于竞争对手</option>";
                                }else{
                                    tr+="<option value='1'>低于竞争对手</option>" +
                                            "<option value='2' selected>高于竞争对手</option>";
                                }
                                tr+="</select>" +
                                        "<select name='competitorRankingAdd"+tracking.id+"'>";
                                if(rank.increaseordecrease=="-"){
                                    tr+= "<option selected>-</option>" +
                                            "<option >+</option>";
                                }else{
                                    tr+= "<option >-</option>" +
                                            "<option selected>+</option>";
                                }
                                tr+="</select>" +
                                        "<input name='competitorRankingInput"+tracking.id+"' value='"+rank.inputvalue+"' style='width: 50px;margin-left: 5px;margin-right: 5px; '/>" +
                                        "<select name='competitorRankingSymbol"+tracking.id+"'>";
                                if(rank.sign=="$"){
                                    tr+= "<option selected>$</option>" +
                                            "<option>%</option>";
                                }else{
                                    tr+= "<option>$</option>" +
                                            "<option selected>%</option>";
                                }
                                tr+="</select></td></tr>";
                            }*/
                            $(competitorTable).append(tr);
                            addremark();
                        }
                        Base.token;
                    },
                        function(m,r){
                            var competitorTable=document.getElementById("competitorId");
                            var tr=document.getElementById("competitorTr");
                            if(tr){
                                $(tr).remove();
                            }
                            var tr2="<tr name='competitorTr' id='competitorTr'>" +
                                    "<td></td>" +
                                    "<td valign='top'><label  class='control-label' style='line-height: 45px;' >对手物品码:</label></td>" +
                                    "<td valign='top' align='left'><ul id='addRemark' style='padding: 3px 5px 3px 5px;border: 1px solid lightgray;width: 300px;border-radius: 4px;margin-left: 4px;background-color: #ffffff;margin-top: 3px;float: left'><input style='margin-left:10px;width:40px;background-color: #fff;border-radius: 5px;' id='kk' type='text' value='' onfocus='addLength(this)' onblur='subLength(this)' onkeyup='addColor(this);' /></ul><br/><br/>" +
                                    "<span style='color: #0000ff;float: left'>请使用【enter】键添加物品码</span><br/><table style='float: left'><tr><td style='width: 300px;'>价格始终<select name='competitorPrice'><option value='0'>竞争对手价格</option><option value='1'>低于竞争对手</option><option value='2'>高于竞争对手</option></select><select name='competitorPriceAdd'><option >-</option><option  >+</option></select><input name='competitorPriceInput'  style='width: 50px;margin-left: 5px;margin-right: 5px; '/><select name='competitorPriceSymbol'><option>$</option><option>%</option></select></td></tr>" +
                                    "</table></td>" +
                                    "</tr>";
                            $(competitorTable).append(tr2);
                            addremark();
                            Base.token();
                        }]
            );
        }
        function addColor(obj){
            obj.style.color='#333';
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
        function addLength(obj){
            this.value='';
            $(obj).attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
        }
        function subLength(obj){
            if(obj.value==''){obj.value='';obj.style.color='#999';}
            $(obj).attr("style","margin-left:10px;width: 40px;background-color: #fff;border-radius: 5px;");
            $(obj).val("");
        }
    </script>
</head>
<body>
<br/><br/>
<form id="autoPriceItemForm">
    <input type="hidden" name="itemIds" id="remark123">
<table id="competitorId">
    <tr>
        <td width="50px;"></td>
        <td style="height: 50px;"><label  class="control-label" style="line-height: 20px;" >SKU:</label></td>
        <td style="height: 50px;width: 400px;"><div class="controls">
            <input type="text" onchange="queryItemId(this);" name="worker" id="worker" multiple class="multiSelect" style="width: 300px;margin-left: 5px;" value="123">
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
    <%--<tr><td></td><td ><a href="javascript:void(0)" onclick="addCompetitors();" style="color: #0000ff;margin-left: 10px;" >添加竞争对手</a></td><td></td></tr>--%>
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
                                    preload_data[i]={ id: r[i].id, text: r[i].sku};
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
        document.getElementById("select2-chosen-1").innerHTML="";
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
 var lablId = -1;

 $(function() {
     $("#kk").blur(function() {
         if (isNan(this.value) != false) {
             this.value = '';
             this.style.color = '#999';
         }
     });
 });
function addremark(){
     $("#kk").keydown(function(event) {
         if (event.keyCode == 13) {
             var str = $("#kk").val();
             if (isNan(str) != true) {
                 var li_id = $(".label li:last-child").attr('id');
                 if (li_id != undefined) {
                     li_id = li_id.split('_');
                     li_id = parseInt(li_id[1]) + 1;
                 } else {
                     li_id = 0;
                 }
                 $(".label_box").css("display", "block");
                 var text = "<a href='javascript:#' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class=\"icon-remove-sign\" style='margin-right: 2px;'></i><span >" + str + "</span><input type='hidden' name='label' value='" + str + "'></a>";
                 var spans=$("#addRemark").find("span");
                 for(var i=0;i<spans.length;i++){
                     var span=spans[i].innerHTML;
                     if(str==span){
                         return;
                     }
                 }
                 $("#kk").before(text);
             }
             $("#kk").val("");
             $("#kk").attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
         }
     })
 }
 function isNan(obj) {
     try {
         return obj == 0 ? true: !obj
     } catch(e) {
         return true;
     }
 }


 function deletes(obj) {
     $(obj).remove();
 }

 function addlabl(id) {
     if (lablId == id) {
         return;
     }
     lablId = id;
     var str = $("#add_" + id).text();
     var li_id = $(".label li:last-child").attr('id');
     if (li_id != undefined) {
         li_id = li_id.split('_');
         li_id = parseInt(li_id[1]) + 1;
     } else {
         li_id = 0;
     }
     $(".label_box").css("display", "block");
     var text = "<li id='li_" + li_id + "'><a href='javascript:;' onclick='deletes(" + li_id + ");' ><img src='images/label_03.png' class='label-pic'>" + str + "</a><input type='hidden' name='label[" + li_id + "].name' value='" + str + "'></li>";
     $(".label").append(text);
 }
</script>
</body>
</html>
