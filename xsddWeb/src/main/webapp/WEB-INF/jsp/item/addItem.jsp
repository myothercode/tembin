<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>

    <script>
        var myDescription=null;
        $(document).ready(function() {
            $("#form").validationEngine();
            myDescription = UE.getEditor('myDescription');
        });
        function saveData(objs) {
            if(!$("#form").validationEngine("validate")){
                return;
            }
            var nameList = $("input[type='text'][name='name']").each(function(i,d){
                var name_= $(d).prop("name");
                var t="ItemSpecifics.NameValueList["+i+"].";
                $(d).prop("name",t+name_);
            });
            var valueList = $("input[type='text'][name='value']").each(function(i,d){
                var name_= $(d).prop("name");
                var t="ItemSpecifics.NameValueList["+i+"].";
                $(d).prop("name",t+name_);
            });
            $("#Description").val(myDescription.getContent());
            var data = $('#form').serialize();
            var urll = "/xsddWeb/saveItem.do";
            $(objs).attr("disabled",true);
            var api = frameElement.api, W = api.opener;
            $().invoke(
                    urll,
                    data,
                    [function (m, r) {
                        Base.token();
                        alert(r);
                        $(objs).attr("disabled",false);
                        W.refreshTable();
                        W.returnItem.close();
                    },
                        function (m, r) {
                            Base.token();
                            $(objs).attr("disabled",false);
                        }]
            )
        }
        function addValueTr(obj1,obj2){
            var trStr='<tr><td><input type="text" name="name" value="'+obj1+'"></td><td><input type="text" name="value" value="'+obj2+'"></td></tr>';
            return trStr;
        }
        function addAttrTr(name,value){
            var trStr='<tr><td>'+name+'</td><td><input type="text" name="'+name+'" value="'+value+'"></td></tr>';
            return trStr;
        }
        /**
        *添加自定义属性
         */
        function addValue(){
            $("#trValue").after().append(addValueTr('',''));
        }
        /**
        *添加定固定属性
         */
        function addAttr(name){
            $("#trValue").after().append(addAttrTr(name,''));
        }
   </script>
</head>
<c:set var="item" value="${item}"/>
<body>
<form id="form">
    <table width="100%">
        <tr>
            <td colspan="2">
                一般信息
                <hr/>
            </td>
        </tr>
        <tr>
            <td width="200" align="right">名称</td>
            <td><input type="text" class="validate[required]" name="itemName" id="itemName" ></td>
        </tr>
        <tr>
            <td align="right">ebay账户</td>
            <td>
                <select name="ebayAccount" class="validate[required]" >
                    <c:forEach items="${ebayList}" var="ebay">
                        <option value="${ebay.id}">${ebay.configName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right">站点</td>
            <td>
                <select name="site">
                    <c:forEach items="${siteList}" var="sites">
                        <option value="${sites.id}">${sites.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right">刊登类型</td>
            <td>
                <input type="radio" name="listingType" value="0" disabled>拍买
                <input type="radio" name="listingType" value="1" checked>固价
                <input type="radio" name="listingType" value="2">多属性
            </td>
        </tr>
        <tr>
            <td align="right">SKU</td>
            <td><input type="text" name="SKU" id="SKU"></td>
        </tr>
        <tr>
            <td align="right" style="vertical-align: top;">物品标题</td>
            <td>
                标题<input type="text" name="Title" id="Title" value="">
                <br/>
                子标题<input type="text" name="SubTitle" id="SubTitle" value="">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                分类
                <hr/>
            </td>
        </tr>
        <tr>
            <td align="right">第一分类</td>
            <td>
                <input type="text" name="PrimaryCategory.categoryID" title="PrimaryCategory.categoryID" value="">
            </td>
        </tr>
        <tr>
            <td align="right">第二分类</td>
            <td>
                <input type="text" name="SecondaryCategory.CategoryID" title="SecondaryCategory.CategoryID" value="">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                物品属性与状况
                <hr/>
            </td>
        </tr>
        <tr>
            <td align="right" style="vertical-align: top;">自定义物品属性</td>
            <td>
                <table>
                    <tr>
                        <td align="center">名称</td>
                        <td align="center">值</td>
                    </tr>
                    <tr>
                        <td colspan="2" id="trValue"></td>
                    </tr>
                </table>
                <a href="javascript:void(0);" onclick="addValue();">添加自定义属性</a>
                <a href="javascript:void(0);" onclick="addAttr('ProductListingDetails.BrandMPN.MPN');">添加MPN</a>
                <a href="javascript:void(0);" onclick="addAttr('ProductListingDetails.BrandMPN.Brand');">添加Brand</a>
                <%--<a href="javascript:void(0);" onclick="addValue();">添加Type</a>
                <a href="javascript:void(0);" onclick="addValue();">添加Compatible Brand</a>
                <a href="javascript:void(0);" onclick="addValue();">添加Country/Region of Manufacture</a>--%>
            </td>
        </tr>
        <tr>
            <td align="right">物品状况</td>
            <td>
                <select name="ConditionID">
                    <option selected="selected" value="1000">New</option>
                    <option value="1500">New other (see details)</option>
                    <option value="2000">Manufacturer refurbished</option>
                    <option value="2500">Seller refurbished</option>
                    <option value="3000">Used</option>
                    <option value="7000">For parts or not working</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                描述
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="hidden" name="Description" id="Description">
                <script id="myDescription" type="text/plain" style="width:975px;height:300px;"></script>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="button" value="确定" onclick="saveData(this)">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
