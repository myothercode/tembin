<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<%--<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>--%>
<html>
<head>
    <link href=
          <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <title></title>
    <script>
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            if(!jQuery("#payPalForm").validationEngine("validate"))
            {
                return;
            }
            var skuStr = "";
            $(".select2-search-choice").each(function(i,d){
                skuStr+=$(d).find("div").text()+",";
            });
            if(skuStr!=""){
                skuStr=skuStr.substr(0,skuStr.length-1);
                $("#skuStr").val(skuStr);
            }
            var url=path+"/ajax/saveInventoryComplement.do";
            var data=$("#payPalForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        W.refreshInventoryComplementTable();
                        W.inventorycomplement.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        var preload_data=new Array();
        $(document).ready(function() {
            //编辑界面加载数据
            var indexs='';
            var skuValue='';
            <c:forEach items="${litcm}" var="tcm" varStatus="start">
                indexs = '${start.index}';
                skuValue = '${tcm.skuValue}';
                preload_data[indexs]={ id: indexs, text: skuValue,text1: skuValue};
            </c:forEach>

            initSelectMore();
            var ebayId = '${tic.ebayId}';
            if(ebayId!=null&&ebayId!=""){
                $("input[type='checkbox'][name='ebayAccounts'][value='"+ebayId+"']").attr("checked", "checked");
            }

            $('.multiSelect').select2('data', preload_data)
        });
        function winCloseSetEbay(){
            W.inventorycomplement.close();
        }
        //初始化选择框
        function initSelectMore(){
            $('.multiSelect').select2({
                multiple: true,
                query: function (query) {
                    var content = query.term;
                    var data = {results: []};
                    if (content && content != "") {
                        var url = path + "/inventory/ajax/loadInventorySkuList.do";
                        $().delayInvoke(url, {"content":content},
                                [function (m, r) {
                                    for (var i = 0; i < r.length; i++) {
                                        preload_data[i] = { id: r[i].id, text: r[i].sku, text1: r[i].sku};
                                    }
                                    $.each(preload_data, function () {
                                        if (query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0) {
                                            data.results.push({id: this.id, text: this.text });
                                        }
                                    });
                                    query.callback(data);
                                    preload_data = new Array();
                                },
                                    function (m, r) {
                                        alert(r);
                                    }]
                        );
                    } else {
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
        }
    </script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>

</head>
<body>
<%--<div class="modal-header">
    <div class="newtitle">支付方式</div>
</div>--%>
<div class="modal-body" style="padding-bottom: 5px;">
    <form class="form-horizontal" role="form" id="payPalForm">
        <table width="90%" border="0" style="margin-left:40px;">
            <tr>
                <td height="28" align="right" style="width: 20%;">商品SKU：</td>
                <td height="28">
                    <input type="hidden" name="skuStr" id="skuStr"/>
                    <input type="hidden" name="id" id="id" value="${tic.id}"/>
                    <input type="text" name="itemSku" class="form-control validate[required]" value="${tic.itemSku}"/>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td height="28" align="right">ebay账户：</td>
                <td height="28">
                    <c:forEach items="${ebayList}" var="ebay">
                        <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}" />${ebay.ebayNameCode}</em>
                    </c:forEach>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td height="28" align="right">库存SKU：</td>
                <td height="28">
                    <input name="worker" id="worker" multiple class="multiSelect" style="width:400px;">
                </td>
                <td height="28">&nbsp;</td>
            </tr>
        </table>
    </form>
</div>
<div class="modal-footer" style="margin-top:0px; ">
    <button type="button" class="net_put" onclick="submitCommit()">确定</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="winCloseSetEbay()">关闭</button>
</div>
</body>
</html>
