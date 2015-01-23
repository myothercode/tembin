<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <title></title>
    <script>
        var api = frameElement.api, W = api.opener;
        function closeWin(){
            W.quickEdits.close();
        }

        function submit(){
            var url=path+"/ajax/savequickData.do";
            var data=$("#quireData").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.reshTable();
                        W.quickEdits.close();
                    },
                        function(m,r){
                            alert(r);
                        }],{isConverPage:true}
            );
        }
    </script>
</head>
<body>
<form id="quireData">
    <input type="hidden" name="listingType" value="${listingType}">
    <div id="itemTable" class="Contentbox">
        <br>
        <table width="100%">
            <tr style="height: 38px;">
                <td bgcolor="#F7F7F7" height="30" align="center">图片</td>
                <td bgcolor="#F7F7F7" height="30">物品ID/SKU</td>
                <td bgcolor="#F7F7F7" height="30">ebay账户</td>
                <td bgcolor="#F7F7F7" height="30">标题/子标题</td>
                <c:if test="${listingType=='Chinese'}">
                    <td bgcolor="#F7F7F7" height="30">超始价</td>
                    <td bgcolor="#F7F7F7" height="30">保留价</td>
                    <td bgcolor="#F7F7F7" height="30">一口价</td>
                    <td bgcolor="#F7F7F7" height="30">数量</td>
                </c:if>
                <c:if test="${listingType=='2'}">

                </c:if>
                <c:if test="${listingType=='FixedPriceItem'}">
                    <td bgcolor="#F7F7F7" height="30">价格</td>
                    <td bgcolor="#F7F7F7" height="30">数量</td>
                </c:if>
            </tr>
            <c:forEach items="${litld}" var="tld" varStatus="idx">
                <tr>
                    <td>
                        <img width='50px' height='50px' src='${tld.picUrl}'>
                        <input type="hidden" name="itemid_${idx.index}" value="${tld.itemId}">
                        <input type="hidden" name="ids" value="${tld.id}">
                        <input type="hidden" name="id_${idx.index}" value="${tld.id}">
                    </td>
                    <td>
                            ${tld.itemId}<br>
                        <c:if test="${listingType!='2'}">
                            <input type="text" style='width:100px;height:27px;margin-top: 5px;margin-bottom: 5px;' class='form-control' name="sku_${idx.index}" size="6" value="${tld.sku}">
                        </c:if>
                    </td>
                    <td>${tld.ebayAccount}</td>
                    <td>
                        <input type="text" class="form-control" style="height:27px;margin-top: 5px;margin-bottom: 5px;" name="title_${idx.index}" value="${tld.title}" size="40">
                        <br><input type="text"  class="form-control" style="height:27px;margin-top: 5px;margin-bottom: 5px;" name="subtitle_${idx.index}" value="${tld.subtitle}"  size="40">
                    </td>
                    <c:if test="${listingType=='Chinese'}">
                        <td><input type="text" name="buyitnowprice_${idx.index}" value="${tld.buyitnowprice}" size="2"><span style="line-height: 27px;">${tld.listingduration}</span></td>
                        <td><input type="text" name="price_${idx.index}" value="${tld.price}" size="2"><span style="line-height: 27px;">${tld.listingduration}</span></td>
                        <td><input type="text" name="reserveprice_${idx.index}" value="${tld.reserveprice}" size="2"><span style="line-height: 27px;">${tld.listingduration}</span></td>
                        <td>
                            <select>
                                <option>单独物品</option>
                                <option>批量物品</option>
                            </select>
                            <input type="text" name="quantity_${idx.index}" value="${tld.quantity}" size="2">
                        </td>
                    </c:if>
                    <c:if test="${listingType=='2'}">

                    </c:if>
                    <c:if test="${listingType=='FixedPriceItem'}">
                        <td><input type="text" value="${tld.price}"  class='newinputt' name="price_${idx.index}" size="2"><span style="line-height: 27px;">${tld.listingduration}</span></td>
                        <td height="30"><input type="text"  class='newinputt' name="quantity_${idx.index}" value="${tld.quantity}" size="2"></td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
</form>
<div>
    <div class="control-group" style="text-align: right;">
        <div class="controls">
            <button class="net_put" onclick="submit();">确定</button>
            <button class="net_put_1" onclick="closeWin();">关闭</button>
        </div>
    </div>
</div>
</body>
</html>
