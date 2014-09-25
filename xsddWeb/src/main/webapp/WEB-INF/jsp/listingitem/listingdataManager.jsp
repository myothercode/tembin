<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/23
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var map = new Map();
        map.put("listing", path + "/getListingItemList.do?1=1");
        map.put("sold", path + "/getListingItemList.do?flag=1");
        map.put("unsold", path + "/getListingItemList.do?flag=2");
        map.put("updatelog", path + "/ReturnpolicyList.do");
        function setTab(obj) {
            var name = $(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if ($(d).attr("name") == name) {
                    $(d).attr("class", "new_tab_1");
                } else {
                    $(d).attr("class", "new_tab_2");
                }
            });
            $("#listing_frame").attr("src", map.get(name));
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
        function selectData(){
            var urls = $("#listing_frame").attr("src");
            var selectType = $("select[name='selecttype']").find("option:selected").val();
            var selectValue = $("input[name='selectvalue']").val();
            if(urls.indexOf(selectStr)>0){
                urls.replace(selectStr,"");
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;
            }else{
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;
            }
            if(selectType!=null&&selectType!=""){
                selectStr="&selectType="+selectType+"&selectValue="+selectValue;
            }
            $("#listing_frame").attr("src",urls);
        }

        function selectAllData(obj){
            var urls = $("#listing_frame").attr("src");
            if(urls.indexOf(selectStr)>0) {
                urls = urls.replace(selectStr,"");
            }
            $(obj).parent().find("select[name='selecttype']").val("");
            $(obj).parent().find("input[name='selectvalue']").val("");
            $("#listing_frame").attr("src",urls);
        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>刊登</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls">
            <dt name="listing" class=new_tab_1 onclick="setTab(this)">在线</dt>
            <dt name="sold" class=new_tab_2 onclick="setTab(this)">已售</dt>
            <dt name="unsold" class=new_tab_2 onclick="setTab(this)">未卖出</dt>
            <dt name="updatelog" class=new_tab_2 onclick="setTab(this)">在线修改日志</dt>
        </div>
        <div class=Contentbox>
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list"><span class="newusa_i">选择国家：</span>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="US"><span class="newusa_ici_1"><img src="../../img/usa_1.png">美国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="UK"><span class="newusa_ici_1"><img src="../../img/usa_2.png">英国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="Germany"><span class="newusa_ici_1"><img src="../../img/usa_2.png">德国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="Australia"><span class="newusa_ici_1"><img src="../../img/usa_2.png">澳大利亚</span></a>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">选择账号：</span>
                    <a href="javascript:void(0)" onclick="selectEbayAccount(this)"  value=""><span class="newusa_ici">全部</span></a>
                    <c:forEach items="${ebayList}" var="ebay">
                        <a href="javascript:void(0)" onclick="selectEbayAccount(this)" value="${ebay.ebayName}"><span class="newusa_ici_1">${ebay.ebayNameCode}</span></a>
                    </c:forEach>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">刊登类型：</span>

                    <a href="javascript:void(0)" onclick="selectListType(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="FixedPriceItem"><span class="newusa_ici_1">固价</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="2"><span class="newusa_ici_1">多属性</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Chinese"><span class="newusa_ici_1">拍买</span></a>
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
                <div class="newds">
                    <div class="newsj_left">

                        <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"/></span>

                        <div class="numlist">
                            <div class="ui-select" style="margin-top:1px; width:10px">
                                <select>
                                    <option value="AK">选项</option>
                                    <option value="AK">动作</option>
                                </select>
                            </div>
                            <div class="ui-select" style="margin-top:1px; width:10px">
                                <select>
                                    <option value="AK">移动</option>
                                    <option value="AK">动作</option>
                                </select>
                            </div>
                        </div>
                        <span class="newusa_ici_del">提前结束</span><span class="newusa_ici_del"
                                                                      style="color:#F90">表格调价</span><span
                            class="newusa_ici_del">新建文件夹</span></div>
                    <div class="tbbay"><a data-toggle="modal" href="#myModal" class="">同步eBay</a></div>
                </div>
            </div>
            <iframe src="/xsddWeb/getListingItemList.do?1=1" id="listing_frame" height="1000px;" frameborder="0"
                    width="100%">
            </iframe>
        </div>

    </div>
</div>
</body>
</html>
