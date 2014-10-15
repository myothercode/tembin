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
    <title></title>
    <script>
        $(document).ready(function(){
            var parentId = '${parentId}';
            var urls="/ajax/getListItemDataAmend.do?1=1&parentId="+parentId;
            $("#itemTable").initTable({
                url:path + urls,
                columnData:[
                    {title:"图片",name:"Option1",width:"8%",align:"left",format:picUrl},
                    {title:"物品标题",name:"title",width:"8%",align:"left"},
                    {title:"SKU",name:"sku",width:"8%",align:"left"},
                    {title:"ebay账户",name:"ebayAccount",width:"8%",align:"left"},
                    /*{title:"站点",name:"site",width:"8%",align:"left"},
                    {title:"刊登类型",name:"listingType",width:"8%",align:"left"},
                    {title:"价格",name:"price",width:"8%",align:"left"},
                    {title:"数量/已售",name:"price",width:"8%",align:"left"},
                    {title:"商品数量",name:"Option1",width:"8%",align:"left",format:tjCount},*/
                    {title:"修改时间",name:"amendTime",width:"8%",align:"left"},
                    {title:"操作内容",name:"content",width:"8%",align:"left"},
                    {title:"状态",name:"content",width:"8%",align:"left",format:itemstatus}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function itemstatus(json){
            var htm = "";
            if(json.isFlag=="1"){
                htm="<img src='"+path+"/img/new_yes.png'>";
            }else{
                htm="<img src='"+path+"/img/new_no.png'>";
            }
            return htm;
        }
        function picUrl(json){
            var htm="<img width='50px' height='50px' src='"+json.picUrl+"'>";
            return htm;
        }
        function tjCount(json){
            var htm=json.quantity+"/"+json.quantitysold;
            return htm;
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"edit('"+json.itemId+"');\">在线编辑</a>";
            return htm;
        }
        function  refreshTable(){
            $("#itemTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        //在线编辑
        var editPage = "";
        function edit(itemid){
            editPage = $.dialog({title: '编辑在线商品',
                content: 'url:/xsddWeb/editListingItem.do?itemid='+itemid,
                icon: 'succeed',
                width:1000
            });
        }
    </script>
</head>
<body>
<div id="itemTable">
</div>
</body>
</html>
