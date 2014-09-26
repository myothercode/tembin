<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/23
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <title></title>
    <script>
        function setTab(obj){
            var name=$(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if($(d).attr("name")==name){
                    $(d).attr("class","new_tab_1");
                }else{
                    $(d).attr("class","new_tab_2");
                }
            });

            var map=new Map();
            map.put("allProduct",path+"/itemList.do");
            map.put("listingProduct",path+"/itemList.do?flag=1");
            map.put("product",path+"/itemList.do?flag=2");
            map.put("timeProduct",path+"/itemList.do?flag=3");
            $("#listing_frame").attr("src",map.get(name)) ;
        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>范本</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls">
            <dt name="allProduct" class=new_tab_1 onclick="setTab(this)">所有范本</dt>
            <dt name="listingProduct" class=new_tab_2 onclick="setTab(this)">有在线刊登</dt>
            <dt name="product" class=new_tab_2 onclick="setTab(this)">无在线刊登</dt>
            <dt name="timeProduct" class=new_tab_2 onclick="setTab(this)">定时</dt>
        </div>
        <div class=Contentbox>
            <iframe src="/xsddWeb/itemList.do" id="listing_frame" height="1000px;" frameborder="0" width="100%">

            </iframe>
        </div>

    </div>
</div>
</body>
</html>
