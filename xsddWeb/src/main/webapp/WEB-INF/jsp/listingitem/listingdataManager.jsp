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
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <title></title>
    <script>
        var map = new Map();
        map.put("listing", path + "/getListingItemList.do?1=1");
        map.put("sold", path + "/getListingItemList.do?flag=1");
        map.put("unsold", path + "/getListingItemList.do?flag=2");
        map.put("updatelog", path + "/getListItemDataAmend.do?1=1");
        function setTab(obj) {
            var name = $(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if ($(d).attr("name") == name) {
                    $(d).attr("class", "new_tab_1");
                } else {
                    $(d).attr("class", "new_tab_2");
                }
            });
            if(name=="updatelog"){
                $("#amendlog").show();
            }else{
                $("#amendlog").hide();
            }
            $("a").each(function(i,d){
                if($(d).find("span").text()=="全部"){
                    $(d).find("span").attr("class","newusa_ici");
                }else if($(d).find("span").text()!=""){
                    $(d).find("span").attr("class","newusa_ici_1");
                }
            });
            if(name.indexOf("myFolder_")==-1){
                $("#listing_frame").attr("src", map.get(name));
            }else{
                $("#listing_frame").attr("src", path + "/getListingItemList.do?1=1&folderid="+$(obj).attr("val"));
            }


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
                urls=urls.replace(selectStr,"");
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
        function getCentents(){
            alert($("#centents").val());
        }
        function endItem(obj) {
            $("obj").attr({style:"color:red"});
            var item = $(document.getElementById('listing_frame').contentWindow.document.body).find("input[name='listingitemid']");
            var itemidStr = "";
            for (var i = 0; i < item.length; i++) {
                if ($(item[i])[0].checked) {
                    itemidStr += $(item[i])[0].value + ",";
                }
            }
            if(itemidStr==""){
                alert("请选择需结束的商品！");
                return ;
            }

            var tent = "<div>原因<select id='centents' name='centents'><option value='Incorrect'>Incorrect</option>" +
                    "<option value='LostOrBroken'>LostOrBroken</option>" +
                    "<option value='NotAvailable'>NotAvailable</option>" +
                    "<option value='OtherListingError'>OtherListingError</option>" +
                    "<option value='SellToHighBidder'>SellToHighBidder</option>" +
                    "<option value='Sold'>Sold</option></select></div>";
            var editPage = $.dialog({title: '提前结束原因',
                content: tent,
                icon: 'succeed',
                width: 400,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            if (iwins.parent.document.getElementById("centents").value == "") {
                                alert("结束原因必填！");
                                return false;
                            } else {
                                //alert(iwins.parent.document.getElementById("centents").selectedIndex);
                                reason = iwins.parent.document.getElementById("centents").options[iwins.parent.document.getElementById("centents").selectedIndex].value;
                                //alert(reason);
                                var url = path + "/ajax/endItem.do?itemidStr=" + itemidStr+"&reason="+reason;
                                $().invoke(url, {},
                                        [function (m, r) {
                                            alert(r);
                                            Base.token();
                                            refreshTable();
                                        },
                                            function (m, r) {
                                                alert(r);
                                                Base.token();
                                            }]
                                );
                            }
                        }
                    }
                ]
            });
            alert(reason);

        }
        function addTabRemark(){
            var url=path+"/order/selectTabRemark.do?folderType=listingFolder";
            OrderGetOrders=$.dialog({title: '选择文件夹',
                content: 'url:'+url,
                icon: 'succeed',
                width:800
            });
        }

        /**
        *表格调 价
         */
        var tablePricewin=null;
        function tablePrice(obj){
            var url=path+"/getTablePriceList.do";
            tablePricewin=$.dialog({title: '表格调价列表',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:400
            });
        }
        $(document).ready(function(){
            var url=path+"/ajax/selfFolder.do";
            $().invoke(url,{},
                    [function(m,r){
                        var htmlstr='<dt name="listing" class=new_tab_1 onclick="setTab(this)">在线</dt>'
                                +'<dt name="sold" class=new_tab_2 onclick="setTab(this)">已售</dt>'
                                +'<dt name="unsold" class=new_tab_2 onclick="setTab(this)">未卖出</dt>'
                                +'<dt name="updatelog" class=new_tab_2 onclick="setTab(this)">在线修改日志</dt>';
                        for(var i = 0;i < r.length;i++){
                            htmlstr+="<dt name='myFolder_"+i+"' val='"+r[i].id+"' class=new_tab_2 onclick='setTab(this)'>"+r[i].configName+"</dt>";
                        }
                        $("#tab").html(htmlstr);
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        });

    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>刊登</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="tab">

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
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Chinese"><span class="newusa_ici_1">拍卖</span></a>
                </li>
                <div id="amendlog" style="display: none;">
                    <li class="new_usa_list">
                        <span class="newusa_i">修改类型：</span>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value=""><span class="newusa_ici">全部</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="StartPrice"><span class="newusa_ici_1">价格</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="Title"><span class="newusa_ici_1">标题</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="Quantity"><span class="newusa_ici_1">数量</span></a>
                    </li>
                    <li class="new_usa_list">
                        <span class="newusa_i">修改状态：</span>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value=""><span class="newusa_ici">全部</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value="1"><span class="newusa_ici_1">成功</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value="0"><span class="newusa_ici_1">失败</span></a>
                    </li>
                </div>
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
                        <span class="newusa_ici_del" onclick="endItem(this)">提前结束</span>
                        <span class="newusa_ici_del" onclick="tablePrice(this)">表格调价</span><span
                            class="newusa_ici_del"  onclick="addTabRemark();">管理文件夹</span></div>
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
