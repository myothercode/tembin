<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/4
  Time: 15:54
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
        var add=0;
        function addAttrabute() {
            add = add + 1;
            var addAttr1 = " <tr><td>名称</td><td>值</td></tr><tr><td><input type=\"text\" name=\"attrName\" /></td><td><input type=\"text\" name=\"attrValue\" /><a href=\"javascript:void()\" onclick=\"removeAttrabute(this)\" >移除</a></td></tr>"
            var addAttr2 = "<tr><td><input type=\"text\" name=\"attrName\" /></td><td><input type=\"text\" name=\"attrValue\" /><a href=\"javascript:void()\" onclick=\"removeAttrabute(this)\" >移除</a></td></tr>";
            var table = document.getElementById("attrabute1");
            if (add == 1) {
                table.innerHTML = addAttr1;
            }
            if (add > 1) {
                console.debug($(addAttr2));
                $(table).append($(addAttr2));
            }
        }
        function removeAttrabute(th){
            add=add-1;
            var tr=$(th).parent().parent();
            var table=document.getElementById("attrabute1");
            if(add<=0){
                add=0;
                table.innerHTML="";
            }else{
                tr.remove();
            }
        }
        var addsupplier=false;
        function addSupplier(){
            addsupplier=!addsupplier;
            var tr="<tr><td>ID:</td><td><input name=\"supplierId\"/></td></tr>" +
            "<tr><td>供应商:</td><td>" +
            "<select name=\"supplierName\">" +
            "<option>爱酷客</option>" +
            "</select>" +
            "</td></tr>" +
            "<tr><td>价格:</td><td><input name=\"supplierPrice\"/></td><td>" +
            "<select name=\"supplierCurrency\">" +
            "<option>AUD</option>" +
            "</select></td></tr>" +
            "<tr><td>厂商SKU</td><td><input name=\"supperSku\"/></td></tr>" +
            "<tr><td>备注</td><td><textarea name=\"supplierRemark\"></textarea></td></tr>";
            var table=document.getElementById("supper");
            if(addsupplier){
                table.innerHTML=tr;
            }else{
                table.innerHTML="";
            }
        }
        function closedialog() {
            window.parent.location.reload();
     /*       W.itemInformation.close();*/
        }
        function submitCommit(){
            var url=path+"/information/ajax/saveItemInformation.do";
            var data=$("#informationForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
                        /*Base.token();*/
                        W.itemInformation.close();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
    </script>
</head>
<body>
<form id="informationForm">
<input type="hidden" name="id" value="${itemInformation.id}"/>
<input type="hidden" name="inventoryid" value="${inventory.id}"/>
<input type="hidden" name="customid" value="${custom.id}"/>
<input type="hidden" name="supplierid" value="${supplier.id}"/>
<table style="width: 1000px;">
    <tr>
        <td></td><td>名称:</td><td><input type="text" name="name" value="${itemInformation.name}"/></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td><b>产品明细</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>SKU</td><td><input type="text" name="sku" value="${itemInformation.sku}"/></td>
    </tr>
    <tr>
        <td></td><td></td><td>
            <select>
                <option>UPC</option>
                <option>EAN</option>
                <option>ISBN</option>
                <option>eBay ReferenceID</option>
            </select>
            <input type="text"/>
       </td>
    </tr>
    <tr>
        <td></td><td>商品分类</td><td>
            <select name="itemType">
                <c:forEach items="${types}" var="type">
                    <option value="${type.id}">${type.configName}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td></td><td>图片</td><td><a href="javascript:void()">选择图片</a><br/>
        </td>
    </tr>
    <tr>
        <td></td><td>描述</td><td><a href="javascript:void()">添加描述</a></td>
    </tr>
    <tr>
        <td></td><td>自定义物品属性</td><td>
        <div>
            <table id="attrabute1" >
            </table>
        </div>
        <a href="javascript:void()" onclick="addAttrabute();">添加</a></td>
    </tr>
    <tr>
        <td><b>ebay</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>相关范本</td><td>此处仅计算通过PA刊登的物品。</td>
    </tr>
    <tr>
        <td><b>供应商</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <c:if test="${supplier==null}">
            <td></td><td></td><td><a href="javascript:void()" onclick="addSupplier();">添加</a><br/>
        </c:if>
        <c:if test="${supplier!=null}">
            <td></td><td></td><td><br/>
        </c:if>
        <table id="supper">
            <c:if test="${supplier!=null}">
                <tr><td>ID:</td><td><input name="supplierId" value="${supplier.supplierid}"/></td></tr>
                <tr><td>供应商:</td><td>
                <select name="supplierName">
                <option>爱酷客</option>
                </select>
                </td></tr>
                <tr><td>价格:</td><td><input name="supplierPrice" value="${supplier.price}"/></td><td>
                <select name="supplierCurrency">
                <option>AUD</option>
                </select></td></tr>
                <tr><td>厂商SKU</td><td><input name="supperSku" value="${supplier.suppersku}"/></td></tr>
                <tr><td>备注</td><td><textarea name="supplierRemark" ${supplier.remark}></textarea></td></tr>
            </c:if>
        </table>
    </td>
    </tr>
    <tr>
        <td><b>库存</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>库存</td><td>总数量:</td>
    </tr>
    <tr>
        <td></td><td>库存预警</td><td><input type="text" name="warning" value="${inventory.warningnumber}"/></td>
    </tr>
    <tr>
        <td></td><td>均价</td><td>0.00  USD</td>
    </tr>
    <tr>
        <td></td><td>长</td><td><input type="text" name="length" value="${inventory.length}"/>&nbsp;宽:&nbsp;<input type="text" name="width" value="${inventory.width}"/>&nbsp;高:&nbsp;<input type="text" name="height" value="${inventory.height}"/></td>
    </tr>
    <tr>
        <td><b>报关信息</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>申报名1</td><td><input type="text" name="customEnglishName" value="${custom.englishname}"/></td>
    </tr>
    <tr>
        <td></td><td>申报名2</td><td><input type="text" name="customName" value="${custom.name}"/></td>
    </tr>
    <tr>
        <td></td><td>重量(kg)</td><td><input type="text" name="weight" value="${custom.weight}"/></td>
    </tr>
    <tr>
        <td></td><td>申报价值</td><td><input type="text" name="declaredValue" value="${custom.declaredvalue}"/>&nbsp;
        <select name="currencyType">

        </select>
    </tr>
    <tr>
        <td></td><td>原产地</td><td><input type="text" name="place" value="${custom.productionplace}"/></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
</table>
</form>
<div>
    <input type="button" value="保存" onclick="submitCommit();"/>
    <input type="button" value="关闭" onclick="closedialog();"/>
</div>
</body>
</html>
