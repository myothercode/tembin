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
            map.put("allProduct",path+"/itemList.do?1=1");
            map.put("listingProduct",path+"/itemList.do?flag=1");
            map.put("product",path+"/itemList.do?flag=2");
            map.put("timeProduct",path+"/itemList.do?flag=3");
            $("a").each(function(i,d){
                if($(d).find("span").text()=="全部"){
                    $(d).find("span").attr("class","newusa_ici");
                }else if($(d).find("span").text()!=""){
                    $(d).find("span").attr("class","newusa_ici_1");
                }
            });
            $("#listing_frame").attr("src",map.get(name)) ;
        }
        function selectCounty(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if($("#listing_frame").attr("src").indexOf("&county=")!=-1){
                if($("#listing_frame").attr("src").substr($("#listing_frame").attr("src").indexOf("county=")).indexOf("&")!=-1){
                    var str = $("#listing_frame").attr("src").substr($("#listing_frame").attr("src").indexOf("county="));
                    $("#listing_frame").attr("src",$("#listing_frame").attr("src").substr(0,$("#listing_frame").attr("src").indexOf("&county="))+str.substr(str.indexOf("&"))+"&county="+$(obj).attr("value"));
                }else{
                    $("#listing_frame").attr("src",$("#listing_frame").attr("src").substr(0,$("#listing_frame").attr("src").indexOf("&county="))+"&county="+$(obj).attr("value"));
                }
            }else{
                $("#listing_frame").attr("src",$("#listing_frame").attr("src")+"&county="+$(obj).attr("value"));
            }

        }
        function selectListType(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if($("#listing_frame").attr("src").indexOf("&listingtype=")!=-1){
                if($("#listing_frame").attr("src").substr($("#listing_frame").attr("src").indexOf("listingtype=")).indexOf("&")!=-1){
                    var str = $("#listing_frame").attr("src").substr($("#listing_frame").attr("src").indexOf("listingtype="));
                    $("#listing_frame").attr("src",$("#listing_frame").attr("src").substr(0,$("#listing_frame").attr("src").indexOf("&listingtype="))+str.substr(str.indexOf("&"))+"&listingtype="+$(obj).attr("value"));
                }else{
                    $("#listing_frame").attr("src",$("#listing_frame").attr("src").substr(0,$("#listing_frame").attr("src").indexOf("&listingtype="))+"&listingtype="+$(obj).attr("value"));
                }
            }else{
                $("#listing_frame").attr("src",$("#listing_frame").attr("src")+"&listingtype="+$(obj).attr("value"));
            }
        }
        function selectEbayAccount(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if($("#listing_frame").attr("src").indexOf("&ebayaccount=")!=-1){
                if($("#listing_frame").attr("src").substr($("#listing_frame").attr("src").indexOf("ebayaccount=")).indexOf("&")!=-1){
                    var str = $("#listing_frame").attr("src").substr($("#listing_frame").attr("src").indexOf("ebayaccount="));
                    $("#listing_frame").attr("src",$("#listing_frame").attr("src").substr(0,$("#listing_frame").attr("src").indexOf("&ebayaccount="))+str.substr(str.indexOf("&"))+"&ebayaccount="+$(obj).attr("value"));
                }else{
                    $("#listing_frame").attr("src",$("#listing_frame").attr("src").substr(0,$("#listing_frame").attr("src").indexOf("&ebayaccount="))+"&ebayaccount="+$(obj).attr("value"));
                }
            }else{
                $("#listing_frame").attr("src",$("#listing_frame").attr("src")+"&ebayaccount="+$(obj).attr("value"));
            }

        }
        var selectStr = "";
        var selectQuery="";
        function selectData(){
            var urls = $("#listing_frame").attr("src");
            var selectType = $("select[name='selecttype']").find("option:selected").val();
            var selectValue = $("input[name='selectvalue']").val();
            if(urls.indexOf(selectQuery)>0){
                urls = urls.replace(selectQuery,"");
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;

            }else{
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;
            }
            if(selectType!=null&&selectType!=""){
                selectQuery="&selectType="+selectType+"&selectValue="+selectValue;
            }
            $("#listing_frame").attr("src",urls);
        }

        function selectAllData(obj){
            var urls = $("#listing_frame").attr("src");
            if(urls.indexOf(selectQuery)>0) {
                urls = urls.replace(selectQuery,"");
            }
            $(obj).parent().find("select[name='selecttype']").val("");
            $(obj).parent().find("input[name='selectvalue']").val("");
            $("#listing_frame").attr("src",urls);
        }
        var amendType = "";
        var amendFlag = "";
        function selectAmendType(obj){
            var urls = $("#listing_frame").attr("src");
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if(urls.indexOf(amendType)>0) {
                urls = urls.replace(amendType,"");
                urls = urls+"&amendType="+$(obj).attr("value");
            }else{
                urls = urls+"&amendType="+$(obj).attr("value");
            }
            amendType="&amendType="+$(obj).attr("value");
            $("#listing_frame").attr("src",urls);

        }

        function selectAmendFlag(obj){
            var urls = $("#listing_frame").attr("src");
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if(urls.indexOf(amendFlag)>0) {
                urls = urls.replace(amendFlag,"");
                urls = urls+"&amendFlag="+$(obj).attr("value");
            }else{
                urls = urls+"&amendFlag="+$(obj).attr("value");
            }
            amendFlag="&amendFlag="+$(obj).attr("value");
            $("#listing_frame").attr("src",urls);
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
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list"><span class="newusa_i">选择国家：</span>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="311"><span class="newusa_ici_1"><img src="../../img/usa_1.png">美国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="310"><span class="newusa_ici_1"><img src="../../img/usa_2.png">英国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="298"><span class="newusa_ici_1"><img src="../../img/usa_2.png">德国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="291"><span class="newusa_ici_1"><img src="../../img/usa_2.png">澳大利亚</span></a>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">选择账号：</span>
                    <a href="javascript:void(0)" onclick="selectEbayAccount(this)"  value=""><span class="newusa_ici">全部</span></a>
                    <c:forEach items="${ebayList}" var="ebay">
                        <a href="javascript:void(0)" onclick="selectEbayAccount(this)" value="${ebay.id}"><span class="newusa_ici_1">${ebay.ebayNameCode}</span></a>
                    </c:forEach>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">刊登类型：</span>

                    <a href="javascript:void(0)" onclick="selectListType(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="FixedPriceItem"><span class="newusa_ici_1">固价</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="2"><span class="newusa_ici_1">多属性</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Chinese"><span class="newusa_ici_1">拍卖</span></a>
                </li>
                <div class="newsearch">
                    <span class="newusa_i">搜索内容：</span>
                    <a href="javascript:void(0)" onclick="selectAllData(this)" value=""><span class="newusa_ici">全部</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="selecttype" class="select">
    <option selected="selected" value="">选择类型</option>
    <option value="sku">sku</option>
    <option value="title">物品标题</option>
</select>
</span>
</span>

                    <div class="vsearch">
                        <input name="selectvalue" type="text" class="key_1"><input name="newbut" onclick="selectData()" type="button" class="key_2"></div>
                </div>

            <iframe src="/xsddWeb/itemList.do?1=1" id="listing_frame" height="1000px;" frameborder="0" width="100%">

            </iframe>
        </div>

    </div>
</div>
</body>
</html>
