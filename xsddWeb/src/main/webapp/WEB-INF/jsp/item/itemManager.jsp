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
    <title>范本列表</title>
    <script type="text/javascript" src=<c:url value ="/js/item/itemManager.js" /> ></script>

    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>

    <style>
        .newusa_i{
            width: 74px;
        }
    </style>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>范本</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <input type="hidden" name="timerListing" id="timerListing">
        <input type="hidden" id="idStr">
        <div class="new_tab_ls" id="tab">

        </div>
        <div class=Contentbox id="Contentbox">
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list" id="li_countyselect"><span class="newusa_i">选择国家：</span>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="311"><span class="newusa_ici_1"><img src="<c:url value ='/img/usa_1.png'/> ">美国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="310"><span class="newusa_ici_1"><img src="<c:url value ='/img/UK.jpg'/> ">英国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="298"><span class="newusa_ici_1"><img src="<c:url value ='/img/DE.png'/> ">德国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="291"><span class="newusa_ici_1"><img src="<c:url value ='/img/AU.jpg'/> ">澳大利亚</span></a>
                    <a href="javascript:void(0)" onclick="selectSiteList(this)"><span style="padding-left: 20px;vertical-align: middle;color: #5F93D7">更多...</span></a>
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
                    <a name="t_a" href="javascript:void(0)" onclick="selectAllData(this)" value="">
                        <span class="newusa_ici">全部</span>
                    </a>
                    <a name="t_a" href="javascript:void(0)" onclick="chageOldDom(this)" value="sku">
                        <span class="newusa_ici_1" >SKU</span>
                    </a>
                    <a name="t_a" href="javascript:void(0)" onclick="chageOldDom(this)" value="title">
                        <span class="newusa_ici_1" >物品标题</span>
                    </a>
                    <a name="t_a" href="javascript:void(0)" onclick="chageOldDom(this)" value="item_name">
                        <span class="newusa_ici_1" >范本名称</span>
                    </a>
                    <span>
                        <input id="_selectvalue" type="text" style="width:200px;float: left"  multiple class="multiSelect">
                        <input name="newbut" onclick="selectData()" type="button" class="key_2">
                    </span>

                </div>


                <div class="newsearch" style="display: none">
                    <span class="newusa_i">搜索内容：</span>
                    <a href="javascript:void(0)" onclick="selectAllData(this)" value=""><span class="newusa_ici">全部</span></a>
                    <span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
                    <select name="selecttype" class="select" style="color: #737FA7;width: 80px;">
                        <option selected="selected" value="">选择类型</option>
                        <option value="sku">SKU</option>
                        <option value="title">物品标题</option>
                        <option value="item_name">范本名称</option>
                    </select>
                    </span>
                    </span>

                    <div class="vsearch">
                        <input name="selectvalue" type="text" class="key_1"  style="vtical-align:middle;line-height:100%;"><input name="newbut" onclick="selectData()" type="button" class="key_2">
                    </div>
                </div>
                <div class="newds">
                    <div class="newsj_left">

                        <span class="newusa_ici_del_in" style="padding-left: 4px;">
                            <input type="checkbox" onclick="onselectAlls(this);" name="checkbox" id="checkbox"/>
                        </span>

                        <div class="numlist" style="padding-left: 8px;">
                            <div class="ui-select" style="margin-top:1px; width:80px;min-width:0px;">
                                <select onchange="changeSelect(this)" id="selectMoreType" style="width: 80px;padding: 0px;">
                                    <option value="">&nbsp;&nbsp;&nbsp;&nbsp;请选择</option>
                                    <option value="del">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;删除</option>
                                    <option value="copy">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;复制</option>
                                    <option value="timerListingItem">&nbsp;&nbsp;定时刊登</option>
                                    <option value="rename">&nbsp;&nbsp;&nbsp;&nbsp;重命名</option>
                                    <option value="editMoreItem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编辑</option>
                                    <option value="remark">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注</option>
                                </select>
                            </div>
                        </div>
                        <span class="newusa_ici_del" onclick="shiftToFolder(this)">移动到</span>
                        <span class="newusa_ici_del" onclick="listing(this)" id="listing">立即刊登</span>
                        <span class="newusa_ici_del"  onclick="addTabRemark();">管理文件夹</span></div>
                    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addItem()">新增范本</a></div>
                </div>
            <%--<iframe src="/xsddWeb/itemList.do?1=1" id="listing_frame" height="1000px;" frameborder="0" width="100%">

            </iframe>--%>
                <div style="width: 100%;float: left;height: 5px"></div>
                <div id="itemTable"></div>
        </div>

    </div>
</div>
</div>
</body>
</html>
