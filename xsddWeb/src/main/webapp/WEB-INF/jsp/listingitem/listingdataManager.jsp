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
        function setTab(obj) {
            var name = $(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if ($(d).attr("name") == name) {
                    $(d).attr("class", "new_tab_1");
                } else {
                    $(d).attr("class", "new_tab_2");
                }
            });

            var map = new Map();
            map.put("listing", path + "/getListingItemList.do");
            map.put("sold", path + "/getListingItemList.do?flag=1");
            map.put("unsold", path + "/getListingItemList.do?flag=2");
            map.put("updatelog", path + "/ReturnpolicyList.do");
            $("#listing_frame").attr("src", map.get(name));
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
                <li class="new_usa_list"><span class="newusa_i">选择国家：全部</span><a href="#"><span class="newusa_ic"><img
                        src="../../img/usa_1.png">国家( 2651)</span></a><a href="#"><span class="newusa_ic_1"><img
                        src="../../img/usa_2.png">国家( 2651)</span></a><a href="#"><span class="newusa_ic_1"><img
                        src="../../img/usa_2.png">国家( 2651)</span></a><a href="#"><span class="newusa_ic_1"><img
                        src="../../img/usa_2.png">国家( 2651)</span></a></li>
                <li class="new_usa_list"><span class="newusa_i">选择账号：全部</span><span class="newusa_ici">eBay账号</span><a
                        href="#"><span class="newusa_ici_1">亚马逊账号</span></a><a href="#"><span
                        class="newusa_ici_1">亚马逊账号</span></a><a href="#"><span class="newusa_ici_1">亚马逊账号</span></a><a
                        href="#"><span class="newusa_ici_1">亚马逊账号</span></a></li>
                <li class="new_usa_list"><span class="newusa_i">刊登类型：全部</span><span class="newusa_ici">固价</span><a
                        href="#"><span class="newusa_ici_1">多属性</span></a><a href="#"><span
                        class="newusa_ici_1">多属性</span></a></li>
                <div class="newsearch">
                    <span class="newusa_i">搜索内容：全部</span>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select">
    <option selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>

                    <div class="vsearch">
                        <input name="" type="text" class="key_1"><input name="newbut" type="button" class="key_2"></div>
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
            <iframe src="/xsddWeb/getListingItemList.do" id="listing_frame" height="1000px;" frameborder="0"
                    width="100%">
            </iframe>
        </div>

    </div>
</div>
</body>
</html>
